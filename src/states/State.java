package states;

import java.awt.Graphics;

//Clase abstracta que define la estructura base para los diferentes estados del juego.
public abstract class State {
	
	 // Estado actual del juego (se comparte entre todos los estados).
	private static State currentState = null;
	
	// Método estático para obtener el estado actual.
	public static State getCurrentState() {return currentState;} // Devuelve la referencia al estado actual.
	// Método estático para cambiar al nuevo estado.
	public static void changeState(State newState) {
		currentState = newState; // Reemplaza el estado actual por el nuevo.
	}
	
	// Métodos abstractos que deben ser implementados por cada estado concreto
	public abstract void update(float dt); // Lógica de actualización del estado.
	public abstract void draw(Graphics g); // Renderización de los elementos del estado.
	
}
