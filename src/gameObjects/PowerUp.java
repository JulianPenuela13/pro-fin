package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Sound;
import math.Vector2D;
import states.GameState;
import ui.Action;

//Clase PowerUp: representa un potenciador en el juego.
//Extiende MovingObject y contiene lógica específica para aplicar efectos temporales al jugador.
public class PowerUp extends MovingObject {

	private long duration; // Duración de vida del PowerUp
	private Action action; // Acción que ejecutará el PowerUp al ser recogido
	private Sound powerUpPick; // Sonido que se reproduce al recoger el PowerUp
	private BufferedImage typeTexture; // Textura específica del tipo de PowerUp
	
	// Constructor
	public PowerUp(Vector2D position, BufferedImage texture, Action action, GameState gameState) {
		super(position, new Vector2D(), 0, Assets.orb, gameState); // Llama al constructor de MovingObject con posición inicial, velocidad nula y textura genérica

		this.action = action; // Acción a ejecutar (puede ser escudo, doble disparo, etc.)
		this.typeTexture = texture; // Textura específica del tipo de PowerUp
		duration = 0; // Inicializa la duración en 0
		powerUpPick = new Sound(Assets.powerUp); // Carga el sonido asociado al PowerUp

	}
	
	// Ejecuta la acción del PowerUp al ser recogido por el jugador
	void executeAction() {
		action.doAction(); // Ejecuta la lógica específica definida en el objeto `Action`
		powerUpPick.play(); // Reproduce el sonido al recoger el PowerUp
	}

	// Método de actualización
	@Override
	public void update(float dt) {
		angle += 0.1; // Incrementa el ángulo para rotar el PowerUp continuament
		duration += dt; // Incrementa el temporizador de duración
		
		// Destruye el PowerUp si supera su duración máxima
		if(duration > Constants.POWER_UP_DURATION) {
			this.Destroy();
		}
		
		collidesWith(); // Verifica colisiones con otros objetos

	}

	// Método para dibujar el PowerUp
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		// Configura la transformación para rotar el ícono del PowerUp
		at = AffineTransform.getTranslateInstance(
				position.getX() + Assets.orb.getWidth()/2 - typeTexture.getWidth()/2,
				position.getY() + Assets.orb.getHeight()/2 - typeTexture.getHeight()/2);

		// Aplica la rotación al PowerUp
		at.rotate(angle,
				typeTexture.getWidth()/2,
				typeTexture.getHeight()/2);
		
		// Dibuja el contenedor genérico del PowerUp
		g.drawImage(Assets.orb, (int)position.getX(), (int)position.getY(), null);
		
		
		// Dibuja la textura específica del PowerUp con rotación
		g2d.drawImage(typeTexture, at, null);
	}

}
