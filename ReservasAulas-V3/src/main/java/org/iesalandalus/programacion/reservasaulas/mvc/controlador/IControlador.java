package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public interface IControlador {

	// Método comenzar
	void comenzar();

	// Método terminar
	void terminar();

	// Método insertarAula
	void insertarAula(Aula aula) throws OperationNotSupportedException;

	// Método insertarProfesor
	void insertarProfesor(Profesor profesor) throws OperationNotSupportedException;

	// Método borrarAula
	void borrarAula(Aula aula) throws OperationNotSupportedException;

	// Método borrarProfesor
	void borrarProfesor(Profesor profesor) throws OperationNotSupportedException;

	// Método buscarAula(Aula)
	Aula buscarAula(Aula aula);

	// Método buscarProfesor
	Profesor buscarProfesor(Profesor profesor);

	// Método List<String> representarAulas
	List<String> representarAulas();

	// Método List<String> representarProfesores
	List<String> representarProfesores();

	// Método List<String> representarReservas
	List<String> representarReservas();

	// Método realizarReservas
	void realizarReserva(Reserva reserva) throws OperationNotSupportedException;

	// Método anularReservas
	void anularReserva(Reserva reserva) throws OperationNotSupportedException;

	// Método List<Reserva> getReservasProfesor(Profesor)
	List<Reserva> getReservasProfesor(Profesor profesor);

	// Método List<Reserva> getReservasAula(Aula)
	List<Reserva> getReservasAula(Aula aula);

	// Método List<Reserva> getReservasPermanencia(Permanencia)
	List<Reserva> getReservasPermanencia(Permanencia permanencia);

	// Método consultarDisponibilidad(Aula,Permanencia)
	boolean consultarDisponibilidad(Aula aula, Permanencia permanencia);

}