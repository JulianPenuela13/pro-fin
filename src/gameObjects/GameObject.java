package gameObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import math.Vector2D;

//Clase abstracta que sirve como base para todos los objetos del juego
public abstract class GameObject {
	
	protected BufferedImage texture; // Textura gráfica del objeto (imagen que representa al objeto en pantalla)
	protected Vector2D position; // Posición del objeto en el espacio del juego, representada por un vector 2D
	
	// Constructor para inicializar un GameObject con una posición y textura
	public GameObject(Vector2D position, BufferedImage texture)
	{
		this.position = position; // Asigna la posición inicial del objeto
		this.texture = texture; // Asigna la textura inicial del objeto
	}
	
	// Método abstracto para actualizar el estado del objeto
	// `dt` representa el delta time, que es el tiempo transcurrido desde el último frame
	public abstract void update(float dt);
	
	// Método abstracto para dibujar el objeto en la pantalla
	// `g` es el contexto gráfico usado para renderizar el objeto
	public abstract void draw(Graphics g); 

	// Getter para obtener la posición actual del objeto
	public Vector2D getPosition() {
		return position;
	}

	// Setter para cambiar la posición del objeto
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
}
