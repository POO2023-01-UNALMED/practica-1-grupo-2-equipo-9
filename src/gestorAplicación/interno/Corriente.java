package gestorAplicación.interno;

import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Divisas;

public class Corriente extends Cuenta{
	private static final long serialVersionUID = 8L;
	public static final String nombreD = "Corriente";
	
	//Atributos
	private Double Cupo = 0.0d;
	private boolean existenciaPrestamo;
	
	//Constructores
	//Hacer chequeo, cupo viene por defecto según suscripción y banco asociado.
	protected Corriente(Banco banco, int clave, Divisas divisa, String nombre) {
		super(banco, clave, divisa, nombre);
	}	
	
	protected Corriente(Banco banco, int clave, String nombre) {
		super(banco, clave, nombre);
	}
	
	protected Corriente() {
		super();
	}
	
	//Métodos
	public Corriente crearCuenta(Banco banco, int clave, Divisas divisa, String nombre) {
		return (new Corriente(banco, clave, divisa, nombre));
	}
	
	public Corriente crearCuenta(Banco banco, int clave, String nombre) {
		return (new Corriente(banco, clave, nombre));
	}
	
	public Double getCupo() {
		return Cupo;
	}
	public void setCupo(Double cupo) {
		Cupo = cupo;
	}
	public boolean getExistenciaPrestamo() {
		return existenciaPrestamo;
	}
	public void setExistenciaPrestamo(boolean existenciaPrestamo) {
		this.existenciaPrestamo = existenciaPrestamo;
	}
	
	public String toString() {
		return "Cuenta: " + this.nombre +
				"\nCuenta Corriente # " + this.id +
				"\nBanco: " + this.banco +
				"\nDivisa: " + this.divisa +
				"\nCupo disponible: " + this.Cupo + " " + this.divisa;
	}
}
