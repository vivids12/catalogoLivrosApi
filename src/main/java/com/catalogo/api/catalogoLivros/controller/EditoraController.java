package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.editora.AtualizarEditoraDto;
import com.catalogo.api.catalogoLivros.dto.editora.CadastroEditoraDto;
import com.catalogo.api.catalogoLivros.dto.editora.EditoraDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.service.EditoraService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/editora")
@RestController
@SecurityRequirement(name = "bearer-key")
public class EditoraController {
    @Autowired
    private EditoraService service;

    @Autowired
    private EditoraRepository repository;

    @Operation(summary = "Cadastrar uma editora", description = "Cadastra uma editora e retorna as informações cadastradas")
    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastroEditoraDto dto) {
        try {
            Editora editora = service.cadastrar(dto);
            return ResponseEntity.ok(editora);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listar editoras", description = "Lista todas as editoras cadastradas")
    @GetMapping
    public ResponseEntity<?> listarEditoras() {
        List<EditoraDto> editoras = service.listarEditoras();

        if (editoras.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(editoras);
    }

    @Operation(summary = "Editora por ID", description = "Retorna a editora com o ID informado")
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id){
        try {
            EditoraDto editora = service.listarPorId(id);
            return ResponseEntity.ok(editora);
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listar editora por nome", description = "Retorna uma lista de editoras com o nome informado")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<?> listarPorNome(@PathVariable String nome){
        List<EditoraDto> editoras = service.listarPorNome(nome);

        if(editoras.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(editoras);
    }

    @Operation(summary = "Atualizar uma editora", description = "Atualiza uma editora com os dados informados")
    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarEditoraDto dto) {
        try {
            var editora = repository.getReferenceById(dto.id());
            editora.atualizarInformacoes(dto);

            return ResponseEntity.ok("Editora atualizada com sucesso!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Não existe uma editora com esse id!");
        }
    }

    @Operation(summary = "Excluir uma editora", description = "Exclui uma editora com o ID informado")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        try {
            var editora = repository.getById(id);
            editora.excluir();
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Não existe uma editora com esse id!");
        }

    }

}
