package com.clafaleo.reencaminhaqui.provedores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.clafaleo.reencaminhaqui.exceptions.NegocioException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class ProvedorSimulado implements ProvedorMensageria {

    private static final Logger log = LoggerFactory.getLogger(ProvedorSimulado.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String nome() { return "SIMULADO"; }

    @Override
    public boolean assinaturaValida(String corpo, Map<String, String> headers) { return true; }

    @Override
    public List<MensagemRecebida> interpretar(String corpo) {
        try {
            JsonNode n = mapper.readTree(corpo);
            return List.of(new MensagemRecebida(
                    n.path("idExterno").asText(null),
                    n.path("remetente").asText(null),
                    n.path("numeroOrigem").asText(null),
                    n.path("texto").asText(""),
                    List.of(),
                    corpo));
        } catch (Exception e) {
            throw NegocioException.invalido("JSON inválido no webhook simulado");
        }
    }

    @Override
    public String enviar(MensagemSaida s) {
        log.info("ENVIO SIMULADO de {} para {}: {}", s.de(), s.para(), s.texto());
        return "sim_" + UUID.randomUUID();
    }
}