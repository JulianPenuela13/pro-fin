package gameObjects;

import java.awt.image.BufferedImage;

import graphics.Assets;

//Definición del enumerador Size que representa diferentes tamaños
//cada uno con propiedades específicas como cantidad y texturas asociadas.
public enum Size {
	
	// Constantes del enumerador con sus valores asociados:
	// BIG: tamaño grande, MED: tamaño mediano, SMALL: tamaño pequeño, TINY: tamaño muy pequeño.
	BIG(2, Assets.meds), // Tamaño grande, con cantidad 2 y texturas de "Assets.meds".
	MED(2, Assets.smalls), // Tamaño mediano, con cantidad 2 y texturas de "Assets.smalls".
	SMALL(2, Assets.tinies), // Tamaño pequeño, con cantidad 2 y texturas de "Assets.tinies".
	TINY(0, null); // Tamaño muy pequeño, con cantidad 0 y sin texturas asociadas (null).

     
	public int quantity; // Atributo que define la cantidad asociada a cada tamaño.
	
	public BufferedImage[] textures; // Array que contiene las texturas gráficas asociadas a cada tamaño.
	
	// Constructor privado del enumerador. 
	// Se usa para inicializar los valores de las constantes BIG, MED, SMALL y TINY.
	private Size(int quantity, BufferedImage[] textures){
		this.quantity = quantity; // Asigna el valor de cantidad al atributo "quantity".
		this.textures = textures; // Asigna las texturas al atributo "textures".
	}
	
}
