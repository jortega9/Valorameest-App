package vista.dialogs;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import org.json.JSONArray;
import org.json.JSONObject;

import controlador.Controlador;
import controlador.eventos.NewContenidoEvent;
import modelo.contenido.Contenido;
import modelo.contenido.Genero;
import modelo.contenido.TipoContenido;

public class CrearContenidoDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private Controlador control;
	
	private JTextField id, titulo, imgURL;
	private JTextArea descripcion;
	private JComboBox<TipoContenido> tipo;
	private JList<Genero> generos;
	private JSpinner fecha;
	//Peliculas
	private JTextField saga;
	private JSpinner duracionMins;
	//Series
	private JSpinner capitulos, duracionCap;
	//Videojuegos
	private JSpinner horasMin, horasMax;
	
	private JPanel[] panelesExtra;
	private JPanel centralPanel;
	private JButton bCrear, bCancelar;
	
	public CrearContenidoDialog(Controlador control, JFrame owner) {
		super(owner, "Crear contenido");
		this.control = control;
		initGUI();
		acciones();
		setVisible(true);
	}
	
	private void initGUI() {
		setModal(true);
		setResizable(false);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		getContentPane().add(Box.createHorizontalStrut(20));
		
		JPanel pane = new JPanel();
		pane.setBackground(Color.WHITE);
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		JPanel supPanel = new JPanel(new GridLayout(4, 2, 15, 0));
		supPanel.add(new JLabel("ID:"));
		supPanel.add(new JLabel("Tipo:"));
		id = new JTextField(3);
		supPanel.add(id);
		tipo = new JComboBox<>(TipoContenido.values());
		supPanel.add(tipo);
		supPanel.add(new JLabel("Título:"));
		supPanel.add(new JLabel("Fecha:"));
		titulo = new JTextField(1);
		supPanel.add(titulo);
		fecha = new JSpinner(new SpinnerDateModel());
		fecha.setEditor(new JSpinner.DateEditor(fecha, "dd/MM/yyyy"));
		supPanel.add(fecha);
		pane.add(supPanel);
		
		JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		imgPanel.add(new JLabel("URL de imagen:"));
		pane.add(imgPanel);
		imgURL = new JTextField(1);
		pane.add(imgURL);
		JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		descPanel.add(new JLabel("Descripción:"));
		pane.add(descPanel);
		descripcion = new JTextArea(3, 5);
		descripcion.setLineWrap(true);
		pane.add(new JScrollPane(descripcion));
		
		generos = new JList<>(Genero.values());
		pane.add(new JScrollPane(generos));
		
		initExtraPanels();
		centralPanel = new JPanel();
		centralPanel.add(panelesExtra[0]);
		pane.add(centralPanel);
		
		JPanel botonPanel = new JPanel();
		bCrear = new JButton("Crear");
		botonPanel.add(bCrear);
		bCancelar = new JButton("Cancelar");
		botonPanel.add(bCancelar);
		pane.add(botonPanel);
		
		getContentPane().add(pane);
		getContentPane().add(Box.createHorizontalStrut(20));
		pack();
	}
	
	private void initExtraPanels() {
		panelesExtra = new JPanel[TipoContenido.values().length];
		
		JPanel pPelis = new JPanel(new GridLayout(2, 2, 15, 0));
		pPelis.add(new JLabel("Saga:"));
		pPelis.add(new JLabel("Duracion en mins:"));
		saga = new JTextField(1);
		pPelis.add(saga);
		duracionMins = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
		pPelis.add(duracionMins);
		panelesExtra[TipoContenido.PELICULA.ordinal()] = pPelis;
		
		JPanel pSeries = new JPanel(new GridLayout(2, 2, 15, 0));
		pSeries.add(new JLabel("Num de capítulos:"));
		pSeries.add(new JLabel("Minutos por capítulo:"));
		capitulos = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
		pSeries.add(capitulos);
		duracionCap = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
		pSeries.add(duracionCap);
		panelesExtra[TipoContenido.SERIE.ordinal()] = pSeries;
		
		JPanel pVideoj = new JPanel(new GridLayout(2, 2, 15, 0));
		pVideoj.add(new JLabel("Min de horas de juego:"));
		pVideoj.add(new JLabel("Max de horas de juego:"));
		horasMin = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
		pVideoj.add(horasMin);
		horasMax = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
		pVideoj.add(horasMax);
		panelesExtra[TipoContenido.VIDEOJUEGO.ordinal()] = pVideoj;
	}
	
	private void acciones() {
		bCrear.addActionListener((e) -> {
			List<JSONObject> lista = new ArrayList<>();
			lista.add(crearJSON());
			try {
				control.runEvent(new NewContenidoEvent(lista));
				CrearContenidoDialog.this.dispose();
			}
			catch (IllegalArgumentException iae) {
				SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, iae.getMessage()));
			}
		});
		bCancelar.addActionListener((e) -> {
			CrearContenidoDialog.this.dispose();
		});
		tipo.addActionListener((e) -> {
			centralPanel.removeAll();
			centralPanel.add(panelesExtra[tipo.getSelectedIndex()]);
			centralPanel.updateUI();
			CrearContenidoDialog.this.pack();
		});
		id.addKeyListener(textLimit(id, 9));
		titulo.addKeyListener(textLimit(titulo, 25));
		descripcion.addKeyListener(textLimit(descripcion, 500));
		imgURL.addKeyListener(textLimit(imgURL, 200));
		saga.addKeyListener(textLimit(saga, 25));
	}
	
	private KeyListener textLimit(JTextComponent t, int lim) {
		return new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (t.getText().length() >= lim)
					e.consume();
			}
		};
	}
	
	private JSONObject crearJSON() {
		JSONObject jo = new JSONObject(), jo2 = new JSONObject();
		jo2.put("id", id.getText());
		jo2.put("titulo", titulo.getText());
		jo2.put("fecha", new SimpleDateFormat("yyyy/MM/dd").format(fecha.getValue()));
		jo2.put("imagen", imgURL.getText());
		jo2.put("descripcion", descripcion.getText());
		jo2.put("valoracion", Contenido.DEFAULT_VALORACION);
		jo2.put("seguidores", Contenido.DEFAULT_SEGUIDORES);
		jo2.put("generos", new JSONArray(generos.getSelectedValuesList()));
		
		jo2.put("saga", saga.getText());
		jo2.put("duracionMins", duracionMins.getValue());

		jo2.put("capitulos", capitulos.getValue());
		jo2.put("duracionCap", duracionCap.getValue());
		jo2.put("otrasTemp", new JSONArray());

		jo2.put("horasMin", horasMin.getValue());
		jo2.put("horasMax", horasMax.getValue());
		jo2.put("dlcs", new JSONArray());
		
		jo.put("data", jo2);
		jo.put("tipo", tipo.getSelectedItem().toString());
		return jo;
	}
}
