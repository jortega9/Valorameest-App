package modelo.filtros;

public abstract class AbstractFilter implements FilterStrategy {

	@Override
	public abstract boolean equals(Object obj);
}
