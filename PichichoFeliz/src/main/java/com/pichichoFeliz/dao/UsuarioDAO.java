package com.pichichoFeliz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pichichoFeliz.conexion.ConexionBD;

public class UsuarioDAO {
	
	public boolean validarUsuario(String nombre, String contrasena, String rol) throws SQLException {
		String sql = "SELECT * FROM usuario WHERE nombre = ? AND contrasena = ? AND rol = ?";

		Connection conexion = ConexionBD.obtenerConexion();
		PreparedStatement statement = conexion.prepareStatement(sql);

		statement.setString(1, nombre);
		statement.setString(2, contrasena);
		statement.setString(3, rol);

		ResultSet resultado = statement.executeQuery();

		boolean existe = resultado.next();

		resultado.close();
		statement.close();
		conexion.close();

		return existe;
	}
	
}
