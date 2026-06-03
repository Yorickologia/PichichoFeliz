package com.pichichoFeliz.vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pichichoFeliz.modelo.Usuario;

public class MenuPrincipal extends JFrame {
	private Usuario usuarioSistema;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnRegistrarPago;
	private JButton btnReportes;
	private JButton btnInventario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
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
	public MenuPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 532, 389);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("PichichoFeliz");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 24));
		lblNewLabel.setBounds(174, 10, 152, 38);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Gestión Mascotas");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GestionMascotaFrame ventanaMascotas = new GestionMascotaFrame();
						ventanaMascotas.setVisible(true);
						dispose();
					}
				});
			}
		});
		btnNewButton.setBounds(30, 101, 180, 30);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Gestión Citas");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestionCitasFrame ventanaCitas = new GestionCitasFrame();
				ventanaCitas.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setBounds(30, 141, 180, 31);
		contentPane.add(btnNewButton_1);
		
		btnRegistrarPago = new JButton("Registrar Pago");

		btnRegistrarPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarPagoFrame ventanaPago = new RegistrarPagoFrame();
				ventanaPago.setVisible(true);
				dispose();
			}
		});

		btnRegistrarPago.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRegistrarPago.setBounds(30, 182, 180, 30);
		contentPane.add(btnRegistrarPago);
		
		btnReportes = new JButton("Reportes");
		btnReportes.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnReportes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReportesFrame reportesFrame = new ReportesFrame();
				reportesFrame.setVisible(true);
				dispose();
			}
		});
		btnReportes.setBounds(30, 221, 180, 31);
		contentPane.add(btnReportes);
		
		btnInventario = new JButton("Inventario");
		btnInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestionInventarioFrame inventarioFrame = new GestionInventarioFrame();
				inventarioFrame.setVisible(true);
				dispose();
			}
		});
		btnInventario.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnInventario.setBounds(30, 262, 180, 30);
		contentPane.add(btnInventario);
		
		JButton btnNewButton_5 = new JButton("Salir"); 	
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opcion = JOptionPane.showConfirmDialog(
					null,
					"¿Está seguro que desea salir del sistema?",
					"Confirmar salida",
					JOptionPane.YES_NO_OPTION
				);

				if (opcion == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_5.setBounds(30, 305, 180, 30);
		contentPane.add(btnNewButton_5);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\NAHUEL\\Downloads\\sonrisa.png"));
		lblNewLabel_1.setBounds(249, 58, 308, 266);
		contentPane.add(lblNewLabel_1);
		ImageIcon icono = new ImageIcon(MenuPrincipal.class.getResource("/img/sonrisa.png"));

		Image imagenEscalada = icono.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);

		lblNewLabel_1.setIcon(new ImageIcon(imagenEscalada));
		
		JButton btnNewButton_2_1 = new JButton("Gestión Clientes");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestionClientesFrame ventanaClientes = new GestionClientesFrame();
				ventanaClientes.setVisible(true);
			dispose();
			}
		});
		
		btnNewButton_2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_2_1.setBounds(30, 61, 180, 30);
		contentPane.add(btnNewButton_2_1);

	}
	
	public MenuPrincipal(Usuario usuarioSistema) {
		this();
		this.usuarioSistema = usuarioSistema;
		setTitle("Menú Principal - " + usuarioSistema.getRol());
		aplicarPermisos();
	}
	
	private void aplicarPermisos() {
		if (usuarioSistema != null && usuarioSistema.getRol().equals("Estilista")) {
			btnRegistrarPago.setEnabled(false);
			btnReportes.setEnabled(false);
			btnInventario.setEnabled(false);
		}
	}
}
