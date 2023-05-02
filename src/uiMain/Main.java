package uiMain;

import baseDatos.Serializador;
import baseDatos.Deserializador;
import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Cuotas;
import gestorAplicación.externo.Divisas;
import gestorAplicación.externo.Estado;
import gestorAplicación.interno.Ahorros;
import gestorAplicación.interno.Categoria;
import gestorAplicación.interno.Corriente;
import gestorAplicación.interno.Cuenta;
import gestorAplicación.interno.Metas;
import gestorAplicación.interno.Movimientos;
import gestorAplicación.interno.Suscripcion;
import gestorAplicación.interno.Usuario;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.time.Instant;

public final class Main {
	
	// FUNCIONALIDAD DE PRESTAMO 
	private static void funcionalidadPrestamo(Usuario usu){
		System.out.println("Bienvenido a Prestamos");
		ArrayList prestamo = usu.comprobarConfiabilidad(usu);
		if(prestamo.get(0) instanceof Ahorros){
			// Si tiene cuentas entonces vamos a comprar cuanto dieron prestan los bancos de las cuentas y mostrale al usuario
			prestamo = Ahorros.comprobarPrestamo(prestamo);
			if(prestamo.get(0) instanceof Ahorros){
				System.out.println("Estas son las cuentas valida para hacer un prestamo y el valor maximo del prestamo");
				for(int i=0;i<prestamo.size();i++){
					Ahorros cuenta = (Ahorros) prestamo.get(i);
					System.out.println(i+"-Cuenta: "+ cuenta.getNombre()+" Maximo a prestar:"+cuenta.getBanco().getPrestamo()*usu.getSuscripcion().getPorcentajePrestamo());
				}
				System.out.println(prestamo.size()+"-Salir al Menú");
				System.out.println("Seleccione una:");
//				recibe la opccion del usuario
				int opcion = Integer.parseInt(sc.nextLine());
				System.out.println("");

//				En caso de que desee salir se sale,
				if(opcion==prestamo.size()){
					return;
				}else{
//					encaso de que seleccione una de las cuentas
					Ahorros cuenta = (Ahorros) prestamo.get(opcion);
					double maxPrestamo = cuenta.getBanco().getPrestamo()*usu.getSuscripcion().getLimiteCuentas();
					System.out.println("Ingrese el valor del prestamo, el valor de este debe ser menor de $"+maxPrestamo);
					maxPrestamo = Double.parseDouble(sc.nextLine());
					Boolean exito = Movimientos.realizarPrestamo(cuenta,maxPrestamo);
					if(exito){
						System.out.println("|----------------------------------|\n\nPrestamo Realizado con Exito\n\n|----------------------------------|\n\n");
					}else{
						System.out.println("|----------------------------------|\n\nPor favor seleccione una cantidad adecuada\n\n|----------------------------------|\n\n");
						funcionalidadPrestamo(usu);
					}
				}


			}else{
				System.out.println("|----------------------------------|\n\nLos bancos de sus cuentas no realizan prestamos\n\n|----------------------------------|\n\n");
				return;
			}
		}else{
			for(int i = 0;i<prestamo.size();i++){
				System.out.println(prestamo.get(i));
			}
			return ;
		}
	}
	
	// CREAR UNA META EN EL MAIN	
	static void crearMeta() throws ParseException {

		int opcionMetas = 1;

		while (opcionMetas == 1) {
			// FORMATO EN EL QUE DESEA CREAR LA META
			System.out.println("¿En qué formato le gustaría crear su meta?: "
					+ "\n1. NombreMeta, CantidadMeta, FechaMeta" + "\n2. NombreMeta, CantidadMeta"
					+ "\n3. NombreMeta, FechaMeta" + "\n4. CantidadMeta, FechaMeta");

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
						opcionMetas = 0;
					}
				}

				else {
					Metas meta = new Metas(nombreMe, cantidadMe, fechaMe, 1);
					user.asociarMeta(meta);
				}

				System.out.println("Sus metas son: ");

				// Mostrar las metas del usuario
				verMetas();

				System.out.println("");

