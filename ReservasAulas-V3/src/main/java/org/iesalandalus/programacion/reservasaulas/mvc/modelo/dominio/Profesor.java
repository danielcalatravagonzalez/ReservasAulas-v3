package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Profesor implements Comparable<Profesor>, Serializable {
	// Atributos
	private static final String ER_TELEFONO = ("[69][0-9]{8}");
	private static final String ER_CORREO = ("[A-Za-z0-9+_.-]+@[a-z]+(\\.)[a-z]+$");
	private String nombre;
	private String correo;
	private String telefono;

	// Constructor con parámetros nombre y correo
	public Profesor(String nombre, String correo) {
		setNombre(nombre);
		setCorreo(correo);
	}

	// Constructor con parámetros nombre, correo y telefono
	public Profesor(String nombre, String correo, String telefono) {
		setNombre(nombre);
		setCorreo(correo);
		setTelefono(telefono);
	}

	// Constructor copia, validamos si es nulo, si no es nulo cogemos los setters
	public Profesor(Profesor otroProfesor) {
		if (otroProfesor == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
			} else {
			setNombre(otroProfesor.getNombre());
			setCorreo(otroProfesor.getCorreo());
			setTelefono(otroProfesor.getTelefono());
			}
	}

	//Setter de nombre, validamos si es nulo o si esta vacío, si no cumple las dos premisas cogemos el nombre
	//del método formateaNombre
	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre del profesor no puede ser nulo.");
		} else if (nombre.isBlank()) {
			throw new IllegalArgumentException("ERROR: El nombre del profesor no puede estar vacío.");
		} else {
			this.nombre = formateaNombre(nombre);
		}
	}
	
	//Método formateaNombre que permite devolver un nombre con la primera letra en mayúscula de cada palabra
	//y borra espacios que se creen de sobra
		private String formateaNombre(String nombre) {
			nombre = nombre.replaceAll("\\s{2,}", " ").trim();
			String [] palabras = nombre.split(" ");
			String nuevoNombre = "";
			for (int i=0; i<=palabras.length-1; i++) {
				palabras[i] = palabras[i].substring(0,1).toUpperCase() + palabras[i].substring(1).toLowerCase();
				nuevoNombre = nuevoNombre + palabras[i] + " ";
			}
			nombre = nuevoNombre.trim();
			return nombre;
		}

	// Setter de correo
	public void setCorreo(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		} else if (correo.isBlank() || !correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El correo del profesor no es válido.");
		} else {
			this.correo = correo;
		}
	}

	// Setter de telefono
	public void setTelefono(String telefono) {
		if (telefono == null) {
			this.telefono = telefono;
		} else if (telefono.isBlank() || telefono.length() != 9 || !telefono.matches(ER_TELEFONO)) {
		      throw new IllegalArgumentException("ERROR: El teléfono del profesor no es válido.");
		    } else {
		    	this.telefono = telefono;
		    } 
		  }

	// Getter de nombre
	public String getNombre() {
		return nombre;
	}

	// Getter de correo
	public String getCorreo() {
		return correo;
	}

	// Getter de telefono
	public String getTelefono() {
		return telefono;
	}
	
	// Método getProfesorFicticio
	public static Profesor getProfesorFicticio(String correo) {
		Profesor profesor = new Profesor("Profesor", correo);
		return new Profesor(profesor);
	}

	// Métodos hashCode y equals
	@Override
	public int hashCode() {
		return Objects.hash(nombre, correo, telefono);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Profesor))
			return false;
		Profesor other = (Profesor) obj;
		return Objects.equals(correo, other.correo);
	}

	//Método toString
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("nombre=");
		sb.append(this.nombre);
		sb.append(", correo=");
		sb.append(this.correo);
		if (this.telefono !=null) {
			sb.append(", teléfono=");
			sb.append(this.telefono);
		}
		return sb.toString();
	}

	//Método compareTo
	@Override
	public int compareTo(Profesor o) {
		return getCorreo().compareTo(o.getCorreo());
	}
}