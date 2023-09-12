package vista.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import javax.swing.text.JTextComponent;

import org.json.JSONArray;
import org.json.JSONObject;

import controlador.Controlador;
import controlador.eventos.BuscarPorIDEvent;
import controlador.eventos.UpdateContenidoEvent;
import modelo.contenido.Contenido;
import modelo.contenido.Genero;
import modelo.contenido.Pelicula;
import modelo.contenido.Serie;
import modelo.contenido.TipoContenido;
import modelo.contenido.Videojuego;
import utils.ImageParser;
import vista.DialogObservadorAdapter;

public class ActualizarContenidoDialog extends DialogObservadorAdapter {

	private static final long serialVersionUID = 1L;

	private Controlador control;
	private Contenido cont;
	private boolean buscandoSerie = false;
	
	private JTextField id, titulo, imgURL;
	private JTextArea descripcion;
	private JList<Genero> generos;
	private JSpinner fecha;
	//Peliculas
	private JTextField saga;
	private JSpinner duracionMins;
	//Series
	private JSpinner capitulos, duracionCap;
	private JTextField tmpField;
	private JButton bAnadirTmp;
	private DefaultListModel<String> tmpsModel;
	private JList<String> tmps;
	private Contenido serie;
	
	//Videojuegos
	private JSpinner horasMin, horasMax;
	private JTextField dlcField;
	private JButton bAnadirDlc;
	private DefaultListModel<String> dlcsModel;
	private JList<String> dlcs;
	
	private JPanel[] panelesExtra;
	private JPanel centralPanel;
	private JButton bCrear, bCancelar, buscarB;
	
	
	public ActualizarContenidoDialog(Controlador control, JFrame owner) {
		super(owner, "Actualizar contenido", control);
		this.control = control;
		control.addObservador(this);
		initGUI();
		acciones();
		setVisible(true);
	}
	
	private void initGUI() {
		setModal(true);
		setResizable(false);
		setLayout(new BorderLayout());
		
		JPanel idPanel = new JPanel();
		id = new JTextField();
		id.setPreferredSize(new Dimension(80, 20));
		buscarB = new JButton(ImageParser.iconFromFile("lupa.png", 15, 15));
		buscarB.setPreferredSize(new Dimension(20, 20));
		idPanel.add(new JLabel("ID:"));
		idPanel.add(id);
		idPanel.add(buscarB);
		
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		JPanel supPanel = new JPanel(new GridLayout(2, 2, 15, 0));
		supPanel.add(new JLabel("Título:"));
		supPanel.add(new JLabel("Fecha:"));
		titulo = new JTextField(1);
		titulo.setEnabled(false);
		supPanel.add(titulo);
		fecha = new JSpinner(new SpinnerDateModel());
		fecha.setEnabled(false);
		fecha.setEditor(new JSpinner.DateEditor(fecha, "dd/MM/yyyy"));
		supPanel.add(fecha);
		pane.add(supPanel);
		
		JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		imgPanel.add(new JLabel("URL de imagen:"));
		pane.add(imgPanel);
		imgURL = new JTextField(1);
		imgURL.setEnabled(false);
		pane.add(imgURL);
		JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		descPanel.add(new JLabel("Descripción:"));
		pane.add(descPanel);
		descripcion = new JTextArea(3, 5);
		descripcion.setLineWrap(true);
		descripcion.setEnabled(false);
		pane.add(new JScrollPane(descripcion));
		
		generos = new JList<>(Genero.values());
		generos.setEnabled(false);
		pane.add(new JScrollPane(generos));
		
		initExtraPanels();
		centralPanel = new JPanel();
		centralPanel.setOpaque(false);
		pane.add(centralPanel);
		
		JPanel botonPanel = new JPanel();
		bCrear = new JButton("Actualizar");
		bCrear.setEnabled(false);
		botonPanel.add(bCrear);
		bCancelar = new JButton("Cancelar");
		botonPanel.add(bCancelar);
		pane.add(botonPanel);
		
		add(idPanel, BorderLayout.NORTH);
		add(pane, BorderLayout.CENTER);
		add(Box.createHorizontalStrut(20), BorderLayout.WEST);
		add(Box.createHorizontalStrut(20), BorderLayout.EAST);
		pack();
	}
	
