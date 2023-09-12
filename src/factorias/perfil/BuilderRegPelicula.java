package factorias.perfil;

import java.sql.ResultSet;

import org.json.JSONObject;

import factorias.Builder;
import factorias.contenido.FactoriaContenido;
import modelo.BD;
import modelo.contenido.Pelicula;
import modelo.contenido.TipoContenido;
import modelo.perfil.EstadoReg;
import modelo.perfil.Perfil;
import modelo.perfil.RegPelicula;
import utils.Consulta;
import utils.VBD;

public class BuilderRegPelicula extends Builder<RegPelicula> {
	
	private static final String TIPO = TipoContenido.PELICULA.toString();
	
	private Perfil perfil;

	protected BuilderRegPelicula(Perfil p) {
		super(TIPO);
		perfil = p;
	}

	@Override
	protected RegPelicula createTheInstance(JSONObject data) {
		JSONObject jo = new JSONObject();
		jo.put("tipo", TIPO);
		jo.put("data", data);
		return new RegPelicula(perfil, (Pelicula) FactoriaContenido.open().createInstance(jo));
	}

	@Override
	protected RegPelicula loadTheInstance(ResultSet data) throws Exception {
		RegPelicula reg = new RegPelicula(perfil, (Pelicula) FactoriaContenido.open().loadInstance(data), data.getInt(VBD.MINS_RP));
		reg.setValoracion(data.getFloat(VBD.VALOR_REG));
		
		Consulta cons = new Consulta(Consulta.FROM_ESTADO_REG);
		cons.putWhere(String.format("%s = '%s'", VBD.ID_EST_REG, reg.getContenido().getID()));
		cons.putWhere(String.format("%s = '%s'", VBD.PERF_EST_REG, reg.getPerfil().getCorreo()));
		ResultSet rs = BD.open().select(cons.buildConsulta());
		while (rs.next())
			reg.switchEstado(EstadoReg.valueOf(rs.getString(VBD.ESTADO_EST_REG)));
		return reg;
	}
}
