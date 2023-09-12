package modelo.filtros;

import utils.Consulta;

public class FiltroOrden extends AbstractFilter {
	
	private String campo;
	private boolean desc;
	
	/**
	 * Crea un filtro que utiliza el campo (nombre de una columna en la base de datos) proporcionado para ordenar una selección en función de sus valores. Este orden puede ser ascendente o descendente
	 * @param campo - Valores a utilizar en la ordenación
	 * @param desc - True para seleccionar en orden descendente, False para orden ascendente
	 */
	public FiltroOrden(String campo, boolean desc) {
		this.campo = campo;
		this.desc = desc;
	}

	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}

	@Override
	public void filtrar(Consulta consulta) {
		consulta.putOrder(campo + (desc ? " desc" : " asc"));
	}
}
