package launcher;

import javax.swing.SwingUtilities;

import controlador.Controlador;
import modelo.Aplicacion;
import vista.MainWindow;

public class Main {

	public static void main(String[] args) throws Exception {
		Aplicacion m = new Aplicacion();
		Controlador c = new Controlador(m);
		SwingUtilities.invokeLater(() -> new MainWindow(c));
	}
}
