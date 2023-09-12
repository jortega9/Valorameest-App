package utils;

/**
 * Vocabulario de la Base de Datos
 * @contiene
 * Únicamente Strings estáticos, que representan todo el vocabulario utilizado en la base de datos
 * 
 */
public class VBD {
	
	//SENTENCIAS
	
	/** Sentencia delete mediante nombre de la tabla, la columna de la clave principal y el valor a eliminar */
	public static String delete(String tabla, String idColumn, String idAEliminar) {
		return String.format("DELETE FROM %s WHERE %s = '%s'", tabla, idColumn, idAEliminar);
	}
	public static String delete(String tabla, String[] colsPK, String[] valsPK) {
		String condiciones = colsPK[0] + " = " + valsPK[0];
		for (int i = 1; i < colsPK.length; ++i)
			condiciones += " AND " + colsPK[i] + " = " + valsPK[i];
		return String.format("DELETE FROM %s WHERE %s", tabla, condiciones);
	}
	
	/** Sentencia delete que elimina los elementos de una tabla cuyas id empiecen por "test" */
	public static String deleteTests(String tabla, String idColumn) {
		return "DELETE FROM " + tabla + " WHERE " + idColumn + " LIKE 'test%'";
	}
	
	/** Sentencia insert mediante nombre de la tabla, los nombres de columnas en el orden introducido y sus valores */
	public static String insert(String tabla, String ordenAtr, String values) {
		return String.format("INSERT INTO %s(%s) VALUES (%s)", tabla, ordenAtr, values);
	}
	
	/** Sentencia update mediante nombre de la tabla, el conjunto de columnas a modificar y sus nuevos valores */
	public static String update(String tabla, String[] cols, String[] vals, String[] colsPK, String[] valsPK) {
		String valores = cols[0] + " = " + vals[0], condiciones = colsPK[0] + " = " + valsPK[0];
		for (int i = 1; i < cols.length; ++i)
			valores += ", " + cols[i] + " = " + vals[i];
		for (int i = 1; i < colsPK.length; ++i)
			condiciones += " AND " + colsPK[i] + " = " + valsPK[i];
		return String.format("UPDATE %s SET %s WHERE %s", tabla, valores, condiciones);
	}
	
	
	//TABLAS
	
	/** Nombre de la tabla de contenido */
	public static final String T_CONTENIDO = "contenido";
	
	/** Nombre de la tabla de videojuegos */
	public static final String T_VIDEOJUEGOS = "videojuegos";
	
	/** Nombre de la tabla de series */
	public static final String T_SERIES = "series";
	
	/** Nombre de la tabla de peliculas */
	public static final String T_PELICULAS = "peliculas";
	
	/** Nombre de la tabla de géneros */
	public static final String T_GENEROS = "genero";
	
	/** Nombre de la tabla de dlcs */
	public static final String T_DLC = "dlc";
	
	/** Nombre de la tabla de pares de series relacionadas por temporadas */
	public static final String T_TEMP_SERIES = "temporadas";
	
	/** Nombre de la tabla de perfiles */
	public static final String T_PERFILES = "perfil";
	
	/** Nombre de la tabla de perfiles */
	public static final String T_REGS = "registro";
	
	/** Nombre de la tabla de perfiles */
	public static final String T_REG_VIDEOJUEGOS = "regVid";
	
	/** Nombre de la tabla de perfiles */
	public static final String T_REG_SERIES = "regSer";
	
	/** Nombre de la tabla de perfiles */
	public static final String T_REG_PELICULAS = "regPel";
	
	/** Nombre de la tabla de administradores */
	public static final String T_ADMIN = "admins";
	
	/** Nombre de la tabla de estados de registros */
	public static final String T_ESTADO_REG = "estado";
	
	
	//CONTENIDO
	
	/** Nombre de las id en la tabla de contenido */
	public static final String ID_CONT = T_CONTENIDO + ".id";
	
	/** Nombre de los titulos en la tabla de contenido */
	public static final String TIT_CONT = T_CONTENIDO + ".titulo";

	/** Nombre de las valoraciones en la tabla de contenido */
	public static final String VAL_CONT = T_CONTENIDO + ".valoracion";

	/** Nombre de los seguidores en la tabla de contenido */
	public static final String SEG_CONT = T_CONTENIDO + ".seguidores";

	/** Nombre de las descripciones en la tabla de contenido */
	public static final String DESC_CONT = T_CONTENIDO + ".descripcion";

	/** Nombre de las fechas en la tabla de contenido */
	public static final String FECHA_CONT = T_CONTENIDO + ".fecha";
	
