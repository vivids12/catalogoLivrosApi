package com.catalogo.api.catalogoLivros.validacoes.livro;

import com.catalogo.api.catalogoLivros.dto.livro.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ValidacaoAutorSemCadastroTest {
    @InjectMocks
    private ValidacaoAutorSemCadastro validacao;

    @Mock
    private AutorRepository repository;

    @Mock
    private CadastroLivroDto dto;

    @Test
    @DisplayName("Deveria permitir cadastro com autor ja cadastrado")
    void DeveriaPermitirCadastro() {
        BDDMockito.given(repository.existsById(dto.idAutor())).willReturn(true);

        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Não deveria permitir cadastro com autor sem cadastro")
    void NaoDeveriaPermitirCadastro() {
        BDDMockito.given(repository.existsById(dto.idAutor())).willReturn(false);

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }
}