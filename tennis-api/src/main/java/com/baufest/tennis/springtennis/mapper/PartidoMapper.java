package com.baufest.tennis.springtennis.mapper;

import com.baufest.tennis.springtennis.dto.PartidoDTO;
import com.baufest.tennis.springtennis.model.Partido;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CanchaMapper.class,JugadorMapper.class},componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PartidoMapper {
    PartidoMapper INSTANCE = Mappers.getMapper(PartidoMapper.class);

    PartidoDTO toDTO(Partido entity);
    Partido fromDTO(PartidoDTO entity);

}
