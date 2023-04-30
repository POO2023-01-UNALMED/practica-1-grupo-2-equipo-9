package gestorAplicación.interno;

import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Cuotas;
import gestorAplicación.externo.Divisas;
import java.util.ArrayList;
import java.util.Date;
import java.time.Instant;

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
		double[] cuotaMensual = new double[3];
		double interes_nominal_mensual = this.calculoInteresNominalMensual(this.getIntereses());
		double interes = DeudaActual * (interes_nominal_mensual / 100);
		cuotaMensual[0] = interes;
		double abono_capital = this.disponible / this.getPlazo_Pago().getCantidad_Cuotas();
		cuotaMensual[1] = abono_capital;
		double cuotaMensualFinal = interes + abono_capital;
		cuotaMensual[2] = cuotaMensualFinal;
		return cuotaMensual;
	}
	
	public static String imprimirCuotaMensual(double[] cuotaMensual) {
		return "Cuota: " + cuotaMensual[2] +
				"\nIntereses: " + cuotaMensual[1] +
				"\nAbono a capital: " + cuotaMensual[0];
	}
	
	public double calculoInteresNominalMensual(double interesEfectivoAnual) {
		double interes = Math.pow((1 + interesEfectivoAnual), (30 / 360)) - 1;
		return interes;
	}
	
	// Funcionalidad asesor inversiones
	@Override
	public void vaciarCuenta(Ahorros gota) {
		Movimientos movimiento = new Movimientos(this, gota, this.getDisponible(), Categoria.OTROS,
				Date.from(Instant.now()));
		this.getTitular().asociarMovimiento(movimiento);
	}
	
	//Funcionalidad Compra de Cartera
	public static Corriente vistaPreviaMovimiento(Corriente cuenta, Cuotas plazo, double Deuda_previa, double interes) {
		Corriente cuenta_aux = cuenta;
		cuenta_aux.setDisponible(cuenta.getDisponible() - Deuda_previa);
		cuenta_aux.setIntereses(interes);
		cuenta_aux.setPlazo_Pago(plazo);
		return cuenta_aux;
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
