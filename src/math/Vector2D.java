package math;

//Clase que representa un vector en dos dimensiones.
public class Vector2D {
	private double x,y; // Componentes x e y del vector.
	
	// Constructor que inicializa el vector con valores dados.
	public Vector2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	// Constructor que copia los valores de otro vector.
	public Vector2D(Vector2D v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	 // Constructor por defecto, inicializa el vector en el origen (0, 0).
	public Vector2D()
	{
		x = 0;
		y = 0;
	}	
	
	// Suma este vector con otro y devuelve un nuevo vector con el resultado.
	public Vector2D add(Vector2D v)
	{
		return new Vector2D(x + v.getX(), y + v.getY());
	}
	
	// Resta otro vector de este y devuelve un nuevo vector con el resultado.
	public Vector2D subtract(Vector2D v)
	{
		return new Vector2D(x - v.getX(), y - v.getY());
	}

	// Escala el vector multiplicando sus componentes por un valor escalar.
	public Vector2D scale(double value)
	{
		return new Vector2D(x*value, y*value);
	}
	
	// Limita la magnitud del vector a un valor máximo dado.
	public Vector2D limit(double value)
	{ // Si la magnitud actual supera el límite, normaliza el vector y escálalo al límite.
		if(getMagnitude() > value)
		{
			return this.normalize().scale(value);
		}
		return this; // Si no excede el límite, devuelve el mismo vector.
	}
	
	/ Normaliza el vector para que tenga una magnitud de 1 (dirección unitaria).
	public Vector2D normalize()
	{
		double magnitude = getMagnitude();
		 // Divide las componentes del vector por su magnitud.
		return new Vector2D(x / magnitude, y / magnitude);
	}
	
	// Calcula y devuelve la magnitud (longitud) del vector.
	public double getMagnitude()
	{
		return Math.sqrt(x*x + y*y); // Fórmula: sqrt(x^2 + y^2).
	}
	
	// Establece una nueva dirección para el vector, manteniendo su magnitud actual.
	public Vector2D setDirection(double angle)
	{
		double magnitude = getMagnitude();
		// Usa el ángulo dado para calcular las nuevas componentes con funciones trigonométricas.
		return new Vector2D(Math.cos(angle)*magnitude, Math.sin(angle)*magnitude);
	}
	 // Devuelve el ángulo actual del vector en radianes.
	public double getAngle() {
		return Math.asin(y/getMagnitude()); // Fórmula: arcsin(y/magnitud).
	}
	
	 // Obtiene el valor de la componente X.
	public double getX() {
		return x;
	}

	// Establece un nuevo valor para la componente X.
	public void setX(double x) {
		this.x = x;
	}

	// Obtiene el valor de la componente Y.
	public double getY() {
		return y;
	}

	// Establece un nuevo valor para la componente Y.
	public void setY(double y) {
		this.y = y;
	}
	
	
}
