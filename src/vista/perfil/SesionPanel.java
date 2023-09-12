package vista.perfil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import controlador.Controlador;
import controlador.eventos.CerrarSesionEvent;
import controlador.eventos.RefreshStatsEvent;
import modelo.contenido.Contenido;
import modelo.perfil.Perfil;
import utils.ImageParser;
import vista.MainWindow;
import vista.PanelObservadorAdapter;
import vista.dialogs.ActualizarContenidoDialog;
import vista.dialogs.CrearContenidoDialog;
import vista.dialogs.EliminarContenidoDialog;
import vista.dialogs.InicioSesionDialog;

public class SesionPanel extends PanelObservadorAdapter {
	
	private static final long serialVersionUID = 1L;
	
	private Controlador control;
	private Perfil perfil;

	private MainWindow main;
	private JButton botonInicioSesion;
	
	public SesionPanel(Controlador control, MainWindow main) {
		this.control = control;
		control.addObservador(this);
		this.main = main;
		initGUI();
	}
	
	private void initGUI() {
		if (perfil == null)
			initSesionCerradaGUI();
		else {
			initUsuarioGUI();
		}
	}
	
	private void initSesionCerradaGUI() {
		botonInicioSesion = new JButton("Iniciar Sesión");
		botonInicioSesion.setBackground(Color.WHITE);
		setOpaque(false);
		setMaximumSize(new Dimension(botonInicioSesion.getWidth(), 35));
		add(botonInicioSesion);
		// Accion de iniciar sesion
		botonInicioSesion.addActionListener((e) -> new InicioSesionDialog(control));
	}
	
	private void initUsuarioGUI() {
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(new JLabel(perfil.getNickname()));
		add(new JLabel(ImageParser.iconFromFile("perfil.png", 30, 30)));
		
		JPopupMenu menu = new JPopupMenu();
		JMenuItem verPerfilItem = new JMenuItem("Ver información del perfil");
		verPerfilItem.addActionListener((e) -> main.setCentralPanel(new JScrollPane(new PerfilPanel(control, main))));
		JMenuItem cerrarItem = new JMenuItem("Cerrar sesión");
		cerrarItem.addActionListener((e) -> control.runEvent(new CerrarSesionEvent()));

		menu.add(verPerfilItem);
		if (perfil.isAdmin()) {
			JMenu adminMenu = new JMenu("Opciones de administrador");
			
			JMenuItem addCont = new JMenuItem("Añadir contenido");
			addCont.addActionListener((e) -> new CrearContenidoDialog(control, null));
			adminMenu.add(addCont);
			JMenuItem elimCont = new JMenuItem("Eliminar contenido");
			elimCont.addActionListener((e) -> new EliminarContenidoDialog(control, null));
			adminMenu.add(elimCont);
			JMenuItem actCont = new JMenuItem("Actualizar contenido");
			actCont.addActionListener((e) -> new ActualizarContenidoDialog(control, null));
			adminMenu.add(actCont);
			JMenuItem refrCont = new JMenuItem("Refrescar estadísticas de contenido");
			refrCont.addActionListener((e) -> control.runEvent(new RefreshStatsEvent()));
			adminMenu.add(refrCont);
			
			menu.add(adminMenu);
		}
		menu.add(cerrarItem);
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				menu.show(SesionPanel.this, 0, SesionPanel.this.getHeight());
			}
		});
	}

	@Override
	public void onCambioPerfil(Perfil p) {
		if (perfil != p) {
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
