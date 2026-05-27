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

import com.pichichoFeliz.dao.PagoDAO;
import com.pichichoFeliz.modelo.Pago;

public class RegistrarPagoFrame extends JFrame {

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
					RegistrarPagoFrame frame = new RegistrarPagoFrame();
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
	public RegistrarPagoFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Pagos");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(181, 10, 56, 24);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ID cita:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_1.setBounds(21, 79, 39, 24);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Monto:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_1_1.setBounds(21, 102, 39, 24);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Metodo:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_1_2.setBounds(21, 128, 44, 24);
		contentPane.add(lblNewLabel_1_2);
		
		textField = new JTextField();
		textField.setBounds(70, 82, 96, 18);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(70, 108, 96, 18);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(70, 134, 96, 18);
		contentPane.add(textField_2);
		
		JButton btnNewButton = new JButton("Guardar");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idCita = Integer.parseInt(textField.getText());
					double monto = Double.parseDouble(textField_1.getText());
					String metodo = textField_2.getText();

					if (metodo.isBlank()) {
						JOptionPane.showMessageDialog(null, "Debe ingresar el método de pago.");
						return;
					}

					if (monto <= 0) {
						JOptionPane.showMessageDialog(null, "El monto debe ser mayor a cero.");
						return;
					}

					Pago pago = new Pago(idCita, monto, metodo);

					PagoDAO pagoDAO = new PagoDAO();
					pagoDAO.guardarPago(pago);

					JOptionPane.showMessageDialog(null, "Pago registrado correctamente.");

					cargarPagos();

					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "ID Cita y Monto deben ser valores numéricos.");
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al registrar pago: " + ex.getMessage());
				}
			}
		});

		btnNewButton.setBounds(220, 79, 84, 20);
		contentPane.add(btnNewButton);
		
		JButton btnBuscar = new JButton("Buscar");

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idCitaTexto = textField.getText();

				if (idCitaTexto.isBlank()) {
					cargarPagos();
					return;
				}

				try {
					int idCita = Integer.parseInt(idCitaTexto);

					PagoDAO pagoDAO = new PagoDAO();
					List<Object[]> pagos = pagoDAO.buscarPagosPorIdCita(idCita);

					mostrarPagosEnTabla(pagos);

					if (pagos.isEmpty()) {
						JOptionPane.showMessageDialog(null, "No se encontraron pagos para esa cita.");
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "El ID Cita debe ser numérico.");
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al buscar pago: " + ex.getMessage());
				}
			}
		});

		btnBuscar.setBounds(313, 79, 84, 20);
		contentPane.add(btnBuscar);
		
		JButton btnLimpiar = new JButton("Limpiar");

		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");     // ID Cita
				textField_1.setText("");   // Monto
				textField_2.setText("");   // Método

				cargarPagos();
			}
		});

		btnLimpiar.setBounds(220, 104, 84, 20);
		contentPane.add(btnLimpiar);
		
		JButton btnVolver = new JButton("Volver");

		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPrincipal menu = new MenuPrincipal();
				menu.setVisible(true);
				dispose();
			}
		});

		btnVolver.setBounds(313, 104, 84, 20);
		contentPane.add(btnVolver);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 162, 414, 91);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID Pago", "ID Cita", "Cliente", "Mascota", "Fecha", "Monto", "Método"
			}
		));
		scrollPane.setViewportView(table);

		cargarPagos();
	}
	
	private void mostrarPagosEnTabla(List<Object[]> pagos) {
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("ID Pago");
		modelo.addColumn("ID Cita");
		modelo.addColumn("Cliente");
		modelo.addColumn("Mascota");
		modelo.addColumn("Fecha");
		modelo.addColumn("Monto");
		modelo.addColumn("Método");

		for (Object[] pago : pagos) {
			modelo.addRow(pago);
		}

		table.setModel(modelo);
	}

	private void cargarPagos() {
		try {
			PagoDAO pagoDAO = new PagoDAO();
			List<Object[]> pagos = pagoDAO.listarPagos();

			mostrarPagosEnTabla(pagos);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar pagos: " + e.getMessage());
		}
	}
	
}
