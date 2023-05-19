package reports;

import java.io.Serializable;

public class ReportePublicacion implements Serializable{
	private static final long serialVersionUID = 1L;
	private String tipoPublicacion;
	private int cantidadPublicaciones;
	private float promedio;
	
	public ReportePublicacion(String tipoPublicacion, int cantidadPublicaciones, float promedio) {
		this.tipoPublicacion=tipoPublicacion;
		this.cantidadPublicaciones = cantidadPublicaciones;
		this.promedio = promedio;
	}
	
	public String getTipoPublicacion() {
		return tipoPublicacion;
	}

	public void setTipoPublicacion(String tipoPublicacion) {
		this.tipoPublicacion = tipoPublicacion;
	}

	public int getCantidadPublicaciones() {
		return cantidadPublicaciones;
	}
	public void setCantidadPublicaciones(int cantidadPublicaciones) {
		this.cantidadPublicaciones = cantidadPublicaciones;
	}

	public float getPromedio() {
		return promedio;
	}

	public void setPromedio(float promedio) {
		this.promedio = promedio;
	}
	
	    @Override
	public String toString() {
		return "ReportePublicacion [tipoPublicacion=" + tipoPublicacion + ", cantidadPublicaciones="
				+ cantidadPublicaciones + ", promedio=" + promedio + "]";
	}

	
	
}
