package com.clafaleo.reencaminhaqui.entities.dto;

import com.clafaleo.reencaminhaqui.entities.enums.StatusEnvio;

import java.time.LocalDateTime;

public class ReencaminhamentoUpdateDTO {
    private Integer tentativas;
    private String idTransacaoProvedor;
    private String mensagemErro;
    private LocalDateTime processadoEm;
    private StatusEnvio statusEnvio;
    private Long mensagemId;

    public ReencaminhamentoUpdateDTO() {}

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