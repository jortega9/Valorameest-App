package modelo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import factorias.statements.ManagerContenido;
import factorias.statements.ManagerPerfiles;
import modelo.contenido.Contenido;
import modelo.filtros.FilterStrategy;
import modelo.perfil.ModifPerfilStrategy;
import modelo.perfil.Perfil;

public class Aplicacion implements Observable {

	private Perfil perfil;
	private Buscador buscador;
	private List<Contenido> contActual;
	
	private List<Observador> obs;
	
	public Aplicacion() {
		perfil = null;
		buscador = new Buscador();
		obs = new ArrayList<>();
		contActual = new ArrayList<>();
	}
	
	/**
	 * Añade un nuevo elemento al contenido disponible en la base de datos de la aplicación
	 * @param c - Objeto de tipo Contenido a introducir
	 */
	public void addContenido(Contenido c) {
		try {
			ManagerContenido.open().insert(c);
		}
		catch (RuntimeException e) {
			e.printStackTrace();
			onError(e.getMessage());
		}
	}
	
	/**
	 * Elimina un elemento del contenido disponible en la base de datos de la aplicación
	 * @param c - Objeto de tipo Contenido a eliminar
	 */
	public void removeContenido(Contenido c) {
		try {
			ManagerContenido.open().delete(c);
		}
		catch (RuntimeException e) {
			onError(e.getMessage());
		}
	}
	
	/**
	 * Actualiza la información de un contenido tras haber sido modificado
	 * @param c - Contenido modificado
	 */
    public void updateContenido(Contenido c) {
        try {
            ManagerContenido.open().update(c);
        }
        catch (IllegalArgumentException e) {
            for (Observador o : obs)
                o.onError(e.getMessage());
        }
    }
	
	/**
	 * Añade un nuevo perfil a la base de datos de la aplicación
	 * @param p - Perfil a añadir
	 */
	public void addPerfil(Perfil p) {
		try {
			ManagerPerfiles.open().insert(p);
		}
		catch (IllegalArgumentException iae) {
			throw iae;
		}
		catch (RuntimeException e) {
			onError(e.getMessage());
		}
	}
	
	/**
	 * Establece un perfil dado como perfil actualmente activo en la aplicación
	 * @param p - Perfil a utilizar
	 */
	public void iniciarSesion(Perfil p) {
		perfil = p;
		for (Observador o : obs)
			o.onCambioPerfil(perfil);
	}
	
	/**
	 * Anula el perfil actualmente activo en la aplicación
	 */
	public void cerrarSesion() {
		try {
			if (perfil != null) {
				ManagerPerfiles.open().update(perfil);
				perfil = null;
				for (Observador o : obs)
					o.onCambioPerfil(perfil);
			}
		}
		catch (RuntimeException re) {
			onError(re.getMessage());
		}
	}
	
	/**
	 * Modifica la información interna del perfil activo según una estrategia de modificación de perfil
	 * @param mps - Estrategia de modificación de perfil
	 */
	public void modificarPerfil(ModifPerfilStrategy mps) {
		try {
			mps.modificarPerfil(perfil);
			for (Observador o : obs)
				o.onCambioPerfil(perfil);
		}
		catch (NullPointerException npe) {
			onError("No se ha iniciado sesión");
		}
		catch (RuntimeException re) {
			onError(re.getMessage());
		}
	}
	
	/**
	 * Busca y devuelve un perfil a partir de su correo único. Si no lo encuentra devuelve null
	 * @param correo - Correo del perfil a buscar
	 * @return Perfil buscado o null si no lo encuentra
	 */
	public Perfil getPerfilWithCorreo(String correo) {
		try {
			return Perfil.cargar(correo);
		}
		catch (RuntimeException re) {
			onError(re.getMessage());
			return null;
		}
	}
	
	/**
	 * Busca y devuelve un objeto de tipo Contenido a partir de su ID única. Si no lo encuentra devuelve null
	 * @param id - ID del contenido a buscar
	 * @return Contenido buscado o null si no lo encuentra
	 */
	public Contenido getWithID(String id) {
		try {
			Contenido c = buscador.getWithID(id);
			for (Observador o : obs)
				o.onBuscarPorId(c);
			return c;
		}
		catch (RuntimeException re) {
			onError(re.getMessage());
			return null;
		}
	}
	
	private List<Contenido> getContenido() {
		return Collections.unmodifiableList(contActual);
	}
	
	/**
	 * Aplica los filtros añadidos al buscador, reiniciando los elementos de la búsqueda
	 */
	public void realizarBusqueda() {
		try {
			contActual.clear();
			buscador.aplicarFiltros();
			for (Observador o : obs)
				o.onNuevaBusqueda();
		}
		catch (RuntimeException re) {
			onError(re.getMessage());
		}
	}
	
	/**
	 * Extrae n elementos de la búsqueda actual (en caso de haberlos)
	 * @param n - Número de elementos a extraer
	 */
	public void cargarResultados(int n) {
		try {
			List<Contenido> nuevo = buscador.extraerElementos(n);
			contActual.addAll(nuevo);
			for (Observador o : obs)
				o.onExtraerDeBusqueda(nuevo);
		}
		catch (RuntimeException re) {
			onError("Error al cargar resultados:\n" + re.getMessage());
		}
	}
	
	/**
	 * Actualiza en la base de datos la información obtenida a partir de cálculos complejos, que no conviene actualizar con updates en tiempo real.
	 */
	public void actualizarEstadisticas() {
		try {
			ManagerContenido.open().refresh();
		}
		catch (RuntimeException re) {
			onError(re.getMessage());
		}
	}
	
	/**
	 * Añade un filtro al catálogo, con el que especificar criterios de búsqueda
	 * @param f - filtro a añadir
	 */
	public void addFiltro(FilterStrategy f) {
		buscador.addFiltro(f);
	}
	
	/**
	 * Elimina del catálogo un filtro equivalente al introducido como parámetro
	 * @param f
	 */
	public void removeFiltro(FilterStrategy f) {
		buscador.removeFiltro(f);
	}
	
	/**
	 * Elimina todos los filtros del catálogo
	 */
	public void clearFiltros() {
		buscador.clearFiltros();
	}

	@Override
	public void addObservador(Observador o) {
		obs.add(o);
		o.onRegister(getContenido(), perfil);
	}

	@Override
	public void removeObservador(Observador o) {
		obs.remove(o);
	}
	
	private void onError(String err) {
		for (Observador o : obs)
			o.onError(err);
	}
}
