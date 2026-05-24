package com.pichichoFeliz.modelo;

public class Estilista extends Usuario {

	public Estilista() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Estilista(int idUsuario, String nombre, String rol) {
		super(idUsuario, nombre, rol);
		// TODO Auto-generated constructor stub
	}

	public Estilista(String nombre, String rol) {
		super(nombre, rol);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String obtenerPermisos() {
		return "El estilista puede consultar citas y registrar el historial de servicios de las mascotas.";
	}

}
