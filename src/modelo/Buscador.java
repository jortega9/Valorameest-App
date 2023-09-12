package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import factorias.contenido.FactoriaContenido;
import modelo.contenido.Contenido;
import modelo.filtros.FilterStrategy;
import utils.Consulta;
import utils.VBD;

public class Buscador {

	private List<FilterStrategy> filtros;
	
	private ResultSet busqueda;
	

	Buscador() {
		filtros = new ArrayList<>();
	}
	
	/**
	 * Realiza internamente una búsqueda a partir de los filtros añadidos previamente
	 */
	void aplicarFiltros() {
		Consulta consulta = new Consulta(Consulta.FROM_ALL_CONTENIDO);
		for (FilterStrategy f : filtros)
			f.filtrar(consulta);
		try {
			busqueda = BD.open().select(consulta.buildConsulta());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error al aplicar los filtros");
		}
	}
	
	/**
	 * Construye una lista de contenido con los n siguientes elementos de la búsqueda (en caso de haberlos)
	 * @return Lista de Contenido
	 */
	List<Contenido> extraerElementos(int n) {
		List<Contenido> resultado = new ArrayList<>();
		int i = 0;
		try {
			while (i < n && busqueda.next()) {
				resultado.add(FactoriaContenido.open().loadInstance(busqueda));
				++i;
			}
			return Collections.unmodifiableList(resultado);
		} catch (SQLException e) {
			throw new RuntimeException("Error al cargar datos del buscador:\n" + e.getMessage());
		}
	}
	
	/**
	 * Busca y devuelve un objeto de tipo Contenido a partir de su ID única. Si no lo encuentra devuelve null
	 * @param id - ID del contenido a buscar
	 * @return Contenido buscado o null si no lo encuentra
	 */
	Contenido getWithID(String id) {
		Consulta cons = new Consulta(Consulta.FROM_ALL_CONTENIDO);
		cons.putWhere(String.format("%s = '%s'", VBD.ID_CONT, id));
		
		try {
			ResultSet rs = BD.open().select(cons.buildConsulta());
			if (!rs.next())
				return null;
			return FactoriaContenido.open().loadInstance(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error al cargar el contenido");
		}
	}
	
	/**
	 * Añade un filtro al buscador, con el que especificar criterios de búsqueda
	 * @param f - filtro a añadir
	 */
	void addFiltro(FilterStrategy f) {
		removeFiltro(f);
		filtros.add(f);
	}
	
	/**
	 * Elimina del buscador un filtro equivalente al introducido como parámetro
	 * @param f - Filtro a eliminar
	 */
	void removeFiltro(FilterStrategy f) {
		filtros.remove(f);
	}
	
	/**
	 * Elimina todos los filtros del buscador
	 */
	void clearFiltros() {
		filtros = new ArrayList<>();
	}
}
