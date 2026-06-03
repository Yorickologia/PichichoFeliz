package com.pichichoFeliz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pichichoFeliz.conexion.ConexionBD;
import com.pichichoFeliz.modelo.Producto;

public class ProductoDAO {

	public void guardarProducto(Producto producto) throws SQLException {
		String sql = "INSERT INTO producto (nombre, cantidad) VALUES (?, ?)";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setString(1, producto.getNombre());
		statement.setInt(2, producto.getCantidad());

		statement.executeUpdate();

		statement.close();
		conexion.close();
	}

	public List<Object[]> listarProductos() throws SQLException {
		List<Object[]> productos = new ArrayList<>();

		String sql = "SELECT id_producto, nombre, cantidad FROM producto";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Object[] fila = {
				resultado.getInt("id_producto"),
				resultado.getString("nombre"),
				resultado.getInt("cantidad")
			};

			productos.add(fila);
		}

		resultado.close();
		statement.close();
		conexion.close();

		return productos;
	}

	public List<Object[]> buscarProductosPorNombre(String nombreBuscado) throws SQLException {
		List<Object[]> productos = new ArrayList<>();

		String sql = "SELECT id_producto, nombre, cantidad FROM producto WHERE nombre LIKE ?";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setString(1, "%" + nombreBuscado + "%");

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Object[] fila = {
				resultado.getInt("id_producto"),
				resultado.getString("nombre"),
				resultado.getInt("cantidad")
			};

			productos.add(fila);
		}

		resultado.close();
		statement.close();
		conexion.close();

		return productos;
	}

	public void editarProducto(int idProducto, String nombre, int cantidad) throws SQLException {
		String sql = "UPDATE producto SET nombre = ?, cantidad = ? WHERE id_producto = ?";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setString(1, nombre);
		statement.setInt(2, cantidad);
		statement.setInt(3, idProducto);

		statement.executeUpdate();

		statement.close();
		conexion.close();
	}
	
	public void eliminarProducto(int idProducto) throws SQLException {
		String sql = "DELETE FROM producto WHERE id_producto = ?";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setInt(1, idProducto);

		statement.executeUpdate();

		statement.close();
		conexion.close();
	}
}