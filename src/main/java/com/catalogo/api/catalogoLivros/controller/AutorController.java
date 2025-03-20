package com.catalogo.api.catalogoLivros.controller;

import com.catalogo.api.catalogoLivros.dto.CadastroAutorDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
import com.catalogo.api.catalogoLivros.model.Autor;
import com.catalogo.api.catalogoLivros.repository.AutorRepository;
import com.catalogo.api.catalogoLivros.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autor")
public class AutorController {
    @Autowired
    private AutorService service;

    @PostMapping
    public ResponseEntity<String> cadastrar(@Valid @RequestBody CadastroAutorDto dto) {
        try{
            service.cadastrar(dto);
            return ResponseEntity.ok("Autor cadastrado com sucesso!");
        } catch (ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
