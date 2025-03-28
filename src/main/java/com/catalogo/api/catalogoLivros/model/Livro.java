package com.catalogo.api.catalogoLivros.model;

import com.catalogo.api.catalogoLivros.dto.livro.AtualizarLivroDto;
import com.catalogo.api.catalogoLivros.dto.livro.CadastroLivroDto;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table
public class Livro {
    @Setter
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

    private Boolean status;

    public Livro() {}

    public Livro(CadastroLivroDto dto, Autor autor, Editora editora) {
        this.titulo = dto.titulo();
        this.autor = autor;
        this.editora = editora;
        this.genero = dto.genero();
        this.status = true;
    }

    public void atualizarInformacoes(AtualizarLivroDto livro, Editora editora) {
        if(livro.titulo() != null) {
            this.titulo = livro.titulo();
        }
        if (editora != null) {
            this.editora = editora;
        }
    }

    public void excluir() {
        this.status = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public Editora getEditora() {
        return editora;
    }

    public Genero getGenero() {
        return genero;
    }

    public Boolean getStatus() {
        return status;
    }
}
