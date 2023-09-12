package controlador.eventos;

import modelo.Aplicacion;
import modelo.perfil.Perfil;

public class IniciarSesionEvent implements Event {
	
	private String correo;
	private String contra;
	
	/**
	 * Inicia sesión de un perfil existente en la aplicación mediante su correo y contraseña. La ejecución lanzará excepción si el correo no existe o la contraseña es incorrecta.
	 * @param correo - Correo del perfil buscado
	 * @param contra - Contraseña del perfil buscado
	 */
	public IniciarSesionEvent(String correo, String contra) {
		this.correo = correo;
		this.contra = contra;
	}

	@Override
	public void ejecutar(Aplicacion modelo) {
		Perfil p = modelo.getPerfilWithCorreo(correo);
		if (p != null) {
			if (p.getPassword().equals(contra)) {
				modelo.iniciarSesion(p);
			}
			else
				throw new IllegalArgumentException("Contraseña incorrecta");
		}
		else 
			throw new IllegalArgumentException("Correo incorrecto");
	}
}
