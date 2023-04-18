package gestorAplicación;

import java.util.ArrayList;

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
	private ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
	
	//Constructores
	public Usuario(String nombre, String correo, String contrasena, int id, ArrayList<Cuenta> cuentas, Suscripcion suscripcion) {
		super.getUsuarios().add(this);
		this.setSuscripcion(suscripcion);
		this.setLimiteBancos(suscripcion.getLimite_Bancos());
		this.setNombre(nombre);
		this.setContrasena(contrasena);
		this.setCorreo(correo);
		this.setId(id);
		this.setCuentas(cuentas);
	}
	
	public Usuario(String nombre, String correo, String contrasena, int id, Cuenta cuenta, Suscripcion suscripcion) {
		super.getUsuarios().add(this);
		this.setSuscripcion(suscripcion);
		this.setLimiteBancos(suscripcion.getLimite_Bancos());
		this.setNombre(nombre);
		this.setContrasena(contrasena);
		this.setCorreo(correo);
		this.setId(id);
		this.getCuentas().add(cuenta);
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
	
	public Boolean verificarContrasena(String contrasena) {	return (this.contrasena.equals(contrasena)); }
	
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
	public ArrayList<Cuenta> getCuentas() { return cuentas; }
	public void setCuentas(ArrayList<Cuenta> cuentas) { this.cuentas = cuentas; }
	public int getLimiteBancos() { return limiteBancos; }
	public void setLimiteBancos(int limiteBancos) { this.limiteBancos = limiteBancos; }
	public ArrayList<Banco> getBancosAsociados() { return bancosAsociados; }
	public void setBancosAsociados(ArrayList<Banco> bancosAsociados) { this.bancosAsociados = bancosAsociados; }
	public double getComisionUsuario() { return comisionUsuario; }
	public void setComisionUsuario(double comisionUsuario) { this.comisionUsuario = comisionUsuario; }
	public int getContadorMovimientos() { return contadorMovimientos; }
	public void setContadorMovimientos(int contadorMovimientos) { this.contadorMovimientos = contadorMovimientos; }
	
}
