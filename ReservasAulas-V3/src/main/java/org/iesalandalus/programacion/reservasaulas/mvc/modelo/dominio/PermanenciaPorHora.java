package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PermanenciaPorHora extends Permanencia implements Comparable<Permanencia> {
	//Atributos
	private static final int PUNTOS = 3;
	private static final LocalTime HORA_INICIO = LocalTime.of(8, 0);
	private static final LocalTime HORA_FIN = LocalTime.of(22, 0);
	protected static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
	private LocalTime hora;
	
	//Constructor con parametros
	public PermanenciaPorHora(LocalDate dia, LocalTime hora) {
		super(dia);
		setHora(hora);
	}
	
	//Constructor copia
		public PermanenciaPorHora(PermanenciaPorHora otraPermanencia) {
			super(otraPermanencia);
			setHora(otraPermanencia.getHora());
			}
	
	//Métodos get y Set de Hora
	public LocalTime getHora() {
		return hora;
	}
	
	//Validamos si es nulo, si no es nulo coge la hora
	private void setHora(LocalTime hora) {
		if (hora == null) {
			throw new NullPointerException ("ERROR: La hora de una permanencia no puede ser nula.");
		} else if (hora.isBefore(HORA_INICIO) || hora.isAfter(HORA_FIN)) {
			throw new IllegalArgumentException("ERROR: La hora de una permanencia no es válida.");	
		} else if (!hora.toString().endsWith(":00")){
			throw new IllegalArgumentException("ERROR: La hora de una permanencia debe ser una hora en punto.");
		} else {
			this.hora = hora;
		}
		
	}
	
	//Método getPuntos
		public int getPuntos() {
			return PUNTOS;
		}

		@Override
		public int hashCode() {
			return Objects.hash(hora);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof PermanenciaPorHora))
				return false;
			PermanenciaPorHora other = (PermanenciaPorHora) obj;
			return Objects.equals(hora, other.hora);
		}

	//Método toString
		@Override
		public String toString() {
			return super.toString() + ", hora=" + hora + "";
		}

		// Método compareTo realizado como en PermanenciaPorTramo, en este caso se realiza un casting implícito 
		// con PermanenciaPorHora
		@Override
		public int compareTo(Permanencia otraPermanencia) {
			return this.getHora().compareTo(((PermanenciaPorHora)otraPermanencia).getHora());
		}
}
