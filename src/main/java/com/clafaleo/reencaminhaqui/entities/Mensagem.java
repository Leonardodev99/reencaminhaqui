package com.clafaleo.reencaminhaqui.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.clafaleo.reencaminhaqui.entities.enums.TipoConteudo;

@Entity
@Table(name = "mensagem")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_externo", unique = true)
    private String idExterno;   // id da mens

    @Column(name = "remetente_original")
    private String remetenteOriginal;

    @Lob
    @Column(name = "conteudo_texto")
    private String conteudoTexto;

    @Lob
    @Column(name = "payload_bruto")
    private String payloadBruto; // texto JSON bruto ou string qualquer

    @Column(name = "recebido_em")
    private LocalDateTime recebidoEm;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conteudo")
    private TipoConteudo tipoConteudo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rota_id")
    private Rota rota;

    @OneToMany(mappedBy = "mensagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Anexo> anexos = new ArrayList<>();

    @OneToMany(mappedBy = "mensagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reencaminhamento> reencaminhamentos = new ArrayList<>();

    public Mensagem() {
    }

    public Mensagem(Long id, String remetenteOriginal, String conteudoTexto, String payloadBruto,
                    LocalDateTime recebidoEm, TipoConteudo tipoConteudo, Rota rota) {
        this.id = id;
        this.remetenteOriginal = remetenteOriginal;
        this.conteudoTexto = conteudoTexto;
        this.payloadBruto = payloadBruto;
        this.recebidoEm = recebidoEm;
        this.tipoConteudo = tipoConteudo;
        this.rota = rota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdExterno() { return idExterno; }
    public void setIdExterno(String idExterno) { this.idExterno = idExterno; }

    public String getRemetenteOriginal() {
        return remetenteOriginal;
    }

    public void setRemetenteOriginal(String remetenteOriginal) {
        this.remetenteOriginal = remetenteOriginal;
    }

    public String getConteudoTexto() {
        return conteudoTexto;
    }

    public void setConteudoTexto(String conteudoTexto) {
        this.conteudoTexto = conteudoTexto;
    }

    public String getPayloadBruto() {
        return payloadBruto;
    }

    public void setPayloadBruto(String payloadBruto) {
        this.payloadBruto = payloadBruto;
    }

    public LocalDateTime getRecebidoEm() {
        return recebidoEm;
    }

    public void setRecebidoEm(LocalDateTime recebidoEm) {
        this.recebidoEm = recebidoEm;
    }

    public TipoConteudo getTipoConteudo() {
        return tipoConteudo;
    }

    public void setTipoConteudo(TipoConteudo tipoConteudo) {
        this.tipoConteudo = tipoConteudo;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    public List<Anexo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Anexo> anexos) {
        this.anexos = anexos != null ? anexos : new ArrayList<>();
    }

    public List<Reencaminhamento> getReencaminhamentos() {
        return reencaminhamentos;
    }

    public void setReencaminhamentos(List<Reencaminhamento> reencaminhamentos) {
        this.reencaminhamentos = reencaminhamentos != null ? reencaminhamentos : new ArrayList<>();
    }

    public void addAnexo(Anexo anexo) {
        if (anexo == null) return;
        anexos.add(anexo);
        anexo.setMensagem(this);
    }

    public void removeAnexo(Anexo anexo) {
        if (anexo == null) return;
        anexos.remove(anexo);
        anexo.setMensagem(null);
    }

    public void addReencaminhamento(Reencaminhamento r) {
        if (r == null) return;
        reencaminhamentos.add(r);
        r.setMensagem(this);
    }

    public void removeReencaminhamento(Reencaminhamento r) {
        if (r == null) return;
        reencaminhamentos.remove(r);
        r.setMensagem(null);
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
        Mensagem other = (Mensagem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}