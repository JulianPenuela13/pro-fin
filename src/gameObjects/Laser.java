package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import math.Vector2D;
import states.GameState;

//Clase Laser que representa un láser disparado en el juego, extiende MovingObject
public class Laser extends MovingObject{

	// Constructor para inicializar un láser con su posición, velocidad, ángulo y textura
	public Laser(Vector2D position, Vector2D velocity, double maxVel, double angle, BufferedImage texture, GameState gameState) {
		super(position, velocity, maxVel, texture, gameState); // Llama al constructor de la clase padre (MovingObject)
		this.angle = angle; // Define el ángulo inicial de dirección del láser
		this.velocity = velocity.scale(maxVel); // Escala la velocidad del láser a su velocidad máxima
	}

	// Método que actualiza la posición y estado del láser en cada frame
	@Override
	public void update(float dt) {
		position = position.add(velocity); // Actualiza la posición del láser sumando la velocidad
		// Verifica si el láser sale de los límites de la pantalla
		if(position.getX() < 0 || position.getX() > Constants.WIDTH ||
				position.getY() < 0 || position.getY() > Constants.HEIGHT){
			Destroy(); // Destruye el láser si está fuera de los límites
		}
		
		collidesWith(); // Comprueba colisiones con otros objetos en el juego
		
	}

	// Método para dibujar el láser en la pantalla
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g; // Convierte el contexto gráfico a Graphics2D
		
		at = AffineTransform.getTranslateInstance(position.getX() - width/2, position.getY()); // Configura una transformación afín para posicionar y rotar el láser
		
		at.rotate(angle, width/2, 0); // Aplica rotación según el ángulo del láser
		
		g2d.drawImage(texture, at, null); // Dibuja la textura del láser en la pantalla usando la transformación
		
	}
	
	// Método para obtener el centro del láser (utilizado para colisiones u otras lógicas)
	@Override
	public Vector2D getCenter(){
		return new Vector2D(position.getX() + width/2, position.getY() + width/2); // Calcula y retorna un nuevo vector representando el centro del láser
	}
	
}
