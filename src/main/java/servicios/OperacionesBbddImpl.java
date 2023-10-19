package servicios;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.LibroDto;
import util.ADto;

/**
 * Clase que implementa la interfaz que dan servicio al CRUD para las
 * operaciones con BBDD
 */
public class OperacionesBbddImpl implements OperacionesBbddInterface {

	@Override
	public void readLibro(Connection conexionGenerada, List<LibroDto> listaLibros) {
		Statement declaracionSQL = null;
		ResultSet resultadoConsulta = null;
		ADto adto = new ADto();

		try {

			// Se abre una declaración
			declaracionSQL = conexionGenerada.createStatement();
			// Se define la consulta de la declaración y se ejecuta
			resultadoConsulta = declaracionSQL.executeQuery("SELECT * FROM gbp_almacen.gbp_alm_cat_libros");

			// Llamada a la conversión a LibroDTO y añadido en su lista
			listaLibros = adto.pasarResultSetALibrosDto(resultadoConsulta);

			// Mostrando los libros al usuario
			if (listaLibros.isEmpty()) {
				JOptionPane.showMessageDialog(null, "¡¡No hay libros registrados que mostrar!!");
			} else {
				String mostrarTodosLibros = JOptionPane.showInputDialog(
						"Quiere que se muestren todos los libros o mostrar por id (Y = todos / Cualquier tecla = por id)? : ")
						.toUpperCase();
				if (mostrarTodosLibros.charAt(0) == 'Y') {
					mostarTodosLosLibros(listaLibros);
				} else {
					Integer idLibroAmostrar = Integer
							.parseInt(JOptionPane.showInputDialog("Introduce el id del libro a mostrar: "));
					mostrarLibroPorID(listaLibros, idLibroAmostrar);
				}
			}

			// Liberando los recursos
			resultadoConsulta.close();
			declaracionSQL.close();
			conexionGenerada.close();
			System.out.println("**INFO OperacionesBbddImpl readLibro** Cierre conexión a bbdd, statement y resulset");

		} catch (SQLException sqle) {
			System.out.println(
					"**ERROR OperacionesBbddImpl readLibro** Error generando o ejecutando la declaracionSQL: " + sqle);
			sqle.printStackTrace();
		} catch (HeadlessException he) {
			System.out.println(
					"**ERROR OperacionesBbddImpl readLibro** Error, el usuario no dispone de interfaz gráfica: "
							+ he.getMessage());
			he.printStackTrace();
		} catch (NumberFormatException nfe) {
			System.out.println("**ERROR OperacionesBbddImpl readLibro** Error al convertir una edición a entero: "
					+ nfe.getMessage());
			nfe.printStackTrace();
		}
	}

	/**
	 * Itera la lista con los libros y los muestra todos al usuario
	 * 
	 * @param listaLibros
	 */
	private void mostarTodosLosLibros(List<LibroDto> listaLibros) {
		for (LibroDto libro : listaLibros) {
			JOptionPane.showMessageDialog(null, libro.toString());
		}
	}

	/**
	 * Itera la lista con los libros y muestra al usuario el libro con id que
	 * introduzca
	 * 
	 * @param listaLibros
	 * @param idLibroAmostrar
	 */
	private void mostrarLibroPorID(List<LibroDto> listaLibros, Integer idLibroAmostrar) {
		boolean libroEncontrado = false;
		for (int i = 0; i < listaLibros.size(); i++) {
			if (listaLibros.get(i).getIdLibro() == idLibroAmostrar) {
				JOptionPane.showMessageDialog(null, listaLibros.get(i).toString());
				libroEncontrado = true;
			}
		}
		if (libroEncontrado == false) {
			JOptionPane.showMessageDialog(null, "No existe libro con id " + idLibroAmostrar);
		}
	}