	private void initExtraPanels() {
		panelesExtra = new JPanel[TipoContenido.values().length];
		
		panelesExtra[TipoContenido.PELICULA.ordinal()] = panelPeliculas();
		panelesExtra[TipoContenido.SERIE.ordinal()] = panelSeries();
		panelesExtra[TipoContenido.VIDEOJUEGO.ordinal()] = panelVideojuegos();
	}
	
	private JPanel panelPeliculas() {
		JPanel pPelis = new JPanel(new GridLayout(2, 2, 15, 0));
		pPelis.add(new JLabel("Saga:"));
		pPelis.add(new JLabel("Duracion en mins:"));
		saga = new JTextField(1);
		saga.setEnabled(false);
		pPelis.add(saga);
		duracionMins = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
		duracionMins.setEnabled(false);
		pPelis.add(duracionMins);
		
		return pPelis;
	}
	
	private JPanel panelSeries() {
		JPanel pAtribSeries = new JPanel(new GridLayout(2, 2, 15, 0));
		pAtribSeries.add(new JLabel("Num de capítulos:"));
		pAtribSeries.add(new JLabel("Minutos por capítulo:"));
		capitulos = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
		capitulos.setEnabled(false);
		pAtribSeries.add(capitulos);
		duracionCap = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
		duracionCap.setEnabled(false);
		pAtribSeries.add(duracionCap);
		
		JPanel tmpId = new JPanel();
		tmpField = new JTextField();
		tmpField.setPreferredSize(new Dimension(150, 20));
		tmpField.addKeyListener(textLimit(tmpField, 9));
		bAnadirTmp = new JButton("Añadir");
		bAnadirTmp.addActionListener((e) -> {
			buscandoSerie = true;
			control.runEvent(new BuscarPorIDEvent(tmpField.getText()));
			if (serie != null) 
				tmpsModel.addElement(serie.getID());
			else
				JOptionPane.showMessageDialog(null, "No se ha encontrado una serie con esa ID.");
		});
		tmpId.add(new JLabel("Temporada (ID): "));
		tmpId.add(tmpField);
		tmpId.add(bAnadirTmp);
		
		tmpsModel = new DefaultListModel<>();
		tmps = new JList<>(tmpsModel);
		JScrollPane spTmps = new JScrollPane(tmps);
		spTmps.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		
		JPanel pSeries = new JPanel();
		pSeries.setLayout(new BoxLayout(pSeries, BoxLayout.Y_AXIS));
		pSeries.add(pAtribSeries);
		pSeries.add(tmpId);
		pSeries.add(spTmps);
		
		return pSeries;
	}
	
	private JPanel panelVideojuegos() {
		JPanel pAtribVideoj = new JPanel(new GridLayout(2, 2, 15, 0));
		pAtribVideoj.add(new JLabel("Min de horas de juego:"));
		pAtribVideoj.add(new JLabel("Max de horas de juego:"));
		horasMin = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
		horasMin.setEnabled(false);
		pAtribVideoj.add(horasMin);
		horasMax = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
		horasMax.setEnabled(false);
		pAtribVideoj.add(horasMax);
		
		JPanel dlcId = new JPanel();
		dlcField = new JTextField();
		dlcField.setPreferredSize(new Dimension(200, 20));
		dlcField.addKeyListener(textLimit(dlcField, 50));
		bAnadirDlc = new JButton("Añadir");
		bAnadirDlc.addActionListener((e) -> dlcsModel.addElement(dlcField.getText()));
		dlcId.add(new JLabel("Dlc: "));
		dlcId.add(dlcField);
		dlcId.add(bAnadirDlc);
		
		dlcsModel = new DefaultListModel<>();
		dlcs = new JList<>(dlcsModel);
		JScrollPane spDlcs = new JScrollPane(dlcs);
		spDlcs.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		
		JPanel pVideoj = new JPanel();
		pVideoj.setLayout(new BoxLayout(pVideoj, BoxLayout.Y_AXIS));
		pVideoj.add(pAtribVideoj);
		pVideoj.add(dlcId);
		pVideoj.add(spDlcs);
		
		return pVideoj;
	}
	
