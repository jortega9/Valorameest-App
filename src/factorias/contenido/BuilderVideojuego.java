package factorias.contenido;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import factorias.Builder;
import modelo.BD;
import modelo.contenido.Genero;
import modelo.contenido.TipoContenido;
import modelo.contenido.Videojuego;
import utils.Consulta;
import utils.VBD;

public class BuilderVideojuego extends Builder<Videojuego> {
	
	public static final String TIPO = TipoContenido.VIDEOJUEGO.toString();

	BuilderVideojuego() {
		super(TIPO);
	}

	@Override
	protected Videojuego createTheInstance(JSONObject data) {
		List<Genero> gens = new ArrayList<>();
		for (Object str : data.getJSONArray("generos").toList())
			gens.add(Genero.valueOf(str.toString()));
		
		List<String> dlcs = new ArrayList<>();
		for (Object str : data.getJSONArray("dlcs").toList())
			dlcs.add(str.toString());
		
		return new Videojuego(data.getString("id"), data.getString("titulo"), data.getFloat("valoracion"),
				data.getInt("seguidores"), data.getString("descripcion"), data.getString("fecha"), gens, data.getString("imagen"),
				data.getInt("horasMin"), data.getInt("horasMax"), dlcs);
	}

	@Override
	protected Videojuego loadTheInstance(ResultSet data) throws Exception {
		Consulta cons = new Consulta(Consulta.FROM_GENEROS);
		cons.putWhere(String.format("%s = '%s'", VBD.ID_G, data.getString(VBD.ID_CONT)));
		ResultSet set = BD.open().select(cons.buildConsulta());
		
		List<Genero> gens = new ArrayList<>();
		while (set.next())
			gens.add(Genero.valueOf(set.getString(VBD.GENERO_G)));
		
		cons = new Consulta(Consulta.FROM_DLCS);
		cons.putWhere(String.format("%s = '%s'", VBD.ID_DLC, data.getString(VBD.ID_CONT)));
		set = BD.open().select(cons.buildConsulta());
		
		List<String> dlcs = new ArrayList<>();
		while (set.next())
			dlcs.add(set.getString(VBD.DLC_DLC));
		
		return new Videojuego(data.getString(VBD.ID_CONT), data.getString(VBD.TIT_CONT), data.getFloat(VBD.VAL_CONT),
				data.getInt(VBD.SEG_CONT), data.getString(VBD.DESC_CONT), data.getString(VBD.FECHA_CONT), gens, data.getString(VBD.IMG_CONT),
				data.getInt(VBD.HORASMIN_V), data.getInt(VBD.HORASMAX_V), dlcs);
	}
}
