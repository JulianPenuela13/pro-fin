package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Animation;
import graphics.Assets;
import graphics.Sound;
import input.KeyBoard;
import math.Vector2D;
import states.GameState;

//Clase Player: representa al jugador controlado por el usuario. Extiende MovingObject.
public class Player extends MovingObject{
	
	// Vectores de dirección y aceleración
	private Vector2D heading;	// Dirección actual del jugador
	private Vector2D acceleration; // Aceleración del jugador

	// Estados y temporizadores
	private boolean accelerating = false; // Indica si el jugador está acelerando
	private long fireRate; // Temporizador para controlar la cadencia de disparos
	
	private boolean spawning, visible; // Estados de aparición e invisibilidad durante el respawn
	
	private long spawnTime, flickerTime, shieldTime, doubleScoreTime, fastFireTime, doubleGunTime; // Temporizadores para potenciadores
	
	private Sound shoot, loose; // Sonido de disparo y pérdida de vida
	
	private boolean shieldOn, doubleScoreOn, fastFireOn, doubleGunOn;; // Estados de los potenciadores
	
	private Animation shieldEffect; // Animación para el efecto del escudo
	
	private long fireSpeed; // Velocidad de disparo, modificada por el potenciador Fast Fire
	
	// Constructor
	public Player(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
		super(position, velocity, maxVel, texture, gameState);
		heading = new Vector2D(0, 1); // Dirección inicial hacia arriba
		acceleration = new Vector2D(); // Sin aceleración inicial
		// Inicialización de temporizadores
		fireRate = 0;
		spawnTime = 0;
		flickerTime = 0;
		shieldTime = 0;
		fastFireTime = 0;
		doubleGunTime = 0;
		
		// Cargar sonidos y efectos
		shoot = new Sound(Assets.playerShoot);
		loose = new Sound(Assets.playerLoose);
		
		shieldEffect = new Animation(Assets.shieldEffect, 80, null); // Animación del escudo

		
		visible = true; // El jugador es visible al inicio
	}
	
	// Método de actualización principal
	@Override
	public void update(float dt) // Actualizar temporizador de disparo
	{
		
		// Actualizar potenciadores y efectos temporales
		fireRate += dt;
		
		if(shieldOn)
			shieldTime += dt;
		
		if(doubleScoreOn)
			doubleScoreTime += dt;
		
		if(fastFireOn) {
			fireSpeed = Constants.FIRERATE / 2; // Disparos más rápidos
			fastFireTime += dt;
		}else {
			fireSpeed = Constants.FIRERATE; // Velocidad normal de disparo
		}
		
		if(doubleGunOn)
			doubleGunTime += dt;
		
		// Desactivar potenciadores al vencer el tiempo
		if(shieldTime > Constants.SHIELD_TIME) {
			shieldTime = 0;
			shieldOn = false;
		}
		
		if(doubleScoreTime > Constants.DOUBLE_SCORE_TIME) {
			doubleScoreOn = false;
			doubleScoreTime = 0;
		}
		
		if(fastFireTime > Constants.FAST_FIRE_TIME) {
			fastFireOn = false;
			fastFireTime = 0;
		}
		
		if(doubleGunTime > Constants.DOUBLE_GUN_TIME) {
			doubleGunOn = false;
			doubleGunTime = 0;
		}
		
		// Manejar estado de "spawning" (invulnerabilidad al reaparecer)
		if(spawning) {
			
			flickerTime += dt;
			spawnTime += dt;
			
			// Alternar visibilidad para efecto de parpadeo
			if(flickerTime > Constants.FLICKER_TIME) {
				
				visible = !visible;
				flickerTime = 0;
			}
			
			// Terminar el estado de spawning al vencer el tiempo
			if(spawnTime > Constants.SPAWNING_TIME) {
				spawning = false;
				visible = true;
			}
			
		}
		
		// Manejar disparos
		if(KeyBoard.SHOOT &&  fireRate > fireSpeed && !spawning)
		{
			// Doble disparo
			if(doubleGunOn) {
				Vector2D leftGun = getCenter();
				Vector2D rightGun = getCenter();
				
				Vector2D temp = new Vector2D(heading);
				temp.normalize();
				temp = temp.setDirection(angle - 1.3f);
				temp = temp.scale(width);
				rightGun = rightGun.add(temp);
				
				temp = temp.setDirection(angle - 1.9f);
				leftGun = leftGun.add(temp);
				
				Laser l = new Laser(leftGun, heading, Constants.LASER_VEL, angle, Assets.blueLaser, gameState);
				Laser r = new Laser(rightGun, heading, Constants.LASER_VEL, angle, Assets.blueLaser, gameState);
				
				gameState.getMovingObjects().add(0, l);
				gameState.getMovingObjects().add(0, r);
				
			}else { // Disparo único
				gameState.getMovingObjects().add(0,new Laser(
						getCenter().add(heading.scale(width)),
						heading,
						Constants.LASER_VEL,
						angle,
						Assets.blueLaser,
						gameState
						));
			}

			fireRate = 0; // Reinicia el temporizador de disparo
			shoot.play(); // Reproduce el sonido de disparo
		}
		
		
		if(shoot.getFramePosition() > 8500) {
			shoot.stop();
		}
		
		// Rotación
		if(KeyBoard.RIGHT)
			angle += Constants.DELTAANGLE;
		if(KeyBoard.LEFT)
			angle -= Constants.DELTAANGLE;
		
		// Aceleración
		if(KeyBoard.UP)
		{
			acceleration = heading.scale(Constants.ACC);
			accelerating = true;
		}else
		{
			if(velocity.getMagnitude() != 0)
				acceleration = (velocity.scale(-1).normalize()).scale(Constants.ACC/2); // Frenado
			accelerating = false;
		}
		
		// Actualizar velocidad y posición
		velocity = velocity.add(acceleration);
		
		velocity = velocity.limit(maxVel);
		
		heading = heading.setDirection(angle - Math.PI/2);
		
		position = position.add(velocity);
		
		// Movimiento continuo (envoltura en bordes de pantalla)
		if(position.getX() > Constants.WIDTH)
			position.setX(0);
		if(position.getY() > Constants.HEIGHT)
			position.setY(0);
		
		if(position.getX() < -width)
			position.setX(Constants.WIDTH);
		if(position.getY() < -height)
			position.setY(Constants.HEIGHT);
		
		// Actualizar efecto del escudo si está activo
		if(shieldOn)
			shieldEffect.update(dt);
		
		// Detectar colisiones
		collidesWith();
	}
	
