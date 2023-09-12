package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnails;

public class ImageParser {
	
	/**
	 * Devuelve una imagen a partir de una URL s, la cual busca en la carpeta resources\tmp, y en caso de no encontrarla la descarga para futuros usos.
	 * @param s - URL de la imagen
	 * @param width - Anchura final
	 * @param height - Altura final
	 * @return - Imagen de la URL con las dimensiones dadas, o una imagen por defecto en caso de no poder cargarla.
	 */
	public static ImageIcon iconFromURL(String s, int width, int height) {
		BufferedImage image = null;
		String s2 = replaceIllegal(s);
		try {
			File file = new File("resources\\tmp\\" + s2 + ".png");
			if (file.createNewFile())
				Thumbnails.of(new URL(s)).scale(1).toFile(file);
			image = Thumbnails.of("resources\\tmp\\" + s2 + ".png").forceSize(width, height).asBufferedImage();
		}
		catch (IOException ioe) {
        	try {
				image = Thumbnails.of(new File("resources\\imagenes\\unknown.jpg")).forceSize(width, height).asBufferedImage();
			} catch (IOException e1) {
				throw new RuntimeException("No se ha encontrado la imagen unknown.jpg");
			}
		}
		return new ImageIcon(image);
	}
	
	/**
	 * Devuelve una imagen a partir del nombre s, el cual busca en la carpeta resources\imagenes, con las dimensiones especificadas.
	 * @param s - Nombre del archivo de imagen
	 * @param width - Anchura final
	 * @param height - Altura final
	 * @return - Imagen del archivo con las dimensiones dadas, o una imagen por defecto en caso de no encontrar el archivo.
	 */
	public static ImageIcon iconFromFile(String s, int width, int height) {
		try {
			return new ImageIcon(Thumbnails.of(new File("resources\\imagenes\\" + s)).forceSize(width, height).asBufferedImage());
        } 
        catch (IOException e) {
			throw new RuntimeException("No se ha encontrado la imagen " + s);
        }
	}
	
	private static String replaceIllegal(String s) {
		return s.replace("/", "")
		.replace("\\", "")
		.replace(":", "")
		.replace("*", "")
		.replace("?", "")
		.replace("\"", "")
		.replace("<", "")
		.replace(">", "")
		.replace("|", "");
	}
}
