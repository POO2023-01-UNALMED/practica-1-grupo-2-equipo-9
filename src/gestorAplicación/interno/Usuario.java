package gestorAplicación.interno;

import java.io.Serializable;
import java.util.ArrayList;
import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Estado;
import gestorAplicación.externo.Tablas;

public class Usuario implements Serializable, Tablas {
	//Atributos
	//Atributos para serializar
	private static final long serialVersionUID = 3L;
	public static final String nombreD = "Usuarios";
	
	//Atributos de instancia
	private String nombre;
	private String correo;
	private String contrasena;
	private int id;
	private Suscripcion suscripcion;
	private static transient ArrayList<Usuario> usuariosTotales = new ArrayList<Usuario>();
	private ArrayList<Banco> bancosAsociados = new ArrayList<Banco>();
	
	//Funcionalidad de Suscripciones de Usuario
	private ArrayList<Cuenta> cuentasAsociadas = new ArrayList<Cuenta>();
	private int limiteCuentas;
	private int contadorMovimientos;

	
	//Funcionalidad Asesor inversiones
	private ArrayList<Movimientos> movimientosAsociados = new ArrayList<Movimientos>();
	private ArrayList<Corriente> CuentasCorrienteAsociadas = new ArrayList<Corriente>();
	private ArrayList<Ahorros> CuentasAhorrosAsociadas = new ArrayList<Ahorros>();
	private ArrayList<Metas> metasAsociadas = new ArrayList<Metas>();

	//Constructores
	public Usuario(String nombre, String correo, String contrasena, Suscripcion suscripcion) {
		Usuario.getUsuariosTotales().add(this);
		this.setSuscripcion(suscripcion);
		this.setLimiteCuentas(suscripcion.getLimiteCuentas());
		this.setNombre(nombre);
		this.setContrasena(contrasena);
		this.setCorreo(correo);
		this.setId(Usuario.getUsuariosTotales().size());
	}
	
	public Usuario(String nombre, String correo, String contrasena) {
		Usuario.getUsuariosTotales().add(this);
		this.setSuscripcion(Suscripcion.BRONCE);
		this.setLimiteCuentas(this.getSuscripcion().getLimiteCuentas());
		this.setNombre(nombre);
		this.setContrasena(contrasena);
		this.setCorreo(correo);
		this.setId(Usuario.getUsuariosTotales().size());
	}
	
	public Usuario() {
		this("Pepe Morales", "PepeMorales@mail.com", "12345", Suscripcion.DIAMANTE);
	}

	//Métodos de clase
	public static Object verificarCredenciales(String nombre, String contraseña) {
		for (Usuario usuario: usuariosTotales) {
			if (usuario.getNombre().equals(nombre) || usuario.getCorreo().equals(nombre)) {
				if (usuario.getContrasena().equals(contraseña)) {
					return usuario;
				}
			}
		}
		return null;
	}
	
	// Funcionalidad Asesor Inversiones
	public static int hallarUsuariogotaGota() {
		int contador = 0;
		for (int i = 0; i < Usuario.getUsuariosTotales().size(); i++) {
			if (Usuario.getUsuariosTotales().get(i).getNombre().equals("gotaGota")) {
				contador = i;
			} else {
				continue;
			}
		}
		return contador;
	}

	public static int hallarUsuarioImpuestosPortafolio() {
		int contador = 0;
		for (int i = 0; i < Usuario.getUsuariosTotales().size(); i++) {
			if (Usuario.getUsuariosTotales().get(i).getNombre().equals("Impuestos Portafolio")) {
				contador = i;
			} else {
				continue;
			}
		}
		return contador;
	}
	
