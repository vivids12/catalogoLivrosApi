package com.catalogo.api.catalogoLivros.repository;

import com.catalogo.api.catalogoLivros.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EditoraRepository extends JpaRepository<Editora, Long> {
    Boolean existsByCnpj(String cnpj);

    Boolean existsByEmail(String email);

    @Query("SELECT e.id FROM Editora e WHERE e.nome = :nome")
    List<Long> getIdsByNome(@Param("nome") String editora);
}
