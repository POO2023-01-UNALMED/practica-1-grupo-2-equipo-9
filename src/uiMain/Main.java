package uiMain;

import baseDatos.*;
import gestorAplicación.Metas;
import gestorAplicación.Suscripcion;
import gestorAplicación.Usuario;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException {
		
		Usuario u1 = new Usuario("Juan Pablo", "Juan1@gmail.com", "Juanpa0128", 0, Suscripcion.DIAMANTE);
		System.out.println(u1.getSuscripcion().getLimite_Bancos());
		
		
		Scanner sc = new Scanner(System.in);
		int seguir = 1;
		
		/* La variable seguir se usa para poder terminar el programa. Por ejemplo cuando voy a salir del
		 * programa le asigno el valor de 0 para que termine.
		 * Esto mismo se usa de diferentes maneras para varias partes de la interfaz del usuario.
		*/
		while (seguir == 1) {
			// Interfaz de usuario
			System.out.println("Bienvenido al gestor de dinero, ¿en que te podemos ayudar?"
					+ "\n1. Ingresar a Cuenta"
					+ "\n2. Ingresar a Usuarios"
					+ "\n3. Ingresar a Metas"
					+ "\n4. Ingresar a Movimientos"
					+ "\n5. Salir");
			
			/* Cada vez que se se vaya a leer un entero por consola debe ponerse Integer.parseInt(sc.nextLine());. 
			 * De otro modo se ejecutara un \n que dañara el codigo. Lo mismo para los double. Para los String 
			 * si se puede redactar de manera usual sc.nextLine();. 
			*/
			
			int clase = Integer.parseInt(sc.nextLine());
			
			while (clase == 1) {
				// Contenido de Cuenta
				System.out.println("Bienvenido a Cuenta, ¿en que te podemos ayudar?"
						+ "\n5. Salir al menú principal");
				
				int opcion = Integer.parseInt(sc.nextLine());
				
				if (opcion == 5) {
					System.out.println("Redirigiendo al menú principal");
					clase = 0;
				}
			}
			
			while (clase == 2) {
				// Contenido de Usuario
				System.out.println("Bienvenido a Usuarios, ¿en que te podemos ayudar?"
						+ "\n5. Salir al menú principal");
				
				int opcion = Integer.parseInt(sc.nextLine());
				
				if (opcion == 5) {
					System.out.println("Redirigiendo al menú principal");
					clase = 0;
				}
			}
			
			while (clase == 3) {
				ArrayList<Metas> mel = (ArrayList<Metas>) Deserializador.deserializar_listas("Metas");
				ArrayList<Metas> me = new ArrayList<Metas>();
				
				System.out.println("Bienvenido a Metas, ¿en que te podemos ayudar?"
						+ "\n1. Crear una meta"
						+ "\n2. Eliminar una meta"
						+ "\n3. Ver mis metas"
						+ "\n4. Salir al menú principal");
				
				int opcion = Integer.parseInt(sc.nextLine());
				
				// Crear una meta
				while (opcion == 1) {
					System.out.println("Llene los siguientes campos para crear una meta");
					
					System.out.println("Nombre de la meta: ");
					String nombreMe = sc.nextLine();
					
					System.out.println("Cantidad de ahorro: ");
					double cantidadMe = Double.parseDouble(sc.nextLine());
					
					System.out.println("Fecha de la meta (en el formato dd/MM/yyyy): ");
					String fechaMe = sc.nextLine();
					
					int id = 1;
					
					Metas me4 = new Metas(nombreMe, cantidadMe, fechaMe, id);
					me.add(me4);
					Serializador.serializar(me, "Metas");
					System.out.println("Sus metas actuales son: ");
					id++;
					
					// Terminar o continuar. 
					System.out.println("¿Desea crear otra meta? "
							+ "\nEscriba 'y' para sí o 'n' para salir al menú de Metas");
					String c = sc.nextLine();
					
					if (c.equals("y")) {
						System.out.println("Continuar con la creación de metas");
						continue;
					}
					
					else if (c.equals("n")) {
						System.out.println("Redirigiendo al menú de Metas");
						opcion = 0;
					}
				}
				
				// Eliminar una meta
				while (opcion == 2) {
					System.out.println("¿Cual meta deseas eliminar?");
					
					// Mostrara opciones
					for (int i = 0; i < mel.size(); i++) {
						System.out.println(i + 1 + ". " + mel.get(i).getNombre());
					}
					
				    // Entrada de opcion que se desea eliminar
					int n = Integer.parseInt(sc.nextLine());
					mel.get(n - 1).setDel(n);
					
					// Aplicar el metodo
					mel.get(n - 1).eliminarMeta();
					
					// Terminar o continuar
					System.out.println("¿Desea eliminar otra meta? "
							+ "\nEscriba 'y' para sí o 'n' para salir al menú de Metas");
					String c = sc.nextLine();
					
					if (c.equals("y")) {
						System.out.println("Continuar con la eliminacion de metas");
						continue;
					}
					
					else if (c.equals("n")) {
						System.out.println("Redirigiendo al menú de Metas");
						opcion = 0;
					}
				}
				
				while (opcion == 3) {
					for (int i = 0; i < mel.size(); i++) {
						System.out.println(i + 1 + ". " + mel.get(i).getNombre() + " " + mel.get(i).getCantidad()
								+ " " + mel.get(i).getFecha());					
					}
					// Terminar o continuar. 
					System.out.println("¿Desea salir al menu de Metas? "
							+ "\nEscriba 'y' para salir");
					String c = sc.nextLine();
					
					if (c.equals("y")) {
						System.out.println("Continuar con la creación de metas");
						opcion = 0;
					}
				}
				
				if (opcion == 4) {
					System.out.println("Redirigiendo al menú principal");
					clase = 0;
				}
			}
			
			while (clase == 4) {
				// Contenido de Movimientos
				System.out.println("Bienvenido a Movimientos, ¿en que te podemos ayudar?"
						+ "\n5. Salir al menú principal");
				
				int opcion = Integer.parseInt(sc.nextLine());
				
				if (opcion == 5) {
					System.out.println("Redirigiendo al menú principal");
					clase = 0;
				}
			}
			
			if (clase == 5) {
				System.out.println("Hasta la próxima");
				seguir = 0;
			}
			
		}
		sc.close();
	}	
}