	//Métodos de instancia
	//Funcionalidad de Suscripciones de Usuarios
	public String verificarContadorMovimientos() {
		this.setContadorMovimientos(this.getMovimientosAsociados().size());
		if(this.getContadorMovimientos() == 5) {
			switch(this.getSuscripcion()) {
				case DIAMANTE:
					this.setContadorMovimientos(0);
					return("Felicidades, has alcanzado el nivel máximo de suscripción");
				case ORO:
					this.setContadorMovimientos(0);
					this.setSuscripcion(Suscripcion.DIAMANTE);
					return("Felicidades, has sido promovido al nivel de DIAMANTE, estos son tus beneficios: " + "puedes asociar un máximo de " + Suscripcion.DIAMANTE.getLimiteCuentas() + " cuentas, la probabilidad de ganar en tu inversión es de " + Suscripcion.DIAMANTE.getProbabilidad_Inversion());
				case PLATA:
					this.setContadorMovimientos(0);
					this.setSuscripcion(Suscripcion.ORO);
					return("Felicidades, has sido promovido al nivel de ORO, estos son tus beneficios: " + "puedes asociar un máximo de " + Suscripcion.ORO.getLimiteCuentas() + " cuentas, la probabilidad de ganar en tu inversión es de " + Suscripcion.ORO.getProbabilidad_Inversion());
				case BRONCE:
					this.setContadorMovimientos(0);
					this.setSuscripcion(Suscripcion.PLATA);
					return("Felicidades, has sido promovido al nivel de PLATA, estos son tus beneficios: " + "puedes asociar un máximo de " + Suscripcion.PLATA.getLimiteCuentas() + " cuentas, la probabilidad de ganar en tu inversión es de " + Suscripcion.PLATA.getProbabilidad_Inversion());
				default:
					return("");
			
			}
		}else {
			return("Debes completar 5 movimientos para ser promovido de nivel, llevas " + this.getContadorMovimientos() + " movimiento(s)");
		}
	}
	
	public String asociarBanco(Banco banco) {
		if(Banco.getBancosTotales().contains(banco) && !bancosAsociados.contains(banco)) {
			this.getBancosAsociados().add(banco);
			return("El banco " + banco.getNombre() + " se ha asociado con éxito al usuario " + this.getNombre());
		}else {
			return("No se encuentra el banco ó debes verificar que el banco que quieres asociar no se haya asociado antes, esta es la lista de bancos asociados: " + this.mostrarBancosAsociados());
		}
	}
	
	public String asociarCuenta(Cuenta cuenta) {
		if(!cuentasAsociadas.contains(cuenta)) {
			cuenta.setTitular(this);
			this.getCuentasAsociadas().add(cuenta);
			if(cuenta instanceof Ahorros) {
				return(this.asociarCuentaAhorros((Ahorros) cuenta));
			}else {
				if(((Corriente) cuenta).getCupo() == 0.0) {
					Corriente.inicializarCupo((Corriente) cuenta);
				}
				return(this.asociarCuentaCorriente((Corriente) cuenta));
			}
		}else {
			return("Debes comprobar que la cuenta no haya sido asociada con anterioridad.");
		}
	}
	
	public String asociarMeta(Metas meta) {
		if(Metas.getMetasTotales().contains(meta)) {
			meta.setDueno(this);
			this.getMetasAsociadas().add(meta);
			return("La meta " + meta.getNombre() + " se ha asociado con éxito al usuario " + this.getNombre());
		}else {
			return("No se encuentra tu meta, debes verificar que la meta que quieres asociar exista" );
		}
	}
	
	public String asociarMovimiento(Movimientos movimiento) {
		if(Movimientos.getMovimientosTotales().contains(movimiento)) {
			movimiento.setOwner(this);
			this.getMovimientosAsociados().add(movimiento);
			if (movimiento.getDestino() == null) {
				return("El movimiento con origen " + movimiento.getOrigen().getNombre() + " ha sido asociada correctamente al usuario " + this.getNombre());
			}else {
				return("El movimiento con destino " + movimiento.getDestino().getNombre() + " ha sido asociada correctamente al usuario " + this.getNombre());
			}
		}else {
			return("No se encuentra el movimiento. Por favor asegurese de que el movimiento se haya realizado con éxito" );
		}
	}
	
