package modelo;

import java.time.LocalDate;
import excepciones.DuracionInvalidaExcepcion;
import modelo.enums.EnumTipoPublicacion;
import modelo.interfaces.IDurable;

/**
 * La Clase Audio. 
 * Extiende de La Clase Publicacion. 
 * Implementa interface Durable.
 */
public class Audio extends Publicacion implements IDurable {

	/** Estatico serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** La velocidad en bits. */
	private int velocidadBits;

	/** El inicio. */
	private float inicio;

	/** El fin. */
	private float fin;

	/** El fin de la reproduccion. */
	private float finReproduccion;

	/**
	 * Instancia un nuevo audio.
	 *
	 * @param nombrePublicacion el nombre publicacion
	 * @param fechaSubida       la fecha subida
	 * @param cantMG            la cantidad de Me Gustas
	 * @param velocidadBits     la velocidad en bits
	 * @param duracion          la duracion
	 */
	public Audio(String nombrePublicacion, LocalDate fechaSubida, int cantMG, int velocidadBits, float duracion) {
		super(nombrePublicacion, fechaSubida, cantMG, EnumTipoPublicacion.AUDIO);
		this.velocidadBits = velocidadBits;
		this.inicio = 0;
		this.fin = duracion;
		this.finReproduccion = duracion;
	}
	
	/**
	 * Obtiene el fin configurado en la reproducción.
	 *
	 * @return tiempo de detencón configurada en la reproducción [segundos]
	 */
	public float getFinReproduccion() {
		return finReproduccion;
	}

	/**
	 * To string.
	 *
	 * @return una cadena de texto que representa los atributos particulares del audio.
	 */
	@Override
	public String toString() {
		return "Audio{" + super.toString() + ", velocidadBits=" + velocidadBits + '}';
	}

	/**
	 * getVelocidadBits
	 * Obtiene la velocidad en bits.
	 *
	 * @return la velocidad en bits[int]
	 */
	public int getVelocidadBits() {
		return velocidadBits;
	}

	/**
	 * setVelocidadBits
	 * Setea la velocidad en bits.
	 *
	 * @param velocidadBits
	 */
	public void setVelocidadBits(int velocidadBits) {
		this.velocidadBits = velocidadBits;
	}

	/**
	 * Obtiene la duracion actualizada según la configuración particular de la reproducción.
	 *
	 * @return duracion [segundos]
	 */
	//
	public float calcularDuracion() {
		return this.finReproduccion - this.inicio;
	}

	/**
	 * Obtiene el tiempo de inicio configurado en la reproducción.
	 *
	 * @return tiempo de inicio [segundos]
	 */
	public float getInicioReproduccion() {
		return inicio;
	}

	 /**
		 * Obtiene el tiempo de finalización original.
		 *
		 * @return tiempo de finalización original [segundos]
		 */
	public float getFinOriginal() {
		return fin;
	}

	/**
	 * Avanzar. De la interface Durable.
	 * Actualiza la duracion de la reproduccion del audio.
	 *
	 * @param inicioRelativo [segundos]
	 * @throws DuracionInvalidaExcepcion
	 */
	public void avanzar(float inicioRelativo) throws DuracionInvalidaExcepcion {
		if (inicioRelativo >= 0 && inicioRelativo < this.finReproduccion) {
			this.inicio = inicioRelativo;
		} else {
			throw new DuracionInvalidaExcepcion(
					"El tiempo de inicio debe ser menor al de detención y mayor o igual a 0");
		}
	}

	/**
	 * Detener. De la interface Durable.
	 * Actualiza la duracion de la reproduccion del audio.
	 *
	 * @param finRelativo [segundos]
	 * @throws DuracionInvalidaExcepcion
	 */
	public void detener(float finRelativo) throws DuracionInvalidaExcepcion {
		if (finRelativo > this.inicio && finRelativo <= this.fin) {
			this.finReproduccion = finRelativo;
		} else {
			throw new DuracionInvalidaExcepcion(
					"El tiempo de detención debe ser mayor al de inicio y menor o igual a la duración original");
		}
	}

}
