package com.catalogo.api.catalogoLivros.model;

import com.catalogo.api.catalogoLivros.dto.CadastroLivroDto;
import jakarta.persistence.*;

@Entity
@Table
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

    public Livro(CadastroLivroDto dto, Autor autor, Editora editora) {
        this.titulo = dto.titulo();
        this.autor = autor;
        this.editora = editora;
        this.genero = dto.genero();
    }
}
