package factorias;

import java.sql.ResultSet;

import org.json.JSONObject;

import utils.VBD;

public abstract class Builder<T> {
	
	String tipo;

	protected Builder(String type) {
		if (type == null)
			throw new IllegalArgumentException("Invalid type: " + type);
		else
			tipo = type;
	}

	/**
	 * Crea una instancia de la clase dada a partir de un JSONObject compatible
	 * @param info - JSONObject con la descripción del objeto a crear
	 * @return Instancia del objeto ya creado, null si el JSONObject no corresponde al tipo del builder
	 */
	public T createInstance(JSONObject info) {

		T b = null;

		if (tipo != null && tipo.equalsIgnoreCase(info.getString("tipo"))) {
			b = createTheInstance(info.has("data") ? info.getJSONObject("data") : new JSONObject());
		}

		return b;
	}
	
	/**
	 * Carga una instancia de la clase dada a partir del resultado de una consulta que contenga su información
	 * @param info - Resultado de la consulta con los datos del objeto
	 * @return Instancia del objeto ya cargado, null si la consulta no corresponde al tipo de objetos del builder
	 */
	public T loadInstance(ResultSet info) {

		T b = null;

		try {
			if (tipo != null && tipo.equalsIgnoreCase(info.getString(VBD.TIPO_CONT))) {
				b = loadTheInstance(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return b;
	}

	protected abstract T createTheInstance(JSONObject data);
	
	protected abstract T loadTheInstance(ResultSet data) throws Exception;
}
