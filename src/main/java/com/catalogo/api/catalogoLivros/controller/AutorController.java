package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.autor.AtualizarAutorDto;
import com.catalogo.api.catalogoLivros.dto.autor.AutorDto;
import com.catalogo.api.catalogoLivros.dto.autor.CadastroAutorDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import com.catalogo.api.catalogoLivros.service.AutorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/autor")
@RestController
@SecurityRequirement(name = "bearer-key")
public class AutorController {

    @Autowired
    private AutorService service;

    @Autowired
    private AutorRepository repository;

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody CadastroAutorDto dto) {
        try{
            Autor autorSalvo =  service.cadastrar(dto);
            return ResponseEntity.ok(autorSalvo);
        } catch (ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarAutores() {
        List<AutorDto> autores = service.listarAutores();

        if (autores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(autores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id){
        try {
            AutorDto autor = service.listarPorId(id);
            return ResponseEntity.ok(autor);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<?> listarPorNome(@PathVariable String nome){
        List<AutorDto> autores = service.listarPorNome(nome);

        if(autores.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(autores);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarAutorDto dto) {
        try {
            var autor = repository.getReferenceById(dto.id());
            autor.atualizarInformacoes(dto);

            return ResponseEntity.ok("Autor atualizado com sucesso!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Não existe um autor com esse id!");
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        try {
            var autor = repository.getById(id);
            autor.excluir();
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Não existe uma autor com esse id!");
        }

    }
}
