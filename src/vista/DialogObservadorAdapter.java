package vista;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

import controlador.Controlador;
import modelo.Observador;
import modelo.contenido.Contenido;
import modelo.perfil.Perfil;

public abstract class DialogObservadorAdapter extends JDialog implements Observador {

	private static final long serialVersionUID = 1L;
	
	protected Controlador control;

	public DialogObservadorAdapter(JFrame owner, String tit, Controlador control) {
		super(owner, tit);
		this.control = control;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		control.removeObservador(this);
	}
	
	@Override
	public void onNuevaBusqueda() {}

	@Override
	public void onExtraerDeBusqueda(List<Contenido> nuevo) {}

	@Override
	public void onBuscarPorId(Contenido cont) {}

	@Override
	public void onCambioPerfil(Perfil p) {}

	@Override
	public void onRegister(List<Contenido> nuevo, Perfil p) {}

	@Override
	public void onError(String err) {}
}
