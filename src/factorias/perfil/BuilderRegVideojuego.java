package factorias.perfil;

import java.sql.ResultSet;

import org.json.JSONObject;

import factorias.Builder;
import factorias.contenido.FactoriaContenido;
import modelo.BD;
import modelo.contenido.TipoContenido;
import modelo.contenido.Videojuego;
import modelo.perfil.EstadoReg;
import modelo.perfil.Perfil;
import modelo.perfil.RegVideojuego;
import utils.Consulta;
import utils.VBD;

public class BuilderRegVideojuego extends Builder<RegVideojuego> {
	
	private static final String TIPO = TipoContenido.VIDEOJUEGO.toString();
	
	private Perfil perfil;

	protected BuilderRegVideojuego(Perfil p) {
		super(TIPO);
		perfil = p;
	}

	@Override
	protected RegVideojuego createTheInstance(JSONObject data) {
		JSONObject jo = new JSONObject();
		jo.put("tipo", TIPO);
		jo.put("data", data);
		return new RegVideojuego(perfil, (Videojuego) FactoriaContenido.open().createInstance(jo));
	}

	@Override
	protected RegVideojuego loadTheInstance(ResultSet data) throws Exception {
		RegVideojuego reg = new RegVideojuego(perfil, (Videojuego) FactoriaContenido.open().loadInstance(data), data.getInt(VBD.HORAS_RV));
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
