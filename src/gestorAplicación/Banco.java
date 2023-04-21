package gestorAplicación;

import java.util.ArrayList;

public class Banco extends Estado {
	private static final long serialVersionUID = 2L;
	public static final String nombreD = "Bancos";
	private String nombreb;
	private double comision;
	private static ArrayList<Banco> bancosTotales = new ArrayList<Banco>();
	private Estado estadoAsociado;
	private double prestamo;
	
	//Constructores
	public Banco(String nombre, String nombreb, double tasa_interes, double tasa_impuestos, double comision, double prestamo, Divisas divisa) {
		super(nombre, tasa_interes, tasa_impuestos, divisa);
		this.nombreb = nombreb;
		this.comision = comision;
		this.prestamo = prestamo;
		bancosTotales.add(this);
	}
	
	public Banco(String nombreb, double comision, Estado estado) {
		this.nombreb = nombreb;
		this.comision = comision;
		this.setEstadoAsociado(estado);
		bancosTotales.add(this);
	}
	
	public Banco() {}
	
	//Métodos
	
	public String mostrarBancosTotales() {
		if(Banco.bancosTotales.size() != 0) { 
			for (int i = 0; i < Banco.bancosTotales.size();) { 
				return(i+1 + ". " + Banco.bancosTotales.get(i).getNombreb()); 
				}
		}else { 
			return("No hay bancos en este momento, considere asociar bancos"); 
			}
		return ("");
	}
	
	//Funcionalidad de Suscripciones de Usuarios
	public Object comprobarSuscripción(Usuario usuario) {
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

	public static ArrayList<Banco> getBancosTotales() {
		return bancosTotales;
	}
	
	public Estado getEstadoAsociado() {
		return estadoAsociado;
	}

	public double getPrestamo(){return prestamo;}

	//Sets
	public void setComision(double comision) {
		this.comision = comision;
	}
	public void setNombreb(String nombreb) {
		this.nombreb = nombreb;
	}

	public static void setBancosTotales(ArrayList<Banco> bancosTotales) {
		Banco.bancosTotales = bancosTotales;
	}

	public void setEstadoAsociado(Estado estadoAsociado) {
		this.estadoAsociado = estadoAsociado;
	}

	public void setPrestamo(Double prestamo){this.prestamo = prestamo;}
}
