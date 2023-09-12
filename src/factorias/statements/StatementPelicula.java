package factorias.statements;

import java.text.NumberFormat;
import java.util.Locale;

import modelo.BD;
import modelo.contenido.Pelicula;
import modelo.contenido.TipoContenido;
import utils.VBD;

public class StatementPelicula extends StatementContenido<Pelicula> {
	
	private static final TipoContenido TIPO = TipoContenido.PELICULA;

	protected StatementPelicula() {
		super(TIPO);
	}

	@Override
	protected void concreteInsert(Pelicula elem) throws Exception {
		BD.open().insert(VBD.insert(VBD.T_PELICULAS, VBD.ATRIBUTOS_P, String.format("'%s', '%s', %d", elem.getID(), elem.getSaga(), elem.getDuracionMins())));
	}

	@Override
	protected void concreteUpdate(Pelicula elem) throws Exception {
		String[] cols = {VBD.SAGA_P, VBD.DURMIN_P},
				vals = {"'" + elem.getSaga() + "'", NumberFormat.getNumberInstance(Locale.ENGLISH).format(elem.getDuracionMins())},
				colsPK = {VBD.ID_P},
		        valsPK = {"'" + elem.getID() + "'"};
		BD.open().update(VBD.update(VBD.T_PELICULAS, cols, vals, colsPK, valsPK));
	}
}
