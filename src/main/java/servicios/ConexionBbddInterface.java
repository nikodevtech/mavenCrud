package servicios;

import java.sql.Connection;
/**
 * Interface que configura, genera y establece conexión a BBDD con la configuración dada en el .properties
 */
public interface ConexionBbddInterface {
	/**
	 * Establece un canal de comunicación con BBDD
	 * @return Objeto Connection que representa una canal de conexión a bbdd abierta
	 */
	public Connection generarConexion();

}
