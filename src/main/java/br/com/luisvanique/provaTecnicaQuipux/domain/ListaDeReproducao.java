package br.com.luisvanique.provaTecnicaQuipux.domain;

import java.util.List;

public class ListaDeReproducao {

    private String nome;

    private String descricao;

    private List<Musica> musicas;

    public ListaDeReproducao() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }




}
