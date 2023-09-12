package factorias.perfil;

import java.sql.ResultSet;

import org.json.JSONObject;

import factorias.Builder;
import factorias.contenido.FactoriaContenido;
import modelo.BD;
import modelo.contenido.Serie;
import modelo.contenido.TipoContenido;
import modelo.perfil.EstadoReg;
import modelo.perfil.Perfil;
import modelo.perfil.RegSerie;
import utils.Consulta;
import utils.VBD;

public class BuilderRegSerie extends Builder<RegSerie> {
	
	private static final String TIPO = TipoContenido.SERIE.toString();
	
	private Perfil perfil;

	protected BuilderRegSerie(Perfil p) {
		super(TIPO);
		perfil = p;
	}

	@Override
	protected RegSerie createTheInstance(JSONObject data) {
		JSONObject jo = new JSONObject();
		jo.put("tipo", TIPO);
		jo.put("data", data);
		return new RegSerie(perfil, (Serie) FactoriaContenido.open().createInstance(jo));
	}

	@Override
	protected RegSerie loadTheInstance(ResultSet data) throws Exception {
		RegSerie reg = new RegSerie(perfil, (Serie) FactoriaContenido.open().loadInstance(data), data.getInt(VBD.CAPS_RS));
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
