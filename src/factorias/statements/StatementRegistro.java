package factorias.statements;

import java.text.NumberFormat;
import java.util.Locale;

import factorias.Statement;
import modelo.BD;
import modelo.contenido.TipoContenido;
import modelo.perfil.EstadoReg;
import modelo.perfil.Registro;
import utils.VBD;

public abstract class StatementRegistro<T extends Registro> implements Statement<Registro> {

	TipoContenido tipo;
	
	protected StatementRegistro(TipoContenido tipo) {
		if (tipo == null)
			throw new IllegalArgumentException("Tipo de StatementContenido no válido");
		this.tipo = tipo;
	}
	
	@SuppressWarnings("unchecked")
	public boolean insert(Registro reg) throws Exception {
		if (reg.getContenido().isTipo(tipo)) {
			BD.open().insert(VBD.insert(VBD.T_REGS, VBD.ATRIBUTOS_REGISTRO, 
					String.format("'%s', '%s', %s", reg.getContenido().getID(), reg.getPerfil().getCorreo(), NumberFormat.getNumberInstance(Locale.ENGLISH).format(reg.getValoracion()))));
			for (EstadoReg e : reg.getEstados())
				BD.open().insert(VBD.insert(VBD.T_ESTADO_REG, VBD.ATRIBUTOS_ESTADO_REG, 
						String.format("'%s', '%s', '%s'", reg.getContenido().getID(), reg.getPerfil().getCorreo(), e.toString())));
			concreteInsert((T) reg);
			return true;
		}
		return false;
	}
	
	protected abstract void concreteInsert(T elem) throws Exception;
	
	@SuppressWarnings("unchecked")
	public boolean update(Registro reg) throws Exception {
		if (reg.getContenido().isTipo(tipo)) {
			String[] cols = { VBD.VALOR_REG },
					vals = { NumberFormat.getNumberInstance(Locale.ENGLISH).format(reg.getValoracion()) },
					colsPK = { VBD.ID_REG, VBD.PERF_REG },
					valsPK = { "'" + reg.getContenido().getID() + "'", "'" + reg.getPerfil().getCorreo() + "'" };
			BD.open().update(VBD.update(VBD.T_REGS, cols, vals, colsPK, valsPK));
			
			String[] colsPK2 = { VBD.ID_EST_REG, VBD.PERF_EST_REG };
			BD.open().delete(VBD.delete(VBD.T_ESTADO_REG, colsPK2, valsPK));
			
			for (EstadoReg e : reg.getEstados())
				BD.open().insert(VBD.insert(VBD.T_ESTADO_REG, VBD.ATRIBUTOS_ESTADO_REG, 
						String.format("'%s', '%s', '%s'", reg.getContenido().getID(), reg.getPerfil().getCorreo(), e.toString())));
			
			concreteUpdate((T) reg);
			return true;
		}
		return false;
	}
	
	protected abstract void concreteUpdate(T elem) throws Exception;
}
