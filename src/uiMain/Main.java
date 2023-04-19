package uiMain;

import baseDatos.*;
import gestorAplicación.Banco;
import gestorAplicación.Categoria;
import gestorAplicación.Cuenta;
import gestorAplicación.Estado;
import gestorAplicación.Metas;
import gestorAplicación.Movimientos;
import gestorAplicación.Suscripcion;
import gestorAplicación.Usuario;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException {
		
		Estado e1 = new Estado("Colombia", 0.65, 0.2);
		Banco b1 = new Banco("Bancolombia", 0.3, e1);
		Banco b2 = new Banco("Davivienda", 0.5, e1);
		Usuario u1 = new Usuario("Juan Pablo", "Juan1@gmail.com", "Juanpa0128", 0, Suscripcion.DIAMANTE);
		Cuenta c1 = new Cuenta(b1, "Ahorros", 1234, "COP", "Cuenta 1");
		Cuenta c2 = new Cuenta(b2, "Corriente", 5678, "USD", "Cuenta 2");
		System.out.println(u1.asociarBanco(b1));
		System.out.println(u1.asociarBanco(b2));
		System.out.println(u1.asociarCuenta(c1));
		System.out.println(u1.asociarCuenta(c2));
		c1.setSaldo(500.0);
		c2.setSaldo(500.0);
		System.out.println(u1.mostrarBancosAsociados());
		System.out.println(Movimientos.crearMovimiento(c1, c2, u1, 1, 250.0, Categoria.EDUCACION, new Date()));
		System.out.println(c1.getSaldo());
		System.out.println(c1.invertirSaldo());
		
		
		
		Scanner sc = new Scanner(System.in);
		int seguir = 1;
		
		/* LA VARIABLE SEGUIR SE USA PARA PODER TERMINAR EL PROGRAMA. POR EJEMPLO CUANDO VOY A SALIR DEL
		 * PROGRAMA LE ASIGNO EL VALOR DE 0 PARA QUE TERMINE.
		 * ESTO MISMO SE USA DE DIFERENTES MANERAS PARA VARIAS PARTES DE LA INTERFAZ DEL USUARIO. */
		
		int sesioniniciada = 0;
		// La variable sesioniniciada tiene una función análoga a la de seguir, en este caso será útil para volver a pedir
		// los datos del usuario.
		
		// Interfaz de usuario
		System.out.println("Bienvenido al gestor de dinero."
				+ "\n1. Ingresar Usuario"
				+ "\n2. Crear Usuario"
				+ "\n3. Cerrar Programa");
			
		int opcionUsuario = Integer.parseInt(sc.nextLine());
		System.out.println("");	
		
		while(seguir == 1) {
			if (opcionUsuario == 1) {
				System.out.println("Ingrese nombre de usuario o correo electrónico: ");
				String usuario = sc.nextLine();
				System.out.println("");
				System.out.println("Ingrese su contraseña: ");
				String contraseña = sc.nextLine();
				System.out.println("");
				
				boolean credencial = Usuario.verificarCredenciales(usuario, contraseña);
				if (credencial) {
					sesioniniciada = 1;
				} else {
					System.out.println("Las credenciales son incorrectas, ingrese nuevamente");
				}
			
			} else if(opcionUsuario == 2) {
				
				//Creación de un Usuario. Recordar validación de igualdad de usuarios y/o correos.
				System.out.println("Para crear un usuario nuevo, por favor diligencie los siguiente datos: ");
				
				System.out.println("Nombre: ");
				String nombreUsuario = sc.nextLine();
				System.out.println("");
					
				System.out.println("Correo electrónico: ");
				String correoElectronico = sc.nextLine();
				System.out.println("");
				
				System.out.println("Identificación: ");
				
				System.out.println("Contraseña: ");
				String contraseña = sc.nextLine();
				System.out.println("");
				
				System.out.println("Verificar contraseña: ");
				String verificacionContraseña = sc.nextLine();
				System.out.println("");
				
				

			} else if(opcionUsuario == 3){
				seguir = 0;
			} else {	
				System.out.println("Entrada no valida");
				System.out.println("NOTA: Recuerde que debe ingresar el numeral de la opción que desea escoger.");
				System.out.println("");
			}
		}
		
			
		while (sesioniniciada == 1) {	
			System.out.println("Bienvenido, " //Colocar nombre
					+ " a tu gestor de dinero, ¿a qué sección deseas ingresar?"
					+ "\n1. Mis productos"
					//+ "\n2. Ingresar a Usuarios"
					+ "\n3. Mis metas"
					+ "\n4. Mis movimientos"
					+ "\n5. Cerrar sesión");
			
			/* CADA VEZ QUE SE VAYA A LEER UN ENTERO POR CONSOLA DEBE PONERSE INTEGER.PARSEINT(SC.NEXTLINE()); 
			 * DE OTRO MODO SE EJECUTARÁ UN \n QUE DAÑARÁ EL CODIGO. LO MISMO PARA LOS DOUBLE. PARA LOS STRING 
			 * SI SE PUEDE REDACTAR DE MANERA USUAL USANDO SC.NEXTLINE();. */
			
			int seccion = Integer.parseInt(sc.nextLine());
			System.out.println("");
			
			if (seccion <= 0 & seccion > 5) {	
				System.out.println("Entrada no valida");
				System.out.println("");
				continue;
				}
			
			// CLASE DE CUENTA
			while (seccion == 1) {
				// Contenido de Cuenta
				System.out.println("Bienvenido a tus productos, ¿en que te podemos ayudar?"
						+ "\n1. Crear una cuenta"
						+ "\n2. Eliminar una cuenta"
						+ "\n5. Salir al menú principal");
				
				// SALIR AL MENÚ PRINCIPAL
				int opcion = Integer.parseInt(sc.nextLine());
				System.out.println("");
				
				// CREAR UNA CUENTA
				while(opcion == 1) {
					//PRIMERO DEBEMOS PEDIR LOS DATOS, COMO ALGUNOS SON OPCIONALES, SE PEDIRÁ QUE SI NO SE QUIERE INGRESAR
					//LA INFORMACIÓN SOLICITADA SE DE ENTER
					System.out.println("Para crear una nueva cuenta, porfavor diligencie los siguientes datos: ");
					System.out.println("NOTA: Si no desea ingresar la información en un campo opcional deje el espacio en blanco y continue."
							+ "Las casillas obligatorias se marcarán con un '*'");
					
					System.out.println("* ¿A que Banco desea afiliar su cuenta?");
					//Listar los bancos posibles.
					int Banco_Afiliacion = Integer.parseInt(sc.nextLine());
					System.out.println("");
					
					//IMP: POSIBLE MODIFICACIÓN SEGÚN EL MEDIO DE INGRESO DE LA PERSONA
					System.out.println("* Nombre del titular: ");
					String nombre_titular = sc.nextLine();
					System.out.println("");
					
					System.out.println("* Tipo de cuenta: ");
					int Tipo_Cuenta = Integer.parseInt(sc.nextLine());
					System.out.println("");
					
					System.out.println("* Clave de la cuenta: (Recuerde que será una combinación de 4 números)");
					int Clave_Cuenta = Integer.parseInt(sc.nextLine());
					System.out.println("");
					
					System.out.println("Divisa: (De no ser colocada ninguna se seguirá con al divisa predeterminada del Banco)");
					String Divisa_Cuenta = sc.nextLine();
					System.out.println("");
					
					System.out.println("Nombre de la Cuenta: ");
					String Nombre_Cuenta = sc.nextLine();
					System.out.println("");
						
				}
				
				//SALIR AL MENÚ PRINCIPAL
				if (opcion == 5) {
					seccion = 0;
				}
			}
			
			// CLASE DE USUARIO
			while (seccion == 2) {
				// Contenido de Usuario
				System.out.println("Bienvenido a Usuarios, ¿en que te podemos ayudar?"
						+ "\n5. Salir al menú principal");
				
				int opcion = Integer.parseInt(sc.nextLine());
				System.out.println("");
				
				// SALIR AL MENÚ PRINCIPAL
				if (opcion == 5) {
					seccion = 0;
				}
			}
			
			// CLASE DE METAS
			while (seccion == 3) {
				
				System.out.println("Bienvenido a Metas, ¿en que te podemos ayudar?"
						+ "\n1. Crear una meta"
						+ "\n2. Eliminar una meta"
						+ "\n3. Ver mis metas"
						+ "\n4. Salir al menú principal");
				
				int opcion = Integer.parseInt(sc.nextLine());
				System.out.println("");
				
				// CREAR UNA META
				int id = 1;
				while (opcion == 1) {
					// PRIMERO SE PIDEN LOS DATOS
					System.out.println("Llene los siguientes campos para crear una meta");
						
					System.out.println("Nombre de la meta: ");
					String nombreMe = sc.nextLine();
					System.out.println("");
						
					System.out.println("Cantidad de ahorro: ");
					double cantidadMe = Double.parseDouble(sc.nextLine());
					System.out.println("");
					
					System.out.println("Fecha de la meta (formato dd/MM/yyyy): ");
					String fechaMe = sc.nextLine();
					System.out.println("");
					
					// VALIDAMOS QUE LAS ENTRADAS SEAN CORRECTAS
					if (nombreMe == null || cantidadMe == 0 || fechaMe == null) {
						System.out.println("Alguna de la entrada de los campos no es válida");
						System.out.println("");
						System.out.println("¿Desea crear otra meta? "
								+ "\nEscriba “y” para sí o “n” para salir al menú de Metas");
						String c = sc.nextLine();
						System.out.println("");
						
						if (c.equals("y") || c.equals("Y")) {
							continue;
						}
						
						// SALIR AL MENÚ METAS
						else if (c.equals("n") || c.equals("N")) {
							opcion = 0;
						}
						
						// VALIDAR ENTRADA
						else {
							System.out.println("Entrada no valida");
							System.out.println("");
							continue;
						}
					}
					else {
						// CREAMOS UN OBJETO DE TIPO METAS
						Metas miMeta = new Metas(nombreMe, cantidadMe, fechaMe, id);	
							
						// USAMOS EL METODO CREARMETA
						miMeta.crearMeta(miMeta);
						
						System.out.println("Sus metas son: ");
						
						// MOSTRAMOS LAS METAS DEL USUARIO
						for (int i = 0; i < Metas.mel.size(); i++) {
							System.out.println(i + 1 + ". " + Metas.mel.get(i).getNombre() + ", " +  
									Metas.mel.get(i).getCantidad() + ", " + 
									Metas.mel.get(i).getFecha());
						}
						
						System.out.println("");
						
						// TERMINAR O CONTINUAR
						System.out.println("¿Desea crear otra meta? "
								+ "\nEscriba “y” para sí o “n” para salir al menú de Metas");
						String c = sc.nextLine();
						System.out.println("");
						
						if (c.equals("y") || c.equals("Y")) {
							continue;
						}
						
						// SALIR AL MENÚ METAS
						else if (c.equals("n") || c.equals("N")) {
							opcion = 0;
						}
						
						// VALIDAR ENTRADA
						else {
							System.out.println("Entrada no valida");
							System.out.println("");
							continue;
						}
					}
				}
				
				// 	ELIMINAR UNA META
				while (opcion == 2) {
					System.out.println("¿Cual meta deseas eliminar?");
					
					// MOSTRAR OPCIONES
					for (int i = 0; i < Metas.mel.size(); i++) {
						System.out.println(i + 1 + ". " + Metas.mel.get(i).getNombre());
					}
					
					// ENTRADA DE META QUE SE DESEA ELIMINAR
					int n = Integer.parseInt(sc.nextLine());
					System.out.println("");
					
					System.out.println("La meta con nombre " + Metas.mel.get(n-1).getNombre() + 
							" y cantidad " + Metas.mel.get(n-1).getCantidad() + 
				        	" para la fecha " + Metas.mel.get(n-1).getFecha() + 
				        	" fue eliminada satisfactoriamente del sistema."
				        	+ "\n");
					
					// LLAMAMOS AL METODO
					Metas.mel.get(n-1).eliminarMeta(n-1);
					
					// TERMINAR O CONTINUAR
					System.out.println("¿Desea eliminar otra meta? "
							+ "\nEscriba “y” para sí o “n” para salir al menú de Metas");
					String c = sc.nextLine();
					System.out.println("");
					
					if (c.equals("y") || c.equals("Y")) {
						continue;
					}
					
					// SALIR AL MENÚ METAS
					else if (c.equals("n") || c.equals("N")) {
						opcion = 0;
					}
					// VALIDAR ENTRADA
					else {
						System.out.println("Entrada no valida");
						System.out.println("");
						continue;
					}
					
				}
				
				// VER MIS METAS
				while (opcion == 3) {
					for (int i = 0; i < Metas.mel.size(); i++) {
						System.out.println(i + 1 + ". " + Metas.mel.get(i).getNombre() + ", " +  
								Metas.mel.get(i).getCantidad() + ", " + 
								Metas.mel.get(i).getFecha() + ", " +
								Metas.mel.get(i).getId());
					}
					
					System.out.println("");
					
					// SALIR AL MENU PRINCIPAL
					System.out.println("Escriba “y” para salir al menú de Metas");
					String c = sc.nextLine();
					System.out.println("");

					if (c.equals("y") || c.equals("Y")) {
						opcion = 0;
					}
					
					// VALIDAR ENTRADA
					else {
						System.out.println("Entrada no valida");
						System.out.println("");
						continue;
					}
				}
				
				// SALIR AL MENÚ PRINCIPAL
				if (opcion == 4) {
					seccion = 0;
				}
				
				if (opcion != 1 && opcion != 2 && opcion != 3 && opcion != 4) {
					System.out.println("Entrada no valida");
					System.out.println("");
					continue;
					}
			}
			
			
			// CLASE DE MOVIMIENTOS
			while (seccion == 4) {
				// Contenido de Movimientos
				System.out.println("Bienvenido a Movimientos, ¿en que te podemos ayudar?"
						+ "\n5. Salir al menú principal");
				
				int opcion = Integer.parseInt(sc.nextLine());
				System.out.println("");
				
				// SALIR AL MENÚ PRINCIPAL
				if (opcion == 5) {
					seccion = 0;
				}
			}
			
			// CERRAR SESIÓN
			if (seccion == 5) {
				System.out.println("Hasta la próxima");
				sesioniniciada = 0;
			}
			
		}
		sc.close();
	}	
}