package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.CadastroEditoraDto;
import com.catalogo.api.catalogoLivros.dto.CadastroLivroDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.service.EditoraService;
import com.catalogo.api.catalogoLivros.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/editora")
public class EditoraController {
    @Autowired
    private EditoraService service;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroEditoraDto dto) {
        try{
            service.cadastrar(dto);
            return ResponseEntity.ok("Editora cadastrada com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
