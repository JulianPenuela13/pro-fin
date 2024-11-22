package graphics;

import java.awt.image.BufferedImage;

import math.Vector2D;

//Clase Animation: Maneja la animación de un objeto mediante un arreglo de imágenes (frames)
public class Animation {
	
	private BufferedImage[] frames; // Arreglo de imágenes que conforman los frames de la animación
	private int velocity; // Velocidad de cambio entre frames, en milisegundos
	private int index;  // Índice del frame actual en el arreglo
	private boolean running; // Estado de la animación: true si está en ejecución, false si terminó
	private Vector2D position; // Posición de la animación en el espacio (coordenadas)
	private long time; // Tiempo acumulado para gestionar el cambio de frames
	
	// Constructor de la clase Animation: inicializa los frames, la velocidad, y la posición
	public Animation(BufferedImage[] frames, int velocity, Vector2D position){
		this.frames = frames; // Asigna los frames de la animación
		this.velocity = velocity; // Asigna la velocidad de la animación
		this.position = position; // Asigna la posición de la animación
		index = 0; // Inicia el índice en el primer frame
		running = true; // La animación comienza activa
	} 
	
	 // Método update: actualiza la animación en función del tiempo transcurrido (dt)
	public void update(float dt){
		
		time += dt; // Incrementa el tiempo acumulado con el delta time recibido
		
		// Si el tiempo acumulado excede la velocidad, cambia al siguiente frame
		if(time > velocity){
			time  = 0; // Reinicia el tiempo acumulado
			index ++; // Avanza al siguiente frame
			
			// Si se alcanza el último frame, detiene la animación y reinicia el índice
			if(index >= frames.length){
				running = false; // La animación se detiene
				index = 0; // Reinicia el índice para volver al inicio
			}
		}
	}

	// Método isRunning: retorna si la animación está activa o no
	public boolean isRunning() {
		return running;
	}

	// Método getPosition: retorna la posición actual de la animación
	public Vector2D getPosition() {
		return position;
	}
	
	// Método getCurrentFrame: retorna el frame actual que debe mostrarse
	public BufferedImage getCurrentFrame(){
		return frames[index];
	}
	
	
	
	
	
}
