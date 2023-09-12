package factorias.perfil;

import java.sql.ResultSet;

import org.json.JSONObject;

import factorias.Factory;
import modelo.BD;
import modelo.perfil.Perfil;
import utils.Consulta;
import utils.VBD;

public class FactoriaPerfil implements Factory<Perfil> {
	
	private static FactoriaPerfil instance = null;
	
	private FactoriaPerfil() {
	}
	
	/**
	 * Devuelve una instancia a la factoría de perfiles
	 * @return Instancia de FactoriaPerfil
	 */
	public static FactoriaPerfil open() {
		if (instance == null)
			instance = new FactoriaPerfil();
		return instance;
	}

	@Override
	public Perfil createInstance(JSONObject info) {
		try {
			return new Perfil(info.getString("nickname"), info.getString("password"), info.getString("correo"));
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Invalid value for createInstance");
		}
	}

	@Override
	public Perfil loadInstance(ResultSet info) {
		try {
			Perfil p = new Perfil(info.getString(VBD.NICK_PERF), info.getString(VBD.PASSW_PERF), info.getString(VBD.CORREO_PERF));
			
			Consulta cons = new Consulta(Consulta.FROM_REGS);
			cons.putWhere(String.format("%s = '%s'", VBD.PERF_REG, p.getCorreo()));
			ResultSet rs = BD.open().select(cons.buildConsulta());
			while (rs.next())
				p.addRegistro(FactoriaRegistros.open(p).loadInstance(rs));

			return p;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Invalid value for loadInstance");
		}
	}
}
