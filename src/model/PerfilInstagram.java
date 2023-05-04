package model;

import parser.CargaXML;
import reports.ReportePublicacion;

import java.util.*;
import exception.*;

public class PerfilInstagram {
	// SINGLETON
	String nombrePerfil; // BUSCAR COMO PONER EL NOMBRE (SETEAR O SOBRECARGAR GETINSTACE)
	private static PerfilInstagram perfil;
	private Set<Publicacion> listaPublicaciones;
	private List<Album> listaAlbumes;

	private PerfilInstagram() {
		this.listaPublicaciones = new TreeSet<Publicacion>();
		this.listaAlbumes = new ArrayList<Album>();
	}

	public static PerfilInstagram getInstance() {
		if (perfil == null)
			perfil = new PerfilInstagram();
		return perfil;
	}

	public void cargarPublicaciones() {
		CargaXML cargador = new CargaXML();
		cargador.cargarPublicacionesXML(this);// es como si le pasara perfil o sea la instancia donde se ejecuta el
												// cargarPublicaciones()
	}
	
	public Set<Publicacion> getPublicaciones() {
		return listaPublicaciones;
	}
	
	public String[] getNombresPublicaciones() {
		// este metodo deberia devolver un array de strings
		// con los nombres de todas las publicaciones que hay en la lista
		// se invoca en FiltraPublicaciones.java para que el usuario seleccione
		// que publicaciones quiere consultar/reproducir
		String nombres[] = new String[2];
		nombres[0] = "ESTO ES UNA PRUEBA 1";
		nombres[1] = "ESTO ES UNA PRUEBA 2";
		 return nombres;
	}

	public void addPublicacion(Publicacion publi) {
		if (publi != null) {
			listaPublicaciones.add(publi);
		}
	}

	public void addAlbum(Album nuevoAlbum) {
		if (nuevoAlbum != null)
			listaAlbumes.add(nuevoAlbum);
	}

	public Album buscaAlbum(String nombre) throws AlbumNoEncontradoException {
		int i = 0;
		while (i < listaAlbumes.size()) {
			Album album = listaAlbumes.get(i);
			if (album.getNombreAlbum().equalsIgnoreCase(nombre))
				return album;
			i++;
		}
		throw new AlbumNoEncontradoException("El álbum no se encuentra en la lista.");
	}
	
	public Publicacion buscaPubli(String nombre) throws PublicacionNoEncontradaException{
		Iterator <Publicacion> i = listaPublicaciones.iterator();
		while (i.hasNext()) {
			Publicacion publi = i.next();
			if (publi.getNombrePublicacion().equals(nombre))
				return publi;
		}
		throw new PublicacionNoEncontradaException("La publicación no se encuentra en la lista.");
	}
	
	
	
	public Map<String,List<Publicacion>> agruparPublicacionesPorTipo() {
		Map<String,List<Publicacion>> publicacionesPorTipo=new HashMap();
		for(Publicacion publicacion: listaPublicaciones) {
			String tipoPublicacion=publicacion.getTipoPublicacion();
			//putIfAbsent agrega un par clave-valor solo si la clave no existe en el mapa
			publicacionesPorTipo.putIfAbsent(tipoPublicacion,new ArrayList<>());
			//cuando al metodo get del map le paso una clave me devuelve el valor correspondiente a esa
			//clave. en este caso la clave es una lista vacia q cree arriba
			publicacionesPorTipo.get(tipoPublicacion).add(publicacion);
		}
		return publicacionesPorTipo;	
	}
	
	void muestraPublicacionesPorTipo() {
		Map<String, List<Publicacion>> publicacionesPorTipo = this.ordenarPublicacionesPorMg();
	    for (Map.Entry<String, List<Publicacion>> entry : publicacionesPorTipo.entrySet()) {
	        System.out.println("Tipo: " + entry.getKey());
	        for (Publicacion publicacion : entry.getValue()) {
	            System.out.println(publicacion);
	        }
	    }
	}
	
	public Map<String,List<Publicacion>> ordenarPublicacionesPorMg() {
		Map<String,List<Publicacion>> publicacionesPorTipo=this.agruparPublicacionesPorTipo();
		for(Map.Entry<String, List<Publicacion>> entry: publicacionesPorTipo.entrySet()) {
			List<Publicacion> publicaciones=entry.getValue();//obtengo la lista completa 
			
			Collections.sort(publicaciones, new Comparator<Publicacion>(){
			@Override
		         public int compare(Publicacion p1, Publicacion p2) {
					//al cambiar el orden de p2 y p1 los ordena descendentemente
		            return Integer.compare(p2.getCantMG(), p1.getCantMG());
			}
		});
		
		}
		return publicacionesPorTipo;
	}
	
	
	public List<ReportePublicacion> cantidadYpromedioDeMg(){
		Map<String, List<Publicacion>> publicacionesPorTipo=this.ordenarPublicacionesPorMg();
		List<ReportePublicacion> reporte=new ArrayList<ReportePublicacion>();
		for(Map.Entry<String, List<Publicacion>> entry: publicacionesPorTipo.entrySet()) {
			String tipoPublicacion=entry.getKey();
			List<Publicacion> publicaciones=entry.getValue();
			float promedio=0;
			int sum=0;
			int totalPublicaciones=publicaciones.size();
			for(Publicacion publi: publicaciones) {
				sum+=publi.getCantMG();
			}
			promedio=(sum/totalPublicaciones);
			
			reporte.add(new ReportePublicacion(tipoPublicacion,totalPublicaciones,promedio));
		}
		return reporte;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void addPubliDentroAlbum(String nombreAlbum, String nombrePubli) {
		
	}
	
	public void eliminaAlbum(Album albumAEliminar) throws AlbumNoEncontradoException {
		// elimina album de la lista de albumes
		int albumAEliminarIndice = listaAlbumes.indexOf(albumAEliminar);
		if (albumAEliminarIndice == -1) {
			throw new AlbumNoEncontradoException("Album no encontrado");
		} else {
			Album album = listaAlbumes.get(0);
			album.desasociarReferenciasAPublicaciones();
			listaAlbumes.remove(albumAEliminar);
			// en perfil instagram tenemos una referencia a las subAlbumes??
		}
	}
	
	public void confListaReproduccion() {
		/*
		 * Permita la consulta y reproducción de un grupo de publicaciones seleccionadas
		 * de acuerdo a filtros flexibles aplicados a los atributos que se crean más
		 * relevantes. El orden de reproducción puede ser configurable de acuerdo a
		 * algún o algunos atributos.
		 */
	}

	public void resporteAlfabeticoAlbumes() {
		/*
		 * Listado alfabético de Álbumes, detallando para cada uno cantidad de
		 * publicaciones subidas en un rango de fechas solicitado al operador. Incluir
		 * la cantidad de comentarios correspondientes a esas publicaciones.(por
		 * pantalla y en archivos de texto):
		 */
	}

	@Override
	public String toString() {
		// return "PerfilInstagram [listaPublicaciones=" + listaPublicaciones + ", listaAlbumes=" + listaAlbumes + "]";
		StringBuilder sb = new StringBuilder();
        sb.append("Lista de publicaciones:\n");
        for (Publicacion publicacion : listaPublicaciones) {
            sb.append("\t- " + publicacion.toString() + "\n");
        }
        sb.append("Lista de álbumes:\n");
        for (Album album : listaAlbumes) {
            sb.append("\t- " + album.toString() + "\n");
        }
        return sb.toString();
	}
}
