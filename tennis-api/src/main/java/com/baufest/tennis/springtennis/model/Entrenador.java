package com.baufest.tennis.springtennis.model;

import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Entrenador {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String nombre;

	public Entrenador(String nombre) {
		this.nombre = nombre;
	}

	public Entrenador(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Entrenador(){}

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

	@Override
	public String toString() {
		return "Entrenador [id=" + id + ", nombre=" + nombre + "]";
	}

	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("id",getId());
		jo.put("nombre",getNombre());
		return jo;
	}
}
