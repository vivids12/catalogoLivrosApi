package com.catalogo.api.catalogoLivros.dto.autor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CadastroAutorDto(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
//        (XX)XXXX-XXXX
//        (XX)XXXXX-XXXX
//        XX-XXXX-XXXX
//        XX-XXXXX-XXXX
//        (XX)XXXXXXXX
//        (XX)XXXXXXX
        String telefone,
        @NotBlank
        String cpf
) {
}
