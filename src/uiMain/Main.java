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
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
	
	static int seguir = 1;
	static int opcionMetas;
	static int sesioniniciada = 1;
	static int seccion = 1;
	
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


		Usuario u2 = new Usuario("Pepito", "pepito@gmail.com", "perez", 0, Suscripcion.DIAMANTE);

		//Usuario.listaUsuarios.clear();
		//Serializador.serializar(Usuario.listaUsuarios, "Usuario");
		
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
					seguir = 0;
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
					+ "\n2. Ingresar a Usuarios" //Configuración
					+ "\n3. Mis metas"
					+ "\n4. Mis movimientos"
					+ "\n5. Pedir Prestamo"
					+ "\n6. Cerrar sesión");

			/* CADA VEZ QUE SE VAYA A LEER UN ENTERO POR CONSOLA DEBE PONERSE INTEGER.PARSEINT(SC.NEXTLINE());
			 * DE OTRO MODO SE EJECUTARÁ UN \n QUE DAÑARÁ EL CODIGO. LO MISMO PARA LOS DOUBLE. PARA LOS STRING 
			 * SI SE PUEDE REDACTAR DE MANERA USUAL USANDO SC.NEXTLINE();. */

			int seccion = Integer.parseInt(sc.nextLine());
			System.out.println("");

			if (seccion <= 0 || seccion > 6) {
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

			// METAS
			while (seccion == 3) {

				System.out.println("Bienvenido a Metas, ¿en que te podemos ayudar?" + "\n1. Crear una meta"
						+ "\n2. Eliminar una meta" + "\n3. Ver mis metas" + "\n4. Salir al menú principal");

				opcionMetas = Integer.parseInt(sc.nextLine());
				System.out.println("");

				// Crear una meta
				while (opcionMetas == 1) {
					crearMeta();
				}

				// Eliminar una meta
				while (opcionMetas == 2) {
					eliminarMeta();
				}

				// Ver las metas
				while (opcionMetas == 3) {
					verMetas();
				}

				// Salir al menu principal
				while (opcionMetas == 4) {
					seccion = 0;
				}

				// Verificar entrada
				if (opcionMetas != 0 && opcionMetas != 1 && opcionMetas != 2 && opcionMetas != 3 && opcionMetas != 4) {
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
//			Pedir Prestamo
			while(seccion ==5){
				Main.funcionalidadPrestamo(u1);
			}

			// CERRAR SESIÓN
			if (seccion == 6) {
				System.out.println("Hasta la próxima");
				sesioniniciada = 0;
			}

		}
		sc.close();
	}
	
	// FUNCIONALIDADES 
	private static int funcionalidadPrestamo(Usuario usu){
		Scanner sc = new Scanner(System.in);
		int seccion = 0;
		System.out.println("Bienvenido a Prestamos");
		ArrayList prestamo = usu.comprobarConfiabilidad(usu);
		if(prestamo.get(0) instanceof Cuenta){
			// Si tiene cuentas entonces vamos a comprar cuanto dieron prestan los bancos de las cuentas y mostrale al usuario
			prestamo = Cuenta.comprobarPrestamo(prestamo);
			if(prestamo.get(0) instanceof Cuenta){
				System.out.println("Estas son las cuentas valida para hacer un prestamo y el valor maximo del prestamo");
				for(int i=0;i<prestamo.size();i++){
					Cuenta cuenta = (Cuenta) prestamo.get(i);
					System.out.println(i+"-Cuenta: "+ cuenta.getNombre()+" Maximo a prestar:"+cuenta.getBanco().getPrestamo());
				}
				System.out.println(prestamo.size()+"-Salir al Menú");
				System.out.println("Seleccione una:");
				int opcion = Integer.parseInt(sc.nextLine());
				System.out.println("");
				if(opcion==prestamo.size()){
					seccion =0;
				}else{
					Cuenta cuenta = (Cuenta) prestamo.get(opcion);
					double maxPrestamo = cuenta.getBanco().getPrestamo();
					System.out.println("Ingrese el valor del prestamo, el valor de este debe ser menor de $"+maxPrestamo);
					maxPrestamo = Integer.parseInt(sc.nextLine());
					Boolean exito = Movimientos.realizarPrestamo(cuenta,maxPrestamo);
					if(exito){
						System.out.println("|----------------------------------|\n\nPrestamo Realizado con Exito\n\n|----------------------------------|\n\n");
					}else{
						System.out.println("|----------------------------------|\n\nPor favor seleccione una cantidad adecuada\n\n|----------------------------------|\n\n");

					}
				}


			}else{
				System.out.println("|----------------------------------|\n\nLos bancos de sus cuentas no realizan prestamos\n\n|----------------------------------|\n\n");
				seccion=0;
			}
		}else{
			System.out.println("|----------------------------------|\n\n"+prestamo.get(0)+"\n\n|----------------------------------|\n\n");
			seccion=0;
		}
	}
	
	// Crear una meta
	static void crearMeta() throws ParseException {
		Scanner sc = new Scanner(System.in);
		int id = 1;
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

		// Validar entradas
		if (nombreMe == null || cantidadMe == 0 || fechaMe == null) {
			System.out.println("Alguna de la entrada de los campos no es válida");
			System.out.println("");
			System.out.println("¿Desea volver a comenzar con el proceso?"
					+ "\nEscriba “y” para sí o “n” para salir al menú de Metas");
			String c = sc.nextLine();
			System.out.println("");

			if (c.equals("y") || c.equals("Y")) {
				opcionMetas = 1;
			}

			// Salir al menu de metas
			else if (c.equals("n") || c.equals("N")) {
				opcionMetas = 0;
			}

			// Validar entrada
			else {
				System.out.println("Entrada no valida");
				System.out.println("");
				seccion = 1;
			}
		}

		else {
			// Usamos metoso crearMeta
			Metas.crearMeta(new Metas(nombreMe, cantidadMe, fechaMe, id));
		}

		System.out.println("Sus metas son: ");

		// Mostrar las metas del usuario
		for (int i = 0; i < Metas.mel.size(); i++) {
			System.out.println(i + 1 + ". " + Metas.mel.get(i).getNombre() + ", " + Metas.mel.get(i).getCantidad()
					+ ", " + Metas.mel.get(i).getFechaNormal());
		}

		System.out.println("");

		// Terminar o continuar
		System.out.println("¿Desea crear otra meta? " + "\nEscriba “y” para sí o “n” para salir al menú de Metas");
		String c = sc.nextLine();
		System.out.println("");

		if (c.equals("y") || c.equals("Y")) {
			opcionMetas = 1;
		}

		// Salir al menu de metas
		else if (c.equals("n") || c.equals("N")) {
			opcionMetas = 0;
		}

		// Validar entrada
		else {
			System.out.println("Entrada no valida");
			System.out.println("");
			seccion = 1;
		}
		sc.close();
	}

	// ELIMINAR UNA META
	static void eliminarMeta() {
		Scanner sc = new Scanner(System.in);
		System.out.println("¿Cual meta deseas eliminar?");

		// Opciones
		for (int i = 0; i < Metas.mel.size(); i++) {
			System.out.println(i + 1 + ". " + Metas.mel.get(i).getNombre());
		}

		int n = Integer.parseInt(sc.nextLine());
		System.out.println("");

		System.out.println("La meta con nombre " + Metas.mel.get(n - 1).getNombre() + " y cantidad "
				+ Metas.mel.get(n - 1).getCantidad() + " para la fecha " + Metas.mel.get(n - 1).getFechaNormal()
				+ " fue eliminada satisfactoriamente del sistema." + "\n");

		// llamamos la metodo eliminarMeta
		Metas.mel.get(n - 1).eliminarMeta(n - 1);

		// Terminar o continuar
		System.out.println("¿Desea eliminar otra meta? " + "\nEscriba “y” para sí o “n” para salir al menú de Metas");
		String c = sc.nextLine();
		System.out.println("");

		if (c.equals("y") || c.equals("Y")) {
			opcionMetas = 2;
		}

		// Salir al menu de metas
		else if (c.equals("n") || c.equals("N")) {
			opcionMetas = 0;
		}

		// Validar entrada
		else {
			System.out.println("Entrada no valida");
			System.out.println("");
			seccion = 1;
		}
		sc.close();
	}

	// VER MIS METAS
	static void verMetas() {
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < Metas.mel.size(); i++) {
			System.out.println(i + 1 + ". " + Metas.mel.get(i).getNombre() + ", " + Metas.mel.get(i).getCantidad()
					+ ", " + Metas.mel.get(i).getFechaNormal());
		}

		System.out.println("");

		// SALIR AL MENU PRINCIPAL
		System.out.println("Escriba “y” para salir al menú de Metas");
		String c = sc.nextLine();
		System.out.println("");

		if (c.equals("y") || c.equals("Y")) {
			opcionMetas = 0;
		}

		// Validar entrada
		else {
			System.out.println("Entrada no valida");
			System.out.println("");
			seccion = 1;
		}
		sc.close();
	}

	// FUNCIONALIDAD ASESORAMIENTO DE INVERSIONES
	static void asesorInversiones() throws ParseException {
		Scanner sc = new Scanner(System.in);
		System.out.println("¿Cuál es su tolerancia de riesgos?: " + "\n1. Baja" + "\n2. Media" + "\n3. Alta");
		int riesgo = Integer.parseInt(sc.nextLine());
		System.out.println("");

		System.out.println("¿Qué cantidad piensa invertir?: ");
		int invertir = Integer.parseInt(sc.nextLine());
		System.out.println("");

		// Revisar que las entradas sean correctas

		Metas.revisionMetas();

		System.out.println("Tienes una meta para una fecha muy próxima: " + Metas.mel.get(Metas.metaProxima).getNombre()
				+ ", " + Metas.mel.get(Metas.metaProxima).getCantidad() + ", "
				+ Metas.mel.get(Metas.metaProxima).getFechaNormal()
				+ "\n¿Desearías cambiar la fecha de esta meta para invertir ese dinero en tu portafolio? (y/n)");
		String cambiarFecha = sc.nextLine();
		System.out.println("");

		if (cambiarFecha.equals("y") || cambiarFecha.equals("Y")) {
			System.out.println("¿Para que fecha desearías cambiar la meta? (formato dd/MM/yyyy");
			String nuevaFecha = sc.nextLine();
			Metas.cambiarFecha(nuevaFecha);
			System.out.println("La fecha ha sido modificada satisfactoriamente");
			System.out.println(Metas.plazo);

		}

		else if (cambiarFecha.equals("n") || cambiarFecha.equals("N")) {
			Metas.plazo(Metas.mel.get(Metas.metaProxima).getFecha());

		}

		else {
			System.out.println("Entrada no valida");
		}

		System.out.println("¿Desearías crear una nueva meta para ahorrar e invertir más dinero? (y/n)");
		String nuevaMeta = sc.nextLine();
		System.out.println("");

		if (nuevaMeta.equals("y") || nuevaMeta.equals("Y")) {
			crearMeta();
			System.out.println("La meta será puesta como prioridad en tu lista de metas");
			Metas.prioridadMetas(Metas.mel.get(Metas.mel.size() - 1));
			for (int i = 0; i < Metas.mel.size(); i++) {
				System.out.println(i + 1 + ". " + Metas.mel.get(i).getNombre());
			}
		}

		else if (nuevaMeta.equals("n") || nuevaMeta.equals("N")) {
			seccion = 0;
		}

		else {
			System.out.println("Entrada no valida");
		}

		seccion = 0;

		sc.close();
	}

	//FUNCIONALIDAD COMPRA DE CARTERA
	static void compraCartera(Usuario usuario) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Cuentas a nombre de " + usuario.getNombre() + " con préstamos asociados: ");
		ArrayList<Cuenta> cuentasEnDeuda = usuario.retornarDeudas();
		for (Cuenta cuentas: cuentasEnDeuda) {
			System.out.println();
		}
	}
}
	
