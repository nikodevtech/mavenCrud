package servicios;

/**
 * Interface que contiene los metodos que dan servicio al menu de opciones
 */
public interface MenuInterface {

	/**
	 * Método encargado de mostrar al usuario un menú por consola con las opciónes
	 * disponibles
	 * 
	 * @return Número entero en relación a la opción seleccionada (-1 en caso de ser null el valor)
	 */
	public int mostrarMenu();
}
