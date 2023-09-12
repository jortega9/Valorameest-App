package vista.contenido;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controlador.Controlador;
import modelo.contenido.Contenido;
import utils.ImageParser;
import vista.MainWindow;

public class PortadaVerticalPanel extends PortadaElementoPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 180;
	private static final int HEIGHT = 370;
	private static final Dimension DIM = new Dimension(WIDTH, HEIGHT);

	private static final int IMG_WIDTH = 200;
	private static final int IMG_HEIGHT = 300;

	public PortadaVerticalPanel(MainWindow main, Contenido cont, Controlador control) {
		super(main, cont, control);
	}
	
	public PortadaVerticalPanel(MainWindow main, Contenido cont, Controlador control, String anotacion, String anterior) {
		super(main, cont, control, anotacion, anterior);
	}
	
	@Override
	protected void initGUI() {
		setPreferredSize(DIM);
		setMaximumSize(DIM);
		setMinimumSize(DIM);
		setSize(DIM);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		
		JPanel supPanel = new JPanel();
		supPanel.setLayout(new BoxLayout(supPanel, BoxLayout.X_AXIS));
		JLabel val = getValoracionLabel();
		val.setFont(main.mainFont(14));
		supPanel.add(val);
		supPanel.add(Box.createHorizontalGlue());
		JLabel tip = new JLabel(cont.getTipo().toString());
		tip.setFont(main.mainFont(14));
		supPanel.add(tip);
		supPanel.setMaximumSize(new Dimension(WIDTH + 15, 20));
		supPanel.setOpaque(false);
		
		JPanel centrPanel = new JPanel(null);
		img = new JLabel(ImageParser.iconFromURL(cont.getImagenURL(), IMG_WIDTH, IMG_HEIGHT));
		centrPanel.add(img);
		img.setBounds(0, 0, IMG_WIDTH, IMG_HEIGHT);
		centrPanel.setMaximumSize(new Dimension(IMG_WIDTH, IMG_HEIGHT));
		centrPanel.setMinimumSize(new Dimension(IMG_WIDTH, IMG_HEIGHT));
		centrPanel.setPreferredSize(new Dimension(IMG_WIDTH, IMG_HEIGHT));
		centrPanel.setSize(new Dimension(IMG_WIDTH, IMG_HEIGHT));
		
		nombre = new JLabel(cont.getTitulo());
		nombre.setFont(new Font("Tahoma", Font.BOLD, 20));
		nombre.setAlignmentX(CENTER_ALIGNMENT);
		nombre.setMaximumSize(new Dimension(WIDTH + 15, 30));
		
		add(supPanel);
		add(centrPanel);
		add(nombre);
	}
}
