package com.catalogo.api.catalogoLivros.repository;

import com.catalogo.api.catalogoLivros.dto.LivroDto;
import com.catalogo.api.catalogoLivros.model.Genero;
import com.catalogo.api.catalogoLivros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Boolean existsByTituloAndAutorId(String titulo, Long idAutor);

    List<Livro> findByGeneroAndStatusTrue(Genero genero);

    List<Livro> findByAutorIdAndStatusTrue(Long id);

    List<Livro> findByEditoraIdAndStatusTrue(Long id);

    List<Livro> findByTituloAndStatusTrue(String titulo);

    List<Livro> findByStatusTrue();
}
