package com.pichichoFeliz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pichichoFeliz.conexion.ConexionBD;

public class ReporteDAO {

	public Object[] obtenerDatosCita(int idCita) throws SQLException {
		String sql = "SELECT ci.id_cita, cl.nombre AS cliente, m.nombre AS mascota, ci.fecha, ci.hora "
				   + "FROM cita ci "
				   + "INNER JOIN cliente cl ON ci.id_cliente = cl.id_cliente "
				   + "INNER JOIN mascota m ON ci.id_mascota = m.id_mascota "
				   + "WHERE ci.id_cita = ?";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setInt(1, idCita);

		ResultSet resultado = statement.executeQuery();

		Object[] datos = null;

		if (resultado.next()) {
			datos = new Object[] {
				resultado.getInt("id_cita"),
				resultado.getString("cliente"),
				resultado.getString("mascota"),
				resultado.getString("fecha"),
				resultado.getString("hora")
			};
		}

		resultado.close();
		statement.close();
		conexion.close();

		return datos;
	}
}