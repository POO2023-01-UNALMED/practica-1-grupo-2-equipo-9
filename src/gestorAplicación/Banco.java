package gestorAplicación;

import java.util.ArrayList;

public class Banco extends Estado {
	private static final long serialVersionUID = 2L;
	public static final String nombreD = "Bancos";
	private String nombreb;
	private double comision;
	private static ArrayList<Banco> bancos = new ArrayList<Banco>();
	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	//Constructor
	public Banco(String nombre, double tasa_interes, double tasa_impuestos, String nombreb, double comision, ArrayList<Banco> bancos) {
		super(nombre, tasa_interes, tasa_impuestos);
		this.nombreb = nombreb;
		this.comision = comision;
		bancos.add(this);
	}
	
	public Banco() {}
	
	//Métodos
	
	public String imprimirBancos() {
		if(Banco.bancos.size() != 0) { for (int i = 0; i < Banco.bancos.size(); i++) { return(i+1 + ". " + Banco.bancos.get(i).getNombreb()); }
		}else { return("No hay bancos en este momento, considere asociar bancos"); }
		return ("");
	}
	
	//Funcionalidad de Suscripciones de Usuarios
	public String comprobarSuscripción(Usuario usuario) {
		for(Usuario u : this.getUsuarios()) {
			if(usuario.getId() == u.getId()) {
				switch(usuario.getSuscripcion()) {
					case DIAMANTE:
						this.setComision(this.getComision() * 0.50);
						return ("Bienvenido " + usuario.getNombre() + ", eres un cliente fiel de nuestro banco, por eso te cobramos " + (this.getComision() * 0.50));
					case ORO:
						this.setComision(this.getComision() * 0.65);
						return ("Bienvenido " + usuario.getNombre() + ", eres un cliente frecuente de nuestro banco, por eso te cobramos " + this.getComision() * 0.65);
					case PLATA:
						this.setComision(this.getComision() * 0.85);
						return ("Bienvenido " + usuario.getNombre() + ", eres un cliente ocasional de nuestro banco, por eso te cobramos " + this.getComision() * 0.85);
					case BRONCE:
						this.setComision(this.getComision());
						return ("Bienvenido " + usuario.getNombre() + ", eres un cliente ausente de nuestro banco, por eso te cobramos " + this.getComision());	
					default:
						return ("No encontramos tu grado de suscripción, considera registrarte en nuestro banco.");
				}
			}
		} return ("No encontramos tu ID registrado en este banco, considera registrarte en nuestro banco.");
	}
	
	//Gets
	public double getComision() {
		return comision;
	}
	public String getNombreb() {
		return nombreb;
	}

	public ArrayList<Banco> getBancos() {
		return bancos;
	}
	
	public ArrayList<Usuario> getUsuarios() {
		return (this.usuarios);
	}
	
	//Sets
	public void setComision(double comision) {
		this.comision = comision;
	}
	public void setNombreb(String nombreb) {
		this.nombreb = nombreb;
	}

	public void setBancos(ArrayList<Banco> bancos) {
		Banco.bancos = bancos;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
