package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import gameObjects.Constants;
import graphics.Assets;
import graphics.Loader;
import graphics.Text;
import math.Vector2D;

//Clase que representa el estado de carga del juego, se extiende de State.
public class LoadingState extends State{

	private Thread loadingThread; // Hilo que se encargará de cargar los recursos.
	
	private Font font; // Fuente personalizada para mostrar texto.
	
	// Constructor que inicializa el estado de carga.
	public LoadingState(Thread loadingThread) {
		this.loadingThread = loadingThread; // Asigna el hilo de carga.
		this.loadingThread.start(); // Inicia la ejecución del hilo.
		font = Loader.loadFont("/fonts/futureFont.ttf", 38); // Carga una fuente desde los recursos.
	}
	
	 // Método para actualizar el estado de carga.
	@Override
	public void update(float dt) {
		 // Verifica si todos los recursos han sido cargados.
		if(Assets.loaded) {
			State.changeState(new MenuState()); // Cambia al estado de menú una vez cargados.
			try {
				loadingThread.join(); // Espera a que el hilo de carga termine.
			} catch (InterruptedException e) {
				e.printStackTrace(); // Maneja posibles interrupciones del hilo.
			}
		}
		
	}

	// Método para renderizar elementos en pantalla durante el estado de carga.
	@Override
	public void draw(Graphics g) {
		// Crea un degradado para la barra de carga.
		GradientPaint gp = new GradientPaint(
				Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2, // Coordenada x inicial.
				Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2, // Coordenada y inicial.
				Color.WHITE, // Color inicial del degradado.
				Constants.WIDTH / 2 + Constants.LOADING_BAR_WIDTH / 2, // Coordenada x final.
				Constants.HEIGHT / 2 + Constants.LOADING_BAR_HEIGHT / 2, // Coordenada y final.
				Color.BLUE // Color final del degradado.
				);
		
		Graphics2D g2d = (Graphics2D)g; // Conversión a Graphics2D para usar características avanzadas.
		 
		g2d.setPaint(gp); // Establece el degradado como pintura actual.
		
		 // Calcula el porcentaje de carga basado en los recursos cargados.
		float percentage = (Assets.count / Assets.MAX_COUNT);
		
		// Dibuja la barra de carga.
		g2d.fillRect(Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2, // Posición x.
				Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2, // Posición y.
				(int)(Constants.LOADING_BAR_WIDTH * percentage), // Ancho proporcional al porcentaje de carga.
				Constants.LOADING_BAR_HEIGHT); // Altura fija de la barra.
		
		// Dibuja el borde de la barra de carga.
		g2d.drawRect(Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2,
				Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2,
				Constants.LOADING_BAR_WIDTH,
				Constants.LOADING_BAR_HEIGHT);
		
		// Dibuja el título del juego.
		Text.drawText(g2d, "SPACE SHIP GAME", new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2 - 50), // Posición en la pantalla.
				true, Color.WHITE, font);// Centrar el texto. // Color del texto. // Fuente utilizada.
		
		// Dibuja el mensaje "LOADING...".
		Text.drawText(g2d, "LOADING...", new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2 + 40),
				true, Color.WHITE, font); // Centrar el texto // Color del texto. // Fuente utilizada.
		
	}

}
