package com.pichichoFeliz.modelo;

public abstract class  Usuario {

	private int idUsuario;
	private String nombre;
	private String rol;
	
	
	public Usuario() {}


	public Usuario(int idUsuario, String nombre, String rol) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.rol = rol;
	}


	public Usuario(String nombre, String rol) {
		super();
		this.nombre = nombre;
		this.rol = rol;
	}


	public int getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getRol() {
		return rol;
	}


	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	public abstract String obtenerPermisos();
	
}
