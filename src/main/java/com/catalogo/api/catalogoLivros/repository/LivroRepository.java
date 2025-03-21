package com.catalogo.api.catalogoLivros.repository;

import com.catalogo.api.catalogoLivros.dto.LivroDto;
import com.catalogo.api.catalogoLivros.model.Genero;
import com.catalogo.api.catalogoLivros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Boolean existsByTituloAndAutorId(String titulo, Long idAutor);

    List<Livro> findByGenero(Genero genero);

    List<Livro> findByAutorId(Long id);

    List<Livro> findByEditoraId(Long id);
}
