package com.catalogo.api.catalogoLivros.repository;

import com.catalogo.api.catalogoLivros.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditoraRepository extends JpaRepository<Editora, Long> {
}
