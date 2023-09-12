package modelo.perfil;

import modelo.contenido.Contenido;
import modelo.contenido.Serie;

public class RegSerie extends Registro {

	private Serie serie;
	private int capsVistos;
	
	public RegSerie (Perfil per, Serie s) {
		super(per);
		serie = s;
		capsVistos = 0;
	}
	
	public RegSerie (Perfil per, Serie s, int caps) {
		this(per, s);
		capsVistos = caps;
	}
	
	@Override
	public Contenido getContenido() {
		return serie;
	}
	
	public int getCapsVistos() {
		return capsVistos;
	}
	
	public void setCapsVistos(int caps) {
		capsVistos = caps;
	}
}
