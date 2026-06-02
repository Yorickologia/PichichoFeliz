package com.pichichoFeliz.vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.pichichoFeliz.dao.ReporteDAO;

public class ReportesFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextArea textArea;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportesFrame frame = new ReportesFrame();
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
	public ReportesFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Reportes");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(166, 10, 75, 21);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ID cita:");
		lblNewLabel_1.setBounds(34, 74, 43, 12);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Servicio Realizado:");
		lblNewLabel_1_1.setBounds(10, 96, 87, 12);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Observacion:");
		lblNewLabel_1_2.setBounds(10, 116, 67, 12);
		contentPane.add(lblNewLabel_1_2);
		
		textField = new JTextField();
		textField.setBounds(101, 71, 96, 18);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Recomendacion:");
		lblNewLabel_1_2_1.setBounds(10, 138, 87, 12);
		contentPane.add(lblNewLabel_1_2_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(101, 93, 96, 18);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(101, 113, 96, 18);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(101, 135, 96, 18);
		contentPane.add(textField_3);
		
		JButton btnNewButton = new JButton("Generar Reporte");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idCita = Integer.parseInt(textField.getText());
					String servicio = textField_1.getText();
					String observacion = textField_2.getText();
					String recomendacion = textField_3.getText();

					if (servicio.isBlank() || observacion.isBlank() || recomendacion.isBlank()) {
						JOptionPane.showMessageDialog(null, "Debe completar servicio, observación y recomendación.");
						return;
					}

					ReporteDAO reporteDAO = new ReporteDAO();
					Object[] datosCita = reporteDAO.obtenerDatosCita(idCita);

					if (datosCita == null) {
						JOptionPane.showMessageDialog(null, "No existe una cita con ese ID.");
						return;
					}

					String reporte = "REPORTE DE ATENCIÓN - PICHICHO FELIZ\n"
							+ "----------------------------------------\n"
							+ "ID Cita: " + datosCita[0] + "\n"
							+ "Cliente: " + datosCita[1] + "\n"
							+ "Mascota: " + datosCita[2] + "\n"
							+ "Fecha: " + datosCita[3] + "\n"
							+ "Hora: " + datosCita[4] + "\n\n"
							+ "Servicio realizado: " + servicio + "\n"
							+ "Observación: " + observacion + "\n"
							+ "Recomendación: " + recomendacion + "\n"
							+ "----------------------------------------\n"
							+ "Reporte generado correctamente.";

					textArea.setText(reporte);

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "El ID de cita debe ser numérico.");
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al generar reporte: " + ex.getMessage());
				}
			}
		});

		btnNewButton.setBounds(220, 70, 109, 20);
		contentPane.add(btnNewButton);
		
		JButton btnLimpiar = new JButton("Limpiar");

		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textArea.setText("");
			}
		});

		btnLimpiar.setBounds(342, 70, 84, 20);
		contentPane.add(btnLimpiar);
		
		JButton btnVolver = new JButton("Volver");

		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPrincipal menu = new MenuPrincipal();
				menu.setVisible(true);
				dispose();
			}
		});

		btnVolver.setBounds(283, 112, 84, 20);
		contentPane.add(btnVolver);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 167, 416, 86);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

	}
}
