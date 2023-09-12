package modelo.contenido;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ContenidoTest {

	@Test
	void test_1() {
		List<Genero> gens = new ArrayList<>();
		gens.add(Genero.ACCION);
		gens.add(Genero.COMEDIA);
		Contenido c = new Videojuego("v1", "videojuego1", (float) 5.25, 831, "descripción v1", "0101/01/01", gens, "", 0, 0, null);
		
		assertEquals(c.getID(), "v1");
		assertEquals(c.getTitulo(), "videojuego1");
		
		assertEquals(c.getValoracion(), 5.25);
		c.setValoracion((float) 5.4);
		assertEquals(NumberFormat.getNumberInstance().format(c.getValoracion()), "5,4");
		
		assertEquals(c.getSeguidores(), 831);
		assertEquals(c.getDescripcion(), "descripción v1");
		assertEquals(c.getFecha(), "0101/01/01");
		
		assertTrue(c.hasGenero(Genero.ACCION));
		assertTrue(!c.hasGenero(Genero.DRAMA));
		
		assertTrue(c.isTipo(TipoContenido.VIDEOJUEGO));
		assertTrue(!c.isTipo(TipoContenido.SERIE));
	}
}
