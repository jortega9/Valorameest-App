package factorias.contenido;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import factorias.Builder;
import modelo.BD;
import modelo.contenido.Genero;
import modelo.contenido.Pelicula;
import modelo.contenido.TipoContenido;
import utils.Consulta;
import utils.VBD;

public class BuilderPelicula extends Builder<Pelicula> {
	
	public static final String TIPO = TipoContenido.PELICULA.toString();

	BuilderPelicula() {
		super(TIPO);
	}

	@Override
	protected Pelicula createTheInstance(JSONObject data) {
		List<Genero> gens = new ArrayList<>();
		for (Object str : data.getJSONArray("generos").toList())
			gens.add(Genero.valueOf(str.toString()));
		
		return new Pelicula(data.getString("id"), data.getString("titulo"), data.getFloat("valoracion"),
				data.getInt("seguidores"), data.getString("descripcion"), data.getString("fecha"), gens, data.getString("imagen"),
				data.getString("saga"), data.getInt("duracionMins"));
	}

	@Override
	protected Pelicula loadTheInstance(ResultSet data) throws Exception {
		Consulta cons = new Consulta(Consulta.FROM_GENEROS);
		cons.putWhere(String.format("%s = '%s'", VBD.ID_G, data.getString(VBD.ID_CONT)));
		ResultSet set = BD.open().select(cons.buildConsulta());
		
		List<Genero> gens = new ArrayList<>();
		while (set.next())
			gens.add(Genero.valueOf(set.getString(VBD.GENERO_G)));
		
		return new Pelicula(data.getString(VBD.ID_CONT), data.getString(VBD.TIT_CONT), data.getFloat(VBD.VAL_CONT),
				data.getInt(VBD.SEG_CONT), data.getString(VBD.DESC_CONT), data.getString(VBD.FECHA_CONT), gens, data.getString(VBD.IMG_CONT),
				data.getString(VBD.SAGA_P), data.getInt(VBD.DURMIN_P));
	}
}