	@Override
	public void createLibro(Connection conexionGenerada, List<LibroDto> listaLibros) {

		List<LibroDto> listaNuevosLibros = new ArrayList<LibroDto>();
		String registrarOtroLibro = "";

		try {
			// Recopila datos de libros desde el usuario
			do {
				String titulo = JOptionPane.showInputDialog("Introduce el título del nuevo libro a registrar: ");
				String autor = JOptionPane.showInputDialog("Introduce el autor del nuevo libro a registrar: ");
				String ISBN = JOptionPane.showInputDialog("Introduce el ISBN del nuevo libro a registrar: ");
				int edicion = Integer
						.parseInt(JOptionPane.showInputDialog("Introduce la edición del libro a registrar: "));

				listaNuevosLibros.add(new LibroDto(titulo, autor, ISBN, edicion));
				registrarOtroLibro = JOptionPane
						.showInputDialog("Quiere registrar otro libro más (Y = si / Cualquier tecla = no)?: ")
						.toUpperCase();
			} while (registrarOtroLibro.charAt(0) == 'Y');

			// Verifica que la lista de los nuevos libros a registrar no esté vacía
			if (!listaNuevosLibros.isEmpty()) {
				// Consulta a la bbdd con los valores parametrizados --> ?
				String query = "INSERT INTO gbp_almacen.gbp_alm_cat_libros (titulo, autor, isbn, edicion) VALUES (?,?,?,?)";
				// Consulta preparada con los 4 campos del nuevo libro a registrar
				PreparedStatement consultaPreparada = conexionGenerada.prepareStatement(query);

				// Iteramos la lista con los nuevos libros a registrar
				for (LibroDto libro : listaNuevosLibros) {

					consultaPreparada.setString(1, libro.getTitulo()); // Se establecen los parametros con los datos
					consultaPreparada.setString(2, libro.getAutor());
					consultaPreparada.setString(3, libro.getIsbn());
					consultaPreparada.setInt(4, libro.getEdicion());

					consultaPreparada.executeUpdate();
				}
				listaLibros.addAll(listaNuevosLibros);

				consultaPreparada.close(); // Liberando recursos cerrando el preparedstatement
				JOptionPane.showMessageDialog(null, listaNuevosLibros.size() + "  registro/s correcto");
			}
			conexionGenerada.close(); // Liberando recursos cerrando la conexion a bbdd
		} catch (SQLException e) {
			System.out.println(
					"**ERROR OperacionesBbddImpl createLibro** Error generando o ejecutando la declaración SQL: "
							+ e.getMessage());
			e.printStackTrace();

		} catch (NumberFormatException nfe) {
			System.out.println("**ERROR OperacionesBbddImpl createLibro** Error al convertir una edición a entero: "
					+ nfe.getMessage());
			nfe.printStackTrace();

		} catch (HeadlessException he) {
			System.out.println(
					"**ERROR OperacionesBbddImpl createLibro** Error, el usuario no dispone de interfaz gráfica: "
							+ he.getMessage());
			he.printStackTrace();

		}
	}

	@Override
	public void updateLibro(Connection conexionGenerada, List<LibroDto> listaLibros) {

		listaLibros = cargaListaLibrosActuales(conexionGenerada);
		mostarTodosLosLibros(listaLibros); // Muestra libros actuales para que pueda identificar cual modificar
		boolean libroAmodificarEsValido = false;
		Integer idLibroAmodificar;

		try {
			idLibroAmodificar = Integer
					.parseInt(JOptionPane.showInputDialog("Introduce el id del libro a modificar: "));
			libroAmodificarEsValido = compruebaIdLibro(listaLibros, idLibroAmodificar);

			if (libroAmodificarEsValido) {
				PreparedStatement consultaPreparada = null;
				String campoAmodificar = JOptionPane
						.showInputDialog(
								"Introduce el campo del libro que quiere modificar (titulo, autor, isbn o edicion)")
						.toLowerCase();

				switch (campoAmodificar) {

				case "titulo":

					String nuevoTitulo = JOptionPane
							.showInputDialog("Introduce el nuevo título del libro a modificar: ");
					String queryModificarTitulo = "UPDATE gbp_almacen.gbp_alm_cat_libros SET titulo = ? WHERE id_libro = ?";
					consultaPreparada = conexionGenerada.prepareStatement(queryModificarTitulo);
					consultaPreparada.setString(1, nuevoTitulo);
					consultaPreparada.setInt(2, idLibroAmodificar);
					consultaPreparada.executeUpdate();
					break;
				case "autor":
					String nuevoAutor = JOptionPane.showInputDialog("Introduce el nuevo autor del libro a modificar: ");
					String queryModificarAutor = "UPDATE gbp_almacen.gbp_alm_cat_libros SET autor = ? WHERE id_libro = ?";
					consultaPreparada = conexionGenerada.prepareStatement(queryModificarAutor);
					consultaPreparada.setString(1, nuevoAutor);
					consultaPreparada.setInt(2, idLibroAmodificar);
					consultaPreparada.executeUpdate();
					break;
				case "isbn":
					String nuevoISBN = JOptionPane.showInputDialog("Introduce el nuevo ISBN del libro a modificar: ");
					String queryModificarISBN = "UPDATE gbp_almacen.gbp_alm_cat_libros SET isbn = ? WHERE id_libro = ?";
					consultaPreparada = conexionGenerada.prepareStatement(queryModificarISBN);
					consultaPreparada.setString(1, nuevoISBN);
					consultaPreparada.setInt(2, idLibroAmodificar);
					consultaPreparada.executeUpdate();
					break;
				case "edicion":
					int nuevaEdicion = Integer.parseInt(
							JOptionPane.showInputDialog("Introduce la nueva edición del libro a modificar: "));
					String queryModificarEdicion = "UPDATE gbp_almacen.gbp_alm_cat_libros SET edicion = ? WHERE id_libro = ?";
					consultaPreparada = conexionGenerada.prepareStatement(queryModificarEdicion);
					consultaPreparada.setInt(1, nuevaEdicion);
					consultaPreparada.setInt(2, idLibroAmodificar);
					consultaPreparada.executeUpdate();
					break;
				default:
					JOptionPane.showMessageDialog(null, "No se reconoce el campo " + campoAmodificar + " introducido");
					break;
				}
				JOptionPane.showMessageDialog(null, campoAmodificar + " actualizado correctamente");
				consultaPreparada.close(); // Liberando recursos cerrando la consulta preparada
			} else {
				JOptionPane.showMessageDialog(null,
						"Libro con id " + idLibroAmodificar + " no encontrado en el sistema");
			}
			conexionGenerada.close(); // Liberando recursos cerrando la conexion a bbdd
		} catch (SQLException sqle) {
			System.out.println(
					"**ERROR OperacionesBbddImpl updateLibro** Error generando o ejecutando la declaración SQL: "
							+ sqle.getMessage());
			sqle.printStackTrace();
		} catch (NumberFormatException nfe) {
			System.out.println("**ERROR OperacionesBbddImpl updateLibro** Error al convertir una edición a entero: "
					+ nfe.getMessage());
			nfe.printStackTrace();
		} catch (HeadlessException he) {
			System.out.println(
					"**ERROR OperacionesBbddImpl updateLibro** Error, el usuario no dispone de interfaz gráfica: "
							+ he.getMessage());
			he.printStackTrace();
		}

	}

