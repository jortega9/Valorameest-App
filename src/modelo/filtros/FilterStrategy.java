package modelo.filtros;

import utils.Consulta;

public interface FilterStrategy {

	/**
	 * Modifica una consulta dada añadiendo una condición, criterio de búsqueda, etc
	 * @param consulta - consulta a filtrar
	 */
	public void filtrar(Consulta consulta);
}
