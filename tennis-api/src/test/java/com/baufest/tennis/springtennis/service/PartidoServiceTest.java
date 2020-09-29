package com.baufest.tennis.springtennis.service;

import com.baufest.tennis.springtennis.dto.CanchaDTO;
import com.baufest.tennis.springtennis.dto.EntrenadorDTO;
import com.baufest.tennis.springtennis.dto.JugadorDTO;
import com.baufest.tennis.springtennis.dto.PartidoDTO;
import com.baufest.tennis.springtennis.enums.Estado;
import com.baufest.tennis.springtennis.enums.ModoJugador;
import com.baufest.tennis.springtennis.mapper.CanchaMapper;
import com.baufest.tennis.springtennis.mapper.JugadorMapper;
import com.baufest.tennis.springtennis.mapper.PartidoMapper;
import com.baufest.tennis.springtennis.mapper.PartidoMapperImpl;
import com.baufest.tennis.springtennis.model.Cancha;
import com.baufest.tennis.springtennis.model.Entrenador;
import com.baufest.tennis.springtennis.model.Jugador;
import com.baufest.tennis.springtennis.model.Partido;
import com.baufest.tennis.springtennis.repository.PartidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartidoServiceTest {

    public static final int SCORE_ADV = 4;
    public static final int SCORE_40 = 3;
    public static final int SCORE_30 = 2;
    public static final int SCORE_15 = 1;
    public static final int SCORE_0 = 0;

    List<Partido> partidosDePrueba = new ArrayList<>();
    List<PartidoDTO> partidosDTODePrueba = new ArrayList<>();

    Jugador jugador1 = new Jugador(1L, "Federer", SCORE_30, new Entrenador("Freya"));
    Jugador jugador2 = new Jugador(2L, "Nadal", SCORE_30, new Entrenador("Freya"));

    JugadorDTO jugadorDTO1 = new JugadorDTO(1L, "Federer", SCORE_30, new EntrenadorDTO("Kali"));
    JugadorDTO jugadorDTO2 = new JugadorDTO(2L, "Nadal", SCORE_30, new EntrenadorDTO("Kali"));

    Cancha cancha1 = new Cancha("Roland Garros", "Av Francia 123");
    CanchaDTO canchaDTO1 = new CanchaDTO("Roland Garros", "Av Francia 123");

    Partido partidoParaAgregar = new Partido(1L, new Date(2543447456000L), Estado.NO_INICIADO, jugador1, jugador2, 0, 0, 0, 0, cancha1);

    PartidoDTO partidoDTOParaAgregar = new PartidoDTO(1L, new Date(2543447456000L), Estado.NO_INICIADO, jugadorDTO1, jugadorDTO2, 0, 0, 0, 0, canchaDTO1);

    @Mock()
    PartidoRepository partidoRepository;

    @InjectMocks
    PartidoServiceImpl partidoService;

    @Spy
    PartidoMapper mapper = new PartidoMapperImpl(CanchaMapper.INSTANCE, JugadorMapper.INSTANCE);

    @BeforeEach
    public void setUp() {
        partidosDePrueba.clear();
        partidosDePrueba.add(new Partido(new Date(), Estado.NO_INICIADO, jugador1, jugador1, cancha1));
        partidosDePrueba.add(new Partido());
        partidosDePrueba.add(new Partido());
        partidosDePrueba.add(new Partido());

        partidosDTODePrueba.clear();
        partidosDTODePrueba.add(new PartidoDTO(new Date(), Estado.NO_INICIADO, jugadorDTO1, jugadorDTO1, canchaDTO1));
        partidosDTODePrueba.add(new PartidoDTO());
        partidosDTODePrueba.add(new PartidoDTO());
        partidosDTODePrueba.add(new PartidoDTO());
    }


    @Test
    void testListPartidos() {
        when(partidoRepository.findAll()).thenReturn(partidosDePrueba);

        List<PartidoDTO> canchasConseguidos = partidoService.listAll();
        assertEquals(partidosDePrueba.size(), canchasConseguidos.size());
    }

    @Test
    void testGetPartidoByID() {
        when(partidoRepository.findById(partidoParaAgregar.getId()))
                .thenReturn(Optional.of(partidoParaAgregar));
        PartidoDTO partidoEncontrado = partidoService.getById(partidoParaAgregar.getId());
        assertEquals(partidoParaAgregar.getId(), partidoEncontrado.getId());
    }

    @Test
    void testSaveOrUpdate() {
        partidoService.save(partidoDTOParaAgregar);
        ArgumentCaptor<Partido> argumentCaptor = ArgumentCaptor.forClass(Partido.class);
        verify(partidoRepository).save(argumentCaptor.capture());
        assertEquals(partidoParaAgregar.getId(), argumentCaptor.getValue().getId());
    }

    @Test
    void testDelete() {
        Long idParaBorrar = 1L;
        when(partidoRepository.existsById(1L)).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoParaAgregar));

        partidoService.delete(idParaBorrar);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(partidoRepository).deleteById(argumentCaptor.capture());
        assertEquals(idParaBorrar, argumentCaptor.getValue());
    }

    @Test
    void testDeleteNotFound() {
        Long idParaBorrar = 1L;
        when(partidoRepository.existsById(1L)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> partidoService.delete(idParaBorrar));
    }

    @Test
    void testDeletePartidoFinalizado() {
        Partido partidoFinalizado = new Partido(new Date(), Estado.FINALIZADO, jugador1, jugador1, cancha1);
        partidoFinalizado.setId(1L);
        when(partidoRepository.existsById(1L)).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoFinalizado));
        assertThrows(IllegalArgumentException.class, () -> partidoService.delete(1L));
    }

    @Test
    void testDeletePartidoEnCurso() {
        Partido partidoEnCurso = new Partido(new Date(), Estado.EN_CURSO, jugador1, jugador1, cancha1);
        partidoEnCurso.setId(1L);
        when(partidoRepository.existsById(1L)).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoEnCurso));
        assertThrows(IllegalArgumentException.class, () -> partidoService.delete(1L));
    }

    @Test
    void testInsertExisting() {
        partidoParaAgregar.setId(1L);
        when(partidoRepository.existsById(partidoParaAgregar.getId())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> partidoService.save(partidoDTOParaAgregar));
    }

    @Test
    void testInsertPartidoConJugadoresIgualesEntreSi() {
        PartidoDTO partido = new PartidoDTO(new Date(), Estado.NO_INICIADO, jugadorDTO1, jugadorDTO1, canchaDTO1);
        assertThrows(IllegalArgumentException.class, () -> partidoService.save(partido));
    }

    @Test
    void testInsertPartidoConFechaMenorALaActual() {
        Date ayer = Date.from(ZonedDateTime.now().minusDays(1).toInstant());
        PartidoDTO partido = new PartidoDTO(ayer, Estado.NO_INICIADO, jugadorDTO1, jugadorDTO2, canchaDTO1);
        assertThrows(IllegalArgumentException.class, () -> partidoService.save(partido));
    }

    @Test
    void testUpdateExisting() {
        when(partidoRepository.existsById(partidoParaAgregar.getId())).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoParaAgregar));

        partidoService.update(partidoDTOParaAgregar);
        ArgumentCaptor<Partido> argumentCaptor = ArgumentCaptor.forClass(Partido.class);
        verify(partidoRepository).save(argumentCaptor.capture());
        assertEquals(partidoParaAgregar.getId(), argumentCaptor.getValue().getId());
    }

    @Test
    void testUpdateNotFound() {
        when(partidoRepository.existsById(partidoParaAgregar.getId())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> partidoService.update(partidoDTOParaAgregar));
    }

    @Test
    void testUpdatePartidoConFechaMenorALaActual() {
        Date ayer = Date.from(ZonedDateTime.now().minusDays(1).toInstant());

        PartidoDTO partidoDTO = new PartidoDTO(ayer, Estado.NO_INICIADO, jugadorDTO1, jugadorDTO2, canchaDTO1);
        partidoDTO.setId(1L);

        Partido partido = this.mapper.fromDTO(partidoDTO);

        when(partidoRepository.existsById(1L)).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));

        assertThrows(IllegalArgumentException.class, () -> partidoService.update(partidoDTO));
    }

    @Test
    void testUpdatePartidoFinalizado() {
        PartidoDTO partidoFinalizado = new PartidoDTO(new Date(), Estado.FINALIZADO, jugadorDTO1, jugadorDTO2, canchaDTO1);
        partidoFinalizado.setId(1L);
        Partido partido = mapper.fromDTO(partidoFinalizado);

        when(partidoRepository.existsById(1L)).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));

        assertThrows(IllegalArgumentException.class, () -> partidoService.update(partidoFinalizado));
    }

    @Test
    void testUpdatePartidoEnCurso() {
        PartidoDTO partidoEnCurso = new PartidoDTO(new Date(), Estado.EN_CURSO, jugadorDTO1, jugadorDTO2, canchaDTO1);
        partidoEnCurso.setId(1L);

        Partido partido = mapper.fromDTO(partidoEnCurso);

        when(partidoRepository.existsById(1L)).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));

        assertThrows(IllegalArgumentException.class, () -> partidoService.update(partidoEnCurso));
    }

    @Test
    void initGamePartidoInexistente() {
        when(partidoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> partidoService.initGame(1L));
    }

    @Test
    void initGamePartidoExistente() {
        Partido partidoAIniciar = new Partido(new Date(), Estado.NO_INICIADO, jugador1, jugador1, cancha1);
        partidoAIniciar.setId(1L);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoAIniciar));
        partidoService.initGame(1L);
        ArgumentCaptor<Partido> argumentCaptor = ArgumentCaptor.forClass(Partido.class);
        verify(partidoRepository).save(argumentCaptor.capture());
        assertEquals(Estado.EN_CURSO, argumentCaptor.getValue().getEstado());
    }

    @Test
    void sumarPuntosPartidoInexistente() {
        when(partidoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> partidoService.sumarPuntos(1L, ModoJugador.LOCAL));
    }

    @Test
    void testSumarPuntosPartidoFinalizado() {
        Partido partidoFinalizado = new Partido(new Date(), Estado.FINALIZADO, jugador1, jugador1, cancha1);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoFinalizado));
        assertThrows(IllegalArgumentException.class, () -> partidoService.sumarPuntos(1L, ModoJugador.LOCAL));
    }

    @Test
    void testSumarPuntosPartidoNoInicializado() {
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoParaAgregar));
        assertThrows(IllegalArgumentException.class, () -> partidoService.sumarPuntos(1L, ModoJugador.LOCAL));
    }

    @Test
    void sumarPuntos1() {
        testSumarPuntos(SCORE_0, SCORE_15, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos2() {
        testSumarPuntos(SCORE_0, SCORE_30, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos3() {
        testSumarPuntos(SCORE_0, SCORE_40, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos4() {
        testSumarPuntosInconsistent(SCORE_0, SCORE_ADV, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos5() {
        testSumarPuntos(SCORE_15, SCORE_15, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos6() {
        testSumarPuntos(SCORE_15, SCORE_30, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos7() {
        testSumarPuntos(SCORE_15, SCORE_40, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos8() {
        testSumarPuntosInconsistent(SCORE_15, SCORE_ADV, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos9() {
        testSumarPuntos(SCORE_30, SCORE_15, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos10() {
        testSumarPuntos(SCORE_30, SCORE_30, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos11() {
        testSumarPuntos(SCORE_30, SCORE_40, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos12() {
        testSumarPuntosInconsistent(SCORE_30, SCORE_ADV, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos13() {
        testWinGamePoint(SCORE_40, SCORE_15, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos14() {
        testWinGamePoint(SCORE_40, SCORE_30, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos15() {
        testSumarPuntos(SCORE_40, SCORE_40, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos16() {
        testSumarPuntos(SCORE_40, SCORE_ADV, ModoJugador.LOCAL, true);
    }

    @Test
    void sumarPuntos17() {
        testSumarPuntos(SCORE_0, SCORE_15, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos18() {
        testSumarPuntos(SCORE_0, SCORE_30, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos19() {
        testWinGamePoint(SCORE_0, SCORE_40, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos20() {
        testSumarPuntosInconsistent(SCORE_0, SCORE_ADV, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos21() {
        testSumarPuntos(SCORE_15, SCORE_15, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos22() {
        testSumarPuntos(SCORE_15, SCORE_30, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos23() {
        testWinGamePoint(SCORE_15, SCORE_40, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos24() {
        testSumarPuntosInconsistent(SCORE_15, SCORE_ADV, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos25() {
        testSumarPuntos(SCORE_30, SCORE_15, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos26() {
        testSumarPuntos(SCORE_30, SCORE_30, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos27() {
        testWinGamePoint(SCORE_30, SCORE_40, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos28() {
        testSumarPuntosInconsistent(SCORE_30, SCORE_ADV, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos29() {
        testSumarPuntos(SCORE_40, SCORE_15, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos30() {
        testSumarPuntos(SCORE_40, SCORE_30, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos31() {
        testSumarPuntos(SCORE_40, SCORE_40, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos32() {
        testWinGamePoint(SCORE_40, SCORE_ADV, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos33() {
        testWinMatchPoint(SCORE_15, SCORE_40, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos34() {
        testWinMatchPoint(SCORE_40, SCORE_15, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos35() {
        testSumarPuntos(SCORE_ADV, SCORE_40, ModoJugador.VISITANTE, true);
    }

    @Test
    void sumarPuntos36() {
        testSumarPuntosInconsistent(SCORE_ADV, SCORE_15, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos37() {
        testSumarPuntosInconsistent(SCORE_ADV, SCORE_30, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos38() {
        testWinGamePoint(SCORE_ADV, SCORE_40, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos39() {
        testSumarPuntosInconsistent(SCORE_ADV, SCORE_0, ModoJugador.VISITANTE);
    }

    private void testSumarPuntos(int localScore, int visitanteScore, ModoJugador modoJugador, boolean deuce) {
        Partido partido = new Partido(1L, new Date(), Estado.EN_CURSO, jugador1, jugador1, localScore, 0, visitanteScore, 0, cancha1);
        Partido partidoClon = new Partido(1L, new Date(), Estado.EN_CURSO, jugador1, jugador1, localScore, 0, visitanteScore, 0, cancha1);

        when(partidoRepository.save(any())).thenAnswer((Answer<Partido>) invocation -> (Partido) invocation.getArguments()[0]);

        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));
        PartidoDTO partidoResult = partidoService.sumarPuntos(1L, modoJugador);
        ArgumentCaptor<Partido> argumentCaptor = ArgumentCaptor.forClass(Partido.class);
        verify(partidoRepository).save(argumentCaptor.capture());
        assertEquals(partidoResult.getId(), argumentCaptor.getValue().getId());
        assertEquals(argumentCaptor.getValue().getId(), partidoClon.getId());
        assertEquals(argumentCaptor.getValue().getCancha(), partidoClon.getCancha());
        assertEquals(argumentCaptor.getValue().getCantidadGamesLocal(), partidoClon.getCantidadGamesLocal());
        assertEquals(argumentCaptor.getValue().getCantidadGamesVisitante(), partidoClon.getCantidadGamesVisitante());
        assertEquals(argumentCaptor.getValue().getJugadorLocal(), partidoClon.getJugadorLocal());
        assertEquals(argumentCaptor.getValue().getJugadorVisitante(), partidoClon.getJugadorVisitante());
        assertEquals(argumentCaptor.getValue().getEstado(), partidoClon.getEstado());

        if (modoJugador.equals(ModoJugador.LOCAL)) {
            if (deuce) {
                assertEquals(argumentCaptor.getValue().getScoreLocal(), localScore);
                assertEquals(argumentCaptor.getValue().getScoreVisitante(), visitanteScore - 1);
            } else {
                assertEquals(argumentCaptor.getValue().getScoreLocal(), localScore + 1);
                assertEquals(argumentCaptor.getValue().getScoreVisitante(), visitanteScore);
            }
        } else {
            if (deuce) {
                assertEquals(argumentCaptor.getValue().getScoreVisitante(), visitanteScore);
                assertEquals(argumentCaptor.getValue().getScoreLocal(), localScore - 1);
            } else {
                assertEquals(argumentCaptor.getValue().getScoreVisitante(), visitanteScore + 1);
                assertEquals(argumentCaptor.getValue().getScoreLocal(), localScore);
            }
        }

    }

    private void testWinGamePoint(int localScore, int visitanteScore, ModoJugador modoJugador) {
        Partido partido = new Partido(1L, new Date(), Estado.EN_CURSO, jugador1, jugador1, localScore, 0, visitanteScore, 0, cancha1);
        Partido partidoClon = new Partido(1L, new Date(), Estado.EN_CURSO, jugador1, jugador1, localScore, 0, visitanteScore, 0, cancha1);

        when(partidoRepository.save(any())).thenAnswer((Answer<Partido>) invocation -> (Partido) invocation.getArguments()[0]);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));
        PartidoDTO partidoResult = partidoService.sumarPuntos(1L, modoJugador);
        ArgumentCaptor<Partido> argumentCaptor = ArgumentCaptor.forClass(Partido.class);
        verify(partidoRepository).save(argumentCaptor.capture());
        assertEquals(partidoResult.getId(), argumentCaptor.getValue().getId());
        assertEquals(argumentCaptor.getValue().getId(), partidoClon.getId());
        assertEquals(argumentCaptor.getValue().getCancha(), partidoClon.getCancha());
        if (modoJugador.equals(ModoJugador.LOCAL)) {
            assertEquals(argumentCaptor.getValue().getCantidadGamesLocal(), partidoClon.getCantidadGamesLocal() + 1);
            assertEquals(argumentCaptor.getValue().getCantidadGamesVisitante(), partidoClon.getCantidadGamesVisitante());
        } else {
            assertEquals(argumentCaptor.getValue().getCantidadGamesLocal(), partidoClon.getCantidadGamesLocal());
            assertEquals(argumentCaptor.getValue().getCantidadGamesVisitante(), partidoClon.getCantidadGamesVisitante() + 1);
        }
        assertEquals(argumentCaptor.getValue().getJugadorLocal(), partidoClon.getJugadorLocal());
        assertEquals(argumentCaptor.getValue().getJugadorVisitante(), partidoClon.getJugadorVisitante());
        assertEquals(0, argumentCaptor.getValue().getScoreVisitante());
        assertEquals(0, argumentCaptor.getValue().getScoreLocal());
        assertEquals(argumentCaptor.getValue().getEstado(), partidoClon.getEstado());

    }

    private void testWinMatchPoint(int localScore, int visitanteScore, ModoJugador modoJugador) {
        Partido partido = new Partido(1L, new Date(), Estado.EN_CURSO, jugador1, jugador1, localScore, 0, visitanteScore, 0, cancha1);

        if (modoJugador.equals(ModoJugador.LOCAL)) {
            partido.setCantidadGamesLocal(5);
            partido.setCantidadGamesVisitante(1);
        }
        if (modoJugador.equals(ModoJugador.VISITANTE)) {
            partido.setCantidadGamesLocal(1);
            partido.setCantidadGamesVisitante(5);
        }
        when(partidoRepository.save(any())).thenAnswer((Answer<Partido>) invocation -> (Partido) invocation.getArguments()[0]);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));
        PartidoDTO partidoResult = partidoService.sumarPuntos(1L, modoJugador);
        ArgumentCaptor<Partido> argumentCaptor = ArgumentCaptor.forClass(Partido.class);
        verify(partidoRepository).save(argumentCaptor.capture());
        assertEquals(partidoResult.getId(), argumentCaptor.getValue().getId());
        assertEquals(0, argumentCaptor.getValue().getScoreVisitante());
        assertEquals(0, argumentCaptor.getValue().getScoreLocal());
        assertEquals(Estado.FINALIZADO, argumentCaptor.getValue().getEstado());
    }

    private void testSumarPuntosInconsistent(int localScore, int visitanteScore, ModoJugador modoJugador) {
        Partido partidoAIniciar = new Partido(new Date(), Estado.NO_INICIADO, jugador1, jugador1, cancha1);
        partidoAIniciar.setId(1L);
        partidoAIniciar.setScoreLocal(localScore);
        partidoAIniciar.setScoreVisitante(visitanteScore);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoAIniciar));
        assertThrows(IllegalArgumentException.class, () -> partidoService.sumarPuntos(1L, modoJugador));
    }
}
