package model;
import java.io.Serializable;
import java.time.LocalDate;
public class Video extends Publicacion implements Durable, Filtrable,Serializable {
	private String resolucion;
	private int cantCuadros;
	private float duracion;

	public Video(String nombrePublicacion, LocalDate fechaSubida, int cantMG, String resolucion, int cantCuadros, float duracion) {
		super(nombrePublicacion, fechaSubida, cantMG);
		this.resolucion = resolucion;
		this.cantCuadros = cantCuadros;
		this.duracion = duracion;
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
	
	public float getDuracion() {
		return duracion;
	}
	
	public void setDuracion(float duracion) {
		this.duracion = duracion;
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
