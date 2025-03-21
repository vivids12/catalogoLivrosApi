package com.catalogo.api.catalogoLivros.validacoes.autor;

import com.catalogo.api.catalogoLivros.dto.autor.CadastroAutorDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidacaoCpfCadastradoTest {

    @InjectMocks
    private ValidacaoCpfCadastrado validacao;

    @Mock
    private AutorRepository repository;

    @Mock
    private CadastroAutorDto dto;

    @Test
    @DisplayName("Deveria permitir o cadastro sem outro CPF cadastrado")
    void deveriaPermitirCadastro(){
        // ARRANGE
        BDDMockito.given(repository.existsByCpf(dto.cpf())).willReturn(false);
        // ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Não deveria permitir cadastro com CPF ja cadastrado")
    void naoDeveriaPermitirCadastro(){
        BDDMockito.given(repository.existsByCpf(dto.cpf())).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

}