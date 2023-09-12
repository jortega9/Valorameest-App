package controlador.eventos;

import java.util.List;

import org.json.JSONObject;

import factorias.Factory;
import factorias.contenido.FactoriaContenido;
import modelo.Aplicacion;
import modelo.contenido.Contenido;

public class NewContenidoEvent implements Event {
	
	private List<JSONObject> nuevo;
	private Factory<Contenido> factory;
	
	/**
	 * Añade nuevos contenidos a la aplicación a partir de sus descripciones en forma de lista de JSONObjects
	 * @param nuevo - JSONObjects de cada uno de los contenidos a añadir
	 */
	public NewContenidoEvent(List<JSONObject> nuevo) {
		this.nuevo = nuevo;
		factory = FactoriaContenido.open();
	}

	@Override
	public void ejecutar(Aplicacion modelo) {
		for (JSONObject jo : nuevo) 
			modelo.addContenido(factory.createInstance(jo));
	}
}
