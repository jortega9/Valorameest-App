package modelo.filtros;

import utils.Consulta;
import utils.VBD;

public class FiltroTitulo extends AbstractFilter {
	
	private String tit;
	
	/**
	 * Crea un filtro que utiliza el parámetro tit para filtrar el contenido según si coincide con su título
	 * @param tit - título buscado
	 */
	public FiltroTitulo(String tit) {
		this.tit = tit;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}

	@Override
	public void filtrar(Consulta consulta) {
		consulta.putWhere(String.format("lower(%s) LIKE(lower('%s'))", VBD.TIT_CONT, tit + "%"));
	}
}
