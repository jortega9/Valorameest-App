package controlador.eventos;

import modelo.Aplicacion;
import modelo.filtros.FilterStrategy;

public class RemoveFiltroEvent implements Event {
	
	FilterStrategy filtro;
	
	/**
	 * Elimina de la aplicación un filtro dado (en caso de haberlo)
	 * @param filtro - Filtro a eliminar
	 */
	public RemoveFiltroEvent(FilterStrategy filtro) {
		this.filtro = filtro;
	}

	@Override
	public void ejecutar(Aplicacion modelo) {
		modelo.removeFiltro(filtro);
	}
}
