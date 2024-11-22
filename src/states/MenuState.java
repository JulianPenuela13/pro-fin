package states;

import java.awt.Graphics;
import java.util.ArrayList;

import gameObjects.Constants;
import graphics.Assets;
import ui.Action;
import ui.Button;

//Clase que representa el estado del menú principal del juego, extendiendo la clase base State.
public class MenuState extends State{
	
	private ArrayList<Button> buttons; // Lista de botones que aparecen en el menú.
	
	// Constructor para inicializar el menú principal.
	public MenuState() {
		buttons = new ArrayList<Button>(); // Inicializa la lista de botones.
		
		// Botón "PLAY", inicia el juego
		buttons.add(new Button(
				Assets.greyBtn, // Imagen del botón en estado normal.
				Assets.blueBtn, // Imagen del botón en estado hover.
				Constants.WIDTH / 2 - Assets.greyBtn.getWidth()/2, // Coordenada X centrada.
				Constants.HEIGHT / 2 - Assets.greyBtn.getHeight() * 2, // Coordenada Y ajustada hacia arriba.
				Constants.PLAY, // Texto que aparecerá en el botón.
				new Action() { // Acción que se ejecutará al hacer clic
					@Override
					public void doAction() {
						State.changeState(new GameState()); // Cambia al estado del juego.

					}
				}
				));
		
		// Botón "EXIT", cierra la aplicación.
		buttons.add(new Button(
				Assets.greyBtn, // Imagen del botón en estado normal.
				Assets.blueBtn, // Imagen del botón en estado hover.
				Constants.WIDTH / 2 - Assets.greyBtn.getWidth()/2, // Coordenada X centrada.
				Constants.HEIGHT / 2 + Assets.greyBtn.getHeight() * 2 , // Coordenada Y ajustada hacia abajo.
				Constants.EXIT, // Texto que aparecerá en el botón.
				new Action() { // Acción que se ejecutará al hacer 
					@Override
					public void doAction() {
						System.exit(0); // Termina la ejecución del programa.
					}
				}
				));
		// Botón "HIGH SCORES", muestra las puntuaciones más alt
		buttons.add(new Button(
				Assets.greyBtn, // Imagen del botón en estado normal.
				Assets.blueBtn, // Imagen del botón en estado hover.
				Constants.WIDTH / 2 - Assets.greyBtn.getWidth()/2, // Coordenada X centrada.
				Constants.HEIGHT / 2, // Coordenada Y centrada.
				Constants.HIGH_SCORES, // Texto que aparecerá en el botón.
				new Action() {  // Acción que se ejecutará al hacer clic.
					@Override
					public void doAction() {
						State.changeState(new ScoreState()); // Cambia al estado de puntuaciones.
					}
				}
				));
		
		
	}
	
	  // Método para actualizar el estado de los botones.
	@Override
	public void update(float dt) {
		for(Button b: buttons) {
			b.update(); // Actualiza cada botón (manejo de hover y clic).
		}
	}
	// Método para dibujar los elementos en pantalla.
	@Override
	public void draw(Graphics g) {
		for(Button b: buttons) {
			b.draw(g); // Dibuja cada botón en la pantal
		}
	}

}
