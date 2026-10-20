package com.clafaleo.reencaminhaqui.provedores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class ProvedorSimulado implements ProvedorMensageria {

    private static final Logger log = LoggerFactory.getLogger(ProvedorSimulado.class);

    @Override
    public String nome() { return "SIMULADO"; }

    @Override
    public boolean assinaturaValida(String corpo, Map<String, String> headers) { return true; }

    @Override
    public List<MensagemRecebida> interpretar(String corpo) { return List.of(); }

    @Override
    public String enviar(MensagemSaida s) {
        log.info("ENVIO SIMULADO de {} para {}: {}", s.de(), s.para(), s.texto());
        return "sim_" + UUID.randomUUID();
    }
}