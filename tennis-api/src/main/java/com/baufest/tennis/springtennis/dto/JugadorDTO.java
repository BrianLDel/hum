package com.baufest.tennis.springtennis.dto;

import org.json.JSONObject;


public class JugadorDTO {

	private Long id;

	private String nombre;

	private int puntos;

	private EntrenadorDTO entrenador;

	public JugadorDTO(String nombre, int puntos, EntrenadorDTO entrenador) {
		this.nombre = nombre;
		this.puntos = puntos;
		this.entrenador = entrenador;
	}

	public JugadorDTO(Long id, String nombre, int puntos, EntrenadorDTO entrenador) {
		this.id = id;
		this.nombre = nombre;
		this.puntos = puntos;
		this.entrenador = entrenador;
	}

	public JugadorDTO(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public EntrenadorDTO getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(EntrenadorDTO entrenador) {
		this.entrenador = entrenador;
	}

	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("id",getId());
		jo.put("nombre",getNombre());
		jo.put("puntos",getPuntos());
		jo.put("entrenador",getEntrenador());
		return jo;
	}

}
