package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Reserva implements Comparable<Reserva>, Serializable {
	//Atributos
	private Profesor profesor;
	private Aula aula;
	private Permanencia permanencia;
	
	//Constructor con parámetros
	public Reserva (Profesor profesor, Aula aula, Permanencia permanencia) {
		setProfesor(profesor);
		setAula(aula);
		setPermanencia(permanencia);
	}
	
	//Constructor copia, comprueba si es nula, si no cogemos los setters
	public Reserva (Reserva otraReserva) {
		if (otraReserva == null) {
			throw new NullPointerException ("ERROR: No se puede copiar una reserva nula.");
		} else {
			setProfesor(otraReserva.getProfesor());
			setAula(otraReserva.getAula());
			setPermanencia(otraReserva.getPermanencia());
		}
		
	}
	
	// Setter de profesor, validamos null y creamos una nueva instancia de profesor
	private void setProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException ("ERROR: La reserva debe estar a nombre de un profesor.");
		} else {
			this.profesor = new Profesor(profesor);
		}
		
	}

	// Getter de profesor
	public Profesor getProfesor() {
		return new Profesor(profesor);
	}

	// Setter de aula, validamos null y creamos una nueva instancia de aula
	private void setAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException ("ERROR: La reserva debe ser para un aula concreta.");
		} else {
			this.aula = new Aula(aula);
		}
		
	}

	// Getter de aula
	public Aula getAula() {
		return new Aula(aula);
	}

	// Setter de permanencia, comprobamos que sea nulo, si no variamos entre instancia por tramo y por hora
	private void setPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new NullPointerException ("ERROR: La reserva se debe hacer para una permanencia concreta.");
		} else if (permanencia instanceof PermanenciaPorHora) {
				this.permanencia = new PermanenciaPorHora((PermanenciaPorHora) permanencia);
		} else if (permanencia instanceof PermanenciaPorTramo) {
			this.permanencia = new PermanenciaPorTramo((PermanenciaPorTramo) permanencia);
		}
	}
	
	// Getter de permanencia, creamos una variable copia para diferenciar las instancias entre tramo y hora
	// y el método devuelve la variable copiaPermanencia
	public Permanencia getPermanencia() {
		return permanencia;
	}
	
	// Método getReservaFicticia, a partir de un Aula y una permanencia por parámetro
	// obtiene un profesor ficticio y devuelve una reserva
		public static Reserva getReservaFicticia(Aula aula, Permanencia permanencia) {
			Reserva reserva=new Reserva(Profesor.getProfesorFicticio("correodeprueba@gmail.com"), aula, permanencia);
			return new Reserva(reserva);
		}
		
	//Método getPuntos, recoge los puntos de permanencia y aula
		public float getPuntos() {
			return permanencia.getPuntos() + aula.getPuntos();
		}
		

	//Métodos hashCode y equals
	@Override
	public int hashCode() {
		return Objects.hash(aula, permanencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Reserva))
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(aula, other.aula) && Objects.equals(permanencia, other.permanencia);
	}

	//Método toString
	@Override
	public String toString() {
		return profesor.toString() + ", " + aula.toString() + ", " + permanencia.toString() + ", puntos=" + String.format("%.1f", getPuntos()) + "";
	}
	
	//Método compareTo
	@Override
	public int compareTo(Reserva o) {
		int comparadorAula = getAula().getNombre().compareTo(o.getAula().getNombre());

		if (comparadorAula == 0) {

			int comparadorFecha = getPermanencia().getDia().compareTo(o.getPermanencia().getDia());
			if (comparadorFecha == 0) {
				if (getPermanencia() instanceof PermanenciaPorTramo
						&& o.getPermanencia() instanceof PermanenciaPorTramo) {
					if (((PermanenciaPorTramo) getPermanencia()).getTramo() == Tramo.MANANA
							&& ((PermanenciaPorTramo) o.getPermanencia()).getTramo() == Tramo.TARDE) {
						return -1;
					} else if (((PermanenciaPorTramo) getPermanencia()).getTramo() == Tramo.TARDE
							&& ((PermanenciaPorTramo) o.getPermanencia()).getTramo() == Tramo.MANANA) {
						return 1;
					} else {
						return 0;
					}
				} else {
					return ((PermanenciaPorHora) getPermanencia()).getHora()
							.compareTo(((PermanenciaPorHora) o.getPermanencia()).getHora());
				}
			}
			return comparadorFecha;

		}
		return comparadorAula;
	}

}
