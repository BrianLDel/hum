package com.baufest.tennis.springtennis.mapper;

import com.baufest.tennis.springtennis.dto.EntrenadorDTO;
import com.baufest.tennis.springtennis.model.Entrenador;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntrenadorMapper {
	EntrenadorMapper INSTANCE = Mappers.getMapper(EntrenadorMapper.class);

	EntrenadorDTO toDTO(Entrenador entity);
	Entrenador fromDTO(EntrenadorDTO entity);
}
