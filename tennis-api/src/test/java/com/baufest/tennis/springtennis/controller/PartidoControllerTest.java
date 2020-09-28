package com.baufest.tennis.springtennis.controller;

import com.baufest.tennis.springtennis.dto.CanchaDTO;
import com.baufest.tennis.springtennis.dto.PartidoDTO;
import com.baufest.tennis.springtennis.enums.ModoJugador;
import com.baufest.tennis.springtennis.service.PartidoServiceImpl;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(PartidoController.class)
class PartidoControllerTest {
    String basePath = "/springtennis/api/v1/partidos/";
    List<PartidoDTO> partidosDePrueba = new ArrayList<>();
    JSONArray partidosDePruebaEnJSON = new JSONArray();
    PartidoDTO partidoParaAgregar = new PartidoDTO();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PartidoController partidoController;

    @MockBean
    PartidoServiceImpl partidoService;

    @BeforeEach
    public void setUp() {
        partidosDePrueba.clear();
        partidosDePrueba.add(new PartidoDTO());
        partidosDePrueba.add(new PartidoDTO());
        partidosDePrueba.add(new PartidoDTO());
        partidosDePrueba.add(new PartidoDTO());
        partidosDePrueba.add(new PartidoDTO());


        partidosDePrueba.get(0).setId(1L);
        partidosDePrueba.get(1).setId(2L);
        partidosDePrueba.get(2).setId(3L);
        partidosDePrueba.get(3).setId(4L);
        partidosDePrueba.get(4).setId(5L);


        partidosDePrueba.get(0).setCancha(new CanchaDTO());
        partidosDePrueba.get(1).setCancha(new CanchaDTO());
        partidosDePrueba.get(2).setCancha(new CanchaDTO());
        partidosDePrueba.get(3).setCancha(new CanchaDTO());
        partidosDePrueba.get(4).setCancha(new CanchaDTO());

        partidoParaAgregar.setId(5L);
        partidoParaAgregar.setCancha(new CanchaDTO());

        partidosDePrueba.forEach((x) -> partidosDePruebaEnJSON.put(x.toJSONObject()));

    }

    @Test
    void testListAll() throws Exception {
        when(partidoService.listAll()).thenReturn(partidosDePrueba);

        mockMvc.perform(MockMvcRequestBuilders.get(basePath).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(partidosDePruebaEnJSON.toString()));
    }

    @Test
    void testGetByID() throws Exception {
        long idPartidoGet = 1L;
        when(partidoService.getById(1L)).thenReturn(partidosDePrueba.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + idPartidoGet).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(partidosDePrueba.get(0)
                        .toJSONObject().toString()));
    }

    @Test
    void testSavePartido() throws Exception {
        when(partidoService.save(any())).thenReturn(partidosDePrueba.get(0));

        mockMvc.perform(MockMvcRequestBuilders.post(basePath).contentType(MediaType.APPLICATION_JSON)
                .content(partidoParaAgregar.toJSONObject().toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        ArgumentCaptor<PartidoDTO> argumentCaptor = ArgumentCaptor.forClass(PartidoDTO.class);
        verify(partidoService).save(argumentCaptor.capture());
        assertEquals(partidoParaAgregar.getId(), argumentCaptor.getValue().getId());
    }

    @Test
    void testUpdatePartido() throws Exception {
        when(partidoService.update(any())).thenReturn(partidosDePrueba.get(4));

        mockMvc.perform(MockMvcRequestBuilders.put(basePath + partidosDePrueba.get(4).getId()).contentType(MediaType.APPLICATION_JSON)
                .content(partidoParaAgregar.toJSONObject().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ArgumentCaptor<PartidoDTO> argumentCaptor = ArgumentCaptor.forClass(PartidoDTO.class);
        verify(partidoService).update(argumentCaptor.capture());
        assertEquals(partidosDePrueba.get(4).getId(), argumentCaptor.getValue().getId());
        assertEquals(partidoParaAgregar.getId(), argumentCaptor.getValue().getId());
    }

    @Test
    void testDeletePartido() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(basePath + partidosDePrueba.get(0).getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(partidoService).delete(argumentCaptor.capture());
        assertEquals(partidosDePrueba.get(0).getId(), argumentCaptor.getValue());
    }

    @Test
    void testInitGame() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + partidosDePrueba.get(0).getId() + "/actions/init"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(partidoService).initGame(argumentCaptor.capture());
        assertEquals(partidosDePrueba.get(0).getId(), argumentCaptor.getValue());
    }

    @Test
    void testInitGameFailed() throws Exception {
        doThrow(new NoSuchElementException()).when(partidoService).initGame(any());

        mockMvc.perform(MockMvcRequestBuilders.put(basePath + partidosDePrueba.get(0).getId() + "/actions/init"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testInitGameInternalError() throws Exception {
        doThrow(new RuntimeException()).when(partidoService).initGame(any());

        mockMvc.perform(MockMvcRequestBuilders.put(basePath + partidosDePrueba.get(0).getId() + "/actions/init"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testSumarPuntosLocalFailed() throws Exception {
        doThrow(new IllegalArgumentException()).when(partidoService).sumarPuntos(any(), any());

        mockMvc.perform(MockMvcRequestBuilders.post(basePath + partidosDePrueba.get(0).getId() + "/actions/sumar-punto")
                .param("modoJugador", "LOCAL"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testSumarPuntosLocal() throws Exception {
        when(partidoService.sumarPuntos(any(), any())).thenReturn(partidosDePrueba.get(0));

        mockMvc.perform(MockMvcRequestBuilders.post(basePath + partidosDePrueba.get(0).getId() + "/actions/sumar-punto")
                .param("modoJugador", "LOCAL"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        ArgumentCaptor<Long> id = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ModoJugador> modoJugador = ArgumentCaptor.forClass(ModoJugador.class);

        verify(partidoService).sumarPuntos(id.capture(), modoJugador.capture());
        assertEquals(partidosDePrueba.get(0).getId(), id.getValue());
        assertEquals(ModoJugador.LOCAL, modoJugador.getValue());
    }

    @Test
    void testSumarPuntosVisitante() throws Exception {
        when(partidoService.sumarPuntos(any(), any())).thenReturn(partidosDePrueba.get(0));

        mockMvc.perform(MockMvcRequestBuilders.post(basePath + partidosDePrueba.get(0).getId() + "/actions/sumar-punto")
                .param("modoJugador", "VISITANTE"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        ArgumentCaptor<Long> id = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ModoJugador> modoJugador = ArgumentCaptor.forClass(ModoJugador.class);

        verify(partidoService).sumarPuntos(id.capture(), modoJugador.capture());
        assertEquals(partidosDePrueba.get(0).getId(), id.getValue());
        assertEquals(ModoJugador.VISITANTE, modoJugador.getValue());
    }
}
