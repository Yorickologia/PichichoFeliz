package com.pichichoFeliz.modelo;

public class Mascota {

	private int idMascota;
	private int idCliente;
	private String nombre;
	private String raza;
	private int edad;
	
	
	public Mascota() {

	}


	public Mascota(int idMascota, int idCliente, String nombre, String raza, int edad) {
		super();
		this.idMascota = idMascota;
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.raza = raza;
		this.edad = edad;
	}


	public Mascota(int idCliente, String nombre, String raza, int edad) {
		super();
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.raza = raza;
		this.edad = edad;
	}


	public int getIdMascota() {
		return idMascota;
	}


	public void setIdMascota(int idMascota) {
		this.idMascota = idMascota;
	}


	public int getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getRaza() {
		return raza;
	}


	public void setRaza(String raza) {
		this.raza = raza;
	}


	public int getEdad() {
		return edad;
	}


	public void setEdad(int edad) {
		this.edad = edad;
	}
	
	
	
}
