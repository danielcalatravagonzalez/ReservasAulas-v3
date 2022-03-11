package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;

public interface IProfesores {

	// Método List<Profesor> getProfesores(), coge una copia del método copiaProfunda para evitar aliasing
	List<Profesor> getProfesores();

	// Método getNumProfesores, obtiene tamaño de la coleccion
	int getNumProfesores();

	// Método insertar, valida null, si no es null comprueba si en coleccionProfesores
	// no está metido el profesor y si no está lo añadimos
	void insertar(Profesor profesor) throws OperationNotSupportedException;

	// Método buscar, validamos null, si no es null utilizamos index of para ver si encuentra ese profesor,
	// si no existe saldrá -1 y se devuelve null, si no añadimos ese profesor
	Profesor buscar(Profesor profesor);

	// Método borrar, validamos null, si no es null comprueba si la colección tiene el profesor
	// y si está dentro lo borra
	void borrar(Profesor profesor) throws OperationNotSupportedException;

	// Metodo representar: guarda arrayList en toString pasando por iterador
	List<String> representar();

}