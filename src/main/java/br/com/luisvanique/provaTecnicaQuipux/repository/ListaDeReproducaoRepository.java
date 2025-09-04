package br.com.luisvanique.provaTecnicaQuipux.repository;

import br.com.luisvanique.provaTecnicaQuipux.domain.ListaDeReproducao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaDeReproducaoRepository extends JpaRepository<ListaDeReproducao, Long> {

    ListaDeReproducao findByNome(String nome);
}
