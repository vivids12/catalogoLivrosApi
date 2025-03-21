package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.AtualizarLivroDto;
import com.catalogo.api.catalogoLivros.dto.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.dto.LivroDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Genero;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.repository.LivroRepository;
import com.catalogo.api.catalogoLivros.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/livro")
public class LivroController {
    @Autowired
    private LivroService service;

    @Autowired
    private LivroRepository repository;

    @Autowired
    private EditoraRepository editoraRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroLivroDto dto) {
        try{
            service.cadastrar(dto);
            return ResponseEntity.ok("Livro cadastrado com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<?> listarPorGenero(@PathVariable String genero){
       try{
            Genero generoEnum = Genero.valueOf(genero.toUpperCase());
            List<LivroDto> livros = service.listarPorGenero(generoEnum);

            if(livros.isEmpty()){
                return ResponseEntity.notFound().build();
            }

           return ResponseEntity.ok(livros);
       } catch (IllegalArgumentException e) {
           return ResponseEntity.badRequest().body("Gênero inválido");
       }
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<?> listarPorAutor(@PathVariable String autor){
        List<LivroDto> livros = service.listarPorAutor(autor.toLowerCase());

        if(livros == null){
            return ResponseEntity.badRequest().body("Autor inválido");
        }
        if(livros.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(livros);
    }

    @GetMapping("/editora/{editora}")
    public ResponseEntity<?> listarPorEditora(@PathVariable String editora){
        List<LivroDto> livros = service.listarPorEditora(editora.toLowerCase());

        if(livros == null){
            return ResponseEntity.badRequest().body("Editora inválido");
        }
        if(livros.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(livros);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<?> listarPorNome(@PathVariable String titulo){
        List<LivroDto> livros = service.listarPorTitulo(titulo.toLowerCase());

        if(livros.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorNome(@PathVariable Long id){
        List<LivroDto> livros = service.listarPorId(id);

        if(livros.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(livros);
    }

    @GetMapping
    public ResponseEntity<?> listarLivros(){
        List<LivroDto> livros = service.listarLivros();

        if(livros.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(livros);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarLivroDto dto) {
        var livro = repository.getReferenceById(dto.id());
        var editora = editoraRepository.getReferenceById(dto.idEditora());
        livro.atualizarInformacoes(livro, editora);

        return ResponseEntity.ok("Livro atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> atualizar(@PathVariable Long id) {
        var livro = repository.getById(id);
        livro.excluir();
        return ResponseEntity.noContent().build();
    }
}
