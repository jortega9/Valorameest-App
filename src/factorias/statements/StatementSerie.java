package factorias.statements;

import java.text.NumberFormat;
import java.util.Locale;

import modelo.BD;
import modelo.contenido.Serie;
import modelo.contenido.TipoContenido;
import utils.VBD;

public class StatementSerie extends StatementContenido<Serie> {
	
	private static final TipoContenido TIPO = TipoContenido.SERIE;

	protected StatementSerie() {
		super(TIPO);
	}

	@Override
	protected void concreteInsert(Serie elem) throws Exception {
		BD.open().insert(VBD.insert(VBD.T_SERIES, VBD.ATRIBUTOS_S, String.format("'%s', %d, %d", elem.getID(), elem.getNumCaps(), elem.getDuracionCap())));
	}

	@Override
	protected void concreteUpdate(Serie elem) throws Exception {
		String[] cols = {VBD.CAPITULOS_S, VBD.DURCAP_S},
				vals = {"'" + elem.getNumCaps() + "'", NumberFormat.getNumberInstance(Locale.ENGLISH).format(elem.getDuracionCap())},
				colsPK = {VBD.ID_S},
		        valsPK = {"'" + elem.getID() + "'"};
		BD.open().update(VBD.update(VBD.T_SERIES, cols, vals, colsPK, valsPK));
		
		BD.open().delete(VBD.delete(VBD.T_TEMP_SERIES, VBD.S1_TEMP, elem.getID()));
		BD.open().delete(VBD.delete(VBD.T_TEMP_SERIES, VBD.S2_TEMP, elem.getID()));
		for (String s : elem.getOtrasTemp())
			BD.open().insert(VBD.insert(VBD.T_TEMP_SERIES, VBD.S1_TEMP + ", " + VBD.S2_TEMP, String.format("'%s', '%s'", elem.getID(), s)));
	}
}
