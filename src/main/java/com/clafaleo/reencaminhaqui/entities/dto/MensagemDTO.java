package com.clafaleo.reencaminhaqui.entities.dto;

import com.clafaleo.reencaminhaqui.entities.Mensagem;
import com.clafaleo.reencaminhaqui.entities.enums.TipoConteudo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MensagemDTO {
    private Long id;
    private String remetenteOriginal;
    private String conteudoTexto;
    private String payloadBruto;
    private LocalDateTime recebidoEm;
    private TipoConteudo tipoConteudo;
    private Long rotaId;
    private List<Long> anexoIds;
    private List<Long> reencaminhamentoIds;

    public MensagemDTO() {}

    public MensagemDTO(Long id, String remetenteOriginal, String conteudoTexto, String payloadBruto,
                       LocalDateTime recebidoEm, TipoConteudo tipoConteudo, Long rotaId,
                       List<Long> anexoIds, List<Long> reencaminhamentoIds) {
        this.id = id;
        this.remetenteOriginal = remetenteOriginal;
        this.conteudoTexto = conteudoTexto;
        this.payloadBruto = payloadBruto;
        this.recebidoEm = recebidoEm;
        this.tipoConteudo = tipoConteudo;
        this.rotaId = rotaId;
        this.anexoIds = anexoIds;
        this.reencaminhamentoIds = reencaminhamentoIds;
    }

    // construtor a partir da entidade
    public MensagemDTO(Mensagem m) {
        if (m != null) {
            this.id = m.getId();
            this.remetenteOriginal = m.getRemetenteOriginal();
            this.conteudoTexto = m.getConteudoTexto();
            this.payloadBruto = m.getPayloadBruto();
            this.recebidoEm = m.getRecebidoEm();
            this.tipoConteudo = m.getTipoConteudo();
            this.rotaId = m.getRota() != null ? m.getRota().getId() : null;
            this.anexoIds = m.getAnexos() == null ? List.of() : m.getAnexos().stream().map(a -> a.getId()).collect(Collectors.toList());
            this.reencaminhamentoIds = m.getReencaminhamentos() == null ? List.of() : m.getReencaminhamentos().stream().map(r -> r.getId()).collect(Collectors.toList());
        }
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public List<Long> getAnexoIds() { return anexoIds; }
    public void setAnexoIds(List<Long> anexoIds) { this.anexoIds = anexoIds; }

    public List<Long> getReencaminhamentoIds() { return reencaminhamentoIds; }
    public void setReencaminhamentoIds(List<Long> reencaminhamentoIds) { this.reencaminhamentoIds = reencaminhamentoIds; }
}