package com.baufest.tennis.springtennis.mapper;

import com.baufest.tennis.springtennis.dto.CanchaDTO;
import com.baufest.tennis.springtennis.model.Cancha;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CanchaMapper {
    CanchaMapper INSTANCE = Mappers.getMapper(CanchaMapper.class);

    CanchaDTO toDTO(Cancha entity);
    Cancha fromDTO(CanchaDTO entity);

}
