package gestorAplicación;

public class Usuario {
	private String nombre;
	private String correo;
	private String contrasena;
	private int id;
	
	public Usuario(String nombre, String correo, String contrasena, int id) {
		 this.nombre = nombre;
		 this.contrasena = contrasena;
		 this.correo = correo;
		 this.id = id;
	}
	
	public Boolean verificarContrasena(String contrasena) {	return (this.contrasena.equals(contrasena)); }
	
	@Override
	protected void finalize() {
		System.out.println("El usuario con id: " + this.getId() + " y nombre: " + this.getNombre() + " fue eliminado satisfactoriamente del sistema.");
	}
	
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getCorreo() { return correo; }
	public void setCorreo(String correo) { this.correo = correo; }
	public String getContrasena() { return contrasena; }
	public void setContrasena(String contrasena) { this.contrasena = contrasena; }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

}
