package vista;

import java.util.List;

import javax.swing.JPanel;

import modelo.Observador;
import modelo.contenido.Contenido;
import modelo.perfil.Perfil;

public abstract class PanelObservadorAdapter extends JPanel implements Observador {

	private static final long serialVersionUID = 1L;

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
