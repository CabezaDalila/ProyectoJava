package reports;

import java.io.Serializable;

public class ReporteAlbum implements Serializable{
	private static final long serialVersionUID = 1L;
    private String nombreAlbum;
    private int cantidadPublicaciones;
    private int cantidadComentarios;
    
    
	public ReporteAlbum(String nombreAlbum, int cantidadPublicaciones, int cantidadComentarios) {
		super();
		this.nombreAlbum = nombreAlbum;
		this.cantidadPublicaciones = cantidadPublicaciones;
		this.cantidadComentarios = cantidadComentarios;
	}
	
	public String getNombreAlbum() {
		return nombreAlbum;
	}
	public void setNombreAlbum(String nombreAlbum) {
		this.nombreAlbum = nombreAlbum;
	}
	public int getCantidadPublicaciones() {
		return cantidadPublicaciones;
	}
	public void setCantidadPublicaciones(int cantidadPublicaciones) {
		this.cantidadPublicaciones = cantidadPublicaciones;
	}
	public int getCantidadComentarios() {
		return cantidadComentarios;
	}
	public void setCantidadComentarios(int cantidadComentarios) {
		this.cantidadComentarios = cantidadComentarios;
	}

    
}
