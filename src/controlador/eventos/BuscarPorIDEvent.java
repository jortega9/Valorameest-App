package controlador.eventos;

import modelo.Aplicacion;

public class BuscarPorIDEvent implements Event {
	
	private String id;
	
	/**
	 * Realiza una búsqueda de un único contenido de la aplicación a partir de su ID. El elemento resultante será null en caso de no encontrarlo.
	 * @param id - ID del contenido buscado
	 */
	public BuscarPorIDEvent(String id) {
		this.id = id;
	}

	@Override
	public void ejecutar(Aplicacion modelo) {
		modelo.getWithID(id);
	}
}
