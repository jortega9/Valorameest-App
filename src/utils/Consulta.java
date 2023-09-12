package utils;

import java.util.Map;
import java.util.TreeMap;

public class Consulta {
	
	private static final String SELECT = "SELECT";
	private static final String FROM = "FROM";
	private static final String WHERE = "WHERE";
	private static final String ORDER = "ORDER BY";
	
	public static final String FROM_ALL_CONTENIDO = VBD.T_CONTENIDO
			+ String.format(" LEFT JOIN %s ON %s = %s", VBD.T_PELICULAS, VBD.ID_CONT, VBD.ID_P)
			+ String.format(" LEFT JOIN %s ON %s = %s", VBD.T_SERIES, VBD.ID_CONT, VBD.ID_S)
			+ String.format(" LEFT JOIN %s ON %s = %s", VBD.T_VIDEOJUEGOS, VBD.ID_CONT, VBD.ID_V);
	public static final String FROM_GENEROS = VBD.T_GENEROS;
	public static final String FROM_DLCS = VBD.T_DLC;
	public static final String FROM_TEMP_SERIES = VBD.T_TEMP_SERIES;
	public static final String FROM_PERFILES = VBD.T_PERFILES;
	public static final String FROM_ADMIN = VBD.T_ADMIN;
	public static final String FROM_REGS = VBD.T_REGS
			+ String.format(" LEFT JOIN %s ON %s = %s", VBD.T_REG_VIDEOJUEGOS, VBD.ID_REG, VBD.ID_RV)
			+ String.format(" LEFT JOIN %s ON %s = %s", VBD.T_REG_PELICULAS, VBD.ID_REG, VBD.ID_RP)
			+ String.format(" LEFT JOIN %s ON %s = %s", VBD.T_REG_SERIES, VBD.ID_REG, VBD.ID_RS)
			+ String.format(" LEFT JOIN %s ON %s = %s", VBD.T_CONTENIDO, VBD.ID_REG, VBD.ID_CONT)
			+ String.format(" LEFT JOIN %s ON %s = %s", VBD.T_PELICULAS, VBD.ID_CONT, VBD.ID_P)
			+ String.format(" LEFT JOIN %s ON %s = %s", VBD.T_SERIES, VBD.ID_CONT, VBD.ID_S)
			+ String.format(" LEFT JOIN %s ON %s = %s", VBD.T_VIDEOJUEGOS, VBD.ID_CONT, VBD.ID_V);
	public static final String FROM_ESTADO_REG = VBD.T_ESTADO_REG;
	
	public static final String SELECT_ALL = "*";
	public static String count(String column) { return "COUNT(" + column + ")"; }
	public static String sum(String column) { return "SUM(" + column + ")"; }
	public static String average(String column) { return "AVG(" + column + ")"; }

	private Map<String, String> consulta;
	
	/**
	 * Crea una colsulta con los campos de SELECT y FROM especificados, que se pueden obtener mediante las constantes de esta misma clase.
	 * @param select - Campos en SELECT
	 * @param from - Tablas en FROM
	 */
	public Consulta(String select, String from) {
		consulta = new TreeMap<>();
		consulta.put(FROM, from);
		consulta.put(SELECT, select);
	}
	
	/**
	 * Crea una consulta con las tablas de FROM especificadas, que se pueden obtener mediante las constantes de esta misma clase. Por defecto se selecciona el resultado completo de la consulta.
	 * @param from - Tablas en FROM
	 */
	public Consulta(String from) {
		this(SELECT_ALL, from);
	}
	
	/**
	 * Construye una consulta completa a partir de la información ya introducida con otros métodos/constructores
	 * @return String con la consulta final
	 */
	public String buildConsulta() {
		String str = String.format("%s %s", SELECT, (consulta.containsKey(SELECT) ? consulta.get(SELECT) : SELECT_ALL))
					+String.format(" %s %s", FROM, consulta.get(FROM));
		
		if (consulta.containsKey(WHERE))
			str += String.format(" %s %s", WHERE, consulta.get(WHERE));
		if (consulta.containsKey(ORDER))
			str += String.format(" %s %s", ORDER, consulta.get(ORDER));
		
		return str;
	}
	
	/**
	 * Añade una condición en forma de String al WHERE de la consulta
	 * @param condicion - String con la condicion
	 */
	public void putWhere(String condicion) {
		if (consulta.containsKey(WHERE))
			consulta.put(WHERE, consulta.get(WHERE).concat(" AND " + condicion));
		else
			consulta.put(WHERE, condicion);
	}
	
	/**
	 * Establece un criterio en forma de String al ORDER BY de la consulta
	 * @param order - Criterio para ordenar
	 */
	public void putOrder(String order) {
		consulta.put(ORDER, order);
	}
}
