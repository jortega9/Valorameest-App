package factorias.statements;

import factorias.DBManager;
import modelo.BD;
import modelo.perfil.Perfil;
import modelo.perfil.Registro;
import utils.VBD;

public class ManagerPerfiles implements DBManager<Perfil> {
	
	private static ManagerPerfiles instance = null;
	
	private ManagerPerfiles() {
	}

	/**
	 * Devuelve una instancia al manager de perfiles
	 * @return Instancia de ManagerPerfiles
	 */
	public static ManagerPerfiles open() {
		if (instance == null)
			instance = new ManagerPerfiles();
		return instance;
	}

	@Override
	public void insert(Perfil elem) {
		try {
			BD.open().insert(VBD.insert(VBD.T_PERFILES, VBD.ATRIBUTOS_PERFIL, 
					String.format("'%s', '%s', '%s'", elem.getNickname(), elem.getPassword(), elem.getCorreo())));
		}
		catch (IllegalArgumentException iae) {
			throw iae;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error al insertar datos");
		}
	}

	@Override
	public void delete(Perfil elem) {
		try {
			BD.open().delete(VBD.delete(VBD.T_PERFILES, VBD.CORREO_PERF, elem.getCorreo()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error al eliminar datos");
		}
	}

	@Override
	public void update(Perfil elem) {
		try {
			String[] cols = { VBD.NICK_PERF, VBD.PASSW_PERF },
					vals = { "'" + elem.getNickname() + "'", "'" + elem.getPassword() + "'" }, 
					colsPK = { VBD.CORREO_PERF },
					valsPK = { "'" + elem.getCorreo() + "'" };
			BD.open().update(VBD.update(VBD.T_PERFILES, cols, vals, colsPK, valsPK));
			for (Registro r : elem.getRegistros())
				ManagerRegistros.open().update(r);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error al guardar datos");
		}
	}

	@Override
	public void refresh() {
	}
}
