package com.baufest.tennis.springtennis.service;

import java.util.List;

import com.baufest.tennis.springtennis.dto.PartidoDTO;
import com.baufest.tennis.springtennis.enums.ModoJugador;
import com.baufest.tennis.springtennis.model.Partido;

public interface PartidoService {
	List<PartidoDTO> listAll();

	PartidoDTO getById(Long id);
	
	void delete(Long id);

    PartidoDTO save(PartidoDTO partido);

	PartidoDTO update(PartidoDTO partido);

	void initGame(Long id);

	PartidoDTO sumarPuntos(Long id, ModoJugador modo);
}