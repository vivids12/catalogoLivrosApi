package com.catalogo.api.catalogoLivros.repository;

import com.catalogo.api.catalogoLivros.model.Autor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Boolean existsByCpf(String cpf);

    Boolean existsByEmailOrTelefone(String email, String telefone);

    Long getIdByNome(String autor);

    @Query("SELECT a.id FROM Autor a WHERE a.nome = :nome")
    List<Long> getIdsByNome(@Param("nome") String nome);
}
