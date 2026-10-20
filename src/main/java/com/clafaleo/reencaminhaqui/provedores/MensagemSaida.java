package com.clafaleo.reencaminhaqui.provedores;

import java.util.List;

public record MensagemSaida(String de, String para, String texto, List<String> urlsMidia) {}