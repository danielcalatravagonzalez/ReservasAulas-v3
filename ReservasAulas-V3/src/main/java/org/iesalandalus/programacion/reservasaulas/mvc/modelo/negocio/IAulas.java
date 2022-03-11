package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;

public interface IAulas {

	// Método List<Aula> getAulas(), coge una copia del método copiaProfunda para evitar aliasing, creamos
	// una variable para ordenar las aulas de coleccionAulas por nombre, por último devolvemos
	// la variable
	List<Aula> getAulas();

	// Método getNumAulas, obtiene tamaño de la coleccion
	int getNumAulas();

	// Método insertar, valida null, si no es null comprueba si en coleccionAulas no está
	// metida el aula y si no está la añadimos
	void insertar(Aula aula) throws OperationNotSupportedException;

	// Método buscar, validamos null, si no es null utilizamos index of para ver si encuentra esa aula,
	// si no existe saldrá -1 y se devuelve null, si no añadimos esa aula
	Aula buscar(Aula aula);

	// Método borrar, validamos null, si no es null comprueba si la colección tiene el aula
	// y si está dentro la borra
	void borrar(Aula aula) throws OperationNotSupportedException;

	// Metodo representar: guarda arrayList en toString pasando por iterador
	List<String> representar();

}