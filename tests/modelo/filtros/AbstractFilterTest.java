package modelo.filtros;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import modelo.contenido.TipoContenido;

class AbstractFilterTest {

	@Test
	void equals_test() {
		AbstractFilter af1 = new FiltroTitulo("a"),
								  af2 = new FiltroTitulo("b");
		
		assertTrue(af1.equals(af2));
		
		af2 = new FiltroTipo(TipoContenido.SERIE);
		
		assertTrue(!af1.equals(af2));
	}
}
