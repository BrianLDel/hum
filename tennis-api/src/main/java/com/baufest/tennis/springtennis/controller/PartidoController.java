package com.baufest.tennis.springtennis.controller;

import com.baufest.tennis.springtennis.dto.PartidoDTO;
import com.baufest.tennis.springtennis.enums.ModoJugador;
import com.baufest.tennis.springtennis.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("springtennis/api/v1/partidos")
public class PartidoController {

    private final PartidoService partidoService;

    @Autowired
    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @GetMapping("")
    public ResponseEntity<List<PartidoDTO>> listAll() {
        return ResponseEntity.ok(partidoService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoDTO> getById(@PathVariable Long id) {
        PartidoDTO partido = partidoService.getById(id);
        return ResponseEntity.ok(partido);
    }

    @PostMapping(value = "")
    public ResponseEntity<Long> savePartido(@RequestBody PartidoDTO partido) {
        PartidoDTO savedPartido = partidoService.save(partido);
        return new ResponseEntity<>(
                savedPartido.getId(),
                HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Long> updatePartido(@PathVariable Long id, @RequestBody PartidoDTO partido) {
        partido.setId(id);
        PartidoDTO updatedPartido = partidoService.update(partido);
        return ResponseEntity.ok(updatedPartido.getId());
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePartido(@PathVariable Long id) {
        ResponseEntity<Void> response;
        partidoService.delete(id);
        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @PostMapping(value = "/{id}/actions/sumar-punto")
    public ResponseEntity<PartidoDTO> sumarPuntos(@PathVariable Long id, @RequestParam ModoJugador modoJugador) {
        PartidoDTO partido = partidoService.sumarPuntos(id, modoJugador);
        return new ResponseEntity<>(partido, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/actions/init")
    public ResponseEntity<Void> initGame(@PathVariable Long id) {
        partidoService.initGame(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
