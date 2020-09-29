package com.baufest.tennis.springtennis.dto;

import org.json.JSONObject;

public class JugadorGananciaDTO {

	private Long idJugador;

	private String nombre;

	private int ganancia;

	public JugadorGananciaDTO(Long idJugador, String nombre, int ganancia) {
		this.idJugador = idJugador;
		this.nombre = nombre;
		this.ganancia = ganancia;
	}

	public JugadorGananciaDTO(){}

	public Long getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(Long idJugador) {
		this.idJugador = idJugador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getGanancia() {
		return ganancia;
	}

	public void setGanancia(int ganancia) {
		this.ganancia = ganancia;
	}

	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("idJugador",getIdJugador());
		jo.put("nombre",getNombre());
		jo.put("ganancia",getGanancia());
		return jo;
	}
}
