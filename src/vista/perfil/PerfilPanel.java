package vista.perfil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controlador.Controlador;
import modelo.contenido.Contenido;
import modelo.perfil.EstadoReg;
import modelo.perfil.Perfil;
import modelo.perfil.Registro;
import utils.ImageParser;
import vista.MainWindow;
import vista.PanelObservadorAdapter;
import vista.contenido.PortadaVerticalPanel;
import vista.dialogs.InicioSesionDialog;
import vista.dialogs.RegistrarseDialog;

public class PerfilPanel extends PanelObservadorAdapter {

	private static final long serialVersionUID = 1L;

	private Controlador control;
	private MainWindow main;
	private Perfil perfil;
	
	private List<JButton> botonesEst;
	private JPanel tablaCont;
	
	public PerfilPanel(Controlador control, MainWindow main) {
		this.control = control;
		control.addObservador(this);
		this.main = main;
		initGUI();
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		if (perfil == null)
			initSesionCerradaGUI();
		else
			initUsuarioGUI();
		
		add(Box.createHorizontalStrut(200), BorderLayout.WEST);
		add(Box.createHorizontalStrut(200), BorderLayout.EAST);
		add(Box.createVerticalStrut(30), BorderLayout.NORTH);
	}
	
	private void initSesionCerradaGUI() {
		JPanel pane = new JPanel();
		pane.setOpaque(false);
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		JTextArea textArea = new JTextArea("¡Inicia sesión con tu cuenta o regístrate\n" + " para poder ver y guardar información!");
		textArea.setFont(new Font("Tahoma", Font.BOLD, 32));
		textArea.setEditable(false);
		JPanel auxText = new JPanel();
		auxText.setOpaque(false);
		auxText.add(textArea);
		
		JPanel panelBotones = new JPanel();
		panelBotones.setOpaque(false);
		JButton bIniciar = new JButton("Iniciar sesión"), bRegistrar = new JButton("Registrarse");
		bIniciar.setPreferredSize(new Dimension(250, 50));
		bIniciar.setFont(new Font(null, Font.PLAIN, 24));
		bIniciar.setBackground(Color.WHITE);
		bIniciar.addActionListener((e) -> new InicioSesionDialog(control));
		bRegistrar.setPreferredSize(new Dimension(250, 50));
		bRegistrar.setFont(new Font(null, Font.PLAIN, 24));
		bRegistrar.setBackground(Color.WHITE);
		bRegistrar.addActionListener((e) -> new RegistrarseDialog(control));
		panelBotones.add(bIniciar);
		panelBotones.add(Box.createRigidArea(new Dimension(30, 50)));
		panelBotones.add(bRegistrar);
		
		pane.add(auxText);
		pane.add(panelBotones);
		pane.add(Box.createVerticalGlue());
		
		add(pane, BorderLayout.CENTER);
	}

	private void initUsuarioGUI() {
		JPanel pane = new JPanel();
		pane.setOpaque(false);
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		pane.add(perfilPanel());
		pane.add(Box.createVerticalStrut(50));
		pane.add(listasPanel());
		pane.add(Box.createVerticalGlue());

		botonesEst.get(0).doClick();
		add(pane, BorderLayout.CENTER);
	}
	
	private JPanel perfilPanel() {
		JPanel perfilPanel = new JPanel(new BorderLayout());
		perfilPanel.setOpaque(false);
		perfilPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
		perfilPanel.add(new JLabel(ImageParser.iconFromFile("perfil.png", 200, 200)), BorderLayout.WEST);
		
		JPanel nombrePanel = new JPanel(new GridLayout(3, 1, 10, 10));
		nombrePanel.add(Box.createRigidArea(new Dimension(1, 1)));
		nombrePanel.setOpaque(false);
		JLabel nickLabel = new JLabel(perfil.getNickname()), correoLabel = new JLabel(perfil.getCorreo());
		nickLabel.setFont(new Font("Tahoma", Font.BOLD, 50));
		correoLabel.setFont(new Font(null, Font.ITALIC, 24));
		correoLabel.setForeground(Color.GRAY);
		nombrePanel.add(nickLabel);
		nombrePanel.add(correoLabel);
		
		JPanel auxNombre = new JPanel(new FlowLayout(FlowLayout.LEFT));
		auxNombre.add(Box.createRigidArea(new Dimension(30, 30)));
		auxNombre.add(nombrePanel);
		auxNombre.setOpaque(false);
		perfilPanel.add(auxNombre);
		
		return perfilPanel;
	}
	
	private JPanel listasPanel() {
		JPanel listasPanel = new JPanel(new BorderLayout());
		listasPanel.setOpaque(false);
		
		JPanel estadosPanel = new JPanel(new GridLayout(1, 0));
		botonesEst = new ArrayList<>();
		for (EstadoReg e : EstadoReg.values()) {
			JButton be = botonEstado(e);
			botonesEst.add(be);
			estadosPanel.add(be);
		}
		
		tablaCont = new JPanel(new GridLayout(0, 5, 10, 10));
		tablaCont.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		tablaCont.setOpaque(false);
		
		listasPanel.add(estadosPanel, BorderLayout.NORTH);
		listasPanel.add(tablaCont, BorderLayout.CENTER);
		return listasPanel;
	}
	
	private JButton botonEstado(EstadoReg e) {
		JButton botonEstado = new JButton(e.toString());
		botonEstado.setBackground(Color.WHITE);
		botonEstado.setBorderPainted(false);
		botonEstado.addActionListener((a) -> {
			for (JButton b : botonesEst) {
				b.setBackground(Color.WHITE);
				b.setForeground(Color.BLACK);
			}
			botonEstado.setBackground(new Color(150, 150, 255));
			botonEstado.setForeground(Color.WHITE);
			listarEstado(e);
		});
		return botonEstado;
	}
	
	private void listarEstado(EstadoReg e) {
		tablaCont.removeAll();
		for (Registro reg : perfil.getRegistros())
			if (reg.hasEstado(e)) 
				tablaCont.add(new PortadaVerticalPanel(main, reg.getContenido(), control, "En tu lista de " + e.toString().charAt(0) + e.toString().substring(1).toLowerCase(), "Perfil"));
		
		tablaCont.updateUI();
	}

	@Override
	public void onCambioPerfil(Perfil p) {
		if (p != perfil) {
			perfil = p;
			removeAll();
			initGUI();
			updateUI();
		}
	}

	@Override
	public void onRegister(List<Contenido> nuevo, Perfil p) {
		perfil = p;
	}
}
