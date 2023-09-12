package factorias.contenido;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import factorias.Builder;
import factorias.Factory;
import modelo.contenido.Contenido;

public class FactoriaContenido implements Factory<Contenido> {

	private static FactoriaContenido factory = null;
	
	private List<Builder<? extends Contenido>> builders;
	
	private FactoriaContenido() {
		builders = new ArrayList<>();
		builders.add(new BuilderVideojuego());
		builders.add(new BuilderPelicula());
		builders.add(new BuilderSerie());
	}
	
	/**
	 * Devuelve una instancia a la factoría de contenido
	 * @return Instancia de FactoriaContenido
	 */
	public static FactoriaContenido open() {
		if (factory == null)
			factory = new FactoriaContenido();
		return factory;
	}

	@Override
	public Contenido createInstance(JSONObject info) {
		if (info != null) {
			for (Builder<? extends Contenido> bc : builders) {
				Contenido o = bc.createInstance(info);
				if (o != null)
					return o;
			}
		}
		throw new IllegalArgumentException("Invalid value for createInstance: " + info.toString(3));
	}

	@Override
	public Contenido loadInstance(ResultSet info) {
		if (info != null) {
			for (Builder<? extends Contenido> bc : builders) {
				Contenido o = bc.loadInstance(info);
				if (o != null)
					return o;
			}
		}
		throw new IllegalArgumentException("Invalid value for loadInstance");
	}
}