	public String asociarCuentaCorriente(Corriente corriente) {
		if(Corriente.getCuentasCorrienteTotales().contains(corriente)) {
			this.getCuentasCorrienteAsociadas().add(corriente);
			return("La cuenta corriente " + corriente.getNombre() + " ha sido asociada correctamente al usuario " + this.getNombre());
		}else {
			return("Debes verificar que la cuenta no haya sido asociada antes");
		}
	}
	
	public String asociarCuentaAhorros(Ahorros ahorros) {
		if(Ahorros.getCuentasAhorroTotales().contains(ahorros)) {
			this.getCuentasAhorrosAsociadas().add(ahorros);
			return("La cuenta de ahorros " + ahorros.getNombre() + " ha sido asociada correctamente al usuario " + this.getNombre());
		}else {
			return("Debes verificar que la cuenta no haya sido asociada antes");
		}
	}
	
	public Object mostrarBancosAsociados() {
		ArrayList<Banco> bancos = this.getBancosAsociados();
		if(bancos.size() != 0) {
			return bancos;
		}else {
			return ("Primero debes asociar bancos");
		}
	}
	
	public Object mostrarCuentasAsociadas() {
		ArrayList<Cuenta> cuentas = this.getCuentasAsociadas();
		if(cuentas.size() != 0) {
			return cuentas;
		}else {
			return ("Primero debes asociar cuentas");
		}
	}
	
	//    Funcionalidad Prestamos
	public ArrayList<?> comprobarConfiabilidad(){
		//Deserializacion de las cuentas
		ArrayList<Ahorros> cuentasUsuario = this.getCuentasAhorrosAsociadas();
		ArrayList<String> cadena = new ArrayList<>();

//		Conseguimos la suscripciones y miramos las deudas
		Suscripcion suscripcion = getSuscripcion();
//		comprobamos y contamos las deudas que estan asociadas al usuario
		ArrayList<Deuda> deudasUsuario = (ArrayList<Deuda>) Deuda.conseguirDeudas(this);
//		returns
		if(deudasUsuario.size()<suscripcion.getMaxDeudas()){
			if(cuentasUsuario.size()!=0){
				return cuentasUsuario;
			}else{
				cadena.add("¡Error! Usted no tiene ninguna cuenta Ahorros creada");
			}
		}else{
			cadena.add("¡Error! La suscripción"+this.getSuscripcion().name()+"solo permite realizar un total de"+this.getSuscripcion().getMaxDeudas()+". Usted tiene"+this.getSuscripcion().getMaxDeudas()+"/"+this.getSuscripcion().getMaxDeudas()+"Deudas");
//			Agrega a cadena todas las cuentas del usuario para mostrarselas
			for(int i =0;i<deudasUsuario.size();i++){
				cadena.add(i+"-Deuda con id"+deudasUsuario.get(i).getId()+"efectuada por el banco"+deudasUsuario.get(i).getBanco()+" en la cuenta"+deudasUsuario.get(i).getCuenta()+"por una cantidad de"+deudasUsuario.get(i).getCantidad());
			}
		}
		return cadena;
	}

	//Funcionalidad Compra Cartera
	public ArrayList<Corriente> retornarDeudas(){
		ArrayList<Corriente> cuentasConDeuda = new ArrayList<Corriente>();
		for (Cuenta cuenta: cuentasAsociadas) {
			if(cuenta instanceof Corriente) {
				if (((Corriente) cuenta).getDisponible().compareTo(((Corriente) cuenta).getCupo()) != 0) {
					cuentasConDeuda.add((Corriente) cuenta);
				}
			}
		}
		return cuentasConDeuda;
	}
	
