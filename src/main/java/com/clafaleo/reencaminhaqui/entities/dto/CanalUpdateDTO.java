package com.clafaleo.reencaminhaqui.entities.dto;

import com.clafaleo.reencaminhaqui.entities.enums.StatusCanal;

public class CanalUpdateDTO {
    
    private String nome;
    private String provedor;
    private StatusCanal statusCanal;

    public CanalUpdateDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getProvedor() { return provedor; }
    public void setProvedor(String provedor) { this.provedor = provedor; }

    public StatusCanal getStatusCanal() { return statusCanal; }
    public void setStatusCanal(StatusCanal statusCanal) { this.statusCanal = statusCanal; }

}