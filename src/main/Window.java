package main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import gameObjects.Constants;
import graphics.Assets;
import input.KeyBoard;
import input.MouseInput;
import states.LoadingState;
import states.State;

//Clase principal que extiende JFrame e implementa Runnable para ejecutar en un hilo separado.
public class Window extends JFrame implements Runnable{
	
	private static final long serialVersionUID = 1L;// ID para la serialización (recomendado para clases JFrame).
	
	private Canvas canvas; // Superficie para renderizar gráficos.
	private Thread thread; // Hilo donde se ejecuta el juego.
	private boolean running = false; // Controla si el juego está en ejecución.
	
	private BufferStrategy bs; // Estrategia de buffer para manejar la representación gráfica.
	private Graphics g; // Objeto para dibujar elementos gráficos.
	 
	// Variables para manejar la velocidad de fotogramas.
	private final int FPS = 60; // Fotogramas por segundo objetivo.
	private double TARGETTIME = 1000000000/FPS; // Tiempo objetivo por fotograma en nanosegundos.
	private double delta = 0;  // Controla el tiempo acumulado entre fotogramas.
	private int AVERAGEFPS = FPS; // Almacena el promedio de fotogramas por segundo.
	
	private KeyBoard keyBoard; // Objeto para manejar la entrada del teclado.
	private MouseInput mouseInput; // Objeto para manejar la entrada del ratón.

	// Constructor de la clase Window
	public Window()
	{
		// Constructor de la clase Window
		setTitle("Space Ship Game"); // Título de la ventana.
		setSize(Constants.WIDTH, Constants.HEIGHT);  // Tamaño de la ventana.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar el programa al cerrar la ventana.
		setResizable(false); // Evita que la ventana sea redimensionable.
		setLocationRelativeTo(null); // Centra la ventana en la pantalla.
		 
		 // Inicialización del canvas y dispositivos de entrada.
		canvas = new Canvas();
		keyBoard = new KeyBoard();
		mouseInput = new MouseInput();
		
		// Configuración del tamaño del canvas.
		canvas.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
		canvas.setMaximumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
		canvas.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
		canvas.setFocusable(true); // Hace visible la ventana.
		
		// Agregar el canvas y los listeners a la ventana.
		add(canvas);
		canvas.addKeyListener(keyBoard);
		canvas.addMouseListener(mouseInput);
		canvas.addMouseMotionListener(mouseInput);
		setVisible(true);
	}
	
	
	// Método para actualizar la lógica del juego.
	public static void main(String[] args) {
		new Window().start(); // Crea una instancia de la ventana y arranca el hilo del juego.
    
	}
	
	// Método para actualizar la lógica del juego.
	private void update(float dt){
		keyBoard.update(); // Actualiza el estado del teclado.
		State.getCurrentState().update(dt); // Actualiza el estado actual del juego.
	}

	// Método para renderizar los gráficos.
	private void draw(){
		bs = canvas.getBufferStrategy(); // Obtiene la estrategia de buffer del canvas.
		
		if(bs == null)
		{ // Si no existe la estrategia, la crea.
			canvas.createBufferStrategy(3); // Usa triple buffer para suavizar los gráficos.
			return; 
		}
		
		g = bs.getDrawGraphics(); // Obtiene el objeto Graphics para dibujar.
		
		//-----------------------
		
		g.setColor(Color.BLACK);  // Establece el color de fondo.
		
		g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT); // Rellena el fondo con negro.
		 
		State.getCurrentState().draw(g); // Llama al método de dibujo del estado actual del juego.
		
		g.setColor(Color.WHITE); // Cambia el color al blanco.
		
		g.drawString(""+AVERAGEFPS, 10, 20); // Muestra el FPS promedio en pantall
		
		//---------------------
		
		g.dispose(); // Libera los recursos de Graphics.
		bs.show(); // Muestra el contenido del buffer en pantalla.
	}
	
	// Método para inicializar los recursos del juego.
	private void init()
	{ // Inicia los recursos del juego en un hilo separado
		
		Thread loadingThread = new Thread(new Runnable() {

			@Override
			public void run() {
				Assets.init(); // Carga los recursos del juego.
			}
		});
		
		
		
		State.changeState(new LoadingState(loadingThread)); // Cambia al estado de carga inicial.
    
	}
	
	// Método que contiene el ciclo principal del juego.
	@Override
	public void run() {
		
		long now = 0; // Tiempo actual.
		long lastTime = System.nanoTime(); // Último tiempo registrado.
		int frames = 0;  // Contador de fotogramas renderizados.
		long time = 0; // Tiempo acumulado en el ciclo.
		
		init(); // Inicializa el juego.
		
		while(running)
		{ // Mientras el juego esté en ejecución.
			now = System.nanoTime(); // Obtiene el tiempo actual.
			delta += (now - lastTime)/TARGETTIME; // Calcula el delta de tiempo.
			time += (now - lastTime); // Acumula el tiempo total.
			lastTime = now; // Actualiza el último tiempo.
			
			if(delta >= 1) // Si es momento de actualizar y dibujar:
			{	
				update((float) (delta * TARGETTIME * 0.000001f)); // Lógica del juego.
				draw(); // Renderiza los gráficos.
				delta --; // Disminuye el delta.
				frames ++; // Incrementa el contador de frames.
			}
			if(time >= 1000000000)
			{ // Si ha pasado un segundo.

				AVERAGEFPS = frames; // Actualiza el FPS promedio.
				frames = 0; // Reinicia el contador de frames.
				time = 0; // Reinicia el tiempo acumulado.
				
			}
			
			
		}
		
		stop(); // Detiene el juego cuando termina el ciclo.
	}
	
	// Método para iniciar el juego en un nuevo hilo.
	private void start(){
		
		thread = new Thread(this); // Crea un hilo que ejecuta el método run().
		thread.start();  // Inicia el hilo.
		running = true; // Marca el juego como en ejecución.
		
		
	}
	
	 // Método para detener el juego.
	private void stop(){
		try {
			thread.join(); // Espera a que el hilo termine.
			running = false;  // Marca el juego como detenido.
		} catch (InterruptedException e) {
			e.printStackTrace(); // Manejo de errores.
		}
	}
}