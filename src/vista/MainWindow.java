package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import controlador.Controlador;
import controlador.eventos.CerrarSesionEvent;
import modelo.contenido.TipoContenido;
import utils.ImageParser;
import vista.perfil.SesionPanel;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Controlador control;
	
	private static final int WIDTH = 1400;
	private static final int HEIGHT = 700;
	
	// Elementos de la barra de Inicio
	private JLabel tituloPrincipal;
	
	// Elementos de la barra de Categorias
	private JButton botonHome;
	private JButton botonCatalogoSeries;
	private JButton botonCatalgoPeliculas;
	private JButton botonCatalogoVideojuegos;
	private JButton botonCatalogoGeneral;
	private JButton botonVolver;
	private JTextField barraBusqueda;
	private JButton buscar;
	
	// Ventana que alterna
	private Stack<JComponent> anteriores;
	private JPanel centralPanel, centralVacio;
	private CatalogoPanel catalogoPanel;
	private HomePanel homePanel;
	private SesionPanel perfilPanel;

	public MainWindow(Controlador control) {
		this.control = control;
		anteriores = new Stack<>();
		initGUI();
	}

	private void initGUI() {
		setVisible(false);
		// Configuracion de la ventana
		this.setSize(WIDTH, HEIGHT);
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Añadimos la barra de titulo y el panel principal
        this.getContentPane().add(barraTitulo(), BorderLayout.NORTH);
        this.getContentPane().add(panelPrincipal(), BorderLayout.CENTER);

		//Paneles secundarios
		iniciarPanelesSecundarios();
		setCentralPanel(homePanel);
		// Todas los ActionListeners
        acciones();
        
        this.addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		int op = JOptionPane.showConfirmDialog(MainWindow.this, "¿Quieres salir de la aplicación?", "Salir", JOptionPane.YES_NO_OPTION);
    			if (op == JOptionPane.OK_OPTION) {
    				control.runEvent(new CerrarSesionEvent());
    				System.exit(0);
    			}
        	}
        });
		setVisible(true);
	}
	
	private JPanel panelPrincipal() {
		// Main Panel configuracion inicial
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setLayout(new BorderLayout());
		
		centralVacio = new JPanel();
		centralVacio.setLayout(new BoxLayout(centralVacio, BoxLayout.Y_AXIS));
		JLabel vacio = new JLabel("No se ha cargado ninguna ventana");
		vacio.setAlignmentX(CENTER_ALIGNMENT);
		centralVacio.add(Box.createVerticalStrut(HEIGHT / 3));
		centralVacio.add(vacio);
		
		centralPanel = new JPanel();
		centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        
        // Añadimos la barra de categorias y el panel alternante
        mainPanel.add(barraCategorias(), BorderLayout.NORTH);
        mainPanel.add(centralPanel, BorderLayout.CENTER);
        return mainPanel;
	}
	
	private JToolBar barraTitulo() {
		// Barra de titulo (Configuracion inicial)
		JToolBar barraTitulo = new JToolBar();
		barraTitulo.setBackground(Color.WHITE);
		barraTitulo.setFloatable(false);
		
		// Configuracion del titulo principal (Boton invisible)
		tituloPrincipal = new JLabel("Valoramest App");
		tituloPrincipal.setFont(new Font("Verdana", Font.PLAIN, 32));
		
		//Configurador del buscar
		buscar = new JButton("Buscar");
		buscar.setOpaque(false);
        buscar.setContentAreaFilled(false);
        buscar.setBorderPainted(false);

		perfilPanel = new SesionPanel(control, this);
		
		// Añadimos los elementos a la barra de titulo
		barraTitulo.add(Box.createHorizontalGlue());
		barraTitulo.add(tituloPrincipal);
		barraTitulo.add(Box.createHorizontalGlue());
		barraTitulo.add(perfilPanel);
		barraTitulo.addSeparator();
		return barraTitulo;
	}
	
	private JToolBar barraCategorias() {
		// Configuracion de la barra de categorias
		JToolBar barraCategorias = new JToolBar();
		barraCategorias.setBackground(Color.WHITE);
		barraCategorias.setFloatable(false);
		barraCategorias.addSeparator();
		
		botonVolver = new JButton(ImageParser.iconFromFile("Volver.png", 15, 15));
		botonVolver.setBackground(Color.WHITE);
		botonVolver.setEnabled(false);
		barraCategorias.add(botonVolver);
		barraCategorias.addSeparator();
		
		botonHome = new JButton(ImageParser.iconFromFile("home.png", 15, 15));
		botonHome.setBackground(Color.WHITE);
		barraCategorias.add(botonHome);
		barraCategorias.addSeparator();
		// Configuracion del boton del catalogo general
		botonCatalogoGeneral = new JButton("General");
		botonCatalogoGeneral.setBackground(Color.WHITE);
		barraCategorias.add(botonCatalogoGeneral);
		barraCategorias.addSeparator();
		// Configuracion del boton del catalogo de series
		botonCatalogoSeries = new JButton("Series");
		botonCatalogoSeries.setBackground(Color.WHITE);
		barraCategorias.add(botonCatalogoSeries);
		barraCategorias.addSeparator();
		// Configuracion del boton del catologo de peliculas
		botonCatalgoPeliculas = new JButton("Peliculas");
		botonCatalgoPeliculas.setBackground(Color.WHITE);
		barraCategorias.add(botonCatalgoPeliculas);
		barraCategorias.addSeparator();
		// Configuracion del boton del catologo de videojuegos
		botonCatalogoVideojuegos = new JButton("Videojuegos");
		botonCatalogoVideojuegos.setBackground(Color.WHITE);
		barraCategorias.add(botonCatalogoVideojuegos);
		barraCategorias.addSeparator();
		
		barraBusqueda = new JTextField(3);
		barraCategorias.add(barraBusqueda);
		barraCategorias.add(buscar);
		barraCategorias.addSeparator();
		return barraCategorias;
	}
	
	private void iniciarPanelesSecundarios() {
		try {
			catalogoPanel = new CatalogoPanel(control, this);
			homePanel = new HomePanel(this, control);
		} catch (RuntimeException re) {
			JOptionPane.showMessageDialog(null, "Error al cargar datos, compruebe su conexión antes de continuar con la aplicación.");
		}
	}
	
	private void acciones() {
		// Accion de volver al panel inicial
		tituloPrincipal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCentralPanel(homePanel);
			}
		});
		botonVolver.addActionListener((e) -> retornarCentralPanel());
		
		// Accion de volver al panel inicial
		botonHome.addActionListener((e) -> {
			setCentralPanel(homePanel);
		});
		
		botonCatalogoGeneral.addActionListener((e) -> {
			seleccionarCatalogo(null);
		});
		// Accion de abrir catalogo de series
		botonCatalogoSeries.addActionListener((e) -> {
			seleccionarCatalogo(TipoContenido.SERIE);
		});
		// Accion de abrir catalogo de peliculas
		botonCatalgoPeliculas.addActionListener((e) -> {
			seleccionarCatalogo(TipoContenido.PELICULA);
		});
		// Accion de abrir catalogo de videojuegos
		botonCatalogoVideojuegos.addActionListener((e) -> {
			seleccionarCatalogo(TipoContenido.VIDEOJUEGO);
		});
		buscar.addActionListener((e) -> {
			catalogoPanel.buscar();
			setCentralPanel(catalogoPanel);
		});
		
		barraBusqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					buscar.doClick();
				else
					catalogoPanel.setTitulo(barraBusqueda.getText());
			}
		});
	}
	
	private void asignarCentral(JComponent nuevo) {
		centralPanel.removeAll();
		centralPanel.add(nuevo);
		centralPanel.updateUI();
	}
	
	public void setCentralPanel(JComponent nuevo) {
		if (nuevo == null)
			nuevo = centralVacio;
		else if (centralPanel.getComponentCount() > 0) {
			anteriores.push((JComponent) centralPanel.getComponent(0));
			botonVolver.setEnabled(true);
		}
		asignarCentral(nuevo);
	}
	
	public void retornarCentralPanel() {
		asignarCentral(anteriores.pop());
		botonVolver.setEnabled(!anteriores.empty());
	}
	
	public void seleccionarCatalogo(TipoContenido t) {
		catalogoPanel.setTipo(t);
		setCentralPanel(catalogoPanel);
	}
	
	public Font mainFont(int tam) {
		return new Font("Tahoma", Font.BOLD, tam);
	}
}