package br.com.luisvanique.provaTecnicaQuipux.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity(name = "ListaDeReproducao")
@Table(name = "tb_listaDeReproducao")
public class ListaDeReproducao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "lista_id")
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
