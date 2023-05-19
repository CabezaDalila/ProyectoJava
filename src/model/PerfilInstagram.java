package model;

import parser.CargaXML;
import reports.ReporteAlbum;
import reports.ReportePublicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import exception.*;

public class PerfilInstagram implements Serializable{
	
	/** 
	 * Uso de Modelo Singleton
	 */
	private static final long serialVersionUID = 1L;
	String nombrePerfil; // BUSCAR COMO PONER EL NOMBRE (SETEAR O SOBRECARGAR GETINSTACE)
	private static PerfilInstagram perfil;
	private Set<Publicacion> listaPublicaciones;
	private List<Album> listaAlbumes;
	

	private PerfilInstagram() {
		this.listaPublicaciones = new TreeSet<Publicacion>();
		this.listaAlbumes = new ArrayList<Album>();
	}

	public static PerfilInstagram getInstance() {
	    if (perfil == null) {
	        perfil = new PerfilInstagram();
	        System.out.println("Creando nueva instancia de PerfilInstagram");
	    } else {
	        System.out.println("Devolviendo la instancia existente de PerfilInstagram");
	    }
	    return perfil;
	}


	public void cargarPublicaciones() {
		CargaXML cargador = new CargaXML();
		cargador.cargarPublicacionesXML(this);
	}
	
	
	public List<Album> getListaAlbumes() {
		return listaAlbumes;
	}

	public void setListaAlbumes(List<Album> listaAlbumes) {
		this.listaAlbumes = listaAlbumes;
	}

	public Set<Publicacion> getPublicaciones() {
		return listaPublicaciones;
	}
	
	public Set<String> getNombresPublicaciones() throws SinDatosException {
		if (listaPublicaciones.isEmpty()) {
			throw new SinDatosException("No hay datos.");
		}
		Set<String> nombres = new TreeSet<>();
		for (Publicacion p : listaPublicaciones) {
			nombres.add(p.getNombrePublicacion());
		}
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

	public void addPubliDentroAlbum(Album album, Publicacion publicacion) {
			publicacion.agregaAlbumPertenece(album);
			album.agregaPublicacionAalbum(publicacion);
	}

	
	public void eliminaAlbum(Album albumAEliminar) throws AlbumNoEncontradoException {
		// elimina album de la lista de albumes
		int albumAEliminarIndice = listaAlbumes.indexOf(albumAEliminar);
		if (albumAEliminarIndice == -1) {
			throw new AlbumNoEncontradoException("Album no encontrado");
		} else {
			Album album = listaAlbumes.get(albumAEliminarIndice);
			album.desasociarReferenciasAPublicaciones();
			listaAlbumes.remove(albumAEliminar);
			// en perfil instagram tenemos una referencia a las subAlbumes??
		}
	}
	
	public void eliminarPublicacion(Publicacion publicacionAEliminar) throws PublicacionNoEncontradaException, AlbumNoEncontradoException {
	    Iterator<Publicacion> iteradorPublicacion = listaPublicaciones.iterator();
	    while (iteradorPublicacion.hasNext()) {
	        Publicacion publicacion = iteradorPublicacion.next();
	        if (publicacion.equals(publicacionAEliminar)) {
	            iteradorPublicacion.remove();
	            break;
	        }
	    }
	    Iterator<Album> iteradorAlbum = listaAlbumes.iterator();
	    while (iteradorAlbum.hasNext()) {
	        Album album = iteradorAlbum.next();
	        if (album.existePublicacion(publicacionAEliminar)) {
	            album.desasociarReferenciasAPublicaciones();
	        }
	    }
	}
	public void sacarPublicacionDelAlbum(Publicacion publicacionASacar, Album album) throws PublicacionNoEncontradaException, AlbumNoEncontradoException{
		album.sacarPublicacion(publicacionASacar);
		publicacionASacar.sacarAlbum(album);
	}

	public void confListaReproduccion() {
		/*
		 * Permita la consulta y reproducción de un grupo de publicaciones seleccionadas
		 * de acuerdo a filtros flexibles aplicados a los atributos que se crean más
		 * relevantes. El orden de reproducción puede ser configurable de acuerdo a
		 * algún o algunos atributos.
		 */
	}

	public List<ReporteAlbum> listadoDeAlbumes(LocalDate inicio,LocalDate fin) {
		List<ReporteAlbum> listaReportesAlbumes=new ArrayList<ReporteAlbum>();
		for(Album album:listaAlbumes) {
			String nombreAlbum=album.getNombreAlbum();
			int contPublicaciones=0;
			int contComentarios=0;
			for(Publicacion publicacion:album.getListaPublicaciones()) {
				if(publicacion.estaFechaEnRango(inicio,fin)) {
					contPublicaciones++;
					contComentarios+=publicacion.getCantidadDeComentarios();
				}
			}
			ReporteAlbum reporte=new ReporteAlbum(nombreAlbum,contPublicaciones,contComentarios);
			listaReportesAlbumes.add(reporte);
		}
		
		return listaReportesAlbumes;
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
