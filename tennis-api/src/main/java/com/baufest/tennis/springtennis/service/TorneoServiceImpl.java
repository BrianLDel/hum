package com.baufest.tennis.springtennis.service;

import com.baufest.tennis.springtennis.dto.TorneoDTO;
import com.baufest.tennis.springtennis.mapper.TorneoMapper;
import com.baufest.tennis.springtennis.repository.TorneoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TorneoServiceImpl implements TorneoService {

	public static final String FIELD_WITH_ID = "Torneo con id = ";
	public static final String DOES_NOT_EXIST = " does not exist.";
	public static final String ALREADY_EXISTS = " already exists.";
	private final TorneoRepository torneoRepository;
	private final TorneoMapper torneoMapper;

	@Autowired
	public TorneoServiceImpl(TorneoRepository torneoRepository, TorneoMapper torneoMapper) {
		this.torneoRepository = torneoRepository;
		this.torneoMapper = torneoMapper;
	}

	@Override
	public List<TorneoDTO> listAll() {
		return torneoRepository.findAll()
				.stream()
				.map(this.torneoMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public TorneoDTO getById(Long id) {
		return torneoMapper.toDTO(torneoRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(FIELD_WITH_ID + id + DOES_NOT_EXIST)));
	}

	@Override
	public TorneoDTO save(TorneoDTO torneo) {
		boolean exists = torneo.getId() != null && torneoRepository.existsById(torneo.getId());
		if (exists) {
			throw new IllegalArgumentException(FIELD_WITH_ID + torneo.getId() + ALREADY_EXISTS);
		}
		return this.torneoMapper
				.toDTO(torneoRepository.save(this.torneoMapper.fromDTO(torneo)));
	}

	@Override
	public TorneoDTO update(TorneoDTO torneo) {
		boolean exists = torneoRepository.existsById(torneo.getId());
		if (!exists) {
			throw new NoSuchElementException(FIELD_WITH_ID + torneo.getId() + DOES_NOT_EXIST);
		}
		return this.torneoMapper
				.toDTO(torneoRepository.save(this.torneoMapper.fromDTO(torneo)));
	}

	@Override
	public void delete(Long id) {
		boolean exists = torneoRepository.existsById(id);
		if (!exists) {
			throw new NoSuchElementException(FIELD_WITH_ID + id + DOES_NOT_EXIST);
		}
		torneoRepository.deleteById(id);
	}
}
