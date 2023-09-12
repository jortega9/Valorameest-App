package modelo.contenido;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Serie extends Contenido {
	
	private static final TipoContenido TIPO = TipoContenido.SERIE;
	private int capitulos;
	private List<String> otrasTemp;
	private int duracionCap;
	
	public Serie(String id, String tit, float val, int seg, String desc, String fech, List<Genero> gens, String imagenURL,
			int caps, int durCap, List<String> otras) {
		super(id, tit, val, seg, desc, fech, gens, TIPO, imagenURL);
		capitulos = caps;
		duracionCap = durCap;
		otrasTemp = otras;
	}

	@Override
	public String getDuracionFormato() {
		return String.format("%d capítulos (%d mins)", capitulos, duracionCap);
	}

	/**
	 * Devuelve una lista con las ID de series que pertenezcan al mismo grupo de temporadas
	 * @return Lista de Strings con las ID de series que pertenezcan al mismo grupo de temporadas
	 */
	public List<String> getOtrasTemp() {
		return Collections.unmodifiableList(otrasTemp);
	}
	
	public int getNumCaps() {
		return capitulos;
	}

	public int getDuracionCap() {
		return duracionCap;
	}

	@Override
	public String toString() {
		String str = super.toString();
		if (otrasTemp.size() == 0)
			str += "\nTemporada única";
		else {
			str += "\n" + otrasTemp.size() + " temporada(s) más -> ID: " + otrasTemp.get(0);
			for (int i = 1; i < otrasTemp.size(); ++i)
				str += ", " + otrasTemp.get(i);
		}
		
		return str;
	}
	
	@Override
	public JSONObject detalles() {
		JSONObject jo = super.detalles();
		
		jo.getJSONObject("data").put("capitulos", capitulos);
		jo.getJSONObject("data").put("duracionCap", duracionCap);
		jo.getJSONObject("data").put("otrasTemp", new JSONArray(otrasTemp));
		
		return jo;
	}
}
