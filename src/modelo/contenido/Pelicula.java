package modelo.contenido;

import java.util.List;

import org.json.JSONObject;

public class Pelicula extends Contenido {
	
	private static final TipoContenido TIPO = TipoContenido.PELICULA;
	private String saga;
	private int duracionMins;
	
	public Pelicula(String id, String tit, float val, int seg, String desc, String fech, List<Genero> gens, String imagenURL,
			String saga, int mins) {
		super(id, tit, val, seg, desc, fech, gens, TIPO, imagenURL);
		this.saga = saga;
		duracionMins = mins;
	}

	@Override
	public String getDuracionFormato() {
		return String.format("%d h %d mins", duracionMins / 60, duracionMins % 60);
	}

	public String getSaga() {
		return saga;
	}
	
	public int getDuracionMins() {
		return duracionMins;
	}
	
	@Override
	public String toString() {
		return super.toString()
				+ "\nSaga: " + saga;
	}
	
	@Override
	public JSONObject detalles() {
		JSONObject jo = super.detalles();
		
		jo.getJSONObject("data").put("saga", saga);
		jo.getJSONObject("data").put("duracionMins", duracionMins);
		
		return jo;
	}
}
