package gestorAplicación.interno;

import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Cuotas;
import gestorAplicación.externo.Divisas;
import java.util.ArrayList;

public class Corriente extends Cuenta{
	//Atributos
	private static final long serialVersionUID = 8L;
	public static final String nombreD = "Corriente";
	private double cupo;
	private double disponible;
	private Cuotas plazo_Pago;
	//Tasa efectiva anual
	private double intereses;
	private static ArrayList<Corriente> cuentasCorrienteTotales = new ArrayList<Corriente>();;
	
	
	//Constructores
	//Hacer chequeo, cupo viene por defecto según suscripción y banco asociado.
	public Corriente(Banco banco, int clave, Divisas divisa, String nombre) {
		super(banco, clave, divisa, nombre);
		cuentasCorrienteTotales.add(this);
	}	
	
	public Corriente(Banco banco, int clave, String nombre) {
		super(banco, clave, nombre);
		cuentasCorrienteTotales.add(this);
	}
	
	public Corriente() {
		super();
		cuentasCorrienteTotales.add(this);
	}
	
	//Métodos
	public Corriente crearCuenta(Banco banco, int clave, Divisas divisa, String nombre) {
		return (new Corriente(banco, clave, divisa, nombre));
	}
	
	public Corriente crearCuenta(Banco banco, int clave, String nombre) {
		return (new Corriente(banco, clave, nombre));
	}
	
	public double[] retornoCuotaMensual(double DeudaActual) {
		double[] CuotaMensual = new double[3];
		double interes_nominal_mensual = this.calculoInteresNominalMensual(this.getIntereses());
		double interes = DeudaActual * (interes_nominal_mensual / 100);
		CuotaMensual[0] = interes;
		double abono_capital = this.disponible / this.getPlazo_Pago().getCantidad_Cuotas();
		CuotaMensual[1] = abono_capital;
		double cuotaMensual = interes + abono_capital;
		CuotaMensual[2] = cuotaMensual;
		return CuotaMensual;
	}
	
	public double calculoInteresNominalMensual(double interesEfectivoAnual) {
		double interes = Math.pow((1 + interesEfectivoAnual), (30 / 360)) - 1;
		return interes;
	}
	
	public Double getCupo() {
		return cupo;
	}
	
	public void setCupo(Double cupo) {
		this.cupo = cupo;
	}
	
	public Cuotas getPlazo_Pago() {
		return plazo_Pago;
	}
	
	public void setPlazo_Pago(Cuotas plazo_Pago) {
		this.plazo_Pago = plazo_Pago;
	}
	
	public Double getDisponible() {
		return disponible;
	}
	
	public void setDisponible(Double disponible) {
		this.disponible = disponible;
	}

	public double getIntereses() {
		return intereses;
	}

	public void setIntereses(double intereses) {
		this.intereses = intereses;
	}

	public String toString() { 
		return "Cuenta: " + this.nombre +
				"\nCuenta Corriente # " + this.id +
				"\nBanco: " + this.banco +
				"\nDivisa: " + this.divisa +
				"\nCupo disponible: " + this.cupo + " " + this.divisa +
				"\nCuotas: " + this.plazo_Pago;
	}
	
	public static ArrayList<Corriente> getCuentasCorrienteTotales() {
		return cuentasCorrienteTotales;
	}

	public static void setCuentasCorrienteTotales(ArrayList<Corriente> cuentasCorrienteTotales) {
		Corriente.cuentasCorrienteTotales = cuentasCorrienteTotales;
	}
}
