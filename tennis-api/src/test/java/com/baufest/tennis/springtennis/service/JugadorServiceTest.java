package com.baufest.tennis.springtennis.service;

import com.baufest.tennis.springtennis.dto.JugadorDTO;
import com.baufest.tennis.springtennis.enums.Estado;
import com.baufest.tennis.springtennis.mapper.JugadorMapper;
import com.baufest.tennis.springtennis.model.Cancha;
import com.baufest.tennis.springtennis.model.Jugador;
import com.baufest.tennis.springtennis.model.Partido;
import com.baufest.tennis.springtennis.repository.JugadorRepository;
import com.baufest.tennis.springtennis.repository.PartidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JugadorServiceTest {
    private final List<Jugador> jugadoresDePrueba = new ArrayList<>();
    private final List<JugadorDTO> jugadoresDTODePrueba = new ArrayList<>();
    private final JugadorDTO jugadorParaAgregar = new JugadorDTO();
    Cancha cancha = new Cancha("Roland Garros", "Av Francia 123");

    @InjectMocks
    JugadorServiceImpl jugadorService;

    @Mock
    JugadorRepository jugadorRepository;

    @Mock
    PartidoRepository partidoRepository;

    @Spy
    JugadorMapper mapper = JugadorMapper.INSTANCE;

    @BeforeEach
    public void setUp() {
        jugadoresDTODePrueba.clear();
        jugadoresDTODePrueba.add(new JugadorDTO());
        jugadoresDTODePrueba.add(new JugadorDTO());
        jugadoresDTODePrueba.add(new JugadorDTO());
        jugadoresDTODePrueba.add(new JugadorDTO());
        jugadoresDTODePrueba.get(0).setNombre("facu");
        jugadoresDTODePrueba.get(1).setNombre("fer");
        jugadoresDTODePrueba.get(2).setNombre("juli");
        jugadoresDTODePrueba.get(3).setNombre("axel");
        jugadoresDTODePrueba.get(0).setId(1L);
        jugadoresDTODePrueba.get(1).setId(2L);
        jugadoresDTODePrueba.get(2).setId(3L);
        jugadoresDTODePrueba.get(3).setId(4L);
        jugadoresDTODePrueba.get(0).setPuntos(20);
        jugadoresDTODePrueba.get(1).setPuntos(15);
        jugadoresDTODePrueba.get(2).setPuntos(10);
        jugadoresDTODePrueba.get(3).setPuntos(5);

        jugadoresDePrueba.clear();
        jugadoresDePrueba.add(new Jugador());
        jugadoresDePrueba.add(new Jugador());
        jugadoresDePrueba.add(new Jugador());
        jugadoresDePrueba.add(new Jugador());
        jugadoresDePrueba.get(0).setNombre("facu");
        jugadoresDePrueba.get(1).setNombre("fer");
        jugadoresDePrueba.get(2).setNombre("juli");
        jugadoresDePrueba.get(3).setNombre("axel");
        jugadoresDePrueba.get(0).setId(1L);
        jugadoresDePrueba.get(1).setId(2L);
        jugadoresDePrueba.get(2).setId(3L);
        jugadoresDePrueba.get(3).setId(4L);
        jugadoresDePrueba.get(0).setPuntos(20);
        jugadoresDePrueba.get(1).setPuntos(15);
        jugadoresDePrueba.get(2).setPuntos(10);
        jugadoresDePrueba.get(3).setPuntos(5);

        jugadorParaAgregar.setId(5L);
        jugadorParaAgregar.setNombre("lucas");
        jugadorParaAgregar.setPuntos(25);

    }

    @Test
    void testListJugadores() {
        when(jugadorRepository.findAllByOrderByNombreAsc()).thenReturn(jugadoresDePrueba);
        List<JugadorDTO> jugadoresConseguidos = jugadorService.listAll();
        assertEquals(jugadoresDTODePrueba.size(),jugadoresConseguidos.size());
    }

    @Test
    void testGetJugadorByID() {
        when(jugadorRepository.findById(jugadoresDTODePrueba.get(0).getId())).thenReturn(Optional.of(jugadoresDePrueba.get(0)));
        JugadorDTO jugadorEncontrado = jugadorService.getById(jugadoresDTODePrueba.get(0).getId());
        assertEquals(jugadoresDTODePrueba.get(0).getId(),jugadorEncontrado.getId());
    }

    @Test
    void testSaveOrUpdate() {
        jugadorService.save(jugadorParaAgregar);

        ArgumentCaptor<Jugador> argumentCaptor = ArgumentCaptor.forClass(Jugador.class);
        verify(jugadorRepository).save(argumentCaptor.capture());
        assertEquals(jugadorParaAgregar.getId(),argumentCaptor.getValue().getId());
    }

    @Test
    void testDelete() {
        Long idParaBorrar = 1L;
        when(jugadorRepository.existsById(1L)).thenReturn(true);

        jugadorService.delete(idParaBorrar);

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(jugadorRepository).deleteById(argumentCaptor.capture());
        assertEquals(idParaBorrar,argumentCaptor.getValue());
    }

    @Test
    void testDeleteNotFound() {
        Long idParaBorrar = 1L;
        when(jugadorRepository.existsById(1L)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> jugadorService.delete(idParaBorrar));
    }

    @Test
    void testInsertExisting() {
        when(jugadorRepository.existsById(jugadorParaAgregar.getId())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () ->  jugadorService.save(jugadorParaAgregar));
    }

    @Test
    void testUpdateExisting() {
        when(jugadorRepository.existsById(jugadorParaAgregar.getId())).thenReturn(true);
        jugadorService.update(jugadorParaAgregar);
        ArgumentCaptor<Jugador> argumentCaptor = ArgumentCaptor.forClass(Jugador.class);
        verify(jugadorRepository).save(argumentCaptor.capture());
        assertEquals(jugadorParaAgregar.getId(),argumentCaptor.getValue().getId());
    }

    @Test
    void testUpdateNotFound() {
        when(jugadorRepository.existsById(jugadorParaAgregar.getId())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () ->  jugadorService.update(jugadorParaAgregar));
    }

    @Test
    void testRecalcularRankingJugadorGanoUnPartidoDeLocal() {
        Jugador jugador = jugadoresDePrueba.get(0);

        List<Partido> partidos = new ArrayList<>();
        partidos.add(new Partido(1L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(1), 0, 6, 0, 1, cancha));

        when(partidoRepository.findAll()).thenReturn(partidos);
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(jugador)).thenReturn(jugador);

        JugadorDTO jugadorDTO = this.jugadorService.recalculateRanking(1L);

        assertEquals(10, jugadorDTO.getPuntos());
    }

    @Test
    void testRecalcularRankingJugadorGanoTresPartidosDeLocalYDosDeVisitante() {
        Jugador jugador = jugadoresDePrueba.get(0);

        List<Partido> partidos = new ArrayList<>();
        partidos.add(new Partido(1L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(1), 0, 6, 0, 1, cancha));
        partidos.add(new Partido(2L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(2), 0, 6, 0, 1, cancha));
        partidos.add(new Partido(3L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(3), 0, 6, 0, 1, cancha));
        partidos.add(new Partido(4L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(1), jugador, 0, 0, 0, 6, cancha));
        partidos.add(new Partido(5L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(2), jugador, 0, 0, 0, 6, cancha));

        when(partidoRepository.findAll()).thenReturn(partidos);
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(jugador)).thenReturn(jugador);

        JugadorDTO jugadorDTO = this.jugadorService.recalculateRanking(1L);

        assertEquals(60, jugadorDTO.getPuntos());
    }

    @Test
    void testRecalcularRankingJugadorPerdioDosPartidosDeLocal() {
        Jugador jugador = jugadoresDePrueba.get(0);

        List<Partido> partidos = new ArrayList<>();
        partidos.add(new Partido(1L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(1), 0, 1, 0, 6, cancha));
        partidos.add(new Partido(2L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(2), 0, 0, 0, 6, cancha));

        when(partidoRepository.findAll()).thenReturn(partidos);
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(jugador)).thenReturn(jugador);

        JugadorDTO jugadorDTO = this.jugadorService.recalculateRanking(1L);

        assertEquals(0, jugadorDTO.getPuntos());
    }

    @Test
    void testRecalcularRankingJugadorPerdioDosPartidosDeVisitante() {
        Jugador jugador = jugadoresDePrueba.get(0);

        List<Partido> partidos = new ArrayList<>();
        partidos.add(new Partido(1L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(1), jugador, 0, 6, 0, 1, cancha));
        partidos.add(new Partido(2L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(2), jugador, 0, 6, 0, 0, cancha));

        when(partidoRepository.findAll()).thenReturn(partidos);
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(jugador)).thenReturn(jugador);

        JugadorDTO jugadorDTO = this.jugadorService.recalculateRanking(1L);

        assertEquals(0, jugadorDTO.getPuntos());
    }

    @Test
    void testRecalcularRankingJugadorGanoDosPartidosDeLocalGanoUnoDeVisitantePerdioTresDeLocalYPerdioDosDeVisitante() {

        Jugador jugador = jugadoresDePrueba.get(0);

        List<Partido> partidos = new ArrayList<>();

        partidos.add(new Partido(1L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(1), 0, 6, 0, 1, null));
        partidos.add(new Partido(2L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(2), 0, 6, 0, 2, null));
        partidos.add(new Partido(3L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(1), jugador, 0, 2, 0, 6, null));
        partidos.add(new Partido(4L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(1), 0, 0, 0, 6, null));
        partidos.add(new Partido(5L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(2), 0, 1, 0, 6, null));
        partidos.add(new Partido(6L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(3), 0, 0, 0, 6, null));
        partidos.add(new Partido(7L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(1), jugador, 0, 6, 0, 0, null));
        partidos.add(new Partido(8L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(2), jugador, 0, 6, 0, 1, null));

        when(partidoRepository.findAll()).thenReturn(partidos);
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(jugador)).thenReturn(jugador);

        JugadorDTO jugadorDTO = this.jugadorService.recalculateRanking(1L);

        assertEquals( 20, jugadorDTO.getPuntos());
    }
}