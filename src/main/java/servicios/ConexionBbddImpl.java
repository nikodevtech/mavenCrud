package servicios;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase que implementa la interfaz que establece y configura la conexión a la BBDD definiendo el detalle
 */
public class ConexionBbddImpl implements ConexionBbddInterface {

	@Override
	public Connection generarConexion() {

		Connection conexion = null;
		// url, user y pass obtenido del .properties 
		String[] parametrosConexion = configuracionConexion();

		// Se controla que los parámetros de conexión se completen correctamente para que se establezca
		if (!parametrosConexion[2].isEmpty()) { 
			try {
				Class.forName("org.postgresql.Driver");

				// Se establece la conexión
				conexion = DriverManager.getConnection(parametrosConexion[0], parametrosConexion[1],
						parametrosConexion[2]);
				boolean esValida = conexion.isValid(50000);
				if (esValida == false) {
					conexion = null;
				}
				System.out.println(esValida ? "**INFO ConexionBbddImpl generaConexion** Conexión a BBDD correcta"
						: "**ERROR ConexionBbddImpl generaConexion** Conexión a BBDD incorrecta");
				return conexion;

			} catch (ClassNotFoundException cnfe) {
				System.out.println("**ERROR ConexionBbddImpl generaConexion** Error con el driver de la BBDD: " + cnfe);
				return conexion = null;
			} catch (SQLException sqle) {
				System.out.println("**ERROR ConexionBbddImpl generaConexion** Error en la conexión a la BBDD ("
						+ parametrosConexion[0] + "): " + sqle);
				return conexion = null;
			}

		} else {
			System.out.println(
					"**ERROR ConexionBbddImpl generaConexion** Parametros de conexion no se han establecido correctamente");
			return conexion;
		}
	}

	/**
	 * Obtiene los datos para la conexión (url, user y pass) obtenido del fichero .properties 
	 * @return un array con los datos necesarios para establecer la conexion a la bbdd 
	 */
	private String[] configuracionConexion() {

		String usuario = "", contraseña = "", puerto = "", servidor = "", nombreBaseDatos = "", url = "";

		Properties propiedadesConexionBbdd = new Properties();
		try {
			propiedadesConexionBbdd.load(new FileInputStream(new File(
					".\\src\\main\\java\\util\\conexion_postgresql.properties")));
			usuario = propiedadesConexionBbdd.getProperty("user");
			contraseña = propiedadesConexionBbdd.getProperty("pass");
			puerto = propiedadesConexionBbdd.getProperty("port");
			servidor = propiedadesConexionBbdd.getProperty("host");
			nombreBaseDatos = propiedadesConexionBbdd.getProperty("db");
			url = "jdbc:postgresql://" + servidor + ":" + puerto + "/" + nombreBaseDatos;
			String[] stringConfiguracion = { url, usuario, contraseña };

			return stringConfiguracion;

		} catch (Exception e) {
			System.out.println(
					"**ERROR ConexionBbddImpl configuracionConexion** - Error al acceder al fichero properties de conexion.");
			return null;
		}

	}

}
