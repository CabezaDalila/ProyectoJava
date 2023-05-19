package model;
import java.io.Serializable;
import java.time.LocalDate;
public class Video extends Publicacion implements Durable, Filtrable,Serializable {
	private static final long serialVersionUID = 1L;
	private String resolucion;
	private int cantCuadros;

	public Video(String nombrePublicacion, LocalDate fechaSubida, int cantMG, String resolucion, int cantCuadros) {
		super(nombrePublicacion, fechaSubida, cantMG);
		this.resolucion = resolucion;
		this.cantCuadros = cantCuadros;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public int getCantCuadros() {
		return cantCuadros;
	}

	public void setCantCuadros(int cantCuadros) {
		this.cantCuadros = cantCuadros;
	}

	@Override
	public String toString() {
	    return "Video{" +
	        super.toString() +
	        ", Resolucion=" + resolucion +", Cantidad de cuadros=" + cantCuadros +
	        '}';
	}

	public void avanzar() {
		

	}

	public void detener() {
		

	}
	
	public void aplicarFiltro() {
		
	}
	
	public String getTipoPublicacion() {
		return "Video";
	}

}
