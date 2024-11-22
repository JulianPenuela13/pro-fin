package states;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import gameObjects.Constants;
import graphics.Assets;
import graphics.Text;
import io.JSONParser;
import io.ScoreData;
import math.Vector2D;
import ui.Action;
import ui.Button;

//Clase que representa el estado de puntuaciones más altas, extiende la clase base State.
public class ScoreState extends State{
	
	private Button returnButton; // Botón para regresar al menú principal.
	
	private PriorityQueue<ScoreData> highScores; // Cola de prioridad para almacenar las puntuaciones más altas
	
	private Comparator<ScoreData> scoreComparator; // Comparador para ordenar las puntuaciones.
	
	private ScoreData[] auxArray; // Arreglo auxiliar para ordenar y mostrar las puntuaciones.
	
	// Constructor que inicializa el estado de las puntuaciones altas.
	public ScoreState() {
		// Configuración del botón para regresar al menú principal.
		returnButton = new Button(
				Assets.greyBtn, // Imagen del botón en estado normal.
				Assets.blueBtn, // Imagen del botón en estado hover.
				Assets.greyBtn.getHeight(), // Coordenada X.
				Constants.HEIGHT - Assets.greyBtn.getHeight() * 2, // Coordenada Y.
				Constants.RETURN, // Texto del botón.
				new Action() { // Acción que se ejecutará al hacer clic.
					@Override
					public void doAction() {
						State.changeState(new MenuState()); // Cambia al estado del menú principal.
	                
					}
				}
			);
		// Configura un comparador para ordenar las puntuaciones de menor a mayor.
		scoreComparator = new Comparator<ScoreData>() {
			@Override
			public int compare(ScoreData e1, ScoreData e2) {
				return e1.getScore() < e2.getScore() ? -1: e1.getScore() > e2.getScore() ? 1: 0;
			}
		};
		// Inicializa la cola de prioridad con capacidad máxima de 10 y el comparador.
		highScores = new PriorityQueue<ScoreData>(10, scoreComparator);
		
		try {
			// Lee las puntuaciones desde un archivo JSON.
			ArrayList<ScoreData> dataList = JSONParser.readFile();
			 // Agrega cada puntuación a la cola de prioridad.
			for(ScoreData d: dataList) {
				highScores.add(d); // Elimina las puntuaciones más bajas
			}
			// Limita las puntuaciones a las 10 más altas.
			while(highScores.size() > 10) {
				highScores.poll();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); // Manejo de errores si no se encuentra el archivo.
		}
		
	}
	// Método que actualiza el estado de los elementos del menú (botón de regreso).
	@Override
	public void update(float dt) {
		returnButton.update();// Actualiza el estado del botón.
	}
	 // Método que dibuja los elementos en pantalla.
	@Override
	public void draw(Graphics g) {
		returnButton.draw(g); // Dibuja el botón de regreso.
		
		// Convierte la cola de prioridad a un arreglo para ordenar las puntuaciones.
		auxArray = highScores.toArray(new ScoreData[highScores.size()]);
		
		Arrays.sort(auxArray, scoreComparator); // Ordena las puntuaciones de menor a mayor.

		
		// Configura las posiciones iniciales para dibujar las puntuaciones y fechas.
		Vector2D scorePos = new Vector2D(
				Constants.WIDTH / 2 - 200,
				100
				); // Posición para las puntuaciones.
		Vector2D datePos = new Vector2D(
				Constants.WIDTH / 2 + 200,
				100
				);  // Posición para las fechas.
		
		// Dibuja los encabezados "SCORE" y "DATE".
		Text.drawText(g, Constants.SCORE, scorePos, true, Color.BLUE, Assets.fontBig);
		Text.drawText(g, Constants.DATE, datePos, true, Color.BLUE, Assets.fontBig);
		
		// Ajusta la posición vertical para mostrar las puntuaciones.
		scorePos.setY(scorePos.getY() + 40);
		datePos.setY(datePos.getY() + 40);
		
		// Recorre las puntuaciones en orden descendente (de mayor a menor
		for(int i = auxArray.length - 1; i > -1; i--) {
			
			ScoreData d = auxArray[i]; // Obtiene la puntuación actual.
			
			 // Dibuja la puntuación y la fecha correspondiente.
			Text.drawText(g, Integer.toString(d.getScore()), scorePos, true, Color.WHITE, Assets.fontMed);
			Text.drawText(g, d.getDate(), datePos, true, Color.WHITE, Assets.fontMed);
			
			// Ajusta las posiciones verticales para la siguiente fil
			scorePos.setY(scorePos.getY() + 40);
			datePos.setY(datePos.getY() + 40);
			
		}
		
	}
	
}
