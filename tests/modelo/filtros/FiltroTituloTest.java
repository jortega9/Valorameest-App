package modelo.filtros;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import utils.Consulta;
import utils.VBD;

class FiltroTituloTest {

	@Test
	void test_1() {
		Consulta cons = new Consulta(Consulta.FROM_ALL_CONTENIDO);
		new FiltroTitulo("serie1").filtrar(cons);
		
		String str = cons.buildConsulta();
		
		String correcto = "SELECT * FROM " + Consulta.FROM_ALL_CONTENIDO
						+ " WHERE lower(" + VBD.TIT_CONT + ") LIKE(lower('serie1%'))";
		
		assertTrue(str.equals(correcto));
		
		
		new FiltroTitulo("peli2").filtrar(cons);
		str = cons.buildConsulta();
		
		correcto += " AND lower(" + VBD.TIT_CONT + ") LIKE(lower('peli2%'))";

		assertTrue(str.equals(correcto));
	}
}
