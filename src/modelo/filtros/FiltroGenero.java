package modelo.filtros;

import modelo.contenido.Genero;
import utils.Consulta;
import utils.VBD;

public class FiltroGenero extends AbstractFilter {
	
	private Genero gen;
	
	/**
	 * Crea un filtro que utiliza el parámetro gen para filtrar el contenido según si contiene dicho género
	 * @param gen - género buscado
	 */
	public FiltroGenero(Genero gen) {
		this.gen = gen;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass()) && this.gen.equals(((FiltroGenero) obj).gen);
	}

	@Override
	public void filtrar(Consulta consulta) {
		Consulta c = new Consulta(Consulta.FROM_GENEROS);
		consulta.putWhere(String.format("(%s, '%s') IN (%s)", VBD.ID_CONT, gen.toString(), c.buildConsulta()));
	}
}
