package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class BD {
	
    private Connection connection;
    
    private static BD instance = null;
    
    private BD() throws Exception {
        try {
    		System.out.println("Connecting to the database...");
            connection = DriverManager.getConnection("jdbc:mysql://ufotovgtietqlfqa:IkE5jtzhrl2UN4l9XblN@b43utfqblvwlvs4mcz4h-mysql.services.clever-cloud.com:3306/b43utfqblvwlvs4mcz4h?autoReconnect=true", "ufotovgtietqlfqa", "IkE5jtzhrl2UN4l9XblN");//Establishing connection
            System.out.println("Connected With the database successfully");
        }
        catch (SQLException e) {
            System.out.println("Error while connecting to the database");
        }
    }

    /**
     * Devuelve una instancia de BD con una conexión a la base de datos establecida
     * @return BD ya conectada
     * @throws Exception Si no se ha podido conectar a la base de datos
     */
    public static BD open() throws Exception {
    	if (instance == null)
    		instance = new BD();
    	else if (!instance.connection.isValid(5)) {
    		System.out.println("Reconnecting to the database...");
    		instance.connection = DriverManager.getConnection("jdbc:mysql://ufotovgtietqlfqa:IkE5jtzhrl2UN4l9XblN@b43utfqblvwlvs4mcz4h-mysql.services.clever-cloud.com:3306/b43utfqblvwlvs4mcz4h?autoReconnect=true", "ufotovgtietqlfqa", "IkE5jtzhrl2UN4l9XblN");//Establishing connection
    		System.out.println("Reconnected successfully");
    	}
    	return instance;
    }

    /**
     * Realiza una inserción a partir de una sentencia INSERT correcta en forma de String
     * @param insercion - String con la sentencia INSERT
     */
    public void insert(String insercion) {
        try {
            connection.prepareStatement(insercion).executeUpdate();
        }
        catch (SQLIntegrityConstraintViolationException dive) {
        	throw new IllegalArgumentException("Violación de clave primaria");
        }
        catch (SQLException e) {
        	throw new IllegalArgumentException("Datos no válidos:\n" + e.getMessage());
        }
    }

    /**
     * Realiza una selección a partir de una consulta correcta en forma de String
     * @param seleccion - String con la consulta
     */
    public ResultSet select(String seleccion) {
        try {
            return connection.prepareStatement(seleccion).executeQuery();
        }
        catch (SQLException e) {
            System.out.println("Error al seleccionar elementos");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Elimina elementos a partir de una sentencia DELETE correcta en forma de String
     * @param delete - String con la sentencia DELETE
     */
    public void delete(String delete) {
        try {
            connection.prepareStatement(delete).executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar elementos");
            e.printStackTrace();
        }
    }
    
    /**
     * Actualiza elementos de la base de datos a partir de una sentencia UPDATE correcta en forma de String
     * @param update
     */
    public void update(String update) {
        try {
            connection.prepareStatement(update).executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar elementos");
            e.printStackTrace();
        }
    }
    
    public void reset(String tabla) {
    	delete("DELETE FROM " + tabla);
    }
}



