package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Clase MouseInput: maneja la entrada del ratón, incluyendo clics, movimientos y arrastres
public class MouseInput extends MouseAdapter{
	
	// Variables estáticas para rastrear el estado y la posición del ratón
	public static int X, Y; // Coordenadas actuales del ratón en la pantalla
	public static boolean MLB; // Estado del botón izquierdo del ratón (true si está presionado)
	 
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			MLB = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			MLB = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		X = e.getX();
		Y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		X = e.getX();
		Y = e.getY();
	}
	
}
