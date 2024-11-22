package gameObjects;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Assets;
import graphics.Sound;
import math.Vector2D;
import states.GameState;

//Clase abstracta MovingObject: representa cualquier objeto que se mueve en el juego.
//Hereda de GameObject y añade comportamiento relacionado con el movimiento y colisiones.
public abstract class MovingObject extends GameObject{
	
	// Propiedades del movimiento
	protected Vector2D velocity; // Velocidad del objeto (dirección y magnitud)
	protected AffineTransform at; // Transformación afín para rotación y posición
	protected double angle; // Ángulo de rotación del objeto
	protected double maxVel; // Velocidad máxima permitida
	// Dimensiones del objeto
	protected int width; // Ancho del objeto
	protected int height; // Alto del objeto
	protected GameState gameState; // Referencia al estado del juego (para acceder a otros objetos y elementos)
	
	private Sound explosion; // Sonido de explosión
	
	protected boolean Dead; // Indica si el objeto está "muerto" (debe ser eliminado del juego)
	
	// Constructor: inicializa las propiedades del objeto en movimiento
	public MovingObject(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
		super(position, texture); // Llama al constructor de GameObject
		this.velocity = velocity; // Asigna la velocidad inicial
		this.maxVel = maxVel; // Asigna la velocidad máxima
		this.gameState = gameState; // Asigna el estado del juego
		width = texture.getWidth(); // Calcula el ancho del objeto a partir de la textura
		height = texture.getHeight();  // Calcula la altura del objeto a partir de la textura
		angle = 0; // Inicializa el ángulo de rotación
		explosion = new Sound(Assets.explosion); // Asocia el sonido de explosión
		Dead = false;  // Inicializa el estado como "vivo"
	}
	
	// Método para detectar colisiones con otros objetos en movimiento
	protected void collidesWith(){
		
		ArrayList<MovingObject> movingObjects = gameState.getMovingObjects(); // Obtiene la lista de objetos en movimiento desde el estado del juego
		
		// Itera sobre todos los objetos en movimiento
		for(int i = 0; i < movingObjects.size(); i++){
			
			MovingObject m  = movingObjects.get(i);
			
			// Ignora colisiones consigo mismo
			if(m.equals(this))
				continue;
			
			double distance = m.getCenter().subtract(getCenter()).getMagnitude(); // Calcula la distancia entre este objeto y otro objeto
			
			// Si están lo suficientemente cerca y ambos están "vivos", ocurre una colisión
			if(distance < m.width/2 + width/2 && movingObjects.contains(this) && !m.Dead && !Dead){
				objectCollision(this, m);
			}
		}
	}
	
	// Maneja las interacciones específicas entre dos objetos que colisionan
	private void objectCollision(MovingObject a, MovingObject b) {
		
		Player p = null;
		
		// Determina si alguno de los objetos es un jugador
		if(a instanceof Player)
			p = (Player)a;
		else if(b instanceof Player)
			p = (Player)b;
		
		// Si el jugador está en su estado de invulnerabilidad ("spawning"), no hace nada
		if(p != null && p.isSpawning()) 
			return;
		
		// Si ninguno de los objetos es un PowerUp, ambos se destruyen
		if(a instanceof Meteor && b instanceof Meteor)
			return;
		
		// Si uno de los objetos es un PowerUp y el otro es un jugador, ejecuta el efecto
		if(!(a instanceof PowerUp || b instanceof PowerUp)){
			a.Destroy();
			b.Destroy();
			return;
		}
		
		// Si uno de los objetos es un PowerUp y el otro es un jugador, ejecuta el efecto
		if(p != null){
			if(a instanceof Player){
				((PowerUp)b).executeAction(); // Activa el PowerUp
				b.Destroy(); // Destruye el PowerUp
			}else if(b instanceof Player){
				((PowerUp)a).executeAction();  // Activa el PowerUp
				a.Destroy(); // Destruye el PowerUp
			}
		}
		
	}

	// Destruye este objeto (marca como "muerto" y reproduce un sonido si aplica)
	protected void Destroy(){
		Dead = true;  // Marca el objeto como "muerto"
		
		// Reproduce el sonido de explosión si no es un láser ni un PowerUp
		if(!(this instanceof Laser) && !(this instanceof PowerUp))
			explosion.play();
	}
	
	// Devuelve el centro del objeto como un Vector2D (útil para detección de colisiones)
	protected Vector2D getCenter(){
		return new Vector2D(position.getX() + width/2, position.getY() + height/2);
	}
	
	// Verifica si el objeto está "muerto"
	public boolean isDead() {return Dead;}
	
}
