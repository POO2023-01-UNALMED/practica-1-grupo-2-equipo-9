package gestorAplicación;

import java.util.ArrayList;

public class Usuario extends Estado {
	//Atributos
	private static final long serialVersionUID = 3L;
	public static String nombreD = "Usuarios";
	private String nombre;
	private String correo;
	private String contrasena;
	private int id;
	private ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
	
	//Constructores
	public Usuario(String nombre, String correo, String contrasena, int id, ArrayList<Cuenta> cuentas) {
		 this.nombre = nombre;
		 this.contrasena = contrasena;
		 this.correo = correo;
		 this.id = id;
		 this.setCuentas(cuentas);
	}
	
	public Usuario(String nombre, String correo, String contrasena, int id) {
		 this.nombre = nombre;
		 this.contrasena = contrasena;
		 this.correo = correo;
		 this.id = id;
	}
	
	//Metodos de instancia
	public Boolean verificarContrasena(String contrasena) {	return (this.contrasena.equals(contrasena)); }
	
	@Override
	protected void finalize() {
		System.out.println("El usuario con id: " + this.getId() + " y nombre: " + this.getNombre() + " fue eliminado satisfactoriamente del sistema.");
	}
	
	//Métodos Get & Set
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getCorreo() { return correo; }
	public void setCorreo(String correo) { this.correo = correo; }
	public String getContrasena() { return contrasena; }
	public void setContrasena(String contrasena) { this.contrasena = contrasena; }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public ArrayList<Cuenta> getCuentas() { return cuentas; }
	public void setCuentas(ArrayList<Cuenta> cuentas) { this.cuentas = cuentas; }
	
}
