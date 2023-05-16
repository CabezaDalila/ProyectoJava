package sistema;
import view.*;

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import model.PerfilInstagram;
import reports.ReportePublicacion;

public class Sistema {
	
	private static PerfilInstagram perfil;

	public static void main(String[] args) {
		perfil = PerfilInstagram.getInstance();
		perfil.cargarPublicaciones();
		
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                PerfilUsuario frame = new PerfilUsuario();
                frame.setVisible(true);
            }
        });
		
		List<ReportePublicacion> listaReportes = perfil.cantidadYpromedioDeMg();

	}
	
	public static void generarReporteEnPantalla(List<ReportePublicacion> listaReportes) {
		for(ReportePublicacion rep: listaReportes) {
			System.out.println("Tipo: "+rep.getTipoPublicacion());
			System.out.println("Cantidad de publicaciones: "+rep.getCantidadPublicaciones());
			System.out.println("Promedio de Mg: "+rep.getPromedio());
		}
	}
	
	public static void generarReporteEnArchivo(List<ReportePublicacion> listaReportes) {
        String nombreArchivo = "reporte.txt";
        
        try (FileWriter fileWriter = new FileWriter(nombreArchivo);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (ReportePublicacion rep : listaReportes) {
                bufferedWriter.write("Tipo: " + rep.getTipoPublicacion());
                bufferedWriter.newLine();
                bufferedWriter.write("Cantidad de publicaciones: " + rep.getCantidadPublicaciones());
                bufferedWriter.newLine();
                bufferedWriter.write("Promedio de Mg: " + rep.getPromedio());
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
        
    }

}
