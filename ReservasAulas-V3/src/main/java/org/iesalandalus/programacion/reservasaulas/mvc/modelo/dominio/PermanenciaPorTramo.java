package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.time.LocalDate;
import java.util.Objects;

public class PermanenciaPorTramo extends Permanencia implements Comparable<Permanencia> {
	//Atributos
	private static final int PUNTOS = 10;
	public Tramo tramo;
	
	//Constructor con parámetros
	public PermanenciaPorTramo(LocalDate dia, Tramo tramo) {
		super(dia);
		setTramo(tramo);
	}

	//Constructor copia
	public PermanenciaPorTramo(PermanenciaPorTramo otraPermanencia) {
		super(otraPermanencia);
		if (otraPermanencia == null) {
			throw new NullPointerException("ERROR: No se puede copiar una permanencia nula.");
		} else {
			setTramo(otraPermanencia.getTramo());
		}
	}

	//Getters y Setters de tramo
	public Tramo getTramo() {
		return tramo;
	}

	private void setTramo(Tramo tramo) {
		if (tramo == null) {
			throw new NullPointerException ("ERROR: El tramo de una permanencia no puede ser nulo.");
		} else {
		this.tramo = tramo;
		}
		
	}
	
	//Método getPuntos
	@Override
	public int getPuntos() {
		return PUNTOS;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tramo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PermanenciaPorTramo))
			return false;
		PermanenciaPorTramo other = (PermanenciaPorTramo) obj;
		return tramo == other.tramo;
	}

	//Método toString
	@Override
	public String toString() {
		return super.toString() + ", tramo=" + tramo + "";
	}

	@Override
	public int compareTo(Permanencia otraPermanencia) {
		return this.getDia().compareTo(otraPermanencia.getDia());
		/*He leido el manual de la clase LocalDate (https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html) para poner la línea 70
		 *  Forma hecha en clase:
		 * int resultado = 0;
		if (this.getDia().isAfter(otraPermanencia.getDia())) {
			resultado = 1;
		} else if (this.getDia().isBefore(otraPermanencia.getDia())) {
			resultado = -1;
		} else if (this.getDia().isEqual(otraPermanencia.getDia())) {
			if (otraPermanencia instanceof PermanenciaPorTramo) {
				PermanenciaPorTramo p = new PermanenciaPorTramo((PermanenciaPorTramo) otraPermanencia);
				if (this.getTramo().equals(Tramo.MANANA) && p.getTramo().equals(Tramo.TARDE)) {
					resultado = -1;
				} else if (this.getTramo().equals(Tramo.TARDE) && p.getTramo().equals(Tramo.MANANA)) {
					resultado = 1;
				} else {
					resultado = 0;
				}
			}
		}
		return resultado;
		*/
	}

}
