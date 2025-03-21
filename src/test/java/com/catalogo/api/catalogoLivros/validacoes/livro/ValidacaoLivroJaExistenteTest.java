package com.catalogo.api.catalogoLivros.validacoes.livro;

import com.catalogo.api.catalogoLivros.dto.livro.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.repository.LivroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ValidacaoLivroJaExistenteTest {
    @InjectMocks
    private ValidacaoLivroJaExistente validacao;

    @Mock
    private LivroRepository repository;

    @Mock
    private CadastroLivroDto dto;

    @Test
    @DisplayName("Deveria permitir cadastro com livro sem cadastro")
    void DeveriaPermitirCadastro() {
        BDDMockito.given(repository.existsByTituloAndAutorId(dto.titulo(), dto.idAutor()))
                .willReturn(false);

        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Não deveria permitir cadastro com livro ja cadastrado")
    void NaoDeveriaPermitirCadastro() {
        BDDMockito.given(repository.existsByTituloAndAutorId(dto.titulo(), dto.idAutor()))
                .willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }
}