	/** Nombre de los tipos en la tabla de contenido */
	public static final String TIPO_CONT = T_CONTENIDO + ".tipo";

	/** Nombre de las imágenes en la tabla de contenido */
	public static final String IMG_CONT = T_CONTENIDO + ".foto";
	
	/** Enumeracion de campos de la tabla de contenido 
	 * @return ID_CONTENIDO, TIT_CONT, VAL_CONT, SEG_CONT, DESC_CONT, FECHA_CONT, TIPO_CONT, IMG_CONT */
	public static final String ATRIBUTOS_CONTENIDO = String.format("%s, %s, %s, %s, %s, %s, %s, %s", ID_CONT, TIT_CONT, VAL_CONT, SEG_CONT, DESC_CONT, FECHA_CONT, TIPO_CONT, IMG_CONT);

	
	//VIDEOJUEGOS
	
	/** Nombre de las id en la tabla de videojuegos */
	public static final String ID_V = T_VIDEOJUEGOS + ".vid";
	
	/** Nombre de las horas mínimas de juego en la tabla de videojuegos */
	public static final String HORASMIN_V = T_VIDEOJUEGOS + ".horasDeJuegoMin";
	
	/** Nombre de las horas máximas de juego en la tabla de videojuegos */
	public static final String HORASMAX_V = T_VIDEOJUEGOS + ".horasDeJuegoMax";
	
	/** Enumeracion de campos de la tabla de videojuegos 
	 * @return ID_V, HORASMIN_V, HORASMAX_V */
	public static final String ATRIBUTOS_V = String.format("%s, %s, %s", ID_V, HORASMIN_V, HORASMAX_V);
	
	
	//SERIES
	
	/** Nombre de las id en la tabla de series */
	public static final String ID_S = T_SERIES + ".Sid";
	
	/** Nombre del número de capítulos en la tabla de series */
	public static final String CAPITULOS_S = T_SERIES + ".capitulos";
	
	/** Nombre de las duraciones de capítulos en la tabla de series */
	public static final String DURCAP_S = T_SERIES + ".duracion";
	
	/** Enumeracion de campos de la tabla de series 
	 * @return ID_S, CAPITULOS_S, DURCAP_S */
	public static final String ATRIBUTOS_S = String.format("%s, %s, %s", ID_S, CAPITULOS_S, DURCAP_S);
	
	
	//PELICULAS
	
	/** Nombre de las id en la tabla de peliculas */
	public static final String ID_P = T_PELICULAS + ".Pid";
	
	/** Nombre de las sagas en la tabla de series */
	public static final String SAGA_P = T_PELICULAS + ".saga";
	
	/** Nombre de las duraciones en min en la tabla de series */
	public static final String DURMIN_P = T_PELICULAS + ".duracion";
	
	/** Enumeracion de campos de la tabla de películas 
	 * @return ID_P, SAGA_P, DURMIN_P */
	public static final String ATRIBUTOS_P = String.format("%s, %s, %s", ID_P, SAGA_P, DURMIN_P);
	
	
	//GENEROS
	
	/** Nombre de las id en la tabla de generos */
	public static final String ID_G = T_GENEROS + ".Cid";
	
	/** Nombre de los géneros en la tabla de generos */
	public static final String GENERO_G = T_GENEROS + ".genero";
	
	/** Enumeracion de campos de la tabla de géneros 
	 * @return ID_G, GENERO_G */
	public static final String ATRIBUTOS_GENEROS = String.format("%s, %s", ID_G, GENERO_G);
	
	
	//DLCs
	
	/** Nombre de las id del videojuego en la tabla de dlcs */
	public static final String ID_DLC = T_DLC + ".VDid";
	
	/** Nombre de las id de dlcs en la tabla de dlcs */
	public static final String DLC_DLC = T_DLC + ".nombreDlc";
	
	/** Enumeracion de campos de la tabla de géneros 
	 * @return ID_DLC, DLC_DLC */
	public static final String ATRIBUTOS_DLC = String.format("%s, %s", ID_DLC, DLC_DLC);
	
	
	//TEMPORADAS SERIES
	
	/** Nombre de la serie1 en la tabla de pares de series relacionadas */
	public static final String S1_TEMP = T_TEMP_SERIES + ".S1id";
	
	/** Nombre de la serie2 en la tabla de pares de series relacionadas */
	public static final String S2_TEMP = T_TEMP_SERIES + ".S2id";
	
	
	//PERFILES
	
	/** Nombre de los nicknames en la tabla de perfiles */
	public static final String NICK_PERF = T_PERFILES + ".nickname";
	
	/** Nombre de las contraseñas en la tabla de perfiles */
	public static final String PASSW_PERF = T_PERFILES + ".contraseña";
	
