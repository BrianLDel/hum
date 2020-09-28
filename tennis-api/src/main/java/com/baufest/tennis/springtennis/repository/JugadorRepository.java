package com.baufest.tennis.springtennis.repository;

import com.baufest.tennis.springtennis.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    List<Jugador> findAllByOrderByNombreAsc();

}
