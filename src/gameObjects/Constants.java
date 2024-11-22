package gameObjects;

import javax.swing.filechooser.FileSystemView;

public class Constants {
	
	// Dimensiones del marco principal de la ventana del juego
	
	public static final int WIDTH = 1000; // Ancho del marco
	public static final int HEIGHT = 600; // Altura del marco
	
	// Propiedades del jugador
	
	public static final int FIRERATE = 300; // Tiempo entre disparos del jugador (en milisegundos)
	public static final double DELTAANGLE = 0.1; // Incremento del ángulo de rotación del jugador
	public static final double ACC = 0.2; // Aceleración del jugador
	public static final double PLAYER_MAX_VEL = 7.0; // Velocidad máxima del jugador
	public static final long FLICKER_TIME = 200; // Tiempo de parpadeo del jugador al reaparecer (en milisegundos)
	public static final long SPAWNING_TIME = 3000; // Tiempo de protección al reaparecer (en milisegundos)
	public static final long GAME_OVER_TIME = 3000; // Tiempo de espera antes de finalizar el juego tras perder (en milisegundos)
	
	// Propiedades del láser
	
	public static final double LASER_VEL = 15.0; // Velocidad del láser
	
	// Propiedades de los meteoros
	
	public static final double METEOR_INIT_VEL = 2.0; // Velocidad inicial de los meteoros
	
	public static final int METEOR_SCORE = 20;// Puntos otorgados por destruir un meteoro
	
	public static final double METEOR_MAX_VEL = 6.0; // Velocidad máxima de los meteoros
	
	public static final int SHIELD_DISTANCE = 150; // Distancia mínima para activar el escudo
	
	// Propiedades de los ovnis (UFO)
	
	public static final int NODE_RADIUS = 160; // Radio del nodo de movimiento del ovni
	
	public static final double UFO_MASS = 60; // Masa del ovni (afecta su movimiento)
	
	public static final int UFO_MAX_VEL = 3; // Velocidad máxima del ovni
	
	public static long UFO_FIRE_RATE = 1000; // Tiempo entre disparos del ovni (en milisegundos)
	
	public static double UFO_ANGLE_RANGE = Math.PI / 2; // Rango del ángulo de disparo del ovni
	
	public static final int UFO_SCORE = 40; // Puntos otorgados por destruir un ovni
	
	public static final long UFO_SPAWN_RATE = 10000; // Tiempo entre apariciones de ovnis (en milisegundos)
	
	// Texto de las opciones del menú
	
	public static final String PLAY = "PLAY"; // Texto para el botón de jugar
	
	public static final String EXIT = "EXIT"; // Texto para el botón de salir
	
	// Propiedades de la barra de carga
	
	public static final int LOADING_BAR_WIDTH = 500; // Ancho de la barra de carga
	public static final int LOADING_BAR_HEIGHT = 50; // Altura de la barra de carga
	
	// Otras constantes de texto
	
	public static final String RETURN = "RETURN"; // Texto para el botón de retorno
	public static final String HIGH_SCORES = "HIGHEST SCORES"; // Texto para el puntaje más alto
	
	public static final String SCORE = "SCORE"; // Etiqueta de puntaje
	public static final String DATE = "DATE"; // Etiqueta de fecha
	
	// Ruta del archivo de almacenamiento de puntajes
	
	public static final String SCORE_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() +
			"\\Space_Ship_Game\\data.json"; // data.xml if you use XMLParser Ruta del archivo JSON para guardar puntajes
	
	// Variables utilizadas para XMLParser (si se usa XML en lugar de JSON)
	
	public static final String PLAYER = "PLAYER"; // Etiqueta para un jugador individual
	public static final String PLAYERS = "PLAYERS"; // Etiqueta para la lista de jugadores
	
	// =============================================
	// Propiedades de los potenciadores (power-ups)
	
	public static final long POWER_UP_DURATION = 10000; // Duración de un potenciador (en milisegundos)
	public static final long POWER_UP_SPAWN_TIME = 8000; // Tiempo entre apariciones de potenciadores (en milisegundos)
	
	// Duraciones específicas de cada tipo de potenciador
	
	public static final long SHIELD_TIME = 12000; // Tiempo de duración del escudo (en milisegundos)
	public static final long DOUBLE_SCORE_TIME = 10000; // Tiempo de duración del potenciador de doble puntaje
	public static final long FAST_FIRE_TIME = 14000; // Tiempo de duración del potenciador de disparos rápidos
	public static final long DOUBLE_GUN_TIME = 12000; // Tiempo de duración del potenciador de armas dobles
	
	// Umbral de puntaje para bonificaciones adicionales
	
	public static final int SCORE_STACK = 1000; // Incremento de puntaje acumulativo para bonificaciones
	
}
