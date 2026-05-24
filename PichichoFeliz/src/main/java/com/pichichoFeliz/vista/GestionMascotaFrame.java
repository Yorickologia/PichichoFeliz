package com.pichichoFeliz.vista;

import java.awt.Color;
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

import com.pichichoFeliz.dao.MascotaDAO;
import com.pichichoFeliz.modelo.Mascota;

public class GestionMascotaFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionMascotaFrame frame = new GestionMascotaFrame();
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
	public GestionMascotaFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Gestion Mascotas");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(133, 10, 177, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ID cliente:");
		lblNewLabel_1.setBounds(10, 58, 70, 20);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(78, 59, 96, 18);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nombre:");
		lblNewLabel_1_1.setBounds(10, 93, 70, 20);
		contentPane.add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(78, 88, 96, 18);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Raza:");
		lblNewLabel_1_1_1.setBounds(10, 123, 70, 20);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Edad:");
		lblNewLabel_1_1_1_1.setBounds(10, 153, 70, 20);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(78, 124, 96, 18);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(78, 154, 96, 18);
		contentPane.add(textField_3);
		
		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idCliente = Integer.parseInt(textField.getText());
					String nombre = textField_1.getText();
					String raza = textField_2.getText();
					int edad = Integer.parseInt(textField_3.getText());

					if (nombre.isBlank() || raza.isBlank()) {
						JOptionPane.showMessageDialog(null, "Debe completar nombre y raza.");
						return;
					}

					if (edad < 0) {
						JOptionPane.showMessageDialog(null, "La edad no puede ser negativa.");
						return;
					}

					Mascota mascota = new Mascota(idCliente, nombre, raza, edad);

					MascotaDAO mascotaDAO = new MascotaDAO();
					mascotaDAO.guardarMascota(mascota);

					JOptionPane.showMessageDialog(null, "Mascota guardada correctamente.");

					cargarMascotas();

					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
					textField_3.setText("");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "ID Cliente y Edad deben ser números.");
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al guardar mascota: " + ex.getMessage());
				}
			}
		});
		btnNewButton.setBounds(215, 58, 84, 20);
		contentPane.add(btnNewButton);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreBuscado = textField_1.getText();

				if (nombreBuscado.isBlank()) {
					cargarMascotas();
					return;
				}

				try {
					MascotaDAO mascotaDAO = new MascotaDAO();
					List<Object[]> mascotas = mascotaDAO.buscarMascotasPorNombre(nombreBuscado);

					mostrarMascotasEnTabla(mascotas);

					if (mascotas.isEmpty()) {
						JOptionPane.showMessageDialog(null, "No se encontraron mascotas.");
					}

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al buscar mascota: " + ex.getMessage());
				}
			}
		});

		btnBuscar.setBounds(321, 58, 84, 20);
		contentPane.add(btnBuscar);
		
		JButton btnLimpiar = new JButton("Editar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int filaSeleccionada = table.getSelectedRow();

				if (filaSeleccionada < 0) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar una mascota de la tabla.");
					return;
				}

				try {
					int idMascota = Integer.parseInt(table.getValueAt(filaSeleccionada, 0).toString());
					int idCliente = Integer.parseInt(textField.getText());
					String nombre = textField_1.getText();
					String raza = textField_2.getText();
					int edad = Integer.parseInt(textField_3.getText());

					if (nombre.isBlank() || raza.isBlank()) {
						JOptionPane.showMessageDialog(null, "Debe completar nombre y raza.");
						return;
					}

					if (edad < 0) {
						JOptionPane.showMessageDialog(null, "La edad no puede ser negativa.");
						return;
					}

					MascotaDAO mascotaDAO = new MascotaDAO();
					mascotaDAO.editarMascota(idMascota, idCliente, nombre, raza, edad);

					JOptionPane.showMessageDialog(null, "Mascota editada correctamente.");

					cargarMascotas();

					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
					textField_3.setText("");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "ID Cliente y Edad deben ser números.");
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al editar mascota: " + ex.getMessage());
				}
			}
		});

		btnLimpiar.setBounds(215, 93, 84, 20);
		contentPane.add(btnLimpiar);
		
		JButton btnLimpiar_1 = new JButton("Limpiar");
		btnLimpiar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");

				cargarMascotas();
			}
		});

		btnLimpiar_1.setBounds(321, 93, 84, 20);
		contentPane.add(btnLimpiar_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(179, 134, 226, 119);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Cliente", "Mascota", "Raza", "Edad"
			}
		));
		scrollPane.setViewportView(table);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int filaSeleccionada = table.getSelectedRow();

				if (filaSeleccionada >= 0) {
					textField_1.setText(table.getValueAt(filaSeleccionada, 2).toString());
					textField_2.setText(table.getValueAt(filaSeleccionada, 3).toString());
					textField_3.setText(table.getValueAt(filaSeleccionada, 4).toString());
				}
			}
		});
		
		cargarMascotas();
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setForeground(new Color(0, 0, 0));

		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPrincipal menu = new MenuPrincipal();
				menu.setVisible(true);
				dispose();
			}
		});

		btnVolver.setBounds(10, 223, 84, 20);
		contentPane.add(btnVolver);

	}
	private void mostrarMascotasEnTabla(List<Object[]> mascotas) {
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("ID");
		modelo.addColumn("Cliente");
		modelo.addColumn("Mascota");
		modelo.addColumn("Raza");
		modelo.addColumn("Edad");

		for (Object[] mascota : mascotas) {
			modelo.addRow(mascota);
		}

		table.setModel(modelo);
	}
	
	private void cargarMascotas() {
		try {
			MascotaDAO mascotaDAO = new MascotaDAO();
			List<Object[]> mascotas = mascotaDAO.listarMascotas();

			mostrarMascotasEnTabla(mascotas);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar mascotas: " + e.getMessage());
		}
	}
}