				// Terminar o continuar
				System.out.println(
						"¿Desea crear otra meta? " + "\nEscriba “y” para sí o “n” para salir al menú de Metas");
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
					opcionMetas = 0;
				}

			}

			else if (formato == 2) {
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
						opcionMetas = 1;
					}
				}

				else {
					Metas meta = new Metas(nombreMe, cantidadMe, 1);
					user.asociarMeta(meta);
				}

				System.out.println("Sus metas son: ");

				// Mostrar las metas del usuario
				verMetas();

				System.out.println("");

				// Terminar o continuar
				System.out.println(
						"¿Desea crear otra meta? " + "\nEscriba “y” para sí o “n” para salir al menú de Metas");
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
					opcionMetas = 0;
				}

			}

			else if (formato == 3) {
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
						opcionMetas = 0;
					}
				}

				else {
					Metas meta = new Metas(nombreMe, fechaMe, 1);
					user.asociarMeta(meta);
				}

				System.out.println("Sus metas son: ");

				// Mostrar las metas del usuario
				verMetas();

				System.out.println("");

				// Terminar o continuar
				System.out.println(
						"¿Desea crear otra meta? " + "\nEscriba “y” para sí o “n” para salir al menú de Metas");
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
					opcionMetas = 0;
				}
			}

			else if (formato == 4) {
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
						opcionMetas = 0;
					}
				}

				else {
					Metas meta = new Metas(cantidadMe, fechaMe, 1);
					user.asociarMeta(meta);
				}

				System.out.println("Sus metas son: ");

				// Mostrar las metas del usuario
				verMetas();

				System.out.println("");

				// Terminar o continuar
				System.out.println(
						"¿Desea crear otra meta? " + "\nEscriba “y” para sí o “n” para salir al menú de Metas");
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
					opcionMetas = 0;
				}

			}

			else {
				System.out.println("Alguna de las entradas no es valida");
				opcionMetas = 0;
			}
		}
	}

	// ELIMINAR UNA META EN EL MAIN
	static void eliminarMeta() {
		int opcionEliminarMeta = 1;
		while (opcionEliminarMeta == 1) {

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
			System.out
					.println("¿Desea eliminar otra meta? " + "\nEscriba “y” para sí o “n” para salir al menú de Metas");
			String c = sc.nextLine();
			System.out.println("");

			if (c.equals("y") || c.equals("Y")) {
				opcionEliminarMeta = 1;
			}

			// Salir al menu de metas
			else if (c.equals("n") || c.equals("N")) {
				opcionEliminarMeta = 0;
			}

			// Validar entrada
			else {
				System.out.println("Entrada no valida");
				System.out.println("");
				opcionEliminarMeta = 0;
			}
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
		int funcionalidad = 1;
		while (funcionalidad == 1) {
			// Se confirman que hayan ciertos requeriminetos para el buen funcionamiento de
			// la funcionalidad
			if (user.getMetasAsociadas().size() == 0) {
				System.out.println("Primero debes crear una meta para acceder a esta funcionalidad");
			}

			else if (user.getBancosAsociados().size() == 0) {
				System.out.println("Primero debes estar asociado a un banco para acceder a esta funcionalidad");
			}

			else if (user.getCuentasAhorrosAsociadas().size() == 0 && user.getCuentasCorrienteAsociadas().size() == 0) {
				System.out.println(
						"Primero debes crear una cuenta asociada a tu usuario para acceder a esta funcionalidad");
			}

			else if (user.getMovimientosAsociados().size() == 0) {
				System.out.println("Primero debes realizar un movimiento para acceder a esta funcionalidad");
			}

			else {
				System.out.println("¿Cuál es su tolerancia de riesgos?: " + "\n1. Baja" + "\n2. Media" + "\n3. Alta");
				int riesgo = Integer.parseInt(sc.nextLine());
				System.out.println("");

				// Revisar que las entradas sean correctas
				if (riesgo < 1 || riesgo > 3) {
					System.out.println("Alguna de las entradas no es válida");
					break;
				}

				else if (Metas.revisionMetas(user) == null) {
					System.out.println("Primero debes crear una meta con fecha para acceder a esta funcionalidad");
					break;
				}

				else {
					
					System.out.println("¿Qué cantidad piensa invertir?: ");
					int invertir = Integer.parseInt(sc.nextLine());
					System.out.println("");
					
					System.out.println("Tienes una meta para una fecha muy próxima: "
							+ Metas.revisionMetas(user).getNombre() + ", " + Metas.revisionMetas(user).getCantidad()
							+ ", " + Metas.revisionMetas(user).getFechaNormal()
							+ "\n¿Desearías cambiar la fecha de esta meta para invertir ese dinero "
							+ "en tu portafolio? (Y/N)");
					String cambiarFecha = sc.nextLine();
					System.out.println("");

					if (cambiarFecha.equals("y") || cambiarFecha.equals("Y")) {
						System.out.println("¿Para que fecha desearías cambiar la meta? " + "(formato dd/MM/yyyy)");
						String nuevaFecha = sc.nextLine();
						Metas.determinarPlazo(Metas.cambioFecha(Metas.revisionMetas(user), nuevaFecha));
						System.out.println("");
						System.out.println("La fecha ha sido modificada satisfactoriamente");
						System.out.println("");
						System.out.println("Su plazo de inversión es: Plazo " + Metas.plazo);
						System.out.println("");
					}

					else if (cambiarFecha.equals("n") || cambiarFecha.equals("N")) {
						Metas.determinarPlazo(Metas.revisionMetas(user));
						System.out.println("Su plazo de inversión es: Plazo " + Metas.plazo);
						System.out.println("");
					}

					else {
						System.out.println("Entrada no valida");
						break;
					}

					System.out.println("Con el fin de aumentar la inversión inicial y hacer una buena "
							+ "recomendación, analizaremos " + "\nsus movimientos para encontrar la categoría "
							+ "en la que más dinero ha gastado.");
					System.out.println("");

					// Buscamos la categoría en la que el usuario ha gastado más dinero
					Movimientos.analizarCategoria(user, Metas.plazo);

					System.out.println(
							"La categoría en la que más dinero ha gastado es en: " + Movimientos.nombreCategoria
									+ " que suma un total de " + Movimientos.cantidadCategoria + ".");
					System.out.println("Le sugerimos crear una meta con el fin de ahorrar la misma "
							+ "cantidad que ha gastado en esta categoría. "
							+ "\nSi desea crear la meta escriba “y”. En caso contrario escriba “n”.");
					String nuevaMeta = sc.nextLine();
					System.out.println("");

					// Crear una meta con los parametros preestablecidos
					if (nuevaMeta.equals("y") || nuevaMeta.equals("Y")) {
						System.out.println(
								"Usaremos tus datos para crear la meta. Luego vamos a priorizar esa meta respecto a las demás que tengas");

						Metas metaCategoria = new Metas(Movimientos.nombreCategoria, Movimientos.cantidadCategoria,
								Movimientos.recomendarFecha, 1);

						user.asociarMeta(metaCategoria);

						// Priorizamos la meta
						System.out.println(
								"La meta ha sido creada satisfactoriamente y será puesta como prioridad en tu lista de metas");
						Metas.prioridadMetas(user, metaCategoria);
						verMetas();
						System.out.println("");

					} else if (nuevaMeta.equals("n") || nuevaMeta.equals("N")) {}

					else {
						System.out.println("Entrada no valida");
						break;
					}

					String bancoPortafolio = "El banco asociado al portafolio es: "
							+ Banco.bancoPortafolio(user).getNombre() + ". Con una tasa de interes del: "
							+ Banco.interesesPortafolio(Banco.bancoPortafolio(user), user) + "%";

					if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 1) {
						System.out.println("Deberías invertir tu dinero en: " + "\nServicios de comunicación"
								+ "\nconsumo discrecional" + "\nBienes raíces" + "\n" + bancoPortafolio);
					}
					// Portafolio 2
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 2) {
						System.out.println("Deberías invertir tu dinero en: " + "\nProductos básicos de consumo\r\n"
								+ "Energía\r\n" + "Compañías de inteligencia artificial\r\n" + "\n" + bancoPortafolio);
					}
					// Portafolio 3
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 3) {
						System.out.println("Deberías invertir tu dinero en: " + "\nFinanzas\r\n"
								+ "Cuidado de la salud\r\n" + "Servicios de comunicación\r\n" + "\n" + bancoPortafolio);
					}
					// Portafolio 4
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 4) {
						System.out.println("Deberías invertir tu dinero en: " + "\nOro\r\n"
								+ "Acciones industriales\r\n" + "Información tecnológica\r\n" + "\n" + bancoPortafolio);
					}
					// Portafolio 5
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 5) {
						System.out.println("Deberías invertir tu dinero en: " + "\nMateriales de construcción\r\n"
								+ "Bienes raíces\r\n" + "Finanzas\r\n" + "\n" + bancoPortafolio);
					}
					// Portafolio 6
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 6) {
						System.out.println("Deberías invertir tu dinero en: " + "\nCuidado de la salud\r\n"
								+ "Utilidades\r\n" + "Comodidades\r\n" + "\n" + bancoPortafolio);
					}
					// Portafolio 7
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 7) {
						System.out.println("Deberías invertir tu dinero en: " + "\nOro\r\n"
								+ "Bonos gubernamentales a mediano plazo\r\n" + "Información tecnológica\r\n" + "\n"
								+ bancoPortafolio);
					}
					// Portafolio 8
					else if (Banco.retornoPortafolio(riesgo, invertir, Metas.plazo, user) == 8) {
						System.out.println(
								"Deberías invertir tu dinero en: " + "\nCompañías de inteligencia artificial\r\n"
										+ "Bonos gubernamentales a largo plazo\r\n" + "Productos básicos de consumo\r\n"
										+ "\n" + bancoPortafolio);
					}

					else {
						System.out.println("No tenemos portafolios para recomendarte");
					}

					System.out.println("");
					System.out.println("Finalmente, para mejorar aún más tu inversión, te recomendamos "
							+ "hacer un préstamo con nuestra funcionalidad " + "\n“Pedir un préstamo”. "
							+ "\n¿Deseas hacer el préstamo? (Y/N)");

					String prestamo = sc.nextLine();
					if (prestamo.equals("y") || prestamo.equals("Y")) {

						funcionalidadPrestamo(user);

					}

					else if (prestamo.equals("n") || prestamo.equals("N")) {
						System.out.println(
								"¿Deseas hacer el préstamo pero los intereses de los bancos te parecen muy altos?(Y/N)");
						String prestamoI = sc.nextLine();
						if (prestamoI.equals("y") || prestamoI.equals("Y")) {
							System.out.println("Tenemos la solución para ti, aunque no sea la más correcta…"
									+ " Vas a hacer un prestamo con el usuario " + "\ngota a gota"
									+ "\nIngrese el monto que desea solicitar prestado: ");

							// Parte del gota a gota
							Banco banco = new Banco("Banco ilegal", 0, Estado.getEstadosTotales().get(0));
							Usuario gotaGota = new Usuario("gotaGota", "gotaGota", "gotaGota");
							Ahorros gota = new Ahorros(banco, 1234, Divisas.COP, "Gota", 1000000000.0);
							gotaGota.asociarCuentaAhorros(gota);
							gota.setTitular(gotaGota);
							double cantidadPrestamo = Double.parseDouble(sc.nextLine());

							// Métodos
							Cuenta.gotaGota(cantidadPrestamo, user, gota).vaciarCuenta(gota);
							System.out.println("Era una trampa, ahora el usuario gota a gota vació tu cuenta");
						}
					}

					else {
						System.out.println("Entrada no valida");
						break;
					}

					// Fin de la funcionalidad
					System.out.println("");
					System.out.println("Ha sido un placer asesorarte en este proceso, "
							+ "espero que nuestra recomendación haya sido de ayuda.");
				}
			}
			funcionalidad = 0;
		}
	}
	
	// Sobrecarga funcionalidad para chequeo en eliminación de cuenta
	static boolean compraCartera(Corriente cuenta) {
		Usuario usuario = cuenta.getTitular();
		
		//Arreglo que almacena las cuentas asociadas a un usuario
		ArrayList<Cuenta> cuentasAux = usuario.getCuentasAsociadas();
		
		cuentasAux.remove(cuenta);
		
		//Arreglo que almacena las cuentas capaces de recibir la deuda
		ArrayList<Corriente> cuentasCapacesDeuda = usuario.Capacidad_Endeudamiento(cuentasAux, cuenta);
		//Arreglo que almacena las tasas de intereses aplicables con orden del arreglo anterior
		ArrayList<Double> tasacionCuentas = Banco.verificarTasasdeInteres(usuario, cuentasCapacesDeuda);
		
		System.out.println("Las cuentas a su nombre que pueden recibir la deuda de la Cuenta a eliminar son: ");
		for (int i = 0; i <= cuentasCapacesDeuda.size(); i++) {
			System.out.println(i + 1 + ". " + cuentasCapacesDeuda.get(i)
					+ "\n Tasa de Interés: " + tasacionCuentas.get(i));
			System.out.println("");
		}
		
		//Atributo de validacion de la entrada Cuenta_Destino
		boolean validacion_Cuenta_Destino = true;
		//Atributo auxiliar que almacenará la cuenta destino de la deuda
		int Cuenta_Destino = 0;
		while (validacion_Cuenta_Destino) {
			System.out.println("Por favor escoga la cuenta destino de la deuda:");
			Cuenta_Destino = Integer.parseInt(sc.nextLine());
			if (Cuenta_Destino >= 1 && Cuenta_Destino <= cuentasCapacesDeuda.size()) {
				validacion_Cuenta_Destino = false;
			}
			else {
				System.out.println("Entrada no válida, intente de nuevo");
			}
		}
		
		//Atributo de validacion de la entrada Periodicidad
		boolean validacion_Periodicidad = true;
		//Atributo auxiliar para almacenar decision de periodicidad
		int Periodicidad = 0;
		while (validacion_Periodicidad) {
			System.out.println("¿Desea mantener la periodicidad del pago de la deuda?"
					+ "\n1. Sí"
					+ "\n2. No");
			Periodicidad = Integer.parseInt(sc.nextLine());
			if (Periodicidad == 1 || Periodicidad == 2) {
				validacion_Periodicidad = false;
			} else {
				System.out.println("Entrada no válida, intente de nuevo");
			}
		}
		
		if (Periodicidad == 1) {
			System.out.println("Perfecto, la deuda mantendrá un plazo de pago a " + cuentasCapacesDeuda.get(Cuenta_Destino - 1).getPlazo_Pago());
		}
		//Atributo de la periodicidad
		Cuotas eleccion_periodicidad = null;
		if (Periodicidad == 2) {
			//Atributo de validacion de la seleccion de periodicidad
			boolean validacion_Seleccion_Periodicidad = true;
			int seleccion_periodicidad = 0;
			
			while (validacion_Seleccion_Periodicidad) {
				System.out.println("Por favor seleccione la nueva periodicidad de la Deuda: "
						+ "\n1. 1 Cuota"
						+ "\n2. 6 Cuotas"
						+ "\n3. 12 Cuotas"
						+ "\n4. 18 Cuotas"
						+ "\n5. 24 Cuotas"
						+ "\n6. 36 Cuotas"
						+ "\n7. 48 Cuotas");
				seleccion_periodicidad = Integer.parseInt(sc.nextLine());
				if (seleccion_periodicidad < 1 || seleccion_periodicidad > 7) {
					System.out.println("Entrada no válida, intente de nuevo");
				}
				else {
					validacion_Seleccion_Periodicidad = false;
				}
			}
			switch(seleccion_periodicidad) {
				case 1:
					eleccion_periodicidad = Cuotas.C1;
					System.out.println("Deuda establecida a: " + Cuotas.C1.getCantidad_Cuotas());
					break;
				case 2:
					eleccion_periodicidad = Cuotas.C6;
					System.out.println("Deuda establecida a: " + Cuotas.C6.getCantidad_Cuotas());
					break;
				case 3:
					eleccion_periodicidad = Cuotas.C12;
					System.out.println("Deuda establecida a: " + Cuotas.C12.getCantidad_Cuotas());
					break;
				case 4:
					eleccion_periodicidad = Cuotas.C18;
					System.out.println("Deuda establecida a: " + Cuotas.C18.getCantidad_Cuotas());
					break;
				case 5:
					eleccion_periodicidad = Cuotas.C24;
					System.out.println("Deuda establecida a: " + Cuotas.C24.getCantidad_Cuotas());
					break;
				case 6:
					eleccion_periodicidad = Cuotas.C36;
					System.out.println("Deuda establecida a: " + Cuotas.C36.getCantidad_Cuotas());
					break;
				case 7:
					eleccion_periodicidad = Cuotas.C48;
					System.out.println("Deuda establecida a: " + Cuotas.C48.getCantidad_Cuotas());
					break;
			}
		}
		
		//Vista Previa de los resultados del cambio
		System.out.println("Vista previa de como quedaría la cuenta escogida para recibir la deuda: ");
		Corriente vistaPrevia = Corriente.vistaPreviaMovimiento(cuentasCapacesDeuda.get(Cuenta_Destino - 1), eleccion_periodicidad, 
															cuenta.getDisponible(), 
															tasacionCuentas.get(Cuenta_Destino - 1));
		double[] cuota = vistaPrevia.retornoCuotaMensual(vistaPrevia.getDisponible());
		String cuotaMensual = Corriente.imprimirCuotaMensual(cuota);
		System.out.println(vistaPrevia);
		System.out.println("Pago de cuota: ");
		System.out.println(cuotaMensual);
		
		//Atributo de validacion de la entrada confirmacion Movimiento
		boolean validacion_vistaPrevia = true;
		//Atributo auxiliar para almacenar la confirmación del movimiento
		int confirmacionMovimiento = 0;
		while (validacion_vistaPrevia) {
			System.out.println("¿Desea confirmar la realización del movimiento?"
					+ "\n1. Sí"
					+ "\n2. No");
			confirmacionMovimiento = Integer.parseInt(sc.nextLine());
			if (confirmacionMovimiento == 1 || confirmacionMovimiento == 2) {
				validacion_vistaPrevia = false;
			} else {
				System.out.println("Entrada no válida, intente de nuevo");
			}
		}
		if (confirmacionMovimiento == 1) {
			//Efectuación del movimiento...
			
			return true;
		}
		else{
			System.out.println("Movimiento cancelado.");
			return false;
		}
	}
	
	// FUNCIONALIDAD COMPRA DE CARTERA
	static void compraCartera(Usuario usuario) {
		//Arreglo que almacena las cuentas con deuda alguna 
		ArrayList<Corriente> cuentasEnDeuda = usuario.retornarDeudas();
		//Comprobación de existencia de Deudas por parte del Usuario
		if (cuentasEnDeuda.size() == 0) {
			System.out.println("El usuario " + usuario.getNombre() + " no tiene préstamos asociados, no es posible realizar la funcionalidad.");
			return;
		}
		
		//Arreglo que almacena las cuentas asociadas a un usuario
		ArrayList<Cuenta> cuentasAux = usuario.getCuentasAsociadas();
		
		//Atributo auxiliar que almacenará el índice de la cuenta escogida por el usuario
		int Cuenta_Compra = 0;
		//Booleano usado para repetir el proceso de seleccion de cuenta
		boolean seleccion_Cuenta = true;
		
		while (seleccion_Cuenta) {
			System.out.println("Cuentas a nombre de " + usuario.getNombre() + " con préstamos asociados: ");
		
			//Impresión Cuentas con Préstamo Asociado
			int i = 1;
			for (Cuenta cuentas: cuentasEnDeuda) {
				System.out.println(i + ". " + cuentas.getNombre());
				i++;
			}
			
			//Atributo para validación entrada Cuenta_Compra
			boolean validacion_Cuenta_Compra = true;
			while (validacion_Cuenta_Compra) {
				System.out.println("Por favor, seleccione la cuenta a la cual quiere aplicar la compra de cartera: ");
				Cuenta_Compra = Integer.parseInt(sc.nextLine());
				
				if (Cuenta_Compra >= 1 && Cuenta_Compra < i) {
					validacion_Cuenta_Compra = false;
				}
				else {
					System.out.println("Entrada no válida, intente de nuevo.");
				}
			}
		
			//Retornar información de la Cuenta
			System.out.println("Información de la cuenta: ");
			System.out.println(cuentasEnDeuda.get(Cuenta_Compra - 1));
			
			System.out.println("");
			System.out.println("Confirme por favor si esta es la cuenta a la cual desea aplicar este mecanismo financiero (y/n): ");
			String ConfirmacionI = sc.nextLine();
			
			
			if (ConfirmacionI.equals("y") || ConfirmacionI.equals("Y")) {
				seleccion_Cuenta = false;
			}
			else if (ConfirmacionI.equals("n") || ConfirmacionI.equals("N")) {
				boolean validacion_ConfirmacionII = true;
				while (validacion_ConfirmacionII) {
					System.out.println("¿Qué desea hacer?"
							+ "\n1. Reescoger cuenta."
							+ "\n2. Salir de la funcionalidad.");
					int ConfirmacionII = Integer.parseInt(sc.nextLine());
					if (ConfirmacionII == 1) {
						validacion_ConfirmacionII = false;
					}
					else if(ConfirmacionII == 2) {
						return;
					}
					else {
						System.out.println("Entrada no válida, intente de nuevo.");
					}
				}
				
			}else {
				System.out.println("Entrada no válida");
			}
			
		}
		
		cuentasAux.remove(cuentasEnDeuda.get(Cuenta_Compra - 1));
		
		//Arreglo que almacena las cuentas capaces de recibir la deuda
		ArrayList<Corriente> cuentasCapacesDeuda = usuario.Capacidad_Endeudamiento(cuentasAux, cuentasEnDeuda.get(Cuenta_Compra - 1));
		//Arreglo que almacena las tasas de intereses aplicables con orden del arreglo anterior
		ArrayList<Double> tasacionCuentas = Banco.verificarTasasdeInteres(usuario, cuentasCapacesDeuda);
		
		System.out.println("Las cuentas a su nombre que pueden recibir la deuda de la Cuenta escogida son: ");
		for (int i = 0; i <= cuentasCapacesDeuda.size(); i++) {
			System.out.println(i + 1 + ". " + cuentasCapacesDeuda.get(i)
					+ "\n Tasa de Interés: " + tasacionCuentas.get(i));
			System.out.println("");
		}
		
		//Atributo de validacion de la entrada Cuenta_Destino
		boolean validacion_Cuenta_Destino = true;
		//Atributo auxiliar que almacenará la cuenta destino de la deuda
		int Cuenta_Destino = 0;
		while (validacion_Cuenta_Destino) {
			System.out.println("Por favor escoga la cuenta destino de la deuda:");
			Cuenta_Destino = Integer.parseInt(sc.nextLine());
			if (Cuenta_Destino >= 1 && Cuenta_Destino <= cuentasCapacesDeuda.size()) {
				validacion_Cuenta_Destino = false;
			}
			else {
				System.out.println("Entrada no válida, intente de nuevo");
			}
		}
		
		//Atributo de validacion de la entrada Periodicidad
		boolean validacion_Periodicidad = true;
		//Atributo auxiliar para almacenar decision de periodicidad
		int Periodicidad = 0;
		while (validacion_Periodicidad) {
			System.out.println("¿Desea mantener la periodicidad del pago de la deuda?"
					+ "\n1. Sí"
					+ "\n2. No");
			Periodicidad = Integer.parseInt(sc.nextLine());
			if (Periodicidad == 1 || Periodicidad == 2) {
				validacion_Periodicidad = false;
			} else {
				System.out.println("Entrada no válida, intente de nuevo");
			}
		}
		
		if (Periodicidad == 1) {
			System.out.println("Perfecto, la deuda mantendrá un plazo de pago a " + cuentasCapacesDeuda.get(Cuenta_Destino - 1).getPlazo_Pago());
		}
		//Atributo de la periodicidad
		Cuotas eleccion_periodicidad = null;
		if (Periodicidad == 2) {
			//Atributo de validacion de la seleccion de periodicidad
			boolean validacion_Seleccion_Periodicidad = true;
			int seleccion_periodicidad = 0;
			
			while (validacion_Seleccion_Periodicidad) {
				System.out.println("Por favor seleccione la nueva periodicidad de la Deuda: "
						+ "\n1. 1 Cuota"
						+ "\n2. 6 Cuotas"
						+ "\n3. 12 Cuotas"
						+ "\n4. 18 Cuotas"
						+ "\n5. 24 Cuotas"
						+ "\n6. 36 Cuotas"
						+ "\n7. 48 Cuotas");
				seleccion_periodicidad = Integer.parseInt(sc.nextLine());
				if (seleccion_periodicidad < 1 || seleccion_periodicidad > 7) {
					System.out.println("Entrada no válida, intente de nuevo");
				}
				else {
					validacion_Seleccion_Periodicidad = false;
				}
			}
			switch(seleccion_periodicidad) {
				case 1:
					eleccion_periodicidad = Cuotas.C1;
					System.out.println("Deuda establecida a: " + Cuotas.C1.getCantidad_Cuotas());
					break;
				case 2:
					eleccion_periodicidad = Cuotas.C6;
					System.out.println("Deuda establecida a: " + Cuotas.C6.getCantidad_Cuotas());
					break;
				case 3:
					eleccion_periodicidad = Cuotas.C12;
					System.out.println("Deuda establecida a: " + Cuotas.C12.getCantidad_Cuotas());
					break;
				case 4:
					eleccion_periodicidad = Cuotas.C18;
					System.out.println("Deuda establecida a: " + Cuotas.C18.getCantidad_Cuotas());
					break;
				case 5:
					eleccion_periodicidad = Cuotas.C24;
					System.out.println("Deuda establecida a: " + Cuotas.C24.getCantidad_Cuotas());
					break;
				case 6:
					eleccion_periodicidad = Cuotas.C36;
					System.out.println("Deuda establecida a: " + Cuotas.C36.getCantidad_Cuotas());
					break;
				case 7:
					eleccion_periodicidad = Cuotas.C48;
					System.out.println("Deuda establecida a: " + Cuotas.C48.getCantidad_Cuotas());
					break;
			}
		}
		
		//Vista Previa de los resultados del cambio
		System.out.println("Vista previa de como quedaría la cuenta escogida para recibir la deuda: ");
		Corriente vistaPrevia = Corriente.vistaPreviaMovimiento(cuentasCapacesDeuda.get(Cuenta_Destino - 1), eleccion_periodicidad, 
															cuentasEnDeuda.get(Cuenta_Compra - 1).getDisponible(), 
															tasacionCuentas.get(Cuenta_Destino - 1));
		double[] cuota = vistaPrevia.retornoCuotaMensual(vistaPrevia.getDisponible());
		String cuotaMensual = Corriente.imprimirCuotaMensual(cuota);
		System.out.println(vistaPrevia);
		System.out.println("Pago de cuota: ");
		System.out.println(cuotaMensual);
		
		//Atributo de validacion de la entrada confirmacion Movimiento
		boolean validacion_vistaPrevia = true;
		//Atributo auxiliar para almacenar la confirmación del movimiento
		int confirmacionMovimiento = 0;
		while (validacion_vistaPrevia) {
			System.out.println("¿Desea confirmar la realización del movimiento?"
					+ "\n1. Sí"
					+ "\n2. No");
			confirmacionMovimiento = Integer.parseInt(sc.nextLine());
			if (confirmacionMovimiento == 1 || confirmacionMovimiento == 2) {
				validacion_vistaPrevia = false;
			} else {
				System.out.println("Entrada no válida, intente de nuevo");
			}
		}
		if (confirmacionMovimiento == 1) {
			//Efectuación del movimiento...
			
			return;
		}
		if (confirmacionMovimiento == 2) {
			System.out.println("Movimiento cancelado.");
			return;
		}
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
			System.out.println("No se encuentra un usuario con estos datos. Inténtalo de nuevo.");
			seguir = 0;
			Main.bienvenidaApp();
		}
	}
	
	// INVERTIR SALDO DE CUENTA EN EL MAIN - FUNCIONALIDAD DE SUSCRIPCIONES DE USUARIOS
	static void invertirSaldoUsuario(Usuario user) {
		if(user.getCuentasAhorrosAsociadas().size() == 0) {
			System.out.println("Primero debes asociar cuentas de ahorro. Volviendo al menú anterior");
			seccion = 1;
		}else {
			System.out.println("");
			Main.verCuentasAhorroAsociadas();
			System.out.print("Seleccione el número de cuenta de ahorro asociada al usuario para realizar la inversión de saldo: ");
			int opcion_cuenta = Integer.parseInt(sc.nextLine());
			while(true) {
				if(opcion_cuenta < 1 && opcion_cuenta > user.getCuentasAhorrosAsociadas().size()) {
					System.out.print("Debes seleccionar una cuenta válida. Inténtalo de nuevo:");
					opcion_cuenta = Integer.parseInt(sc.nextLine());
				}else if(user.getCuentasAhorrosAsociadas().get(opcion_cuenta - 1).getSaldo() <= 0){
					Ahorros c = user.getCuentasAhorrosAsociadas().get(opcion_cuenta - 1);
					System.out.println("Para invertir saldo debemos comprobar que el saldo de la cuenta sea diferente de cero." + " El saldo para la cuenta " + c.getNombre() + " es de " + c.getSaldo());
					System.out.println("Volviendo al menú anterior.");
					break;	
				}else {
					System.out.println("");
					Ahorros c = user.getCuentasAhorrosAsociadas().get(opcion_cuenta - 1);
					Object inversion = c.invertirSaldo();
					if(inversion instanceof Movimientos) {
						user.getMovimientosAsociados().add((Movimientos) inversion);
						System.out.println("La inversión de saldo ha sido exitosa: ");
						System.out.println(inversion);
						System.out.println("");
						System.out.println(user.verificarContadorMovimientos());
						break;
					}else {
						System.out.println(inversion);
						break;
					}
				}
			}		
		}
	}
	
	// CONSIGNAR COMO USUARIO EN SALDO DE CUENTA EN EL MAIN
	static void consignarSaldoCuenta(Usuario user) {
		if(user.getCuentasAhorrosAsociadas().size() == 0) {
			System.out.println("Primero debes asociar cuentas de ahorro. Volviendo al menú anterior");
			seccion = 1;
		}else {
			System.out.println("");
			Main.verCuentasAhorroAsociadas();
			System.out.print("Seleccione el número de cuenta de ahorro asociada al usuario para realizar la consignación de saldo: ");
			int opcion_cuenta = Integer.parseInt(sc.nextLine());
			while(true) {
				if(opcion_cuenta < 1 && opcion_cuenta > user.getCuentasAhorrosAsociadas().size()) {
					System.out.print("Debes seleccionar una cuenta válida. Inténtalo de nuevo:");
					opcion_cuenta = Integer.parseInt(sc.nextLine());
				}else {
					System.out.println("");
					Ahorros c = user.getCuentasAhorrosAsociadas().get(opcion_cuenta - 1);
					System.out.print("Ingrese el monto de su consignación de saldo(En formato double): ");
					double saldo_consignar = Double.parseDouble(sc.nextLine()); 
					Object saldo_movimiento = Movimientos.crearMovimiento(c, saldo_consignar, Categoria.OTROS, new Date());
					
					if(saldo_movimiento instanceof Movimientos) {
						System.out.println("");
						user.getMovimientosAsociados().add((Movimientos) saldo_movimiento);
						System.out.println("La consignación de saldo ha sido exitosa: ");
						System.out.println(saldo_movimiento);
						break;
					}else {
						System.out.println(saldo_movimiento);
						break;
					}
				}
			}		
		}
	}	
	
	// VER CATEGORIAS EN EL MAIN
	static void verCategorias() {
		for(int i = 1; i < Categoria.getCategorias().size() + 1; i++) {
			System.out.println(i + ". " + Categoria.getCategorias().get(i - 1));
		}
	}
	
	// TRANSFERIR SALDO ENTRE CUENTAS POR USUARIO EN EL MAIN
	static void transferirSaldoCuentasUsuario(Usuario user) {
		if(user.getCuentasAhorrosAsociadas().size() < 2) {
			System.out.println("Debes asociar más de una cuenta de ahorros. Volviendo al menú anterior");
			seccion = 1;
		}else {
			System.out.println("");
			Main.verCuentasAhorroAsociadas();
			System.out.print("Seleccione el número de cuenta origen asociada al usuario desde donde deseas transferir saldo: ");
			int opcion_cuenta_origen = Integer.parseInt(sc.nextLine());
			while(true) {
				if(opcion_cuenta_origen < 1 || opcion_cuenta_origen > user.getCuentasAhorrosAsociadas().size()) {
					System.out.print("Debes seleccionar una cuenta válida. Inténtalo de nuevo: ");
					opcion_cuenta_origen = Integer.parseInt(sc.nextLine());	
				}else if(user.getCuentasAhorrosAsociadas().get(opcion_cuenta_origen - 1).getSaldo() == 0) {
					System.out.println("La cuenta seleccionada tiene saldo cero");
					System.out.print("¿Desea volver al menú anterior? (Y/N): ");
					String confirmacion = sc.nextLine();
					if(confirmacion.equals("Y") || confirmacion.equals("y")) {
						System.out.println("Volviendo al menú anterior");
						seccion = 1;
						break;
					}else {
						System.out.print("Inténtelo de nuevo: ");
						opcion_cuenta_origen = Integer.parseInt(sc.nextLine());
					}
				}else {
					System.out.println("");
					Ahorros c_origen = user.getCuentasAhorrosAsociadas().get(opcion_cuenta_origen - 1);
					Main.verCuentasAhorroTotales();
					System.out.print("Seleccione el número de cuenta destino donde deseas transferir saldo: ");
					int opcion_cuenta_destino = Integer.parseInt(sc.nextLine());
					Ahorros c_destino = null;
					while(true) {
						if(opcion_cuenta_destino < 1 || opcion_cuenta_destino > user.getCuentasAhorrosAsociadas().size()) {
							System.out.print("Inténtelo de nuevo. Seleccione el número de cuenta destino donde deseas transferir saldo: ");
							opcion_cuenta_destino = Integer.parseInt(sc.nextLine());
						}else {
							c_destino = Ahorros.getCuentasAhorroTotales().get(opcion_cuenta_destino - 1);
							break;
							}
						}
					if(c_destino == null) {
						
					}else {
						System.out.println("");
						System.out.print("Inserte el monto de la transferencia(En formato double): ");
						double monto_transferencia = Double.parseDouble(sc.nextLine());
						
						Main.verCategorias();
						System.out.println("");
						System.out.print("Seleccione el número de categoría para la transferencia: ");
						int categoria_transferencia_op = Integer.parseInt(sc.nextLine());
						Categoria categoria_transferencia = Categoria.getCategorias().get(categoria_transferencia_op - 1);
						
						Object modificar_saldo = Movimientos.modificarSaldo(c_origen, c_destino, monto_transferencia, user, categoria_transferencia);
						if(modificar_saldo instanceof Movimientos) {
							System.out.println((Movimientos) modificar_saldo);
							System.out.println(user.verificarContadorMovimientos());
							break;
						}else {
							System.out.println(modificar_saldo);
							break;	
						}	
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
		if(contrasena_admin.equals("admin")) {
			for(Usuario u : Usuario.getUsuariosTotales()) {
				if(u.getNombre() == "admin") {
					user = u;
				}		
			}
		}else {
			System.out.print("Inserta la contraseña de administrador (Es admin): ");
			contrasena_admin = sc.nextLine();
			while(!contrasena_admin.equals("admin")) {
				System.out.print("Contraseña errada. Inténtelo de nuevo: ");
				contrasena_admin = sc.nextLine();	
			}
			System.out.println("");
			System.out.print("Ingresando al sistema como administrador...");
			System.out.print("");
			user = new Usuario("admin", "admin@admin.com", "admin", Suscripcion.DIAMANTE);
		}
		
		while(contrasena_admin.equals("admin")) {
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
						new Usuario("Pepe Morales", "PepeMorales@mail.com", "12345", Suscripcion.DIAMANTE);
						System.out.println("El usuario por defecto fue creado con éxito, éstas son las credenciales de ingreso: ");
						System.out.println("Nombre: " + user.getNombre());
						System.out.println("Contraseña: " + user.getContrasena());
						System.out.println("");
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
							System.out.println("");
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
						System.out.println("");
						break;
					}else if(confirmacion.equals("N") || confirmacion.equals("n")){
						Main.crearEstado();
						break;
					}else {
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

			System.out.println("Cuál es el tipo que quiere seleccionar para su cuenta? La lista de Tipos disponibles son: "
					+ "\n1. Cuenta de Ahorros"
					+ "\n2. Cuenta Corriente");
			
			int tipo_op = Integer.parseInt(sc.nextLine());

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
			System.out.print("Inserte el nombre de la cuenta: ");
			String nombre_cuenta = sc.nextLine();
			
			if (tipo_op == 1) {
				System.out.println(user.asociarCuenta(new Ahorros(banco_cuenta, clave_cuenta, divisas_cuenta, nombre_cuenta)));
				System.out.println("Cuenta creada con éxito");
			}
			if (tipo_op == 2) {
				System.out.println(user.asociarCuenta(new Corriente(banco_cuenta, clave_cuenta, divisas_cuenta, nombre_cuenta)));
				System.out.println("Cuenta creada con éxito");
			}
			
		}
	}
	
	// ELIMINAR CUENTA, SE REALIZA COMPROBACIÓN ENTRE CORRIENTE Y AHORROS
	static void eliminarCuentaComprobacion(Cuenta cuenta, Usuario user) {
		if(cuenta instanceof Ahorros) {
			if (((Ahorros) cuenta).getSaldo() != 0.0d) {
				System.out.println("Por favor, elija el destino del saldo restante en la cuenta:");
				System.out.println("Recuerde que para el proceso debe ingresar sólo los numerales de las opciones que desee escoger.");
				System.out.println("1. Cuenta externa");
				System.out.println("2. Cuenta propia");
				int decision_saldo = Integer.parseInt(sc.nextLine());
				switch(decision_saldo) {
					case 1:
						System.out.println("Ingrese los datos de la cuenta a la cual desea transferir su saldo. Recuerde que debe ser una cuenta de ahorros.");
						System.out.print("Nombre de la cuenta destino: ");
						String destino = sc.nextLine();
						for(Cuenta dest : Cuenta.getCuentasTotales()) {
							if(destino == dest.getNombre() && dest instanceof Ahorros) {
								System.out.println(Movimientos.crearMovimiento((Ahorros) cuenta, (Ahorros) dest, ((Ahorros) cuenta).getSaldo(), Categoria.OTROS, new Date()));
								break;
							}else {
								System.out.print("Inténtelo de nuevo. Recuerde que debe escoger una cuenta de ahorros: ");
								destino = sc.nextLine();
									
								}
							}
					case 2:
						System.out.println("A cual de sus cuentas desea transferir su saldo:");
						ArrayList<Cuenta> cuentas = cuenta.getTitular().getCuentasAsociadas();
						for (int i = 1; i == cuentas.size() + 1; i++) {
							System.out.println(i + ". " + cuentas.get(i - 1).getNombre());
						}
						int decision_cuenta = Integer.parseInt(sc.nextLine());
						Movimientos.crearMovimiento((Ahorros) cuenta, (Ahorros) cuentas.get(decision_cuenta - 1), ((Ahorros) cuenta).getSaldo(), Categoria.OTROS, new Date());
					default:
						System.out.println("Opción no válida. Inténtelo de nuevo");
						System.out.println("Por favor, elija el destino del saldo restante en la cuenta:");
						System.out.println("Recuerde que para el proceso debe ingresar sólo los numerales de las opciones que desee escoger.");
						System.out.println("1. Cuenta externa");
						System.out.println("2. Cuenta propia");
						decision_saldo = Integer.parseInt(sc.nextLine());
				}
		
			
			}else {
				Cuenta.getCuentasTotales().remove(cuenta);
				user.getCuentasAsociadas().remove(cuenta);
				cuenta = null;
			}
		}else {
			if(((Corriente) cuenta).getDisponible().compareTo(((Corriente) cuenta).getCupo()) != 0){
				System.out.print("Tienes deudas pendientes. ¿Deseas pagarlas? (Y/N): ");
				String confirmacion = sc.nextLine();
				if(confirmacion.equals("Y") || confirmacion.equals("y")) {
					boolean validacion = Main.compraCartera(((Corriente) cuenta));
					if (validacion) {
						Cuenta.getCuentasTotales().remove(cuenta);
						user.getCuentasAsociadas().remove(cuenta);
						cuenta = null;
					}
					else {
						System.out.println("Debes pagar las deudas para eliminar la cuenta.");
					}
						
				}else {
					System.out.println("Debes pagar las deudas para eliminar la cuenta.");
				}
				
			}else {
				Cuenta.getCuentasTotales().remove(cuenta);
				user.getCuentasAsociadas().remove(cuenta);
				cuenta = null;	
			}
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
					continue;
				}else {
					for(int i = 0; i < user.getCuentasAsociadas().size(); i++) {
						if(user.getCuentasAsociadas().get(cuentaOp - 1) == user.getCuentasAsociadas().get(i)) {
							Main.eliminarCuentaComprobacion(user.getCuentasAsociadas().get(i), user);
							System.gc();
						}	
					} break;
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
	
	// VER CUENTAS DE AHORRO DEL USUARIO EN EL MAIN
	static void verCuentasAhorroAsociadas() {
		//SE VERIFICA QUE EXISTAN CUENTAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CREADAS POR EL USUARIO
		if(user.getCuentasAsociadas().size() > 0) {
			System.out.println("La lista de Cuentas de ahorro creadas por el Usuario " + user.getNombre() + " son: ");
			int i = 1;
			for(Cuenta cuenta : user.getCuentasAsociadas()) {
				if(cuenta instanceof Ahorros) {
					System.out.println(i + ". " + cuenta.getNombre());
					i++;
				}
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
	
	// VER CUENTAS DE AHORRO Y CORRIENTES DEL USUARIO EN EL MAIN
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
	
	// VER CUENTAS CORRIENTES TOTALES EN EL MAIN
	static void verCuentasCorrientesTotales() {
		//SE VERIFICA QUE EXISTAN CUENTAS CORRIENTES CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CORRIENTES CREADAS
		if(Corriente.getCuentasCorrienteTotales().size() > 0) {
			System.out.println("La lista de Cuentas Corrientes totales en el sistema son: ");
			for(int i = 1; i < Corriente.getCuentasCorrienteTotales().size() + 1; i++) {
				System.out.println(i + ". " + Corriente.getCuentasCorrienteTotales().get(i - 1).getNombre());
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
	
	// VER CUENTAS DE AHORRO TOTALES EN EL MAIN
	static void verCuentasAhorroTotales() {
		//SE VERIFICA QUE EXISTAN CUENTAS DE AHORRO CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS DE AHORRO CREADAS
		if(Ahorros.getCuentasAhorroTotales().size() > 0) {
			System.out.println("La lista de Cuentas de Ahorro totales en el sistema son: ");
			for(int i = 1; i < Ahorros.getCuentasAhorroTotales().size() + 1; i++) {
				System.out.println(i + ". " + Ahorros.getCuentasAhorroTotales().get(i - 1).getNombre());
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
	
	// VER CUENTAS TOTALES EN EL MAIN
	static void verCuentasTotales() {
		//SE VERIFICA QUE EXISTAN CUENTAS CREADAS, SI ESE ES EL CASO, SE IMPRIME EL NOMBRE DE LAS CUENTAS CREADAS
		if(Cuenta.getCuentasTotales().size() > 0) {
			System.out.println("La lista de Cuentas totales en el sistema son: ");
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
	
	// VER ESTAOS TOTALES EN EL MAIN
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
	
	// GUARDAR OBJETOS INDIVIDUALES EN EL MAIN
	static void guardarObjetosIndividuales() throws ParseException {
		//Guardar objetos individuales
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
	
	// GUARDAR OBJETOS EN EL MAIN
	static void guardarObjetos() throws ParseException{
		System.out.print("¿Desea guardar el estado actual del sistema? (Y/N): ");
		String confirmacion = sc.nextLine();
		while(true){
			if(confirmacion.equals("Y") || confirmacion.equals("y")) {
				System.out.println(Serializador.serializar(Usuario.getUsuariosTotales(), "Usuario"));
				System.out.println(Serializador.serializar(Banco.getBancosTotales(), "Bancos"));
				System.out.println(Serializador.serializar(Estado.getEstadosTotales(), "Estados"));
				System.out.println(Serializador.serializar(Cuenta.getCuentasTotales(), "Cuentas"));
				System.out.println(Serializador.serializar(Movimientos.getMovimientosTotales(), "Movimientos"));
				System.out.println(Serializador.serializar(Metas.getMetasTotales(), "Metas"));
			}
			else if(confirmacion.equals("N") || confirmacion.equals("n")) {
				break;
			}
			else {
				System.out.println("Opción no válida");
				System.out.println("NOTA: Solo se recibe como respuesta Y o N");
				System.out.print("¿Desea guardar el estado actual del sistema? (Y/N): ");
				confirmacion = sc.nextLine();
			}
		}
	}
	
	static void guardadoAutomatico(){
		Serializador.serializar(Usuario.getUsuariosTotales(), "Usuario");
		Serializador.serializar(Banco.getBancosTotales(), "Bancos");
		Serializador.serializar(Estado.getEstadosTotales(), "Estados");
		Serializador.serializar(Cuenta.getCuentasTotales(), "Cuentas");
		Serializador.serializar(Movimientos.getMovimientosTotales(), "Movimientos");
		Serializador.serializar(Metas.getMetasTotales(), "Metas");
	}
	
	private void formWindowClosing(java.awt.event.WindowEvent evt) {
		if (!existencia) {
			guardadoAutomatico();
		}
	}
	
	// CARGAR OBJETOS EN EL MAIN	
	static boolean existencia = false;	

	static void cargarObjetos() throws ParseException{
			System.out.println("Bienvenido a la Base de Datos...");
			System.out.print("¿Desea cargar el estado previo del sistema? (Y/N): ");
			String confirmacion = sc.nextLine();

			while(true){
				if(confirmacion.equals("Y") || confirmacion.equals("y")) {
					File f = new File("");
					File fUsuario =new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Usuario.nombreD + "_lista" + ".dat");
					if (fUsuario.exists()) {
						ArrayList<Usuario> usuariosDeserializados = (ArrayList<Usuario>) Deserializador.deserializar_listas("Usuarios");
						System.out.println("Una lista con " + usuariosDeserializados.size() + " usuarios ha sido cargada con éxito en el sistema.");
						existencia = true;
					}
					File fEstado =new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Estado.nombreD + "_lista" + ".dat");
					if (fEstado.exists()) {
						ArrayList<Estado> estadosDeserializados = (ArrayList<Estado>) Deserializador.deserializar_listas("Estados");
						System.out.println("Una lista con " + estadosDeserializados.size() + " estados ha sido cargada con éxito en el sistema.");
						existencia = true;
					}
					File fBanco = new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Banco.nombreD + "_lista" + ".dat");
					if (fBanco.exists()) {
						ArrayList<Banco> bancosDeserializados = (ArrayList<Banco>) Deserializador.deserializar_listas("Bancos");
						System.out.println("Una lista con " + bancosDeserializados.size() + " bancos ha sido cargada con éxito en el sistema.");
						existencia = true;
					}
					File fMovimientos = new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Movimientos.nombreD + "_lista" + ".dat");
					if (fMovimientos.exists()) {
						ArrayList<Movimientos> movimientosDeserializados = (ArrayList<Movimientos>) Deserializador.deserializar_listas("Movimientos");
						System.out.println("Una lista con " + movimientosDeserializados.size() + " movimientos ha sido cargada con éxito en el sistema.");
						existencia = true;
					}
					File fMetas = new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Metas.nombreD + "_lista" + ".dat");;
					if (fMetas.exists()) {
						ArrayList<Metas> metasDeserializados = (ArrayList<Metas>) Deserializador.deserializar_listas("Metas");
						System.out.println("Una lista con " + metasDeserializados.size() + " metas ha sido cargada con éxito en el sistema.");
						existencia = true;
					}
					File fCuenta = new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Cuenta.nombreD + "_lista" + ".dat");
					if (fCuenta.exists()) {
						ArrayList<Cuenta> cuentasDeserializados = (ArrayList<Cuenta>) Deserializador.deserializar_listas("Cuentas");
						System.out.println("Una lista con " + cuentasDeserializados.size() + " cuentas ha sido cargada con éxito en el sistema.");
						existencia = true;
					}
					if (!existencia) {
						System.out.println("No existe un estado previo del sistema guardado");
					}
					
					
				}
				else if(confirmacion.equals("N") || confirmacion.equals("n")) {
					break;
				}
				else {
					System.out.println("Opción no válida");
					System.out.println("NOTA: Solo se recibe como respuesta Y o N");
					System.out.print("¿Desea cargar el estado previo del sistema? (Y/N): ");
					confirmacion = sc.nextLine();
				}
			}
	}
	
	// CARGAR OBJETOS INDIVIDUALES EN EL MAIN
	static void cargarObjetosIndividuales() throws ParseException {
		//Cargar objetos individuales
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
	
	// ASOCIAR CUENTA A USUARIO EN EL MAIN
	static void asociarCuentaUsuario(Cuenta cuenta) {
		System.out.println(user.asociarCuenta(cuenta));
	}
	
	// INTERFAZ DE BIENVENIDA EN EL MAIN - MÉTODO DE INICIO DE PROGRAMA
	static void bienvenidaApp() throws ParseException {
		while(interfaz == 1) {
			/* LA VARIABLE INTERFAZ SE USA PARA PODER TERMINAR EL PROGRAMA. POR EJEMPLO CUANDO VOY A SALIR DEL PROGRAMA LE ASIGNO EL VALOR DE 0 PARA QUE TERMINE. 
			* ESTO MISMO SE USA DE DIFERENTES MANERAS PARA VARIAS PARTES DE LA INTERFAZ DEL USUARIO. */
				
			/* La variable sesioniniciada tiene una función análoga a la de seguir, en este caso será útil para volver a pedir los datos del usuario. */
				
			// INTERFAZ DE BIENVENIDA
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
					System.out.println("");

				} else if(opcionUsuario == 3){
					Main.accesoAdministrativo();
					System.out.println("");
	
				} else if(opcionUsuario == 4){
					Main.guardarObjetos();
					System.out.println("");
	
				} else if(opcionUsuario == 5){
					Main.cargarObjetos();
					System.out.println("");

				} else if(opcionUsuario == 6){
					Main.guardarObjetos();
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
							+ "\n2. Eliminar una cuenta"
							+ "\n3. Ver mis cuentas"
							+ "\n4. Salir al menú principal");
						
					opcion = Integer.parseInt(sc.nextLine());
					
					// Crear una cuenta
					if(opcion == 1) {
						Main.crearCuenta();
					}
					// Asociar una cuenta
//					else if(opcion == 2) {
//						if(Cuenta.getCuentasTotales().size() == 0) {
//							System.out.println("Primero debes crear cuentas. Volviendo al menú anterior");
//							seccion = 1;
//						} else {
//							System.out.println("");
//							Main.verCuentasTotales();
//							System.out.print("Seleccione el número de cuenta para asociarla al usuario: ");
//							int opcion_cuenta = Integer.parseInt(sc.nextLine());
//							while(true) {
//								if(opcion_cuenta < 1 || opcion_cuenta > Cuenta.getCuentasTotales().size()) {
//									System.out.print("Debes seleccionar un banco válido. Inténtalo de nuevo:");
//									opcion_cuenta = Integer.parseInt(sc.nextLine());
//								}else {
//									System.out.println("");
//									Cuenta cuenta = Cuenta.getCuentasTotales().get(opcion_cuenta - 1);
//									Main.asociarCuentaUsuario(cuenta);
//									break;
//								}
//							} 
//						}	
//					}
					// Eliminar una cuenta
					else if(opcion == 2) {
						Main.eliminarCuenta();
					}
					// Ver mis cuentas
					else if(opcion == 3) {
						Main.verCuentasAsociadas();
					}
					// Salir al menú principal
					else if (opcion == 4) {
						seccion = 0;
					}
					//Comprobar que la opción seleccionada pueda ejecutarse
					else if (opcion < 1 || opcion > 4) {
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
							+ "\n2. Invertir saldo de cuenta"
							+ "\n3. Consignar saldo a mi cuenta"
							+ "\n4. Transferir saldo entre cuentas"
							+ "\n5. Salir al menú principal");
		
					opcion = Integer.parseInt(sc.nextLine());
					System.out.println("");
						
					if(opcion == 1) {
						Main.modificarSuscripcionUsuario(user);
							
					} else if(opcion == 2) {
						Main.invertirSaldoUsuario(user);
					}
					
					 else if(opcion == 3) {
						Main.consignarSaldoCuenta(user);
					} 
					
					 else if(opcion == 4) {
							Main.transferirSaldoCuentasUsuario(user);
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
					if (opcionMetas == 1) {
						Main.crearMeta();
					}
					// Eliminar una meta
					else if (opcionMetas == 2) {
						Main.eliminarMeta();
					}
					// Ver las metas
					else if (opcionMetas == 3) {
						Main.verMetas();
					}
					// Volver al menú anterior
					else if (opcionMetas == 4) {
						sesioniniciada = 1;
						seccion = 0;
						break;
					}
					//Comprobar que la opción seleccionada pueda ejecutarse
					else if (opcionMetas < 1 || opcionMetas > 4 ) {
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
					System.out.println("");
					sesioniniciada = 0;
				}
			}
		} sc.close();
	}
		
	//ATRIBUTOS DE CLASE PARA EL FUNCIONAMIENTO DE LA INTERFAZ
	static ArrayList<Object> listaObjetos = new ArrayList<Object>();
	static Usuario user = null;
	static int seguir = 1;
	static int opcionUsuario = 0;
	static int opcionMetas;
	static int sesioniniciada = 0;
	static int interfaz = 1;
	static int seccion = 0;
	static int opcion = 0;
	static String contrasena_admin = "";
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws ParseException{
		
		listaObjetos.add(Estado.class);
		listaObjetos.add(Cuenta.class);
		listaObjetos.add(Usuario.class);
		listaObjetos.add(Banco.class);
		listaObjetos.add(Movimientos.class);
		listaObjetos.add(Metas.class);
		
		Main.bienvenidaApp();

	}	
}