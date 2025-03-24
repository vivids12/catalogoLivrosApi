package com.catalogo.api.catalogoLivros.model;

import com.catalogo.api.catalogoLivros.dto.autor.AtualizarAutorDto;
import com.catalogo.api.catalogoLivros.dto.autor.CadastroAutorDto;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table
public class Autor {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String cpf;

    private Boolean status;

    public Autor() {}

    public Autor(CadastroAutorDto dto) {
        this.nome = dto.nome().toLowerCase();
        this.email = dto.email().toLowerCase();
        this.telefone = dto.telefone();
        this.cpf = dto.cpf();
    }

    public Autor(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void atualizarInformacoes(AtualizarAutorDto dto) {
        if(dto.nome() != null) {
            this.nome = dto.nome();
        }
        if(dto.email() != null) {
            this.email = dto.email();
        }
        if(dto.telefone() != null) {
            this.telefone = dto.telefone();
        }
    }

    public void excluir() {
        this.status = false;
    }

}
