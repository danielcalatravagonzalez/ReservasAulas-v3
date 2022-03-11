package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public interface IModelo {

	// Método List<Aula> getAulas
	List<Aula> getAulas(Aula aula);

	// Método getNumAulas
	int getNumAulas();

	// Método List<String> representarAulas
	List<String> representarAulas();

	// Método buscarAula(Aula)
	Aula buscarAula(Aula aula);

	// Método insertarAula(Aula)
	void insertarAula(Aula aula) throws OperationNotSupportedException;

	// Método borrarAula(Aula)
	void borrarAula(Aula aula) throws OperationNotSupportedException;

	// Método List<Profesor> getProfesores
	List<Profesor> getProfesores(Profesor profesor);

	// Método getNumProfesores
	int getNumProfesores();

	// Método List<String> representarProfesores
	List<String> representarProfesores();

	// Método buscarProfesor(Profesor)
	Profesor buscarProfesor(Profesor profesor);

	// Método insertarProfesor(Profesor)
	void insertarProfesor(Profesor profesor) throws OperationNotSupportedException;

	// Método borrarProfesor(Profesor)
	void borrarProfesor(Profesor profesor) throws OperationNotSupportedException;

	// Método List<Reserva> getReservas
	List<Reserva> getReservas(Reserva reserva);

	// Método getNumReservas
	int getNumReservas();

	// Método List<String> representarReservas
	List<String> representarReservas();

	// Método buscarReserva
	Reserva buscarReserva(Reserva reserva);

	// Método realizarReserva
	void realizarReserva(Reserva reserva) throws OperationNotSupportedException;

	// Método anularReserva
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