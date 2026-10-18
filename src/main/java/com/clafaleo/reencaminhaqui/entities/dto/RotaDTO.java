package com.clafaleo.reencaminhaqui.entities.dto;

import com.clafaleo.reencaminhaqui.entities.Rota;
import com.clafaleo.reencaminhaqui.entities.enums.StatusRota;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class RotaDTO {
    private Long id;

    @NotBlank
    private String numeroOrigem;

    @NotBlank
    private String numeroDestino;

    private LocalDateTime criadoEm;

    @NotNull
    private StatusRota statusRota;

    // referência ao canal por id para evitar serializar a entidade inteira
    private Long canalId;

    public RotaDTO() {}

    public RotaDTO(Long id, String numeroOrigem, String numeroDestino, LocalDateTime criadoEm, StatusRota statusRota, Long canalId) {
        this.id = id;
        this.numeroOrigem = numeroOrigem;
        this.numeroDestino = numeroDestino;
        this.criadoEm = criadoEm;
        this.statusRota = statusRota;
        this.canalId = canalId;
    }

    // construtor a partir da entidade
    public RotaDTO(Rota rota) {
        if (rota != null) {
            this.id = rota.getId();
            this.numeroOrigem = rota.getNumeroOrigem();
            this.numeroDestino = rota.getNumeroDestino();
            this.criadoEm = rota.getCriadoEm();
            this.statusRota = rota.getStatusRota();
            this.canalId = rota.getCanal() != null ? rota.getCanal().getId() : null;
        }
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroOrigem() { return numeroOrigem; }
    public void setNumeroOrigem(String numeroOrigem) { this.numeroOrigem = numeroOrigem; }

    public String getNumeroDestino() { return numeroDestino; }
    public void setNumeroDestino(String numeroDestino) { this.numeroDestino = numeroDestino; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public StatusRota getStatusRota() { return statusRota; }
    public void setStatusRota(StatusRota statusRota) { this.statusRota = statusRota; }

    public Long getCanalId() { return canalId; }
    public void setCanalId(Long canalId) { this.canalId = canalId; }
}