	private void acciones() {
		buscarB.addActionListener((e) -> control.runEvent(new BuscarPorIDEvent(id.getText())));
		bCrear.addActionListener((e) -> {
			control.runEvent(new UpdateContenidoEvent(crearJSON()));
			ActualizarContenidoDialog.this.dispose();
		});
		bCancelar.addActionListener((e) -> {
			ActualizarContenidoDialog.this.dispose();
		});
		
		id.addKeyListener(textLimit(id, 9));
		titulo.addKeyListener(textLimit(titulo, 25));
		descripcion.addKeyListener(textLimit(descripcion, 500));
		imgURL.addKeyListener(textLimit(imgURL, 200));
		saga.addKeyListener(textLimit(saga, 25));
		
		dlcs.addKeyListener(suprimir(dlcs, dlcsModel));
		tmps.addKeyListener(suprimir(tmps, tmpsModel));
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
	
	private <T> KeyListener suprimir(JList<T> lista, DefaultListModel<T> modelo) {
		return new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE)
					for (T a : lista.getSelectedValuesList())
						modelo.removeElement(a);
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
		jo2.put("valoracion", cont.getValoracion());
		jo2.put("seguidores", cont.getSeguidores());
		jo2.put("generos", new JSONArray(generos.getSelectedValuesList()));
		
		jo2.put("saga", saga.getText());
		jo2.put("duracionMins", duracionMins.getValue());

		jo2.put("capitulos", capitulos.getValue());
		jo2.put("duracionCap", duracionCap.getValue());
		jo2.put("otrasTemp", new JSONArray(Collections.list(tmpsModel.elements())));

		jo2.put("horasMin", horasMin.getValue());
		jo2.put("horasMax", horasMax.getValue());
		jo2.put("dlcs", new JSONArray(Collections.list(dlcsModel.elements())));
		
		jo.put("data", jo2);
		jo.put("tipo", cont.getTipo().toString());

		return jo;
	}
	
	private void setEnablidad(boolean si) {
		
		titulo.setEnabled(si);
		fecha.setEnabled(si);
		imgURL.setEnabled(si);
		descripcion.setEnabled(si);
		generos.setEnabled(si);
		saga.setEnabled(si);
		duracionMins.setEnabled(si);
		capitulos.setEnabled(si);
		duracionCap.setEnabled(si);
		horasMin.setEnabled(si);
		horasMax.setEnabled(si);
		bCrear.setEnabled(si);
	}
	
	private void cargarCosos(Contenido cont) {
		titulo.setText(cont.getTitulo());
		try {
			fecha.setValue(new SimpleDateFormat("yyyy-mm-dd").parse(cont.getFecha()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		imgURL.setText(cont.getImagenURL());
		descripcion.setText(cont.getDescripcion());
		int[] arr;
		arr = new int[cont.getGeneros().size()];
		for(int i = 0; i < cont.getGeneros().size();i++) {
			arr[i] = cont.getGeneros().get(i).ordinal();
		}
		generos.setSelectedIndices(arr);
		switch(cont.getTipo()) {
		case PELICULA:
			saga.setText(((Pelicula)cont).getSaga());
			duracionMins.setValue(((Pelicula)cont).getDuracionMins());
			break;
		case SERIE:
			capitulos.setValue(((Serie)cont).getNumCaps());
			duracionCap.setValue(((Serie)cont).getDuracionCap());
			tmpsModel.removeAllElements();
			for (String s : ((Serie)cont).getOtrasTemp())
				tmpsModel.addElement(s);
			break;
		case VIDEOJUEGO:
			horasMin.setValue(((Videojuego)cont).getHorasMin());
			horasMax.setValue(((Videojuego)cont).getHorasMax());
			dlcsModel.removeAllElements();
			for (String d : ((Videojuego)cont).getDlcs())
				dlcsModel.addElement(d);
			break;
		}
	}
	
	private void setExtraPanel(int i) {
		centralPanel.removeAll();
		centralPanel.add(panelesExtra[i]);
		centralPanel.updateUI();
		pack();
	}

	@Override
	public void onBuscarPorId(Contenido cont) {
		if (buscandoSerie) {
			if (cont != null && !cont.isTipo(TipoContenido.SERIE))
				cont = null;
			serie = cont;
			buscandoSerie = false;
		}
		else {
			this.cont = cont;
			if (cont == null) {
				setEnablidad(false);
			}
			else {
				setEnablidad(true);
				setExtraPanel(cont.getTipo().ordinal());
				cargarCosos(cont);
			}
		}
	}
}
