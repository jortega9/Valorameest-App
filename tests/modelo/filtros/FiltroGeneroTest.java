package modelo.filtros;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import modelo.contenido.Genero;
import utils.Consulta;
import utils.VBD;

class FiltroGeneroTest {
	
	@Test
	void equals_test() {
		AbstractFilter af1 = new FiltroGenero(Genero.ACCION),
					   af2 = new FiltroGenero(Genero.COMEDIA);
		
		assertTrue(!af1.equals(af2));
		
		af2 = new FiltroGenero(Genero.ACCION);
		
		assertTrue(af1.equals(af2));
		
		af2 = new FiltroTitulo("a");
		
		assertTrue(!af1.equals(af2));
	}

	@Test
	void test_1() {
		Consulta cons = new Consulta(Consulta.FROM_ALL_CONTENIDO);
		new FiltroGenero(Genero.ACCION).filtrar(cons);

		String str = cons.buildConsulta();
		
		String correcto = "SELECT * FROM " + Consulta.FROM_ALL_CONTENIDO
						+ " WHERE (" + VBD.ID_CONT + ", 'ACCION') IN (SELECT * FROM " + VBD.T_GENEROS + ")";
		
		assertTrue(str.equals(correcto));
		

		new FiltroGenero(Genero.COMEDIA).filtrar(cons);

		str = cons.buildConsulta();

		correcto += " AND (" + VBD.ID_CONT + ", 'COMEDIA') IN (SELECT * FROM " + VBD.T_GENEROS + ")";

		assertTrue(str.equals(correcto));
	}
}
