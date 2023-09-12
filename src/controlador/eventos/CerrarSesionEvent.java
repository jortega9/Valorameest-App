package controlador.eventos;

import modelo.Aplicacion;

public class CerrarSesionEvent implements Event {

	/**
	 * Cierra la sesión del perfil actualmente activo de la aplicación (en caso de haberlo)
	 */
	public CerrarSesionEvent() {}
	
	@Override
	public void ejecutar(Aplicacion modelo) {
		modelo.cerrarSesion();
	}
}
