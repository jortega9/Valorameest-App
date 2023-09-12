package modelo.perfil;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import modelo.contenido.Genero;
import modelo.contenido.Pelicula;

class PerfilTest {
	
	@Test
	void testGestionRegistro() {
		Perfil probar = new Perfil("nick", "pass", "correo");
		List<Genero> generos = new ArrayList<>();
		generos.add(Genero.ACCION);
		Pelicula p = new Pelicula(
				"pelicula", 
				"Una pelicula", 
				9, 
				1, 
				"Una descripcion sosa", 
				"20/20/2020", 
				generos, 
				"saga", 
				"", 100
		);
		RegPelicula r = new RegPelicula(probar, p);
		assertTrue(!probar.getRegistros().contains(r));
		probar.addRegistro(r);
		assertTrue(probar.getRegistros().contains(r));
	}
	
	@Test
	void testAdmin() {
		Perfil p1 = new Perfil("admin", "1234", "admin@gmail.com");
		Perfil p2 = new Perfil("otro", "otro", "otro");
		
		//Existe un admin con el correo "admin" en la tabla de administradores de la BD
		assertTrue(p1.isAdmin());
		
		assertTrue(!p2.isAdmin());
	}
}
