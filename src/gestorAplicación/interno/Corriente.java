package gestorAplicación.interno;

import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Cuotas;
import gestorAplicación.externo.Divisas;

public class Corriente extends Cuenta{
	private static final long serialVersionUID = 8L;
	public static final String nombreD = "Corriente";
	
	//Atributos
	private Double Cupo;
	private Double Disponible = 0.0d;
	private boolean existenciaPrestamo;
	private Cuotas plazo_Pago;
	
	//Constructores
	//Hacer chequeo, cupo viene por defecto según suscripción y banco asociado.
	public Corriente(Banco banco, int clave, Divisas divisa, String nombre) {
		super(banco, clave, divisa, nombre);
	}	
	
	public Corriente(Banco banco, int clave, String nombre) {
		super(banco, clave, nombre);
	}
	
	public Corriente() {
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
	
	public Cuotas getPlazo_Pago() {
		return plazo_Pago;
	}
	public void setPlazo_Pago(Cuotas plazo_Pago) {
		this.plazo_Pago = plazo_Pago;
	}
	
	public Double getDisponible() {
		return Disponible;
	}
	public void setDisponible(Double disponible) {
		Disponible = disponible;
	}

	public String toString() {
		return "Cuenta: " + this.nombre +
				"\nCuenta Corriente # " + this.id +
				"\nBanco: " + this.banco +
				"\nDivisa: " + this.divisa +
				"\nCupo disponible: " + this.Cupo + " " + this.divisa;
	}
}
