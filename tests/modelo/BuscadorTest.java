package modelo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import factorias.statements.ManagerContenido;
import modelo.contenido.Contenido;
import modelo.contenido.Genero;
import modelo.contenido.Pelicula;
import modelo.contenido.Serie;
import modelo.contenido.Videojuego;
import modelo.filtros.FiltroGenero;
import modelo.filtros.FiltroTitulo;
import utils.VBD;

class BuscadorTest {
	
	private static final String INTERVALO_TEST = String.format("(%s = 'test1' OR %s = 'test2' OR %s = 'test3' OR %s = 'test4')", VBD.ID_CONT, VBD.ID_CONT, VBD.ID_CONT, VBD.ID_CONT);
	
	static List<Contenido> crearListaContenido(){
		List<Contenido> listaContenido = new ArrayList<>();
		List<Genero> generos1 = new ArrayList<>();
		generos1.add(Genero.ACCION);
		listaContenido.add(new Pelicula(
				"test1", 
				"Una pelicula", 
				9, 
				1, 
				"Una descripcion sosa", 
				"2020/02/20", 
				generos1, 
				"saga",
				"", 100
		));
		List<Genero> generos2 = new ArrayList<>();
		generos2.add(Genero.COMEDIA);
		listaContenido.add(new Serie(
				"test2", 
				"Una serie", 
				8, 
				2, 
				"Una descripcion todavia mas sosa", 
				"2121/01/21", 
				generos2,
				"", 10, 
				20, 
				new ArrayList<String>()
		));
		List<Genero> generos3 = new ArrayList<>();
		generos3.add(Genero.RPG);
		listaContenido.add(new Videojuego(
				"test3", 
				"Un videojuego", 
				10, 
				1000, 
				"Una descripcion demasiado sosa", 
				"2222/02/22", 
				generos3,
				"", 30, 
				50, 
				new ArrayList<String>()
		));
		List<Genero> generos4 = new ArrayList<>();
		generos4.add(Genero.ACCION);
		listaContenido.add(new Videojuego(
				"test4", 
				"Un videojuego", 
				10, 
				1000, 
				"Una descripcion demasiado sosa", 
				"2222/02/22", 
				generos4,
				"", 30, 
				50, 
				new ArrayList<String>()
		));
		return listaContenido;
	}
	
	private boolean compararListaConstruida(List<Contenido> resultado, List<Contenido> comparar) {
		if (resultado.size() != comparar.size())
			return false;
		
		for (Contenido c : resultado)
			if (!comparar.contains(c))
				return false;
		
		return true;
	}
	
	@Test
	void test_sinFiltros() throws Exception {
		BD.open().delete(VBD.deleteTests(VBD.T_CONTENIDO, VBD.ID_CONT));
		List<Contenido> lista = crearListaContenido();
		for (Contenido c : lista)
			ManagerContenido.open().insert(c);
		
		Buscador buscador = new Buscador();
		buscador.addFiltro((consulta) -> { consulta.putWhere(INTERVALO_TEST); });
		buscador.aplicarFiltros();
		List<Contenido> resultado = buscador.extraerElementos(10);
		
		BD.open().delete(VBD.deleteTests(VBD.T_CONTENIDO, VBD.ID_CONT));
		
		assertTrue(compararListaConstruida(resultado, lista));
	}

	@Test
	void test_unFiltro() throws Exception {
		List<Contenido> lista = new ArrayList<>();
		lista.add(crearListaContenido().get(1));
		
		for (Contenido c : lista)
			ManagerContenido.open().insert(c);
		
		Buscador buscador = new Buscador();
		buscador.addFiltro((consulta) -> { consulta.putWhere(INTERVALO_TEST); });
		buscador.addFiltro(new FiltroTitulo("Una serie"));
		buscador.aplicarFiltros();
		List<Contenido> resultado = buscador.extraerElementos(10);
		
		BD.open().delete(VBD.deleteTests(VBD.T_CONTENIDO, VBD.ID_CONT));
		
		assertTrue(compararListaConstruida(resultado, lista));
	}
	
	@Test
	void test_variosFiltros() throws Exception {
		List<Contenido> listaContenido = crearListaContenido();
		List<Contenido> lista = new ArrayList<>();
		lista.add(listaContenido.get(2));
		lista.add(listaContenido.get(3));
		
		for (Contenido c : lista)
			ManagerContenido.open().insert(c);
		
		Buscador buscador = new Buscador();
		buscador.addFiltro((consulta) -> { consulta.putWhere(INTERVALO_TEST); });
		buscador.addFiltro(new FiltroTitulo("Un videojuego"));
		buscador.aplicarFiltros();
		List<Contenido> resultado = buscador.extraerElementos(10);
		
		buscador.addFiltro(new FiltroGenero(Genero.ACCION));
		buscador.aplicarFiltros();
		List<Contenido> resultado2 = buscador.extraerElementos(10);
		
		BD.open().delete(VBD.deleteTests(VBD.T_CONTENIDO, VBD.ID_CONT));
		
		assertTrue(compararListaConstruida(resultado, lista));

		lista.remove(0);
		assertTrue(compararListaConstruida(resultado2, lista));
	}
	
	@Test
	void test_getID() throws Exception {
		Contenido peli = crearListaContenido().get(0);
		ManagerContenido.open().insert(peli);
		
		Buscador buscador = new Buscador();
		Contenido resultado = buscador.getWithID("test1");
		
		Contenido resultado2 = buscador.getWithID("test2");
		
		BD.open().delete(VBD.deleteTests(VBD.T_CONTENIDO, VBD.ID_CONT));
		
		assertTrue(peli.equals(resultado));
		
		assertTrue(resultado2 == null);
	}
}
