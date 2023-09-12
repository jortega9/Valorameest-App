package modelo;

public interface Observable {

	public void addObservador(Observador o);
	
	public void removeObservador(Observador o);
}
