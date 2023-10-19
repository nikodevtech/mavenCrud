package servicios;

import java.awt.HeadlessException;

import javax.swing.JOptionPane;

/**
 * Clase que implementa la interfaz MenuInterface y es donde se detalla la funcionalidad
 * concreta de los métodos de dicha interfaz.
 */
public class MenuImpl implements MenuInterface{
	
	@Override
	public int mostrarMenu() {
		try {
		return Integer.parseInt(JOptionPane.showInputDialog(
				"\n--- MENU ---\n" + "\n1. Registrar libro\n" + "2. Mostrar libro\n3. Modificar libro"
						+ "\n4. Eliminar libro\n0. Salir\n" + "\nIntroduce la opción deseada: "));
		}catch(NumberFormatException nfe) {
			System.out.println("**ERROR MenuImpl MostrarMenu** Error con el tipo de dato al convertir a string: " + nfe);
			return -1;
		}catch(HeadlessException he) {
			System.out.println("**ERROR MenuImpl MostrarMenu** Error el usuario no tiene interfaz gráfica: " + he);
			return -1;
		}
	}

}
