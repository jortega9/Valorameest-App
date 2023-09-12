package modelo.contenido;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Contenido {
	
	public static final float DEFAULT_VALORACION = 0;
	public static final int DEFAULT_SEGUIDORES = 0;

	private String id;
	private String titulo;
	private float valoracion;
	private int seguidores;
	private String descripcion;
	private List<Genero> generos;
	private String fecha;
	private TipoContenido tipo;
	private String imagenURL;
	
	public Contenido(String id, String tit, float val, int seg, String desc, String fech, List<Genero> gens, TipoContenido tipo, String imagenURL) {
		this.id = id;
		titulo = tit;
		valoracion = val;
		seguidores = seg;
		descripcion = desc;
		fecha = fech;
		generos = gens;
		this.tipo = tipo;
		this.imagenURL = imagenURL;
	}
	
	/**
	 * Asigna un valor a la valoración del contenido
	 * @param val - Nueva valoración
	 */
	public void setValoracion(float val) {
		valoracion = val;
	}
	
	/**
	 * Devuelve la duración del contenido formateada según su tipo, pudiendo representarse en minutos, horas u otras unidades
	 * @return String con la duración del contenido formateada según su tipo
	 */
	public abstract String getDuracionFormato();

	public String getID() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public float getValoracion() {
		return valoracion;
	}

	public int getSeguidores() {
		return seguidores;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getFecha() {
		return fecha;
	}
	
	public List<Genero> getGeneros() {
		return Collections.unmodifiableList(generos);
	}
	
	/**
	 * Devuelve true si el parámetro g es uno de los géneros de este contenido
	 * @param g - el género que se busca en el contenido
	 * @return true si el parámetro g es uno de los géneros de este contenido
	 */
	public boolean hasGenero(Genero g) {
		return generos.contains(g);
	}
	
	/**
	 * Devuelve true si el contenido pertenece al tipo t. Aporta más seguridad que utilizar getClass o métodos similares, al trabajar con true y false en lugar de extraer directamente información de su clase.
	 * @param t - tipo de contenido buscado
	 * @return true si el contenido pertenece al tipo t
	 */
	public boolean isTipo(TipoContenido t) {
		return tipo.equals(t);
	}
	
	public TipoContenido getTipo() {
		return tipo;
	}
	
	public String getImagenURL() {
		return imagenURL;
	}
	
	@Override
	public String toString() {
		String str =  "Título: " + titulo
			+ "\nTipo: " + tipo.toString()
			+ "\nDuración : " + getDuracionFormato()
			+ "\nValoración: " + valoracion
			+ "\nSeguidores: " + seguidores
			+ "\nFecha de lanzamiento: " + fecha
			+ "\nGéneros:";
		for (Genero g : generos)
			str += (" " + g.toString());
		str += "\nDescripción: " + descripcion;
		
		return str;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass()) && this.id.equals(((Contenido) obj).id);
	}
	
	/**
	 * Devuelve un JSONObject con toda la información del contenido, equivalente al que se usaría para crearlo en las factorías
	 * @return JSONObject con la descripción completa del contenido
	 */
	public JSONObject detalles() {
		JSONObject jo = new JSONObject(), jo2 = new JSONObject();
		
		jo2.put("id", id);
		jo2.put("titulo", titulo);
		jo2.put("valoracion", valoracion);
		jo2.put("seguidores", seguidores);
		jo2.put("descripcion", descripcion);
		jo2.put("fecha", fecha);
		jo2.put("imagen", imagenURL);
		jo2.put("generos", new JSONArray(generos));
		
		jo.put("tipo", tipo.toString());
		jo.put("data", jo2);
		
		return jo;
	}
}
