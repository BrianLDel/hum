package com.baufest.tennis.springtennis.repository;

import com.baufest.tennis.springtennis.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {

}
