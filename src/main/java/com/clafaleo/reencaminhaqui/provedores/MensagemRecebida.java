package com.clafaleo.reencaminhaqui.provedores;

import java.util.List;

public record MensagemRecebida(String idExterno, String remetente, String numeroOrigem,
                               String texto, List<String> urlsMidia, String payloadBruto) {}