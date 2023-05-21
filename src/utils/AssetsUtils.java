package utils;

import java.awt.Image;

import javax.swing.ImageIcon;

import view.ReportePublicaciones;

public class AssetsUtils {
	public static ImageIcon obtenerIcono(String nombreIcono) {
    	String ruta = String.format("/assets/icons/%s.png", nombreIcono);
    	try {    		
    		ImageIcon icono = new ImageIcon(ReportePublicaciones.class.getResource(ruta));
    		// Obtener la imagen del ImageIcon
    		Image imagenOriginal = icono.getImage();
    		Image imagenRedimensionada = imagenOriginal.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
    		// Crear un nuevo ImageIcon con la imagen redimensionada
    		ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);
    		return iconoRedimensionado;
    	} catch(Exception e) {
    	    String mensaje = String.format("No se ha podido encontrar el icono %s en la ruta %s", nombreIcono, ruta);
    		AlertUtils.mostrarAdvertencia(mensaje);
    		return null;
    	}
    }
}