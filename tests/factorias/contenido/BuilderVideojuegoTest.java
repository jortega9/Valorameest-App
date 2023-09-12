package factorias.contenido;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import modelo.contenido.Genero;
import modelo.contenido.Videojuego;

class BuilderVideojuegoTest {

	@Test
	void test_1() {
		JSONObject jo = new JSONObject();
		jo.put("tipo", "videojuego");
		
		JSONArray ja = new JSONArray();
		ja.put("ACCION");
		ja.put("RPG");
		ja.put("COMEDIA");
		
		JSONArray ja2 = new JSONArray();
		ja2.put("dlc1");
		
		JSONObject jo2 = new JSONObject();
		jo2.put("id", "v1");
		jo2.put("titulo", "Elder Ring");
		jo2.put("valoracion", 9.81);
		jo2.put("seguidores", 203841);
		jo2.put("descripcion", "descripcion de ejemplo");
		jo2.put("fecha", "2022/02/25");
		jo2.put("horasMin", 0);
		jo2.put("horasMax", 1000000);
		jo2.put("generos", ja);
		jo2.put("dlcs", ja2);
		jo2.put("imagen", "url//ejemplo");
		
		jo.put("data", jo2);
		
		List<Genero> gen = new ArrayList<>();
		gen.add(Genero.ACCION);
		gen.add(Genero.RPG);
		gen.add(Genero.COMEDIA);
		
		List<String> str = new ArrayList<>();
		str.add("dlc1");
		
		Videojuego v1 = new BuilderVideojuego().createInstance(jo);
		Videojuego v2 = new Videojuego("v1", "Elder Ring", (float) 9.81, 203841, "descripcion de ejemplo", "2022/02/25", gen, "url//ejemplo", 0, 1000000, str);
		
		assertTrue(v1.toString().equals(v2.toString()));
	}
}
