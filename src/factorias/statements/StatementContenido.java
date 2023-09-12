package factorias.statements;

import java.text.NumberFormat;
import java.util.Locale;

import factorias.Statement;
import modelo.BD;
import modelo.contenido.Contenido;
import modelo.contenido.Genero;
import modelo.contenido.TipoContenido;
import utils.VBD;

public abstract class StatementContenido<T extends Contenido> implements Statement<Contenido> {

	TipoContenido tipo;
	
	protected StatementContenido(TipoContenido tipo) {
		if (tipo == null)
			throw new IllegalArgumentException("Tipo de StatementContenido no válido");
		this.tipo = tipo;
	}
	
	@SuppressWarnings("unchecked")
	public boolean insert(Contenido elem) throws Exception {
		if (elem.isTipo(tipo)) {
			BD.open().insert(VBD.insert(VBD.T_CONTENIDO, VBD.ATRIBUTOS_CONTENIDO, 
					String.format("'%s', '%s', %s, %d, '%s', '%s', '%s', '%s'", elem.getID(), elem.getTitulo(),
							NumberFormat.getNumberInstance(Locale.ENGLISH).format(elem.getValoracion()), elem.getSeguidores(), elem.getDescripcion(), elem.getFecha(), tipo.toString(), elem.getImagenURL())));
			for (Genero g : elem.getGeneros())
				BD.open().insert(VBD.insert(VBD.T_GENEROS, VBD.ATRIBUTOS_GENEROS, String.format("'%s', '%s'", elem.getID(), g.toString())));
			concreteInsert((T) elem);
			return true;
		}
		return false;
	}
	
	protected abstract void concreteInsert(T elem) throws Exception;
	protected abstract void concreteUpdate(T elem) throws Exception;
	
	@SuppressWarnings("unchecked")
	public boolean update(Contenido elem) throws Exception {
		if(elem.isTipo(tipo)) {
			String[] cols = {VBD.TIT_CONT, VBD.VAL_CONT, VBD.SEG_CONT, VBD.DESC_CONT, VBD.FECHA_CONT, VBD.IMG_CONT},
					vals = {"'" + elem.getTitulo() + "'", NumberFormat.getNumberInstance(Locale.ENGLISH).format(elem.getValoracion()), NumberFormat.getNumberInstance(Locale.ENGLISH).format(elem.getSeguidores()), 
							"'" + elem.getDescripcion() + "'", "'" + elem.getFecha() + "'", "'" + elem.getImagenURL() + "'"},
					colsPK = {VBD.ID_CONT},
					valsPK = {"'" + elem.getID() + "'"};
			BD.open().update(VBD.update(VBD.T_CONTENIDO, cols, vals, colsPK, valsPK));
            
			String[] colsPK2 = {VBD.ID_G};
			BD.open().delete(VBD.delete(VBD.T_GENEROS, colsPK2, valsPK));
			
			for (Genero g : elem.getGeneros())
				BD.open().insert(VBD.insert(VBD.T_GENEROS, VBD.ATRIBUTOS_GENEROS, String.format("'%s', '%s'", elem.getID(), g.toString())));

			concreteUpdate((T) elem);
			return true;
		}
		return false;
	}

}
