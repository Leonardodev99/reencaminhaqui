package com.clafaleo.reencaminhaqui.controllers;

import  com.clafaleo.reencaminhaqui.services.ReencaminhamentoOrquestrador;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("/api/webhooks")
public class WebhookController {

    private final ReencaminhamentoOrquestrador orquestrador;

    public WebhookController(ReencaminhamentoOrquestrador orquestrador) {
        this.orquestrador = orquestrador;
    }

    @PostMapping("/{provedor}")
    public ResponseEntity<Void> receber(@PathVariable String provedor,
                                        @RequestBody String corpo,
                                        @RequestHeader Map<String, String> headers) {
        orquestrador.processar(provedor, corpo, headers);
        return ResponseEntity.ok().build();   // responder rápido, o provedor reenvia se demorar
    }
}