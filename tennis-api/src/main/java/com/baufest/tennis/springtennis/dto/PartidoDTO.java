package com.baufest.tennis.springtennis.dto;

import com.baufest.tennis.springtennis.enums.Estado;
import org.json.JSONObject;

import java.util.Date;

public class PartidoDTO {

	private Long id;

	private Date fechaComienzo;

	private Estado estado;

	private JugadorDTO jugadorLocal;

	private JugadorDTO jugadorVisitante;

	private int scoreLocal;

	private String puntosGameActualLocal;

	private int cantidadGamesLocal;

	private int scoreVisitante;

	private String puntosGameActualVisitante;

	private int cantidadGamesVisitante;

	private CanchaDTO cancha;

	public PartidoDTO(Date fechaComienzo, Estado estado, JugadorDTO jugadorLocal, JugadorDTO jugadorVisitante, CanchaDTO cancha) {
		this.fechaComienzo = fechaComienzo;
		this.estado = estado;
		this.jugadorLocal = jugadorLocal;
		this.jugadorVisitante = jugadorVisitante;
		this.scoreLocal = 0;
		this.puntosGameActualLocal = "0";
		this.cantidadGamesLocal = 0;
		this.scoreVisitante = 0;
		this.puntosGameActualVisitante = "0";
		this.cantidadGamesVisitante = 0;
		this.cancha = cancha;
	}

	public PartidoDTO(Long id, Date fechaComienzo, Estado estado, JugadorDTO jugadorLocal, JugadorDTO jugadorVisitante, int scoreLocal, int cantidadGamesLocal, int scoreVisitante, int cantidadGamesVisitante, CanchaDTO cancha) {
		this.id = id;
		this.fechaComienzo = fechaComienzo;
		this.estado = estado;
		this.jugadorLocal = jugadorLocal;
		this.jugadorVisitante = jugadorVisitante;
		this.scoreLocal = scoreLocal;
		this.cantidadGamesLocal = cantidadGamesLocal;
		this.scoreVisitante = scoreVisitante;
		this.cantidadGamesVisitante = cantidadGamesVisitante;
		this.cancha = cancha;
	}

	public PartidoDTO(Date fechaComienzo, Estado estado, JugadorDTO jugadorLocal, JugadorDTO jugadorVisitante, int scoreLocal,
					  String puntosGameActualLocal, int cantidadGamesLocal, int scoreVisitante, String puntosGameActualVisitante,
					  int cantidadGamesVisitante, CanchaDTO cancha) {
		this.fechaComienzo = fechaComienzo;
		this.estado = estado;
		this.jugadorLocal = jugadorLocal;
		this.jugadorVisitante = jugadorVisitante;
		this.scoreLocal = scoreLocal;
		this.puntosGameActualLocal = puntosGameActualLocal;
		this.cantidadGamesLocal = cantidadGamesLocal;
		this.scoreVisitante = scoreVisitante;
		this.puntosGameActualVisitante = puntosGameActualVisitante;
		this.cantidadGamesVisitante = cantidadGamesVisitante;
		this.cancha = cancha;
	}

	public PartidoDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaComienzo() {
		return fechaComienzo;
	}

	public void setFechaComienzo(Date fechaComienzo) {
		this.fechaComienzo = fechaComienzo;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public JugadorDTO getJugadorLocal() {
		return jugadorLocal;
	}

	public void setJugadorLocal(JugadorDTO jugadorLocal) {
		this.jugadorLocal = jugadorLocal;
	}

	public JugadorDTO getJugadorVisitante() {
		return jugadorVisitante;
	}

	public void setJugadorVisitante(JugadorDTO jugadorVisitante) {
		this.jugadorVisitante = jugadorVisitante;
	}

	public int getScoreLocal() {
		return scoreLocal;
	}

	public void setScoreLocal(int scoreLocal) {
		this.scoreLocal = scoreLocal;
	}

	public String getPuntosGameActualLocal() {
		return puntosGameActualLocal;
	}

	public void setPuntosGameActualLocal(String puntosGameActualLocal) {
		this.puntosGameActualLocal = puntosGameActualLocal;
	}

	public int getCantidadGamesLocal() {
		return cantidadGamesLocal;
	}

	public void setCantidadGamesLocal(int cantidadGamesLocal) {
		this.cantidadGamesLocal = cantidadGamesLocal;
	}

	public int getScoreVisitante() {
		return scoreVisitante;
	}

	public void setScoreVisitante(int scoreVisitante) {
		this.scoreVisitante = scoreVisitante;
	}

	public String getPuntosGameActualVisitante() {
		return puntosGameActualVisitante;
	}

	public void setPuntosGameActualVisitante(String puntosGameActualVisitante) {
		this.puntosGameActualVisitante = puntosGameActualVisitante;
	}

	public int getCantidadGamesVisitante() {
		return cantidadGamesVisitante;
	}

	public void setCantidadGamesVisitante(int cantidadGamesVisitante) {
		this.cantidadGamesVisitante = cantidadGamesVisitante;
	}

	public CanchaDTO getCancha() {
		return cancha;
	}

	public void setCancha(CanchaDTO cancha) {
		this.cancha = cancha;
	}


	@Override
	public String toString() {
		return "partido [id=" + id + ", fechaComienzo=" + fechaComienzo + ", estado=" + estado + ", jugadorLocal="
				+ jugadorLocal + ", jugadorVisitante=" + jugadorVisitante + ", scoreLocal=" + scoreLocal
				+ ", puntosGameActualLocal=" + puntosGameActualLocal + ", cantidadGamesLocal=" + cantidadGamesLocal
				+ ", scoreVisitante=" + scoreVisitante + ", puntosGameActualVisitante=" + puntosGameActualVisitante
				+ ", cantidadGamesVisitante=" + cantidadGamesVisitante + "]";
	}

	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("id", getId());
		jo.put("fechaComienzo", getFechaComienzo());
		jo.put("estado", getEstado());
		jo.put("jugadorLocal",getJugadorLocal());
		jo.put("jugadorVisitante",getJugadorVisitante());
		jo.put("scoreLocal", getScoreLocal());
		jo.put("puntosGameActualLocal", getPuntosGameActualLocal());
		jo.put("cantidadGamesLocal", getCantidadGamesLocal());
		jo.put("scoreVisitante", getScoreVisitante());
		jo.put("puntosGameActualVisitante", getPuntosGameActualVisitante());
		jo.put("cantidadGamesVisitante", getCantidadGamesVisitante());
		jo.put("cancha", getCancha().toJSONObject());
		return jo;
	}
}