	//Funcionalidad Compra Cartera
	public ArrayList<Corriente> Capacidad_Endeudamiento(ArrayList<Cuenta> cuentas, Corriente cuenta_a_Aplicar) {
		double deuda = cuenta_a_Aplicar.getCupo() - cuenta_a_Aplicar.getDisponible();
		ArrayList<Corriente> cuentasCapacesDeuda = new ArrayList<Corriente>();
		for (Cuenta cuenta: cuentas) {
			if (cuenta instanceof Corriente) {
				if (((Corriente) cuenta).getDisponible() >= deuda) {
					cuentasCapacesDeuda.add((Corriente) cuenta);
				}
			}
		}
		return cuentasCapacesDeuda;
	}
	
	public void eliminarMetas(int n) {
		this.getMetasAsociadas().remove(n);
		Metas.getMetasTotales().remove(n);
	}
	
	public void impresionBancos(ArrayList<Banco> bancos) {
		ArrayList<String> cadena = Banco.propiedadesCuenta();
		
		Banco.limpiarPropiedades(cadena);
		
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %20s %10s %10s %15s %20s %20s %29s", 
				"#", cadena.get(3), cadena.get(0), cadena.get(1), cadena.get(2), cadena.get(4), cadena.get(5), cadena.get(7), cadena.get(8));
		System.out.println();
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Banco banco: bancos) {
			System.out.printf("%4d %8d %20s %10s %10s %15s %20s %20s %29s", 
					i, banco.getId(), banco.getNombre(), (banco.getComision() + " %"), banco.getDivisa(), banco.getEstadoAsociado().getNombre(),
					(banco.getCupo_base() + " (" + banco.getMultiplicador() + ")"), banco.getDesc_suscripcion(),
					(banco.getDesc_movimientos_porcentaje() + " (" + banco.getDesc_movimientos_cantidad() + ")"));
			System.out.println();
			i++;
		}
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------");

		return;
	}
	
	public static void limpiarPropiedades(ArrayList<String> arreglo) {
		arreglo.remove("serialVersionUID");
		arreglo.remove("nombreD");
		arreglo.remove("usuariosTotales");
		arreglo.remove("bancosAsociados");
		arreglo.remove("cuentasAsociadas");
		arreglo.remove("movimientosAsociados");
		arreglo.remove("CuentasCorrienteAsociadas");
		arreglo.remove("CuentasAhorrosAsociadas");
		arreglo.remove("metasAsociadas");
		arreglo.remove("$SWITCH_TABLE$gestorAplicación$interno$Suscripcion");
	}
	
	public void impresionEstados(ArrayList<Estado> estados) {
		ArrayList<String> cadena = Estado.propiedadesCuenta();
		
		Estado.limpiarPropiedades(cadena);
		
		System.out.println("-------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %10s %15s %29s %15s", 
				"#", cadena.get(1), cadena.get(0), cadena.get(3), cadena.get(2), cadena.get(4), cadena.get(5));
		System.out.println();
		System.out.println("-------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Estado estado: estados) {
			System.out.printf("%4d %8d %15s %10s %15s %29s %15s", 
					i, estado.getId(), estado.getNombre(), estado.getDivisa(), estado.getDivisa(), (estado.getTasa_impuestos() + " %"),
					(estado.getInteres_bancario_corriente() + " %"), (estado.getTasas_usura() + " %"));
			System.out.println();
			i++;
		}
		System.out.println("-------------------------------------------------------------------------------------------------------");

		return;
	}
	
	public void impresionCuentasCorriente(ArrayList<Corriente> cuentas){
		ArrayList<String> cadena = Corriente.propiedadesCuenta();
		
		Corriente.limpiarPropiedades(cadena);
		Cuenta.limpiarPropiedades(cadena);
		
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %25s %10s %15s %15s %10s %10s %20s %20s", 
				"#", cadena.get(4), cadena.get(3), cadena.get(0), cadena.get(1), cadena.get(6), cadena.get(7),
				cadena.get(8), cadena.get(9), cadena.get(10), cadena.get(5));
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Corriente cuenta: cuentas) {
			System.out.printf("%4d %8d %15s %25s %10d %15s %15s %10d %10s %20s %20s", 
					i, cuenta.getId(), cuenta.getNombre(), cuenta.getTitular().getNombre(), cuenta.getClave(), (cuenta.getCupo() + " " + cuenta.getDivisa()), (cuenta.getDisponible() + " " + cuenta.getDivisa()),
					cuenta.getPlazo_Pago().getCantidad_Cuotas(), (cuenta.getIntereses() + " %"), cuenta.getPrimerMensualidad(), cuenta.getBanco().getNombre());
			System.out.println();
			i++;
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		return;
	}
	
	public void impresionCuentasCorrienteInteres(ArrayList<Corriente> cuentas, ArrayList<Double> intereses){
		ArrayList<String> cadena = Corriente.propiedadesCuenta();
		
		Corriente.limpiarPropiedades(cadena);
		Cuenta.limpiarPropiedades(cadena);
		
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %25s %10s %15s %15s %10s %10s %20s %20s %15s", 
				"#", cadena.get(4), cadena.get(3), cadena.get(0), cadena.get(1), cadena.get(6), cadena.get(7),
				cadena.get(8), cadena.get(9), cadena.get(10), cadena.get(5), "Interés Nuevo");
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Corriente cuenta: cuentas) {
			System.out.printf("%4d %8d %15s %25s %10d %15s %15s %10d %10s %20s %20s %15s", 
					i, cuenta.getId(), cuenta.getNombre(), cuenta.getTitular().getNombre(), cuenta.getClave(), (cuenta.getCupo() + " " + cuenta.getDivisa()), (cuenta.getDisponible() + " " + cuenta.getDivisa()),
					cuenta.getPlazo_Pago().getCantidad_Cuotas(), (cuenta.getIntereses() + " %"), cuenta.getPrimerMensualidad(), cuenta.getBanco().getNombre(), (intereses.get(i - 1) + " %"));
			System.out.println();
			i++;
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		return;
	}
	
	public void impresionCuentasAhorros(ArrayList<Ahorros> cuentas) {
		ArrayList<String> cadena = Ahorros.propiedadesCuenta();
		
		Ahorros.limpiarPropiedades(cadena);
		Cuenta.limpiarPropiedades(cadena);
		
		System.out.println("---------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %25s %10s %15s %20s", 
				"#", cadena.get(4), cadena.get(3), cadena.get(0), cadena.get(1), cadena.get(6), cadena.get(5));
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Ahorros cuenta: cuentas) {
			System.out.printf("%4d %8d %15s %25s %10d %15s %20s", 
					i, cuenta.getId(), cuenta.getNombre(), cuenta.getTitular().getNombre(), cuenta.getClave(),
					(cuenta.getSaldo() + " " + cuenta.getDivisa()), cuenta.getBanco().getNombre());
			System.out.println();
			i++;
		}
		System.out.println("---------------------------------------------------------------------------------------------------------");

		return;
	}
	
	public void impresionCuentas(ArrayList<Cuenta> cuentas) {
		ArrayList<String> cadena = Cuenta.propiedadesCuenta();

		Cuenta.limpiarPropiedades(cadena);
		
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %25s %10s %20s", 
				"#", cadena.get(4), cadena.get(3), cadena.get(0), cadena.get(1), cadena.get(5));
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		int i = 1;
		for (Cuenta cuenta: cuentas) {
			System.out.printf("%4d %8d %15s %25s %10d %20s", 
					i, cuenta.getId(), cuenta.getNombre(), cuenta.getTitular().getNombre(), cuenta.getClave(), cuenta.getBanco().getNombre());
			System.out.println();
			i++;
		}
		System.out.println("-----------------------------------------------------------------------------------------");

		return;
	}
	
	public void impresionDeudas(ArrayList<Deuda> deudas) {
		ArrayList<String> cadena = Deuda.propiedadesCuenta();
		
		Deuda.limpiarPropiedades(cadena);
		Metas.limpiarPropiedades(cadena);
		
		System.out.println("----------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %15s %15s %15s %15s", 
				"#", cadena.get(3), cadena.get(0), cadena.get(4), cadena.get(1), cadena.get(6), cadena.get(5));
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------");
		int i = 1;
		for (Deuda deuda: deudas) {
			System.out.printf("%4d %8d %15s %15s %15s %15s %15s", 
					i, deuda.getId(), deuda.getNombre(), deuda.getDueno().getNombre(), deuda.getCantidad(), deuda.getBanco().getNombre(),
					(deuda.getCuenta().getId() + ": " + deuda.getCuenta().getNombre()));
			System.out.println();
			i++;
		}
		System.out.println("----------------------------------------------------------------------------------------------");

		return;
	}
	
	public void impresionMetas(ArrayList<Metas> metas) {
		ArrayList<String> cadena = Metas.propiedadesCuenta();
		
		Metas.limpiarPropiedades(cadena);
		
		System.out.println("--------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %20s %15s %12s %14s", 
				"#", cadena.get(3), cadena.get(0), cadena.get(4), cadena.get(1), cadena.get(2));
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------");
		int i = 1;
		for (Metas meta: metas) {
			if (meta.getFecha() == null) {
				System.out.printf("%4d %8d %20s %15s %12s %14s", 
					i, meta.getId(), meta.getNombre(), meta.getDueno().getNombre(), meta.getCantidad(), "/");
				System.out.println();
				i++;
			}
			else if(meta.getCantidad() == 0) {
				System.out.printf("%4d %8d %20s %15s %12s %14s", 
					i, meta.getId(), meta.getNombre(), meta.getDueno().getNombre(), "/", meta.getFechaNormal());
				System.out.println();
				i++;
			}
			else if(meta.getNombre() == null) {
				System.out.printf("%4d %8d %20s %15s %12s %14s", 
					i, meta.getId(), "/", meta.getDueno().getNombre(), meta.getCantidad(), meta.getFechaNormal());
				System.out.println();
				i++;
			}
			else {
				System.out.printf("%4d %8d %20s %15s %12s %14s", 
						i, meta.getId(), meta.getNombre(), meta.getDueno().getNombre(), meta.getCantidad(), meta.getFechaNormal());
				System.out.println();
				i++;
			}
			
		}
		System.out.println("--------------------------------------------------------------------------------");

		return;
	}
	
	public void impresionMovimientos(ArrayList<Movimientos> movimiento) {
		ArrayList<String> cadena = Movimientos.propiedadesMovimientos();
		
		Movimientos.limpiarPropiedades(cadena);
		
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %15s %15s %35s %20s %20s", 
				"#", cadena.get(0), cadena.get(1), cadena.get(2), cadena.get(3), cadena.get(5), cadena.get(4));
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Movimientos mov: movimiento) {
			if (mov.getOrigen() == null) {
				System.out.printf("%4d %8d %15f %15s %35s %20s %20s", 
					i, mov.getId(), mov.getCantidad(), mov.getCategoria(), mov.getFecha(),
					"/", (mov.getDestino().getId() + ": " + mov.getDestino().getNombre()));
			}
			else if(mov.getDestino() == null) {
				System.out.printf("%4d %8d %15s %15s %35s %20s %20s", 
					i, mov.getId(), mov.getCantidad(), mov.getCategoria(), mov.getFecha(),
					(mov.getOrigen().getId() + ": " + mov.getOrigen().getNombre()), "/");
			}
			else {
				System.out.printf("%4d %8d %15s %15s %35s %20s %20s", 
					i, mov.getId(), mov.getCantidad(), mov.getCategoria(), mov.getFecha(),
					(mov.getOrigen().getId() + ": " + mov.getOrigen().getNombre()),
					(mov.getDestino().getId() + ": " + mov.getDestino().getNombre()));
			}
			
			System.out.println();
			i++;
		}
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");

		return;
	}
	
	public void impresionUsuarios(ArrayList<Usuario> usuarios) {
		ArrayList<String> cadena = this.recibirPropiedadesObjeto();
		
		Usuario.limpiarPropiedades(cadena);

		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%4s %8s %22s %30s %15s %12s %15s %20s", 
				"#", cadena.get(3), cadena.get(0), cadena.get(1), cadena.get(2), cadena.get(4), cadena.get(5), cadena.get(6));
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (Usuario usuario: usuarios) {
			System.out.printf("%4s %8s %22s %30s %15s %12s %15d %20d", 
					i, usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getContrasena(), usuario.getSuscripcion(),
					usuario.getLimiteCuentas(), usuario.getContadorMovimientos());
			System.out.println();
			i++;
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");

		return;
		
	}
	
	@Override
	protected void finalize() { System.out.println("El usuario con id: " + this.getId() + " y nombre: " + this.getNombre() + " fue eliminado satisfactoriamente del sistema."); }
	
	@Override
	public boolean equals(Object o) {
		if(this.getId() == ((Usuario) o).getId()){
			return true;
		}else {
			return false;
		}	
	}
	
	public String toString() {
		return "Usuario: " + this.getNombre() +
				"\nCorreo: " + this.getCorreo() +
				"\n#: " + this.getId() +
				"\nCuentas Asociadas: " + this.getCuentasAsociadas() +
				"\nSuscripción: " + this.getSuscripcion();
	}
	
	//Métodos Get & Set
	public static ArrayList<Usuario> getUsuariosTotales() { return usuariosTotales; }
	public static void  setUsuariosTotales(ArrayList<Usuario> usuariosTotales) { Usuario.usuariosTotales = usuariosTotales; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getCorreo() { return correo; }
	public void setCorreo(String correo) { this.correo = correo; }
	public String getContrasena() { return contrasena; }
	public void setContrasena(String contrasena) { this.contrasena = contrasena; }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public Suscripcion getSuscripcion() { return suscripcion; }
	public void setSuscripcion(Suscripcion suscripcion) { this.suscripcion = suscripcion; }
	public ArrayList<Cuenta> getCuentasAsociadas() { return cuentasAsociadas; }
	public void setCuentasAsociadas(ArrayList<Cuenta> cuentasAsociadas) { this.cuentasAsociadas = cuentasAsociadas; }
	public int getLimiteCuentas() { return limiteCuentas; }
	public void setLimiteCuentas(int limiteCuentas) { this.limiteCuentas = limiteCuentas; }
	public ArrayList<Banco> getBancosAsociados() { return bancosAsociados; }
	public void setBancosAsociados(ArrayList<Banco> bancosAsociados) { this.bancosAsociados = bancosAsociados; }
	public int getContadorMovimientos() { return contadorMovimientos; }
	public void setContadorMovimientos(int contadorMovimientos) { this.contadorMovimientos = contadorMovimientos; }
	public ArrayList<Metas> getMetasAsociadas() {return metasAsociadas;}
	public void setMetasAsociadas(ArrayList<Metas> metasAsociadas) {this.metasAsociadas = metasAsociadas;}
	public ArrayList<Movimientos> getMovimientosAsociados() {return movimientosAsociados;}
	public void setMovimientosAsociados(ArrayList<Movimientos> movimientosAsociados) {this.movimientosAsociados = movimientosAsociados;}
	public ArrayList<Corriente> getCuentasCorrienteAsociadas() {return CuentasCorrienteAsociadas;}
	public void setCuentasCorrienteAsociadas(ArrayList<Corriente> cuentasCorrienteAsociadas) {CuentasCorrienteAsociadas = cuentasCorrienteAsociadas;}
	public ArrayList<Ahorros> getCuentasAhorrosAsociadas() {return CuentasAhorrosAsociadas;}
	public void setCuentasAhorrosAsociadas(ArrayList<Ahorros> cuentasAhorrosAsociadas) {CuentasAhorrosAsociadas = cuentasAhorrosAsociadas;}

}
