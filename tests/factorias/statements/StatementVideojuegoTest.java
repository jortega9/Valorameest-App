package factorias.statements;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import factorias.contenido.FactoriaContenido;
import modelo.BD;
import modelo.contenido.Contenido;
import modelo.contenido.Genero;
import modelo.contenido.Videojuego;
import utils.Consulta;
import utils.VBD;

class StatementVideojuegoTest {

	@Test
	void test() throws Exception {
		List<Genero> gens = new ArrayList<>();
		gens.add(Genero.ACCION); gens.add(Genero.RPG);
		List<String> dlcs = new ArrayList<>();
		dlcs.add("test2");
		Videojuego v = new Videojuego("test1", "test1", 5, 15, "test1", "1001-01-01", gens, "", 20, 30, dlcs);
		
		new StatementVideojuego().insert(v);
		
		Consulta cons = new Consulta(Consulta.FROM_ALL_CONTENIDO);
		cons.putWhere(String.format("%s = '%s'", VBD.ID_CONT, "test1"));
		ResultSet rs = BD.open().select(cons.buildConsulta()); rs.next();
		Contenido v2 = FactoriaContenido.open().loadInstance(rs);
		
		BD.open().delete(VBD.deleteTests(VBD.T_CONTENIDO, VBD.ID_CONT));
		
		assertTrue(v.detalles().similar(v2.detalles()));
	}
}
