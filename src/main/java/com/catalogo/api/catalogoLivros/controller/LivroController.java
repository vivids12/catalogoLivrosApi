package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.dto.LivroDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Genero;
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
}
