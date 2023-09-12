package controlador.eventos;

import modelo.Aplicacion;

public class RefreshStatsEvent implements Event {
	
	/**
	 * Calcula y actualiza de golpe las estadísticas de todos los contenidos de la aplicación
	 */
	public RefreshStatsEvent() {}

	@Override
	public void ejecutar(Aplicacion app) {
		app.actualizarEstadisticas();
	}
}
