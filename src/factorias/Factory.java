package factorias;

import java.sql.ResultSet;

import org.json.JSONObject;

public interface Factory<T> {
	
	/**
	 * Crea una instancia de la clase dada a partir de un JSONObject con su información
	 * @param info - JSONObject con la descripción del objeto a crear
	 * @return Instancia del objeto ya creado
	 */
	public T createInstance(JSONObject info);
	
	/**
	 * Carga una instancia de la clase dada a partir del resultado de una consulta que contenga su información
	 * @param info - Resultado de la consulta con los datos del objeto
	 * @return Instancia del objeto ya cargado
	 */
	public T loadInstance(ResultSet info);
}
