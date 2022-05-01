package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;

public class Vista implements IVista {
	// Atributos
	private IControlador Icontrolador;

//Constructor por defecto
	public Vista() {
		Opcion.setVista(this);
	}

	// Método setControlador
	@Override
	public void setControlador(IControlador controlador) {
		this.Icontrolador = controlador;
	}

	// Método comenzar
	@Override
	public void comenzar() {
		Consola.mostrarCabecera("Programa de gestión para reservar aulas");
		int ordinalOpcion;
		do {
			Consola.mostrarMenu();
			ordinalOpcion = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
			opcion.ejecutar();
		} while (ordinalOpcion != Opcion.SALIR.ordinal());
	}

	// Método salir
	@Override
	public void salir() {
		Icontrolador.terminar();
	}

	// Método insertarAula
	public void insertarAula() {
		Consola.mostrarCabecera("Insertar aula");
		try {
			Aula aula = Consola.leerAula();
			Icontrolador.insertarAula(aula);
			System.out.println("Se ha insertado el aula correctamente");
		} catch (OperationNotSupportedException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	// Método borrarAula
	public void borrarAula() {
		Consola.mostrarCabecera("Borrar aula");
		try {
			Aula aula = Consola.leerAula();
			Icontrolador.borrarAula(aula);
			System.out.println("Se ha borrado el aula correctamente.");
		} catch (OperationNotSupportedException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	// Método buscarAula
	public void buscarAula() {
		Consola.mostrarCabecera("Buscar aula");
		Aula aula = null;
		try {
			aula = Consola.leerAula();
			aula = Icontrolador.buscarAula(aula);
			if (aula != null) {
				System.out.println("El aula buscado es: " + aula);
			} else {
				System.out.println("No existe ningún aula con ese nombre");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// Método listarAula
	public void listarAula() {
		Consola.mostrarCabecera("Listar aulas");
		List<String> aulas = Icontrolador.representarAulas();
		if (aulas.size() != 0) {
			for (String aula : aulas) {
				System.out.println(aula);
			}
		} else {
			System.out.println("No hay aulas que listar.");
		}
	}

	// Método insertarProfesor
	public void insertarProfesor() {
		Consola.mostrarCabecera("Insertar profesor");
		try {
			Profesor profesor = Consola.leerProfesor();
			Icontrolador.insertarProfesor(profesor);
			System.out.println("El profesor se ha insertado correctamente");
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// Método borrarProfesor
	public void borrarProfesor() {
		Consola.mostrarCabecera("Borrar profesor");
		try {
			Profesor profesor = Consola.leerProfesor();
			Icontrolador.borrarProfesor(profesor);
			System.out.println("El profesor se ha borrado correctamente.");
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// Método buscarProfesor
	public void buscarProfesor() {
		Consola.mostrarCabecera("Buscar profesor");
		Profesor profesor = null;
		try {
			profesor = Consola.leerProfesor();
			profesor = Icontrolador.buscarProfesor(profesor);
			if (profesor != null) {
				System.out.println("El profesor buscado es: " + profesor);
			} else {
				System.out.println("No existe ningún profesor con ese nombre");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// Método listarProfesor
	public void listarProfesor() {
		Consola.mostrarCabecera("Listar profesores");
		List<String> profesores = Icontrolador.representarProfesores();
		if (profesores.size() != 0) {
			for (String profesor : profesores) {
				System.out.println(profesor);
			}
		} else {
			System.out.println("No hay profesores que listar.");
		}
	}

	// Método realizarReserva
	public void realizarReserva() {
		Consola.mostrarCabecera("Realizar reserva");
		try {
			Profesor buscarProfesor = Icontrolador.buscarProfesor(Consola.leerProfesor());
			if (buscarProfesor == null) {
				throw new NullPointerException("El profesor introducido no existe.");
			} else {
				Reserva reserva = Consola.leerReserva();
				if (reserva == null) {
					throw new IllegalArgumentException("Hay un error en los datos introducido para hacer la reserva, para volver a intentarlo vuelva a insertar una reserva.");
				}
				Icontrolador.realizarReserva(reserva);
				System.out.println("Reserva realizada correctamente.");
			}
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	// Método anularReserva
	public void anularReserva() {
		Consola.mostrarCabecera("Anular reserva");
		Reserva reserva = null;
		List<String> listaReservas = Icontrolador.representarReservas();
		if (listaReservas == null) {
			System.out.println("No existen reservas que borrar");
		} else {
			try {
				reserva = Consola.leerReservaFicticia();
				Icontrolador.anularReserva(reserva);
				System.out.println("Reserva anulada correctamente.");
			} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	// Método listarReservas
	public void listarReservas() {
		Consola.mostrarCabecera("Listar Reservas");
		List<Reserva> listaReservas = null;
		try {
			Permanencia permanencia=null;
			permanencia = new PermanenciaPorTramo(Consola.leerDia(),Tramo.TARDE);
			listaReservas = Icontrolador.getReservasPermanencia(permanencia);	
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		if (listaReservas == null) {
			System.out.println("No existen reservas");
		} else {
			Iterator<Reserva> iterador = listaReservas.iterator();
			while (iterador.hasNext()) {
				System.out.println(iterador.next().toString());
			}
		}
	}

	// Método listarReservasAula
	public void listarReservasAula() {
		Consola.mostrarCabecera("Listar reservas aula");
		Aula aula = Consola.leerAula();
		List<Reserva> reservasAulas = Icontrolador.getReservasAula(aula);
		if (reservasAulas.size() != 0) {
			for (Reserva reservaAula : reservasAulas) {
				System.out.println(reservaAula);
			}
		} else {
			System.out.println(aula.getNombre() + " no tiene ninguna reserva a su nombre.");
		}
	}

	// Método listarReservasProfesor
	public void listarReservasProfesor() {
		Consola.mostrarCabecera("Listar reservas profesor");
		Profesor profesor = Consola.leerProfesor();
		List<Reserva> reservasProfesor = Icontrolador.getReservasProfesor(profesor);
		if (reservasProfesor.size() != 0) {
			for (Reserva reservaProfesor : reservasProfesor) {
				System.out.println(reservaProfesor);
			}
		} else {
			System.out.println(profesor.getNombre() + " no tiene ninguna reserva a su nombre.");
		}
	}

	// Método consultarDisponibilidad
	public void consultarDisponibilidad() {
		Consola.mostrarCabecera("Consultar disponibilidad");
		try {
			Permanencia permanencia = Consola.leerPermanencia();
			Aula aulaBuscar = Icontrolador.buscarAula(Consola.leerAula());
			if (aulaBuscar == null) {
				System.out.println("El aula introducida no existe.");
			} else {
				if (Icontrolador.consultarDisponibilidad(aulaBuscar, permanencia)) {
					System.out.println("El aula está disponible.");
				} else {
					System.out.println("El aula ya está reservada.");
				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

}
