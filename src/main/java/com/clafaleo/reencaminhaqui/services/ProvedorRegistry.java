package com.clafaleo.reencaminhaqui.services;

import com.clafaleo.reencaminhaqui.exceptions.NegocioException;
import com.clafaleo.reencaminhaqui.provedores.ProvedorMensageria;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProvedorRegistry {

    private final Map<String, ProvedorMensageria> porNome;

    public ProvedorRegistry(List<ProvedorMensageria> provedores) {
        this.porNome = provedores.stream()
                .collect(Collectors.toMap(p -> p.nome().toUpperCase(), Function.identity()));
    }

    public boolean existe(String nome) {
        return nome != null && porNome.containsKey(nome.trim().toUpperCase());
    }

    public ProvedorMensageria get(String nome) {
        if (!existe(nome)) {
            throw NegocioException.invalido("Provedor não suportado: " + nome
                    + ". Disponíveis: " + porNome.keySet());
        }
        return porNome.get(nome.trim().toUpperCase());
    }
}