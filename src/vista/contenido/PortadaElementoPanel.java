package vista.contenido;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controlador.Controlador;
import modelo.contenido.Contenido;
import utils.ImageParser;
import vista.MainWindow;

public abstract class PortadaElementoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected String anotacion, anterior;
	protected MainWindow main;
	protected Controlador control;
	protected Contenido cont;
	protected JLabel nombre, img;
	
	public PortadaElementoPanel(MainWindow main, Contenido cont, Controlador control) {
		this.main = main;
		this.cont = cont;
		this.control = control;
		initGUI();
		acciones();
	}
	
	public PortadaElementoPanel(MainWindow main, Contenido cont, Controlador control, String anotacion, String anterior) {
		this(main, cont, control);
		this.anotacion = anotacion;
		this.anterior = anterior;
	}
	
	protected abstract void initGUI();
	
	protected void acciones() {
		MouseAdapter click = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				InfoContenidoPanel.Builder infoBuilder = InfoContenidoPanel.builder(cont, control, main).hasDetalles();
				if (anotacion != null)
					infoBuilder.anotacion(anotacion, Color.YELLOW.darker());
				if (anterior != null)
					infoBuilder.retorno(main, anterior);
				main.setCentralPanel(infoBuilder.build());
			}
		};
		img.addMouseListener(click);
		nombre.addMouseListener(click);
		img.setToolTipText(cont.getTitulo());
		nombre.setToolTipText(cont.getTitulo());
	}
	
	protected JLabel getValoracionLabel() {
		JLabel label = new JLabel(ImageParser.iconFromFile("star.jpg", 15, 15));
		label.setText(new DecimalFormat("0.00").format(cont.getValoracion()));
		return label;
	}
}
