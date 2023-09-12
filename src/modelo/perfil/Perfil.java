package modelo.perfil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import factorias.perfil.FactoriaPerfil;
import factorias.perfil.FactoriaRegistros;
import factorias.statements.ManagerRegistros;
import modelo.BD;
import modelo.contenido.Contenido;
import utils.Consulta;
import utils.VBD;

public class Perfil {

	private String nickname;
	private String password;
	private String correo;
	Map<String, Registro> registros;
	
	public Perfil(String nick, String pass, String correo) {
		nickname = nick;
		password = pass;
		this.correo = correo;
		registros = new HashMap<>();
	}
	
	/**
	 * Busca y devuelve un perfil a partir de su correo único. Si no lo encuentra devuelve null
	 * @param correo - Correo del perfil a buscar
	 * @return Perfil buscado o null si no lo encuentra
	 */
	public static Perfil cargar(String correo) {
		Consulta cons = new Consulta(Consulta.FROM_PERFILES);
		cons.putWhere(String.format("%s = '%s'", VBD.CORREO_PERF, correo));
		try {
		ResultSet set = BD.open().select(cons.buildConsulta());
		if (set.next())
			return FactoriaPerfil.open().loadInstance(set);
		else
			return null;
		} catch (Exception e) {
			throw new RuntimeException("Error al cargar perfil, inténtelo de nuevo");
		}
	}
	
	/**
	 * Añade un nuevo registro de contenido al perfil
	 * @param r - Nuevo registro
	 */
	public void addRegistro(Registro r) {
		registros.put(r.getContenido().getID(), r);
	}

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public String getCorreo() {
		return correo;
	}
	
	public List<Registro> getRegistros() {
		return Collections.unmodifiableList(new ArrayList<>(registros.values()));
	}
	
	public Registro getReg(Contenido c) {
		return registros.get(c.getID());
	}
	
	/**
	 * Comprueba si el perfil es uno de los administradores
	 * @return True si es administrador
	 */
	public boolean isAdmin() {
		Consulta cons = new Consulta(Consulta.FROM_ADMIN);
		cons.putWhere(String.format("%s = '%s'", VBD.CORREO_ADMIN, getCorreo()));
		try {
			return BD.open().select(cons.buildConsulta()).next();
		} catch (Exception e) {
			throw new RuntimeException("Error al consultar la tabla de admins");
		}
	}
	
	/**
	 * Devuelve un registro del perfil asociado al contenido dado, y en caso de no encontrarlo lo crea
	 * @param c - Contenido del registro buscado
	 * @return Registro del contenido c en el perfil
	 */
	public Registro usarReg(Contenido c) {
		Registro r = getReg(c);
		if (r == null) {
			r = FactoriaRegistros.open(this).createInstance(c.detalles());
			addRegistro(r);
			ManagerRegistros.open().insert(r);
		}
		return r;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.getClass().equals(o.getClass()) && this.correo.equals(((Perfil) o).correo);
	}
	
	@Override
	public String toString() {
		return nickname + "\nCorreo: " + correo + "\n" + registros.size() + " registros de actividad";
	}
}
