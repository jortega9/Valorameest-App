package controlador.eventos;

import org.json.JSONObject;

import factorias.Factory;
import factorias.contenido.FactoriaContenido;
import modelo.Aplicacion;
import modelo.contenido.Contenido;

public class UpdateContenidoEvent implements Event {
	
	private JSONObject nuevo;
	private Factory<Contenido> factory;
	
	/**
	 * Actualiza un contenido de la aplicación a partir de su nueva descripción en forma de JSONObject
	 * @param nuevo - JSONObject con los nuevos datos del contenido
	 */
	public UpdateContenidoEvent(JSONObject nuevo) {
		this.nuevo = nuevo;
		factory = FactoriaContenido.open();
	}

	@Override
	public void ejecutar(Aplicacion modelo) {
		modelo.updateContenido(factory.createInstance(nuevo));
	}
}

