package controlador.eventos;

import modelo.Aplicacion;
import modelo.perfil.ModifPerfilStrategy;

public class ModificarPerfilEvent implements Event {

	private ModifPerfilStrategy mps;
	
	/**
	 * Realiza cambios en el perfil activo de la aplicación a partir de una estrategia dada
	 * @param mps - Estrategia a seguir para modificar el perfil
	 */
	public ModificarPerfilEvent(ModifPerfilStrategy mps) {
		this.mps = mps;
	}

	@Override
	public void ejecutar(Aplicacion app) {
		app.modificarPerfil(mps);
	}
}
