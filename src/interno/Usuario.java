package interno;

import java.util.ArrayList;
import baseDatos.Deserializador;
import externo.Banco;

public class Usuario extends Banco {
	//Funcionalidad de Suscripciones de Usuarios
	private ArrayList<Cuenta> cuentasAsociadas = new ArrayList<Cuenta>();
	private int limiteCuentas;
	private double comisionUsuario;
	private int contadorMovimientos;
	
	//Atributos
	private static final long serialVersionUID = 3L;
	public static final String nombreD = "Usuarios";
	private Suscripcion suscripcion;
	private String nombre;
	private String correo;
	private String contrasena;
	private int id;
	private ArrayList<Banco> bancosAsociados = new ArrayList<Banco>();
	private Boolean confiabilidad;
	private Double deuda;
	private static ArrayList<Usuario> usuariosTotales = new ArrayList<Usuario>();;
	private ArrayList<Metas> metasAsociadas = new ArrayList<Metas>();
	private ArrayList<Movimientos> movimientosAsociadas = new ArrayList<Movimientos>();
	
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
		if(Cuenta.getCuentasTotales().contains(cuenta) && !cuentasAsociadas.contains(cuenta) && this.getCuentasAsociadas().size() < this.getLimiteCuentas()) {
			cuenta.setTitular(this);
			this.getCuentasAsociadas().add(cuenta);
			return("La cuenta " + cuenta.getNombre() + " se ha asociado con éxito al usuario " + this.getNombre());
		}else {
			return("No se encuentra tu cuenta, debes verificar que la cuenta que quieres asociar no haya sido asociada antes ó debes verificar que no hayas alcanzado el máximo de cuentas que puede asociar el usuario: " + this.getLimiteCuentas());
		}
	}
	
	public String asociarMeta(Metas meta) {
		if(Metas.getMetasTotales().contains(meta)) {
			meta.setDueno(this);
			this.getMetasAsociadas().add(meta);
			return("La meta " + meta.getNombre() + " se ha asociado con éxito al usuario " + this.getNombre());
		}else {
			return("No se encuentra tu meta ó debes verificar que la meta que quieres asociar exista" );
		}
	}
	
	public String asociarMovimiento(Movimientos movimiento) {
		if(Movimientos.getMovimientosTotales().contains(movimiento)) {
			movimiento.setOwner(this);
			this.getMovimientosAsociadas().add(movimiento);
			return("El movimiento con destino " + movimiento.getDestino().getNombre() + " ha sido asociada correctamente al usuario " + this.getNombre());
		}else {
			return("No se encuentra el movimiento. Por favor asegurese de que el movimiento se haya realizado con éxito" );
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
	public ArrayList comprobarConfiabilidad(Usuario usuario){
		//Deserializacion de las cuentas
		ArrayList<Cuenta> cuentas = (ArrayList<Cuenta>) Deserializador.deserializar_listas("Cuenta");
		ArrayList<Cuenta> cuentasUsuario = new ArrayList<>();
		ArrayList<String> cadena = new ArrayList<>();

		for(int i = 0; i<cuentas.size();i++){
			if(cuentas.get(i).getTitular()==usuario){
				cuentasUsuario.add(cuentas.get(i));
			}
		}

		if(getConfiabilidad()){
			if(cuentas.size()!=0){
				return cuentasUsuario;
			}else{
				cadena.add("¡Error! Usted no tiene ninguna cuenta creada");
			}
		}else{
			cadena.add("¡Error! Usted ya tiene una deuda de $"+getDeuda());
		}
		return cadena;
	}

	public ArrayList<Cuenta> retornarDeudas(){
		ArrayList<Cuenta> cuentasEndeudadas = new ArrayList<Cuenta>();
		for (Cuenta cuenta: cuentasAsociadas) {
			if (cuenta.getExistenciaPrestamo()) {
				cuentasEndeudadas.add(cuenta);
			}
		}
		return cuentasEndeudadas;
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
	public double getComisionUsuario() { return comisionUsuario; }
	public void setComisionUsuario(double comisionUsuario) { this.comisionUsuario = comisionUsuario; }
	public int getContadorMovimientos() { return contadorMovimientos; }
	public void setContadorMovimientos(int contadorMovimientos) { this.contadorMovimientos = contadorMovimientos; }
	public Boolean getConfiabilidad(){return confiabilidad;}
	public  void setConfiabiliad(Boolean confiabilidad){this.confiabilidad = confiabilidad;}
	public Double getDeuda(){return deuda;}
	public  void setDeuda(Double deuda){this.deuda = deuda;}
	public ArrayList<Metas> getMetasAsociadas() {return metasAsociadas;}
	public void setMetasAsociadas(ArrayList<Metas> metasAsociadas) {this.metasAsociadas = metasAsociadas;}
	public ArrayList<Movimientos> getMovimientosAsociadas() {return movimientosAsociadas;}
	public void setMovimientosAsociadas(ArrayList<Movimientos> movimientosAsociadas) {this.movimientosAsociadas = movimientosAsociadas;}
}
