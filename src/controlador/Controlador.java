package controlador;

import controlador.eventos.Event;
import modelo.Aplicacion;
import modelo.Observador;

public class Controlador {

	private Aplicacion app;
	
	
	public Controlador(Aplicacion a) {
		app = a;
	}
	
	/**
	 * Ejecuta un evento en la aplicación
	 * @param e - Evento a ejecutar
	 */
	public void runEvent(Event e) {
		e.ejecutar(app);
	}
	
	/**
	 * Llama al addObservador de la aplicación
	 * @param o - Observador a añadir
	 */
	public void addObservador(Observador o) {
		app.addObservador(o);
	}

	/**
	 * Llama al removeObservador de la aplicación
	 * @param o - Observador a eliminar
	 */
	public void removeObservador(Observador o) {
		app.removeObservador(o);
	}
}
