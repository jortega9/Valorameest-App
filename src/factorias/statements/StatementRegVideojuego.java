package factorias.statements;

import modelo.BD;
import modelo.contenido.TipoContenido;
import modelo.perfil.RegVideojuego;
import utils.VBD;

public class StatementRegVideojuego extends StatementRegistro<RegVideojuego> {
	
	private static final TipoContenido TIPO = TipoContenido.VIDEOJUEGO;

	protected StatementRegVideojuego() {
		super(TIPO);
	}

	@Override
	protected void concreteInsert(RegVideojuego elem) throws Exception {
		BD.open().insert(VBD.insert(VBD.T_REG_VIDEOJUEGOS, VBD.ATRIBUTOS_REG_VID, String.format("'%s', '%s', %d", 
				elem.getContenido().getID(), elem.getPerfil().getCorreo(), elem.getHorasJugadas())));
	}

	@Override
	protected void concreteUpdate(RegVideojuego elem) throws Exception {
		String[] cols = { VBD.HORAS_RV }, vals = { Integer.toString(elem.getHorasJugadas()) }, 
				colsPK = { VBD.ID_RV, VBD.PERF_RV }, valsPK = { "'" + elem.getContenido().getID() + "'", "'" + elem.getPerfil().getCorreo() + "'" };
		BD.open().update(VBD.update(VBD.T_REG_VIDEOJUEGOS, cols, vals, colsPK, valsPK));
	}
}
