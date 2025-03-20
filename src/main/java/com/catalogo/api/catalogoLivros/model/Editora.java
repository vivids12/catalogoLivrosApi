package com.catalogo.api.catalogoLivros.model;

import com.catalogo.api.catalogoLivros.dto.CadastroEditoraDto;
import jakarta.persistence.*;

@Entity
@Table
public class Editora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;

    private String email;

    private String cnpj;

    public Editora() {}

    public Editora(CadastroEditoraDto dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.cnpj = dto.cnpj();
    }
}
