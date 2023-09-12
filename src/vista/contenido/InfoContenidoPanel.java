package vista.contenido;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import controlador.Controlador;
import controlador.eventos.ModificarPerfilEvent;
import modelo.contenido.Contenido;
import modelo.contenido.Genero;
import modelo.contenido.Pelicula;
import modelo.contenido.Serie;
import modelo.contenido.Videojuego;
import modelo.perfil.EstadoReg;
import modelo.perfil.Perfil;
import modelo.perfil.Registro;
import utils.ImageParser;
import vista.MainWindow;
import vista.PanelObservadorAdapter;

public class InfoContenidoPanel extends PanelObservadorAdapter {

	private static final long serialVersionUID = 1L;
	
	private static final int IZQ_WIDTH = 320;
	private static final Color BG_MARCO = new Color(220, 225, 255);
	
	private Contenido contenido;
	private Controlador control; 
	private Registro reg;
	
	private JPanel panelConcreto;
	private JPanel panelArriba;
	private JPanel panelValorar;
	private JLabel valLabel;
	private JButton bValorar;
	private List<Object[]> botonNombreEstado;
	
	private InfoContenidoPanel(Controlador control, Contenido contenido, JPanel conc, JPanel arriba) {
		this.contenido = contenido;
		this.control = control;
		control.addObservador(this);
		panelArriba = arriba;
		if (conc == null) {
			panelConcreto = new JPanel();
			panelConcreto.setVisible(false);
		}
		else {
			panelConcreto = conc;
		}
		initGUI();
	}
	
	public static Builder builder(Contenido c, Controlador co, MainWindow main) {
		return new Builder(c, co, main);
	}
	
	private void initGUI() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		add(panelArriba, BorderLayout.NORTH);
		add(panelIzq(), BorderLayout.WEST);
		add(new JScrollPane(panelCentral(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
	}

	private JPanel panelCentral() {
		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(Color.WHITE);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.X_AXIS));
        panelDatos.setPreferredSize(new Dimension(panelCentral.getWidth(), 150));
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        Font f1 = new Font("Tahoma", Font.PLAIN, 20), f2 = new Font("Tahoma", Font.BOLD, 22);

        JLabel tipoLab = new JLabel(contenido.getTipo().toString() + "   ");
        tipoLab.setFont(f2);
        
        JPanel panelVal = new JPanel();
        panelVal.setOpaque(false);
        panelVal.setLayout(new BoxLayout(panelVal, BoxLayout.Y_AXIS));
        JLabel valLabHead = new JLabel("Valoración:");
        valLabHead.setFont(f1);
        panelVal.add(valLabHead);
        panelVal.add(Box.createVerticalStrut(30));
        JLabel valLab = new JLabel(new DecimalFormat("0.00").format(contenido.getValoracion()) + "/10");
        valLab.setFont(f2);
        panelVal.add(valLab);
        
        JPanel panelSeg = new JPanel();
        panelSeg.setOpaque(false);
        panelSeg.setLayout(new BoxLayout(panelSeg, BoxLayout.Y_AXIS));
        JLabel segLabHead = new JLabel("Seguidores:");
        segLabHead.setFont(f1);
        panelSeg.add(segLabHead);
        panelSeg.add(Box.createVerticalStrut(30));
        JLabel segLab = new JLabel(NumberFormat.getInstance().format(contenido.getSeguidores()));
        segLab.setFont(f2);
        panelSeg.add(segLab);
        
        JPanel panelFecha = new JPanel();
        panelFecha.setOpaque(false);
        panelFecha.setLayout(new BoxLayout(panelFecha, BoxLayout.Y_AXIS));
        JLabel fechaLabHead = new JLabel("Fecha de lanzamiento:");
        fechaLabHead.setFont(f1);
        panelFecha.add(fechaLabHead);
        panelFecha.add(Box.createVerticalStrut(30));
        JLabel fechaLab = new JLabel(contenido.getFecha());
        fechaLab.setFont(f2);
        panelFecha.add(fechaLab);
        
        JPanel panelDuracion = new JPanel();
        panelDuracion.setOpaque(false);
        panelDuracion.setLayout(new BoxLayout(panelDuracion, BoxLayout.Y_AXIS));
        JLabel durLabHead = new JLabel("Duración:");
        durLabHead.setFont(f1);
        panelDuracion.add(durLabHead);
        panelDuracion.add(Box.createVerticalStrut(30));
        JLabel durLab = new JLabel(contenido.getDuracionFormato());
        durLab.setFont(f2);
        panelDuracion.add(durLab);
        
        panelDatos.add(Box.createHorizontalGlue());
        panelDatos.add(tipoLab);
        panelDatos.add(Box.createHorizontalGlue());
        panelDatos.add(panelVal);
        panelDatos.add(Box.createHorizontalGlue());
        panelDatos.add(panelSeg);
        panelDatos.add(Box.createHorizontalGlue());
        panelDatos.add(panelFecha);
        panelDatos.add(Box.createHorizontalGlue());
        panelDatos.add(panelDuracion);
        panelDatos.add(Box.createHorizontalGlue());
        
        JPanel panelGeneros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelGeneros.setBackground(Color.WHITE);
        JLabel genHead = new JLabel("Géneros: ");
        genHead.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panelGeneros.add(genHead);
        for (Genero g : contenido.getGeneros()) {
        	JLabel gLab = new JLabel(g.toString());
        	gLab.setForeground(Color.WHITE);
        	gLab.setFont(new Font(null, Font.ITALIC, 14));
        	JPanel gPan = new JPanel();
        	gPan.add(gLab);
        	gPan.setBackground(new Color(150, 150, 255));
        	panelGeneros.add(Box.createHorizontalStrut(15));
        	panelGeneros.add(gPan);
        }
        JScrollPane scrollGeneros = new JScrollPane(panelGeneros, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollGeneros.setBorder(BorderFactory.createEmptyBorder());
        scrollGeneros.setPreferredSize(new Dimension(1000, 60));
        scrollGeneros.setMaximumSize(new Dimension(1000, 60));
        JPanel genPanel = new JPanel(new GridLayout(1, 1, 0, 0));
        genPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        genPanel.setOpaque(false);
        genPanel.add(scrollGeneros);
        
        JPanel panelDesc = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDesc.setBackground(Color.WHITE);
        JLabel descHead = new JLabel("Descripción:");
        descHead.setFont(new Font("Tahoma", Font.PLAIN, 18));
        panelDesc.add(descHead);
        panelDesc.add(Box.createHorizontalGlue());
        JTextArea descText = new JTextArea(contenido.getDescripcion());
        descText.setPreferredSize(new Dimension(750, descText.getText().length() / 5 + 15));
        descText.setFont(new Font(null, Font.PLAIN, 16));
        descText.setLineWrap(true);
        descText.setWrapStyleWord(true);
        descText.setEditable(false);
        panelDesc.add(descText);
        panelDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, descText.getPreferredSize().height));
        
