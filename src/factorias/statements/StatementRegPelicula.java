package factorias.statements;

import modelo.BD;
import modelo.contenido.TipoContenido;
import modelo.perfil.RegPelicula;
import utils.VBD;

public class StatementRegPelicula extends StatementRegistro<RegPelicula> {
	
	private static final TipoContenido TIPO = TipoContenido.PELICULA;

	protected StatementRegPelicula() {
		super(TIPO);
	}

	@Override
	protected void concreteInsert(RegPelicula elem) throws Exception {
		BD.open().insert(VBD.insert(VBD.T_REG_PELICULAS, VBD.ATRIBUTOS_REG_PEL, String.format("'%s', '%s', %d", 
				elem.getContenido().getID(), elem.getPerfil().getCorreo(), elem.getMinActual())));
	}

	@Override
	protected void concreteUpdate(RegPelicula elem) throws Exception {
		String[] cols = { VBD.MINS_RP }, vals = { Integer.toString(elem.getMinActual()) }, 
				colsPK = { VBD.ID_RP, VBD.PERF_RP }, valsPK = { "'" + elem.getContenido().getID() + "'", "'" + elem.getPerfil().getCorreo() + "'" };
		BD.open().update(VBD.update(VBD.T_REG_PELICULAS, cols, vals, colsPK, valsPK));
	}
}
