package com.catalogo.api.catalogoLivros.model;

import com.catalogo.api.catalogoLivros.dto.CadastroAutorDto;
import jakarta.persistence.*;

@Entity
@Table
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String cpf;

    public Autor() {}

    public Autor(CadastroAutorDto dto) {
        this.nome = dto.nome().toLowerCase();
        this.email = dto.email().toLowerCase();
        this.telefone = dto.telefone();
        this.cpf = dto.cpf();
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

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }
}
