package controlador.eventos;

import modelo.Aplicacion;
import modelo.contenido.Contenido;

public class RemoveContenidoEvent implements Event {
	
	private Contenido cont;
	
	/**
	 * Elimina de la aplicación un contenido dado, y toda la información relacionada con él
	 * @param c - Contenido a eliminar
	 */
	public RemoveContenidoEvent(Contenido c) {
		cont = c;
	}

	@Override
	public void ejecutar(Aplicacion modelo) {
		modelo.removeContenido(cont);
	}
}
