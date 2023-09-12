package controlador.eventos;

import modelo.Aplicacion;

public class AplicarFiltrosEvent implements Event {
	
	private int n;
	
	/**
	 * Realiza una búsqueda en la aplicación a partir de los filtros que ya estuvieran aplicados, y extrae un cierto número de resultados
	 * @param tamResultado - Número de elementos a extraer
	 */
	public AplicarFiltrosEvent(int tamResultado) {
		n = tamResultado;
	}

	@Override
	public void ejecutar(Aplicacion modelo) {
		modelo.realizarBusqueda();
		new CargarResultadosEvent(n).ejecutar(modelo);
	}
}
