package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controlador.Controlador;
import controlador.eventos.AplicarFiltrosEvent;
import controlador.eventos.CargarResultadosEvent;
import controlador.eventos.ClearFiltrosEvent;
import controlador.eventos.NewFiltroEvent;
import controlador.eventos.RemoveFiltroEvent;
import modelo.contenido.Contenido;
import modelo.contenido.Genero;
import modelo.contenido.TipoContenido;
import modelo.filtros.FiltroGenero;
import modelo.filtros.FiltroOrden;
import modelo.filtros.FiltroTipo;
import modelo.filtros.FiltroTitulo;
import modelo.perfil.Perfil;
import utils.VBD;
import vista.contenido.PortadaVerticalPanel;

public class CatalogoPanel extends PanelObservadorAdapter implements  ItemListener {
	
	private static final long serialVersionUID = 1L;
	
	private static final int TAM_BUSQUEDA = 10;
	private static final String[] ORDENES_H = { "Valoración", "Seguidores", "Más nuevos", "Más antiguos", "Alfabéticamente" };
	private static final Object[][] ORDENES_BD = { { VBD.VAL_CONT, true }, { VBD.SEG_CONT, true }, { VBD.FECHA_CONT, true }, { VBD.FECHA_CONT, false }, { VBD.TIT_CONT, false } };
	
	private TipoContenido tipo;
	private JPanel tablaCatalogo;
	private JLabel tituloCatalogo;
	private JComboBox<String> orden;
	private JButton verMas;
	private MainWindow main;
	
	private Controlador control;
	private String titulo;
	
	public CatalogoPanel(Controlador control, MainWindow main) {
		this.main = main;
		this.control = control;
		titulo = "";
		initGUI();
		control.addObservador(this);
	}
	
	// Inicia la GUI
	void initGUI () {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		
		JPanel titPanel = new JPanel();
		titPanel.setBackground(Color.WHITE);
		tituloCatalogo = new JLabel(tipo == null ? "Catálogo general" : "Catálogo de " + tipo.toString().toLowerCase() + "s");
		tituloCatalogo.setFont(new Font("Tahoma", Font.PLAIN, 40));
		titPanel.add(tituloCatalogo);
		titPanel.add(Box.createVerticalStrut(50));
		
		//Configuracion del panel del catálogo
		tablaCatalogo = new JPanel(new GridLayout(0, 5, 15, 15));
		tablaCatalogo.setOpaque(false);
		verMas = new JButton("Ver más...");
		verMas.setBackground(Color.WHITE);
		verMas.addActionListener((e) -> control.runEvent(new CargarResultadosEvent(TAM_BUSQUEDA)));
		
		JPanel tablaCatalogoPanel = new JPanel();
		tablaCatalogoPanel.setLayout(new BoxLayout(tablaCatalogoPanel, BoxLayout.Y_AXIS));
		tablaCatalogoPanel.setBackground(Color.WHITE);
		tablaCatalogoPanel.add(tablaCatalogo);
		tablaCatalogoPanel.add(Box.createVerticalStrut(30));
		tablaCatalogoPanel.add(verMas);
		tablaCatalogoPanel.add(Box.createVerticalStrut(50));
		
		JPanel catalogoPanel = new JPanel();
		catalogoPanel.setLayout(new BoxLayout(catalogoPanel, BoxLayout.X_AXIS));
		catalogoPanel.add(Box.createHorizontalGlue());
		catalogoPanel.add(tablaCatalogoPanel);
		catalogoPanel.add(Box.createHorizontalGlue());
		
		//Configuración del panel de filtros
		orden = new JComboBox<>(ORDENES_H);
		JPanel genPanel = new JPanel();
		genPanel.setLayout(new BoxLayout(genPanel, BoxLayout.Y_AXIS));
		for (Genero g : Genero.values()) {
			JCheckBox gBox = new JCheckBox(g.toString());
			gBox.addItemListener(this);
			genPanel.add(gBox);
		}
		JScrollPane genSp = new JScrollPane(genPanel);
		genSp.setPreferredSize(new Dimension(genSp.getPreferredSize().width, 200));

		JPanel filtrosPanel = new JPanel();
		filtrosPanel.setLayout(new BoxLayout(filtrosPanel, BoxLayout.Y_AXIS));
		filtrosPanel.setOpaque(false);
		filtrosPanel.add(new JLabel("Ordenar por: "));
		filtrosPanel.add(orden);
		orden.setAlignmentX(LEFT_ALIGNMENT);
		filtrosPanel.add(Box.createVerticalStrut(20));
		filtrosPanel.add(new JLabel("Filtrar por géneros: "));
		filtrosPanel.add(genSp);
		
		JButton bAplicar = new JButton("Aplicar filtros"), bEliminar = new JButton("Eliminar filtros");
		bAplicar.setBackground(new Color(100, 255, 100));
		bAplicar.addActionListener((e) -> buscar());
		bEliminar.setBackground(new Color(255, 150, 150));
		bEliminar.addActionListener((e) -> { 
			control.runEvent(new ClearFiltrosEvent());
			buscar();
		});

		JPanel izqPanel = new JPanel();
		izqPanel.setPreferredSize(new Dimension(200, this.getHeight()));
		izqPanel.setBackground(Color.WHITE);
		izqPanel.add(filtrosPanel);
		izqPanel.add(bAplicar);
		izqPanel.add(bEliminar);
		
		add(titPanel, BorderLayout.NORTH);
		add(new JScrollPane(catalogoPanel), BorderLayout.CENTER);
		add(izqPanel, BorderLayout.WEST);
	}
	
