package br.com.luisvanique.provaTecnicaQuipux.controller;

import br.com.luisvanique.provaTecnicaQuipux.dtos.ListaReproducaoDTO;
import br.com.luisvanique.provaTecnicaQuipux.dtos.MusicaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ListaDeReproducaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Jackson

    private String token;

    private final String URL_BASE = "/lists";

    @BeforeAll
    void setUp() throws Exception {
        Map<String, String> login = Map.of(
                "username", "admin",
                "password", "admin"
        );

        String loginJson = objectMapper.writeValueAsString(login);

        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        token = objectMapper.readTree(response).get("token").asText();
    }

    @Test
    @DisplayName("Deve retornar 201 created para a criacao de uma Lista de Reproducao valida")
    void createListaReproducaoOk() throws Exception {
        MusicaDTO musica1 = new MusicaDTO("Lives", "Daron Malakian", "Dictador", "2018", "Metal");
        MusicaDTO musica2 = new MusicaDTO("Lie Lie Lie", "Serj Tankian", "Elect the Dead", "2007", "Rock");
        List<MusicaDTO> musicas = List.of(musica1, musica2);
        ListaReproducaoDTO dto = new ListaReproducaoDTO("Playlist 1", "Playlista de Rock", musicas);

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post(URL_BASE).header("Authorization", "Bearer " + token).contentType("application/json").content(json)).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar 400 bad request para a criacao de uma Lista de Reproducao invalida")
    void createListaDeReproducaoInvalida() throws Exception {
        MusicaDTO musica1 = new MusicaDTO("Lives", "Daron Malakian", "Dictador", "2018", "Metal");
        MusicaDTO musica2 = new MusicaDTO("Lie Lie Lie", "Serj Tankian", "Elect the Dead", "2007", "Rock");
        List<MusicaDTO> musicas = List.of(musica1, musica2);
        ListaReproducaoDTO dto = new ListaReproducaoDTO(null, "Playlista de Rock", musicas);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post(URL_BASE).header("Authorization", "Bearer " + token).contentType("application/json").content(json)).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 200 ok e buscar todas as listas de reproducao")
    void buscarTodasAsListas() throws Exception {
        mockMvc.perform(get("/lists").header("Authorization", "Bearer " + token).contentType("application/json")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar 404 not found para a busca de uma Lista de Reproducao inexistente")
    void findListaDeReproducaoInexistente() throws Exception {
        MusicaDTO musica1 = new MusicaDTO("Lives", "Daron Malakian", "Dictador", "2018", "Metal");
        MusicaDTO musica2 = new MusicaDTO("Lie Lie Lie", "Serj Tankian", "Elect the Dead", "2007", "Rock");
        List<MusicaDTO> musicas = List.of(musica1, musica2);
        ListaReproducaoDTO dto = new ListaReproducaoDTO("Playlist 1", "Playlista de Rock", musicas);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(get(URL_BASE + "Playlist inexistente").header("Authorization", "Bearer " + token).contentType("application/json")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 200 ok para a busca de uma Lista de Reproducao existente")
    void findListaDeReproducaoExistente() throws Exception {
        MusicaDTO musica1 = new MusicaDTO("Lives", "Daron Malakian", "Dictador", "2018", "Metal");
        MusicaDTO musica2 = new MusicaDTO("Lie Lie Lie", "Serj Tankian", "Elect the Dead", "2007", "Rock");
        List<MusicaDTO> musicas = List.of(musica1, musica2);
        ListaReproducaoDTO dto = new ListaReproducaoDTO("Playlist 1", "Playlista de Rock", musicas);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post(URL_BASE).header("Authorization", "Bearer " + token).contentType("application/json").content(json));
        mockMvc.perform(get(URL_BASE + "/Playlist 1").header("Authorization", "Bearer " + token).contentType("application/json")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve apagar uma lista existente e retornar 204 no content")
    void deleteListaDeReproducao() throws Exception {
        MusicaDTO musica1 = new MusicaDTO("Lives", "Daron Malakian", "Dictador", "2018", "Metal");
        MusicaDTO musica2 = new MusicaDTO("Lie Lie Lie", "Serj Tankian", "Elect the Dead", "2007", "Rock");
        List<MusicaDTO> musicas = List.of(musica1, musica2);
        ListaReproducaoDTO dto = new ListaReproducaoDTO("Playlist 2", "Playlista de Rock", musicas);

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post(URL_BASE).header("Authorization", "Bearer " + token).contentType("application/json").content(json)).andExpect(status().isCreated());
        mockMvc.perform(delete(URL_BASE + "/Playlist 2").header("Authorization", "Bearer " + token).contentType("application/json")).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 404 not found para uma lista não existente ao deletar")
    void deleteListaDeReproducaoInexistente() throws Exception {
        MusicaDTO musica1 = new MusicaDTO("Lives", "Daron Malakian", "Dictador", "2018", "Metal");
        MusicaDTO musica2 = new MusicaDTO("Lie Lie Lie", "Serj Tankian", "Elect the Dead", "2007", "Rock");
        List<MusicaDTO> musicas = List.of(musica1, musica2);
        ListaReproducaoDTO dto = new ListaReproducaoDTO("Playlist 3", "Playlista de Rock", musicas);

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post(URL_BASE).contentType("application/json").header("Authorization", "Bearer " + token).content(json)).andExpect(status().isCreated());
        mockMvc.perform(delete(URL_BASE + "/Playlist 0").header("Authorization", "Bearer " + token).contentType("application/json")).andExpect(status().isNotFound());
    }

}
