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
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {
	// Atributos
	private List<Profesor> coleccionProfesores;
	private static final String NOMBRE_FICHEROS_PROFESORES = "datos/profesores.dat";

	// Constructor por defecto
	public Profesores() {
		coleccionProfesores = new ArrayList<>();
	}

	// Constructor copia, valida null, si no es null coge el setter
	public Profesores(IProfesores copiaProfesores) {
		if (copiaProfesores == null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		} else {
			setProfesores(copiaProfesores);
		}
	}
	
	//Método comenzar
		@Override
		public void comenzar() {
			leer();
		}
		
		//Método leer
		private void leer() {
			File ficherosProfesores = new File(NOMBRE_FICHEROS_PROFESORES);
			try {ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficherosProfesores));
			Profesor profesor = null;
			do {
				profesor = (Profesor) entrada.readObject();
				insertar(profesor);
			}while(profesor != null);
			entrada.close();
				
			} catch (ClassNotFoundException e)  {
				System.out.println("ERROR: No puedo encontrar la clase que tengo que leer.");	
			} catch (FileNotFoundException e)  {
				System.out.println("ERROR: No puedo abrir el fichero de profesores.");	
			} catch (EOFException e)  {
				System.out.println("Fichero profesores leído satisfactoriamente.");	
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
			File ficherosProfesores = new File(NOMBRE_FICHEROS_PROFESORES);
			try {ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficherosProfesores));
			for (Profesor profesor : coleccionProfesores)
				salida.writeObject(profesor);
			salida.close();
			System.out.println("Fichero profesores escrito satisfactoriamente.");
			} catch (FileNotFoundException e)  {
				System.out.println("ERROR: No puedo abrir el fichero de aulas.");	
			} catch (IOException e)  {
				System.out.println("ERROR inesperado de Entrada/Salida.");	
			}
		}
	
	// Método setProfesores, valida null, si no es null obtiene arraylist por getProfesores
	// a coleccionProfesores
	private void setProfesores(IProfesores profesores) {
		if (profesores == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		} else {
			this.coleccionProfesores = profesores.getProfesores();
		}
	}
	
	// Método copiaProfundaProfesores
	private List<Profesor> copiaProfundaProfesores(List<Profesor> listaProfesores) {
		List<Profesor> copiaProfesores = new ArrayList<Profesor>();
		Iterator<Profesor> iterador = listaProfesores.iterator();

		while (iterador.hasNext()) {
			copiaProfesores.add(new Profesor(iterador.next()));
		}
		Collections.sort(copiaProfesores);
		return copiaProfesores;
	}

	// Método List<Profesor> getProfesores(), coge una copia del método copiaProfunda para evitar aliasing
	@Override
	public List<Profesor> getProfesores() {
		return copiaProfundaProfesores(coleccionProfesores);
	}

	// Método getNumProfesores, obtiene tamaño de la coleccion
	@Override
	public int getNumProfesores() {
		return coleccionProfesores.size();

	}

	// Método insertar, valida null, si no es null comprueba si en coleccionProfesores
	// no está metido el profesor y si no está lo añadimos
	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		} else if (!coleccionProfesores.contains(profesor)) {
			coleccionProfesores.add(new Profesor(profesor));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese correo.");
		}
	}

	// Método buscar
	@Override
	public Profesor buscar(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		}
		Iterator<Profesor> iterador = coleccionProfesores.iterator();

		while (iterador.hasNext()) {
			Profesor profesorBuscado = iterador.next();
			if (profesor.equals(profesorBuscado)) {
				return new Profesor(profesorBuscado);
			}
		}
		return null;
	}

	// Método borrar, validamos null, si no es null comprueba si la colección tiene el profesor
	// y si está dentro lo borra
	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		} else if (!coleccionProfesores.contains(profesor)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese correo.");
		} else {
			coleccionProfesores.remove(profesor);
		}
	}

	// Metodo representar: guarda arrayList en toString pasando por iterador
	@Override
	public List<String> representar() {
		List<String> representacion = new ArrayList<String>();
		Iterator<Profesor> iterador = coleccionProfesores.iterator();
		while (iterador.hasNext()) {
			representacion.add(iterador.next().toString());
		}
		return representacion;
	}
}
