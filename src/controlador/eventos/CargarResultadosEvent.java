package controlador.eventos;

import modelo.Aplicacion;

public class CargarResultadosEvent implements Event {
	
	private int n;
	
	/**
	 * Extrae los siguientes n resultados (en caso de haberlos) de la última búsqueda realizada en la aplicación.
	 * @param n - Número de elementos a extraer
	 */
	public CargarResultadosEvent(int n) {
		this.n = n;
	}

	@Override
	public void ejecutar(Aplicacion modelo) {
		modelo.cargarResultados(n);
	}
}