	/** Nombre de los correos en la tabla de perfiles */
	public static final String CORREO_PERF = T_PERFILES + ".correo";
	
	/** Enumeracion de campos de la tabla de pefiles 
	 * @return NICK_PERF, PASSW_PERF, CORREO_PERF */
	public static final String ATRIBUTOS_PERFIL = String.format("%s, %s, %s", NICK_PERF, PASSW_PERF, CORREO_PERF);
	
	
	//ADMINISTRADORES
	
	/** Nombre de los correos en la tabla de perfiles */
	public static final String CORREO_ADMIN = T_ADMIN + ".correo";
	
	
	//REGISTROS
	
	/** Nombre de las ids de contenido en la tabla de registros */
	public static final String ID_REG = T_REGS + ".id";
	
	/** Nombre de los correos de perfiles en la tabla de registros */
	public static final String PERF_REG = T_REGS + ".correo";
	
	/** Nombre de los correos de perfiles en la tabla de registros */
	public static final String VALOR_REG = T_REGS + ".valoracion";
	
	/** Enumeracion de campos de la tabla de registros 
	 * @return ID_REG, PERF_REG, VALOR_REG */
	public static final String ATRIBUTOS_REGISTRO = String.format("%s, %s, %s", ID_REG, PERF_REG, VALOR_REG);
	
	
	//REGISTROS VIDEOJUEGOS
	
	/** Nombre de las ids de videojuegos en la tabla de registros de videojuegos */
	public static final String ID_RV = T_REG_VIDEOJUEGOS + ".Vid";
	
	/** Nombre de los correos de perfiles en la tabla de registros de videojuegos */
	public static final String PERF_RV = T_REG_VIDEOJUEGOS + ".correo";
	
	/** Nombre de las horas jugadas en la tabla de registros de videojuegos */
	public static final String HORAS_RV = T_REG_VIDEOJUEGOS + ".horasJugadas";
	
	/** Enumeracion de campos de la tabla de registros de videojuegos
	 * @return ID_RV, PERF_RV, HORAS_RV */
	public static final String ATRIBUTOS_REG_VID = String.format("%s, %s, %s", ID_RV, PERF_RV, HORAS_RV);
	
	
	//REGISTROS PELICULAS
	
	/** Nombre de las ids de películas en la tabla de registros de películas */
	public static final String ID_RP = T_REG_PELICULAS + ".Pid";
	
	/** Nombre de los correos de perfiles en la tabla de registros de películas */
	public static final String PERF_RP = T_REG_PELICULAS + ".correo";
	
	/** Nombre de el minuto actual en la tabla de registros de películas */
	public static final String MINS_RP = T_REG_PELICULAS + ".minActual";
	
	/** Enumeracion de campos de la tabla de registros de películas
	 * @return ID_RP, PERF_RP, MINS_RP */
	public static final String ATRIBUTOS_REG_PEL = String.format("%s, %s, %s", ID_RP, PERF_RP, MINS_RP);
	
	
	//REGISTROS SERIES
	
	/** Nombre de las ids de series en la tabla de registros de series */
	public static final String ID_RS = T_REG_SERIES + ".Sid";
	
	/** Nombre de los correos de perfiles en la tabla de registros de series */
	public static final String PERF_RS = T_REG_SERIES + ".correo";
	
	/** Nombre del número de capítulos vistos en la tabla de registros de series */
	public static final String CAPS_RS = T_REG_SERIES + ".capsVistos";
	
	/** Enumeracion de campos de la tabla de registros de series
	 * @return ID_RS, PERF_RS, CAPS_RS */
	public static final String ATRIBUTOS_REG_SER = String.format("%s, %s, %s", ID_RS, PERF_RS, CAPS_RS);
	
	
	//ESTADO REGISTROS
	
	/** Nombre de los estados en la tabla de estados de registros */
	public static final String ESTADO_EST_REG = T_ESTADO_REG + ".estado";
	
	/** Nombre de las ids de contenido en la tabla de estados de registros */
	public static final String ID_EST_REG = T_ESTADO_REG + ".id";
	
	/** Nombre de los correos de perfiles en la tabla de estados de registros */
	public static final String PERF_EST_REG = T_ESTADO_REG + ".correo";

	/** Enumeracion de campos de la tabla de estados de registros 
	 * @return ID_EST_REG, PERF_EST_REG, ESTADO_EST_REG */
	public static final String ATRIBUTOS_ESTADO_REG = String.format("%s, %s, %s", ID_EST_REG, PERF_EST_REG, ESTADO_EST_REG);
}
