package modelo;

import java.util.List;

import modelo.contenido.Contenido;
import modelo.perfil.Perfil;

public interface Observador {
	
	/**
	 * Método llamado cuando se ha realizado una nueva búsqueda desde cero en la aplicación (aún sin extraer sus elementos)
	 */
	public void onNuevaBusqueda();
	
	/**
	 * Método llamado cuando se extraen los elementos de una búsqueda ya realizada
	 * @param nuevo - Lista de nuevos elementos extraídos de la búsqueda
	 */
	public void onExtraerDeBusqueda(List<Contenido> nuevo);
	
	/**
	 * Método llamado cuando se busca un elemento concreto mediante su ID
	 * @param cont - Contenido resultante, null si no se ha encontrado
	 */
	public void onBuscarPorId(Contenido cont);
	
	/**
	 * Método llamado cuando se ha realizado algún cambio en la información del perfil activo
	 * @param p - Perfil activo, o null si no se ha iniciado sesión
	 */
	public void onCambioPerfil(Perfil p);
	
	/**
	 * Método llamado cuando un Observable agrega un Observador
	 * @param nuevo - Lista de elementos ya buscados
	 * @param p - Perfil activo, o null si no se ha iniciado sesión
	 */
	public void onRegister(List<Contenido> nuevo, Perfil p);
	
	/**
	 * Método llamado cuando se ha producido un error o excepción en la ejecución de la aplicación
	 * @param err - Mensaje de error
	 */
	public void onError(String err);
}
