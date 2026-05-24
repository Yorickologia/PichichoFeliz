package com.pichichoFeliz.vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.pichichoFeliz.dao.UsuarioDAO;
import com.pichichoFeliz.modelo.Administrador;
import com.pichichoFeliz.modelo.Estilista;
import com.pichichoFeliz.modelo.Usuario;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Usuario:");
		lblNewLabel_1.setBounds(138, 93, 61, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("PichichoFeliz");
		lblNewLabel.setBounds(146, 0, 132, 37);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1_1 = new JLabel("Contraseña:");
		lblNewLabel_1_1.setBounds(138, 118, 61, 13);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Rol:");
		lblNewLabel_1_2.setBounds(138, 141, 45, 13);
		contentPane.add(lblNewLabel_1_2);
		
		textField = new JTextField();
		textField.setBounds(247, 91, 96, 18);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(247, 115, 96, 18);
		contentPane.add(passwordField);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Estilista", "Administrador"}));
		comboBox.setBounds(245, 137, 98, 20);
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("Ingresar");
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String usuario = textField.getText();
				String contrasena = new String(passwordField.getPassword());
				String rol = comboBox.getSelectedItem().toString();
				
				if (usuario.isBlank() || contrasena.isBlank()){
					JOptionPane.showMessageDialog(null, "Debe ingresar usuario y contraseña. ");
			return;
		}
				
				try {
					UsuarioDAO usuarioDAO = new UsuarioDAO();
					boolean usuarioValido = usuarioDAO.validarUsuario(usuario, contrasena, rol);

					if (!usuarioValido) {
						JOptionPane.showMessageDialog(null, "Usuario, contraseña o rol incorrectos.");
						return;
					}

					Usuario usuarioSistema;

					if (rol.equals("Administrador")) {
						usuarioSistema = new Administrador(usuario, rol);
					} else {
						usuarioSistema = new Estilista(usuario, rol);
					}

					JOptionPane.showMessageDialog(null, usuarioSistema.obtenerPermisos());

					MenuPrincipal menu = new MenuPrincipal(usuarioSistema);
					menu.setVisible(true);
					dispose();

				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al validar usuario: " + ex.getMessage());
				}
				
				Usuario usuarioSistema;

				if (rol.equals("Administrador")) {
					usuarioSistema = new Administrador(usuario, rol);
				} else {
					usuarioSistema = new Estilista(usuario, rol);
				}
		
			}
		});
		btnNewButton.setBounds(126, 233, 84, 20);
		contentPane.add(btnNewButton);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(286, 233, 84, 20);
		contentPane.add(btnSalir);

	}
}
