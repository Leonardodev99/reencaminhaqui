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

    private String nome;

    private String provedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_canal")
    private StatusCanal statusCanal;

    @OneToMany(mappedBy = "canal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rota> rotas = new ArrayList<>();

    public Canal() {
    }

    public Canal(Long id, String nome, StatusCanal statusCanal, String provedor) {
        this.id = id;
        this.nome = nome;
        this.statusCanal = statusCanal;
        this.provedor = provedor;
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
        this.nome = nome;
    }

    public String getProvedor() {
        return provedor;
    }

    public void setProvedor(String provedor) {
        this.provedor = provedor;
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
        Canal other = (Canal) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}