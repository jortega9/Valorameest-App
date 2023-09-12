package factorias.perfil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import factorias.Builder;
import factorias.Factory;
import modelo.perfil.Perfil;
import modelo.perfil.Registro;

public class FactoriaRegistros implements Factory<Registro> {
	
	private static FactoriaRegistros instance;
	private List<Builder<? extends Registro>> builders;
	private Perfil perfil;
	
	private FactoriaRegistros(Perfil p) {
		perfil = p;
		builders = new ArrayList<>();
		builders.add(new BuilderRegVideojuego(p));
		builders.add(new BuilderRegPelicula(p));
		builders.add(new BuilderRegSerie(p));
	}
	
	/**
	 * Devuelve una instancia a la factoría de registros
	 * @return Instancia de FactoriaRegistros
	 */
	public static FactoriaRegistros open(Perfil p) {
		if (instance == null || !instance.perfil.equals(p))
			instance = new FactoriaRegistros(p);
		return instance;
	}

	@Override
	public Registro createInstance(JSONObject info) {
		if (info != null) {
			for (Builder<? extends Registro> bc : builders) {
				Registro o = bc.createInstance(info);
				if (o != null)
					return o;
			}
		}
		throw new IllegalArgumentException("Invalid value for createInstance: " + info.toString(3));
	}

	@Override
	public Registro loadInstance(ResultSet info) {
		if (info != null) {
			for (Builder<? extends Registro> bc : builders) {
				Registro o = bc.loadInstance(info);
				if (o != null)
					return o;
			}
		}
		throw new IllegalArgumentException("Invalid value for loadInstance");
	}
}
