package com.catalogo.api.catalogoLivros.service;

import com.catalogo.api.catalogoLivros.dto.editora.CadastroEditoraDto;
import com.catalogo.api.catalogoLivros.dto.editora.EditoraDto;
import com.catalogo.api.catalogoLivros.model.Editora;
import com.catalogo.api.catalogoLivros.repository.EditoraRepository;
import com.catalogo.api.catalogoLivros.validacoes.editora.ValidacoesCadastroEditora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditoraService {
    @Autowired
    private EditoraRepository repository;

    @Autowired
    private List<ValidacoesCadastroEditora> validacoes;

    public void cadastrar(CadastroEditoraDto dto){
        validacoes.forEach(validacao -> validacao.validar(dto));

        repository.save(new Editora(dto));
    }

    public List<EditoraDto> listarEditoras() {
        List<EditoraDto> editoras = repository.findByStatusTrue()
                .stream()
                .map(EditoraDto::new)
                .toList();
        return editoras;
    }

    public List<EditoraDto> listarPorId(Long id) {
        List<EditoraDto> editoras = repository.findByIdAndStatusTrue(id)
                .stream()
                .map(EditoraDto::new)
                .toList();
        return editoras;
    }

    public List<EditoraDto> listarPorNome(String nome) {
        List<EditoraDto> editoras = repository.findByNomeAndStatusTrue(nome)
                .stream()
                .map(EditoraDto::new)
                .toList();

        return editoras;
    }
}
