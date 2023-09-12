package factorias.contenido;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import factorias.Builder;
import modelo.BD;
import modelo.contenido.Genero;
import modelo.contenido.Serie;
import modelo.contenido.TipoContenido;
import utils.Consulta;
import utils.VBD;

public class BuilderSerie extends Builder<Serie> {
	
	public static final String TIPO = TipoContenido.SERIE.toString();

	BuilderSerie() {
		super(TIPO);
	}

	@Override
	protected Serie createTheInstance(JSONObject data) {
		List<Genero> gens = new ArrayList<>();
		for (Object str : data.getJSONArray("generos").toList())
			gens.add(Genero.valueOf(str.toString()));
		
		List<String> otras = new ArrayList<>();
		for (Object str : data.getJSONArray("otrasTemp").toList())
			otras.add(str.toString());
		
		return new Serie(data.getString("id"), data.getString("titulo"), data.getFloat("valoracion"),
				data.getInt("seguidores"), data.getString("descripcion"), data.getString("fecha"), gens, data.getString("imagen"),
				data.getInt("capitulos"), data.getInt("duracionCap"), otras);
	}

	@Override
	protected Serie loadTheInstance(ResultSet data) throws Exception {
		Consulta cons = new Consulta(Consulta.FROM_GENEROS);
		cons.putWhere(String.format("%s = '%s'", VBD.ID_G, data.getString(VBD.ID_CONT)));
		ResultSet set = BD.open().select(cons.buildConsulta());
		
		List<Genero> gens = new ArrayList<>();
		while (set.next())
			gens.add(Genero.valueOf(set.getString(VBD.GENERO_G)));
		
		cons = new Consulta(Consulta.FROM_TEMP_SERIES);
		cons.putWhere(String.format("(%s = '%s' OR %s = '%s')", VBD.S1_TEMP, data.getString(VBD.ID_CONT), VBD.S2_TEMP, data.getString(VBD.ID_CONT)));
		set = BD.open().select(cons.buildConsulta());
		
		List<String> otras = new ArrayList<>();
		while (set.next())
			otras.add((set.getString(1).equals(data.getString(VBD.ID_S)) ? set.getString(2) : set.getString(1)));
		
		return new Serie(data.getString(VBD.ID_CONT), data.getString(VBD.TIT_CONT), data.getFloat(VBD.VAL_CONT),
				data.getInt(VBD.SEG_CONT), data.getString(VBD.DESC_CONT), data.getString(VBD.FECHA_CONT), gens, data.getString(VBD.IMG_CONT),
				data.getInt(VBD.CAPITULOS_S), data.getInt(VBD.DURCAP_S), otras);
	}
}
