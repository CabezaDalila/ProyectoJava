package sistema;
import java.awt.EventQueue;
import java.io.*;
import java.time.LocalDate;
import java.util.List;
import model.PerfilInstagram;
import reports.ReportePublicacion;
import view.PerfilUsuario;
import reports.*;
import parser.*;
/*public class Sistema {
	
	private static PerfilInstagram perfil;

	
	public Sistema() {
	    File a = new File("Datos.ser");
	    perfil = PerfilInstagram.getInstance();
	    if (perfil == null) {
	    
	        throw new IllegalStateException("El perfil no pudo ser inicializado.");
	    }

	    if (a.exists()) {
	    	System.out.println("Existe el perfil");
	        this.recupera();

	    } else {

	        this.setSistema();
	    }
	 
	}

	
	public void setSistema() {
		CargaXML carga=new CargaXML();
		System.out.println("Perfil antes de cargarPublicacionesXML: " + perfil);
		carga.cargarPublicacionesXML(perfil);
		this.serializa();
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
	        PerfilInstagram.setInstance((PerfilInstagram) in.readObject());
	        perfil = PerfilInstagram.getInstance();

	       // PerfilInstagram.setInstance(perfil); // Establece la instancia recuperada como la instancia única
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

	
	
	public static void main(String[] args){
		Sistema sistema=new Sistema();
		perfil = PerfilInstagram.getInstance();   
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				PerfilUsuario frame = new PerfilUsuario(sistema);
				sistema.recupera();//deserializo aca
				frame.setVisible(true);
			}
		});
		perfil.cargarPublicaciones();
		List<ReportePublicacion> listaReportes = perfil.cantidadYpromedioDeMg();
		//generarReportePublicacionEnPantalla(listaReportes);
		generarReportePublicacionEnArchivo(listaReportes);
		
		

		LocalDate inicio=LocalDate.parse("2023-05-01");
        LocalDate fin=LocalDate.parse("2023-05-02");

        List<ReporteAlbum> listaReportesAlbumes=perfil.listadoDeAlbumes(inicio,fin);
        System.out.println("Cantidad de albumes "+perfil.getListaAlbumes().size());//0

        for(ReporteAlbum reportes:listaReportesAlbumes) {
        	System.out.println("Album: "+reportes.getNombreAlbum()+" Cantidad de publicaciones: "+
        	reportes.getCantidadPublicaciones()+" Cantidad de comentarios: "+reportes.getCantidadComentarios());
        }

	}
	
	public static void generarReportePublicacionEnPantalla(List<ReportePublicacion> listaReportes) {
		for(ReportePublicacion rep: listaReportes) {
			System.out.println("Tipo: "+rep.getTipoPublicacion());
			System.out.println("Cantidad de publicaciones: "+rep.getCantidadPublicaciones());
			System.out.println("Promedio de Mg: "+rep.getPromedio());
		}
	}
	
	public static void generarReportePublicacionEnArchivo(List<ReportePublicacion> listaReportes) {
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
	public static void generarReporteAlbumesEnArchivo(List<ReporteAlbum> listaReportesAlbumes) {
        String nombreArchivo = "reporteAlbumes.txt"; 
        try (FileWriter fileWriter = new FileWriter(nombreArchivo);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (ReporteAlbum rep : listaReportesAlbumes) {
                bufferedWriter.write("Nombre: " + rep.getNombreAlbum());
                bufferedWriter.newLine();
                bufferedWriter.write("Cantidad de publicaciones en rango de fechas: " 
                + rep.getCantidadPublicaciones());
                bufferedWriter.newLine();
                bufferedWriter.write("Cantidad de comentarios: " + rep.getCantidadComentarios());
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }*/

public class Sistema {

    private static PerfilInstagram perfil;

    public Sistema() {
        perfil = PerfilInstagram.getInstance();

        File archivoSerializado = new File("C:\\Users\\LENOVO\\Desktop\\git-main\\ProyectoJava\\Datos.ser");
        if (archivoSerializado.exists()) {
        	System.out.println("Existe el archivo");
            recupera();
        } else {
            setSistema();
            serializa();
        }
    }

    public void setSistema() {
        CargaXML carga = new CargaXML();
        carga.cargarPublicacionesXML(perfil);
    }

    public void serializa() {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream("Datos.ser"))) {
            salida.writeObject(perfil);
        } catch (IOException e) {
            System.out.println("Error al serializar el perfil: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void recupera() {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream("Datos.ser"))) {
            PerfilInstagram perfilRecuperado = (PerfilInstagram) entrada.readObject();
            PerfilInstagram.setInstance(perfilRecuperado);
            perfil = PerfilInstagram.getInstance();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Clase PerfilInstagram no encontrada: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de E/S: " + e.getMessage());
        }
    }




    public static void main(String[] args) {
        Sistema sistema = new Sistema();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                PerfilUsuario frame = new PerfilUsuario(sistema);
                frame.setVisible(true);
            }
        });

        perfil.cargarPublicaciones();

        List<ReportePublicacion> listaReportes = perfil.cantidadYpromedioDeMg();
        generarReportePublicacionEnArchivo(listaReportes);

        LocalDate inicio = LocalDate.parse("2023-05-01");
        LocalDate fin = LocalDate.parse("2023-05-02");

        List<ReporteAlbum> listaReportesAlbumes = perfil.listadoDeAlbumes(inicio, fin);
        System.out.println("Cantidad de álbumes1: " + sistema.getPerfilInstagram().getListaAlbumes().size());
        //System.out.println("Cantidad de álbumes: " + listaReportesAlbumes.size());

        for (ReporteAlbum reporte : listaReportesAlbumes) {
            System.out.println("Album: " + reporte.getNombreAlbum());
            System.out.println("Cantidad de publicaciones: " + reporte.getCantidadPublicaciones());
            System.out.println("Cantidad de comentarios: " + reporte.getCantidadComentarios());
        }
    }

    public static void generarReportePublicacionEnArchivo(List<ReportePublicacion> listaReportes) {
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
    public static void generarReporteAlbumesEnArchivo(List<ReporteAlbum> listaReportesAlbumes) {
        String nombreArchivo = "reporteAlbumes.txt"; 
        try (FileWriter fileWriter = new FileWriter(nombreArchivo);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (ReporteAlbum rep : listaReportesAlbumes) {
                bufferedWriter.write("Nombre: " + rep.getNombreAlbum());
                bufferedWriter.newLine();
                bufferedWriter.write("Cantidad de publicaciones en rango de fechas: " 
                + rep.getCantidadPublicaciones());
                bufferedWriter.newLine();
                bufferedWriter.write("Cantidad de comentarios: " + rep.getCantidadComentarios());
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
	
	public PerfilInstagram getPerfilInstagram() {
        return perfil;
    }
}
