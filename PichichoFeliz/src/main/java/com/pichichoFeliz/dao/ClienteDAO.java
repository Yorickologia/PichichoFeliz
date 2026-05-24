package com.pichichoFeliz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pichichoFeliz.conexion.ConexionBD;
import com.pichichoFeliz.modelo.Cliente;

public class ClienteDAO {
	
//metodos de los clientes
	public void guardarCliente(Cliente cliente) throws SQLException {
	    String sql = "INSERT INTO cliente (nombre, telefono, direccion) VALUES (?, ?, ?)";

	    Connection conexion = ConexionBD.obtenerConexion();
	    PreparedStatement statement = conexion.prepareStatement(sql);

	    statement.setString(1, cliente.getNombre());
	    statement.setString(2, cliente.getTelefono());
	    statement.setString(3, cliente.getDireccion());

	    statement.executeUpdate();

	    statement.close();
	    conexion.close();
	}

public List<Object[]> listarClientes() throws SQLException {
    List<Object[]> clientes = new ArrayList<>();

    String sql = "SELECT id_cliente, nombre, telefono, direccion FROM cliente";

    Connection conexion = ConexionBD.obtenerConexion();
	Statement statement = conexion.createStatement();
    ResultSet resultado = statement.executeQuery(sql);

    while (resultado.next()) {
        Object[] fila = {
            resultado.getInt("id_cliente"),
            resultado.getString("nombre"),
            resultado.getString("telefono"),
            resultado.getString("direccion")
        };

        clientes.add(fila);
    }

    resultado.close();
    statement.close();
    conexion.close();

    return clientes;
}

public List<Object[]> buscarClientesPorNombre(String nombreBuscado) throws SQLException {
    List<Object[]> clientes = new ArrayList<>();

    String sql = "SELECT id_cliente, nombre, telefono FROM cliente WHERE nombre LIKE ?";

    Connection conexion = ConexionBD.obtenerConexion();
    PreparedStatement statement = conexion.prepareStatement(sql);

    statement.setString(1, "%" + nombreBuscado + "%");

    ResultSet resultado = statement.executeQuery();

    while (resultado.next()) {
        Object[] fila = {
            resultado.getInt("id_cliente"),
            resultado.getString("nombre"),
            resultado.getString("telefono")
        };

        clientes.add(fila);
    }

    resultado.close();
    statement.close();
    conexion.close();

    return clientes;
}

public void editarCliente(int idCliente, String nombre, String telefono, String direccion) throws SQLException {
    String sql = "UPDATE cliente SET nombre = ?, telefono = ?, direccion = ? WHERE id_cliente = ?";

    Connection conexion = ConexionBD.obtenerConexion();
    PreparedStatement statement = conexion.prepareStatement(sql);

    statement.setString(1, nombre);
    statement.setString(2, telefono);
    statement.setString(3, direccion);
    statement.setInt(4, idCliente);

    statement.executeUpdate();

    statement.close();
    conexion.close();
}
}
