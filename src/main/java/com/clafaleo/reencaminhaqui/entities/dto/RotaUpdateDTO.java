package com.clafaleo.reencaminhaqui.entities.dto;

import com.clafaleo.reencaminhaqui.entities.enums.StatusRota;

public class RotaUpdateDTO {
    private String numeroOrigem;
    private String numeroDestino;
    private StatusRota statusRota;
    private Long canalId;

    public RotaUpdateDTO() {}

    public String getNumeroOrigem() { return numeroOrigem; }
    public void setNumeroOrigem(String numeroOrigem) { this.numeroOrigem = numeroOrigem; }

    public String getNumeroDestino() { return numeroDestino; }
    public void setNumeroDestino(String numeroDestino) { this.numeroDestino = numeroDestino; }

    public StatusRota getStatusRota() { return statusRota; }
    public void setStatusRota(StatusRota statusRota) { this.statusRota = statusRota; }

    public Long getCanalId() { return canalId; }
    public void setCanalId(Long canalId) { this.canalId = canalId; }
}