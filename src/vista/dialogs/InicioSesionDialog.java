package vista.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import controlador.Controlador;
import controlador.eventos.IniciarSesionEvent;

public class InicioSesionDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JButton botonInicioSesion = new JButton("Iniciar Sesión");
	private JButton botonRegistro = new JButton("Registrarse");
	
	private JTextField correoBarra;
	private JPasswordField contrasenaBarra;
	
	private Controlador control;

	public InicioSesionDialog(Controlador control) {
		this.control = control;
		initGUI();
		setVisible(true);
	}
	
	void initGUI() {
		// Configuracion de la ventana 
		Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(s.width/2 - 400 /2, s.height/2 - 500 /2);
		setModal(true);
		setResizable(false);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(Box.createHorizontalStrut(20));
		
		JPanel cp = new JPanel(new GridLayout(0, 1, 0, 20));
		cp.setOpaque(false);

		cp.add(Box.createVerticalStrut(50));
		
		JPanel tituloLabel = new JPanel(new FlowLayout());
		tituloLabel.setOpaque(false);
		JLabel titulo = new JLabel("Iniciar Sesión");
		titulo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		tituloLabel.add(titulo);
		
		cp.add(tituloLabel);
		 
		JPanel nombreUsuarioPanel = new JPanel();
		nombreUsuarioPanel.setOpaque(false);
		nombreUsuarioPanel.setLayout(new BoxLayout(nombreUsuarioPanel, BoxLayout.Y_AXIS));
		
		JLabel correoLabel = new JLabel("Correo:          ");
		nombreUsuarioPanel.add(correoLabel);
		
		JPanel auxBarra = new JPanel(new FlowLayout(FlowLayout.CENTER));
		auxBarra.setOpaque(false);
		
		correoBarra = new JTextField(24);
		auxBarra.add(correoBarra);
		nombreUsuarioPanel.add(auxBarra);
		
		cp.add(nombreUsuarioPanel);
		  
		// Panel donde se introduce la contrase�a
		JPanel contrasenaPanel = new JPanel();
		contrasenaPanel.setOpaque(false);
		contrasenaPanel.setLayout(new BoxLayout(contrasenaPanel, BoxLayout.Y_AXIS));
		
		JLabel contrasenaLabel = new JLabel("Contraseña:   ");
		contrasenaLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contrasenaPanel.add(contrasenaLabel);
		
		JPanel auxBarra1 = new JPanel(new FlowLayout());
		auxBarra1.setOpaque(false);
		
		contrasenaBarra = new JPasswordField(24);
		auxBarra1.add(contrasenaBarra);
		contrasenaPanel.add(auxBarra1);
		
		cp.add(contrasenaPanel);
		 
		// Panel donde esta el boton de confirmacion
		JPanel botonInicioPanel = new JPanel(new FlowLayout());
		botonInicioPanel.setOpaque(false);
		
		botonInicioSesion.setBackground(Color.WHITE);
		botonInicioPanel.add(botonInicioSesion);
		
		cp.add(botonInicioPanel);
		
		// Panel que lleva al usuario a registrarse
		JPanel registrarPanel = new JPanel(new FlowLayout());
		registrarPanel.setOpaque(false);
		
		JLabel mensajeRegistro = new JLabel("No tienes cuenta? Registrate aquí!");
		registrarPanel.add(mensajeRegistro);
		
		botonRegistro.setBackground(Color.WHITE);
		registrarPanel.add(botonRegistro);
		cp.add(registrarPanel);
		
		getContentPane().add(cp);
		getContentPane().add(Box.createHorizontalStrut(20));
 		pack();

		acciones();
	}
	
	private void acciones() {
		botonRegistro.addActionListener((e) -> new RegistrarseDialog(control));
		botonInicioSesion.addActionListener((e) -> {
			try {
				control.runEvent(new IniciarSesionEvent(correoBarra.getText(), new String(contrasenaBarra.getPassword())));
				InicioSesionDialog.this.dispose();
			}
			catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(this, iae.getMessage());
			}
		});
		correoBarra.addKeyListener(textLimit(correoBarra, 25));
		contrasenaBarra.addKeyListener(textLimit(contrasenaBarra, 15));
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
