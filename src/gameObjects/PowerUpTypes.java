package gameObjects;

import java.awt.image.BufferedImage;

import graphics.Assets;

//Definición de un enumerador que representa diferentes tipos de potenciadores (Power-Ups) en un juego.
public enum PowerUpTypes {
	// Cada constante del enumerador representa un tipo de potenciador con un nombre y una textura asociada.
	SHIELD("SHIELD", Assets.shield), // Potenciador que otorga un escudo al jugador.
	LIFE("+1 LIFE", Assets.life), // Potenciador que incrementa en 1 la vida del jugador.
	SCORE_X2("SCORE x2", Assets.doubleScore), // Potenciador que duplica el puntaje ganado temporalmente.
	FASTER_FIRE("FAST FIRE", Assets.fastFire), // Potenciador que aumenta la velocidad de disparo.
	SCORE_STACK("+1000 SCORE", Assets.star), // Potenciador que añade 1000 puntos al puntaje del jugador.
	DOUBLE_GUN("DOUBLE GUN", Assets.doubleGun); // Potenciador que permite disparar con dos armas a la vez.
	
	// Atributos de la clase enum que almacenan el texto descriptivo y la textura gráfica del potenciador.
	public String text; // Texto descriptivo del potenciador.
	public BufferedImage texture; // Imagen asociada al potenciador, tomada de los Assets del juego.
	
	 // Constructor privado del enumerador. Asigna el texto y la textura a las constantes.
	private PowerUpTypes(String text, BufferedImage texture){
		this.text = text; // Inicializa el texto descriptivo del potenciador.
		this.texture = texture; // Inicializa la textura gráfica del potenciador.
	}
}
