package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Aula implements Comparable<Aula>, Serializable {
	//Atributos
	private static final float PUNTOS_POR_PUESTO = (float) 0.5;
	private static final int MIN_PUESTOS = 10;
	private static final int MAX_PUESTOS = 100;
	private String nombre;
	private int puestos;
	
	//Constructor con parámetro
	public Aula (String nombre, int puestos) {
		setNombre(nombre);
		setPuestos(puestos);
	}
	
	//Constructor copia, validamos si es nulo, si no es nulo cogemos los setters
	public Aula(Aula otraAula) {
		if (otraAula == null) {
			throw new NullPointerException("ERROR: No se puede copiar un aula nula.");
		} else {
			setNombre(otraAula.getNombre());
			setPuestos(otraAula.getPuestos());
		}
	}
	
	//Setter de nombre, validamos si es nulo o si esta vacío, si no cumple las dos premisas cogemos el nombre
	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException ("ERROR: El nombre del aula no puede ser nulo.");
		} else if (nombre.isBlank()) {
			throw new IllegalArgumentException ("ERROR: El nombre del aula no puede estar vacío.");
		} else {
			this.nombre = nombre;
		}
		
	}

	//Getter de Puestos
	public int getPuestos() {
		return puestos;
	}
	
	//Setter de Puestos, comprobamos si el valor introducido como parámetro es menor que
	//la constante MIN_PUESTOS y si es mayor que MAX_PUESTOS, si no cumple con
	//las dos premisas cogemos los puestos 
	private void setPuestos(int puestos) {
		if (puestos < MIN_PUESTOS || puestos > MAX_PUESTOS) {
			throw new IllegalArgumentException ("ERROR: El número de puestos no es correcto.");
		} else {
			this.puestos = puestos;
		}
	}
	
	//Getter de nombre
	public String getNombre() {
		return nombre;
	}
	
	//Getter de puntos
	public float getPuntos() {
		return PUNTOS_POR_PUESTO * puestos;
	}
	
	//Método getAulaFicticia
	public static Aula getAulaFicticia(String aula) {
		Aula aula1 = new Aula("Ciencias", 20);
		return new Aula(aula1);
	}

	//Métodos hashCode y equals
	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Aula))
			return false;
		Aula other = (Aula) obj;
		return Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "nombre=" + nombre + ", puestos=" + puestos;
	}

	//Método compareTo
	@Override
	public int compareTo(Aula aula) {
		return getNombre().compareTo(aula.getNombre());
	}



}