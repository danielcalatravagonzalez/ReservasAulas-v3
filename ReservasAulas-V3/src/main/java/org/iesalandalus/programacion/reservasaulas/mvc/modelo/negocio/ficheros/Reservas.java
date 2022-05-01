package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Reservas implements IReservas {
	// Atributos
	private static final float MAX_PUNTOS_PROFESOR_MES = 200.0f;
	private List<Reserva> coleccionReservas;
	private static final String NOMBRE_FICHEROS_RESERVAS = "datos/reservas.dat";

	// Constructor por defecto
	public Reservas() {
		coleccionReservas = new ArrayList<>();
	}

	// Constructor copia, valida null, si no es null coge el setter
	public Reservas(IReservas copiaReservas) {
		if (copiaReservas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		} else {
			setReservas(copiaReservas);
		}
	}
	
	//Método comenzar
			@Override
			public void comenzar() {
				leer();
			}
			
			//Método leer
			private void leer() {
				File ficherosReservas = new File(NOMBRE_FICHEROS_RESERVAS);
				try { ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficherosReservas));
				Reserva reserva = null;
				do {
					reserva = (Reserva) entrada.readObject();
					insertar(reserva);
				}while(reserva != null);
				entrada.close();
					
				} catch (ClassNotFoundException e)  {
					System.out.println("ERROR: No puedo encontrar la clase que tengo que leer.");	
				} catch (FileNotFoundException e)  {
					System.out.println("ERROR: No puedo abrir el fichero de reservas.");	
				} catch (EOFException e)  {
					System.out.println("Fichero reservas leído satisfactoriamente.");	
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
				File ficherosReservas = new File(NOMBRE_FICHEROS_RESERVAS);
				try { ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficherosReservas));
				for (Reserva reserva : coleccionReservas)
					salida.writeObject(reserva);
				salida.close();
				System.out.println("Fichero reservas escrito satisfactoriamente.");
				} catch (FileNotFoundException e)  {
					System.out.println("ERROR: No puedo abrir el fichero de aulas.");	
				} catch (IOException e)  {
					System.out.println("ERROR inesperado de Entrada/Salida.");	
				}
			}

	// Método setReservas, valida null, si no es null obtiene arraylist por
	// getReservas a coleccionReservas
	private void setReservas(IReservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se puede copiar una reserva nula.");
		} else {
			this.coleccionReservas = reservas.getReservas();
		}
	}

	// Método copiaProfundaReservas, en este caso utilizamos el comparator en la
	// copiaProfunda para ordenar las reservas por Aula y Permanencia
	private List<Reserva> copiaProfundaReservas(List<Reserva> listaReservas) {
		List<Reserva> copiaReservas = new ArrayList<Reserva>();
		Iterator<Reserva> iterador = listaReservas.iterator();

		while (iterador.hasNext()) {
			copiaReservas.add(new Reserva(iterador.next()));
		}
		Collections.sort(copiaReservas);
		return copiaReservas;
	}

	// Método List<Reservas> getReservas(), obtiene de la copiaProfunda 
	@Override
	public List<Reserva> getReservas() {
		return copiaProfundaReservas(coleccionReservas);
	}

	// Método getNumReservas, coge el tamaño de las reservas
	@Override
	public int getNumReservas() {
		return coleccionReservas.size();

	}

	// Método insertar
	@Override
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
		}
		Reserva reservaExistente = getReservaAulaDia(reserva.getAula(), reserva.getPermanencia().getDia());
		if (reservaExistente != null) {
			if (reservaExistente.getPermanencia() instanceof PermanenciaPorTramo && reserva.getPermanencia() instanceof PermanenciaPorHora) {
				throw new OperationNotSupportedException("ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este día.");
			}
			if (reservaExistente.getPermanencia() instanceof PermanenciaPorHora
					&& reserva.getPermanencia() instanceof PermanenciaPorTramo) {
				throw new OperationNotSupportedException("ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este día.");
			}
		}
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException("ERROR: Sólo se pueden hacer reservas para el mes que viene o posteriores.");
		}
		if ((getPuntosGastadosReserva(reserva) + reserva.getPuntos()) > MAX_PUNTOS_PROFESOR_MES) {
			throw new OperationNotSupportedException("ERROR: Esta reserva excede los puntos máximos por mes para dicho profesor.");
		}
		if (coleccionReservas.contains(reserva)) {
			throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
		} else {
			coleccionReservas.add(new Reserva(reserva));
		}

	}

	// Método esMesSiguienteOPosterior(Reserva), comprueba si la reserva se hace en 
	// un mes posterior
	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: La reserva no puede ser nula");
		}
		boolean mesSiguienteOPosterior = false;
		if (reserva.getPermanencia().getDia().compareTo(LocalDate.now().plusMonths(1).withDayOfMonth(1)) != -1) {
			mesSiguienteOPosterior = true;
		}
		return mesSiguienteOPosterior;


	}
	
	//Método getPuntosGastadosReserva(Reserva), obtiene puntos gastados de la reserva
	private float getPuntosGastadosReserva(Reserva reserva) {
		List<Reserva> listadoReservasProfesor = getReservasProfesorMes(reserva.getProfesor(),
				reserva.getPermanencia().getDia());

		float sumaPuntosTotales = 0;

		Iterator<Reserva> iterador = listadoReservasProfesor.iterator();

		while (iterador.hasNext()) {
			sumaPuntosTotales = sumaPuntosTotales + iterador.next().getPuntos();
		}

		return sumaPuntosTotales;
	}
	

	// Método List<Reserva> getReservasProfesorMes(Profesor, LocalDate)
	// validamos null, si no es null se obtiene las reservas del profesor en un mes determinado
	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate fecha) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo");
		} else if (fecha == null) {
			throw new NullPointerException("ERROR: La fecha no puede ser nula");
		}
		List<Reserva> reservasMes = new ArrayList<>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva comprobar = iterador.next();
			Month mesLista = comprobar.getPermanencia().getDia().getMonth();
			Month mesFecha = fecha.getMonth();
			if (profesor.equals(comprobar.getProfesor()) && mesLista.getValue() == mesFecha.getValue()) {
				reservasMes.add(new Reserva(comprobar));
			}
		}
		return reservasMes;
	}

	// Método Reserva getReservaAulaDia(Aula, LocalDate)
	// validamos null, si no es null se obtiene las reservas en un dia determinado
	private Reserva getReservaAulaDia(Aula aula, LocalDate fecha) {
		if (aula == null) {
			throw new NullPointerException("ERROR: El aula no puede ser nula");
		} else if (fecha == null) {
			throw new NullPointerException("ERROR: La fecha no puede ser nula");
		}
		Reserva reservaDia = null;
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva comprobar = iterador.next();
			if (aula.equals(comprobar.getAula()) && fecha.equals(comprobar.getPermanencia().getDia())) {
				reservaDia = new Reserva(comprobar);
			}
		}
		return reservaDia;
	}

	// Método buscar
	@Override
	public Reserva buscar(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
		}

		Iterator<Reserva> iterador = coleccionReservas.iterator();

		while (iterador.hasNext()) {
			Reserva reservaBuscada = iterador.next();
			if (reserva.equals(reservaBuscada)) {
				return new Reserva(reservaBuscada);
			}
		}
		return null;
	}

	// Método borrar, validamos null, si no es null con el método buscar comprueba donde está esa reserva
	// y entra al índice para borrarlo
	@Override
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
		}
		if (esMesSiguienteOPosterior(reserva)) {
			if (coleccionReservas.contains(reserva)) {
				coleccionReservas.remove(reserva);
			} else {
				throw new OperationNotSupportedException("ERROR: No existe ninguna reserva igual.");
			}
		} else {
			throw new OperationNotSupportedException("ERROR: Sólo se pueden anular reservas para el mes que viene o posteriores.");
		}

	}

	// Metodo representar: guarda arrayList en toString pasando por iterador
	@Override
	public List<String> representar() {
		List<String> representacion = new ArrayList<String>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			representacion.add(iterador.next().toString());
		}
		return representacion;
	}

	// Método List<Reserva> getReservasProfesor(Profesor), obtiene reservas por profesor
	@Override
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		List<Reserva> reservasProfesor = new ArrayList<Reserva>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva reserva = iterador.next();
			if (profesor.equals(reserva.getProfesor())) {
				reservasProfesor.add(new Reserva(reserva));
			}
		}
		Collections.sort(reservasProfesor);
		return reservasProfesor;
	}

	// Método List<Reserva> getReservasAula(Aula), obtiene reservas por aula
	@Override
	public List<Reserva> getReservasAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: El aula no puede ser nula.");
		}
		List<Reserva> reservasAula = new ArrayList<Reserva>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva reserva = iterador.next();
			if (aula.equals(reserva.getAula())) {
				reservasAula.add(new Reserva(reserva));
			}
		}
		Collections.sort(reservasAula);
		return reservasAula;
	}

	// Método List<Reserva> getReservasPermanencia(Permanencia), obtiene reservas por permanencia
	@Override
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new NullPointerException("ERROR: La permanencia no puede ser nula.");
		}
		List<Reserva> reservasPermanencia = new ArrayList<Reserva>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva reserva = iterador.next();
			if (permanencia.equals(reserva.getPermanencia())) {
				reservasPermanencia.add(new Reserva(reserva));
			}
		}
		Collections.sort(reservasPermanencia);
		return reservasPermanencia;
	}

	// Método consultarDisponibilidad(Aula,Permanencia)
	@Override
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		}

		if (permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}

		boolean consulta = true;

		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva auxiliar = iterador.next();
			if (permanencia.equals(auxiliar.getPermanencia()) && aula.equals(auxiliar.getAula())) {
				consulta = false;
			}
		}

		return consulta;
	}
}
