package controlador.eventos;

import org.json.JSONObject;

import factorias.perfil.FactoriaPerfil;
import modelo.Aplicacion;
import modelo.perfil.Perfil;

public class NewPerfilEvent implements Event {
	
	JSONObject jo;
	
	/**
	 * Añade un nuevo perfil válido a la aplicación, al que será posible acceder iniciando sesión
	 * @param jo - Descripción del perfil en forma de JSONObject
	 */
	public NewPerfilEvent(JSONObject jo) {
		this.jo = jo;
	}

	@Override
	public void ejecutar(Aplicacion modelo) {
		Perfil p = null;
		try {
			p = FactoriaPerfil.open().createInstance(jo);
			modelo.addPerfil(p);
		}
		catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException("Ya existe un perfil con ese correo.");
		}
	}
}
