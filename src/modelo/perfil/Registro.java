package modelo.perfil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import modelo.contenido.Contenido;

public abstract class Registro {
	
	private static final float INIT_VAL = -1;
	
	protected Perfil perfil;
	protected List<EstadoReg> estados;
	protected float valoracion;
	
	public Registro(Perfil p) {
		perfil = p;
		estados = new ArrayList<>();
		valoracion = INIT_VAL;
	}
	
	/**
	 * Devuelve true si el registro tiene el estado introducido
	 * @param est - Estado a comprobar
	 * @return true si el registro tiene el estado introducido, false en caso contrario
	 */
	public boolean hasEstado(EstadoReg est) {
		for (EstadoReg e : estados)
			if (e == est)
				return true;
		return false;
	}
	
	/**
	 * Alterna un estado (lo añade, o en caso de ya tenerlo lo elimina) del registro
	 * @param e - Estado a alternar
	 */
	public void switchEstado(EstadoReg estado) {
		if (hasEstado(estado))
			estados.remove(estado);
		else {
			removeIncompatible(estado);
			estados.add(estado);
		}
	};
	
	public List<EstadoReg> getEstados() {
		return Collections.unmodifiableList(estados);
	}
	
	public float getValoracion() {
		return valoracion;
	}
	
	public Perfil getPerfil() {
		return perfil;
	}
	
	public abstract Contenido getContenido();
	
	private void removeIncompatible(EstadoReg est) {
		for (EstadoReg e : estados)
			if (!e.compatible(est)) {
				estados.remove(e);
				break;
			}
	}
	
	/**
	 * Establece una nueva valoracion para el registro
	 * @param val - Nueva valoracion
	 */
	public void setValoracion(float val) {
		valoracion = val;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.getClass().equals(o.getClass()) && perfil.equals(((Registro) o).perfil) && getContenido().equals(((Registro) o).getContenido());
	}
}
