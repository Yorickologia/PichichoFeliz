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

import com.pichichoFeliz.dao.ProductoDAO;
import com.pichichoFeliz.modelo.Producto;

public class GestionInventarioFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionInventarioFrame frame = new GestionInventarioFrame();
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
	public GestionInventarioFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Inventario");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(158, 10, 88, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre Producto:");
		lblNewLabel_1.setBounds(10, 78, 94, 12);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Cantidad:");
		lblNewLabel_2.setBounds(20, 103, 54, 12);
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(99, 75, 96, 18);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(99, 100, 96, 18);
		contentPane.add(textField_1);
		
		JButton btnGuardar = new JButton("Guardar");

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nombre = textField.getText();
					int cantidad = Integer.parseInt(textField_1.getText());

					if (nombre.isBlank()) {
						JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del producto.");
						return;
					}

					if (cantidad < 0) {
						JOptionPane.showMessageDialog(null, "La cantidad no puede ser negativa.");
						return;
					}

					Producto producto = new Producto(nombre, cantidad);

					ProductoDAO productoDAO = new ProductoDAO();
					productoDAO.guardarProducto(producto);

					JOptionPane.showMessageDialog(null, "Producto guardado correctamente.");

					cargarProductos();

					textField.setText("");
					textField_1.setText("");

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "La cantidad debe ser numérica.");
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, "Error al guardar producto: " + ex.getMessage());
				}
			}
		});

		btnGuardar.setBounds(225, 50, 84, 20);
		contentPane.add(btnGuardar);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(319, 50, 84, 20);
		contentPane.add(btnBuscar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(225, 74, 84, 20);
		contentPane.add(btnEditar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(319, 74, 84, 20);
		contentPane.add(btnEliminar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.setBounds(225, 99, 84, 20);
		contentPane.add(btnLimpiar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(319, 99, 84, 20);
		contentPane.add(btnVolver);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 156, 406, 97);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Producto", "Cantidad"
			}
		));
		scrollPane.setViewportView(table);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int filaSeleccionada = table.getSelectedRow();

				if (filaSeleccionada >= 0) {
					textField.setText(table.getValueAt(filaSeleccionada, 1).toString());
					textField_1.setText(table.getValueAt(filaSeleccionada, 2).toString());
				}
			}
		});

		cargarProductos();

	}
	
	private void mostrarProductosEnTabla(List<Object[]> productos) {
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("ID");
		modelo.addColumn("Producto");
		modelo.addColumn("Cantidad");

		for (Object[] producto : productos) {
			modelo.addRow(producto);
		}

		table.setModel(modelo);
	}

	private void cargarProductos() {
		try {
			ProductoDAO productoDAO = new ProductoDAO();
			List<Object[]> productos = productoDAO.listarProductos();

			mostrarProductosEnTabla(productos);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e.getMessage());
		}
	}
}
