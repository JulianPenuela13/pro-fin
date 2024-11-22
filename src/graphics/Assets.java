package graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

//Clase Assets: centraliza la carga y gestión de recursos (imágenes, sonidos, fuentes, etc.) para el juego
public class Assets {
	
	 // Variables de control para el estado de la carga
	public static boolean loaded = false; // Indica si todos los recursos se han cargado
	public static float count = 0; // Contador de recursos cargados
	public static float MAX_COUNT = 57; // Número total de recursos esperados
	
	// Recursos gráficos para el jugador
	public static BufferedImage player; // Imagen del jugador
	public static BufferedImage doubleGunPlayer; // Imagen del jugador con arma doble
	
	// Efectos especiales
	
	public static BufferedImage speed; // Imagen del efecto de velocidad
	
	public static BufferedImage[] shieldEffect = new BufferedImage[3]; // Imágenes de la animación del escudo
	
	// Animación de explosión
	
	public static BufferedImage[] exp = new BufferedImage[9]; // Imágenes de los frames de la explosión
	
	 // Recursos de láseres
	
	public static BufferedImage blueLaser, greenLaser, redLaser; // Imágenes de diferentes tipos de láseres
	
	// Recursos de meteoros
	
	public static BufferedImage[] bigs = new BufferedImage[4]; // Imágenes de meteoros grandes
	public static BufferedImage[] meds = new BufferedImage[2]; // Imágenes de meteoros medianos
	public static BufferedImage[] smalls = new BufferedImage[2]; // Imágenes de meteoros pequeños
	public static BufferedImage[] tinies = new BufferedImage[2]; // Imágenes de meteoros diminutos
	
	 // Recursos para el ovni
	
	public static BufferedImage ufo; // Imagen del ovni enemigo
	
	// numbers
	
	public static BufferedImage[] numbers = new BufferedImage[11]; // Imágenes de los dígitos del 0 al 10
	
	// Recursos de vidas
	public static BufferedImage life; // Imagen que representa una vida extra
	
	// Fuentes
	
	public static Font fontBig; // Fuente para textos grandes
	public static Font fontMed; // Fuente para textos medianos
	
	// Recursos de sonido
	public static Clip backgroundMusic, explosion, playerLoose, playerShoot, ufoShoot, powerUp;
	
	// ui
	
	public static BufferedImage blueBtn; // Imágenes de botones para la interfaz de usuario (UI)
	public static BufferedImage greyBtn; // Imágenes de botones para la interfaz de usuario (UI)
	
	// power ups
	
	public static BufferedImage orb, doubleScore, doubleGun, fastFire, shield, star;
	
	// Método init: carga todos los recursos necesarios para el juego
	public static void init()
	{
		// Carga de recursos gráficos
		player = loadImage("/ships/player.png"); // Imagen del jugador
		doubleGunPlayer = loadImage("/ships/doubleGunPlayer.png"); // Imagen del jugador con arma doble
		
		speed = loadImage("/effects/fire08.png"); // Imagen del efecto de velocidad
		
		 // Carga de láseres
		blueLaser = loadImage("/lasers/laserBlue01.png");
		
		greenLaser = loadImage("/lasers/laserGreen11.png");
		
		redLaser = loadImage("/lasers/laserRed01.png");
		
		// Carga del ovni
		ufo = loadImage("/ships/ufo.png");
		
		// Carga del icono de vida extra
		life = loadImage("/others/life.png");
		
		// Carga de fuentes
		fontBig = loadFont("/fonts/futureFont.ttf", 42);
		
		fontMed = loadFont("/fonts/futureFont.ttf", 20);
		
		// Carga de efectos de escudo
		for(int i = 0; i < 3; i++)
			shieldEffect[i] = loadImage("/effects/shield" + (i + 1) +".png"); 
		
		// Carga de meteoros
		for(int i = 0; i < bigs.length; i++)
			bigs[i] = loadImage("/meteors/big"+(i+1)+".png");
		
		for(int i = 0; i < meds.length; i++)
			meds[i] = loadImage("/meteors/med"+(i+1)+".png");
		
		for(int i = 0; i < smalls.length; i++)
			smalls[i] = loadImage("/meteors/small"+(i+1)+".png");
		
		for(int i = 0; i < tinies.length; i++)
			tinies[i] = loadImage("/meteors/tiny"+(i+1)+".png");
		
		// Carga de frames de explosión
		for(int i = 0; i < exp.length; i++)
			exp[i] = loadImage("/explosion/"+i+".png");
		
		// Carga de números
		for(int i = 0; i < numbers.length; i++)
			numbers[i] = loadImage("/numbers/"+i+".png");
		
		// Carga de sonidos
		backgroundMusic = loadSound("/sounds/backgroundMusic.wav");
		explosion = loadSound("/sounds/explosion.wav");
		playerLoose = loadSound("/sounds/playerLoose.wav");
		playerShoot = loadSound("/sounds/playerShoot.wav");
		ufoShoot = loadSound("/sounds/ufoShoot.wav");
		powerUp = loadSound("/sounds/powerUp.wav");
		
		 // Carga de recursos de UI
		greyBtn = loadImage("/ui/grey_button.png");
		blueBtn = loadImage("/ui/blue_button.png");
		
		// Carga de power-ups
		orb = loadImage("/powers/orb.png");
		doubleScore = loadImage("/powers/doubleScore.png");
		doubleGun = loadImage("/powers/doubleGun.png");
		fastFire = loadImage("/powers/fastFire.png");
		star = loadImage("/powers/star.png");
		shield = loadImage("/powers/shield.png");
		
		// ===========================================================
		
		// Marcar como cargado una vez que se han inicializado todos los recursos
		loaded = true;
		
	}

	// Método loadImage: carga una imagen desde el path proporcionado
	public static BufferedImage loadImage(String path) {
		count ++; // Incrementa el contador de recursos cargados
		return Loader.ImageLoader(path); // Utiliza el método de carga del Loader
	}
	// Método loadFont: carga una fuente desde el path proporcionado y establece el tamaño
	public static Font loadFont(String path, int size) {
		count ++; // Incrementa el contador de recursos cargados
		return Loader.loadFont(path, size); // Utiliza el método de carga del Loader
	}
	// Método loadSound: carga un sonido desde el path proporcionado
	public static Clip loadSound(String path) {
		count ++; // Incrementa el contador de recursos cargados
		return Loader.loadSound(path); // Utiliza el método de carga del Loader
	}
	
}
