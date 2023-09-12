package controlador.eventos;

import modelo.Aplicacion;

public class ClearFiltrosEvent implements Event {
	
	/**
	 * Elimina todos los filtros que hubiera seleccionados en la aplicación
	 */
	public ClearFiltrosEvent() {}

	@Override
	public void ejecutar(Aplicacion modelo) {
		modelo.clearFiltros();
	}
}
