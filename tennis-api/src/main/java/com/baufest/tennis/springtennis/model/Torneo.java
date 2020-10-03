package com.baufest.tennis.springtennis.model;

import org.json.JSONObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "torneos")
public class Torneo {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( name = "idJugador1",nullable = false)
	private Jugador jugador1;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( name = "idJugador2",nullable = false)
	private Jugador jugador2;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( name = "idJugador3",nullable = false)
	private Jugador jugador3;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( name = "idJugador4",nullable = false)
	private Jugador jugador4;

	@Column(nullable = false)
	private Date fechaComienzo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( name = "idCancha",nullable = true)
	private Cancha cancha;

	public Torneo(Jugador jugador1, Jugador jugador2, Jugador jugador3, Jugador jugador4, Date fechaComienzo, Cancha cancha) {
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.jugador3 = jugador3;
		this.jugador4 = jugador4;
		this.fechaComienzo = fechaComienzo;
		this.cancha = cancha;
	}

	public Torneo(Long id, Jugador jugador1, Jugador jugador2, Jugador jugador3, Jugador jugador4, Date fechaComienzo, Cancha cancha) {
		this.id = id;
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.jugador3 = jugador3;
		this.jugador4 = jugador4;
		this.fechaComienzo = fechaComienzo;
		this.cancha = cancha;
	}

	public Torneo(Jugador jugador1, Jugador jugador2, Jugador jugador3, Jugador jugador4, Date fechaComienzo) {
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.jugador3 = jugador3;
		this.jugador4 = jugador4;
		this.fechaComienzo = fechaComienzo;
		this.cancha = new Cancha("Roland","Paris");
	}

	public Torneo(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Jugador getJugador1() {
		return jugador1;
	}

	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}

	public Jugador getJugador2() {
		return jugador2;
	}

	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}

	public Jugador getJugador3() {
		return jugador3;
	}

	public void setJugador3(Jugador jugador3) {
		this.jugador3 = jugador3;
	}

	public Jugador getJugador4() {
		return jugador4;
	}

	public void setJugador4(Jugador jugador4) {
		this.jugador4 = jugador4;
	}

	public Date getFechaComienzo() {
		return fechaComienzo;
	}

	public void setFechaComienzo(Date fechaComienzo) {
		this.fechaComienzo = fechaComienzo;
	}

	public Cancha getCancha() {
		return cancha;
	}

	public void setCancha(Cancha cancha) {
		this.cancha = cancha;
	}

	@Override
	public String toString() {
		return "partido [id=" + id + ", fechaComienzo=" + fechaComienzo + ", jugador1="+ jugador1+ ", jugador2="
				+ jugador2 + ", jugador3=" + jugador3 + ", jugador4=" + jugador4 + "]";
	}

	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("id", getId());
		jo.put("fechaComienzo", getFechaComienzo());
		jo.put("jugador1", getJugador1());
		jo.put("jugador2", getJugador2());
		jo.put("jugador3", getJugador3());
		jo.put("jugador4", getJugador4());
		jo.put("cancha", getCancha().toJSONObject());
		return jo;
	}
}
