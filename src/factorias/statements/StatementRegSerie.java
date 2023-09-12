package factorias.statements;

import modelo.BD;
import modelo.contenido.TipoContenido;
import modelo.perfil.RegSerie;
import utils.VBD;

public class StatementRegSerie extends StatementRegistro<RegSerie> {
	
	private static final TipoContenido TIPO = TipoContenido.SERIE;

	protected StatementRegSerie() {
		super(TIPO);
	}

	@Override
	protected void concreteInsert(RegSerie elem) throws Exception {
		BD.open().insert(VBD.insert(VBD.T_REG_SERIES, VBD.ATRIBUTOS_REG_SER, String.format("'%s', '%s', %d", 
				elem.getContenido().getID(), elem.getPerfil().getCorreo(), elem.getCapsVistos())));
	}

	@Override
	protected void concreteUpdate(RegSerie elem) throws Exception {
		String[] cols = { VBD.CAPS_RS }, vals = { Integer.toString(elem.getCapsVistos()) }, 
				colsPK = { VBD.ID_RS, VBD.PERF_RS }, valsPK = { "'" + elem.getContenido().getID() + "'", "'" + elem.getPerfil().getCorreo() + "'" };
		BD.open().update(VBD.update(VBD.T_REG_SERIES, cols, vals, colsPK, valsPK));
	}
}
