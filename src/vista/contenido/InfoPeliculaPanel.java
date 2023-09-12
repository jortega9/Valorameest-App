package vista.contenido;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import controlador.Controlador;
import controlador.eventos.ModificarPerfilEvent;
import modelo.contenido.Contenido;
import modelo.contenido.Pelicula;
import modelo.perfil.Perfil;
import modelo.perfil.RegPelicula;
import vista.PanelObservadorAdapter;

public class InfoPeliculaPanel extends PanelObservadorAdapter {

	private static final long serialVersionUID = 1L;

	private Pelicula pel;
	private Controlador control;
	
	private JLabel minActual;
	private JProgressBar minBarra;
	private JPanel panelModif;
	
	public InfoPeliculaPanel(Pelicula p, Controlador control) {
		this.control = control;
		pel = p;
		initGUI();
		control.addObservador(this);
	}
	
	private void initGUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		
		JPanel minActualPanel = new JPanel(new GridLayout(3, 1, 5, 5));
		minActualPanel.setMaximumSize(new Dimension(400, 100));
		minActualPanel.setPreferredSize(new Dimension(400, 100));
		minActualPanel.setOpaque(false);
		minActual = new JLabel("");
		minActual.setFont(new Font("Tahoma", Font.PLAIN, 18));
		minBarra = new JProgressBar();
		minActualPanel.add(minActual);
		minActualPanel.add(minBarra);

		JSpinner minSpin = new JSpinner(new SpinnerNumberModel(0, 0, pel.getDuracionMins(), 1));
		minSpin.setPreferredSize(new Dimension(50, 20));
		JButton botonGuardar = new JButton("Actualizar");
		botonGuardar.setPreferredSize(new Dimension(100, 20));
		botonGuardar.addActionListener((e) -> control.runEvent(new ModificarPerfilEvent((p) -> {
			((RegPelicula) p.usarReg(pel)).setMinActual((int) minSpin.getValue());
		})));
		panelModif = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelModif.setOpaque(false);
		panelModif.add(new JLabel("¿En qué minuto te quedaste? "));
		panelModif.add(minSpin);
		panelModif.add(botonGuardar);
		minActualPanel.add(panelModif);

		JPanel auxMinAct = new JPanel(new FlowLayout(FlowLayout.LEFT));
		auxMinAct.setOpaque(false);
		auxMinAct.add(minActualPanel);
		
		add(auxMinAct);
		add(Box.createVerticalGlue());
	}
	
	@Override
	public void onCambioPerfil(Perfil p) {
		if (p == null) {
			minActual.setText("Inicia sesión para consultar tu actividad");
			minBarra.setVisible(false);
			panelModif.setVisible(false);
		}
		else {
			panelModif.setVisible(true);
			RegPelicula reg = (RegPelicula) p.getReg(pel);
			if (reg == null) {
				minActual.setText("No visto");
				minBarra.setVisible(false);
			}
			else {
				minActual.setText(reg.getMinActual() == 0 ? "No visto" : "Tiempo visto: " + (reg.getMinActual() >= 60 ? Integer.toString(reg.getMinActual() / 60) + " h " : "") + (reg.getMinActual() % 60) + " mins");
				minBarra.setVisible(reg.getMinActual() > 0);
				minBarra.setValue(reg.getMinActual() * 100 / pel.getDuracionMins());
			}
		}
	}
	
	@Override
	public void onRegister(List<Contenido> nuevo, Perfil p) {
		onCambioPerfil(p);
	}
}
