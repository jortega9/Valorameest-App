package factorias;

public interface DBManager<T> {

	/**
	 * Inserta en la base de datos un objeto dado
	 * @param elem - Objeto a insertar
	 */
	public void insert(T elem);
	
	/**
	 * Elimina de la base de datos el objeto equivalente al dado, en caso de haberlo
	 * @param elem - Objeto a eliminar
	 */
	public void delete(T elem);
	
	/**
	 * Actualiza los datos de un objeto en la base de datos
	 * @param elem - Objeto a actualizar
	 */
	public void update(T elem);
	
	/**
	 * Actualiza en la base de datos toda la información obtenida a partir de cálculos complejos, que no conviene actualizar con updates en tiempo real.
	 */
	public void refresh();
}
