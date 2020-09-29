package com.baufest.tennis.springtennis.controller;

import com.baufest.tennis.springtennis.dto.EntrenadorDTO;
import com.baufest.tennis.springtennis.service.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("springtennis/api/v1/entrenadores")
public class EntrenadorController {
	private final EntrenadorService entrenadorService;

	@Autowired
	public EntrenadorController(EntrenadorService entrenadorService) {
		this.entrenadorService = entrenadorService;
	}

	@GetMapping("")
	public ResponseEntity<List<EntrenadorDTO>> listAll() {
		return ResponseEntity.ok(entrenadorService.listAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntrenadorDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(entrenadorService.getById(id));
	}

	@PostMapping(value = "")
	public ResponseEntity<Long> saveEntrenador(@RequestBody EntrenadorDTO entrenador) {
		EntrenadorDTO entrenadorSaved = entrenadorService.save(entrenador);
		return new ResponseEntity<>(entrenadorSaved.getId(), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Long> updateEntrenador(@PathVariable Long id, @RequestBody EntrenadorDTO entrenador) {
		entrenador.setId(id);
		EntrenadorDTO entrenadorUpdated = entrenadorService.update(entrenador);
		return ResponseEntity.ok(entrenadorUpdated.getId());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteEntrenador(@PathVariable Long id) {
		entrenadorService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
