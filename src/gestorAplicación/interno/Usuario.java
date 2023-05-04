package gestorAplicación.interno;

import java.io.Serializable;
import java.util.ArrayList;
import baseDatos.Deserializador;
import gestorAplicación.externo.Banco;
import gestorAplicación.interno.Deuda;

public class Usuario implements Serializable {
	//Atributos
	//Funcionalidad de Suscripciones de Usuarios
	private static final long serialVersionUID = 3L;
	public static final String nombreD = "Usuarios";
	private ArrayList<Cuenta> cuentasAsociadas = new ArrayList<Cuenta>();
	private int limiteCuentas;
	private static transient ArrayList<Usuario> usuariosTotales = new ArrayList<Usuario>();
	private ArrayList<Banco> bancosAsociados = new ArrayList<Banco>();
	private int contadorMovimientos;
	private ArrayList<Usuario> usuariosTotalesAux = new ArrayList<Usuario>();
	private Suscripcion suscripcion;
	private String nombre;
	private String correo;
	private String contrasena;
	private int id;
	
	//Funcionalidad Asesor inversiones
	private ArrayList<Movimientos> movimientosAsociados = new ArrayList<Movimientos>();
	private ArrayList<Corriente> CuentasCorrienteAsociadas = new ArrayList<Corriente>();
	private ArrayList<Ahorros> CuentasAhorrosAsociadas = new ArrayList<Ahorros>();
	private ArrayList<Metas> metasAsociadas = new ArrayList<Metas>();

	
	//Constructor
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
	
	//Métodos de instancia
	//Funcionalidad de Suscripciones de Usuarios
	public String verificarContadorMovimientos() {
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
		if(!cuentasAsociadas.contains(cuenta) && this.getCuentasAsociadas().size() < this.getLimiteCuentas()) {
			cuenta.setTitular(this);
			this.getCuentasAsociadas().add(cuenta);
			if(cuenta instanceof Ahorros) {
				return(this.asociarCuentaAhorros((Ahorros) cuenta));
			}else {
				return(this.asociarCuentaCorriente((Corriente) cuenta));
			}
		}else {
			return("Debes verificar que no hayas alcanzado el máximo de cuentas que puede asociar el usuario. El máximo de cuentas que puede asociar el usuario " + this.getNombre()  + " es " + this.getLimiteCuentas() + " y la cantidad de cuentas asociadas es " + this.getCuentasAsociadas().size());
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
			return("El movimiento con destino " + movimiento.getDestino().getNombre() + " ha sido asociada correctamente al usuario " + this.getNombre());
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
	
//	public Object mostrarCuentasAsociadas() {
//		ArrayList<Cuenta> cuentas = this.getCuentasAsociadas();
//		if(cuentas.size() != 0) {
//			return cuentas;
//		}else {
//			return ("Primero debes asociar cuentas");
//		}
//	}
	
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

	//    Funcionalidad Prestamos
	public ArrayList comprobarConfiabilidad(){
		//Deserializacion de las cuentas
		ArrayList<Ahorros> cuentas = (ArrayList<Ahorros>) Deserializador.deserializar_listas("Ahorros");
		ArrayList<Ahorros> cuentasUsuario = new ArrayList<>();
		ArrayList<String> cadena = new ArrayList<>();

		for(int i = 0; i<cuentas.size();i++){
			if(cuentas.get(i).getTitular()==this){
				cuentasUsuario.add(cuentas.get(i));
			}
		}
//		Conseguimos la suscripciones y miramos las deudas
		Suscripcion suscripcion = getSuscripcion();
//		comprobamos y contamos las deudas que estan asociadas al usuario
		ArrayList<Deuda> deudasUsuario = (ArrayList<Deuda>) Deuda.conseguirDeudas(this);
//		returns
		if(deudasUsuario.size()<suscripcion.getMaxDeudas()){
			if(cuentasUsuario.size()!=0){
				return cuentasUsuario;
			}else{
				cadena.add("¡Error! Usted no tiene ninguna cuenta Corriente creada");
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
				if (((Corriente) cuenta).getCupo() != ((Corriente) cuenta).getDisponible()) {
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
	
	@Override
	protected void finalize() { System.out.println("El usuario con id: " + this.getId() + " y nombre: " + this.getNombre() + " fue eliminado satisfactoriamente del sistema."); }
	
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
	public ArrayList<Usuario> getUsuariosTotalesAux() { return usuariosTotalesAux; }
	public void setUsuariosTotalesAux(ArrayList<Usuario> usuariosTotalesAux) { this.usuariosTotalesAux = usuariosTotalesAux; }
}
