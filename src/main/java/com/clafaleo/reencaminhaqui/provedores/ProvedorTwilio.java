package com.clafaleo.reencaminhaqui.provedores;

import com.clafaleo.reencaminhaqui.exceptions.NegocioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Component
public class ProvedorTwilio implements ProvedorMensageria {

    private final String authToken;
    private final String webhookUrl;
    private final String prefixo;
    private final String templateTeste;
    private final RestClient http;

    public ProvedorTwilio(@Value("${twilio.account-sid}") String sid,
                          @Value("${twilio.auth-token}") String authToken,
                          @Value("${twilio.webhook-url}") String webhookUrl,
                          @Value("${twilio.prefixo:whatsapp:}") String prefixo,
                          @Value("${twilio.template-teste:}") String templateTeste) {
        this.authToken = authToken;
        this.webhookUrl = webhookUrl;
        this.prefixo = prefixo;
        this.templateTeste = templateTeste;
        this.http = RestClient.builder()
                .baseUrl("https://api.twilio.com/2010-04-01/Accounts/" + sid)
                .defaultHeaders(h -> h.setBasicAuth(sid, authToken))
                .build();
    }

    @Override
    public String nome() { return "TWILIO"; }

    @Override
    public boolean assinaturaValida(String corpo, Map<String, String> headers) {
        String recebida = headers.entrySet().stream()
                .filter(e -> e.getKey().equalsIgnoreCase("X-Twilio-Signature"))
                .map(Map.Entry::getValue).findFirst().orElse(null);
        if (recebida == null) return false;
        try {
            StringBuilder dados = new StringBuilder(webhookUrl);
            parametros(corpo).forEach((k, v) -> dados.append(k).append(v)); // TreeMap: ordenado
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(authToken.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
            String esperada = Base64.getEncoder()
                    .encodeToString(mac.doFinal(dados.toString().getBytes(StandardCharsets.UTF_8)));
            return MessageDigest.isEqual(esperada.getBytes(StandardCharsets.UTF_8),
                                         recebida.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<MensagemRecebida> interpretar(String corpo) {
        Map<String, String> p = parametros(corpo);
        if (!p.containsKey("MessageSid") || !p.containsKey("From")) {
            return List.of(); // ex.: callbacks de status, ignorar
        }
        int n = Integer.parseInt(p.getOrDefault("NumMedia", "0"));
        List<String> midias = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (p.containsKey("MediaUrl" + i)) midias.add(p.get("MediaUrl" + i));
        }
        return List.of(new MensagemRecebida(
                p.get("MessageSid"),
                semPrefixo(p.get("From")),
                semPrefixo(p.get("To")),
                p.getOrDefault("Body", ""),
                midias,
                corpo));
    }

    @Override
    public String enviar(MensagemSaida s) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("From", prefixo + s.de());
        form.add("To", prefixo + s.para());
        // em contas trial o Twilio só aceita templates predefinidos: se houver um configurado, usa-o
        form.add("Body", templateTeste.isBlank() ? s.texto() : templateTeste);

        Map<?, ?> resp = http.post()
                .uri("/Messages.json")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(Map.class);

        if (resp == null || resp.get("sid") == null) {
            throw NegocioException.invalido("Resposta inesperada do Twilio");
        }
        return resp.get("sid").toString();
    }

    private String semPrefixo(String numero) {
        return numero == null ? null : numero.replaceFirst("^whatsapp:", "");
    }

    private Map<String, String> parametros(String corpo) {
        Map<String, String> m = new TreeMap<>();
        if (corpo == null || corpo.isBlank()) return m;
        for (String par : corpo.split("&")) {
            int i = par.indexOf('=');
            String k = URLDecoder.decode(i < 0 ? par : par.substring(0, i), StandardCharsets.UTF_8);
            String v = i < 0 ? "" : URLDecoder.decode(par.substring(i + 1), StandardCharsets.UTF_8);
            m.put(k, v);
        }
        return m;
    }
}