package factorias.statements;

import java.util.ArrayList;
import java.util.List;

import factorias.DBManager;
import modelo.perfil.Registro;

public class ManagerRegistros implements DBManager<Registro> {
	
	private static ManagerRegistros instance = null;
	
	private List<StatementRegistro<? extends Registro>> sts;
	
	private ManagerRegistros() {
		sts = new ArrayList<>();
		sts.add(new StatementRegVideojuego());
		sts.add(new StatementRegPelicula());
		sts.add(new StatementRegSerie());
	}

	/**
	 * Devuelve una instancia al manager de registros
	 * @return Instancia de ManagerRegistros
	 */
	public static ManagerRegistros open() {
		if (instance == null)
			instance = new ManagerRegistros();
		return instance;
	}

	@Override
	public void insert(Registro elem) {
		boolean ok = false;
		try {
			if (elem != null) {
				for (StatementRegistro<? extends Registro> s : sts)
					if (s.insert(elem)) {
						ok = true;
						break;
					}
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Error en la base de datos, vuelva a intentarlo");
		}
		if (!ok)
			throw new IllegalArgumentException("Valor no válido para insert");
	}

	@Override
	public void delete(Registro elem) {
		throw new UnsupportedOperationException("No se permite eliminar registros");
	}

	@Override
	public void update(Registro elem) {
		boolean ok = false;
		try {
			if (elem != null) {
				for (StatementRegistro<? extends Registro> s : sts)
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
	}
}
