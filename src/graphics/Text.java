package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import math.Vector2D;

//Clase Text: proporciona un método para dibujar texto en la pantalla con opciones de centrado, color y fuente
public class Text {
	//Método estático drawText: dibuja texto en la pantalla con opciones personalizables.
	public static void drawText(Graphics g, String text, Vector2D pos, boolean center, Color color, Font font) {
		g.setColor(color); // Establece el color del texto
		g.setFont(font); // Establece la fuente para el texto
		Vector2D position = new Vector2D(pos.getX(), pos.getY());  // Copia la posición inicial para evitar modificar el objeto origina
		
		// Si el texto debe ser centrado, ajusta la posición según las dimensiones del texto
		if(center) {
			FontMetrics fm = g.getFontMetrics(); // Obtiene las métricas de la fuente actual
			position.setX(position.getX() - fm.stringWidth(text) / 2); // Ajusta la posición X para centrar el texto horizontalmente
			position.setY(position.getY() - fm.getHeight() / 2); // Ajusta la posición Y para centrar el texto verticalmente
			
		}
		
		// Dibuja el texto en la posición ajustada
		g.drawString(text, (int)position.getX(), (int)position.getY());
		
	}
}
