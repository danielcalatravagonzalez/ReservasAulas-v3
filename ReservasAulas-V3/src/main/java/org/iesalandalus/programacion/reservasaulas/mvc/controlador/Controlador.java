package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class Controlador implements IControlador {
	//Atributos
	private IModelo Imodelo;
	private IVista Ivista;
	
	//Constructor con parámetros IModelo e IVista
	public Controlador(IModelo modelo, IVista vista) 
	{
		if (modelo == null) {
			throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
		}
		if (vista == null) {
			throw new NullPointerException("ERROR: La vista no puede ser nula.");
		}
		this.Imodelo = modelo;
		this.Ivista = vista;
		this.Ivista.setControlador(this);
	}
	
	//Método comenzar
	@Override
	public void comenzar() {
		Imodelo.comenzar();
		Ivista.comenzar();
	}
	
	//Método terminar
	@Override
	public void terminar() {
		Imodelo.terminar();
		System.out.println("¡Hasta la próxima!");
	}
	
	//Método insertarAula(Aula)
	@Override
	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		Imodelo.insertarAula(aula);
	}
	
	//Método insertarProfesor
	@Override
	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
		Imodelo.insertarProfesor(profesor);
	}
	
	//Método borrarAula
	@Override
	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		Imodelo.borrarAula(aula);
	}
	
	//Método borrarProfesor
	@Override
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		Imodelo.borrarProfesor(profesor);
	}
	
	//Método buscarAula
	@Override
	public Aula buscarAula(Aula aula) {
		Aula aulaBuscada = Imodelo.buscarAula(aula);
		return aulaBuscada;
	}
	
	//Método buscarProfesor
	@Override
	public Profesor buscarProfesor(Profesor profesor) {
		Profesor profesorBuscado = Imodelo.buscarProfesor(profesor);
		return profesorBuscado;
	}
	
	// Método List<String> representarAulas
	@Override
	public List<String> representarAulas() {
		List<String> listaAulas = Imodelo.representarAulas();
		return listaAulas;
	}
	
	// Método List<String> representarProfesores
	@Override
	public List<String> representarProfesores() {
		List<String> listaProfesores = Imodelo.representarProfesores();
		return listaProfesores;
	}
	
	// Método List<String> representarReservas
	@Override
	public List<String> representarReservas() {
		List<String> listaReservas = Imodelo.representarReservas();
		return listaReservas;
	}
	
	//Método realizarReserva
	@Override
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		Imodelo.realizarReserva(reserva);
	}
	
	//Método anularReserva
	@Override
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		Imodelo.anularReserva(reserva);
	}
	
	// Método List<Reserva> getReservasAula(Aula)
	@Override
	public List<Reserva> getReservasAula(Aula aula) {
		List<Reserva> reservasAula = Imodelo.getReservasAula(aula);
		return reservasAula;
	}
	
	// Método List<Reserva> getReservasProfesor(Profesor)
	@Override
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		List<Reserva> reservasProfesor = Imodelo.getReservasProfesor(profesor);
		return reservasProfesor;
	}
	
	// Método List<Reserva> getReservasPermanencia(Permanencia)
	@Override
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		List<Reserva> reservasPermanencia = Imodelo.getReservasPermanencia(permanencia);
		return reservasPermanencia;
	}
	
	// Método consultarDisponibilidad(Aula,Permanencia)
	@Override
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		boolean disponibilidad = Imodelo.consultarDisponibilidad(aula, permanencia);
		return disponibilidad;
	}	
}
