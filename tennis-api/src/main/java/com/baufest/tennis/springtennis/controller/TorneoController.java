package com.baufest.tennis.springtennis.controller;

import com.baufest.tennis.springtennis.dto.TorneoDTO;
import com.baufest.tennis.springtennis.service.TorneoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("springtennis/api/v1/torneos")
public class TorneoController {

	TorneoService torneoService;

	@Autowired
	public TorneoController(TorneoService torneoService){
		this.torneoService = torneoService;
	}

	@GetMapping("")
	public ResponseEntity<List<TorneoDTO>> listAll(){
		return ResponseEntity.ok(torneoService.listAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TorneoDTO> getById(@PathVariable Long id){
		return ResponseEntity.ok(torneoService.getById(id));
	}

	@PostMapping (value = "")
	public ResponseEntity<Long> save(@RequestBody TorneoDTO torneo){
		TorneoDTO torneoSaved = torneoService.save(torneo);
		return new ResponseEntity<>(torneoSaved.getId(), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Long> updateTorneo(@PathVariable Long id, @RequestBody TorneoDTO torneo){
		torneo.setId(id);
		TorneoDTO torneoUpdated = torneoService.update(torneo);
		return ResponseEntity.ok(torneoUpdated.getId());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Long> delete(@PathVariable Long id){
		torneoService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

