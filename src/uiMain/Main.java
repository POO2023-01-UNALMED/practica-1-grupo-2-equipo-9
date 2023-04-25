package uiMain;

import baseDatos.Serializador;
import baseDatos.Deserializador;
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
import java.time.Instant;

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
		
	// CREAR UNA META EN EL MAIN
	static void crearMeta() throws ParseException {
		// FORMATO EN EL QUE DESEA CREAR LA META
		System.out.println("¿En qué formato le gustaría crear su meta?: " + "\n1. NombreMeta, CantidadMeta, FechaMeta"
				+ "\n2. NombreMeta, CantidadMeta" + "\n3. NombreMeta, FechaMeta" + "\n4. CantidadMeta, FechaMeta");

		int formato = Integer.parseInt(sc.nextLine());
		System.out.println("");

		if (formato == 1) {
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
				Metas meta = new Metas(nombreMe, cantidadMe, fechaMe, 1);
				user.asociarMeta(meta);
			}

		}

		if (formato == 2) {
			// PRIMERO SE PIDEN LOS DATOS
			System.out.println("Llene los siguientes campos para crear una meta");

			System.out.println("Nombre de la meta: ");
			String nombreMe = sc.nextLine();
			System.out.println("");

			System.out.println("Cantidad de ahorro: ");
			double cantidadMe = Double.parseDouble(sc.nextLine());
			System.out.println("");

			// Validar entradas
			if (nombreMe == null || cantidadMe == 0) {
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
				Metas meta = new Metas(nombreMe, cantidadMe, 1);
				user.asociarMeta(meta);
			}

		}

		if (formato == 3) {
			// PRIMERO SE PIDEN LOS DATOS
			System.out.println("Llene los siguientes campos para crear una meta");

			System.out.println("Nombre de la meta: ");
			String nombreMe = sc.nextLine();
			System.out.println("");

			System.out.println("Fecha de la meta (formato dd/MM/yyyy): ");
			String fechaMe = sc.nextLine();
			System.out.println("");

			// Validar entradas
			if (nombreMe == null || fechaMe == null) {
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
				Metas meta = new Metas(nombreMe, fechaMe, 1);
				user.asociarMeta(meta);
			}
		}

		if (formato == 4) {
			// PRIMERO SE PIDEN LOS DATOS
			System.out.println("Llene los siguientes campos para crear una meta");

			System.out.println("Cantidad de ahorro: ");
			double cantidadMe = Double.parseDouble(sc.nextLine());
			System.out.println("");

			System.out.println("Fecha de la meta (formato dd/MM/yyyy): ");
			String fechaMe = sc.nextLine();
			System.out.println("");

			// Validar entradas
			if (cantidadMe == 0 || fechaMe == null) {
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
				Metas meta = new Metas(cantidadMe, fechaMe, 1);
				user.asociarMeta(meta);
			}

		}

		if (formato != 1 && formato != 2 && formato != 3 && formato != 4) {
			System.out.println("Alguna de las entradas no es valida");
		}

		System.out.println("Sus metas son: ");

		// Mostrar las metas del usuario
		verMetas();

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

	// ELIMINAR UNA META EN EL MAIN
	static void eliminarMeta() {
		System.out.println("¿Cual meta deseas eliminar?");

		// Opciones
		for (int i = 0; i < user.getMetasAsociadas().size(); i++) {
			System.out.println(i + 1 + ". " + user.getMetasAsociadas().get(i).getNombre());
		}

		int n = Integer.parseInt(sc.nextLine());
		System.out.println("");

		// llamamos la metodo eliminarMetas
		user.eliminarMetas(n - 1);

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

	// VER MIS METAS EN EL MAIN
	static void verMetas() {
		for (int i = 0; i < user.getMetasAsociadas().size(); i++) {

			String name = user.getMetasAsociadas().get(i).getNombre();
			double amount = user.getMetasAsociadas().get(i).getCantidad();
			Date date = user.getMetasAsociadas().get(i).getFecha();

			if (date == null) {
				System.out.println(i + 1 + ". " + user.getMetasAsociadas().get(i).getNombre() + ", "
						+ user.getMetasAsociadas().get(i).getCantidad());
			}

			else if (amount == 0) {
				System.out.println(i + 1 + ". " + user.getMetasAsociadas().get(i).getNombre() + ", "
						+ user.getMetasAsociadas().get(i).getFechaNormal());
			}

			else if (name == null) {
				System.out.println(i + 1 + ". " + user.getMetasAsociadas().get(i).getCantidad() + ", "
						+ user.getMetasAsociadas().get(i).getFechaNormal());
			}

			else {
				System.out.println(i + 1 + ". " + user.getMetasAsociadas().get(i).getNombre() + ", "
						+ user.getMetasAsociadas().get(i).getCantidad() + ", "
						+ user.getMetasAsociadas().get(i).getFechaNormal());
			}
		}
	}

	// CREAR MOVIMIENTO EN EL MAIN
	static void crearMovimiento() {
		System.out.println("Para realizar un MOVIMIENTO por favor ingresar los siguientes datos:");
		System.out.println("Ingrese el id de la cuenta origen:");
		int origen = Integer.parseInt(sc.nextLine());
		System.out.println("Ingrese el id la cuenta destino:");
		int destino = Integer.parseInt(sc.nextLine());
		System.out.print("Ingrese la cantidad a enviar:$");
		double cantidad = Double.parseDouble(sc.nextLine());
		System.out.println("selecione la categoria del movimiento");
		for (int i = 0; i < Categoria.values().length; i++) {
			System.out.println(i + "-" + Categoria.values()[i]);
		}
		int numCategoria = Integer.parseInt(sc.nextLine());
		Categoria categoria = Categoria.values()[numCategoria];
		System.out.println(Movimientos.crearMovimiento(origen, destino, cantidad, categoria, Date.from(Instant.now())));
	}

	// FUNCIONALIDAD ASESORAMIENTO DE INVERSIONES
	static void asesorInversiones() throws ParseException {
		
		// Se confirman que hayan ciertos requeriminetos para el buen funcionamiento de
		// la funcionalidad
		if (user.getMetasAsociadas().size() == 0) {
			System.out.println("Primero debes crear una meta para acceder a esta funcionalidad");
		}
		
		else if (user.getBancosAsociados().size() == 0) {
			System.out.println("Primero debes estar asociado a un banco para acceder a esta funcionalidad");
		}
		
		else if (user.getCuentasAsociadas().size() == 0) {
			System.out.println("Primero debes crear una cuenta asociada a tu usuario");
		}
		
		else if (user.getMovimientosAsociadas().size() == 0) {
			System.out.println("Primero debes crear una cuenta asociada a tu usuario");
		}
		
		else {
		System.out.println("¿Cuál es su tolerancia de riesgos?: " + "\n1. Baja" + "\n2. Media" + "\n3. Alta");
		int riesgo = Integer.parseInt(sc.nextLine());
		System.out.println("");

		System.out.println("¿Qué cantidad piensa invertir?: ");
		int invertir = Integer.parseInt(sc.nextLine());
		System.out.println("");

		// Revisar que las entradas sean correctas
		if (riesgo != 1 && riesgo != 2 && riesgo != 3) {
			System.out.println("Alguna de las entradas no es válida");
		}

		Metas.revisionMetas(user);
		
		System.out.println("Tienes una meta para una fecha muy próxima: " + Metas.metaProxima.getNombre() + ", "
				+ Metas.metaProxima.getCantidad() + ", " + Metas.metaProxima.getFechaNormal()
				+ "\n¿Desearías cambiar la fecha de esta meta para invertir ese dinero " + "en tu portafolio? (y/n)");
		String cambiarFecha = sc.nextLine();
		System.out.println("");
		
		if (cambiarFecha.equals("y") || cambiarFecha.equals("Y")) {
			System.out.println("¿Para que fecha desearías cambiar la meta? " + "(formato dd/MM/yyyy)");
			String nuevaFecha = sc.nextLine();
			Metas.cambioFecha(Metas.metaProxima, nuevaFecha);
			System.out.println("La fecha ha sido modificada satisfactoriamente");
			Metas.determinarPlazo(Metas.metaProxima);
			System.out.println("");
			System.out.println("Su plazo de inversión basado en las fechas de sus metas es: " + "plazo " + Metas.plazo);
			System.out.println("");
		}

		else if (cambiarFecha.equals("n") || cambiarFecha.equals("N")) {
			Metas.determinarPlazo(Metas.metaProxima);
			System.out.println("Su plazo de inversión basado en las fechas de sus metas es: " + "plazo " + Metas.plazo);
			System.out.println("");
		}

		else {
			System.out.println("Entrada no valida");
		}

		System.out.println("Con el fin de aumentar la inversión inicial y hacer una buena "
				+ "recomendación, analizaremos sus movimientos para encontrar la categoría "
				+ "en la que más dinero ha gastado.");
		System.out.println("");

		// Buscamos la categoría en la que el usuario ha gastado más dinero
		Movimientos.analizarCategoria(user);

		System.out.println("La categoría en la que más dinero ha gastado es en: " + Movimientos.nombreCategoria
				+ " que suma un total de " + Movimientos.cantidadCategoria + ".");
		System.out.println("Le sugerimos crear una meta con el fin de ahorrar la misma "
				+ "cantidad que ha gastado en esta categoría. Si desea crear la meta escriba “y”. En caso contrario escriba “n”.");
		String nuevaMeta = sc.nextLine();
		System.out.println("");

		// Crear una meta con los parametros preestablecidos
		if (nuevaMeta.equals("y") || nuevaMeta.equals("Y")) {
			System.out.println("El nombre y los ahorros ya están definidos. Tu solo define " + "la fecha que desees.");

			System.out.println("Fecha de la meta (formato dd/MM/yyyy): ");
			String fechaMe = sc.nextLine();
			System.out.println("");

			// Usamos metodo crearMeta
			Metas metaCategoria = new Metas(Movimientos.nombreCategoria, Movimientos.cantidadCategoria, fechaMe, 1);

			user.asociarMeta(metaCategoria);

			System.out.println("La meta ha sido creada satisfactoriamente.");

			// Priorizamos la meta
			System.out.println("La meta será puesta como prioridad en tu lista de metas");
			Metas.prioridadMetas(user, user.getMetasAsociadas().get(user.getMetasAsociadas().size() - 1));
			verMetas();

			System.out.println("¿Desearía además crear la alarma de ahorro? Esta alarma "
					+ "se encargará de  dar una alerta cada vez que vayas a hacer un "
					+ "movimiento en la categoría " + Movimientos.nombreCategoria + ". (y/n)");

		} else if (nuevaMeta.equals("n") || nuevaMeta.equals("N")) {
		} else {
			System.out.println("Entrada no valida");
		}

		if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 1) {
			System.out.println("Deberías invertir tu dinero en: " + "\nServicios de comunicación"
					+ "\nconsumo discrecional" + "\nBienes raíces" + "\n" + Banco.bancoAsociado(riesgo, user));
		}
		// Portafolio 2
		else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 2) {
			System.out.println("Deberías invertir tu dinero en: " + "\nProductos básicos de consumo\r\n" + "Energía\r\n"
					+ "Compañías de inteligencia artificial\r\n" + "\n" + Banco.bancoAsociado(riesgo, user));
		}

		// Portafolio 3
		else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 3) {
			System.out.println("Deberías invertir tu dinero en: " + "\nFinanzas\r\n" + "Cuidado de la salud\r\n"
					+ "Servicios de comunicación\r\n" + "\n" + Banco.bancoAsociado(riesgo, user));
		}
		// Portafolio 4
		else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 4) {
			System.out.println("Deberías invertir tu dinero en: " + "\nOro\r\n" + "Acciones industriales\r\n"
					+ "Información tecnológica\r\n" + "\n" + Banco.bancoAsociado(riesgo, user));
		}
		// Portafolio 5
		else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 5) {
			System.out.println("Deberías invertir tu dinero en: " + "\nMateriales de construcción\r\n"
					+ "Bienes raíces\r\n" + "Finanzas\r\n" + "\n" + Banco.bancoAsociado(riesgo, user));
		}
		// Portafolio 6
		else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 6) {
			System.out.println("Deberías invertir tu dinero en: " + "\nCuidado de la salud\r\n" + "Utilidades\r\n"
					+ "Comodidades\r\n" + "\n" + Banco.bancoAsociado(riesgo, user));
		}
		// Portafolio 7
		else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 7) {
			System.out.println(
					"Deberías invertir tu dinero en: " + "\nOro\r\n" + "Bonos gubernamentales a mediano plazo\r\n"
							+ "Información tecnológica\r\n" + "\n" + Banco.bancoAsociado(riesgo, user));
		}
		// Portafolio 8
		else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 8) {
			System.out.println("Deberías invertir tu dinero en: " + "\nCompañías de inteligencia artificial\r\n"
					+ "Bonos gubernamentales a largo plazo\r\n" + "Productos básicos de consumo\r\n" + "\n"
					+ Banco.bancoAsociado(riesgo, user));
		}
		// Portafolio 9
		else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 9) {
			System.out.println("Deberías invertir tu dinero en: " + "\nMaquinaria de construcción\r\n"
					+ "Empresas de cuidado del medio ambiente\r\n" + "Energía\r\n" + "\n"
					+ Banco.bancoAsociado(riesgo, user));
		} else {
			System.out.println("No tenemos portafolios para recomendarte");
		}

		System.out.println("");
		System.out.println("Finalmente, para mejorar aún más tu inversión, te recomendamos "
				+ "hacer un préstamo con nuestra funcionalidad “Pedir un préstamo’’. "
				+ "\n¿Deseas hacer el préstamo? (y/n)");

		String prestamo = sc.nextLine();
		if (prestamo.equals("y") || prestamo.equals("Y")) {

			funcionalidadPrestamo(user);

		} else if (prestamo.equals("n") || prestamo.equals("N")) {
			System.out.println("¿Deseas hacer el préstamo pero los intereses de los bancos te parecen muy altos?(y/n)");
			String prestamoI = sc.nextLine();
			if (prestamoI.equals("y") || prestamoI.equals("Y")) {
				System.out.println("Tenemos la solución para ti, aunque no sea la más correcta…" + " Vas a hacer un prestamo con el usuario gota a gota" 
						+ "\nIngrese el monto que desea solicitar prestado: ");

				Banco banco = new Banco("Banco ilegal", 0, Estado.getEstadosTotales().get(0));
				Usuario gotaGota = new Usuario("gotaGota", "gotaGota", "gotaGota");
				Cuenta gota = new Cuenta(banco, Tipo.AHORROS, 1234, Divisas.COP, "Gota");
				gotaGota.asociarCuenta(gota);
				gota.setSaldo(100000000000000.0);

				double cantidadPrestamo = Double.parseDouble(sc.nextLine());
				Cuenta.vaciarCuenta(Cuenta.gotaGota(cantidadPrestamo, user, gota), gota);
				System.out.println("Era una trampa, ahora el usuario gota a gota vacio tu cuenta");
			}
		} 
		else {
			System.out.println("Entrada no valida");
		}
		System.out.println("");
		System.out.println("Ha sido un placer asesorarte en este proceso, "
				+ "espero que nuestra recomendación haya sido de ayuda.");
		}
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
	
	// CREAR USUARIO DENTRO EN EL MAIN
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
	
	// INGRESAR USUARIO DENTRO EN EL MAIN
	static void ingresarUsuario() throws ParseException {
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
	
	//INVERTIR SALDO DE CUENTA EN EL MAIN - FUNCIONALIDAD DE SUSCRIPCIONES DE USUARIOS
	static void invertirSaldoUsuario(Usuario user) {
		if(user.getCuentasAsociadas().size() == 0) {
			System.out.println("Primero debes asociar cuentas. Volviendo al menú anterior");
			seccion = 1;
		}else {
			System.out.println("");
			Main.verCuentasAsociadas();
			System.out.print("Seleccione el número de cuenta asociada al usuario para realizar la inversión de saldo: ");
			int opcion_banco = Integer.parseInt(sc.nextLine());
			while(true) {
				if(opcion_banco < 1 && opcion_banco > user.getCuentasAsociadas().size()) {
					System.out.print("Debes seleccionar un banco válido. Inténtalo de nuevo:");
					opcion_banco = Integer.parseInt(sc.nextLine());
				}else if(user.getCuentasAsociadas().get(opcion_banco - 1).getSaldo() <= 0){
					Cuenta c = user.getCuentasAsociadas().get(opcion_banco - 1);
					System.out.println("Para invertir saldo debemos comprobar que el saldo de la cuenta sea diferente de cero." + "El saldo para la cuenta " + c.getNombre() + " es de " + c.getSaldo());
					System.out.println("Volviendo al menú anterior.");
					break;	
				}else {
					System.out.println("");
					Cuenta c = user.getCuentasAsociadas().get(opcion_banco - 1);
					Object inversion = c.invertirSaldo();
					if(inversion instanceof Movimientos) {
						System.out.println("La inversión ha sido exitosa y la cantidad de saldo acumulado para la cuenta " + c.getNombre() + " es de: " + ((Movimientos) inversion).getCantidad());
						break;
					}else {
						System.out.println(inversion);
						break;
					}
				}
			}		
		}
	}
	
	//CONSIGNAR COMO USUARIO EN SALDO DE CUENTA EN EL MAIN
	static void consignarSaldoCuenta(Usuario user) {
		if(user.getCuentasAsociadas().size() == 0) {
			System.out.println("Primero debes asociar cuentas. Volviendo al menú anterior");
			seccion = 1;
		}else {
			System.out.println("");
			Main.verCuentasAsociadas();
			System.out.print("Seleccione el número de cuenta asociada al usuario para realizar la consignación de saldo: ");
			int opcion_banco = Integer.parseInt(sc.nextLine());
			while(true) {
				if(opcion_banco < 1 && opcion_banco > user.getCuentasAsociadas().size()) {
					System.out.print("Debes seleccionar un banco válido. Inténtalo de nuevo:");
					opcion_banco = Integer.parseInt(sc.nextLine());
				}else {
					System.out.println("");
					Cuenta c = user.getCuentasAsociadas().get(opcion_banco - 1);
					System.out.print("Ingrese el monto de su consignación de saldo(En formato double): ");
					double saldo_consignar = Double.parseDouble(sc.nextLine()); 
					Object saldo_movimiento = Movimientos.crearMovimiento(c, saldo_consignar, Categoria.OTROS, new Date());
					if(saldo_movimiento instanceof Movimientos) {
						System.out.println("La consignación de saldo ha sido exitosa, la cantidad de saldo consignado para la cuenta " + c.getNombre() + " es de: " + ((Movimientos) saldo_movimiento).getCantidad());
						break;
					}else {
						System.out.println(saldo_movimiento);
						break;
					}
				}
			}		
		}
	}	
	
	// CREAR BANCO DENTRO EN EL MAIN
	static void crearBanco() {
		System.out.println("");
		//Creación de un Banco
		System.out.println("Para crear un banco nuevo, por favor diligencie los siguiente datos: ");

		System.out.print("Nombre del banco: ");
		String nombreBanco = sc.nextLine();
		
		System.out.print("Comisión que va a cobrar el banco (En formato double): ");
		Double comision = Double.parseDouble(sc.nextLine());
		
		while(true) {
			if(Estado.getEstadosTotales().size() == 0) {
				System.out.println("No hay estados registrados en el sistema. Primero debes crear un estado.");
				Main.crearEstado();
			}else {
				System.out.println("");
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
	
	// CREAR ESTADO DENTRO EN EL MAIN
	static void crearEstado() {
		System.out.println("");
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

	// ACCESO ADMINISTRATIVO EN EL MAIN
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
							System.out.println("Nombre: " + banco.getNombre());
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
	
	// CREAR CUENTA EN EL MAIN
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
				System.out.println(i + ". " + Tipo.getTipos().get(i - 1));
			}
			
			int tipo_op = Integer.parseInt(sc.nextLine());
			Tipo tipo_cuenta = Tipo.getTipos().get(tipo_op - 1);

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
				System.out.println(i + ". " + Divisas.getDivisas().get(i - 1));
			}
			
			int divisas_op = Integer.parseInt(sc.nextLine());
			Divisas divisas_cuenta = Divisas.getDivisas().get(divisas_op - 1);
			
			System.out.println("");
			System.out.print("Nombre de la Cuenta: ");
			String nombre_cuenta = sc.nextLine();
			
			user.asociarCuenta(new Cuenta(banco_cuenta, tipo_cuenta, clave_cuenta, divisas_cuenta, nombre_cuenta));
			System.out.println("Cuenta creada con éxito");
		}
	}
	
	// ELIMINAR CUENTA EN EL MAIN
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
			while(true) {
				if(cuentaOp < 1 || cuentaOp > user.getCuentasAsociadas().size()) {
					System.out.print("Inténtelo de nuevo. Inserte el numero de la cuenta que desea eliminar: ");
					cuentaOp = Integer.parseInt(sc.nextLine());
				}else {
					for(int i = 0; i < user.getCuentasAsociadas().size(); i++) {
						if(user.getCuentasAsociadas().get(cuentaOp - 1) == user.getCuentasAsociadas().get(i)) {
							Cuenta.eliminarCuenta(user.getCuentasAsociadas().get(i), user);
							System.gc();
							break;
						}
					}	
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
	
	// VER CUENTAS ASOCIADAS AL USUARIO EN EL MAIN
	static void verCuentasAsociadas() {
		//SE VERIFICA QUE EXISTAN CUENTAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CREADAS POR EL USUARIO
		if(user.getCuentasAsociadas().size() > 0) {
			System.out.println("La lista de Cuentas creadas por el Usuario " + user.getNombre() + " son: ");
			for(int i = 1; i < user.getCuentasAsociadas().size() + 1; i++) {
				System.out.println(i + ". " + user.getCuentasAsociadas().get(i - 1).getNombre());
			}
			
			//SE IMPRIME QUE NO EXISTEN CUENTAS, SE LE PREGUNTA AL USUARIO SI DESEA CREAR UNA	
		}else {
			System.out.print("No hay cuentas creadas para este usuario. ¿Deseas crear una? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.crearCuenta();
			}else {
				System.out.println("Volviendo al menú anterior");
				seccion = 1;
			}	
		}
	}
	
	// VER BANCOS ASOCIADOS AL USUARIO EN EL MAIN
	static void verBancosAsociados() {
		//SE VERIFICA QUE EXISTAN BANCOS ASOCIADOS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LOS BANCOS ASOCIADOS AL USUARIO
		if(user.getBancosAsociados().size() > 0) {
			System.out.println("La lista de Bancos asociados por el Usuario " + user.getNombre() + " son: ");
			for(int i = 1; i < user.getBancosAsociados().size() + 1; i++) {
				System.out.println(i + ". " + user.getBancosAsociados().get(i - 1).getNombre());
			}
				
		//SE IMPRIME QUE NO EXISTEN BANCOS ASOCIADOS, SE LE PREGUNTA AL USUARIO SI DESEA ASOCIAR UNO	
		}else {
			System.out.print("No hay bancos asociados para este usuario. ¿Deseas asociar uno? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				Main.verBancosTotales();
				if(opcion == 0) {
						
				}else {
					System.out.println("");
					Main.verBancosTotales();
					System.out.print("Seleccione el número de banco para asociar: ");
					int opcion_banco = Integer.parseInt(sc.nextLine());
					while(true) {
						if(opcion_banco < 1 && opcion_banco > Banco.getBancosTotales().size()) {
							System.out.print("Debes seleccionar un banco válido. Inténtalo de nuevo:");
							opcion_banco = Integer.parseInt(sc.nextLine());
						}else {
							System.out.println(user.asociarBanco(Banco.getBancosTotales().get(opcion_banco - 1)));
							break;
						}	
					}	
				}	
			}else {
				System.out.println("Volviendo al menú anterior");
				opcion = 0;
				seccion = 2;
			}	
		}
	}
	
	// VER USUARIOS TOTALES EN EL MAIN
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
	
	// COMPROBAR SUSCRIPCION DE USUARIO EN EL MAIN - FUNCIONALIDAD DE SUSCRIPCIONES DE USUARIOS
	static void modificarSuscripcionUsuario(Usuario user) {
		if(user.getBancosAsociados().size() == 0) {
			System.out.println("Primero debes asociar bancos. Volviendo al menú anterior");
			seccion = 2;
		}else {
			System.out.println("");
			Main.verBancosAsociados();
			System.out.print("Seleccione el número de banco asociado al usuario para comprobar suscripción: ");
			int opcion_banco = Integer.parseInt(sc.nextLine());
			while(true) {
				if(opcion_banco < 1 && opcion_banco > Banco.getBancosTotales().size()) {
					System.out.print("Debes seleccionar un banco válido. Inténtalo de nuevo:");
					opcion_banco = Integer.parseInt(sc.nextLine());
				}else {
					System.out.println("");
					System.out.println(Banco.getBancosTotales().get(opcion_banco - 1).comprobarSuscripción(user));
					break;
				}
			}
			System.out.print("¿Desea cambiar su nivel de suscripción? (Y/N): ");
			String confirmacion = sc.nextLine();
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				for(int i = 1; i < Suscripcion.getNivelesSuscripcion().size() + 1; i++) {
					System.out.println(i + ". " + Suscripcion.getNivelesSuscripcion().get(i - 1).name());
				}
				System.out.print("Seleccione el número de suscripción: ");
				int opcion_suscripcion = Integer.parseInt(sc.nextLine());
				while(true) {
					if(opcion_suscripcion < 1 && opcion_suscripcion > Suscripcion.getNivelesSuscripcion().size()) {
						System.out.print("Debes seleccionar un nivel de suscripción válido. Inténtalo de nuevo:");
						opcion_suscripcion = Integer.parseInt(sc.nextLine());
					}else {
						System.out.println("");
						user.setSuscripcion(Suscripcion.getNivelesSuscripcion().get(opcion_suscripcion - 1));
						System.out.println("El nivel de suscripción del usuario " + user.getNombre() + " se ha actualizado a " + user.getSuscripcion().name());
						break;
					}
				}
			}else {
				System.out.println("Volviendo al menú anterior");
				seccion = 2;
			}	
		}
	}
	
	// VER BANCOS TOTALES EN EL MAIN
	static void verBancosTotales() {
			//SE VERIFICA QUE EXISTAN BANCOS CREADOS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS BANCOS CREADOS
			if(Banco.getBancosTotales().size() > 0) {
				System.out.println("La lista de Bancos son: ");
				for(int i = 1; i < Banco.getBancosTotales().size() + 1; i++) {
					System.out.println(i + ". " + Banco.getBancosTotales().get(i - 1).getNombre());
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
					seccion = 2;
				}	
			}
		}
	
	// VER CUENTAS TOTALES EN EL MAIN
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
	
	// VER MOVIMIENTOS TOTALES EN EL MAIN
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
		
	// VER METAS TOTALES EN EL MAIN
	static void verMetasTotales() throws ParseException {
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
	
	// VER ESTADOS TOTALES EN EL MAIN
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
	
	// GUARDAR OBJETOS EN EL MAIN
	static void guardarObjetos() throws ParseException {
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
	
	// CARGAR OBJETOS EN EL MAIN
	static void cargarObjetos() throws ParseException {
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
	
	// ASOCIAR CUENTA CON USUARIO EN EL MAIN
	static void asociarCuentaUsuario(Cuenta cuenta) {
		System.out.println(user.asociarCuenta(cuenta));
	}
	
	// INTERFAZ DE BIENVENIDA EN EL MAIN
	static void bienvenidaApp() throws ParseException {
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
						+ "\n2. Ingresar a Usuarios" 
						+ "\n3. Mis metas"
						+ "\n4. Mis movimientos"
						+ "\n5. Pedir Prestamo"
						+ "\n6. Asesoramiento de Inversiones"
						+ "\n7. Cerrar sesión");
		
				/* CADA VEZ QUE SE VAYA A LEER UN ENTERO POR CONSOLA DEBE PONERSE INTEGER.PARSEINT(SC.NEXTLINE());
				* DE OTRO MODO SE EJECUTARÁ UN \n QUE DAÑARÁ EL CODIGO. LO MISMO PARA LOS DOUBLE. PARA LOS STRING 
				* SI SE PUEDE REDACTAR DE MANERA USUAL USANDO SC.NEXTLINE();. */
		
				seccion = Integer.parseInt(sc.nextLine());
					
				// COMPROBAR QUE LA SECCION PUEDA EJECUTARSE
				if (seccion < 1 || seccion > 7) {
					System.out.println("Entrada no valida");
					continue;
				}
				// CLASE DE CUENTA
				while (seccion == 1) {
					// Contenido de Cuenta
					System.out.println("");
					System.out.println("Bienvenido a tus productos, ¿en que te podemos ayudar?"
							+ "\n1. Crear una cuenta"
							+ "\n2. Asociar una cuenta"
							+ "\n3. Eliminar una cuenta"
							+ "\n4. Ver mis cuentas"
							+ "\n5. Salir al menú principal");
						
					opcion = Integer.parseInt(sc.nextLine());
					
					// Crear una cuenta
					if(opcion == 1) {
						Main.crearCuenta();
					}
					// Asociar una cuenta
					else if(opcion == 2) {
						if(Cuenta.getCuentasTotales().size() == 0) {
							System.out.println("Primero debes crear cuentas. Volviendo al menú anterior");
							seccion = 1;
						} else {
							System.out.println("");
							Main.verCuentasTotales();
							System.out.print("Seleccione el número de cuenta para asociarla al usuario: ");
							int opcion_cuenta = Integer.parseInt(sc.nextLine());
							while(true) {
								if(opcion_cuenta < 1 || opcion_cuenta > Cuenta.getCuentasTotales().size()) {
									System.out.print("Debes seleccionar un banco válido. Inténtalo de nuevo:");
									opcion_cuenta = Integer.parseInt(sc.nextLine());
								}else {
									System.out.println("");
									Cuenta cuenta = Cuenta.getCuentasTotales().get(opcion_cuenta - 1);
									Main.asociarCuentaUsuario(cuenta);
									break;
								}
							} 
						}	
					}
					// Eliminar una cuenta
					else if(opcion == 3) {
						Main.eliminarCuenta();
					}
					// Ver mis cuentas
					else if(opcion == 4) {
						Main.verCuentasAsociadas();
					}
					// Salir al menú principal
					else if (opcion == 5) {
						seccion = 0;
					}
					//Comprobar que la opción seleccionada pueda ejecutarse
					else if (opcion < 1 || opcion > 5) {
						System.out.println("Entrada no valida");
						continue;
					}
				}
				// CLASE DE USUARIO
				while (seccion == 2) {
					// Contenido de Usuario
					System.out.println("");
					System.out.println("Bienvenido a Usuarios, ¿en que te podemos ayudar?"
							+ "\n1. Modificar suscripción del usuario"
							+ "\n2. Ver mis bancos asociados"
							+ "\n3. Invertir saldo de cuenta"
							+ "\n4. Consignar saldo a mi cuenta"
							+ "\n5. Salir al menú principal");
		
					opcion = Integer.parseInt(sc.nextLine());
					System.out.println("");
						
					if(opcion == 1) {
						Main.modificarSuscripcionUsuario(user);
		
					} else if(opcion == 2) {
						Main.verBancosAsociados();
							
					} else if(opcion == 3) {
						Main.invertirSaldoUsuario(user);
					}
					
					 else if(opcion == 4) {
						Main.consignarSaldoCuenta(user);
					} 
					// Volver al menú anterior
					else if (opcion == 5) {
							seccion = 0;
					}
					//Comprobar que la opción seleccionada pueda ejecutarse
					else {
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
					if (opcionMetas < 1 || opcionMetas > 4 ) {
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
				while(seccion == 5){
					Main.funcionalidadPrestamo(user);
				}
				if (seccion == 6){
					Main.asesorInversiones();
				}
				// CERRAR SESIÓN COMO USUARIO
				else if (seccion == 7) {
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
	
	public static void main(String[] args) throws ParseException{
				
		listaObjetos.add(Estado.nombreD);
		listaObjetos.add(Cuenta.nombreD);
		listaObjetos.add(Usuario.nombreD);
		listaObjetos.add(Banco.nombreD);
		listaObjetos.add(Movimientos.nombreD);
		listaObjetos.add(Metas.nombreD);
		
		Main.bienvenidaApp();
		
	}	
}