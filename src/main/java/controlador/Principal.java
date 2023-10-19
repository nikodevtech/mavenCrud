package controlador;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import dto.LibroDto;
import servicios.ConexionBbddImpl;
import servicios.ConexionBbddInterface;
import servicios.MenuImpl;
import servicios.MenuInterface;
import servicios.OperacionesBbddImpl;
import servicios.OperacionesBbddInterface;

/*
 * Clase por la que inicia la aplicación
 */
public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		boolean cerrarMenu = false;
		int opcion;

		MenuInterface menuInterface = new MenuImpl();
		ConexionBbddInterface conexionBbdd = new ConexionBbddImpl();
		OperacionesBbddInterface operacionBbdd = new OperacionesBbddImpl();
		List<LibroDto> listaLibros = new ArrayList<LibroDto>();
		Connection conexion = null;

		do {
			try {

				conexion = conexionBbdd.generarConexion();

				if (conexion != null) {

					opcion = menuInterface.mostrarMenu();  

					switch (opcion) {
					
					case 1:
						operacionBbdd.createLibro(conexion, listaLibros);
						break;
					case 2:
						operacionBbdd.readLibro(conexion, listaLibros);
						break;
					case 3:
						operacionBbdd.updateLibro(conexion,listaLibros);
						break;
					case 4:
						operacionBbdd.deleteLibro(conexion, listaLibros);
						break;
					case 0:
						cerrarMenu = true;
						break;
					case -1:
						cerrarMenu = true; //Caso para cerrar la aplicación con botón cancelar en el menu
						break;
					}
				}

			} catch (Exception e) {
				System.out.println("\n**ERROR ocurrió una excepción no esperada" + e.getMessage() + " **");
			} 

		} while (!cerrarMenu);
		System.out.println("\nDesconectando, saliendo de la aplicación!");

	}

}
