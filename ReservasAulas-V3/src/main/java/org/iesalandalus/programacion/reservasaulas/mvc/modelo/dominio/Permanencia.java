package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Permanencia implements Serializable {
	//Atributos
	private LocalDate dia;
	protected static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	//Constructor con parámetros
	public Permanencia(LocalDate dia) {
		setDia(dia);
	}

	//Constructor copia, validamos si es nulo, si no es nulo ponemos el setter
	public Permanencia(Permanencia otraPermanencia) {
		if (otraPermanencia == null) {
			throw new NullPointerException("ERROR: No se puede copiar una permanencia nula.");
		} else {
			setDia(otraPermanencia.getDia());
		}
	}

	//Getters y Setters de dia
	public LocalDate getDia() {
		return dia;
	}

	//Aquí validamos si es nulo, si no es nulo coge el día
	private void setDia(LocalDate dia) {
		if (dia == null) {
			throw new NullPointerException ("ERROR: El día de una permanencia no puede ser nulo.");
		}
		LocalDate fechaActual = LocalDate.now();
		if(dia.compareTo(fechaActual) < 0) {
			throw new IllegalArgumentException("ERROR: No puedes introducir una fecha anterior a la actual.");
		}
		
		this.dia = dia;
	}
	
	//Método int getPuntos()
	public abstract int getPuntos();

	//Métodos hashCode y equals
	@Override
	public abstract int hashCode();

	@Override
	public abstract boolean equals(Object obj);

	//Método toString
	@Override
	public String toString() {

		return "día=" + dia.format(FORMATO_DIA);
	}
	public abstract int compareTo(Permanencia otraPermanencia);
}
