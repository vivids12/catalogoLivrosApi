package com.catalogo.api.catalogoLivros.repository;

import com.catalogo.api.catalogoLivros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Boolean existsByTituloAndAutorId(String titulo, Long idAutor);
}
