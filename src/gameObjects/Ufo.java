package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Assets;
import graphics.Sound;
import math.Vector2D;
import states.GameState;

//Clase Ufo que extiende MovingObject. Representa un objeto enemigo (OVNI) en movimiento,
//que sigue una trayectoria definida y puede disparar láseres al jugador.
public class Ufo extends MovingObject{
	
	private ArrayList<Vector2D> path; // Lista de puntos (nodos) que definen la trayectoria que sigue el Ufo.
	
	private Vector2D currentNode; // Nodo actual hacia el que el Ufo se está desplazando.
	
	private int index; // Índice del nodo actual dentro de la lista de trayectoria.
	
	private boolean following; // Indica si el Ufo sigue la trayectoria o ha terminado de recorrerla.
	
	private long fireRate; // Intervalo entre disparos del Ufo.
	
	private Sound shoot; // Objeto que controla el sonido de disparo del Ufo.
	
	// Constructor que inicializa los atributos del Ufo.
	public Ufo(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture,
			ArrayList<Vector2D> path, GameState gameState) {
		super(position, velocity, maxVel, texture, gameState); // Llama al constructor de la clase base.
		this.path = path; // Asigna la trayectoria.
		index = 0; // Inicia el índice del nodo en 0.
		following = true; // Activa el seguimiento de la trayectoria.
		fireRate = 0; // Inicializa la tasa de disparo en 0.
		shoot = new Sound(Assets.ufoShoot); // Carga el sonido de disparo del Ufo.
	}
	
	// Método para calcular la fuerza de dirección hacia el siguiente nodo de la trayectoria.
	private Vector2D pathFollowing() {
		currentNode = path.get(index); // Obtiene el nodo actual de la lista.

		 // Calcula la distancia al nodo actual.
		double distanceToNode = currentNode.subtract(getCenter()).getMagnitude();
		
		// Si la distancia es menor que el radio definido, pasa al siguiente nodo.
		if(distanceToNode < Constants.NODE_RADIUS) {
			index ++; // Incrementa el índice al siguiente nodo.
			if(index >= path.size()) { // Si no hay más nodos, detiene el seguimiento.
				following = false;
			}
		}
		
		return seekForce(currentNode); // Devuelve la fuerza necesaria para moverse hacia el nodo actual.
		
	}
	
	// Método que calcula la fuerza de "búsqueda" para dirigirse hacia un objetivo específico.
	private Vector2D seekForce(Vector2D target) {
		Vector2D desiredVelocity = target.subtract(getCenter()); // Vector hacia el objetivo.
		desiredVelocity = desiredVelocity.normalize().scale(maxVel); // Normaliza y escala a velocidad máxima.
		return desiredVelocity.subtract(velocity); // Devuelve la fuerza ajustada considerando la velocidad actual.
	}
	
	// Método que actualiza la lógica del Ufo en cada frame.
	@Override
	public void update(float dt) {
		
		fireRate += dt; // Incrementa el temporizador para controlar la frecuencia de disparo.
		
		Vector2D pathFollowing;
		
		// Si el Ufo sigue la trayectoria, calcula la fuerza de seguimiento.
		if(following)
			pathFollowing = pathFollowing();
		else 
			pathFollowing = new Vector2D(); // Si no sigue la trayectoria, no hay fuerza de movimiento.
		
		pathFollowing = pathFollowing.scale(1/Constants.UFO_MASS); // Ajusta la fuerza según la masa del Ufo.
		
		velocity = velocity.add(pathFollowing); // Actualiza la velocidad del Ufo.
		
		velocity = velocity.limit(maxVel); // Limita la velocidad al valor máximo permitido.
		
		position = position.add(velocity); // Actualiza la posición basándose en la velocidad.
		
		// Si el Ufo se sale de la pantalla, destrúyelo.
		if(position.getX() > Constants.WIDTH || position.getY() > Constants.HEIGHT
				|| position.getX() < -width || position.getY() < -height)
			Destroy();
		
		// Lógica de disparo.
		
		if(fireRate > Constants.UFO_FIRE_RATE) {
			
			Vector2D toPlayer = gameState.getPlayer().getCenter().subtract(getCenter()); // Calcula la dirección hacia el jugador.
			
			toPlayer = toPlayer.normalize(); // Normaliza la dirección.
			
			double currentAngle = toPlayer.getAngle(); // Ajusta el ángulo de disparo con un rango aleatorio.
			
			currentAngle += Math.random()*Constants.UFO_ANGLE_RANGE - Constants.UFO_ANGLE_RANGE / 2;
			
			// Corrige el ángulo si el vector apunta hacia la izquierda.
			if(toPlayer.getX() < 0)
				currentAngle = -currentAngle + Math.PI;
			
			toPlayer = toPlayer.setDirection(currentAngle); // Aplica el ángulo ajustado al vector.
		
			// Crea un nuevo objeto Laser y lo añade al juego.
			Laser laser = new Laser(
					getCenter().add(toPlayer.scale(width)), // Posición inicial del láser.
					toPlayer, // Dirección del láser.
					Constants.LASER_VEL, // Velocidad del láser.
					currentAngle + Math.PI/2, // Ángulo del láser.
					Assets.redLaser, // Textura del láser.
					gameState // Estado del juego.
					);
			
			gameState.getMovingObjects().add(0, laser);  // Añade el láser a los objetos en movimiento.
			
			fireRate = 0; // Reinicia el temporizador de disparo.
			
			shoot.play(); // Reproduce el sonido de disparo.
			
		}
		
		 // Si el sonido de disparo ha llegado al final, detén la reproducción.
		if(shoot.getFramePosition() > 8500) {
			shoot.stop();
		}
		
		angle += 0.05; // Incrementa el ángulo del Ufo para que gire ligeramente.
		
		collidesWith(); // Verifica colisiones con otros objetos.
		
	}
	// Método para destruir el Ufo y otorgar puntos al jugador.
	@Override
	public void Destroy() {
		gameState.addScore(Constants.UFO_SCORE, position); // Añade puntaje al jugador.
		gameState.playExplosion(position); // Reproduce la animación de explosión.
		super.Destroy(); // Llama al método Destroy de la clase base.
	}
	
	// Método para dibujar el Ufo en pantalla.
	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		// Configura la transformación para posicionar y rotar el Ufo.
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY()); 
		
		at.rotate(angle, width/2, height/2);
		
		g2d.drawImage(texture, at, null); // Dibuja la textura del Ufo con la transformación aplicada.
		
	}
	
}
