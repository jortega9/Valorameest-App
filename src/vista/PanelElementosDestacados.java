package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controlador.Controlador;
import controlador.eventos.AplicarFiltrosEvent;
import controlador.eventos.NewFiltroEvent;
import modelo.contenido.Contenido;
import modelo.contenido.TipoContenido;
import modelo.filtros.FiltroOrden;
import modelo.filtros.FiltroTipo;
import modelo.perfil.Perfil;
import utils.VBD;
import vista.contenido.PortadaHorizontalPanel;

public class PanelElementosDestacados extends PanelObservadorAdapter {
	
	private static final long serialVersionUID = 1L;
	
	private MainWindow main;
	private Controlador control;
	private TipoContenido tipo;
	List<Contenido> cont;
	
	JButton verCatalogo;

	public PanelElementosDestacados(MainWindow main, Controlador control, TipoContenido tipo) {
		this.main = main;
		this.control = control;
		this.tipo = tipo;
		initDatos();
		control.addObservador(this);
		initGUI();
		acciones();
	}
	
	private void initDatos() {
		control.runEvent(new NewFiltroEvent(new FiltroTipo(tipo)));
		control.runEvent(new NewFiltroEvent(new FiltroOrden(VBD.VAL_CONT, true)));
		control.runEvent(new AplicarFiltrosEvent(3));
	}
	
	private void initGUI() {
		setBackground(Color.WHITE);
		setLayout(new GridLayout(1, 4, 5, 5));
		
		JPanel destacado = new JPanel();
		destacado.setBackground(Color.WHITE);
		destacado.setBorder(new LineBorder(Color.BLACK, 1, true));
		destacado.setLayout(new BorderLayout(0, 0));
		
		// BorderLayout North
		JLabel label = new JLabel("   " + tipo.toString().toLowerCase() + "s:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		destacado.add(label, BorderLayout.NORTH);
		
		// BorderLayout Center
		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(Color.WHITE);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		
		if (cont.size() > 0)
			for (int i = 0; i < cont.size(); ++i)
				panelCentral.add(new PortadaHorizontalPanel(main, cont.get(i), control, "Top " + (i + 1) + " en " + tipo.toString().toLowerCase() + "s", "Inicio"));
		else
			panelCentral.add(new JLabel("No se encontraron elementos"));
		
		destacado.add(panelCentral, BorderLayout.CENTER);
		
		// BorderLayout South
		JPanel panelBoton = new JPanel();
		panelBoton.setBackground(Color.WHITE);
		panelBoton.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelBoton.setLayout(new BorderLayout(0, 0));
		verCatalogo = new JButton("Ver más");
		verCatalogo.setBackground(Color.WHITE);
		panelBoton.add(verCatalogo, BorderLayout.EAST);
		
		destacado.add(panelBoton, BorderLayout.SOUTH);
		
		add(destacado);
	}
	
	private void acciones() {
		verCatalogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.seleccionarCatalogo(tipo);
			}
		});
	}

	@Override
	public void onRegister(List<Contenido> nuevo, Perfil p) {
		if (nuevo.size() > 3)
			throw new IllegalArgumentException("Demasiados elementos en el panel de destacados(" + tipo.toString().toLowerCase() + "s)");
		cont = nuevo;
	}
}
