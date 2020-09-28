package com.baufest.tennis.springtennis.mapper;

import com.baufest.tennis.springtennis.dto.JugadorDTO;
import com.baufest.tennis.springtennis.model.Jugador;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface JugadorMapper {
    JugadorMapper INSTANCE = Mappers.getMapper(JugadorMapper.class);

    JugadorDTO toDTO(Jugador entity);
    Jugador fromDTO(JugadorDTO entity);

}
