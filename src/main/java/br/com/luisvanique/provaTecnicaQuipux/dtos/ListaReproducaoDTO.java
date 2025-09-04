package br.com.luisvanique.provaTecnicaQuipux.dtos;

import br.com.luisvanique.provaTecnicaQuipux.domain.ListaDeReproducao;
import br.com.luisvanique.provaTecnicaQuipux.domain.Musica;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public record ListaReproducaoDTO(
        String nome,

        String descricao,

        List<MusicaDTO> musicas
        ) {

        public ListaReproducaoDTO(ListaDeReproducao listaDeReproducao){
                this(listaDeReproducao.getNome(), listaDeReproducao.getDescricao(),
                        listaDeReproducao.getMusicas().stream().map(musica ->
                                new MusicaDTO
                                        (musica.getTitulo(), musica.getArtista(), musica.getAlbum(), musica.getAno(), musica.getGenero()))
                                .collect(Collectors.toList()));
        }
}