        panelCentral.add(panelDatos);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(genPanel);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(panelDesc);
        panelCentral.add(Box.createVerticalStrut(30));
        panelCentral.add(panelConcreto);
        panelCentral.add(Box.createVerticalGlue());
		return panelCentral;
	}

	private JPanel panelIzq() {
		JPanel panel = new JPanel();
		panel.setBackground(BG_MARCO);
		
		JLabel imgLabel = new JLabel(ImageParser.iconFromURL(contenido.getImagenURL(), 280, 250));
		imgLabel.setToolTipText("ID: " + contenido.getID() + " - " + contenido.getTitulo());
		panel.add(imgLabel);
		
		JLabel tuVal = new JLabel("  Tu valoración:");
		tuVal.setFont(new Font(null, Font.PLAIN, 16));
		panel.add(tuVal);
		panel.add(new JLabel(ImageParser.iconFromFile("star.jpg", 20, 20)));
		valLabel = new JLabel((reg == null || reg.getValoracion() < 0) ? "N/A" : Integer.toString((int)reg.getValoracion()));
		valLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(valLabel);
		
		panel.add(Box.createHorizontalStrut(30));
		
		bValorar = new JButton("Valorar");
		bValorar.setPreferredSize(new Dimension(80, 20));
		panel.add(bValorar);
		
		panel.add(Box.createVerticalStrut(20));
		
		panelValorar = new JPanel();
		panelValorar.setOpaque(false);
		panelValorar.removeAll();
		panel.add(panelValorar);
		bValorar.addActionListener((e) -> {
			activarValorar();
			bValorar.setEnabled(false);
		});
		
		botonNombreEstado = new ArrayList<>();
		panel.add(botonEstado("Favoritos", "", EstadoReg.FAVORITOS));
		panel.add(botonEstado("Siguiendo", "", EstadoReg.SIGUIENDO));
		panel.add(botonEstado("Pendientes", "", EstadoReg.PENDIENTES));
		panel.add(botonEstado("Finalizados", "", EstadoReg.FINALIZADOS));
		panel.add(botonEstado("Abandonados", "", EstadoReg.ABANDONADOS));
		
		panel.setPreferredSize(new Dimension(IZQ_WIDTH, 500));
		return panel;
	}
	
	private JButton botonEstado(String nombre, String icono, EstadoReg estado) {
		JButton boton = new JButton((reg != null && reg.hasEstado(estado) ? "Eliminar de " : "Añadir a ") + nombre);
		boton.setOpaque(false);
		boton.setBorderPainted(false);
		boton.setPreferredSize(new Dimension(IZQ_WIDTH, 40));
		boton.setHorizontalAlignment(SwingConstants.LEFT);
		boton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		boton.addActionListener((e) -> {
			control.runEvent(new ModificarPerfilEvent((p) -> p.usarReg(contenido).switchEstado(estado)));
		});
		Object[] bne = { boton, nombre, estado };
		botonNombreEstado.add(bne);
		return boton;
	}
	
	private void activarValorar() {
		JSlider slider = new JSlider(0, 10);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setOpaque(false);
		panelValorar.add(slider);
		JButton aceptar = new JButton("Aceptar");
		aceptar.setPreferredSize(new Dimension(80, 20));
		aceptar.addActionListener((e) -> {
			control.runEvent(new ModificarPerfilEvent((p) -> p.usarReg(contenido).setValoracion(slider.getValue())));
			bValorar.setEnabled(true);
			panelValorar.removeAll();
			updateUI();
		});
		panelValorar.add(aceptar);
		updateUI();
	}

	@Override
	public void onNuevaBusqueda() {
	}

	@Override
	public void onExtraerDeBusqueda(List<Contenido> nuevo) {
	}

	@Override
	public void onBuscarPorId(Contenido cont) {
	}

	@Override
	public void onCambioPerfil(Perfil p) {
		if (p != null)
			reg = p.getReg(contenido);
		else
			reg = null;
		for (Object[] o : botonNombreEstado)
			((JButton) o[0]).setText((reg != null && reg.hasEstado((EstadoReg) o[2]) ? "Eliminar de " : "Añadir a ") + o[1].toString());
		valLabel.setText((reg == null || reg.getValoracion() < 0) ? "N/A" : Integer.toString((int)reg.getValoracion()));
		updateUI();
	}

	@Override
	public void onRegister(List<Contenido> nuevo, Perfil p) {
		if (p != null)
			reg = p.getReg(contenido);
		else
			reg = null;
		updateUI();
	}

	@Override
	public void onError(String err) {
	}
	
	// - - - - - - - - - - BUILDER - - - - - - - - - - //
	public static final class Builder {
		private PanelObservadorAdapter panelConcreto;
		private JPanel panelArriba, panelAnot, panelVolver;
		private Contenido cont;
		private Controlador control;
		private MainWindow main;
		
		private Builder(Contenido contenido, Controlador control, MainWindow main) {
			cont = contenido;
			this.main = main;
			this.control = control;
			initGUI();
		}
		
		private void initGUI() {
			JPanel titP = new JPanel();
			JLabel nombre = new JLabel(cont.getTitulo());
			nombre.setToolTipText("ID: " + cont.getID() + " - " + cont.getTitulo());
			nombre.setFont(new Font("Tahoma", Font.BOLD, 24));
			titP.add(nombre);
			titP.setMaximumSize(new Dimension(IZQ_WIDTH, 35));
			titP.setPreferredSize(new Dimension(IZQ_WIDTH, 35));
			titP.setOpaque(false);
			
			panelAnot = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panelAnot.setOpaque(false);
			panelVolver = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			panelVolver.setOpaque(false);
			
			panelArriba = new JPanel();
			panelArriba.setLayout(new BoxLayout(panelArriba, BoxLayout.X_AXIS));
			titP.setAlignmentX(LEFT_ALIGNMENT);
			panelArriba.add(titP);
			panelArriba.add(panelAnot);
			panelArriba.add(Box.createHorizontalGlue());
			panelArriba.add(panelVolver);
			panelArriba.setBackground(BG_MARCO);
			panelArriba.updateUI();
		}
		
		public InfoContenidoPanel build() {
			return new InfoContenidoPanel(control, cont, panelConcreto, panelArriba);
		}
		
		public Builder hasDetalles() {
			switch (cont.getTipo()) {
			case VIDEOJUEGO:
				panelConcreto = new InfoVideojuegoPanel((Videojuego) cont, control);
				break;
			case SERIE:
				panelConcreto = new InfoSeriePanel((Serie) cont, control, main);
				break;
			case PELICULA:
				panelConcreto = new InfoPeliculaPanel((Pelicula) cont, control);
				break;
			}
			return this;
		}
		
		public Builder anotacion(String nota, Color color) {
			JLabel topLabel = new JLabel(nota);
			topLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
			topLabel.setForeground(color);
			panelAnot.add(topLabel);
			return this;
		}
		
		public Builder retorno(MainWindow main, String anterior) {
			JButton volverB = new JButton("Volver a " + anterior, ImageParser.iconFromFile("volver.png", 20, 20));
			volverB.setBackground(Color.WHITE);
			volverB.setBorderPainted(false);
			volverB.addActionListener((e) -> main.retornarCentralPanel());
			panelVolver.add(volverB);
			return this;
		}
	}
}
