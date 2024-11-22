package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import math.Vector2D;
import states.GameState;

//Clase Meteor: representa un meteoro en el juego, extiende la clase MovingObject
public class Meteor extends MovingObject{

	private Size size;	// Tamaño del meteoro (puede ser Small, Medium, Large.)
	
	// Constructor: inicializa el meteoro con posición, velocidad, textura y tamaño
	public Meteor(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState, Size size) {
		super(position, velocity, maxVel, texture, gameState); // Llama al constructor de la clase padre (MovingObject)
		this.size = size; // Asigna el tamaño del meteoro
		this.velocity = velocity.scale(maxVel); // Escala la velocidad inicial del meteoro a su velocidad máxima
		
	}

	// Actualiza el estado del meteoro en cada frame
	@Override
	public void update(float dt) {
		
		Vector2D playerPos = new Vector2D(gameState.getPlayer().getCenter()); // Obtiene la posición del jugador
		
		int distanceToPlayer = (int) playerPos.subtract(getCenter()).getMagnitude();// Calcula la distancia entre el meteoro y el jugador
		
		// Si el meteoro está cerca del jugador y el escudo del jugador está activado
		if(distanceToPlayer < Constants.SHIELD_DISTANCE / 2 + width / 2) {
			
			// Aplica una fuerza para alejarse del jugador
			if(gameState.getPlayer().isShieldOn()) {
				Vector2D fleeForce = fleeForce();
				velocity = velocity.add(fleeForce);
			}
			

		}
		
		// Limita la velocidad del meteoro si supera su velocidad máxima
		if(velocity.getMagnitude() >= this.maxVel) {
			// Invierte y normaliza la velocidad para desacelerar ligeramente
			Vector2D reversedVelocity = new Vector2D(-velocity.getX(), -velocity.getY());
			velocity = velocity.add(reversedVelocity.normalize().scale(0.01f));
		}
		
		velocity = velocity.limit(Constants.METEOR_MAX_VEL); // Limita la velocidad del meteoro según la constante máxima permitida
		
		position = position.add(velocity); // Actualiza la posición del meteoro
		
		// Maneja el movimiento continuo (wrap-around) cuando el meteoro sale de los bordes de la pantalla
		if(position.getX() > Constants.WIDTH)
			position.setX(-width);
		if(position.getY() > Constants.HEIGHT)
			position.setY(-height);
		
		if(position.getX() < -width)
			position.setX(Constants.WIDTH);
		if(position.getY() < -height)
			position.setY(Constants.HEIGHT);
		
		angle += Constants.DELTAANGLE/2; // Incrementa el ángulo del meteoro para rotarlo ligeramente
		
	}
	
	// Calcula la fuerza para alejarse del jugador cuando el escudo está activado
	private Vector2D fleeForce() {
		Vector2D desiredVelocity = gameState.getPlayer().getCenter().subtract(getCenter()); // Calcula la dirección desde el meteoro hacia el jugador
		desiredVelocity = (desiredVelocity.normalize()).scale(Constants.METEOR_MAX_VEL); // Normaliza y escala la velocidad deseada al máximo permitido
		Vector2D v = new Vector2D(velocity); // Calcula la fuerza de evasión como la diferencia entre la velocidad actual y la deseada
		return v.subtract(desiredVelocity);
	}
	
	// Destruye el meteoro, generando sub-meteoros, efectos y puntuación
	@Override
	public void Destroy(){
		gameState.divideMeteor(this); // Divide el meteoro en partes más pequeñas si es necesario
		gameState.playExplosion(position); // Reproduce una animación de explosión en la posición del meteoro
		gameState.addScore(Constants.METEOR_SCORE, position); // Añade puntos al puntaje del jugador
		super.Destroy(); // Llama al método de destrucción de la clase padre
	}
	
	// Dibuja el meteoro en pantalla con rotación y posición actualizadas
	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY()); // Configura una transformación afín para posicionar y rotar el meteoro
		
		at.rotate(angle, width/2, height/2); // Aplica la rotación alrededor del centro del meteoro
		
		g2d.drawImage(texture, at, null); // Dibuja la textura del meteoro con la transformación aplicada
		
	}

	// Devuelve el tamaño del meteoro
	public Size getSize(){
		return size;
	}
}
