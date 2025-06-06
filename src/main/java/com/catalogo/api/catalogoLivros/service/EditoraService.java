package com.catalogo.api.catalogoLivros.service;

import com.catalogo.api.catalogoLivros.dto.editora.CadastroEditoraDto;
import com.catalogo.api.catalogoLivros.dto.editora.EditoraDto;
import com.catalogo.api.catalogoLivros.exception.ValidacaoException;
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

    public Editora cadastrar(CadastroEditoraDto dto){
        validacoes.forEach(validacao -> validacao.validar(dto));

        return repository.save(new Editora(dto));
    }

    public List<EditoraDto> listarEditoras() {
        List<EditoraDto> editoras = repository.findByStatusTrue()
                .stream()
                .map(EditoraDto::new)
                .toList();
        return editoras;
    }

    public EditoraDto listarPorId(Long id) {
        try {
            EditoraDto editora = repository.getReferenceByIdAndStatusTrue(id);
            return editora;
        } catch (ValidacaoException e) {
            throw new ValidacaoException("Id inválido!");
        }
    }

    public List<EditoraDto> listarPorNome(String nome) {
        List<EditoraDto> editoras = repository.findByNomeAndStatusTrue(nome)
                .stream()
                .map(EditoraDto::new)
                .toList();

        return editoras;
    }
}
