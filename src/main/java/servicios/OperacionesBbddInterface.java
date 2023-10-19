package servicios;

import java.sql.Connection;
import java.util.List;
import dto.LibroDto;

/**
 * Interfaz que contiene los métodos CRUD que realizan operaciones con la BBDD
 */
public interface OperacionesBbddInterface {

	/**
	 * Método que realiza el SELECT para ver los registros de los libros
	 * @param conexionGenerada
	 * @param listaLibros actuales
	 */
	public void readLibro(Connection conexionGenerada, List<LibroDto>listaLibros);
	/**
	 * Método que realiza el INSERT INTO para realizar un nuevo registro de libro
	 * @param conexionGenerada
	 *  @param listaLibros actuales
	 */
	public void createLibro(Connection conexionGenerada, List<LibroDto>listaLibros);
	/**
	 * Método que realiza el UPDATE para modificar un registro de libro
	 * @param conexionGenerada
	 * @param listaLibros actuales
	 */
	public void updateLibro(Connection conexionGenerada, List<LibroDto>listaLibros);
	/**
	 * Método que realiza el DELETE para elinminar un registro de libro
	 * @param conexionGenerada
	 * @param listaLibros actuales
	 */
	public void deleteLibro(Connection conexionGenerada, List<LibroDto>listaLibros);
}
