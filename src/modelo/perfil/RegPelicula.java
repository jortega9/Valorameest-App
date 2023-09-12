package modelo.perfil;

import modelo.contenido.Contenido;
import modelo.contenido.Pelicula;

public class RegPelicula extends Registro {

	private Pelicula pelicula;
	private int minActual;
	
	public RegPelicula (Perfil per, Pelicula p) {
		super(per);
		pelicula = p;
		minActual = 0;
	}
	
	public RegPelicula(Perfil per, Pelicula p, int min) {
		this(per, p);
		minActual = min;
	}

	@Override
	public Contenido getContenido() {
		return pelicula;
	}
	
	public int getMinActual() {
		return minActual;
	}
	
	public void setMinActual(int min) {
		minActual = min;
	}
}
