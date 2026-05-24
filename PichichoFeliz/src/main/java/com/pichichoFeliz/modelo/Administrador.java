package com.pichichoFeliz.modelo;

public class Administrador extends Usuario {

	public Administrador() {
	}

	public Administrador(int idUsuario, String nombre, String rol) {
		super(idUsuario, nombre, rol);
		// TODO Auto-generated constructor stub
	}

	public Administrador(String nombre, String rol) {
		super(nombre, rol);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String obtenerPermisos() {
		return "El administrador puede gestionar clientes, mascotas, citas, pagos, inventario y reportes.";
	}

}
