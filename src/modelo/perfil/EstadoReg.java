package modelo.perfil;

public enum EstadoReg {
	FAVORITOS, PENDIENTES, SIGUIENDO, FINALIZADOS, ABANDONADOS;
	
	/**
	 * Determina si el estado es compatible con otro estado dado, es decir, si se pueden dar a la vez
	 * @param e - Otro estado
	 * @return True si se pueden dar a la vez, False en otro caso
	 */
	public boolean compatible(EstadoReg e) {
		return e.independiente() || this.independiente();
	}
	
	private boolean independiente() {
		return this == FAVORITOS;
	}
}
