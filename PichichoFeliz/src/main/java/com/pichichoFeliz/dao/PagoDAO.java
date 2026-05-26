package com.pichichoFeliz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pichichoFeliz.conexion.ConexionBD;
import com.pichichoFeliz.modelo.Pago;

public class PagoDAO {

	public void guardarPago(Pago pago) throws SQLException {
		String sql = "INSERT INTO pago (id_cita, monto, metodo) VALUES (?, ?, ?)";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setInt(1, pago.getIdCita());
		statement.setDouble(2, pago.getMonto());
		statement.setString(3, pago.getMetodo());

		statement.executeUpdate();

		statement.close();
		conexion.close();
	}

	public List<Object[]> listarPagos() throws SQLException {
		List<Object[]> pagos = new ArrayList<>();

		String sql = "SELECT p.id_pago, p.id_cita, cl.nombre AS cliente, m.nombre AS mascota, "
				   + "ci.fecha, p.monto, p.metodo "
				   + "FROM pago p "
				   + "INNER JOIN cita ci ON p.id_cita = ci.id_cita "
				   + "INNER JOIN cliente cl ON ci.id_cliente = cl.id_cliente "
				   + "INNER JOIN mascota m ON ci.id_mascota = m.id_mascota";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Object[] fila = {
				resultado.getInt("id_pago"),
				resultado.getInt("id_cita"),
				resultado.getString("cliente"),
				resultado.getString("mascota"),
				resultado.getString("fecha"),
				resultado.getDouble("monto"),
				resultado.getString("metodo")
			};

			pagos.add(fila);
		}

		resultado.close();
		statement.close();
		conexion.close();

		return pagos;
	}

	public List<Object[]> buscarPagosPorIdCita(int idCita) throws SQLException {
		List<Object[]> pagos = new ArrayList<>();

		String sql = "SELECT p.id_pago, p.id_cita, cl.nombre AS cliente, m.nombre AS mascota, "
				   + "ci.fecha, p.monto, p.metodo "
				   + "FROM pago p "
				   + "INNER JOIN cita ci ON p.id_cita = ci.id_cita "
				   + "INNER JOIN cliente cl ON ci.id_cliente = cl.id_cliente "
				   + "INNER JOIN mascota m ON ci.id_mascota = m.id_mascota "
				   + "WHERE p.id_cita = ?";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setInt(1, idCita);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Object[] fila = {
				resultado.getInt("id_pago"),
				resultado.getInt("id_cita"),
				resultado.getString("cliente"),
				resultado.getString("mascota"),
				resultado.getString("fecha"),
				resultado.getDouble("monto"),
				resultado.getString("metodo")
			};

			pagos.add(fila);
		}

		resultado.close();
		statement.close();
		conexion.close();

		return pagos;
	}
}