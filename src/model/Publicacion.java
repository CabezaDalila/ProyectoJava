package model;

import java.util.ArrayList;

import exception.AlbumNoEncontradoException;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Publicacion implements Comparable<Publicacion>,Serializable{
	private static final long serialVersionUID = 1L;
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
	public int getCantidadDeComentarios() {
		return listaComentarios.size();
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
	public void sacarAlbum(Album albumAEliminar) throws AlbumNoEncontradoException {
		boolean borrado= this.listaAlbumesPertenece.remove(albumAEliminar);
		if (!borrado) {
			throw new AlbumNoEncontradoException("Album no encontrado");
		}
	}
	public abstract String getTipoPublicacion();
	
	// verifica si la publicacion ya tiene ese album en la lista album pertenece para que no quede repetido
	public boolean existeAlbumPertenece(Album album) {
	    for (Album a : listaAlbumesPertenece) {
	        if (a.equals(album)) {
	            return true;
	        }
	    }
	    return false;
	}
	// antes de agregar verifica que no sea null y que no este repetido
    // deberia tener una excepsion por si esta repedido ?
	public void agregaAlbumPertenece(Album album) {
	    if (album != null && !existeAlbumPertenece(album)) {
	    	listaAlbumesPertenece.add(album);
	    }
	}
	public boolean estaFechaEnRango(LocalDate inicio, LocalDate fin) {
		 
	    return (fechaSubida.isEqual(inicio) || fechaSubida.isAfter(inicio)) &&
	    (fechaSubida.isEqual(fin) || fechaSubida.isBefore(fin));
	}

}
