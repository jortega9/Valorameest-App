package vista.dialogs;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.json.JSONObject;
import org.json.JSONTokener;

import controlador.Controlador;
import controlador.eventos.NewPerfilEvent;

public class RegistrarseDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private final int WIDTH = 330;
	private final int HEIGHT = 435;
	private JTextField nickText;
	private JTextField correoText;
	private JPasswordField passwordField;
	private JButton registrarse;
	
	private Controlador control;

	public RegistrarseDialog(Controlador control) {
		this.control = control;
		initGUI();
		acciones();
		setVisible(true);
	}

	private void initGUI() {
		setSize(WIDTH, HEIGHT);
		setModal(true);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Registrarse");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(91, 30, 135, 29);
		panel.add(lblNewLabel);
		
		JLabel nick = new JLabel("Nick:");
		nick.setFont(new Font("Tahoma", Font.BOLD, 13));
		nick.setBounds(23, 94, 56, 16);
		panel.add(nick);
		
		nickText = new JTextField();
		nickText.setBounds(25, 123, 264, 22);
		panel.add(nickText);
		nickText.setColumns(10);
		
		JLabel nick_1 = new JLabel("Correo Electrónico:");
		nick_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		nick_1.setBounds(23, 158, 122, 16);
		panel.add(nick_1);
		
		correoText = new JTextField();
		correoText.setColumns(10);
		correoText.setBounds(25, 187, 264, 22);
		panel.add(correoText);
		
		JLabel contrasena = new JLabel("Contraseña:");
		contrasena.setFont(new Font("Tahoma", Font.BOLD, 13));
		contrasena.setBounds(23, 222, 83, 16);
		panel.add(contrasena);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(25, 250, 264, 22);
		panel.add(passwordField);
		
		registrarse = new JButton("Registrarse");
		registrarse.setBackground(Color.WHITE);
		registrarse.setBounds(101, 309, 117, 25);
		panel.add(registrarse);
	}
	
	private void acciones() {
		registrarse.addActionListener((e) -> {
			try {
				JSONObject jo = new JSONObject(new JSONTokener("{\r\n" + 
						"\"correo\" : \"" + correoText.getText() + "\",\r\n" + 
						"\"nickname\" : \"" + nickText.getText() + "\",\r\n" + 
						"\"password\" : \"" + new String(passwordField.getPassword()) + "\"\r\n" + 
						"}"));
				control.runEvent(new NewPerfilEvent(jo));
				JOptionPane.showMessageDialog(RegistrarseDialog.this, "Usuario registrado con éxito");
				RegistrarseDialog.this.dispose();
			}
			catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(RegistrarseDialog.this, iae.getMessage());
			}
		});
		nickText.addKeyListener(textLimit(nickText, 15));
		correoText.addKeyListener(textLimit(correoText, 25));
		passwordField.addKeyListener(textLimit(passwordField, 15));
	}
	
	private KeyListener textLimit(JTextComponent t, int lim) {
		return new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (t.getText().length() >= lim)
					e.consume();
			}
		};
	}
}
