package com.pichichoFeliz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pichichoFeliz.conexion.ConexionBD;
import com.pichichoFeliz.modelo.Cita;

public class CitaDAO {

	public void guardarCita(Cita cita) throws SQLException {
		String sql = "INSERT INTO cita (id_cliente, id_mascota, fecha, hora) VALUES (?, ?, ?, ?)";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setInt(1, cita.getIdCliente());
		statement.setInt(2, cita.getIdMascota());
		statement.setString(3, cita.getFecha());
		statement.setString(4, cita.getHora());

		statement.executeUpdate();

		statement.close();
		conexion.close();
	}

	public List<Object[]> listarCitas() throws SQLException {
		List<Object[]> citas = new ArrayList<>();

		String sql = "SELECT ci.id_cita, cl.nombre AS cliente, m.nombre AS mascota, ci.fecha, ci.hora "
				   + "FROM cita ci "
				   + "INNER JOIN cliente cl ON ci.id_cliente = cl.id_cliente "
				   + "INNER JOIN mascota m ON ci.id_mascota = m.id_mascota";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Object[] fila = {
				resultado.getInt("id_cita"),
				resultado.getString("cliente"),
				resultado.getString("mascota"),
				resultado.getString("fecha"),
				resultado.getString("hora")
			};

			citas.add(fila);
		}

		resultado.close();
		statement.close();
		conexion.close();

		return citas;
	}

	public List<Object[]> buscarCitasPorCliente(String nombreBuscado) throws SQLException {
		List<Object[]> citas = new ArrayList<>();

		String sql = "SELECT ci.id_cita, cl.nombre AS cliente, m.nombre AS mascota, ci.fecha, ci.hora "
				   + "FROM cita ci "
				   + "INNER JOIN cliente cl ON ci.id_cliente = cl.id_cliente "
				   + "INNER JOIN mascota m ON ci.id_mascota = m.id_mascota "
				   + "WHERE cl.nombre LIKE ?";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setString(1, "%" + nombreBuscado + "%");

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Object[] fila = {
				resultado.getInt("id_cita"),
				resultado.getString("cliente"),
				resultado.getString("mascota"),
				resultado.getString("fecha"),
				resultado.getString("hora")
			};

			citas.add(fila);
		}

		resultado.close();
		statement.close();
		conexion.close();

		return citas;
	}
	
	public List<Object[]> buscarCitasPorIdCliente(int idCliente) throws SQLException {
		List<Object[]> citas = new ArrayList<>();

		String sql = "SELECT ci.id_cita, cl.nombre AS cliente, m.nombre AS mascota, ci.fecha, ci.hora "
				   + "FROM cita ci "
				   + "INNER JOIN cliente cl ON ci.id_cliente = cl.id_cliente "
				   + "INNER JOIN mascota m ON ci.id_mascota = m.id_mascota "
				   + "WHERE ci.id_cliente = ?";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setInt(1, idCliente);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Object[] fila = {
				resultado.getInt("id_cita"),
				resultado.getString("cliente"),
				resultado.getString("mascota"),
				resultado.getString("fecha"),
				resultado.getString("hora")
			};

			citas.add(fila);
		}

		resultado.close();
		statement.close();
		conexion.close();

		return citas;
	}
	
	public void editarCita(int idCita, int idCliente, int idMascota, String fecha, String hora) throws SQLException {
		String sql = "UPDATE cita SET id_cliente = ?, id_mascota = ?, fecha = ?, hora = ? WHERE id_cita = ?";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setInt(1, idCliente);
		statement.setInt(2, idMascota);
		statement.setString(3, fecha);
		statement.setString(4, hora);
		statement.setInt(5, idCita);

		statement.executeUpdate();

		statement.close();
		conexion.close();
	}
	
	public void editarFechaHoraCita(int idCita, String fecha, String hora) throws SQLException {
		String sql = "UPDATE cita SET fecha = ?, hora = ? WHERE id_cita = ?";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setString(1, fecha);
		statement.setString(2, hora);
		statement.setInt(3, idCita);

		statement.executeUpdate();

		statement.close();
		conexion.close();
	}
}
