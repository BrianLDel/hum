package com.baufest.tennis.springtennis.dto;

import org.json.JSONObject;

import javax.persistence.*;

public class JugadorDTO {

	private Long id;

	private String nombre;

	private int puntos;

	public JugadorDTO(String nombre, int puntos) {
		this.nombre = nombre;
		this.puntos = puntos;
	}

	public JugadorDTO(Long id, String nombre, int puntos) {
		this.id = id;
		this.nombre = nombre;
		this.puntos = puntos;
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

	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("id",getId());
		jo.put("nombre",getNombre());
		jo.put("puntos",getPuntos());
		return jo;
	}

}
