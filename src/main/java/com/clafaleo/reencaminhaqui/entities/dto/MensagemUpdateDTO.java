package com.clafaleo.reencaminhaqui.entities.dto;

import com.clafaleo.reencaminhaqui.entities.enums.TipoConteudo;

import java.time.LocalDateTime;

public class MensagemUpdateDTO {
    private String remetenteOriginal;
    private String conteudoTexto;
    private String payloadBruto;
    private LocalDateTime recebidoEm;
    private TipoConteudo tipoConteudo;
    private Long rotaId;

    public MensagemUpdateDTO() {}

    public String getRemetenteOriginal() { return remetenteOriginal; }
    public void setRemetenteOriginal(String remetenteOriginal) { this.remetenteOriginal = remetenteOriginal; }

    public String getConteudoTexto() { return conteudoTexto; }
    public void setConteudoTexto(String conteudoTexto) { this.conteudoTexto = conteudoTexto; }

    public String getPayloadBruto() { return payloadBruto; }
    public void setPayloadBruto(String payloadBruto) { this.payloadBruto = payloadBruto; }

    public LocalDateTime getRecebidoEm() { return recebidoEm; }
    public void setRecebidoEm(LocalDateTime recebidoEm) { this.recebidoEm = recebidoEm; }

    public TipoConteudo getTipoConteudo() { return tipoConteudo; }
    public void setTipoConteudo(TipoConteudo tipoConteudo) { this.tipoConteudo = tipoConteudo; }

    public Long getRotaId() { return rotaId; }
    public void setRotaId(Long rotaId) { this.rotaId = rotaId; }
}