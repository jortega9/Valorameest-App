package vista.contenido;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controlador.Controlador;
import controlador.eventos.BuscarPorIDEvent;
import controlador.eventos.ModificarPerfilEvent;
import modelo.contenido.Contenido;
import modelo.contenido.Serie;
import modelo.perfil.Perfil;
import modelo.perfil.RegSerie;
import vista.MainWindow;
import vista.PanelObservadorAdapter;

public class InfoSeriePanel extends PanelObservadorAdapter {
	
	private static final long serialVersionUID = 1L;

	private Serie ser, temp;
	private Controlador control;
	private MainWindow main;
	private boolean buscandoTemp = false;
	
	private List<JCheckBox> checksCaps;
	private JPanel temps;
	
	public InfoSeriePanel(Serie s, Controlador control, MainWindow main) {
		ser = s;
		this.main = main;
		this.control = control;
		initGUI();
		control.addObservador(this);
	}
	
	private void initGUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		
		JPanel panelTemps = new JPanel(new BorderLayout());
		panelTemps.setOpaque(false);
		JLabel tempsLabel = new JLabel(" Otras temporadas disponibles:");
		tempsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panelTemps.add(tempsLabel, BorderLayout.NORTH);
		if (ser.getOtrasTemp().size() == 0)
			panelTemps.add(new JLabel("    Temporada única"), BorderLayout.CENTER);
		else {
			temps = new JPanel(new GridLayout(0, 1, 10, 10));
			temps.setOpaque(false);
			checksCaps = new ArrayList<>();
			panelTemps.add(temps, BorderLayout.CENTER);
		}

		JPanel panelCapsHead = new JPanel(new GridLayout(1, 3, 10, 10));
		panelCapsHead.setMaximumSize(new Dimension(600, 30));
		panelCapsHead.setOpaque(false);
		JLabel capsLabel = new JLabel(" Episodios"), vistoLabel = new JLabel(" Vistos");
		capsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		vistoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JPanel vistoPanel = new JPanel(); vistoPanel.setOpaque(false); vistoPanel.add(vistoLabel);
		JButton botonClear = new JButton("Marcar como no visto");
		botonClear.addActionListener((e) -> control.runEvent(new ModificarPerfilEvent((p) -> {
			RegSerie reg = (RegSerie) p.getReg(ser);
			if (reg != null) reg.setCapsVistos(0);
		})));
		panelCapsHead.add(capsLabel);
		panelCapsHead.add(vistoPanel);
		panelCapsHead.add(botonClear);
		
		JPanel panelEps = new JPanel(new GridLayout(0, 2, 10, 10));
		checksCaps = new ArrayList<>();
		for (int i = ser.getNumCaps(); i > 0; --i) {
			panelEps.add(new JLabel("Episodio " + i));
			panelEps.add(crearCheckBoxCapitulo(i));
		}
		JScrollPane spEps = new JScrollPane(panelEps);
		spEps.setMaximumSize(new Dimension(600, 500));
		spEps.setPreferredSize(new Dimension(600, 500));
		spEps.setOpaque(false);
		
		JPanel auxEps = new JPanel(), panelCapitulos = new JPanel();
		auxEps.setLayout(new BoxLayout(auxEps, BoxLayout.Y_AXIS));
		panelCapitulos.setLayout(new BoxLayout(panelCapitulos, BoxLayout.X_AXIS)); 
		auxEps.add(panelCapsHead); auxEps.add(spEps);
		auxEps.setOpaque(false); panelCapitulos.setOpaque(false);
		panelCapitulos.add(auxEps);
		panelCapitulos.add(Box.createHorizontalGlue());
		
		add(panelTemps);
		add(Box.createVerticalStrut(30));
		add(panelCapitulos);
		add(Box.createVerticalGlue());
	}
	
	private JLabel crearTempLabel(String id) {
		buscarTemp(id);
		Serie temporada = temp;
		JLabel lab = new JLabel("    - " + temp.getTitulo());
		lab.setFont(new Font(null, Font.PLAIN, 16));
		lab.setForeground(Color.BLUE);
		lab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				main.setCentralPanel(InfoContenidoPanel.builder(temporada, control, main).hasDetalles().anotacion("Relacionado con " + ser.getTitulo(), Color.WHITE).retorno(main, ser.getTitulo()).build());
			}
		});
		
		return lab;
	}
	
	private JCheckBox crearCheckBoxCapitulo(int i) {
		JCheckBox cb = new JCheckBox();
		cb.addActionListener((e) -> { 
			cb.setSelected(false);
			control.runEvent(new ModificarPerfilEvent((p) -> ((RegSerie) p.usarReg(ser)).setCapsVistos(i)));
		});
		checksCaps.add(cb);
		return cb;
	}
	
	private void buscarTemp(String id) {
		buscandoTemp = true;
		control.runEvent(new BuscarPorIDEvent(id));
	}
	
	@Override
	public void onCambioPerfil(Perfil p) {
		if (p != null) {
			RegSerie reg = (RegSerie) p.getReg(ser);
			int i = 0, tope = (reg == null ? ser.getNumCaps() : ser.getNumCaps() - reg.getCapsVistos());
			for (; i < tope; ++i)
				checksCaps.get(i).setSelected(false);
			for (; i < ser.getNumCaps(); ++i)
				checksCaps.get(i).setSelected(true);
		}
		else
			for (JCheckBox cb : checksCaps)
				cb.setSelected(false);
	}
	
	@Override
	public void onRegister(List<Contenido> nuevo, Perfil p) {
		onCambioPerfil(p);
		if (ser.getOtrasTemp().size() > 0) {
			for (String s : ser.getOtrasTemp()) {
				temps.add(crearTempLabel(s));
			}
			temps.updateUI();
		}
	}
	
	@Override 
	public void onBuscarPorId(Contenido cont) {
		if (buscandoTemp) {
			temp = (Serie) cont;
			buscandoTemp = false;
		}
	}
}
