package com.clafaleo.reencaminhaqui.entities.dto;

import com.clafaleo.reencaminhaqui.entities.Anexo;

public class AnexoDTO {
    private Long id;
    private String urlOrigem;
    private String urlLocalS3;
    private String mimeType;
    private Long mensagemId;

    public AnexoDTO() {}

    public AnexoDTO(Long id, String urlOrigem, String urlLocalS3, String mimeType, Long mensagemId) {
        this.id = id;
        this.urlOrigem = urlOrigem;
        this.urlLocalS3 = urlLocalS3;
        this.mimeType = mimeType;
        this.mensagemId = mensagemId;
    }

    public AnexoDTO(Anexo a) {
        if (a != null) {
            this.id = a.getId();
            this.urlOrigem = a.getUrlOrigem();
            this.urlLocalS3 = a.getUrlLocalS3();
            this.mimeType = a.getMimeType();
            this.mensagemId = a.getMensagem() != null ? a.getMensagem().getId() : null;
        }
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrlOrigem() { return urlOrigem; }
    public void setUrlOrigem(String urlOrigem) { this.urlOrigem = urlOrigem; }

    public String getUrlLocalS3() { return urlLocalS3; }
    public void setUrlLocalS3(String urlLocalS3) { this.urlLocalS3 = urlLocalS3; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public Long getMensagemId() { return mensagemId; }
    public void setMensagemId(Long mensagemId) { this.mensagemId = mensagemId; }
}