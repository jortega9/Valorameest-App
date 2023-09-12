package factorias.contenido;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import modelo.contenido.Genero;
import modelo.contenido.Pelicula;

class BuilderPeliculaTest {

	@Test
	void test_1() {
		JSONObject jo = new JSONObject();
		jo.put("tipo", "pelicula");
		
		JSONArray ja = new JSONArray();
		ja.put("COMEDIA");
		
		JSONObject jo2 = new JSONObject();
		jo2.put("id", "p1");
		jo2.put("titulo", "pelicula1");
		jo2.put("valoracion", 4.9);
		jo2.put("seguidores", 3829);
		jo2.put("descripcion", "descripcion de ejemplo2");
		jo2.put("fecha", "2022/10/03");
		jo2.put("imagen", "url//ejemplo");
		jo2.put("generos", ja);
		jo2.put("saga", "saga1");
		jo2.put("duracionMins", 126);
		
		jo.put("data", jo2);
		
		List<Genero> gen = new ArrayList<>();
		gen.add(Genero.COMEDIA);
		
		Pelicula p1 = new BuilderPelicula().createInstance(jo);
		Pelicula p2 = new Pelicula("p1", "pelicula1", (float) 4.9, 3829, "descripcion de ejemplo2", "2022/10/03", gen, "url//ejemplo", "saga1", 126);
		
		assertTrue(p1.toString().equals(p2.toString()));
	}
}
