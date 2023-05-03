package model;
import java.time.LocalDate;
public class Audio extends Publicacion implements Durable {
	private int velocidadBits;

	public Audio(String nombrePublicacion, LocalDate fechaSubida, int cantMG, int velocidadBits) {
		super(nombrePublicacion, fechaSubida, cantMG);
		this.velocidadBits = velocidadBits;
	}
	
	@Override
	public String toString() {
	    return "Audio{" +
	        super.toString() +
	        ", velocidadBits=" + velocidadBits +
	        '}';
	}

	public int getVelocidadBits() {
		return velocidadBits;
	}

	public void setVelocidadBits(int velocidadBits) {
		this.velocidadBits = velocidadBits;
	}

	public void avanzar() {
		
	}
	
	public void detener() {
		
	}

}
