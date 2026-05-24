package com.pichichoFeliz.vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.pichichoFeliz.dao.ClienteDAO;
import com.pichichoFeliz.modelo.Cliente;

public class GestionClientesFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionClientesFrame frame = new GestionClientesFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GestionClientesFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Gestion Clientes");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 22));
		lblNewLabel.setBounds(178, 10, 184, 28);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nombre\r\n:");
		lblNewLabel_1.setBounds(21, 67, 44, 12);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Telefono:");
		lblNewLabel_1_1.setBounds(21, 94, 44, 12);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Dirección:");
		lblNewLabel_1_2.setBounds(21, 122, 56, 12);
		contentPane.add(lblNewLabel_1_2);

		textField = new JTextField();
		textField.setBounds(76, 64, 96, 18);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(76, 91, 96, 18);
		contentPane.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(76, 119, 96, 18);
		contentPane.add(textField_2);

		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = textField.getText();
				String telefono = textField_1.getText();
				String direccion = textField_2.getText();

				if (nombre.isBlank() || telefono.isBlank() || direccion.isBlank()) {
					JOptionPane.showMessageDialog(null, "Debe completar todos los campos.");
					return;
				}
				
				if (!telefonoValido(telefono)) {
					JOptionPane.showMessageDialog(null, "El teléfono debe contener solo números y tener entre 7 y 15 dígitos.");
					return;
				}
				try {
					Cliente cliente = new Cliente(nombre, telefono, direccion);

					ClienteDAO clienteDAO = new ClienteDAO();
					clienteDAO.guardarCliente(cliente);

					JOptionPane.showMessageDialog(null, "Cliente guardado correctamente.");

					cargarClientes();

					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al guardar cliente: " + ex.getMessage());
				}
			}

		});
		btnNewButton.setBounds(21, 158, 84, 20);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Buscar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nombreBuscado = textField.getText();

				if (nombreBuscado.isBlank()) {
					cargarClientes();
					return;
				}

				try {
					ClienteDAO clienteDAO = new ClienteDAO();
					List<Object[]> clientes = clienteDAO.buscarClientesPorNombre(nombreBuscado);

					mostrarClientesEnTabla(clientes);

					if (clientes.isEmpty()) {
						JOptionPane.showMessageDialog(null, "No se encontraron clientes.");
					}

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al buscar cliente: " + ex.getMessage());
				}
			}
		});
		btnNewButton_1.setBounds(114, 158, 84, 20);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Editar");
		btnNewButton_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		        int filaSeleccionada = table.getSelectedRow();

		        if (filaSeleccionada < 0) {
		            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente de la tabla.");
		            return;
		        }

		        int idCliente = Integer.parseInt(table.getValueAt(filaSeleccionada, 0).toString());

		        String nombre = textField.getText();
		        String telefono = textField_1.getText();
		        String direccion = textField_2.getText();

		        if (nombre.isBlank() || telefono.isBlank() || direccion.isBlank()) {
		            JOptionPane.showMessageDialog(null, "Debe completar todos los campos.");
		            return;
		        }

		        try {
		            ClienteDAO clienteDAO = new ClienteDAO();
		            clienteDAO.editarCliente(idCliente, nombre, telefono, direccion);

		            JOptionPane.showMessageDialog(null, "Cliente editado correctamente.");

		            cargarClientes();

		            textField.setText("");
		            textField_1.setText("");
		            textField_2.setText("");

		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Error al editar cliente: " + ex.getMessage());
		        }
		    }
		});
		btnNewButton_2.setBounds(21, 188, 84, 20);
		contentPane.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Limpiar");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");

				cargarClientes();
			}
		});
		btnNewButton_3.setBounds(114, 188, 84, 20);
		contentPane.add(btnNewButton_3);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 216, 335, 133);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nombre", "Teléfono", "Dirección" }));
		scrollPane.setViewportView(table);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int filaSeleccionada = table.getSelectedRow();

				if (filaSeleccionada >= 0) {
					textField.setText(table.getValueAt(filaSeleccionada, 1).toString());
					textField_1.setText(table.getValueAt(filaSeleccionada, 2).toString());
					textField_2.setText(table.getValueAt(filaSeleccionada, 3).toString());
				}
			}
		});

		JButton btnNewButton_4 = new JButton("Volver");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPrincipal menu = new MenuPrincipal();
				menu.setVisible(true);
				dispose();
			}
		});
		btnNewButton_4.setBounds(443, 329, 84, 20);
		contentPane.add(btnNewButton_4);

		cargarClientes();
	}

	private void cargarClientes() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			List<Object[]> clientes = clienteDAO.listarClientes();

			mostrarClientesEnTabla(clientes);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e.getMessage());
		}
	}

	private void mostrarClientesEnTabla(List<Object[]> clientes) {
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("ID");
		modelo.addColumn("Nombre");
		modelo.addColumn("Teléfono");
		modelo.addColumn("Dirección");

		for (Object[] cliente : clientes) {
			modelo.addRow(cliente);
		}

		table.setModel(modelo);
	}
	
	private boolean telefonoValido(String telefono) {
		return telefono.matches("\\d{7,15}");
	}
}
