package com.clafaleo.reencaminhaqui.entities.dto;

import com.clafaleo.reencaminhaqui.entities.Canal;
import com.clafaleo.reencaminhaqui.entities.enums.StatusCanal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CanalDTO {
    private Long id;

    @NotBlank
    private String nome;

    private String provedor;

    @NotNull
    private StatusCanal statusCanal;

    public CanalDTO() {}

    public CanalDTO(Long id, String nome, String provedor, StatusCanal statusCanal) {
        this.id = id;
        this.nome = nome;
        this.provedor = provedor;
        this.statusCanal = statusCanal;
    }

    public CanalDTO(Canal canal) {
        if (canal != null) {
            id = canal.getId();
            nome = canal.getNome();
            provedor = canal.getProvedor();
            statusCanal = canal.getStatusCanal();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProvedor() {
        return provedor;
    }

    public void setProvedor(String provedor) {
        this.provedor = provedor;
    }

    public StatusCanal getStatusCanal() {
        return statusCanal;
    }

    public void setStatusCanal(StatusCanal statusCanal) {
        this.statusCanal = statusCanal;
    }
}