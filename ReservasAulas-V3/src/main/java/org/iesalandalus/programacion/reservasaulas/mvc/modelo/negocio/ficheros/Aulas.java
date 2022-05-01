package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

public class Aulas implements IAulas {
	// Atributos
	private List<Aula> coleccionAulas;
	private static final String NOMBRE_FICHEROS_AULAS = "datos/aulas.dat";

	// Constructor por defecto
	public Aulas() {
		coleccionAulas = new ArrayList<>();
	}

	// Constructor copia, valida null, si no es null coge el setter
	public Aulas(IAulas copiaAulas) {
		if (copiaAulas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		} else {
			setAulas(copiaAulas);
		}
	}
	
	
	//Método comenzar
	@Override
	public void comenzar() {
		leer();
	}
	
	//Método leer
	private void leer() {
		File ficherosAulas = new File(NOMBRE_FICHEROS_AULAS);
		try {ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficherosAulas));
		Aula aula = null;
		do {
			aula = (Aula) entrada.readObject();
			insertar(aula);
		}while(aula != null);
		entrada.close();
			
		} catch (ClassNotFoundException e)  {
			System.out.println("ERROR: No puedo encontrar la clase que tengo que leer.");	
		} catch (FileNotFoundException e)  {
			System.out.println("ERROR: No puedo abrir el fichero de aulas.");	
		} catch (EOFException e)  {
			System.out.println("Fichero aulas leído satisfactoriamente.");	
		} catch (IOException e)  {
			System.out.println("ERROR inesperado de Entrada/Salida.");	
		} catch (OperationNotSupportedException e)  {
			System.out.println(e.getMessage());	
		}
	}
	
	//Método terminar
	@Override
	public void terminar() {
		escribir();
	}
	
	//Método escribir
	private void escribir() {
		File ficherosAulas = new File(NOMBRE_FICHEROS_AULAS);
		try {ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficherosAulas));
		for (Aula aula : coleccionAulas)
			salida.writeObject(aula);
		salida.close();
		System.out.println("Fichero aulas escrito satisfactoriamente.");
		} catch (FileNotFoundException e)  {
			System.out.println("ERROR: No puedo abrir el fichero de aulas.");	
		} catch (IOException e)  {
			System.out.println("ERROR inesperado de Entrada/Salida.");	
		}
	}

	// Método setAulas(Aulas), valida null, si no es null obtiene arraylist por getAulas a coleccionAulas
	private void setAulas(IAulas aulas) {
		if (aulas == null) {
			throw new NullPointerException("ERROR: No se puede copiar un aula nula.");
		} else {
			coleccionAulas = aulas.getAulas();
		}
	}

	// Método List<Aula> getAulas(), coge una copia del método copiaProfunda para evitar aliasing
	@Override
	public List<Aula> getAulas() {
		return copiaProfundaAulas(coleccionAulas);
	}

	// Método copiaProfundaAulas
	private List<Aula> copiaProfundaAulas(List<Aula> listaAulas) {
		List<Aula> copiaAulas = new ArrayList<Aula>();
		Iterator<Aula> iterador = listaAulas.iterator();
		while (iterador.hasNext()) {
			copiaAulas.add(new Aula(iterador.next()));
		}
		Collections.sort(copiaAulas);
		return copiaAulas;
	}

	// Método getNumAulas, obtiene tamaño de la coleccion
	@Override
	public int getNumAulas() {
		return coleccionAulas.size();

	}

	// Método insertar, valida null, si no es null comprueba si en coleccionAulas no está
	// metida el aula y si no está la añadimos
	@Override
	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
		} else if (!coleccionAulas.contains(aula)) {
			coleccionAulas.add(new Aula(aula));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un aula con ese nombre.");
		}
	}

	// Método buscar
	@Override
	public Aula buscar(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
		}
		Iterator<Aula> iterador = coleccionAulas.iterator();
		while (iterador.hasNext()) {
			Aula aulaBuscada = iterador.next();
			if (aula.equals(aulaBuscada)) {
				return new Aula(aulaBuscada);
			}
		}
		return null;
	}

	// Método borrar, validamos null, si no es null comprueba si la colección tiene el aula
	// y si está dentro la borra
	@Override
	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		} else if (!coleccionAulas.contains(aula)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
		} else {
			coleccionAulas.remove(aula);
		}
	}

	// Metodo representar: guarda arrayList en toString pasando por iterador
	@Override
	public List<String> representar() {
		List<String> representacion = new ArrayList<String>();
		Iterator<Aula> iterador = coleccionAulas.iterator();
		while (iterador.hasNext()) {
			representacion.add(iterador.next().toString());
		}
		return representacion;
	}
}
