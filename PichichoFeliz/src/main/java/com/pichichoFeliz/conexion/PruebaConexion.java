package com.pichichoFeliz.conexion;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaConexion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            Connection conexion = ConexionBD.obtenerConexion();
            System.out.println("Conexión exitosa a la base de datos PichichoFeliz.");
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            System.out.println(e.getMessage());
        }
	}

}
