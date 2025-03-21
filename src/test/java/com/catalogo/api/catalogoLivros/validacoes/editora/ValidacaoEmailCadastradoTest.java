package com.catalogo.api.catalogoLivros.validacoes.editora;

import com.catalogo.api.catalogoLivros.dto.editora.CadastroEditoraDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidacaoEmailCadastradoTest {
    @InjectMocks
    private ValidacaoEmailCadastrado validacao;

    @Mock
    private CadastroEditoraDto dto;

    @Mock
    private EditoraRepository repository;

    @Test
    @DisplayName("Deveria permitir o cadastro sem email cadastrado")
    void DeveriaPermitirCadastro() {
        BDDMockito.given(repository.existsByEmail(dto.email())).willReturn(false);

        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Não deveria permitir o cadastro com email ja cadastrado")
    void NaoDeveriaPermitirCadastro() {
        BDDMockito.given(repository.existsByEmail(dto.email())).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

}