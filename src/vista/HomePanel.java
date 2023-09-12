package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controlador.Controlador;
import modelo.contenido.Contenido;
import modelo.contenido.TipoContenido;
import modelo.perfil.EstadoReg;
import modelo.perfil.Perfil;
import modelo.perfil.RegPelicula;
import modelo.perfil.RegSerie;
import modelo.perfil.RegVideojuego;
import modelo.perfil.Registro;
import utils.ImageParser;
import vista.dialogs.InicioSesionDialog;
import vista.dialogs.RegistrarseDialog;

public class HomePanel extends PanelObservadorAdapter {
	
	private static final long serialVersionUID = 1L;
	
	private MainWindow main;
	private Controlador control;
	private Perfil perfil;
	
	private JPanel infoUser;

	public HomePanel(MainWindow main, Controlador control) {
		this.main = main;
		this.control = control;
		initGUI();		
		control.addObservador(this);
	}
	
	private void initGUI() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JLabel labelDestacadoHoy = new JLabel("    Destacado hoy:");
		labelDestacadoHoy.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add(labelDestacadoHoy, BorderLayout.NORTH);
		
		JPanel paneles = new JPanel();
		paneles.setBackground(Color.WHITE);
		paneles.setBorder(new EmptyBorder(5, 5, 5, 5));
		paneles.setLayout(new GridLayout(1, 4, 5, 5));
		
		// Panel Destacados Series
		
		JPanel destacadoSeries = new PanelElementosDestacados(main, control, TipoContenido.SERIE);
		paneles.add(destacadoSeries);
		 
		// Panel Destacado Peliculas
		
		JPanel destacadoPeliculas = new PanelElementosDestacados(main, control, TipoContenido.PELICULA);
		paneles.add(destacadoPeliculas);
		
		// Panel Destacado Videojuegos
		
		JPanel destacadoVideojuegos = new PanelElementosDestacados(main, control, TipoContenido.VIDEOJUEGO);
		paneles.add(destacadoVideojuegos);
			
		// Panel inicio sesion
		
		infoUser = new JPanel(new GridLayout(1, 1));
		infoUser.setBackground(Color.WHITE);
		infoUser.setBorder(new LineBorder(Color.BLACK, 1, true));
		paneles.add(infoUser);
		
		add(paneles, BorderLayout.CENTER);	
	}

	private JPanel sesionCerradaPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JTextArea textArea = new JTextArea("¡Inicia sesión con tu cuenta o regístrate\n" + 
											"      para poder ver tus estadísticas!");
		textArea.setEditable(false);
		textArea.setMaximumSize(new Dimension(215, 50));
		
		JButton inicioSesion = new JButton("Iniciar sesión");
		inicioSesion.addActionListener((e) -> new InicioSesionDialog(control));
		inicioSesion.setBackground(Color.WHITE);
		
		JButton registrarse = new JButton("Registrarse");
		registrarse.addActionListener((e) -> new RegistrarseDialog(control));
		registrarse.setBackground(Color.WHITE);
		
		JPanel botones = new JPanel();
		botones.setOpaque(false);
		botones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		botones.add(inicioSesion);
		botones.add(registrarse);

		panel.add(Box.createVerticalGlue());
		panel.add(textArea);
		panel.add(Box.createVerticalStrut(20));
		panel.add(botones);
		panel.add(Box.createVerticalGlue());
		
		return panel;
	}
	
	private JPanel usuarioPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createVerticalStrut(37));
		
		JLabel nickLab = new JLabel(perfil.getNickname());
		nickLab.setHorizontalAlignment(SwingConstants.CENTER);
		nickLab.setFont(new Font("Tahoma", Font.BOLD, 28));
		
		JPanel perfilPanel = new JPanel(new GridLayout(2, 1));
		perfilPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
		perfilPanel.setOpaque(false);
		perfilPanel.add(new JLabel(ImageParser.iconFromFile("perfil.png", 80, 80)));
		perfilPanel.add(nickLab);
		
		panel.add(perfilPanel);
		panel.add(Box.createVerticalStrut(20));
		
		JLabel vistosLabel = new JLabel("Has visto/jugado:");
		vistosLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(vistosLabel);
		int[] conts = contenidosVistos();
		for (int i = 0; i < TipoContenido.values().length; ++i) {
			panel.add(Box.createVerticalStrut(10));
			panel.add(new JLabel(Integer.toString(conts[i]) + " " + TipoContenido.values()[i].toString().toLowerCase() + "s"));
		}
		panel.add(Box.createVerticalStrut(30));
		
		JLabel listasLabel = new JLabel("Tus listas:");
		listasLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(listasLabel);
		conts = contarListas();
		for (int i = 0; i < EstadoReg.values().length; ++i) {
			panel.add(Box.createVerticalStrut(10));
			panel.add(new JLabel(Integer.toString(conts[i]) + " " + EstadoReg.values()[i].toString()));
		}

		panel.add(Box.createVerticalGlue());
		
		return panel;
	}
	
	private int[] contenidosVistos() {
		int[] conts = new int[TipoContenido.values().length];
		for (Registro reg : perfil.getRegistros()) {
			switch (reg.getContenido().getTipo()) {
			case PELICULA:
				if (((RegPelicula)reg).getMinActual() > 0) conts[TipoContenido.PELICULA.ordinal()]++;
				break;
			case SERIE:
				if (((RegSerie)reg).getCapsVistos() > 0) conts[TipoContenido.SERIE.ordinal()]++;
				break;
			case VIDEOJUEGO:
				if (((RegVideojuego)reg).getHorasJugadas() > 0) conts[TipoContenido.VIDEOJUEGO.ordinal()]++;
				break;
			}
		}
		return conts;
	}
	
	private int[] contarListas() {
		int[] conts = new int[EstadoReg.values().length];
		for (Registro reg : perfil.getRegistros())
			for (EstadoReg e : reg.getEstados())
				conts[e.ordinal()]++;
		return conts;
	}
	
	@Override
	public void onCambioPerfil(Perfil p) {
		if (p != perfil) {
			perfil = p;
			infoUser.removeAll();
			infoUser.add(p == null ? sesionCerradaPanel() : usuarioPanel());
			infoUser.updateUI();
		}
	}
	
	@Override
	public void onRegister(List<Contenido> nuevo, Perfil p) {
		perfil = p;
		infoUser.add(p == null ? sesionCerradaPanel() : usuarioPanel());
	}
}
