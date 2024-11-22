package graphics;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

//Clase Sound: encapsula el manejo de clips de audio y facilita la reproducción, bucles, y control de volumen
public class Sound {
	
	private Clip clip; // Objeto Clip que representa el sonido a reproducir
	private FloatControl volume; // Control para ajustar el volumen del sonido
	
	// Constructor: inicializa el Clip y su control de volumen
	public Sound(Clip clip) {
		this.clip = clip; // Asigna el clip proporcionado
		volume = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN); // Obtiene el control de volumen maestro del Clip
	}
	
	// Método play: reproduce el sonido desde el inicio
	public void play() {
		clip.setFramePosition(0); // Reinicia el clip al inicio
		clip.start(); // Comienza la reproducción
	}
	
	// Método loop: reproduce el sonido en bucle continuo
	public void loop() {
		clip.setFramePosition(0); // Reinicia el clip al inici
		clip.loop(Clip.LOOP_CONTINUOUSLY); // Inicia la reproducción en bucle infinito
	}
	
	// Método stop: detiene la reproducción del sonido
	public void stop() {
		clip.stop(); // Detiene la reproducción del Clip
	}
	
	// Método getFramePosition: devuelve la posición actual del frame en el Clip
	public int getFramePosition() {
		return clip.getFramePosition(); / Retorna el frame actual de reproducción
	}
	
	// Método changeVolume: cambia el volumen del Clip
	public void changeVolume(float value) {
		volume.setValue(value); // Ajusta el volumen al valor especificado
	}
	
}
