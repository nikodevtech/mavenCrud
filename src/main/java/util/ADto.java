package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.LibroDto;

/**
 * Clase ADTO del paquete util cuya función es pasar los datos del libro a Dto
 */
public class ADto {

	/**
	 * Método que convierte una estructura de datos ResultSet con campos de libros a list de LibroDto
	 * @param resultadoConsulta --> estructura similar a array con datos de los libros
	 * @return list con LibroDtos
	 */
	public List<LibroDto> pasarResultSetALibrosDto(ResultSet resultadoConsulta) {

		List<LibroDto> listaLibros = new ArrayList<LibroDto>();
		try {
			while (resultadoConsulta.next()) { // Leemos el resultado de la consulta hasta que no queden filas

				listaLibros.add(new LibroDto(resultadoConsulta.getLong("id_libro"),
						resultadoConsulta.getString("titulo"), resultadoConsulta.getString("autor"),
						resultadoConsulta.getString("isbn"), resultadoConsulta.getInt("edicion")));
			}

			System.out.println("**INFO ConvertirADto resultsALibrosDto** Número libros: " + listaLibros.size());

		} catch (SQLException e) {
			System.out.println("**ERROR ADto pasarResultSetALibrosDto** Error al pasar el resultset a lista de LibroDto" + e);
		}

		return listaLibros;

	}
}
