package vista.contenido;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.contenido.Contenido;
import utils.ImageParser;
import vista.MainWindow;

public class PortadaHorizontalPanel extends PortadaElementoPanel {

	private static final long serialVersionUID = 1L;
	// Tamaño
	private static final int WIDTH = 300;
	private static final int HEIGHT = 150;
	private static final Dimension DIM = new Dimension(WIDTH, HEIGHT);

	private static final int IMG_WIDTH = 120;
	private static final int IMG_HEIGHT = 125;
	
	
	public PortadaHorizontalPanel(MainWindow main, Contenido contenido, Controlador control) {
		super(main, contenido, control);
	}
	
	public PortadaHorizontalPanel(MainWindow main, Contenido cont, Controlador control, String anotacion, String anterior) {
		super(main, cont, control, anotacion, anterior);
	}
	
	@Override
	protected void initGUI() {
		// Configura el contentPane
		setPreferredSize(DIM);
		setMaximumSize(new Dimension(2 * WIDTH, HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setBackground(Color.WHITE);
		
		// Configura el panel de la imagen
		img = new JLabel(ImageParser.iconFromURL(cont.getImagenURL(), IMG_WIDTH, IMG_HEIGHT));
		
		// Configuracion del panel de informacion
		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(Color.WHITE);
		panelInfo.setLayout(new GridLayout(0, 1));
		
		// Configuracion del nombre del elemento
		nombre = new JLabel(cont.getTitulo());
		nombre.setFont(main.mainFont(20));
		
		// Configuracion de la valoracion del elemento
		JLabel valoracion = getValoracionLabel();
		valoracion.setHorizontalAlignment(SwingConstants.LEFT);
		valoracion.setFont(main.mainFont(16));
		
		JLabel seguidores = new JLabel(cont.getSeguidores() + " seguidores");
		seguidores.setFont(main.mainFont(16));
		
		// A�adimos los elementos al panel de informacion
		panelInfo.add(nombre);
		panelInfo.add(valoracion);
		panelInfo.add(seguidores);
		
		// A�adimos los elementos al contentPane
		add(img);
		add(Box.createHorizontalStrut(5));
		add(panelInfo);
	}
}