	// Activar potenciadores
	public void setShield() {
		if(shieldOn)
			shieldTime = 0;
		shieldOn = true;
	}
	
	public void setDoubleScore() {
		if(doubleScoreOn)
			doubleScoreTime = 0;
		doubleScoreOn = true;
	}
	
	public void setFastFire() {
		if(fastFireOn)
			fastFireTime = 0;
		fastFireOn = true;
	}
	
	public void setDoubleGun() {
		if(doubleGunOn)
			doubleGunTime = 0;
		doubleGunOn = true;
	}
	
	// Método de destrucción
	@Override
	public void Destroy() {
		spawning = true;
		gameState.playExplosion(position);
		spawnTime = 0;
		loose.play();
		// Restar vida o terminar el juego si ya no hay más
		if(!gameState.subtractLife(position)) {
			gameState.gameOver();
			super.Destroy();
		}
		resetValues(); // Reinicia los valores del jugador
		
	}
	
	// Reiniciar valores tras la destrucción
	private void resetValues() {
		
		angle = 0;
		velocity = new Vector2D();
		position = GameState.PLAYER_START_POSITION;
	}
	
	// Dibuja al jugador
	@Override
	public void draw(Graphics g) {
		
		if(!visible)
			return; // No dibujar si el jugador es invisible
		
		
		Graphics2D g2d = (Graphics2D)g;
		
		AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + width/2 + 5,
				position.getY() + height/2 + 10);
		
		AffineTransform at2 = AffineTransform.getTranslateInstance(position.getX() + 5, position.getY() + height/2 + 10);
		
		at1.rotate(angle, -5, -10);
		at2.rotate(angle, width/2 -5, -10);
		
		// Efectos de aceleración
		if(accelerating)
		{
			g2d.drawImage(Assets.speed, at1, null);
			g2d.drawImage(Assets.speed, at2, null);
		}
		
		
		// Efecto de escudo
		if(shieldOn) {
			BufferedImage currentFrame = shieldEffect.getCurrentFrame();
			AffineTransform at3 = AffineTransform.getTranslateInstance(
					position.getX() - currentFrame.getWidth() / 2 + width/2,
					position.getY() - currentFrame.getHeight() / 2 + height/2);
			
			at3.rotate(angle, currentFrame.getWidth() / 2, currentFrame.getHeight() / 2);
					
			g2d.drawImage(shieldEffect.getCurrentFrame(), at3, null);
		}
		
		// Dibuja al jugador (con doble arma si aplica)
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		
		at.rotate(angle, width/2, height/2);
		
		if(doubleGunOn)
			g2d.drawImage(Assets.doubleGunPlayer, at, null);
		else
			g2d.drawImage(texture, at, null);
		
		/*g2d.setColor(Color.RED);
		
		g2d.drawOval(
				(int)(getCenter().getX() - Constants.SHIELD_DISTANCE / 2),
				(int)(getCenter().getY() - Constants.SHIELD_DISTANCE / 2),
				Constants.SHIELD_DISTANCE,
				Constants.SHIELD_DISTANCE);*/
		
	}
	
	// Getters para estados
	public boolean isSpawning() {return spawning;}
	public boolean isShieldOn() {return shieldOn;}
	public boolean isDoubleScoreOn() {return doubleScoreOn;}
}
