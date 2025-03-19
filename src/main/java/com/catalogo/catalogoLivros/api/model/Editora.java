package com.catalogo.catalogoLivros.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "editoras")
public class Editora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;

    private String email;

    private String cnpj;

    public Editora() {}

    public Editora(String nome, String email, String cnpj) {
        this.nome = nome;
        this.email = email;
        this.cnpj = cnpj;
    }
}
