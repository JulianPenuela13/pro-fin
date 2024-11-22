package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import gameObjects.Constants;
import gameObjects.Message;
import gameObjects.Meteor;
import gameObjects.MovingObject;
import gameObjects.Player;
import gameObjects.PowerUp;
import gameObjects.PowerUpTypes;
import gameObjects.Size;
import gameObjects.Ufo;
import graphics.Animation;
import graphics.Assets;
import graphics.Sound;
import io.JSONParser;
import io.ScoreData;
import math.Vector2D;
import ui.Action;

//Clase que representa el estado principal del juego, extendiendo la clase base State.
public class GameState extends State{

    // Posición inicial del jugador, centrada en la pantalla.
	public static final Vector2D PLAYER_START_POSITION = new Vector2D(Constants.WIDTH/2 - Assets.player.getWidth()/2,
			Constants.HEIGHT/2 - Assets.player.getHeight()/2);
	
	private Player player; // Objeto del jugador.
	private ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>(); // Lista de objetos en movimiento.
	private ArrayList<Animation> explosions = new ArrayList<Animation>(); // Lista de animaciones de explosiones.
	private ArrayList<Message> messages = new ArrayList<Message>(); // Lista de mensajes en pantalla.
	 
	private int score = 0; // Puntuación actual.
	private int lives = 3; // Número de vidas restantes.
	
	private int meteors; // Número de meteoros por ola.
	private int waves = 1;  // Número de la ola actual.
	
	private Sound backgroundMusic; // Música de fondo del juego.
	private long gameOverTimer; // Temporizador para manejar el fin del juego.
	private boolean gameOver; // Indica si el juego ha terminado.
	
	private long ufoSpawner; // Temporizador para generar ovnis.
	private long powerUpSpawner; // Temporizador para generar mejoras.
	
	// Constructor: inicializa el estado del juego
	public GameState()
	{
		player = new Player(PLAYER_START_POSITION, new Vector2D(),
				Constants.PLAYER_MAX_VEL, Assets.player, this);
		
		gameOver = false; // Marca que el juego no ha terminado.
		movingObjects.add(player); // Agrega el jugador a la lista de objetos en movimiento.
		 
		meteors = 1; // Inicia con un meteoro.
		startWave(); // Llama a la primera ola de meteoros.
		backgroundMusic = new Sound(Assets.backgroundMusic);
		backgroundMusic.loop(); // Configura la música para que se repita.
		backgroundMusic.changeVolume(-10.0f); // Ajusta el volumen.
		
		gameOverTimer = 0;
		ufoSpawner = 0;
		powerUpSpawner = 0;
		
		gameOver = false;
		
	}
	
	// Agrega puntos a la puntuación y muestra un mensaje en pantalla.
	public void addScore(int value, Vector2D position) {
		
		Color c = Color.WHITE; // Color predeterminado para el mensaje.
		String text = "+" + value + " score";
		if(player.isDoubleScoreOn()) { // Si el jugador tiene el bonus de puntuación doble.
			c = Color.YELLOW;
			value = value * 2;
			text = "+" + value + " score" + " (X2)";
		}
		
		score += value;  // Incrementa la puntuación
		messages.add(new Message(position, true, text, c, false, Assets.fontMed));
	}
	
