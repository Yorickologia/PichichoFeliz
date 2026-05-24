package com.pichichoFeliz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pichichoFeliz.conexion.ConexionBD;
import com.pichichoFeliz.modelo.Mascota;

public class MascotaDAO {

	public void guardarMascota(Mascota mascota) throws SQLException {
		String sql = "INSERT INTO mascota (id_cliente, nombre, raza, edad) VALUES (?, ?, ?, ?)";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setInt(1, mascota.getIdCliente());
		statement.setString(2, mascota.getNombre());
		statement.setString(3, mascota.getRaza());
		statement.setInt(4, mascota.getEdad());

		statement.executeUpdate();

		statement.close();
		conexion.close();
	}

	public List<Object[]> listarMascotas() throws SQLException {
		List<Object[]> mascotas = new ArrayList<>();

		String sql = "SELECT m.id_mascota, c.nombre AS cliente, m.nombre AS mascota, m.raza, m.edad "
				   + "FROM mascota m "
				   + "INNER JOIN cliente c ON m.id_cliente = c.id_cliente";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Object[] fila = {
				resultado.getInt("id_mascota"),
				resultado.getString("cliente"),
				resultado.getString("mascota"),
				resultado.getString("raza"),
				resultado.getInt("edad")
			};

			mascotas.add(fila);
		}

		resultado.close();
		statement.close();
		conexion.close();

		return mascotas;
	}

	public void editarMascota(int idMascota, int idCliente, String nombre, String raza, int edad) throws SQLException {
		String sql = "UPDATE mascota SET id_cliente = ?, nombre = ?, raza = ?, edad = ? WHERE id_mascota = ?";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setInt(1, idCliente);
		statement.setString(2, nombre);
		statement.setString(3, raza);
		statement.setInt(4, edad);
		statement.setInt(5, idMascota);

		statement.executeUpdate();

		statement.close();
		conexion.close();
	}
	
	public List<Object[]> buscarMascotasPorNombre(String nombreBuscado) throws SQLException {
		List<Object[]> mascotas = new ArrayList<>();

		String sql = "SELECT m.id_mascota, c.nombre AS cliente, m.nombre AS mascota, m.raza, m.edad "
				   + "FROM mascota m "
				   + "INNER JOIN cliente c ON m.id_cliente = c.id_cliente "
				   + "WHERE m.nombre LIKE ?";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setString(1, "%" + nombreBuscado + "%");

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Object[] fila = {
				resultado.getInt("id_mascota"),
				resultado.getString("cliente"),
				resultado.getString("mascota"),
				resultado.getString("raza"),
				resultado.getInt("edad")
			};

			mascotas.add(fila);
		}

		resultado.close();
		statement.close();
		conexion.close();

		return mascotas;
	}
}