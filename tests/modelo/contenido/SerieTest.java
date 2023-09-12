package modelo.contenido;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class SerieTest {

	@Test
	void test_1() {
		List<Genero> gen = new ArrayList<>();
		gen.add(Genero.ACCION);
		List<String> temps = new ArrayList<>();
		Serie p = new Serie("s1", "serie1", (float) 8.35, 9213, "desc s1", "01/01/0101", gen, "", 12, 25, temps);
		
		String datosBien = "Título: serie1"
				+ "\nTipo: SERIE"
				+ "\nDuración : 12 capítulos (25 mins)"
				+ "\nValoración: 8.35"
				+ "\nSeguidores: 9213"
				+ "\nFecha de lanzamiento: 01/01/0101"
				+ "\nGéneros: ACCION"
				+ "\nDescripción: desc s1"
				+ "\nTemporada única";
		
		assertTrue(p.toString().equals(datosBien));
		

		temps.add("s2");
		temps.add("s3");
		p = new Serie("s1", "serie1", (float) 8.35, 9213, "desc s1", "01/01/0101", gen, "", 12, 25, temps);
		
		datosBien = "Título: serie1"
				+ "\nTipo: SERIE"
				+ "\nDuración : 12 capítulos (25 mins)"
				+ "\nValoración: 8.35"
				+ "\nSeguidores: 9213"
				+ "\nFecha de lanzamiento: 01/01/0101"
				+ "\nGéneros: ACCION"
				+ "\nDescripción: desc s1"
				+ "\n2 temporada(s) más -> ID: s2, s3";
		
		assertTrue(p.toString().equals(datosBien));
	}
}
