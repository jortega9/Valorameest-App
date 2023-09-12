package factorias.contenido;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import modelo.contenido.Genero;
import modelo.contenido.Serie;

class BuilderSerieTest {

	@Test
	void test_1() {
		JSONObject jo = new JSONObject();
		jo.put("tipo", "serie");
		
		JSONArray ja = new JSONArray();
		ja.put("ACCION");
		ja.put("DRAMA");
		
		JSONArray ja2 = new JSONArray();
		ja2.put("s2");
		ja2.put("s3");
		
		JSONObject jo2 = new JSONObject();
		jo2.put("id", "s1");
		jo2.put("titulo", "serie1");
		jo2.put("valoracion", 0);
		jo2.put("seguidores", 0);
		jo2.put("descripcion", "descripcion de ejemplo");
		jo2.put("fecha", "2022/03/10");
		jo2.put("imagen", "url//ejemplo");
		jo2.put("capitulos", 12);
		jo2.put("duracionCap", 20);
		jo2.put("generos", ja);
		jo2.put("otrasTemp", ja2);
		
		jo.put("data", jo2);
		
		List<Genero> gen = new ArrayList<>();
		gen.add(Genero.ACCION);
		gen.add(Genero.DRAMA);
		
		List<String> str = new ArrayList<>();
		str.add("s2");
		str.add("s3");
		
		Serie s1 = new BuilderSerie().createInstance(jo);
		Serie s2 = new Serie("s1", "serie1", 0, 0, "descripcion de ejemplo", "2022/03/10", gen, "url//ejemplo", 12, 20, str);
		
		assertTrue(s1.toString().equals(s2.toString()));
	}
}
