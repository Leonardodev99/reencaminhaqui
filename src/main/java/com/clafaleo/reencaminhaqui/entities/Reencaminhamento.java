package com.clafaleo.reencaminhaqui.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.clafaleo.reencaminhaqui.entities.enums.StatusEnvio;

@Entity
@Table(name = "reencaminhamento")
public class Reencaminhamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer tentativas;

    @Column(name = "id_transacao_provedor")
    private String idTransacaoProvedor;

    @Column(name = "mensagem_erro", columnDefinition = "text")
    private String mensagemErro;

    @Column(name = "processado_em")
    private LocalDateTime processadoEm;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_envio")
    private StatusEnvio statusEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mensagem_id")
    private Mensagem mensagem;

    public Reencaminhamento() {
    }

    public Reencaminhamento(Long id, Integer tentativas, String idTransacaoProvedor, String mensagemErro,
                            LocalDateTime processadoEm, StatusEnvio statusEnvio, Mensagem mensagem) {
        this.id = id;
        this.tentativas = tentativas;
        this.idTransacaoProvedor = idTransacaoProvedor;
        this.mensagemErro = mensagemErro;
        this.processadoEm = processadoEm;
        this.statusEnvio = statusEnvio;
        this.mensagem = mensagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTentativas() {
        return tentativas;
    }

    public void setTentativas(Integer tentativas) {
        this.tentativas = tentativas;
    }

    public String getIdTransacaoProvedor() {
        return idTransacaoProvedor;
    }

    public void setIdTransacaoProvedor(String idTransacaoProvedor) {
        this.idTransacaoProvedor = idTransacaoProvedor;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public LocalDateTime getProcessadoEm() {
        return processadoEm;
    }

    public void setProcessadoEm(LocalDateTime processadoEm) {
        this.processadoEm = processadoEm;
    }

    public StatusEnvio getStatusEnvio() {
        return statusEnvio;
    }

    public void setStatusEnvio(StatusEnvio statusEnvio) {
        this.statusEnvio = statusEnvio;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    // utilitário para associar bidirecionalmente
    public void associateToMensagem(Mensagem mensagem) {
        setMensagem(mensagem);
        if (mensagem != null && !mensagem.getReencaminhamentos().contains(this)) {
            mensagem.getReencaminhamentos().add(this);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Reencaminhamento other = (Reencaminhamento) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}