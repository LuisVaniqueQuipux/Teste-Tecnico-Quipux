package br.com.luisvanique.provaTecnicaQuipux.controller;

import br.com.luisvanique.provaTecnicaQuipux.domain.ListaDeReproducao;
import br.com.luisvanique.provaTecnicaQuipux.dtos.ListaReproducaoDTO;
import br.com.luisvanique.provaTecnicaQuipux.dtos.MusicaDTO;
import br.com.luisvanique.provaTecnicaQuipux.service.ListaDeReproducaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lists")
public class ListasDeReproducaoController {

    @Autowired
    private ListaDeReproducaoService service;

    @PostMapping
    public ResponseEntity<ListaReproducaoDTO> create(@RequestBody @Valid ListaReproducaoDTO dto){
        ListaDeReproducao listaDeReproducao = service.create(dto);

        ListaReproducaoDTO toDto = new ListaReproducaoDTO(listaDeReproducao.getNome(), listaDeReproducao.getDescricao(),
                listaDeReproducao.getMusicas()
                        .stream().map(musica ->
                                new MusicaDTO(musica.getTitulo(),
                                        musica.getArtista(),
                                        musica.getAlbum(), musica.getAno(), musica.getGenero()))
                        .collect(Collectors.toList()));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nome}")
                .buildAndExpand(URLEncoder.encode(toDto.nome(), StandardCharsets.UTF_8)).toUri();
        return ResponseEntity.created(uri).body(toDto);
    }

    @GetMapping
    public ResponseEntity<Page<ListaReproducaoDTO>> findAll(@PageableDefault Pageable pageable){
        Page<ListaDeReproducao> listaDeReproducoes = service.findAll(pageable);
        Page<ListaReproducaoDTO> dto = listaDeReproducoes.map(ListaReproducaoDTO::new);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{listNome}")
    public ResponseEntity<ListaReproducaoDTO> findByName(@PathVariable(name="listNome") String listNome){
        ListaDeReproducao listaDeReproducao = service.findByName(listNome);
        ListaReproducaoDTO dto = new ListaReproducaoDTO(listaDeReproducao.getNome(),
                listaDeReproducao.getDescricao(), listaDeReproducao.getMusicas().stream().map(musica ->
                        new MusicaDTO(musica.getTitulo(),
                                musica.getArtista(),
                                musica.getAlbum(), musica.getAno(), musica.getGenero()))
                .collect(Collectors.toList()));

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{listNome}")
    public ResponseEntity<Void> delete(@PathVariable(name = "listNome") String listNome){
        service.delete(listNome);
        return ResponseEntity.noContent().build();
    }

}
