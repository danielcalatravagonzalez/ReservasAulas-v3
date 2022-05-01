package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Modelo implements IModelo {
	// Atributos
	private IAulas aulas;
	private IProfesores profesores;
	private IReservas reservas;

	// Constructor por defecto
	public Modelo(IFuenteDatos fuenteDatos) {
		aulas = fuenteDatos.crearAulas();
		profesores = fuenteDatos.crearProfesores();
		reservas = fuenteDatos.crearReservas();
	}
	
	//Método comenzar
	@Override
	public void comenzar() {
		aulas.comenzar();
		profesores.comenzar();
		reservas.comenzar();
	}
	
	//Método terminar
	@Override
	public void terminar() {
		aulas.terminar();
		profesores.terminar();
		reservas.terminar();
	}

	// Método List<Aula> getAulas
	@Override
	public List<Aula> getAulas(Aula aula) {
		return aulas.getAulas();
	}

	// Método getNumAulas
	@Override
	public int getNumAulas() {
		return aulas.getNumAulas();
	}

	// Método List<String> representarAulas
	@Override
	public List<String> representarAulas() {
		return aulas.representar();
	}

	// Método buscarAula(Aula)
	@Override
	public Aula buscarAula(Aula aula) {
		return aulas.buscar(aula);
	}

	// Método insertarAula(Aula)
	@Override
	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		aulas.insertar(aula);
	}

	// Método borrarAula(Aula)
	@Override
	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		aulas.borrar(aula);
	}

	// Método List<Profesor> getProfesores
	@Override
	public List<Profesor> getProfesores(Profesor profesor) {
		return profesores.getProfesores();
	}

	// Método getNumProfesores
	@Override
	public int getNumProfesores() {
		return profesores.getNumProfesores();
	}

	// Método List<String> representarProfesores
	@Override
	public List<String> representarProfesores() {
		return profesores.representar();
	}

	// Método buscarProfesor(Profesor)
	@Override
	public Profesor buscarProfesor(Profesor profesor) {
		return profesores.buscar(profesor);
	}

	// Método insertarProfesor(Profesor)
	@Override
	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
		profesores.insertar(profesor);
	}

	// Método borrarProfesor(Profesor)
	@Override
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		profesores.borrar(profesor);
	}

	// Método List<Reserva> getReservas
	@Override
	public List<Reserva> getReservas(Reserva reserva) {
		return reservas.getReservas();
	}

	// Método getNumReservas
	@Override
	public int getNumReservas() {
		return reservas.getNumReservas();
	}

	// Método List<String> representarReservas
	@Override
	public List<String> representarReservas() {
		return reservas.representar();
	}

	// Método buscarReserva
	@Override
	public Reserva buscarReserva(Reserva reserva) {
		return reservas.buscar(reserva);
	}

	// Método realizarReserva
	@Override
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		reservas.insertar(reserva);
	}

	// Método anularReserva
	@Override
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		reservas.borrar(reserva);
	}

	// Método List<Reserva> getReservasProfesor(Profesor)
	@Override
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		return reservas.getReservasProfesor(profesor);
	}

	// Método List<Reserva> getReservasAula(Aula)
	@Override
	public List<Reserva> getReservasAula(Aula aula) {
		return reservas.getReservasAula(aula);
	}

	// Método List<Reserva> getReservasPermanencia(Permanencia)
	@Override
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return reservas.getReservasPermanencia(permanencia);
	}

	// Método consultarDisponibilidad(Aula,Permanencia)
	@Override
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		return reservas.consultarDisponibilidad(aula, permanencia);
	}

}
