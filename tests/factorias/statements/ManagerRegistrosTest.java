package factorias.statements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import factorias.perfil.FactoriaRegistros;
import modelo.BD;
import modelo.contenido.Videojuego;
import modelo.perfil.EstadoReg;
import modelo.perfil.Perfil;
import modelo.perfil.RegVideojuego;
import modelo.perfil.Registro;
import utils.Consulta;
import utils.VBD;

class ManagerRegistrosTest {

	@Test
	void test() {
		Videojuego v = new Videojuego("test2", "test2", 0, 0, "test2", "1111-11-11", new ArrayList<>(), "test2", 0, 0, new ArrayList<>());
		Perfil p = new Perfil("test1", "test1", "test1");
		ManagerContenido.open().delete(v);
		ManagerPerfiles.open().delete(p);
		ManagerContenido.open().insert(v);
		ManagerPerfiles.open().insert(p);

		Registro reg = new RegVideojuego(p, v);
		reg.switchEstado(EstadoReg.SIGUIENDO);
		
		Consulta cons = new Consulta(Consulta.FROM_REGS);
		cons.putWhere(String.format("%s = '%s' AND %s = '%s'", VBD.ID_REG, reg.getContenido().getID(), VBD.PERF_REG, reg.getPerfil().getCorreo()));
		try {
			ResultSet rs = BD.open().select(cons.buildConsulta());
			assertTrue(!rs.next());

			ManagerRegistros.open().insert(reg);
			rs = BD.open().select(cons.buildConsulta());
			assertTrue(rs.next());
			reg = FactoriaRegistros.open(p).loadInstance(rs);
			assertEquals(reg.getValoracion(), -1);
			assertTrue(reg.hasEstado(EstadoReg.SIGUIENDO));
			
			reg.setValoracion(8);
			reg.switchEstado(EstadoReg.FAVORITOS);
			reg.switchEstado(EstadoReg.SIGUIENDO);
			ManagerRegistros.open().update(reg);
			rs = BD.open().select(cons.buildConsulta());
			rs.next();
			reg = FactoriaRegistros.open(p).loadInstance(rs);
			assertEquals(reg.getValoracion(), 8);
			assertTrue(reg.hasEstado(EstadoReg.FAVORITOS));
			assertTrue(!reg.hasEstado(EstadoReg.SIGUIENDO));
			
			ManagerContenido.open().delete(v);
			ManagerPerfiles.open().delete(p);
			
			rs = BD.open().select(cons.buildConsulta());
			assertTrue(!rs.next());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
