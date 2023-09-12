package modelo.contenido;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PeliculaTest {
	
	@Test
	void test_1() {
		List<Genero> gen = new ArrayList<>();
		gen.add(Genero.COMEDIA);
		Pelicula p = new Pelicula("p1", "pelicula1", (float) 6.1, 3, "desc p1", "0101/01/01", gen, "url", "saga1", 122);
		
		String datosBien = "Título: pelicula1"
				+ "\nTipo: PELICULA"
				+ "\nDuración : 2 h 2 mins"
				+ "\nValoración: 6.1"
				+ "\nSeguidores: 3"
				+ "\nFecha de lanzamiento: 0101/01/01"
				+ "\nGéneros: COMEDIA"
				+ "\nDescripción: desc p1"
				+ "\nSaga: saga1";
		
		assertTrue(p.toString().equals(datosBien));
	}
}
