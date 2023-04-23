package uiMain;

import baseDatos.*;
import gestorAplicación.Banco;
import gestorAplicación.Categoria;
import gestorAplicación.Cuenta;
import gestorAplicación.Divisas;
import gestorAplicación.Estado;
import gestorAplicación.Metas;
import gestorAplicación.Movimientos;
import gestorAplicación.Suscripcion;
import gestorAplicación.Tipo;
import gestorAplicación.Usuario;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public final class Main {
	
	// FUNCIONALIDADES 
	private static int funcionalidadPrestamo(Usuario usu){
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
		
	// CREAR UNA META
	static void crearMeta() {
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
	}

	// ELIMINAR UNA META
	static void eliminarMeta() {
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
		}

	// VER MIS METAS
	static void verMetas() {
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
	}

	// FUNCIONALIDAD ASESORAMIENTO DE INVERSIONES
	static void asesorInversiones() throws ParseException {
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
				+ "\n¿Desearías cambiar la fecha de esta meta para invertir ese dinero en tu portafolio? (Y/N)");
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

		System.out.println("¿Desearías crear una nueva meta para ahorrar e invertir más dinero? (Y/N)");
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
	}

	// FUNCIONALIDAD COMPRA DE CARTERA
	static void compraCartera(Usuario usuario) {
		System.out.println("Cuentas a nombre de " + usuario.getNombre() + " con préstamos asociados: ");
		ArrayList<Cuenta> cuentasEnDeuda = usuario.retornarDeudas();
		int i = 1;
		for (Cuenta cuentas: cuentasEnDeuda) {
			System.out.println(i + ". " + cuentas.getNombre());
			i++;
		}
		int Cuenta_Compra = Integer.parseInt(sc.nextLine());
		//Retornar información de la Cuenta
		
		cuentasEnDeuda.remove(Cuenta_Compra - 1);
			
		//Buscar Cuentas con posibilidad de Deuda
			
		}
	
	// CREAR USUARIO DENTRO DEL MAIN
	static void crearUsuario() {
		//Creación de un Usuario
		System.out.println("Para crear un usuario nuevo, por favor diligencie los siguiente datos: ");

		System.out.print("Nombre: ");
		String nombreUsuario = sc.nextLine();
		
		System.out.print("Correo electrónico: ");
		String correoElectronico = sc.nextLine();
		
		/*System.out.println("Seleccione su nivel de suscripción: ");
		for(int i = 1; i < Suscripcion.getNivelesSuscripcion().size() + 1; i++) {
			System.out.println(i + ". " + Suscripcion.getNivelesSuscripcion().get(i-1));
		}
		
		int suscripcion_op = sc.nextInt();
		Suscripcion suscripcion = Suscripcion.getNivelesSuscripcion().get(suscripcion_op);*/

		System.out.print("Contraseña: ");
		String contrasena = sc.nextLine();

		System.out.print("Verificar contraseña: ");
		String verificacionContrasena = sc.nextLine();
		
		if(!(verificacionContrasena.equals(contrasena))) {
			System.out.println("Verifique que la contraseña esté correctamente ingresada. Inténtelo de nuevo");
			System.out.print("Contraseña: ");
			contrasena = sc.nextLine();

			System.out.print("Verificar contraseña: ");
			verificacionContrasena = sc.nextLine();
		}
		
		//user = Usuario.crearUsuario(nombreUsuario, correoElectronico, contrasena, suscripcion);
		System.out.println("Usuario creado con éxito");
		seguir = 0;
		user = new Usuario(nombreUsuario, correoElectronico, contrasena);
	}
	
	// INGRESAR USUARIO DENTRO DEL MAIN
	static void ingresarUsuario() {
		System.out.print("Ingrese nombre de usuario o correo electrónico: ");
		String usuario = sc.nextLine();
		System.out.print("Ingrese su contraseña: ");
		String contraseña = sc.nextLine();
		System.out.println("");

		Object u = Usuario.verificarCredenciales(usuario, contraseña);
		if (u instanceof Usuario) {
			sesioniniciada = 1;
			seguir = 0;
			user = (Usuario) u;
		} else {
			System.out.println("Las credenciales son incorrectas.");
			seguir = 0;
			Main.bienvenidaApp();
		}
	}
	
	// CREAR BANCO DENTRO DEL MAIN
	static void crearBanco() {
		//Creación de un Banco
		System.out.println("Para crear un banco nuevo, por favor diligencie los siguiente datos: ");

		System.out.print("Nombre del banco: ");
		String nombreBanco = sc.nextLine();
		
		System.out.print("Comisión que va a cobrar el banco (En formato double): ");
		Double comision = Double.parseDouble(sc.nextLine());
		
		while(true) {
			if(Estado.getEstadosTotales().size() == 0) {
				System.out.println("No hay estados registrados en el sistema. Primero debes crear un estado.");
				System.out.println("");
				Main.crearEstado();
			}else {
				System.out.println("Seleccione un estado para la operación del banco. La lista de Estados disponibles son: ");
				for(int i = 1; i < Estado.getEstadosTotales().size() + 1; i++) {
					System.out.println(i + ". " + Estado.getEstadosTotales().get(i-1).getNombre());
				}
				int estado_op = Integer.parseInt(sc.nextLine());
				Estado estado_banco = Estado.getEstadosTotales().get(estado_op - 1);
		
				seguir = 0;
				new Banco(nombreBanco, comision, estado_banco);	
				System.out.println("Banco creado con éxito");
				break;
			}
		}
	}
	
	// CREAR ESTADO DENTRO DEL MAIN
	static void crearEstado() {
		//Creación de un Estado
		System.out.println("Para crear un estado nuevo, por favor diligencie los siguiente datos: ");

		System.out.print("Nombre del estado: ");
		String nombreEstado = sc.nextLine();
		
		System.out.print("Tasa de impuestos del estado (En formato double): ");
		Double tasaImpuestosEstado = Double.parseDouble(sc.nextLine());
		
		System.out.println("Seleccione una divisa para la operación del estado. La lista de divisas disponibles son: ");
		for(int i = 1; i < Divisas.getDivisas().size() + 1; i++) {
			System.out.println(i + ". " + Divisas.getDivisas().get(i-1));
		}
		
		int divisas_op = Integer.parseInt(sc.nextLine());
		Divisas divisa_estado = Divisas.getDivisas().get(divisas_op);
		new Estado(nombreEstado, tasaImpuestosEstado, divisa_estado);
		System.out.println("Estado creado con éxito");
	}	

	// ACCESO ADMINISTRATIVO EN MAIN
	static void accesoAdministrativo() {
		System.out.print("Inserta la contraseña de administrador (Es admin): ");
		String contrasena = sc.nextLine();
		while(!contrasena.equals("admin")) {
			System.out.print("Contraseña errada. Inténtelo de nuevo: ");
			contrasena = sc.nextLine();	
		}
		System.out.println("");
		System.out.print("Ingresando al sistema como administrador...");
		user = new Usuario("admin", "admin@admin.com", "admin", Suscripcion.DIAMANTE);

		while(contrasena.equals("admin")) {
			System.out.println("");
			System.out.println("¿Qué deseas hacer?."
					+ "\n1. Crear Usuario"
					+ "\n2. Crear Banco"
					+ "\n3. Crear Estado"
					+ "\n4. Iniciar Sesión"
					+ "\n5. Volver al menú anterior");
			
			int opcionAdmin = Integer.parseInt(sc.nextLine());
					
			if(opcionAdmin == 1) {
				System.out.print("¿Deseas crear el usuario por defecto? (Y/N): ");
				String confirmacion = sc.nextLine();
				while(true) {
					if(confirmacion.equals("Y") || confirmacion.equals("y")) {
						user = new Usuario("Pepe Morales", "PepeMorales@mail.com", "12345", Suscripcion.DIAMANTE);
						System.out.println("El usuario por defecto fue creado con éxito, éstas son las credenciales de ingreso: ");
						System.out.println("Nombre: " + user.getNombre());
						System.out.println("Contraseña: " + user.getContrasena());
						break;
					}else if(confirmacion.equals("N") || confirmacion.equals("n")){
						Main.crearUsuario();
						break;
					}else {
						System.out.println("");
						System.out.print("Seleccione la opción correcta. ¿Deseas crear el usuario por defecto? (Y/N): ");
						confirmacion = sc.nextLine();
					}
				}			
			} else if(opcionAdmin == 2) {
				if(Estado.getEstadosTotales().size() == 0) {
					System.out.println("Para crear un banco debes crear un estado primero. Volviendo al menú anterior.");
				}else {
					System.out.print("¿Deseas crear el banco por defecto? (Y/N): ");
					String confirmacion = sc.nextLine();
					while(true) {
						if(confirmacion.equals("Y") || confirmacion.equals("y")) {
							Banco banco = new Banco("Banco de Colombia", 0.3, Estado.getEstadosTotales().get(0));
							System.out.println("El banco por defecto fue creado con éxito, éstos son sus datos: ");
							System.out.println("Nombre: " + banco.getNombreb());
							System.out.println("Comisión: " + banco.getComision());
							System.out.println("Estado asociado: " + banco.getEstadoAsociado().getNombre());
							break;
						}else if(confirmacion.equals("N") || confirmacion.equals("n")){
							Main.crearBanco();
							break;
						}else {
							System.out.println("");
							System.out.print("Seleccione la opción correcta. ¿Deseas crear el banco por defecto? (Y/N): ");
							confirmacion = sc.nextLine();
						}
					}	
				}		
			} else if(opcionAdmin == 3) {
				System.out.print("¿Deseas crear el estado por defecto? (Y/N): ");
				String confirmacion = sc.nextLine();
				while(true) {
					if(confirmacion.equals("Y") || confirmacion.equals("y")) {
						Estado estado = new Estado("Colombia", 0.2, Divisas.COP);
						System.out.println("El estado por defecto fue creado con éxito, éstos son sus datos: ");
						System.out.println("Nombre: " + estado.getNombre());
						System.out.println("Tasa de impuestos: " + estado.getTasa_impuestos());
						System.out.println("Divisa: " + estado.getDivisa());
						break;
					}else if(confirmacion.equals("N") || confirmacion.equals("n")){
						Main.crearEstado();
						break;
					}else {
						System.out.println("");
						System.out.print("Seleccione la opción correcta. ¿Deseas crear el estado por defecto? (Y/N): ");
						confirmacion = sc.nextLine();
					}	
				}
			}else if(opcionAdmin == 4) {
				sesioniniciada = 1;
				seguir = 0;
				break;		
			} else if(opcionAdmin == 5) {
				seguir = 0;
				break;			
			} else {
				System.out.println("");
				System.out.println("Entrada no valida");
				System.out.println("NOTA: Recuerde que debe ingresar el numeral de la opción que desea escoger.");
				System.out.println("¿Qué deseas hacer?."
						+ "\n1. Crear Usuario"
						+ "\n2. Crear Banco"
						+ "\n3. Crear Estado"
						+ "\n4. Iniciar Sesión"
						+ "\n5. Volver al menú anterior");
				opcionAdmin = Integer.parseInt(sc.nextLine());			
			}		
		}
	}	
	
	// CREAR CUENTA EN MAIN
	static void crearCuenta() {
		//PRIMERO DEBEMOS PEDIR LOS DATOS, COMO ALGUNOS SON OPCIONALES, SE PEDIRÁ QUE SI NO SE QUIERE INGRESAR
		//LA INFORMACIÓN SOLICITADA SE DE ENTER
		if(Banco.getBancosTotales().size() == 0) {
			System.out.println("Para crear una cuenta deben existir bancos. Volviendo al menú anterior");
			opcion = 0;
			seccion = 1;
		}else {
			System.out.println("Para crear una nueva cuenta, favor diligencie los siguientes datos: ");
			System.out.println("¿A que Banco desea afiliar su cuenta?: ");
			Main.verBancosTotales();
			int banco_op = Integer.parseInt(sc.nextLine());
			Banco banco_cuenta = Banco.getBancosTotales().get(banco_op - 1);
			user.asociarBanco(banco_cuenta);

			System.out.println("Cuál es el tipo que quiere seleccionar para su cuenta? La lista de Tipos disponibles son: ");
			for(int i = 1; i < Tipo.getTipos().size() + 1; i++) {
				System.out.println(i + ". " + Tipo.getTipos().get(i-1));
			}
			
			int tipo_op = Integer.parseInt(sc.nextLine());
			Tipo tipo_cuenta = Tipo.getTipos().get(tipo_op);

			System.out.print("Clave de la cuenta (Recuerde que será una combinación de 4 números): ");
			int clave_cuenta = Integer.parseInt(sc.nextLine());
			while(Integer.toString(clave_cuenta).length() != 4) {
				System.out.print("");
				System.out.print("Recuerde que será una combinación de 4 números. Inténtelo de nuevo: ");
				clave_cuenta = Integer.parseInt(sc.nextLine());
			}
			System.out.println("");
			System.out.println("¿Cuál es la divisa que quiere seleccionar para su cuenta? La lista de Divisas disponibles son: ");
			for(int i = 1; i < Divisas.getDivisas().size() + 1; i++) {
				System.out.println(i + ". " + Divisas.getDivisas().get(i-1));
			}
			
			int divisas_op = Integer.parseInt(sc.nextLine());
			Divisas divisas_cuenta = Divisas.getDivisas().get(divisas_op);
			
			System.out.println("");
			System.out.print("Nombre de la Cuenta: ");
			String nombre_cuenta = sc.nextLine();
			
			user.asociarCuenta(new Cuenta(banco_cuenta, tipo_cuenta, clave_cuenta, divisas_cuenta, nombre_cuenta));
			System.out.println("Cuenta creada con éxito");
		}
	}
	
	// ELIMINAR CUENTA EN MAIN
	static void eliminarCuenta() {
		//SE VERIFICA QUE EXISTAN CUENTAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CREADAS POR EL USUARIO
		if(user.getCuentasAsociadas().size() > 0) {
			System.out.println("La lista de cuentas creadas por el usuario " + user.getNombre() + " son: ");
			for(int i = 1; i < user.getCuentasAsociadas().size() + 1; i++) {
				System.out.println(i + ". " + user.getCuentasAsociadas().get(i - 1).getNombre());
			}
			System.out.println("");
			System.out.print("Inserte el numero de la cuenta que desea eliminar: ");
			int cuentaOp = Integer.parseInt(sc.nextLine());
			for(int i = 0; i < user.getCuentasAsociadas().size(); i++) {
				if(user.getCuentasAsociadas().get(cuentaOp - 1) == user.getCuentasAsociadas().get(i)) {
					Cuenta.eliminarCuenta(user.getCuentasAsociadas().get(i), user);
					System.gc();
				}
			}
			//SE IMPRIME QUE NO EXISTEN CUENTAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
		}else {
			System.out.print("No hay cuentas creadas para este usuario. ¿Desea crear una? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("y") || confirmacion.equals("Y")){
				Main.crearCuenta();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}
		}
	}
	
	// VER CUENTAS EN MAIN
	static void verCuentas() {
		//SE VERIFICA QUE EXISTAN CUENTAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CREADAS POR EL USUARIO
		if(user.getCuentasAsociadas().size() > 0) {
			System.out.println("La lista de Cuentas creadas por el Usuario " + user.getNombre() + " son: ");
			for(int i = 1; i < user.getCuentasAsociadas().size() + 1; i++) {
				System.out.println(i + ". " + user.getCuentasAsociadas().get(i-1).getNombre());
			}
			
			//SE IMPRIME QUE NO EXISTEN CUENTAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
		}else {
			System.out.print("No hay cuentas creadas para este usuario. ¿Deseas crear una? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearCuenta();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}
	
	// VER USUARIOS TOTALES EN MAIN
	static void verUsuariosTotales() {
		//SE VERIFICA QUE EXISTAN USUARIOS CREADOS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS USUARIOS CREADOS
		if(Usuario.getUsuariosTotales().size() > 0) {
			System.out.println("Todos los usuarios son: ");
			for(int i = 1; i < Usuario.getUsuariosTotales().size() + 1; i++) {
				System.out.println(i + ". " + Usuario.getUsuariosTotales().get(i - 1).getNombre());
			}
			
			//SE IMPRIME QUE NO EXISTEN USUARIOS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNO	
		}else {
			System.out.print("No hay usuarios creados. ¿Deseas crear uno? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearUsuario();
				System.out.println("1. Usuario con nombre " + user.getNombre() + " creado");
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}
	
	// VER BANCOS TOTALES EN MAIN
	static void verBancosTotales() {
			//SE VERIFICA QUE EXISTAN BANCOS CREADOS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS BANCOS CREADOS
			if(Banco.getBancosTotales().size() > 0) {
				System.out.println("La lista de Bancos son: ");
				for(int i = 1; i < Banco.getBancosTotales().size() + 1; i++) {
					System.out.println(i + ". " + Banco.getBancosTotales().get(i - 1).getNombreb());
				}
				
			//SE IMPRIME QUE NO EXISTEN USUARIOS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNO	
			}else {
				System.out.print("No hay bancos creados. ¿Deseas crear uno? (Y/N): ");
				String confirmacion = sc.nextLine();
				if(confirmacion.equals("Y") || confirmacion.equals("y")) {
					Main.crearBanco();
				}else {
					System.out.println("Volviendo al menú anterior");
					opcion = 0;
					seccion = 1;
				}	
			}
		}

	// VER CUENTAS TOTALES EN MAIN
	static void verCuentasTotales() {
		//SE VERIFICA QUE EXISTAN CUENTAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CREADAS
		if(Cuenta.getCuentasTotales().size() > 0) {
			System.out.println("La lista de Cuentas son: ");
			for(int i = 1; i < Cuenta.getCuentasTotales().size() + 1; i++) {
				System.out.println(i + ". " + Cuenta.getCuentasTotales().get(i - 1).getNombre());
			}
					
		//SE IMPRIME QUE NO EXISTEN CUENTAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
		}else {
			System.out.print("No hay cuentas creadas. ¿Deseas crear una? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearCuenta();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}
		
	// VER MOVIMIENTOS TOTALES EN MAIN
	static void verMovimientosTotales() {
		//SE VERIFICA QUE EXISTAN MOVIMIENTOS CREADOS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS MOVIMIENTOS CREADOS
		if(Movimientos.getMovimientosTotales().size() > 0) {
			System.out.println("La lista de Movimientos son: ");
			for(int i = 1; i < Movimientos.getMovimientosTotales().size() + 1; i++) {
				System.out.println(i + ". " + Movimientos.getMovimientosTotales().get(i - 1).getId());
			}
					
		//SE IMPRIME QUE NO EXISTEN MOVIMIENTOS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNO	
		}else {
			System.out.print("No hay movimientos creados. ¿Deseas crear uno? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearMovimiento();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}
		
	// VER METAS TOTALES EN MAIN
	static void verMetasTotales() {
		//SE VERIFICA QUE EXISTAN METAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS METAS CREADAS
		if(Metas.getMetasTotales().size() > 0) {
			System.out.println("La lista de Metas son: ");
			for(int i = 1; i < Metas.getMetasTotales().size() + 1; i++) {
				System.out.println(i + ". " + Metas.getMetasTotales().get(i - 1).getNombre());
			}
					
		//SE IMPRIME QUE NO EXISTEN METAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
		}else {
			System.out.print("No hay metas creadas. ¿Deseas crear una? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearMeta();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}
	
	// VER METAS TOTALES EN MAIN
	static void verEstadosTotales() {
		//SE VERIFICA QUE EXISTAN ESTADOS CREADOS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS ESTADOS CREADOS
		if(Estado.getEstadosTotales().size() > 0) {
			System.out.println("La lista de Estados son: ");
			for(int i = 1; i < Estado.getEstadosTotales().size() + 1; i++) {
				System.out.println(i + ". " + Estado.getEstadosTotales().get(i-1).getNombre());
			}
						
		//SE IMPRIME QUE NO EXISTEN ESTADOS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNO	
		}else {
			System.out.print("No hay estados creados. ¿Deseas crear uno? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearEstado();
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 1;
			}	
		}
	}		
	
	// GUARDAR OBJETOS EN MAIN
	static void guardarObjetos() {
		//Guardar objetos
		while(true) {
			System.out.println("Bienvenido a la Base de Datos...");
			System.out.println("Seleccione el objeto que quiere guardar: ");
			
			for(int i = 1; i < listaObjetos.size() + 1; i++) {
				System.out.println(i + ". " + listaObjetos.get(i-1));
			}
			
			System.out.println("7. Volver al menú anterior");
			
			int objeto_op = Integer.parseInt(sc.nextLine());
			if(objeto_op == 7){	
				Main.bienvenidaApp();
				break;
			} else if (objeto_op < 1 || objeto_op > 7) {
				System.out.println("Entrada no valida");
				continue;				
			}else {
				System.out.println("");
				System.out.println("Seleccione una de las opciones: ");
				System.out.println("1. Guardar objetos individuales. "
						+ "\n2. Guardar listas con objetos.");
				int serializarOpcion = Integer.parseInt(sc.nextLine());
				
				String objeto_nombre = listaObjetos.get(objeto_op - 1);
				
				if(objeto_nombre.equals("Usuarios")) {
					if(serializarOpcion == 1) {
						Main.verUsuariosTotales();
						System.out.print("Inserte el número del Usuario que desea guardar: ");
						int opcionUsuario = Integer.parseInt(sc.nextLine());
						Usuario usuarioGuardar = Usuario.getUsuariosTotales().get(opcionUsuario - 1);
						System.out.println(Serializador.serializar(usuarioGuardar));
						break;
					}else {
						System.out.println(Serializador.serializar(Usuario.getUsuariosTotales(), objeto_nombre));
						break;
					}
				}else if(objeto_nombre.equals("Bancos")) {
					if(serializarOpcion == 1) {
						Main.verBancosTotales();
						System.out.print("Inserte el número del Banco que desea guardar: ");
						int opcionBanco = Integer.parseInt(sc.nextLine());
						Banco bancoGuardar = Banco.getBancosTotales().get(opcionBanco - 1);
						System.out.println(Serializador.serializar(bancoGuardar));
						break;
					}else {
						System.out.println(Serializador.serializar(Banco.getBancosTotales(), objeto_nombre));
						break;
					}
				}else if (objeto_nombre.equals("Movimientos")) {
					if(serializarOpcion == 1) {
						Main.verMovimientosTotales();
						System.out.print("Inserte el número del Movimiento que desea guardar: ");
						int opcionMovimiento = Integer.parseInt(sc.nextLine());
						Movimientos movimientoGuardar = Movimientos.getMovimientosTotales().get(opcionMovimiento - 1);
						System.out.println(Serializador.serializar(movimientoGuardar));
						break;
					}else {
						System.out.println(Serializador.serializar(Movimientos.getMovimientosTotales(), objeto_nombre));
						break;
					}
				}else if (objeto_nombre.equals("Cuentas")) {
					if(serializarOpcion == 1) {
						Main.verCuentasTotales();
						System.out.print("Inserte el número de la Cuenta que desea guardar: ");
						int opcionCuenta = Integer.parseInt(sc.nextLine());
						Cuenta cuentaGuardar = Cuenta.getCuentasTotales().get(opcionCuenta - 1);
						System.out.println(Serializador.serializar(cuentaGuardar));
						break;
					}else {
						System.out.println(Serializador.serializar(Cuenta.getCuentasTotales(), objeto_nombre));
						break;
					}
				}else if (objeto_nombre.equals("Estados")) {
					if(serializarOpcion == 1) {
						Main.verEstadosTotales();
						System.out.print("Inserte el número del Estado que desea guardar: ");
						int opcionEstado = Integer.parseInt(sc.nextLine());
						Estado estadoGuardar = Estado.getEstadosTotales().get(opcionEstado - 1);
						System.out.println(Serializador.serializar(estadoGuardar));
						break;
					}else {
						System.out.println(Serializador.serializar(Estado.getEstadosTotales(), objeto_nombre));
						break;
					}
				}else if (objeto_nombre.equals("Metas")) {
					if(serializarOpcion == 1) {
						Main.verMetasTotales();
						System.out.print("Inserte el número de la Meta que desea guardar: ");
						int opcionMeta = Integer.parseInt(sc.nextLine());
						Metas metaGuardar = Metas.getMetasTotales().get(opcionMeta - 1);
						System.out.println(Serializador.serializar(metaGuardar));
						break;
					}else {
						System.out.println(Serializador.serializar(Metas.getMetasTotales(), objeto_nombre));
						break;
					}
				}
			}
		}
	}
	
	// CARGAR OBJETOS EN MAIN
	static void cargarObjetos() {
		//Guardar objetos
		while(true) {
			System.out.println("Bienvenido a la Base de Datos...");
			System.out.println("Seleccione el objeto que quiere cargar: ");
			
			for(int i = 1; i < listaObjetos.size() + 1; i++) {
				System.out.println(i + ". " + listaObjetos.get(i-1));
			}
			
			System.out.println("7. Volver al menú anterior");
			
			int objeto_op = Integer.parseInt(sc.nextLine());
			if(objeto_op == 7){	
				Main.bienvenidaApp();
				break;
			} else if (objeto_op < 1 || objeto_op > 7) {
				System.out.println("Entrada no valida");
				continue;				
			}else {
				System.out.println("");
				System.out.println("Seleccione una de las opciones: ");
				System.out.println("1. Cargar objetos individuales. "
						+ "\n2. Cargar listas con objetos.");
				int serializarOpcion = Integer.parseInt(sc.nextLine());
				
				String objeto_nombre = listaObjetos.get(objeto_op - 1);
				
				if(objeto_nombre.equals("Usuarios")) {
					if(serializarOpcion == 1) {
						Usuario u = (Usuario) Deserializador.deserializar(objeto_nombre);
						System.out.println("El Usuario con nombre " + u.getNombre() + " ha sido cargado con éxito en el sistema.");
						break;
					}else {
						ArrayList<Usuario> usuariosDeserializados = (ArrayList<Usuario>) Deserializador.deserializar_listas(objeto_nombre);
						System.out.println("Una lista con " + usuariosDeserializados.size() + " usuarios ha sido cargada con éxito en el sistema.");
						for(Usuario u : usuariosDeserializados) {
							System.out.println("El Usuario con nombre " + u.getNombre() + " ha sido cargado con éxito en el sistema.");
						}
						break;
					}
				}else if(objeto_nombre.equals("Bancos")) {
					if(serializarOpcion == 1) {
						Banco b = (Banco) Deserializador.deserializar(objeto_nombre);
						System.out.println("El Banco con nombre " + b.getNombre() + " ha sido cargado con éxito en el sistema.");
						break;
					}else {
						ArrayList<Banco> bancosDeserializados = (ArrayList<Banco>) Deserializador.deserializar_listas(objeto_nombre);
						System.out.println("Una lista con " + bancosDeserializados.size() + " bancos ha sido cargada con éxito en el sistema.");
						for(Banco b : bancosDeserializados) {
							System.out.println("El Banco con nombre " + b.getNombre() + " ha sido cargado con éxito en el sistema.");
						}
						break;
					}
				}else if (objeto_nombre.equals("Movimientos")) {
					if(serializarOpcion == 1) {
						Movimientos m = (Movimientos) Deserializador.deserializar(objeto_nombre);
						System.out.println("El Movimiento con id " + m.getId() + " ha sido cargado con éxito en el sistema.");
						break;
					}else {
						ArrayList<Movimientos> movimientosDeserializados = (ArrayList<Movimientos>) Deserializador.deserializar_listas(objeto_nombre);
						System.out.println("Una lista con " + movimientosDeserializados.size() + " movimientos ha sido cargada con éxito en el sistema.");
						for(Movimientos m : movimientosDeserializados) {
							System.out.println("El Movimiento con id " + m.getId() + " ha sido cargado con éxito en el sistema.");
						}
						break;
					}
				}else if (objeto_nombre.equals("Cuentas")) {
					if(serializarOpcion == 1) {
						Cuenta c = (Cuenta) Deserializador.deserializar(objeto_nombre);
						System.out.println("La Cuenta con nombre " + c.getNombre() + " ha sido cargada con éxito en el sistema.");
						break;
					}else {
						ArrayList<Cuenta> cuentasDeserializados = (ArrayList<Cuenta>) Deserializador.deserializar_listas(objeto_nombre);
						System.out.println("Una lista con " + cuentasDeserializados.size() + " cuentas ha sido cargada con éxito en el sistema.");
						for(Cuenta c : cuentasDeserializados) {
							System.out.println("La Cuenta con nombre " + c.getNombre() + " ha sido cargada con éxito en el sistema.");
						}
						break;
					}
				}else if (objeto_nombre.equals("Estados")) {
					if(serializarOpcion == 1) {
						Estado e = (Estado) Deserializador.deserializar(objeto_nombre);
						System.out.println("El Estado con nombre " + e.getNombre() + " ha sido cargado con éxito en el sistema.");
						break;
					}else {
						ArrayList<Estado> estadosDeserializados = (ArrayList<Estado>) Deserializador.deserializar_listas(objeto_nombre);
						System.out.println("Una lista con " + estadosDeserializados.size() + " estados ha sido cargada con éxito en el sistema.");
						for(Estado e : estadosDeserializados) {
							System.out.println("El Estado con nombre " + e.getNombre() + " ha sido cargado con éxito en el sistema.");
						}
						break;
					}
				}else if (objeto_nombre.equals("Metas")) {
					if(serializarOpcion == 1) {
						Metas m = (Metas) Deserializador.deserializar(objeto_nombre);
						System.out.println("La Meta con nombre " + m.getNombre() + " ha sido cargado con éxito en el sistema.");
						break;
					}else {
						ArrayList<Metas> metasDeserializados = (ArrayList<Metas>) Deserializador.deserializar_listas(objeto_nombre);
						System.out.println("Una lista con " + metasDeserializados.size() + " metas ha sido cargada con éxito en el sistema.");
						for(Metas e : metasDeserializados) {
							System.out.println("La Meta con nombre " + e.getNombre() + " ha sido cargado con éxito en el sistema.");
						}
						break;
					}
				}
			}
		}
	}
	
	// INTERFAZ DE BIENVENIDA EN MAIN
	static void bienvenidaApp() {
		while(interfaz == 1) {
			/* LA VARIABLE INTERFAZ SE USA PARA PODER TERMINAR EL PROGRAMA. POR EJEMPLO CUANDO VOY A SALIR DEL PROGRAMA LE ASIGNO EL VALOR DE 0 PARA QUE TERMINE. 
			 * ESTO MISMO SE USA DE DIFERENTES MANERAS PARA VARIAS PARTES DE LA INTERFAZ DEL USUARIO. */
			
			/* La variable sesioniniciada tiene una función análoga a la de seguir, en este caso será útil para volver a pedir los datos del usuario. */
			
			// INTERFAZ DE BIENVENIDA
			System.out.println("");
			System.out.println("Bienvenido al gestor de dinero."
					+ "\n1. Ingresar Usuario"
					+ "\n2. Crear Usuario"
					+ "\n3. Acceso Administrativo"
					+ "\n4. Guardar Objetos"
					+ "\n5. Cargar Objetos"
					+ "\n6. Cerrar Programa");
			
			seguir = 1;
			opcionUsuario = Integer.parseInt(sc.nextLine());
			
			while(seguir == 1) {
				System.out.println("");
				
				if (opcionUsuario == 1) {
					Main.ingresarUsuario();
	
				} else if(opcionUsuario == 2) {
					Main.crearUsuario();
					
				} else if(opcionUsuario == 3){
					Main.accesoAdministrativo();
					
				} else if(opcionUsuario == 4){
					Main.guardarObjetos();
					
				} else if(opcionUsuario == 5){
					Main.cargarObjetos();
			
				} else if(opcionUsuario == 6){
					System.out.println("Finalizando programa. Esperamos verte de nuevo pronto");
					seguir = 0;
					interfaz = 0;	
				
				} else {
					System.out.println("Entrada no valida");
					System.out.println("NOTA: Recuerde que debe ingresar el numeral de la opción que desea escoger.");
					System.out.println("Bienvenido al gestor de dinero."
							+ "\n1. Ingresar Usuario"
							+ "\n2. Crear Usuario"
							+ "\n3. Acceso Administrativo"
							+ "\n4. Guardar Objetos"
							+ "\n5. Cargar Objetos"
							+ "\n6. Cerrar Programa");
					
					opcionUsuario = Integer.parseInt(sc.nextLine());
				}
			}
			// INICIO DE SESION COMO USUARIO
			while (sesioniniciada == 1) {
				System.out.println("");
				System.out.println("Bienvenido, "
						+ user.getNombre()
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
	
				seccion = Integer.parseInt(sc.nextLine());
				
				// COMPROBAR QUE LA SECCION PUEDA EJECUTARSE
				if (seccion < 1 || seccion > 6) {
					System.out.println("Entrada no valida");
					continue;
				}
				// CLASE DE CUENTA
				while (seccion == 1) {
					// Contenido de Cuenta
					System.out.println("");
					System.out.println("Bienvenido a tus productos, ¿en que te podemos ayudar?"
							+ "\n1. Crear una cuenta"
							+ "\n2. Eliminar una cuenta"
							+ "\n3. Ver mis cuentas"
							+ "\n4. Salir al menú principal");
					
					opcion = Integer.parseInt(sc.nextLine());
					
					// Crear una cuenta
					while(opcion == 1) {
						Main.crearCuenta();
						break;
					}
					// Eliminar una cuenta
					while(opcion == 2) {
						Main.eliminarCuenta();
						break;
					}
					// Ver mis cuentas
					while(opcion == 3) {
						Main.verCuentas();
						break;
					}
					// Volver al menú anterior
					if (opcion == 4) {
						seccion = 0;
					}
					//Comprobar que la opción seleccionada pueda ejecutarse
					if (opcion < 1 || opcion > 4) {
						System.out.println("Entrada no valida");
						continue;
					}
				}
				// CLASE DE USUARIO
				while (seccion == 2) {
					// Contenido de Usuario
					System.out.println("");
					System.out.println("Bienvenido a Usuarios, ¿en que te podemos ayudar?"
							+ "\n5. Salir al menú principal");
	
					opcion = Integer.parseInt(sc.nextLine());
					System.out.println("");
	
					// Volver al menú anterior
					if (opcion == 5) {
						seccion = 0;
					}
					//Comprobar que la opción seleccionada pueda ejecutarse
					if (opcion < 1 || opcion > 5) {
						System.out.println("Entrada no valida");
						continue;
					}
				}
				// METAS
				while (seccion == 3) {
					// Contenido de Usuario
					System.out.println("");
					System.out.println("Bienvenido a Metas, ¿en que te podemos ayudar?" 
							+ "\n1. Crear una meta"
							+ "\n2. Eliminar una meta" 
							+ "\n3. Ver mis metas" 
							+ "\n4. Salir al menú principal");
	
					opcionMetas = Integer.parseInt(sc.nextLine());
	
					// Crear una meta
					while (opcionMetas == 1) {
						Main.crearMeta();
					}
					// Eliminar una meta
					while (opcionMetas == 2) {
						Main.eliminarMeta();
					}
					// Ver las metas
					while (opcionMetas == 3) {
						Main.verMetas();
					}
					// Volver al menú anterior
					while (opcionMetas == 4) {
						sesioniniciada = 1;
						seccion = 0;
						break;
					}
					//Comprobar que la opción seleccionada pueda ejecutarse
					if (opcionMetas < 1 || opcionMetas>4 ) {
						System.out.println("Entrada no valida");
						continue;
					}
				}
				// CLASE DE MOVIMIENTOS
				while (seccion == 4) {
					// Contenido de Movimientos
					System.out.println("Bienvenido a Movimientos, ¿en que te podemos ayudar?"
							+ "\n5. Salir al menú principal");
	
					opcion = Integer.parseInt(sc.nextLine());
					System.out.println("");
	
					// Volver al menú anterior
					if (opcion == 5) {
						seccion = 0;
					}
					//Comprobar que la opción seleccionada pueda ejecutarse
					if (opcion < 1 || opcion > 4 ) {
						System.out.println("Entrada no valida");
						continue;
					}
				}
				// PEDIR PRESTAMO
				while(seccion ==5){
					Main.funcionalidadPrestamo(user);
				}
				// CERRAR SESIÓN COMO USUARIO
				if (seccion == 6) {
					System.out.println("¡Vuelve pronto " + user.getNombre() + "!");
					sesioniniciada = 0;
				}
			}	
		} sc.close();
	}
	
	//ATRIBUTOS DE CLASE PARA EL FUNCIONAMIENTO DE LA INTERFAZ
	static ArrayList<String> listaObjetos = new ArrayList<String>();
	static Usuario user = null;
	static int seguir = 1;
	static int opcionUsuario = 0;
	static int opcionMetas;
	static int sesioniniciada = 0;
	static int interfaz = 1;
	static int seccion = 0;
	static int opcion = 0;
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args){
		listaObjetos.add(Estado.nombreD);
		listaObjetos.add(Cuenta.nombreD);
		listaObjetos.add(Usuario.nombreD);
		listaObjetos.add(Banco.nombreD);
		listaObjetos.add(Movimientos.nombreD);
		listaObjetos.add(Metas.nombreD);
		
		Main.bienvenidaApp();
	}	
}