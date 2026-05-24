package com.pichichoFeliz.modelo;

public class Cita {

	private int idCita;
	private int idCliente;
	private int idMascota;
	private String fecha;
	private String hora;

	public Cita() {
	}

	public Cita(int idCita, int idCliente, int idMascota, String fecha, String hora) {
		this.idCita = idCita;
		this.idCliente = idCliente;
		this.idMascota = idMascota;
		this.fecha = fecha;
		this.hora = hora;
	}

	public Cita(int idCliente, int idMascota, String fecha, String hora) {
		this.idCliente = idCliente;
		this.idMascota = idMascota;
		this.fecha = fecha;
		this.hora = hora;
	}

	public int getIdCita() {
		return idCita;
	}

	public void setIdCita(int idCita) {
		this.idCita = idCita;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdMascota() {
		return idMascota;
	}

	public void setIdMascota(int idMascota) {
		this.idMascota = idMascota;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
}
