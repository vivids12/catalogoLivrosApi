package com.catalogo.api.catalogoLivros.repository;

import com.catalogo.api.catalogoLivros.dto.autor.AutorDto;
import com.catalogo.api.catalogoLivros.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Boolean existsByCpf(String cpf);

    Boolean existsByEmailOrTelefone(String email, String telefone);

    Long getIdByNome(String autor);

    @Query("SELECT a.id FROM Autor a WHERE a.nome = :nome")
    List<Long> getIdsByNome(@Param("nome") String nome);

    List<Autor> findByStatusTrue();

    // List<Autor> findByIdAndStatusTrue(Long id);

    List<Autor> findByNomeAndStatusTrue(String nome);

    AutorDto getReferenceByIdAndStatusTrue(Long id);
}
