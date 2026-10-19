package com.clafaleo.reencaminhaqui.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.clafaleo.reencaminhaqui.entities.enums.StatusRota;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rota")
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_origem")
    private String numeroOrigem;

    @Column(name = "numero_destino")
    private String numeroDestino;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_rota")
    private StatusRota statusRota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "canal_id")
    private Canal canal;

    //relacionamento com a mensagem
    @OneToMany(mappedBy = "rota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensagem> mensagens = new ArrayList<>();

    public Rota() {
    }

    public Rota(Long id, String numeroOrigem, String numeroDestino, LocalDateTime criadoEm, StatusRota statusRota, Canal canal) {
        this.id = id;
        this.numeroOrigem = numeroOrigem;
        this.numeroDestino = numeroDestino;
        this.criadoEm = criadoEm;
        this.statusRota = statusRota;
        this.canal = canal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroOrigem() {
        return numeroOrigem;
    }

    public void setNumeroOrigem(String numeroOrigem) {
        this.numeroOrigem = numeroOrigem;
    }

    public String getNumeroDestino() {
        return numeroDestino;
    }

    public void setNumeroDestino(String numeroDestino) {
        this.numeroDestino = numeroDestino;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public StatusRota getStatusRota() {
        return statusRota;
    }

    public void setStatusRota(StatusRota statusRota) {
        this.statusRota = statusRota;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

      public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    public void addMensagem(Mensagem mensagem) {
        mensagens.add(mensagem);
        mensagem.setRota(this);
    }

    public void removeMensagem(Mensagem mensagem) {
        mensagens.remove(mensagem);
        mensagem.setRota(null);
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
        Rota other = (Rota) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}