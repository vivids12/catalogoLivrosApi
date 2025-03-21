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

    private Boolean status;

    public Editora() {}

    public Editora(CadastroEditoraDto dto) {
        this.nome = dto.nome().toLowerCase();
        this.email = dto.email().toLowerCase();
        this.cnpj = dto.cnpj();
    }

    public long getId() {
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

    public String getCnpj() {
        return cnpj;
    }

    public void atualizarInformacoes(Editora editora) {
        this.nome = editora.nome;
        this.email = editora.email;
    }

    public void excluir() {
        this.status = false;
    }
}
