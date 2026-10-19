package com.clafaleo.reencaminhaqui.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "anexo")
public class Anexo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url_origem", length = 512)
    private String urlOrigem;

    @Column(name = "url_local_s3", length = 512)
    private String urlLocalS3;

    @Column(name = "mime_type", length = 255)
    private String mimeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mensagem_id")
    private Mensagem mensagem;

    public Anexo() {
    }

    public Anexo(Long id, String urlOrigem, String urlLocalS3, String mimeType, Mensagem mensagem) {
        this.id = id;
        this.urlOrigem = urlOrigem;
        this.urlLocalS3 = urlLocalS3;
        this.mimeType = mimeType;
        this.mensagem = mensagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlOrigem() {
        return urlOrigem;
    }

    public void setUrlOrigem(String urlOrigem) {
        this.urlOrigem = urlOrigem;
    }

    public String getUrlLocalS3() {
        return urlLocalS3;
    }

    public void setUrlLocalS3(String urlLocalS3) {
        this.urlLocalS3 = urlLocalS3;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    // utilitário para manter associação bidirecional
    public void associateToMensagem(Mensagem mensagem) {
        setMensagem(mensagem);
        if (mensagem != null && !mensagem.getAnexos().contains(this)) {
            mensagem.getAnexos().add(this);
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
        Anexo other = (Anexo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}