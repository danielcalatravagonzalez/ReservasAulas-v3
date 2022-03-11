package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;

public interface IVista {

	// Método setControlador
	void setControlador(IControlador controlador);

	// Método comenzar
	void comenzar();

	// Método salir
	void salir();

}