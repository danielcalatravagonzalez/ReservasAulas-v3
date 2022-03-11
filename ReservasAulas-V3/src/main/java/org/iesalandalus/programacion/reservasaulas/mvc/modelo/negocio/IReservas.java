package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public interface IReservas {

	// Método List<Reservas> getReservas(), obtiene de la copiaProfunda y hace la comparación con aula y permanencia
	// para luego ordenar las reservas por aula y permanencia
	List<Reserva> getReservas();

	// Método getNumReservas, coge el tamaño de las reservas
	int getNumReservas();

	// Método insertar, si no es null primero busca si en coleccionReservas
	// hay alguna coincidencia y si no la añadimos
	void insertar(Reserva reserva) throws OperationNotSupportedException;

	// Método buscar, validamos null, si no es null utilizamos index of para ver si encuentra esa reserva,
	// si no existe saldrá -1 y se devuelve null, si no añadimos esa reserva
	Reserva buscar(Reserva reserva);

	// Método borrar, validamos null, si no es null con el método buscar comprueba donde está esa reserva
	// y entra al índice para borrarlo
	void borrar(Reserva reserva) throws OperationNotSupportedException;

	// Metodo representar: guarda arrayList en toString pasando por iterador
	List<String> representar();

	// Método List<Reserva> getReservasProfesor(Profesor), obtiene reservas por profesor
	List<Reserva> getReservasProfesor(Profesor profesor);

	// Método List<Reserva> getReservasAula(Aula), obtiene reservas por aula
	List<Reserva> getReservasAula(Aula aula);

	// Método List<Reserva> getReservasPermanencia(Permanencia), obtiene reservas por permanencia
	List<Reserva> getReservasPermanencia(Permanencia permanencia);

	// Método consultarDisponibilidad(Aula,Permanencia)
	boolean consultarDisponibilidad(Aula aula, Permanencia permanencia);

}