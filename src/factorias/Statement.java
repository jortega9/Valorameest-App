package factorias;

public interface Statement<T> {

	/**
	 * Intenta insertar un elemento a la base de datos. Si el statement no corresponde al objeto introducido, devuelve false. En otro caso devuelve true.
	 * @param elem - Elemento a introducir
	 * @return true si el statement corresponde al objeto introducido
	 * @throws Exception - En caso de error al conectarse a la base de datos
	 */
	public boolean insert(T elem) throws Exception;
	
	/**
	 * Intenta actualizar un elemento en la base de datos. Si el statement no corresponde al objeto introducido, devuelve false. En otro caso devuelve true.
	 * @param elem - Elemento a actualizar
	 * @return true si el statement corresponde al objeto introducido
	 * @throws Exception - En caso de error al conectarse a la base de datos
	 */
	public boolean update(T elem) throws Exception;
}
