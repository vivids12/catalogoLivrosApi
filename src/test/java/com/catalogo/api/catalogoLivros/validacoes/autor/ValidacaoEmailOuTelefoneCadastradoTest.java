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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoEmailOuTelefoneCadastradoTest {

    @InjectMocks
    private ValidacaoEmailOuTelefoneCadastrado validacao;

    @Mock
    private AutorRepository repository;

    @Mock
    private CadastroAutorDto dto;

    @Test
    @DisplayName("Deveria permitir o cadastro de email e telefone sem cadastro")
    void DeveriaPermitirCadastro(){
        BDDMockito.given(repository.existsByEmailOrTelefone(dto.email(), dto.telefone())).willReturn(false);

        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Não deveria permitir o cadastro de email ou telefone ja cadastrado")
    void NaoDeveriaPermitirCadastro(){
        BDDMockito.given(repository.existsByEmailOrTelefone(dto.email(), dto.telefone())).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

}