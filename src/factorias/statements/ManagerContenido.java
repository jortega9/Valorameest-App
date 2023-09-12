package factorias.statements;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import factorias.DBManager;
import modelo.BD;
import modelo.contenido.Contenido;
import modelo.perfil.EstadoReg;
import utils.Consulta;
import utils.VBD;

public class ManagerContenido implements DBManager<Contenido> {
	
	private static ManagerContenido instance = null;
	
	private List<StatementContenido<? extends Contenido>> sts;
	
	
	private ManagerContenido() {
		sts = new ArrayList<>();
		sts.add(new StatementVideojuego());
		sts.add(new StatementSerie());
		sts.add(new StatementPelicula());
	}
	
	/**
	 * Devuelve una instancia al manager de contenido
	 * @return Instancia de ManagerContenido
	 */
	public static ManagerContenido open() {
		if (instance == null)
			instance = new ManagerContenido();
		return instance;
	}

	@Override
	public void insert(Contenido elem) {
		boolean ok = false;
		try {
			if (elem != null) {
				for (StatementContenido<? extends Contenido> s : sts)
					if (s.insert(elem)) {
						ok = true;
						break;
					}
			}
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		catch (Exception e) {
			throw new RuntimeException("Error al insertar datos");
		}
		if (!ok)
			throw new IllegalArgumentException("Valor no válido para insert");
	}

	@Override
	public void delete(Contenido elem) {
		try {
			BD.open().delete(VBD.delete(VBD.T_CONTENIDO, VBD.ID_CONT, elem.getID()));
		} 
		catch (NullPointerException npe) {
			throw new IllegalArgumentException("No se ha encontrado el contenido");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error al eliminar datos");
		}
	}

	@Override
	public void update(Contenido elem) {
		boolean ok = false;
		try {
			if (elem != null) {
				for (StatementContenido<? extends Contenido> s : sts)
					if (s.update(elem)) {
						ok = true;
						break;
					}
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Error en la base de datos, vuelva a intentarlo");
		}
		if (!ok)
			throw new IllegalArgumentException("Valor no válido para update");
	}
	
	@Override
	public void refresh() {
		try {
			ResultSet rs = BD.open().select(new Consulta(Consulta.FROM_ALL_CONTENIDO).buildConsulta());
			while (rs.next()) {
				Consulta cons = new Consulta(Consulta.average(VBD.VALOR_REG), Consulta.FROM_REGS);
				cons.putWhere(String.format("%s = '%s'", VBD.ID_REG, rs.getString(VBD.ID_CONT)));
				cons.putWhere(VBD.VALOR_REG + " >= 0");
				
				ResultSet avg = BD.open().select(cons.buildConsulta()); avg.next();
				float val = avg.getFloat(1);
				
				cons = new Consulta(Consulta.count(Consulta.SELECT_ALL), Consulta.FROM_ESTADO_REG);
				cons.putWhere(String.format("%s = '%s'", VBD.ID_EST_REG, rs.getString(VBD.ID_CONT)));
				cons.putWhere(String.format("%s = '%s'", VBD.ESTADO_EST_REG, EstadoReg.SIGUIENDO.toString()));

				ResultSet count = BD.open().select(cons.buildConsulta()); count.next();
				int segs = count.getInt(1);
				
				String[] cols = { VBD.VAL_CONT, VBD.SEG_CONT }, vals = { Float.toString(val), Integer.toString(segs) },
						colsPK = { VBD.ID_CONT }, valsPK = { "'" + rs.getString(VBD.ID_CONT) + "'" };
				BD.open().update(VBD.update(VBD.T_CONTENIDO, cols, vals, colsPK, valsPK));
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Error al actualizar datos");
		}
	}
}
