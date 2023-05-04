package model;

import java.util.ArrayList;
import java.time.LocalDate;

public abstract class Publicacion implements Comparable<Publicacion>{
	private String nombrePublicacion;
	private LocalDate fechaSubida;
	private int cantMG;
	private ArrayList<String> listaEtiquetas;
	private ArrayList<String> listaComentarios;
	private ArrayList<Album> listaAlbumesPertenece; // 0 o mas albumes

	public Publicacion(String nombrePublicacion, LocalDate fechaSubida, int cantMG) {
		this.nombrePublicacion = nombrePublicacion;
		this.fechaSubida = fechaSubida;
		this.cantMG = cantMG;
		// creo una lista vacia de Etiquetas,Comentarios y Albumes
		this.listaEtiquetas = new ArrayList<String>();
		this.listaComentarios = new ArrayList<String>();
		this.listaAlbumesPertenece = new ArrayList<Album>();// conviene ordenarlo alfabeticamente para poder mostrar
	}

	public String getNombrePublicacion() {
		return nombrePublicacion;
	}

	public void setNombrePublicacion(String nombrePublicacion) {
		this.nombrePublicacion = nombrePublicacion;
	}

	public LocalDate getFechaSubida() {
		return fechaSubida;
	}

	public void setFechaSubida(LocalDate fechaSubida) {
		this.fechaSubida = fechaSubida;
	}

	public int getCantMG() {
		return cantMG;
	}

	public void setCantMG(int cantMG) {
		this.cantMG = cantMG;
	}

	@Override
	public String toString() {
	    return  "nombrePublicacion{='" + nombrePublicacion + '\'' +
	            ", fechaSubida='" + fechaSubida + '\'' +
	            ", cantMG=" + cantMG +
	            ", etiquetas=" + listaEtiquetas +
	            ", comentarios=" + listaComentarios +
	            ", albumes=" + listaAlbumesPertenece +
	            '}';
	}

	
	//trim elimina los espacios en blanco al principio y al final de cada palabra
	public void agregarEtiqueta(String etiqueta) {
	    if (etiqueta != null && !etiqueta.trim().isEmpty()) {
	        listaEtiquetas.add(etiqueta.trim());
	    }
	}
	
	public void agregarComentario(String comentario) {
	    if (comentario != null && !comentario.trim().isEmpty()) {
	        listaComentarios.add(comentario.trim());
	    }
	}
	public void agregarAlbum(Album album) {
	    if (album != null) {
	    	listaAlbumesPertenece.add(album);
	    }
	}
	public void agregaPublicacion() {
		
	}

	public ArrayList<String> getListaEtiquetas() {
		return listaEtiquetas;
	}

	public void setListaEtiquetas(ArrayList<String> listaEtiquetas) {
		this.listaEtiquetas = listaEtiquetas;
	}

	public ArrayList<String> getListaComentarios() {
		return listaComentarios;
	}

	public void setListaComentarios(ArrayList<String> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}

	public ArrayList<Album> getListaAlbumesPertenece() {
		return listaAlbumesPertenece;
	}

	public void setListaAlbumesPertenece(ArrayList<Album> listaAlbumesPertenece) {
		this.listaAlbumesPertenece = listaAlbumesPertenece;
	}
	@Override
    public int compareTo(Publicacion otraPublicacion) {
        return this.nombrePublicacion.compareTo(otraPublicacion.nombrePublicacion);
    }
	public void eliminaAlbum(Album albumAEliminar) {
		for(Album album :listaAlbumesPertenece) {
			if (album.equals(albumAEliminar)) {
				listaAlbumesPertenece.remove(albumAEliminar);
				break;
			}
		}
	}
	public abstract String getTipoPublicacion();
}
