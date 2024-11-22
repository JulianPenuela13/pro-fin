package graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//Clase Loader: se encarga de cargar recursos como imágenes, fuentes y sonidos desde rutas específicas
public class Loader {
	
	// Método ImageLoader: carga una imagen desde el path especificado
	public static BufferedImage ImageLoader(String path)
	{
		try { // Intenta leer la imagen desde el recurso especificado y retornarla
			return ImageIO.read(Loader.class.getResource(path));
		} catch (IOException e) { // Si ocurre un error de entrada/salida, imprime la traza del error
			e.printStackTrace();
		}
		// Si ocurre algún error, retorna null como valor predeterminado
		return null;
	}
	
	// Método loadFont: carga una fuente desde el path especificado y establece su tamaño
	public static Font loadFont(String path, int size) {
			try { // Crea una fuente TrueType desde el recurso especificado y establece el tamaño
				return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
			} catch (FontFormatException | IOException e) { // Si ocurre un error de formato de fuente o entrada/salida, imprime la traza del error
				e.printStackTrace();
				// Retorna null si no se pudo cargar la fuente
				return null;
			}
	}
	
	// Método loadSound: carga un sonido desde el path especificado y retorna un objeto Clip
	public static Clip loadSound(String path) {
		try { // Crea un objeto Clip para reproducir sonidos
			Clip clip = AudioSystem.getClip(); // Abre el archivo de audio desde el recurso especificado
			clip.open(AudioSystem.getAudioInputStream(Loader.class.getResource(path)));
			return clip; // Retorna el Clip cargado
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// Si ocurre un error (disponibilidad de línea, formato no soportado, o entrada/salida), imprime la traza del error
            e.printStackTrace();
			e.printStackTrace();
		}
		// Si ocurre algún error, retorna null como valor predeterminado
		return null;
	}
	
}
