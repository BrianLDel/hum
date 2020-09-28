package com.baufest.tennis.springtennis.controller;

import com.baufest.tennis.springtennis.dto.JugadorDTO;
import com.baufest.tennis.springtennis.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("springtennis/api/v1/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    @Autowired
    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @GetMapping("")
    public ResponseEntity<List<JugadorDTO>> listAll() {
        return ResponseEntity.ok(jugadorService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.getById(id));
    }

    @PostMapping(value = "")
    public ResponseEntity<Long> saveJugador(@RequestBody JugadorDTO jugador) {
        JugadorDTO savedJugador = jugadorService.save(jugador);
        return new ResponseEntity<>(savedJugador.getId(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Long> updateJugador(@PathVariable Long id, @RequestBody JugadorDTO jugador) {
        jugador.setId(id);
        JugadorDTO updatedJugador = jugadorService.update(jugador);
        return ResponseEntity.ok(updatedJugador.getId());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteJugador(@PathVariable Long id) {
        jugadorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/actions/recalculateRanking")
    public ResponseEntity<Void> recalculateRanking(@PathVariable Long id) {
        jugadorService.recalculateRanking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
	