	// Divide un meteoro grande en meteoros más pequeños.
	public void divideMeteor(Meteor meteor){
		
		Size size = meteor.getSize(); // Tamaño actual del meteoro.
		
		BufferedImage[] textures = size.textures; // Texturas correspondientes al tamaño.

		
		Size newSize = null; // Nuevo tamaño para los meteoros divididos.
		
		switch(size){
		case BIG:
			newSize =  Size.MED;
			break;
		case MED:
			newSize = Size.SMALL;
			break;
		case SMALL:
			newSize = Size.TINY;
			break;
		default:
			return; // Si no se puede dividir más, termina.
		}
		
		// Genera nuevos meteoros en base al tamaño reducido.
		for(int i = 0; i < size.quantity; i++){
			movingObjects.add(new Meteor(
					meteor.getPosition(),
					new Vector2D(0, 1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_INIT_VEL*Math.random() + 1,
					textures[(int)(Math.random()*textures.length)],
					this,
					newSize
					));
		}
	}
	
	// Inicia una nueva ola de meteoros.
	private void startWave(){
		// Muestra un mensaje indicando la nueva ola.
		messages.add(new Message(new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2), false,
				"WAVE "+waves, Color.WHITE, true, Assets.fontBig));
		
		double x, y; // Coordenadas para los meteoros.
		
		for(int i = 0; i < meteors; i++){
			 // Genera posiciones aleatorias en los bordes de la pantalla.
			x = i % 2 == 0 ? Math.random()*Constants.WIDTH : 0;
			y = i % 2 == 0 ? 0 : Math.random()*Constants.HEIGHT;
			
			BufferedImage texture = Assets.bigs[(int)(Math.random()*Assets.bigs.length)];
			
			movingObjects.add(new Meteor(
					new Vector2D(x, y),
					new Vector2D(0, 1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_INIT_VEL*Math.random() + 1,
					texture,
					this,
					Size.BIG
					));
			
		}
		meteors ++; // Incrementa la cantidad de meteoros para la próxima ola.
	}
	// Agrega una animación de explosión en una posición específica.
	public void playExplosion(Vector2D position){
		explosions.add(new Animation(
				Assets.exp,
				50,
				position.subtract(new Vector2D(Assets.exp[0].getWidth()/2, Assets.exp[0].getHeight()/2))
				));
	}
	
	 // Genera un ovni en posiciones aleatorias.
	private void spawnUfo(){
		// Selecciona una posición inicial aleatoria.
		int rand = (int) (Math.random()*2);
		
		double x = rand == 0 ? (Math.random()*Constants.WIDTH): Constants.WIDTH;
		double y = rand == 0 ? Constants.HEIGHT : (Math.random()*Constants.HEIGHT);
		
		 // Genera un camino aleatorio para el ovni
		ArrayList<Vector2D> path = new ArrayList<Vector2D>();
		
		double posX, posY;
		
		posX = Math.random()*Constants.WIDTH/2;
		posY = Math.random()*Constants.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));

		posX = Math.random()*(Constants.WIDTH/2) + Constants.WIDTH/2;
		posY = Math.random()*Constants.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*Constants.WIDTH/2;
		posY = Math.random()*(Constants.HEIGHT/2) + Constants.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		posX = Math.random()*(Constants.WIDTH/2) + Constants.WIDTH/2;
		posY = Math.random()*(Constants.HEIGHT/2) + Constants.HEIGHT/2;	
		path.add(new Vector2D(posX, posY));
		
