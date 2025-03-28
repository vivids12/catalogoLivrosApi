package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.editora.AtualizarEditoraDto;
import com.catalogo.api.catalogoLivros.dto.editora.CadastroEditoraDto;
import com.catalogo.api.catalogoLivros.dto.editora.EditoraDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.service.EditoraService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class EditoraControllerTest {

    @InjectMocks
    private EditoraController controller;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EditoraService service;

    @Mock
    private EditoraRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Não deveria permitir cadastro com erros")
    void deveriaDevolverCodigo400(){
        CadastroEditoraDto dto = Mockito.mock(CadastroEditoraDto.class);
        when(service.cadastrar(any(CadastroEditoraDto.class))).thenThrow(new ValidacaoException("Erro"));

        ResponseEntity<?> response = controller.cadastrar(dto);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria permitir cadastro completo sem erros")
    void deveriaDevolverCodigo200(){
        CadastroEditoraDto dto = Mockito.mock(CadastroEditoraDto.class);
        Editora editora = Mockito.mock(Editora.class);

        when(service.cadastrar(dto)).thenReturn(editora);

        ResponseEntity<?> response = controller.cadastrar(dto);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria listar por id, quando id existente correto")
    void deveriaDevolverCodigo200IdCorreto(){
        EditoraDto editora = Mockito.mock(EditoraDto.class);
        Long id = editora.id();

        when(service.listarPorId(id)).thenReturn(editora);

        ResponseEntity<?> response = controller.listarPorId(id);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Não deveria listar por id, quando id inexistente")
    void deveriaDevolverCodigo400IdInexistente(){
        Long id = 1L;
        when(service.listarPorId(id))
                .thenThrow(new ValidacaoException("Id inválido!"));

        ResponseEntity<?> response = controller.listarPorId(id);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria listar todos os editoras")
    void deveriaDevolverCodigo200ListarEditoras(){
        List<EditoraDto> lista = new ArrayList<>();
        lista.add(new EditoraDto (1l, "Nome", "11"));
        lista.add(new EditoraDto (2l, "Nome", "11"));
        when(service.listarEditoras())
                .thenReturn(lista);

        ResponseEntity<?> response = controller.listarEditoras();

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria retornar vazio quando não tem editoras cadastradas")
    void deveriaDevolverCodigo404ListarEditoras(){
        List<EditoraDto> lista = new ArrayList<>();

        when(service.listarEditoras())
                .thenReturn(lista);

        ResponseEntity<?> response = controller.listarEditoras();

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria listar por nome, quando nome correto")
    void deveriaDevolverCodigo200NomeCorreto(){
        EditoraDto editora = Mockito.mock(EditoraDto.class);
        List<EditoraDto> lista = List.of(editora);

        String nome = editora.nome();
        when(service.listarPorNome(nome)).thenReturn(lista);

        ResponseEntity<?> response = controller.listarPorNome(nome);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Não deveria listar por nome, quando nome incorreto")
    void deveriaDevolverCodigo400NomeIncorreto(){
        String nome = "nome";
        List<EditoraDto> lista = new ArrayList<>();
        when(service.listarPorNome(nome))
                .thenReturn(lista);

        ResponseEntity<?> response = controller.listarPorNome(nome);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria atualizar quando informações corretas")
    void deveriaDevolverCodigo200AtualizarInformacoes(){
        Editora editora = Mockito.mock(Editora.class);
        AtualizarEditoraDto dto = Mockito.mock(AtualizarEditoraDto.class);
        Long id = dto.id();

        when(repository.getReferenceById(id)).thenReturn(editora);

        ResponseEntity<?> response = controller.atualizar(dto);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Não deveria atualizar as informações quando id incorreto")
    void deveriaDevolverCodigo400NaoAtualizarInformacoes(){
        AtualizarEditoraDto dto = Mockito.mock(AtualizarEditoraDto.class);
        Long id = dto.id();

        when(repository.getReferenceById(id)).thenThrow(new EntityNotFoundException("Editora não encontrada!"));

        ResponseEntity response = controller.atualizar(dto);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria excluir editora")
    void deveriaDevolverCodigo204ExcluirAutor(){
        Long id = 1L;
        Editora editora = Mockito.mock(Editora.class);

        when(repository.getById(editora.getId())).thenReturn(editora);

        ResponseEntity<?> response = controller.excluir(editora.getId());

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria dar erro ao excluir editora com id incorreto")
    void deveriaDevolverCodigo400ExcluirAutor(){
        Long id = 0L;

        when(repository.getById(id)).thenThrow(new EntityNotFoundException("Id incorreto!"));

        ResponseEntity<?> response = controller.excluir(id);

        assertEquals(400, response.getStatusCode().value());
    }

}