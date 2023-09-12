package factorias.statements;

import java.text.NumberFormat;
import java.util.Locale;

import modelo.BD;
import modelo.contenido.TipoContenido;
import modelo.contenido.Videojuego;
import utils.VBD;

public class StatementVideojuego extends StatementContenido<Videojuego> {
	
	private static final TipoContenido TIPO = TipoContenido.VIDEOJUEGO;

	protected StatementVideojuego() {
		super(TIPO);
	}

	@Override
	protected void concreteInsert(Videojuego elem) throws Exception {
		BD.open().insert(VBD.insert(VBD.T_VIDEOJUEGOS, VBD.ATRIBUTOS_V, String.format("'%s', %d, %d", elem.getID(), elem.getHorasMin(), elem.getHorasMax())));
		for (String dlc : elem.getDlcs())
			BD.open().insert(VBD.insert(VBD.T_DLC, VBD.ATRIBUTOS_DLC, String.format("'%s', '%s'", elem.getID(), dlc)));
	}

	@Override
	protected void concreteUpdate(Videojuego elem) throws Exception {
		String[] cols = {VBD.HORASMIN_V, VBD.HORASMAX_V},
				vals = {NumberFormat.getNumberInstance(Locale.ENGLISH).format(elem.getHorasMin()), NumberFormat.getNumberInstance(Locale.ENGLISH).format(elem.getHorasMax())},
				colsPK = {VBD.ID_V},
		        valsPK = {"'" + elem.getID() + "'"};
		BD.open().update(VBD.update(VBD.T_VIDEOJUEGOS, cols, vals, colsPK, valsPK));
		
		BD.open().delete(VBD.delete(VBD.T_DLC, VBD.ID_DLC, elem.getID()));
		for (String dlc : elem.getDlcs())
			BD.open().insert(VBD.insert(VBD.T_DLC, VBD.ATRIBUTOS_DLC, String.format("'%s', '%s'", elem.getID(), dlc)));
	}
}
