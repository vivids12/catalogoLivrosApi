package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.autor.AtualizarAutorDto;
import com.catalogo.api.catalogoLivros.dto.autor.AutorDto;
import com.catalogo.api.catalogoLivros.dto.autor.CadastroAutorDto;
import com.catalogo.api.catalogoLivros.dto.livro.LivroMapper;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import com.catalogo.api.catalogoLivros.service.AutorService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class AutorControllerTest {

    @InjectMocks
    private AutorController controller;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AutorService service;

    @MockBean
    private LivroMapper livroMapper;

    @Mock
    private AutorRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Não deveria permitir cadastro com erros")
    void deveriaDeveolverCodigo400(){
        CadastroAutorDto dto = Mockito.mock(CadastroAutorDto.class);
        when(service.cadastrar(dto)).thenThrow(ValidacaoException.class);
        ResponseEntity<?> response = controller.cadastrar(dto);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria permitir cadastro completo sem erros")
    void deveriaDeveolverCodigo200(){
        CadastroAutorDto dto = Mockito.mock(CadastroAutorDto.class);

        ResponseEntity<?> response = controller.cadastrar(dto);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria listar por id, quando id existente correto")
    void deveriaDevolverCodigo200IdCorreto(){
        AutorDto dto = Mockito.mock(AutorDto.class);
        Long id = dto.id();
        when(service.listarPorId(id))
                .thenReturn(dto);

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
    @DisplayName("Deveria listar todos os autores")
    void deveriaDevolverCodigo200ListarAutores(){
        AutorDto dto = Mockito.mock(AutorDto.class);
        List<AutorDto> lista = List.of(dto);
        when(service.listarAutores())
                .thenReturn(lista);

        ResponseEntity<?> response = controller.listarAutores();

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria retornar vazio quando não tem autores cadastrados")
    void deveriaDevolverCodigo404ListarAutores(){
        List<AutorDto> lista = new ArrayList<>();
        when(service.listarAutores())
                .thenReturn(lista);

        ResponseEntity<?> response = controller.listarAutores();

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria listar por nome, quando nome correto")
    void deveriaDevolverCodigo200NomeCorreto(){
        AutorDto dto = Mockito.mock(AutorDto.class);
        List<AutorDto> lista = List.of(dto);

        String nome = dto.nome();
        when(service.listarPorNome(nome))
                .thenReturn(lista);

        ResponseEntity<?> response = controller.listarPorNome(nome);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Não deveria listar por nome, quando nome incorreto")
    void deveriaDevolverCodigo400NomeIncorreto(){
        String nome = "nome";
        List<AutorDto> lista = new ArrayList<>();
        when(service.listarPorNome(nome))
                .thenReturn(lista);

        ResponseEntity<?> response = controller.listarPorNome(nome);

        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria atualizar quando informações corretas")
    void deveriaDevolverCodigo200AtualizarInformacoes(){
        Autor autor = Mockito.mock(Autor.class);
        AtualizarAutorDto dto = Mockito.mock(AtualizarAutorDto.class);
        autor.setId(dto.id());

        when(repository.getReferenceById(dto.id())).thenReturn(autor);

        ResponseEntity<?> response = controller.atualizar(dto);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Não deveria atualizar as informações quando id incorreto")
    void deveriaDevolverCodigo400NaoAtualizarInformacoes(){
        AtualizarAutorDto dto = Mockito.mock(AtualizarAutorDto.class);
        Long id = dto.id();

        String json = """
                {
                    "id": 0
                    "nome": "Silvia Santos",
                    "email": "silvia.santos@gmail.com"
                }
                """;

        when(repository.getReferenceById(id)).thenThrow(new EntityNotFoundException("Autor não encontrado!"));

        ResponseEntity<?> response = controller.atualizar(dto);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria excluir autor")
    void deveriaDevolverCodigo204ExcluirAutor(){
        Long id = 1L;
        Autor autor = Mockito.mock(Autor.class);

        when(repository.getById(autor.getId())).thenReturn(autor);

        ResponseEntity<?> response = controller.excluir(autor.getId());

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deveria dar erro ao excluir autor com id incorreto")
    void deveriaDevolverCodigo400ExcluirAutor(){
        Long id = 0L;

        when(repository.getById(id)).thenThrow(new EntityNotFoundException("Id incorreto!"));

        ResponseEntity<?> response = controller.excluir(id);

        assertEquals(400, response.getStatusCode().value());
    }

}