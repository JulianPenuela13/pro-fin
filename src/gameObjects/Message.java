package gameObjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import graphics.Text;
import math.Vector2D;

//Clase Message: representa un mensaje de texto que aparece en pantalla
public class Message {
	private float alpha; // Nivel de opacidad del mensaje (0 = completamente transparente, 1 = completamente opaco)
	private String text; // Texto que se mostrará en el mensaje
	private Vector2D position; // Posición del mensaje en el espacio del juego, representada como un vector 2D
	private Color color; // Color del texto del mensaje
	private boolean center; // Indica si el mensaje debe estar centrado en su posición
	private boolean fade; // Indica si el mensaje se desvanece con el tiempo
	private Font font; // Fuente utilizada para renderizar el texto
	private final float deltaAlpha = 0.01f; // Velocidad de cambio de opacidad al desvanecerse o aparecer
	private boolean dead; // Estado del mensaje: indica si está "muerto" (debe ser eliminado del juego
	
	// Constructor: inicializa las propiedades del mensaje
	public Message(Vector2D position, boolean fade, String text, Color color,
			boolean center, Font font) {
		this.font = font; // Asigna la fuente del texto
		this.text = text; // Asigna el texto del mensaje
		this.position = new Vector2D(position); // Clona la posición inicial
		this.fade = fade; // Indica si el mensaje debe desvanecerse
		this.color = color; // Asigna el color del texto
		this.center = center; // Indica si el mensaje estará centrado
		this.dead = false; // El mensaje no está muerto inicialmente
		
		if(fade) // Configura el nivel inicial de opacidad según si el mensaje se desvanece
			alpha = 1; // Comienza completamente opaco si se desvanece
		else
			alpha = 0; // Comienza completamente transparente si no se desvanece
		
	}
	
	// Dibuja el mensaje en la pantalla
	public void draw(Graphics2D g2d) {
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // Establece la opacidad actual del mensaje
		
		Text.drawText(g2d, text, position, center, color, font); // Dibuja el texto en la posición especificada
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1)); // Restaura la opacidad completa para evitar afectar otros dibujos
		
		position.setY(position.getY() - 1); // Mueve el mensaje ligeramente hacia arriba
		
		if(fade) // Si el mensaje debe desvanecerse, reduce la opacidad gradualmente
			alpha -= deltaAlpha;
		else // Si no debe desvanecerse, aumenta la opacidad gradualmente
			alpha += deltaAlpha;
		
		// Si el mensaje se desvanece y la opacidad cae por debajo de 0, márcalo como "muerto"
		if(fade && alpha < 0) { 
			dead = true;
		}
		
		// Si el mensaje aparece (no desvanece) y la opacidad supera 1, invierte su comportamiento
		if(!fade && alpha > 1) {
			fade = true; // Comienza a desvanecerse
			alpha = 1; // Establece la opacidad máxima
		}
	
	}
	
	// Verifica si el mensaje está "muerto" (ya no debe ser mostrado)
	public boolean isDead() {return dead;}

	
}
