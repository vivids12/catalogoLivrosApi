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
        this.nome = dto.nome();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.cpf = dto.cpf();
    }
}
