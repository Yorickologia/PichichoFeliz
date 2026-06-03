package com.pichichoFeliz.modelo;

public class Producto {

	private int idProducto;
	private String nombre;
	private int cantidad;

	public Producto() {
	}

	public Producto(int idProducto, String nombre, int cantidad) {
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	public Producto(String nombre, int cantidad) {
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}