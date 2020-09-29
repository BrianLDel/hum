package com.baufest.tennis.springtennis.service;

import com.baufest.tennis.springtennis.dto.CanchaDTO;
import com.baufest.tennis.springtennis.dto.EntrenadorDTO;

import java.util.List;

public interface EntrenadorService {
	List<EntrenadorDTO> listAll();

	EntrenadorDTO getById(Long id);

	EntrenadorDTO save(EntrenadorDTO cancha);

	EntrenadorDTO update(EntrenadorDTO cancha);

	void delete(Long id);
}
