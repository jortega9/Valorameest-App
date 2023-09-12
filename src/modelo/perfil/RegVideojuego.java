package modelo.perfil;

import modelo.contenido.Contenido;
import modelo.contenido.Videojuego;

public class RegVideojuego extends Registro {

	private Videojuego videojuego;
	private int horasJugadas;
	
	public RegVideojuego (Perfil per, Videojuego v) {
		super(per);
		videojuego = v;
		horasJugadas = 0;
	}
	
	public RegVideojuego(Perfil per, Videojuego v, int horas) {
		this(per, v);
		horasJugadas = horas;
	}

	@Override
	public Contenido getContenido() {
		return videojuego;
	}
	
	public int getHorasJugadas() {
		return horasJugadas;
	}
	
	public void setHorasJugadas(int horas) {
		horasJugadas = horas;
	}
}
