package factorias.statements;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import modelo.BD;
import modelo.perfil.Perfil;
import utils.Consulta;
import utils.VBD;

class ManagerPerfilesTest {

	@Test
	void test() throws SQLException, Exception {
		Perfil p = new Perfil("test1", "test1", "test1");
		
		ManagerPerfiles.open().insert(p);
		
		Consulta cons = new Consulta(Consulta.FROM_PERFILES);
		cons.putWhere(String.format("%s = '%s'", VBD.CORREO_PERF, "test1"));
		
		assertTrue(BD.open().select(cons.buildConsulta()).next());
		
		ManagerPerfiles.open().delete(p);
		
		assertTrue(!BD.open().select(cons.buildConsulta()).next());
	}
}
