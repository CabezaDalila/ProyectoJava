package sistema;
import java.io.*;
import java.time.LocalDate;
import java.util.List;
import model.PerfilInstagram;
import reports.*;
import parser.*;

public class Sistema implements Serializable{
	private static final long serialVersionUID = 1L;
	private   PerfilInstagram perfil;
	

	public Sistema() {
	    File a = new File("Datos.ser");
	    perfil = PerfilInstagram.getInstance();
	    if (perfil == null) {
	    
	        throw new IllegalStateException("El perfil no pudo ser inicializado.");
	    }

	    if (a.exists()) {
	    	System.out.println("aa");
	        this.recupera();
	        perfil = PerfilInstagram.getInstance();
	    } else {
	    	perfil = PerfilInstagram.getInstance();
	        this.serializa();
	    }
	    this.setSistema();
	}


	
	public void setSistema() {
		CargaXML carga=new CargaXML();
		System.out.println("Perfil antes de cargarPublicacionesXML: " + perfil);
		carga.cargarPublicacionesXML(perfil);
		this.serializa();
	}
	
	
	
	public static void main(String[] args) {
		
		Sistema sistema=new Sistema();
		 //sistema.perfil = PerfilInstagram.getInstance(); // inicializa perfil antes de usarlo
		//perfil = PerfilInstagram.getInstance();
		//perfil.cargarPublicaciones();
		List<ReportePublicacion> listaReportes = sistema.perfil.cantidadYpromedioDeMg();
		//generarReporteEnPantalla(listaReportes);
		//generarReporteEnArchivo(listaReportes);
		
		
		
		
		
		
		LocalDate inicio=LocalDate.parse("2023-05-01");
        LocalDate fin=LocalDate.parse("2023-05-02");
        
        List<ReporteAlbum> listaReportesAlbumes=sistema.perfil.listadoDeAlbumes(inicio,fin);
        System.out.println("Cantidad de albumes "+sistema.perfil.getListaAlbumes().size());//0
       // System.out.println(listaReportesAlbumes.size());//0

        for(ReporteAlbum reportes:listaReportesAlbumes) {
        	System.out.println("Album: "+reportes.getNombreAlbum()+" Cantidad de publicaciones: "+
        	reportes.getCantidadPublicaciones()+" Cantidad de comentarios: "+reportes.getCantidadComentarios());
        }
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
	
	
	
	
	
	public void serializa() {
	    ObjectOutputStream exit = null;
	    try {
	        exit = new ObjectOutputStream(new FileOutputStream("Datos.ser"));
	        exit.writeObject(perfil);
	    } catch (NotSerializableException nse) {
	        System.out.println("Un objeto no es serializable: " + nse.getMessage());
	        nse.printStackTrace();
	    } catch (IOException ioe) {
	        System.out.println("Error de E/S: " + ioe.getMessage());
	        ioe.printStackTrace();
	    } finally {
	        if (exit != null) {
	            try {
	                exit.close();
	            } catch (IOException ioe) {
	                System.out.println("Error al cerrar el ObjectOutputStream: " + ioe.getMessage());
	            }
	        }
	    }
	}

	public void recupera() {
	    ObjectInputStream in = null;
	    try {
	        in = new ObjectInputStream(new FileInputStream("Datos.ser"));
	        perfil = (PerfilInstagram) in.readObject();
	    } catch (FileNotFoundException fnfe) {
	        System.out.println("Archivo no encontrado: " + fnfe.getMessage());
	    } catch (ClassNotFoundException cnfe) {
	        System.out.println("Clase PerfilInstagram no encontrada: " + cnfe.getMessage());
	    } catch (IOException ioe) {
	        System.out.println("Error de E/S: " + ioe.getMessage());
	    } finally {
	        if (in != null) {
	            try {
	                in.close();
	            } catch (IOException ioe) {
	                System.out.println("Error al cerrar el ObjectInputStream: " + ioe.getMessage());
	            }
	        }
	    }
	}



}

	

