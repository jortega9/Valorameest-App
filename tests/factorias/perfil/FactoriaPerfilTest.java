package factorias.perfil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import factorias.statements.ManagerPerfiles;
import modelo.BD;
import modelo.perfil.Perfil;
import utils.Consulta;
import utils.VBD;

class FactoriaPerfilTest {

	@Test
	void test_create() throws Exception {
		BD.open().delete(VBD.deleteTests(VBD.T_PERFILES, VBD.CORREO_PERF));
		
		Perfil p = new Perfil("test1", "test1", "test1");
		String sj = "{ \"correo\" : \"test1\", \"password\" : \"test1\", \"nickname\" : \"test1\" }";
		JSONObject jo = new JSONObject(sj);
		
		assertEquals(p, FactoriaPerfil.open().createInstance(jo));
	}
	
	@Test
	void test_load() throws Exception {
		BD.open().delete(VBD.deleteTests(VBD.T_PERFILES, VBD.CORREO_PERF));
		
		Perfil p = new Perfil("test1", "test1", "test1");
		ManagerPerfiles.open().insert(p);

		Consulta cons = new Consulta(Consulta.FROM_PERFILES);
		cons.putWhere(String.format("%s = '%s'", VBD.CORREO_PERF, "test1"));
		ResultSet rs = BD.open().select(cons.buildConsulta());
		rs.next();
		
		assertEquals(p, FactoriaPerfil.open().loadInstance(rs));
	}
}
