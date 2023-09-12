package vista.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.Controlador;
import controlador.eventos.BuscarPorIDEvent;
import controlador.eventos.RemoveContenidoEvent;
import modelo.contenido.Contenido;
import utils.ImageParser;
import vista.DialogObservadorAdapter;

public class EliminarContenidoDialog extends DialogObservadorAdapter {

	private static final long serialVersionUID = 1L;
	
	Controlador control;
	Contenido cont;
	
	JLabel resultado;
	JTextField id;
	JButton buscarB, cancelarB, aceptarB;

	public EliminarContenidoDialog(Controlador control, JFrame owner) {
		super(owner, "Eliminar contenido", control);
		this.control = control;
		control.addObservador(this);
		initGUI();
		acciones();
		setVisible(true);
	}
	
	private void initGUI() {
		setVisible(false);
		setBackground(Color.WHITE);
		setResizable(false);
		setModal(true);
		
		JPanel panelArriba = new JPanel();
		panelArriba.add(Box.createVerticalStrut(10));
		panelArriba.add(new JLabel("Introduzca la ID del contenido a eliminar."));
		
		JPanel panelIdBarra = new JPanel();
		panelIdBarra.setOpaque(false);
		id = new JTextField();
		id.setPreferredSize(new Dimension(100, 20));
		buscarB = new JButton(ImageParser.iconFromFile("lupa.png", 15, 15));
		buscarB.setPreferredSize(new Dimension(20, 20));
		panelIdBarra.add(new JLabel("ID:"));
		panelIdBarra.add(id);
		panelIdBarra.add(buscarB);

		JPanel panelResLabel = new JPanel();
		panelResLabel.setOpaque(false);
		resultado = new JLabel("^   ^   ^"); 
		panelResLabel.add(resultado);
		
		JPanel centralPanel = new JPanel();
		centralPanel.setOpaque(false);
		centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
		centralPanel.add(Box.createVerticalStrut(10));
		centralPanel.add(panelIdBarra);
		centralPanel.add(panelResLabel);
		
		JPanel botonesPanel = new JPanel();
		aceptarB = new JButton("Aceptar"); 
		aceptarB.setEnabled(false);
		cancelarB = new JButton("Cancelar");
		botonesPanel.add(aceptarB);
		botonesPanel.add(cancelarB);
		
		add(panelArriba, BorderLayout.NORTH);
		add(centralPanel, BorderLayout.CENTER);
		add(botonesPanel, BorderLayout.SOUTH);
		add(Box.createHorizontalStrut(100), BorderLayout.WEST);
		add(Box.createHorizontalStrut(100), BorderLayout.EAST);
		pack();
	}
	
	private void acciones() {
		cancelarB.addActionListener((e) -> EliminarContenidoDialog.this.dispose());
		buscarB.addActionListener((e) -> control.runEvent(new BuscarPorIDEvent(id.getText())));
		aceptarB.addActionListener((e) -> { 
			control.runEvent(new RemoveContenidoEvent(cont));
			EliminarContenidoDialog.this.dispose();
		});
		id.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (id.getText().length() >= 9)
					e.consume();
			}
		});
	}

	@Override
	public void onBuscarPorId(Contenido cont) {
		this.cont = cont;
		if (cont == null) {
			aceptarB.setEnabled(false);
			resultado.setForeground(Color.RED);
			resultado.setText("No encontrado");
		}
		else {
			aceptarB.setEnabled(true);
			resultado.setForeground(Color.BLUE);
			resultado.setText(cont.getTitulo());
		}
	}
}
