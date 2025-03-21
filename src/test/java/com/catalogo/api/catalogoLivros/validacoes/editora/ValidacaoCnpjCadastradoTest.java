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
class ValidacaoCnpjCadastradoTest {
    @InjectMocks
    private ValidacaoCnpjCadastrado validacao;

    @Mock
    private CadastroEditoraDto dto;

    @Mock
    private EditoraRepository repository;

    @Test
    @DisplayName("Deveria permitir o cadastro sem cnpj cadastrado")
    void DeveriaPermitirCadastro() {
        BDDMockito.given(repository.existsByCnpj(dto.cnpj())).willReturn(false);

        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Não deveria permitir o cadastro com cnpj ja cadastrado")
    void NaoDeveriaPermitirCadastro() {
        BDDMockito.given(repository.existsByCnpj(dto.cnpj())).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

}