package vista.contenido;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import controlador.Controlador;
import controlador.eventos.ModificarPerfilEvent;
import modelo.contenido.Contenido;
import modelo.contenido.Videojuego;
import modelo.perfil.Perfil;
import modelo.perfil.RegVideojuego;
import vista.PanelObservadorAdapter;

public class InfoVideojuegoPanel extends PanelObservadorAdapter {

	private static final long serialVersionUID = 1L;
	
	private Videojuego vid;
	private Controlador control;
	
	private JLabel horasJugadas;
	private JSpinner horasSpin;
	private JButton horasButton;
	
	public InfoVideojuegoPanel(Videojuego v, Controlador control) {
		vid = v;
		this.control = control;
		initGUI();
		control.addObservador(this);
	}
	
	private void initGUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		
		JPanel dlcPanel = new JPanel(new BorderLayout());
		dlcPanel.setOpaque(false);
		JLabel dlcLabel = new JLabel(" DLCs disponibles:");
		dlcLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dlcPanel.add(dlcLabel, BorderLayout.NORTH);
		if (vid.getDlcs().size() == 0)
			dlcPanel.add(new JLabel("    Ninguno"), BorderLayout.CENTER);
		else {
			JPanel dlcs = new JPanel(new GridLayout(0, 1, 10, 10));
			dlcs.setOpaque(false);
			for (String s : vid.getDlcs()) {
				JLabel dLab = new JLabel("    - " + s);
				dLab.setFont(new Font(null, Font.PLAIN, 16));
				dlcs.add(dLab);
			}
			dlcPanel.add(dlcs, BorderLayout.CENTER);
		}
		dlcPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20 + vid.getDlcs().size() * 16));
		
		JPanel horasPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		horasPanel.setOpaque(false);
		horasJugadas = new JLabel("");
		horasJugadas.setFont(new Font("Tahoma", Font.PLAIN, 18));
		horasSpin = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
		horasSpin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		horasSpin.setPreferredSize(new Dimension(60, 30));
		horasSpin.setBorder(BorderFactory.createEmptyBorder());
		horasSpin.addChangeListener((e) -> horasButton.setEnabled(true));
		horasButton = new JButton("Guardar");
		horasButton.addActionListener((e) -> control.runEvent(new ModificarPerfilEvent((p) ->  {
			((RegVideojuego) p.usarReg(vid)).setHorasJugadas((int) horasSpin.getValue());
			this.setEnabled(false);
		})));
		horasPanel.add(horasJugadas);
		horasPanel.add(horasSpin);
		horasPanel.add(horasButton);

		add(dlcPanel);
		add(Box.createVerticalStrut(30));
		add(horasPanel);
		add(Box.createVerticalGlue());
	}

	@Override
	public void onCambioPerfil(Perfil p) {
		if (p == null) {
			horasJugadas.setText("Inicia sesión para consultar tu actividad");
			horasSpin.setVisible(false);
			horasButton.setVisible(false);
		}
		else {
			RegVideojuego reg = (RegVideojuego) p.getReg(vid);
			horasJugadas.setText("Horas jugadas: ");
			if (reg != null) horasSpin.setValue(reg.getHorasJugadas());
			horasSpin.setVisible(true);
			horasButton.setVisible(true);
			horasButton.setEnabled(false);
		}
	}
	
	@Override
	public void onRegister(List<Contenido> nuevo, Perfil p) {
		onCambioPerfil(p);
	}
}
