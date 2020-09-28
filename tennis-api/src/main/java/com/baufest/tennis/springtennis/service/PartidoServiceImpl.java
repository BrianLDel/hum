package com.baufest.tennis.springtennis.service;

import com.baufest.tennis.springtennis.dto.JugadorDTO;
import com.baufest.tennis.springtennis.dto.PartidoDTO;
import com.baufest.tennis.springtennis.enums.Estado;
import com.baufest.tennis.springtennis.enums.ModoJugador;
import com.baufest.tennis.springtennis.mapper.PartidoMapper;
import com.baufest.tennis.springtennis.model.Partido;
import com.baufest.tennis.springtennis.repository.PartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PartidoServiceImpl implements PartidoService {
    public static final int SCORE_ADV = 4;
    public static final int SCORE_40 = 3;
    public static final int SCORE_30 = 2;
    public static final int SCORE_15 = 1;
    public static final int SCORE_0 = 0;
    public static final String DOES_NOT_EXIST = " does not exist.";
    public static final String PARTIDO_WITH_ID = "Partido with id = ";
    public static final String ALREADY_EXISTS = " already exists.";
    public static final String NOT_IN_PROGRESS = " is not in progress. ";
    private static final String ALREADY_IN_PROGRESS = " is already in progress or is finished. ";
    public static final String SCORE_IMPOSIBLE = "Score imposible";
    private static final String PLAYER_MISSING = "Se deben agregar ambos jugadores.";
    private static final String PLAYER_DUPLICATED = "Los jugadores agregados deben ser distintos.";
    private static final String INVALID_DATE = "La fecha/hora de inicio debe ser mayor o igual a la fecha/hora actual.";

    private final PartidoRepository partidoRepository;
    private final PartidoMapper partidoMapper;

    private static final Map<Integer, String> descriptions = new HashMap<>();

    static {
        descriptions.put(SCORE_ADV, "Adv");
        descriptions.put(SCORE_40, "40");
        descriptions.put(SCORE_30, "30");
        descriptions.put(SCORE_15, "15");
        descriptions.put(SCORE_0, "0");
    }

    @Autowired
    public PartidoServiceImpl(PartidoRepository partidoRepository, PartidoMapper partidoMapper) {
        this.partidoRepository = partidoRepository;
        this.partidoMapper = partidoMapper;
    }

    @Override
    public List<PartidoDTO> listAll() {
        return partidoRepository.findAll()
                .stream()
                .map(this.partidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PartidoDTO getById(Long id) {
        return partidoRepository.findById(id).map(this.partidoMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException(PARTIDO_WITH_ID + id + DOES_NOT_EXIST));
    }

    private void validarJugadores(PartidoDTO partido) {
        JugadorDTO jugadorLocal = partido.getJugadorLocal();
        JugadorDTO jugadorVisitante = partido.getJugadorVisitante();

        if (jugadorLocal != null && jugadorVisitante != null) {
            if (jugadorLocal.getId().equals(jugadorVisitante.getId())) {
                throw new IllegalArgumentException(PLAYER_DUPLICATED);
            }
        } else {
            throw new IllegalArgumentException(PLAYER_MISSING);
        }
    }

    private void validarFechaYHora(PartidoDTO partido) {
        Date now = new Date();
        if (partido.getFechaComienzo().compareTo(now) < 0) {
            throw new IllegalArgumentException(INVALID_DATE);
        }
    }

    private void validarNuevoPartido(PartidoDTO partido) {
        boolean exists = partido.getId() != null && partidoRepository.existsById(partido.getId());
        if (exists) {
            throw new IllegalArgumentException(PARTIDO_WITH_ID + partido.getId() + ALREADY_EXISTS);
        }
        this.validarJugadores(partido);
        this.validarFechaYHora(partido);
    }

    @Override
    public PartidoDTO save(PartidoDTO partido) {
        this.validarNuevoPartido(partido);
        return this.partidoMapper.toDTO(partidoRepository.save(this.partidoMapper.fromDTO(partido)));
    }

    private void validarPartidoNoIniciado(Long id) {
        PartidoDTO partido = this.getById(id);
        if (!Estado.NO_INICIADO.equals(partido.getEstado())) {
            throw new IllegalArgumentException(PARTIDO_WITH_ID + partido.getId() + ALREADY_IN_PROGRESS);
        }
    }

    private void validarPartidoEditado(PartidoDTO partido) {
        boolean exists = partidoRepository.existsById(partido.getId());
        if (!exists) {
            throw new NoSuchElementException(PARTIDO_WITH_ID + partido.getId() + DOES_NOT_EXIST);
        }
        this.validarPartidoNoIniciado(partido.getId());
        this.validarJugadores(partido);
        this.validarFechaYHora(partido);
    }

    @Override
    public PartidoDTO update(PartidoDTO partido) {
        this.validarPartidoEditado(partido);
        return this.partidoMapper.toDTO(partidoRepository.save(this.partidoMapper.fromDTO(partido)));
    }

    private void validarPartidoEliminado(Long id) {
        boolean exists = partidoRepository.existsById(id);
        if (!exists) {
            throw new NoSuchElementException(PARTIDO_WITH_ID + id + DOES_NOT_EXIST);
        }
        this.validarPartidoNoIniciado(id);
    }

    @Override
    public void delete(Long id) {
        this.validarPartidoEliminado(id);
        partidoRepository.deleteById(id);
    }

    private String translateScore(int puntos) {
        return descriptions.get(puntos);
    }

    private void gameLocal(Partido partido) {
        partido.setScoreLocal(0);
        partido.setScoreVisitante(0);
        partido.setPuntosGameActualLocal(this.translateScore(partido.getScoreLocal()));
        partido.setPuntosGameActualVisitante(this.translateScore(partido.getScoreVisitante()));

        partido.setCantidadGamesLocal(partido.getCantidadGamesLocal() + 1);
        if (partido.getCantidadGamesLocal() == 6) {
            partido.setEstado(Estado.FINALIZADO);
        }
    }

    private void gameVisitante(Partido partido) {
        partido.setScoreLocal(0);
        partido.setScoreVisitante(0);
        partido.setPuntosGameActualLocal(this.translateScore(partido.getScoreLocal()));
        partido.setPuntosGameActualVisitante(this.translateScore(partido.getScoreVisitante()));

        partido.setCantidadGamesVisitante(partido.getCantidadGamesVisitante() + 1);
        if (partido.getCantidadGamesVisitante() == 6) {
            partido.setEstado(Estado.FINALIZADO);
        }
    }

    @Override
    public void initGame(Long id) {
        Optional<Partido> optPartido = partidoRepository.findById(id);
        if (optPartido.isPresent()) {
            validarPartidoNoIniciado(id);
            Partido partido = optPartido.get();
            partido.setEstado(Estado.EN_CURSO);
            partidoRepository.save(partido);
        } else {
            throw new NoSuchElementException(PARTIDO_WITH_ID + id + DOES_NOT_EXIST);
        }
    }

    @Override
    public PartidoDTO sumarPuntos(Long id, ModoJugador modo) {
        Optional<Partido> optPartido = partidoRepository.findById(id);
        if (optPartido.isPresent()) {
            Partido partido = optPartido.get();

            if (!Estado.EN_CURSO.equals(partido.getEstado())) {
                throw new IllegalArgumentException(PARTIDO_WITH_ID + partido.getId() + NOT_IN_PROGRESS);
            }

            if (modo == ModoJugador.LOCAL) {
                if (partido.getScoreVisitante() == SCORE_ADV) {
                    if (partido.getScoreLocal() != SCORE_40) {
                        throw new IllegalArgumentException(SCORE_IMPOSIBLE);
                    }
                    partido.setScoreVisitante(partido.getScoreVisitante() - 1);
                } else {
                    if (partido.getScoreLocal() == SCORE_ADV && partido.getScoreVisitante() != SCORE_40) {
                        throw new IllegalArgumentException(SCORE_IMPOSIBLE);
                    }
                    partido.setScoreLocal(partido.getScoreLocal() + 1);
                }
            } else {
                if (partido.getScoreLocal() == SCORE_ADV) {
                    if (partido.getScoreVisitante() != SCORE_40) {
                        throw new IllegalArgumentException(SCORE_IMPOSIBLE);
                    }
                    partido.setScoreLocal(partido.getScoreLocal() - 1);
                } else {
                    if (partido.getScoreVisitante() == SCORE_ADV && partido.getScoreLocal() != SCORE_40) {
                        throw new IllegalArgumentException(SCORE_IMPOSIBLE);
                    }
                    partido.setScoreVisitante(partido.getScoreVisitante() + 1);
                }
            }
            this.actualizarScore(partido);
            return this.partidoMapper.toDTO(partidoRepository.save(partido));
        } else {
            throw new NoSuchElementException(PARTIDO_WITH_ID + id + DOES_NOT_EXIST);
        }
    }

    private void actualizarScore(Partido partido) {
        partido.setPuntosGameActualLocal(this.translateScore(partido.getScoreLocal()));
        partido.setPuntosGameActualVisitante(this.translateScore(partido.getScoreVisitante()));

        if (Math.abs(partido.getScoreLocal() - partido.getScoreVisitante()) >= 2) {
            if (partido.getScoreLocal() > partido.getScoreVisitante() && partido.getScoreLocal() >= SCORE_ADV) {
                this.gameLocal(partido);
            } else if (partido.getScoreVisitante() > partido.getScoreLocal() && partido.getScoreVisitante() >= SCORE_ADV) {
                this.gameVisitante(partido);
            }
        }

    }

}
