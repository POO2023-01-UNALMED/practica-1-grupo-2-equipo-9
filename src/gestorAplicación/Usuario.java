package gestorAplicación;

import java.util.ArrayList;
import baseDatos.Deserializador;
import baseDatos.Serializador;
public class Usuario extends Banco {
	//Funcionalidad de Suscripciones de Usuarios
	private ArrayList<Banco> bancosAsociados = new ArrayList<Banco>();
	private int limiteBancos;
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
	private ArrayList<Cuenta> cuentasAsociadas= new ArrayList<Cuenta>();
	private Cuenta cuentaAsociada;
	private Boolean confiabilidad;
	private Double deuda;
	@SuppressWarnings("unchecked")
	public static ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) Deserializador.deserializar_listas("Usuario");
	
	//Constructor
	
	public Usuario(String nombre, String correo, String contrasena, int id, Suscripcion suscripcion) {
		super.getUsuarios().add(this);
		this.setSuscripcion(suscripcion);
		this.setLimiteBancos(suscripcion.getLimite_Bancos());
		this.setNombre(nombre);
		this.setContrasena(contrasena);
		this.setCorreo(correo);
		this.setId(id);
		//listaUsuarios.add(this);
		//Serializador.serializar(listaUsuarios, "Usuario");
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
					return("Felicidades, has sido promovido al nivel de DIAMANTE, estos son tus beneficios: " + "puedes asociar un máximo de " + Suscripcion.DIAMANTE.getLimite_Bancos() + " bancos, la probabilidad de ganar en tu inversión es de " + Suscripcion.DIAMANTE.getProbabilidad_Inversion());
				case PLATA:
					this.setContadorMovimientos(0);
					this.setSuscripcion(Suscripcion.ORO);
					return("Felicidades, has sido promovido al nivel de ORO, estos son tus beneficios: " + "puedes asociar un máximo de " + Suscripcion.ORO.getLimite_Bancos() + " bancos, la probabilidad de ganar en tu inversión es de " + Suscripcion.ORO.getProbabilidad_Inversion());
				case BRONCE:
					this.setContadorMovimientos(0);
					this.setSuscripcion(Suscripcion.PLATA);
					return("Felicidades, has sido promovido al nivel de PLATA, estos son tus beneficios: " + "puedes asociar un máximo de " + Suscripcion.PLATA.getLimite_Bancos() + " bancos, la probabilidad de ganar en tu inversión es de " + Suscripcion.PLATA.getProbabilidad_Inversion());
				default:
					return("");
			
			}
		}else {
		return("Debes completar 5 movimientos para ser promovido de nivel");
		}
	}
	
	public String asociarBanco(Banco banco) {
		if(Banco.getBancos().contains(banco) && !bancosAsociados.contains(banco)) {
			this.getBancosAsociados().add(banco);
			return("El banco " + banco.getNombreb() + " se ha asociado con éxito al usuario " + this.getNombre());
		}else {
			return("No se encuentra el banco ó debes verificar que el banco que quieres asociar no se haya asociado antes, esta es la lista de bancos asociados: " + this.mostrarBancosAsociados());
		}
	}
	
	public String asociarCuenta(Cuenta cuenta) {
		if(Cuenta.getCuentasTotales().contains(cuenta) && bancosAsociados.contains(cuenta.getBanco())) {
			cuenta.setTitular(this);
			this.setCuentaAsociada(cuenta);
			return("La cuenta " + cuenta.getNombre() + " se ha asociado con éxito al usuario " + this.getNombre());
		}else {
			return("No se encuentra tu cuenta ó debes verificar que la cuenta que quieres asociar pertenece a la lista de bancos permitidos para el usuario: " + this.mostrarBancosAsociados());
		}
	}
	
	public String mostrarBancosAsociados() {
		ArrayList<Banco> bancos = this.getBancosAsociados();
		if(bancos.size() != 0) {
			for(Banco b: bancos) {
				return(b.getNombreb());
			}
		}else {
			return ("Primero debes asociar bancos, el limite para el usuario " + this.getNombre() + " es de " + this.getLimiteBancos());
		}
		return ("");
	}
	
	public static boolean verificarCredenciales(String nombre, String contraseña) {
		for (Usuario usuario: listaUsuarios) {
			if (usuario.getNombre().equals(nombre) || usuario.getCorreo().equals(nombre)) {
				if (usuario.getContrasena().equals(contraseña)) {
					return true;
				}
			}
		}
		System.out.println();
		return false;
	}

	//    Funcionalidad Prestamos
	public ArrayList comprobarConfiabilidad(Usuario usuario){
		//Desealizacion de las cuentas
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
	
	@Override
	protected void finalize() { System.out.println("El usuario con id: " + this.getId() + " y nombre: " + this.getNombre() + " fue eliminado satisfactoriamente del sistema."); }
	
	//Métodos Get & Set
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
	public ArrayList<Cuenta> getCuentas() { return cuentasAsociadas; }
	public void setCuentas(ArrayList<Cuenta> cuentasAsociadas) { this.cuentasAsociadas = cuentasAsociadas; }
	public int getLimiteBancos() { return limiteBancos; }
	public void setLimiteBancos(int limiteBancos) { this.limiteBancos = limiteBancos; }
	public ArrayList<Banco> getBancosAsociados() { return bancosAsociados; }
	public void setBancosAsociados(ArrayList<Banco> bancosAsociados) { this.bancosAsociados = bancosAsociados; }
	public double getComisionUsuario() { return comisionUsuario; }
	public void setComisionUsuario(double comisionUsuario) { this.comisionUsuario = comisionUsuario; }
	public int getContadorMovimientos() { return contadorMovimientos; }
	public void setContadorMovimientos(int contadorMovimientos) { this.contadorMovimientos = contadorMovimientos; }
	public Cuenta getCuentaAsociada() { return cuentaAsociada; }
	public void setCuentaAsociada(Cuenta cuentaAsociada) { this.cuentaAsociada = cuentaAsociada; }

	public Boolean getConfiabilidad(){return confiabilidad;}
	public  void setConfiabiliad(Boolean confiabilidad){this.confiabilidad = confiabilidad;}
	public Double getDeuda(){return deuda;}
	public  void setDeuda(Double deuda){this.deuda = deuda;}
	
}
