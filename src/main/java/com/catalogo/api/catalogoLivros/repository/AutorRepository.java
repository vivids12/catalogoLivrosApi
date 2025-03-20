package com.catalogo.api.catalogoLivros.repository;

import com.catalogo.api.catalogoLivros.model.Autor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Boolean existsByCpf(String cpf);

    Boolean exitsByEmailOrTelefone(String email, String telefone);
}
