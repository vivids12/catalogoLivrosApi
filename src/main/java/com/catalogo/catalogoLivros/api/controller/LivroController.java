package com.catalogo.catalogoLivros.api.controller;

import com.catalogo.catalogoLivros.api.model.Livro;
import com.catalogo.catalogoLivros.api.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LivroController {
    @Autowired
    private LivroService service;

    @PostMapping
    public void cadastrarLivro(@RequestBody CadastrarLivroDto dto) {

    }
}
