package com.clafaleo.reencaminhaqui.entities.dto;

import com.clafaleo.reencaminhaqui.entities.Reencaminhamento;
import com.clafaleo.reencaminhaqui.entities.enums.StatusEnvio;

import java.time.LocalDateTime;

public class ReencaminhamentoDTO {
    private Long id;
    private Integer tentativas;
    private String idTransacaoProvedor;
    private String mensagemErro;
    private LocalDateTime processadoEm;
    private StatusEnvio statusEnvio;
    private Long mensagemId;

    public ReencaminhamentoDTO() {}

    public ReencaminhamentoDTO(Long id, Integer tentativas, String idTransacaoProvedor, String mensagemErro,
                               LocalDateTime processadoEm, StatusEnvio statusEnvio, Long mensagemId) {
        this.id = id;
        this.tentativas = tentativas;
        this.idTransacaoProvedor = idTransacaoProvedor;
        this.mensagemErro = mensagemErro;
        this.processadoEm = processadoEm;
        this.statusEnvio = statusEnvio;
        this.mensagemId = mensagemId;
    }

    public ReencaminhamentoDTO(Reencaminhamento r) {
        if (r != null) {
            this.id = r.getId();
            this.tentativas = r.getTentativas();
            this.idTransacaoProvedor = r.getIdTransacaoProvedor();
            this.mensagemErro = r.getMensagemErro();
            this.processadoEm = r.getProcessadoEm();
            this.statusEnvio = r.getStatusEnvio();
            this.mensagemId = r.getMensagem() != null ? r.getMensagem().getId() : null;
        }
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getTentativas() { return tentativas; }
    public void setTentativas(Integer tentativas) { this.tentativas = tentativas; }

    public String getIdTransacaoProvedor() { return idTransacaoProvedor; }
    public void setIdTransacaoProvedor(String idTransacaoProvedor) { this.idTransacaoProvedor = idTransacaoProvedor; }

    public String getMensagemErro() { return mensagemErro; }
    public void setMensagemErro(String mensagemErro) { this.mensagemErro = mensagemErro; }

    public LocalDateTime getProcessadoEm() { return processadoEm; }
    public void setProcessadoEm(LocalDateTime processadoEm) { this.processadoEm = processadoEm; }

    public StatusEnvio getStatusEnvio() { return statusEnvio; }
    public void setStatusEnvio(StatusEnvio statusEnvio) { this.statusEnvio = statusEnvio; }

    public Long getMensagemId() { return mensagemId; }
    public void setMensagemId(Long mensagemId) { this.mensagemId = mensagemId; }
}