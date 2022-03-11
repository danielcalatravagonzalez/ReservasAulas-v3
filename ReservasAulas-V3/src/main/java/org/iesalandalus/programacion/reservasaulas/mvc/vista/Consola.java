package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	// Atributos
	private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");;

	// Constructor consola (utilidad, por lo tanto no se instancian objetos)
	private Consola() {

	}

	// Método mostrarMenu
	public static void mostrarMenu() {
		mostrarCabecera("Bienvenido al gestor de reservas de aulas");
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
	}

	// Método mostrarCabecera
	public static void mostrarCabecera(String mostrarCabecera) {
		System.out.printf("%n%s%n", mostrarCabecera);
		String cadena = "%0" + mostrarCabecera.length() + "d%n";
		System.out.println(String.format(cadena, 0).replace("0", "-"));
	}

	// Método elegirOpcion()
	public static int elegirOpcion() {
		System.out.println("");
		System.out.println("Por favor, elija una opción");
		System.out.println("");
		int opcionElegida = Entrada.entero();
		while (opcionElegida < 0 || opcionElegida > Opcion.values().length) {
			System.out.println("Por favor, elija una opción comprendida entre 0 y 14: ");
			opcionElegida = Entrada.entero();
		}
		return opcionElegida;
	}

	// Método leerAula
	public static Aula leerAula() {
		System.out.println("Introduce el nombre del aula: ");
		String nombre = Entrada.cadena();
		System.out.println("Introduce el número de puestos del aula: ");
		int puestos = Entrada.entero();
		return new Aula(nombre, puestos);

	}
	
	//Método leerNumeroPuestos
	public static int leerNumeroPuestos() {
		System.out.println("Introduzca el número de puestos del aula");
		int puestos = Entrada.entero();
		return puestos;
	}
	
	//Método leerAulaFicticia
	public static Aula leerAulaFicticia() {
		System.out.println("Introduce el nombre del aula: ");
		String nombre = Entrada.cadena();
		return Aula.getAulaFicticia(nombre);
	}
	
	// Método leerNombreAula
	public static String leerNombreAula() {
		System.out.println("Introduzca el nombre del aula:");
		String nombre = Entrada.cadena();
		return nombre;
	}

	// Método leerProfesor
	public static Profesor leerProfesor() {
		System.out.println("Introduce el correo del profesor:");
		String correoProfesor = Entrada.cadena();
		System.out.println("Introduce el teléfono del profesor:");
		String telefonoProfesor = Entrada.cadena();
		if (telefonoProfesor == null || telefonoProfesor.isBlank()) {
			return new Profesor(leerNombreProfesor(), correoProfesor);
		} else {
			return new Profesor(leerNombreProfesor(), correoProfesor, telefonoProfesor);
		}
	}

	// Método leerNombreProfesor
	public static String leerNombreProfesor() {
		System.out.println("Introduzca el nombre del profesor:");
		String nombre = Entrada.cadena();
		return nombre;
	}
	
	//Método leerProfesorFicticio
	public static Profesor leerProfesorFicticio() {
		System.out.println("Introduce el correo del profesor: ");
		return Profesor.getProfesorFicticio(Entrada.cadena());
	}

	// Método leerTramo
	public static Tramo leerTramo() {
		System.out.println("Eliga un tramo insertando 1 (Mañana) o 2 (Tarde): ");
		int indice = Entrada.entero();
		switch (indice) {
		case 1:
			return Tramo.MANANA;

		case 2:
			return Tramo.TARDE;

		default:
			return null;
		}
	}

	// Método leerDia
	public static LocalDate leerDia() {
		LocalDate dia = null;
		System.out.println("Introduza una fecha(dd/MM/yyyy):");
		String fecha = Entrada.cadena();
		try {
			dia = LocalDate.parse(fecha, FORMATO_DIA);
		} catch (DateTimeParseException e) {
			System.out.println("ERROR: El formato de la fecha no es correcto.");
		}
		return dia;
	}
	
	//Método elegirPermanencia
	public static int elegirPermanencia() {
		int permanenciaElegida = 0;
		do {
			System.out.println("Seleccione uno de los dos tipos de permanencia:");
			System.out.println("1- Por tramo (mañana o tarde)");
			System.out.println("2- Por horas");
			permanenciaElegida = Entrada.entero();
		} while (permanenciaElegida < 1 || permanenciaElegida > 2);
		return permanenciaElegida;
	}
	
	//Método leerPermanencia
	public static Permanencia leerPermanencia() {
		int permanenciaElegida = elegirPermanencia();
		Permanencia resolucionPermanencia = null;
		if(permanenciaElegida == 1) {
			resolucionPermanencia = new PermanenciaPorTramo(leerDia(), leerTramo());
		}
		else {
			resolucionPermanencia = new PermanenciaPorHora(leerDia(), leerHora());
		}
		if (resolucionPermanencia instanceof PermanenciaPorTramo)
			return new PermanenciaPorTramo((PermanenciaPorTramo) resolucionPermanencia);
		else {
			return new PermanenciaPorHora((PermanenciaPorHora) resolucionPermanencia);
		}
	}

	//Método leerHora
	private static LocalTime leerHora() {
		LocalTime hora = null;
		boolean excepcion = false;
		do {
			try {
				System.out.println("Introduzca una hora con el siguiente formato: HH:00 (siendo H el número de la hora:");
				String horaIntroducida = Entrada.cadena();
				hora = LocalTime.parse (horaIntroducida);
				excepcion = false;
			} catch (DateTimeParseException e) {
				System.out.println("ERROR: Formato incorrecto");
				excepcion = true;
			}
		} while (excepcion == true);
		return hora;
	}
	
	//Método leerReserva
	public static Reserva leerReserva() {
		Profesor profesor = leerProfesorFicticio();
		Aula aula = leerAulaFicticia();
		Permanencia permanencia = leerPermanencia();
		return new Reserva(profesor, aula, permanencia);
	}
	
	//Método leerReservaFicticia
	public static Reserva leerReservaFicticia() {
		return Reserva.getReservaFicticia(leerAulaFicticia(), leerPermanencia());
	}
}
