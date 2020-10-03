package com.baufest.tennis.springtennis.mapper;

import com.baufest.tennis.springtennis.dto.TorneoDTO;
import com.baufest.tennis.springtennis.model.Torneo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TorneoMapper {
	TorneoMapper INSTANCE = Mappers.getMapper(TorneoMapper.class);

	TorneoDTO toDTO(Torneo entity);
	Torneo fromDTO(TorneoDTO entity);
}
