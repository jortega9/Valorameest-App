package modelo.filtros;

import modelo.contenido.TipoContenido;
import utils.Consulta;
import utils.VBD;

public class FiltroTipo extends AbstractFilter {
	
	TipoContenido tipo;
	
	/**
	 * Crea un filtro que utiliza el parámetro tipo para filtrar el contenido según si coincide con su tipo de contenido
	 * @param tipo - tipo de contenido buscado
	 */
	public FiltroTipo(TipoContenido tipo) {
		this.tipo = tipo;
	}

	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}

	@Override
	public void filtrar(Consulta consulta) {
		consulta.putWhere(String.format("%s = '%s'", VBD.TIPO_CONT, tipo.toString()));
	}
}
