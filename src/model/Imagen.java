package model;

public class Imagen extends Publicacion implements Filtrable {
	private String resolucion;
	private int ancho;
	private int alto;

	public Imagen(String nombrePublicacion, String fechaSubida, int cantMG, String resolucion, int ancho, int alto) {
		super(nombrePublicacion, fechaSubida, cantMG);
		this.resolucion = resolucion;
		this.ancho = ancho;
		this.alto = alto;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	@Override
	public String toString() {
		return "Imagen [resolucion=" + resolucion + ", ancho=" + ancho + ", alto=" + alto + "]";
	}
	
	public void aplicarFiltro() {
		
	}

}