	public void setTipo(TipoContenido t) {
		tipo = t;
		tituloCatalogo.setText(tipo == null ? "Catálogo general" : "Catálogo de " + tipo.toString().toLowerCase() + "s");
		control.runEvent(new ClearFiltrosEvent());
		if (t != null) 
			control.runEvent(new NewFiltroEvent(new FiltroTipo(t)));
		buscar();
	}
	
	public void setTitulo(String tit) {
		titulo = tit;
	}
	
	public void buscar() {
		control.runEvent(new NewFiltroEvent(new FiltroOrden((String) ORDENES_BD[orden.getSelectedIndex()][0], (boolean) ORDENES_BD[orden.getSelectedIndex()][1])));
		control.runEvent(new NewFiltroEvent(new FiltroTitulo(titulo)));
		control.runEvent(new AplicarFiltrosEvent(TAM_BUSQUEDA));
	}
	
	private void addPanelContenido(Contenido c) {
		JPanel p = new PortadaVerticalPanel(main, c, control, (tablaCatalogo.getComponentCount() > 0 ? null : "Mejor resultado"), tituloCatalogo.getText());
		tablaCatalogo.add(p);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED)
			control.runEvent(new NewFiltroEvent(new FiltroGenero(Genero.valueOf(((AbstractButton) e.getItemSelectable()).getText()))));
		else
			control.runEvent(new RemoveFiltroEvent(new FiltroGenero(Genero.valueOf(((AbstractButton) e.getItemSelectable()).getText()))));
	}

	@Override
	public void onNuevaBusqueda() {
		tablaCatalogo.removeAll();
	}

	@Override
	public void onExtraerDeBusqueda(List<Contenido> nuevo) {
		for (Contenido c : nuevo)
			addPanelContenido(c);
		verMas.setEnabled(nuevo.size() == TAM_BUSQUEDA);
		
		if (tablaCatalogo.getComponentCount() == 0)
			tablaCatalogo.add(new JLabel("Sin resultados..."));
		
		tablaCatalogo.updateUI();
	}

	@Override
	public void onRegister(List<Contenido> nuevo, Perfil p) {
		onExtraerDeBusqueda(nuevo);
	}

	@Override
	public void onError(String err) {
		JOptionPane.showMessageDialog(null, err);
	}
}

