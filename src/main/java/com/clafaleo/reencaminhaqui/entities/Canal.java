package com.clafaleo.reencaminhaqui.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.clafaleo.reencaminhaqui.entities.enums.StatusCanal;

@Entity
@Table(name = "canal")
public class Canal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String provedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_canal", nullable = false)
    private StatusCanal statusCanal = StatusCanal.ATIVO;

    // sem cascade: o histórico não deve ser apagado junto com o canal
    @OneToMany(mappedBy = "canal")
    private List<Rota> rotas = new ArrayList<>();

    public Canal() {
    }

    public Canal(Long id, String nome, String provedor, StatusCanal statusCanal) {
    this.id = id;
    this.nome = nome;
    this.provedor = provedor;
    this.statusCanal = statusCanal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
    this.nome = nome != null ? nome.trim().toUpperCase() : null;
    }

    public String getProvedor() {
        return provedor;
    }

    public void setProvedor(String provedor) {
    this.provedor = provedor != null ? provedor.trim().toUpperCase() : null;
    }

    // getter/setter do enum (correto)
    public StatusCanal getStatusCanal() {
        return statusCanal;
    }

    public void setStatusCanal(StatusCanal statusCanal) {
        this.statusCanal = statusCanal;
    }

    public List<Rota> getRotas() {
        return rotas;
    }

    public void setRotas(List<Rota> rotas) {
        this.rotas = rotas != null ? rotas : new ArrayList<>();
    }

    // utilitários para manter consistência do relacionamento bidirecional
    public void addRota(Rota rota) {
        if (rota == null) return;
        rotas.add(rota);
        rota.setCanal(this);
    }

    public void removeRota(Rota rota) {
        if (rota == null) return;
        rotas.remove(rota);
        rota.setCanal(null);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Canal other)) return false;
        return id != null && id.equals(other.id);
    }
}