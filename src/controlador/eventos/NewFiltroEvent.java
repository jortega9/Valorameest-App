package controlador.eventos;

import modelo.Aplicacion;
import modelo.filtros.FilterStrategy;

public class NewFiltroEvent implements Event {
	
	private FilterStrategy filtro;
	
	/**
	 * Añade un nuevo filtro al buscador de la aplicación, sin aplicarlo aún
	 * @param f - Filtro a añadir
	 */
	public NewFiltroEvent(FilterStrategy f) {
		filtro = f;
	}

	@Override
	public void ejecutar(Aplicacion modelo) {
		modelo.addFiltro(filtro);
	}
}
