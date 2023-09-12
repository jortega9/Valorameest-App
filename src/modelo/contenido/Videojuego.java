package modelo.contenido;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Videojuego extends Contenido {
	
	private static final TipoContenido TIPO = TipoContenido.VIDEOJUEGO;
	private int horasMin;
	private int horasMax;
	List<String> dlcs;
	
	public Videojuego(String id, String tit, float val, int seg, String desc, String fech, List<Genero> gens, String imagenURL,
			int hMin, int hMax, List<String> dlcs) {
		super(id, tit, val, seg, desc, fech, gens, TIPO, imagenURL);
		horasMin = hMin;
		horasMax = hMax;
		this.dlcs = dlcs;
	}

	@Override
	public String getDuracionFormato() {
		return String.format("%d-%d h", horasMin, horasMax);
	}
	
	public int getHorasMin() {
		return horasMin;
	}
	
	public int getHorasMax() {
		return horasMax;
	}
	
	/**
	 * Devuelve una lista con las ID de dlcs asociados a este juego
	 * @return Lista de Strings con las ID de dlcs asociados a este juego
	 */
	public List<String> getDlcs() {
		return Collections.unmodifiableList(dlcs);
	}
	
	@Override
	public String toString() {
		String str = super.toString();
		if (dlcs.size() == 0)
			str += "\nSin DLCs";
		else {
			str += "\n" + dlcs.size() + " DLC(s) -> ID: " + dlcs.get(0);
			for (int i = 1; i < dlcs.size(); ++i)
				str += ", " + dlcs.get(i);
		}
		
		return str;
	}
	
	@Override
	public JSONObject detalles() {
		JSONObject jo = super.detalles();
		
		jo.getJSONObject("data").put("horasMin", horasMin);
		jo.getJSONObject("data").put("horasMax", horasMax);
		jo.getJSONObject("data").put("dlcs", new JSONArray(dlcs));
		
		return jo;
	}
}
