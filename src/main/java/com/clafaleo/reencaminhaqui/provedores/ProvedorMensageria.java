package com.clafaleo.reencaminhaqui.provedores;

import java.util.List;
import java.util.Map;

public interface ProvedorMensageria {
    String nome();                                            // "SIMULADO", "TWILIO", "META"...
    boolean assinaturaValida(String corpo, Map<String, String> headers);
    List<MensagemRecebida> interpretar(String corpo);         // normaliza o payload do provedor
    String enviar(MensagemSaida saida);                       // devolve idTransacaoProvedor
}