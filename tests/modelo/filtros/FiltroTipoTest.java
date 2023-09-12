package modelo.filtros;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import modelo.contenido.TipoContenido;
import utils.Consulta;
import utils.VBD;

class FiltroTipoTest {

	@Test
	void test_1() {
		Consulta cons = new Consulta(Consulta.FROM_ALL_CONTENIDO);
		new FiltroTipo(TipoContenido.PELICULA).filtrar(cons);

		String str = cons.buildConsulta();
		
		String correcto = "SELECT * FROM " + Consulta.FROM_ALL_CONTENIDO
						+ " WHERE " + VBD.TIPO_CONT + " = 'PELICULA'";
		
		assertTrue(str.equals(correcto));
		
		
		new FiltroTipo(TipoContenido.VIDEOJUEGO).filtrar(cons);

		str = cons.buildConsulta();

		correcto += " AND " + VBD.TIPO_CONT + " = 'VIDEOJUEGO'";
		
		assertTrue(str.equals(correcto));
	}
}
