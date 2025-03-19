package com.catalogo.catalogoLivros.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Autor autor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Editora editora;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    public Livro() {}

    public Livro(String titulo, Autor autor, Editora editora, Genero genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.genero = genero;
    }
}
