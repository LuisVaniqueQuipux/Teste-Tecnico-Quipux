package br.com.luisvanique.provaTecnicaQuipux.service;

import br.com.luisvanique.provaTecnicaQuipux.domain.ListaDeReproducao;
import br.com.luisvanique.provaTecnicaQuipux.dtos.ListaReproducaoDTO;
import br.com.luisvanique.provaTecnicaQuipux.exceptions.ListaNaoExisteException;
import br.com.luisvanique.provaTecnicaQuipux.exceptions.NomeNuloException;
import br.com.luisvanique.provaTecnicaQuipux.repository.ListaDeReproducaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListaDeReproducaoService {

    @Autowired
    private ListaDeReproducaoRepository repository;

    public ListaDeReproducao create(@Valid ListaReproducaoDTO dto) {
        if(dto.nome() == null){
            throw new NomeNuloException("Nome não pode estar nulo!");
        }
        ListaDeReproducao listaDeReproducao = new ListaDeReproducao(dto);
        listaDeReproducao = repository.save(listaDeReproducao);
        return listaDeReproducao;
    }

    public Page<ListaDeReproducao> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public ListaDeReproducao findByName(String nome){
        ListaDeReproducao listaDeReproducao = repository.findByNome(nome);
        if(listaDeReproducao == null){
            throw new ListaNaoExisteException("Lista indicada nao existe! (Use %20 para sinalizar espaços!)");
        }
        return listaDeReproducao;
    }

    public void delete(String listNome) {
        ListaDeReproducao listaDeReproducao = repository.findByNome(listNome);
        if(listaDeReproducao == null){
            throw new ListaNaoExisteException("Lista indicada nao existe! (Use %20 para sinalizar espaços!)");
        }

        repository.delete(listaDeReproducao);
    }
}
