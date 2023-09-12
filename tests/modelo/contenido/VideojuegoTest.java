package modelo.contenido;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class VideojuegoTest {

	@Test
	void test_1() {
		List<Genero> gen = new ArrayList<>();
		gen.add(Genero.ACCION);
		gen.add(Genero.RPG);
		List<String> dlcs = new ArrayList<>();
		Videojuego p = new Videojuego("v1", "videojuego1", (float) 9.98, 95213, "desc v1", "01/01/0101", gen, "", 20, 30, dlcs);
		
		String datosBien = "Título: videojuego1"
				+ "\nTipo: VIDEOJUEGO"
				+ "\nDuración : 20-30 h"
				+ "\nValoración: 9.98"
				+ "\nSeguidores: 95213"
				+ "\nFecha de lanzamiento: 01/01/0101"
				+ "\nGéneros: ACCION RPG"
				+ "\nDescripción: desc v1"
				+ "\nSin DLCs";
		
		assertTrue(p.toString().equals(datosBien));
		

		dlcs.add("dlc1");
		dlcs.add("dlc2");
		p = new Videojuego("v1", "videojuego1", (float) 9.98, 95213, "desc v1", "01/01/0101", gen, "", 20, 30, dlcs);
		
		datosBien = "Título: videojuego1"
				+ "\nTipo: VIDEOJUEGO"
				+ "\nDuración : 20-30 h"
				+ "\nValoración: 9.98"
				+ "\nSeguidores: 95213"
				+ "\nFecha de lanzamiento: 01/01/0101"
				+ "\nGéneros: ACCION RPG"
				+ "\nDescripción: desc v1"
				+ "\n2 DLC(s) -> ID: dlc1, dlc2";
		
		assertTrue(p.toString().equals(datosBien));
	}
}
