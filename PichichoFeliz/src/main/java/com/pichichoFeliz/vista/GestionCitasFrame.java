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

import com.pichichoFeliz.dao.CitaDAO;
import com.pichichoFeliz.modelo.Cita;

public class GestionCitasFrame extends JFrame {

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
					GestionCitasFrame frame = new GestionCitasFrame();
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
	public GestionCitasFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Turnos");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(176, 10, 72, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ID Cliente:");
		lblNewLabel_1.setBounds(10, 78, 70, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("ID Mascota:");
		lblNewLabel_1_1.setBounds(10, 100, 70, 20);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Fecha:");
		lblNewLabel_1_1_1.setBounds(10, 121, 70, 20);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Hora:");
		lblNewLabel_1_1_1_1.setBounds(10, 140, 70, 20);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(68, 79, 96, 18);
		contentPane.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(68, 101, 96, 18);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(68, 122, 96, 18);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(68, 141, 96, 18);
		contentPane.add(textField_3);
		
		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idCliente = Integer.parseInt(textField.getText());
					int idMascota = Integer.parseInt(textField_1.getText());
					String fecha = textField_2.getText();
					String hora = textField_3.getText();

					if (fecha.isBlank() || hora.isBlank()) {
						JOptionPane.showMessageDialog(null, "Debe completar fecha y hora.");
						return;
					}

					Cita cita = new Cita(idCliente, idMascota, fecha, hora);

					CitaDAO citaDAO = new CitaDAO();
					citaDAO.guardarCita(cita);

					JOptionPane.showMessageDialog(null, "Cita guardada correctamente.");

					cargarCitas();

					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
					textField_3.setText("");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "ID Cliente e ID Mascota deben ser números.");
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al guardar cita: " + ex.getMessage());
				}
			}
		});
		btnNewButton.setBounds(221, 78, 84, 20);
		contentPane.add(btnNewButton);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idClienteTexto = textField.getText();

				if (idClienteTexto.isBlank()) {
					cargarCitas();
					return;
				}

				try {
					int idCliente = Integer.parseInt(idClienteTexto);

					CitaDAO citaDAO = new CitaDAO();
					List<Object[]> citas = citaDAO.buscarCitasPorIdCliente(idCliente);

					mostrarCitasEnTabla(citas);

					if (citas.isEmpty()) {
						JOptionPane.showMessageDialog(null, "No se encontraron citas para ese cliente.");
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "El ID Cliente debe ser numérico.");
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al buscar cita: " + ex.getMessage());
				}
			}
		});
		btnBuscar.setBounds(320, 78, 84, 20);
		contentPane.add(btnBuscar);
		
		JButton btnLimpiar = new JButton("Editar");
		btnLimpiar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = table.getSelectedRow();

				if (filaSeleccionada < 0) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar una cita de la tabla.");
					return;
				}

				try {
					int idCita = Integer.parseInt(table.getValueAt(filaSeleccionada, 0).toString());
					String fecha = textField_2.getText();
					String hora = textField_3.getText();

					if (fecha.isBlank() || hora.isBlank()) {
						JOptionPane.showMessageDialog(null, "Debe completar fecha y hora.");
						return;
					}

					CitaDAO citaDAO = new CitaDAO();
					citaDAO.editarFechaHoraCita(idCita, fecha, hora);

					JOptionPane.showMessageDialog(null, "Cita editada correctamente.");

					cargarCitas();

					textField_2.setText("");
					textField_3.setText("");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Error al obtener el ID de la cita.");
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al editar cita: " + ex.getMessage());
				}
			}
		});
		btnLimpiar.setBounds(221, 100, 84, 20);
		contentPane.add(btnLimpiar);
		
		JButton btnLimpiar_1 = new JButton("Limpiar");
		btnLimpiar_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnLimpiar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");     // ID Cliente
				textField_1.setText("");   // ID Mascota
				textField_2.setText("");   // Fecha
				textField_3.setText("");   // Hora

				cargarCitas();
			}
		});
		btnLimpiar_1.setBounds(320, 100, 84, 20);
		contentPane.add(btnLimpiar_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(176, 140, 250, 113);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Cliente", "Mascota", "Fecha", "Hora"
			}
		));
		scrollPane.setViewportView(table);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int filaSeleccionada = table.getSelectedRow();

				if (filaSeleccionada >= 0) {
					textField_2.setText(table.getValueAt(filaSeleccionada, 3).toString()); // Fecha
					textField_3.setText(table.getValueAt(filaSeleccionada, 4).toString()); // Hora
				}
			}
		});
		
		JButton btnNewButton_1 = new JButton("Volver");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPrincipal menu = new MenuPrincipal();
				menu.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setBounds(22, 221, 84, 20);
		contentPane.add(btnNewButton_1);
		cargarCitas();
	}
	
	private void mostrarCitasEnTabla(List<Object[]> citas) {
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("ID");
		modelo.addColumn("Cliente");
		modelo.addColumn("Mascota");
		modelo.addColumn("Fecha");
		modelo.addColumn("Hora");

		for (Object[] cita : citas) {
			modelo.addRow(cita);
		}

		table.setModel(modelo);
	}

	private void cargarCitas() {
		try {
			CitaDAO citaDAO = new CitaDAO();
			List<Object[]> citas = citaDAO.listarCitas();

			mostrarCitasEnTabla(citas);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar citas: " + e.getMessage());
		}
	}
}
