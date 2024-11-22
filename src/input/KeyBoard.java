package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Clase KeyBoard: Implementa KeyListener para manejar la entrada del teclado en el juego
public class KeyBoard implements KeyListener{
	
	 // Array de booleanos que almacena el estado (presionado o no) de todas las teclas
	private boolean[] keys = new boolean[256]; // Soporta hasta 256 códigos de teclas
	
	public static boolean UP, LEFT, RIGHT, SHOOT; // Variables públicas estáticas para representar teclas específicas del juego
	
	// Constructor: inicializa las variables estáticas como falsas (ninguna tecla está presionada inicialmente
	public KeyBoard()
	{
		UP = false;
		LEFT = false;
		RIGHT = false;
		SHOOT = false;
	}
	
	// Método update: actualiza el estado de las teclas relevantes en función del array keys
	public void update()
	{
		UP = keys[KeyEvent.VK_UP]; // Tecla de flecha hacia arriba
		LEFT = keys[KeyEvent.VK_LEFT]; // Tecla de flecha hacia la izquierda
		RIGHT = keys[KeyEvent.VK_RIGHT]; // Tecla de flecha hacia la derecha
		SHOOT = keys[KeyEvent.VK_SPACE]; // Tecla espacio para disparar
	}
	
	// Método keyPressed: se activa cuando una tecla es presionada
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true; // Marca la tecla como presionada
	}

	// Método keyReleased: se activa cuando una tecla es soltada
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false; // Marca la tecla como no presionada
	}
	
	// Método keyTyped: no se utiliza en esta implementación, pero debe estar presente debido a la interfaz KeyListener
	@Override
	public void keyTyped(KeyEvent e) {}   // Este método está vacío porque no necesitamos manejar eventos de tipeo de teclas
	
}
