package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.*;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.service.EditoraService;
import com.catalogo.api.catalogoLivros.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/editora")
public class EditoraController {
    @Autowired
    private EditoraService service;

    @Autowired
    private EditoraRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroEditoraDto dto) {
        try {
            service.cadastrar(dto);
            return ResponseEntity.ok("Editora cadastrada com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarEditoras() {
        List<EditoraDto> editoras = service.listarEditoras();

        if (editoras.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(editoras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id){
        List<EditoraDto> editoras = service.listarPorId(id);

        if(editoras.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(editoras);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<?> listarPorNome(@PathVariable String nome){
        List<EditoraDto> editoras = service.listarPorNome(nome.toLowerCase());

        if(editoras.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(editoras);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarEditoraDto dto) {
        var editora = repository.getReferenceById(dto.id());
        editora.atualizarInformacoes(editora);

        return ResponseEntity.ok("Livro atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> atualizar(@PathVariable Long id) {
        var editora = repository.getById(id);
        editora.excluir();
        return ResponseEntity.noContent().build();
    }

}
