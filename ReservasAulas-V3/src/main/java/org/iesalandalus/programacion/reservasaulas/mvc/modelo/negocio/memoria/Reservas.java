package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
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
		List<Reserva> copiaProfunda = new ArrayList<>();
		Iterator<Reserva> iterador = listaReservas.iterator();
		while (iterador.hasNext()) {
			copiaProfunda.add(new Reserva(iterador.next()));
		}
		return copiaProfunda;
	}

	// Método List<Reservas> getReservas(), obtiene de la copiaProfunda y hace la comparación con aula y permanencia
	// para luego ordenar las reservas por aula y permanencia
	@Override
	public List<Reserva> getReservas() {
		List<Reserva> reservasOrdenadas = copiaProfundaReservas(coleccionReservas);
		Comparator<Aula> comparadorAula = Comparator.comparing(Aula::getNombre);
		Comparator<Permanencia> comparadorPermanencia = (Permanencia p1, Permanencia p2) -> {
			int comparacionPermanencia = -1;
			if (p1.getDia().equals(p2.getDia())) {
				if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
					comparacionPermanencia = Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(),
							((PermanenciaPorTramo) p2).getTramo().ordinal());
				} else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
					comparacionPermanencia = ((PermanenciaPorHora) p1).getHora().compareTo(((PermanenciaPorHora) p2).getHora());
				}
			} else {
				comparacionPermanencia = p1.getDia().compareTo(p2.getDia());
			}
			return comparacionPermanencia;
		};
		reservasOrdenadas.sort(Comparator.comparing(Reserva::getAula, comparadorAula).thenComparing(Reserva::getPermanencia, comparadorPermanencia));
		return reservasOrdenadas;
	}

	// Método getNumReservas, coge el tamaño de las reservas
	@Override
	public int getNumReservas() {
		return coleccionReservas.size();

	}

	// Método insertar, si no es null primero busca si en coleccionReservas
	// hay alguna coincidencia y si no la añadimos
	@Override
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
		}
		Reserva reservaDia = getReservaAulaDia(reserva.getAula(), reserva.getPermanencia().getDia());
		List<Reserva> reservasProfesor = getReservasProfesorMes(reserva.getProfesor(),reserva.getPermanencia().getDia());
		float puntosGastados = 0;
		for (Reserva r : reservasProfesor) {
			puntosGastados = puntosGastados + r.getPuntos();
		}
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException("ERROR: Sólo se pueden hacer reservas para el mes que viene o posteriores.");
		} else if (puntosGastados + getPuntosGastadosReserva(reserva) > MAX_PUNTOS_PROFESOR_MES) {
			throw new OperationNotSupportedException("ERROR: Esta reserva excede los puntos máximos por mes para dicho profesor.");
		} else if (reservaDia != null) {
			if ((reservaDia.getPermanencia() instanceof PermanenciaPorTramo&& reserva.getPermanencia() instanceof PermanenciaPorHora)|| (reservaDia.getPermanencia() instanceof PermanenciaPorHora && reserva.getPermanencia() instanceof PermanenciaPorTramo)) {
				throw new OperationNotSupportedException("ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este día.");
			}
		} if (!coleccionReservas.contains(reserva)) {
			coleccionReservas.add(new Reserva(reserva));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
		}
	}

	// Método esMesSiguienteOPosterior(Reserva), comprueba si la reserva se hace en 
	// un mes posterior
	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: La reserva no puede ser nula");
		}
		boolean mesSiguienteOPosterior = false;
		Month mes = reserva.getPermanencia().getDia().getMonth();
		Month mesActual = LocalDate.now().getMonth();
		if (mes.getValue() > mesActual.getValue()) {
			mesSiguienteOPosterior = true;
		}
		return mesSiguienteOPosterior;

	}
	
	//Método getPuntosGastadosReserva(Reserva), obtiene puntos gastados de la reserva
	private float getPuntosGastadosReserva(Reserva reserva) {
		return reserva.getPuntos();
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

	// Método buscar, validamos null, si no es null utilizamos index of para ver si encuentra esa reserva,
	// si no existe saldrá -1 y se devuelve null, si no añadimos esa reserva
	@Override
	public Reserva buscar(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
		}
		Reserva reservaEncontrada = null;
		int indice = coleccionReservas.indexOf(reserva);
		if (indice == -1) {
			reservaEncontrada = null;
		} else {
			reservaEncontrada = new Reserva(coleccionReservas.get(indice));
		}
		return reservaEncontrada;
	}

	// Método borrar, validamos null, si no es null con el método buscar comprueba donde está esa reserva
	// y entra al índice para borrarlo
	@Override
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
		} else if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException("ERROR: Sólo se pueden anular reservas para el mes que viene o posteriores.");
		} else if (!coleccionReservas.contains(reserva)) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna reserva igual.");
		} else {
			coleccionReservas.remove(reserva);
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
		List<Reserva> listaProfesor = new ArrayList<>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva comprobar = iterador.next();
			if (profesor.equals(comprobar.getProfesor())) {
				listaProfesor.add(new Reserva(comprobar));
			}
		}
		return listaProfesor;
	}

	// Método List<Reserva> getReservasAula(Aula), obtiene reservas por aula
	@Override
	public List<Reserva> getReservasAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: El aula no puede ser nula.");
		}
		List<Reserva> listaAula = new ArrayList<>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva comprobar = iterador.next();
			if (aula.equals(comprobar.getAula())) {
				listaAula.add(new Reserva(comprobar));
			}
		}
		return listaAula;
	}

	// Método List<Reserva> getReservasPermanencia(Permanencia), obtiene reservas por permanencia
	@Override
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new NullPointerException("ERROR: No se puede reservar con una permanencia nula.");
		}
		List<Reserva> listaPermanencia = new ArrayList<>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva comprobar = iterador.next();
			if (permanencia.equals(comprobar.getPermanencia())) {
				listaPermanencia.add(new Reserva(comprobar));
			}
		}
		return listaPermanencia;
	}

	// Método consultarDisponibilidad(Aula,Permanencia)
	@Override
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		} else if (permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}
		boolean disponibilidad = true;
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva comprobar = iterador.next();
			if (!esMesSiguienteOPosterior(Reserva.getReservaFicticia(aula, permanencia))) {
				disponibilidad = false;
			} else if (aula.equals(comprobar.getAula()) && permanencia.getDia().equals(comprobar.getPermanencia().getDia())) {
				if ((permanencia instanceof PermanenciaPorHora && comprobar.getPermanencia() instanceof PermanenciaPorTramo)|| (permanencia instanceof PermanenciaPorTramo && comprobar.getPermanencia() instanceof PermanenciaPorHora)) {
					disponibilidad = false;
				} else if (permanencia instanceof PermanenciaPorHora && comprobar.getPermanencia() instanceof PermanenciaPorHora) {
					if (((PermanenciaPorHora) permanencia).getHora().equals(((PermanenciaPorHora) comprobar.getPermanencia()).getHora())) {
						disponibilidad = false;
					}
				} else if (permanencia instanceof PermanenciaPorTramo&& comprobar.getPermanencia() instanceof PermanenciaPorTramo) {
					if (((PermanenciaPorTramo) permanencia).getTramo().equals(((PermanenciaPorTramo) comprobar.getPermanencia()).getTramo())) {
						disponibilidad = false;
					}
				}
			}
		}
		return disponibilidad;
	}
}