		movingObjects.add(new Ufo(
				new Vector2D(x, y),
				new Vector2D(),
				Constants.UFO_MAX_VEL,
				Assets.ufo,
				path,
				this
				));
		
	}

	private void spawnPowerUp() {
		// Genera coordenadas aleatorias dentro del área de juego, asegurándose de que no excedan los límites.
		final int x = (int) ((Constants.WIDTH - Assets.orb.getWidth()) * Math.random());
		final int y = (int) ((Constants.HEIGHT - Assets.orb.getHeight()) * Math.random());
		// Selecciona aleatoriamente un tipo de PowerUp basado en su índice.
		int index = (int) (Math.random() * (PowerUpTypes.values().length));
		
		PowerUpTypes p = PowerUpTypes.values()[index]; // Recupera el tipo de PowerUp

		// Configura el texto asociado al PowerUp y crea una acción correspondiente.
		final String text = p.text;
		Action action = null;
		Vector2D position = new Vector2D(x , y); // Establece la posición para el PowerUp.
		// Determina la acción específica en función del tipo de Power
		switch(p) {
		case LIFE: // PowerUp que da una vida extra.
			action = new Action() {
				@Override
				public void doAction() {
					
					lives ++; // Incrementa las vidas del jugador.
					messages.add(new Message(
							position,
							false,
							text,
							Color.GREEN,
							false,
							Assets.fontMed
							)); // Muestra un mensaje en pantalla.
				}
			};
			break;
		case SHIELD:
			action = new Action() {
				@Override
				public void doAction() {
					player.setShield(); // PowerUp que activa un escudo en el jugador.
					messages.add(new Message(
							position,
							false,
							text,
							Color.DARK_GRAY,
							false,
							Assets.fontMed
							));
				}
			};
			break;
		case SCORE_X2:
			action = new Action() {
				@Override
				public void doAction() {
					player.setDoubleScore();
					messages.add(new Message(
							position,
							false,
							text,
							Color.YELLOW,
							false,
							Assets.fontMed
							));
				}
			};
			break;
		case FASTER_FIRE:
			action = new Action() {
				@Override
				public void doAction() {
					player.setFastFire();
					messages.add(new Message(
							position,
							false,
							text,
							Color.BLUE,
							false,
							Assets.fontMed
							));
				}
			};
			break;
		case SCORE_STACK:
			action = new Action() {
				@Override
				public void doAction() {
					score += 1000;
					messages.add(new Message(
							position,
							false,
							text,
							Color.MAGENTA,
							false,
							Assets.fontMed
							));
				}
			};
			break;
		case DOUBLE_GUN:
			action = new Action() {
				@Override
				public void doAction() {
					player.setDoubleGun();
					messages.add(new Message(
							position,
							false,
							text,
							Color.ORANGE,
							false,
							Assets.fontMed
							));
				}
			};
			break;
		default:
			break;
		}
		
		this.movingObjects.add(new PowerUp(
				position,
				p.texture,
				action,
				this
				));
		
		
	}
	// Método para actualizar la lógica del estado del juego.
	public void update(float dt)
	{
		
		if(gameOver)
			gameOverTimer += dt;
		
		powerUpSpawner += dt;
		ufoSpawner += dt;
		
		for(int i = 0; i < movingObjects.size(); i++) {
			
			MovingObject mo = movingObjects.get(i);
			
			mo.update(dt);
			if(mo.isDead()) {
				movingObjects.remove(i);
				i--;
			}
			
		}
		
		for(int i = 0; i < explosions.size(); i++){
			Animation anim = explosions.get(i);
			anim.update(dt);
			if(!anim.isRunning()){
				explosions.remove(i);
			}
			
		}
		
		if(gameOverTimer > Constants.GAME_OVER_TIME) {
			
			try {
				ArrayList<ScoreData> dataList = JSONParser.readFile();
				dataList.add(new ScoreData(score));
				JSONParser.writeFile(dataList);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			backgroundMusic.stop();
			
			State.changeState(new MenuState());
		}
		
		if(powerUpSpawner > Constants.POWER_UP_SPAWN_TIME) {
			spawnPowerUp();
			powerUpSpawner = 0;
		}
		
		
		if(ufoSpawner > Constants.UFO_SPAWN_RATE) {
			spawnUfo();
			ufoSpawner = 0;
		}
		
		for(int i = 0; i < movingObjects.size(); i++)
			if(movingObjects.get(i) instanceof Meteor)
				return;
		
		startWave();
		
	}
	
	 // Método para dibujar los elementos en pantalla.
	public void draw(Graphics g)
	{	
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		for(int i = 0; i < messages.size(); i++) {
			messages.get(i).draw(g2d);
			if(messages.get(i).isDead())
				messages.remove(i);
		}
		
		for(int i = 0; i < movingObjects.size(); i++)
			movingObjects.get(i).draw(g);
		
		for(int i = 0; i < explosions.size(); i++){
			Animation anim = explosions.get(i);
			g2d.drawImage(anim.getCurrentFrame(), (int)anim.getPosition().getX(), (int)anim.getPosition().getY(),
					null);
			
		}
		drawScore(g);
		drawLives(g);
	}
	
	private void drawScore(Graphics g) {
		
		Vector2D pos = new Vector2D(850, 25);
		
		String scoreToString = Integer.toString(score);
		
		for(int i = 0; i < scoreToString.length(); i++) {
			
			g.drawImage(Assets.numbers[Integer.parseInt(scoreToString.substring(i, i + 1))],
					(int)pos.getX(), (int)pos.getY(), null);
			pos.setX(pos.getX() + 20);
			
		}
		
	}
	
	private void drawLives(Graphics g){
		
		if(lives < 1)
			return;
		
		Vector2D livePosition = new Vector2D(25, 25);
		
		g.drawImage(Assets.life, (int)livePosition.getX(), (int)livePosition.getY(), null);
		
		g.drawImage(Assets.numbers[10], (int)livePosition.getX() + 40,
				(int)livePosition.getY() + 5, null);
		
		String livesToString = Integer.toString(lives);
		
		Vector2D pos = new Vector2D(livePosition.getX(), livePosition.getY());
		
		for(int i = 0; i < livesToString.length(); i ++)
		{
			int number = Integer.parseInt(livesToString.substring(i, i+1));
			
			if(number <= 0)
				break;
			g.drawImage(Assets.numbers[number],
					(int)pos.getX() + 60, (int)pos.getY() + 5, null);
			pos.setX(pos.getX() + 20);
		}
		
	}
	
	public ArrayList<MovingObject> getMovingObjects() {
		return movingObjects;
	}
	
	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public boolean subtractLife(Vector2D position) {
		lives --;
		
		Message lifeLostMesg = new Message(
				position,
				false,
				"-1 LIFE",
				Color.RED,
				false,
				Assets.fontMed
				);
		messages.add(lifeLostMesg);
		
		return lives > 0;
	}
	
	
	public void gameOver() {
		Message gameOverMsg = new Message(
				PLAYER_START_POSITION,
				true,
				"GAME OVER",
				Color.WHITE,
				true,
				Assets.fontBig);
		
		this.messages.add(gameOverMsg);
		gameOver = true;
	}
	
}