	@Override
	public void deleteLibro(Connection conexionGenerada, List<LibroDto> listaLibros) {

		listaLibros = cargaListaLibrosActuales(conexionGenerada);
		mostarTodosLosLibros(listaLibros); // Muestra libros actuales para que pueda identificar cual eliminar
		boolean libroAeliminarEsValido = false;
		Integer idLibroAeliminar;

		try {
			idLibroAeliminar = Integer.parseInt(JOptionPane.showInputDialog("Introduce el id del libro a eliminar: "));
			libroAeliminarEsValido = compruebaIdLibro(listaLibros, idLibroAeliminar);

			if (libroAeliminarEsValido) {
				PreparedStatement consultaPreparada = null;
				String queryEliminar = "DELETE FROM gbp_almacen.gbp_alm_cat_libros WHERE id_libro = ?";
				consultaPreparada = conexionGenerada.prepareStatement(queryEliminar);
				consultaPreparada.setInt(1, idLibroAeliminar);
				consultaPreparada.executeUpdate();
				JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
				consultaPreparada.close(); // Liberando recursos cerrando la consulta preparada
			} else {
				JOptionPane.showMessageDialog(null, "No se ha eliminado nada");
			}
			
			conexionGenerada.close(); // Liberando recursos cerrando la conexion a bbdd
		} catch (SQLException sqle) {
			System.out.println(
					"**ERROR OperacionesBbddImpl deleteLibros** Error generando o ejecutando la declaración SQL: "
							+ sqle.getMessage());
			sqle.printStackTrace();
		} catch (NumberFormatException nfe) {
			System.out.println("**ERROR OperacionesBbddImpl deleteLibro** Error al convertir una edición a entero: "
					+ nfe.getMessage());
			nfe.printStackTrace();
		} catch (HeadlessException he) {
			System.out.println(
					"**ERROR OperacionesBbddImpl deleteLibro** Error, el usuario no dispone de interfaz gráfica: "
							+ he.getMessage());
			he.printStackTrace();
		}

	}

	/**
	 * Comprueba el id introducido por el usuario existe en la bbdd
	 * 
	 * @param listaLibros
	 * @param idLibroAeliminar
	 * @return
	 */
	private boolean compruebaIdLibro(List<LibroDto> listaLibros, int idLibroAencontrar) {
		for (LibroDto libro : listaLibros) {
			if (libro.getIdLibro() == idLibroAencontrar) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Método que realiza una consulta en BBDD y carga todos los libros en la lista
	 * para poder trabajar con ellos
	 * 
	 * @param conexionGenerada
	 * @return listaLibros con los libros registrados actualmente en bbdd
	 */
	private List<LibroDto> cargaListaLibrosActuales(Connection conexionGenerada) {
		Statement declaracionSQL = null;
		ResultSet resultadoConsulta = null;
		ADto adto = new ADto();
		List<LibroDto>listaLibros = new ArrayList<LibroDto>();

		try {

			// Se abre una declaración
			declaracionSQL = conexionGenerada.createStatement();
			// Se define la consulta de la declaración y se ejecuta
			resultadoConsulta = declaracionSQL.executeQuery("SELECT * FROM gbp_almacen.gbp_alm_cat_libros");

			// Llamada a la conversión a LibroDTO y añadido en su lista
			listaLibros = adto.pasarResultSetALibrosDto(resultadoConsulta);

			// Liberando los recursos
			resultadoConsulta.close();
			declaracionSQL.close();
			System.out.println(
					"**INFO OperacionesBbddImpl cargaListaLibrosActuales** Cierre conexión a bbdd, statement y resulset");

		} catch (SQLException sqle) {
			System.out.println(
					"**ERROR OperacionesBbddImpl cargaListaLibrosActuales** Error generando o ejecutando la declaracionSQL: "
							+ sqle);
			sqle.printStackTrace();
		}
		return listaLibros;

	}

}
