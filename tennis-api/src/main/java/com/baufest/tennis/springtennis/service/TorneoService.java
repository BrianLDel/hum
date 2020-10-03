package com.baufest.tennis.springtennis.service;

import com.baufest.tennis.springtennis.dto.TorneoDTO;


import java.util.List;

public interface TorneoService {

	List<TorneoDTO> listAll();

	TorneoDTO getById(Long id);

	TorneoDTO save(TorneoDTO torneo);

	TorneoDTO update(TorneoDTO torneo);

	void delete(Long id);
}
