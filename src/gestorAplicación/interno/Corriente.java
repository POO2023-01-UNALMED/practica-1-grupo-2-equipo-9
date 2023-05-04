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
	//Atributo que decide pago de interes en primer mes
	private boolean primerMensualidad;
	private static ArrayList<Corriente> cuentasCorrienteTotales = new ArrayList<Corriente>();;
	
	
	//Constructores
	//Hacer chequeo, cupo viene por defecto según suscripción y banco asociado.
	public Corriente(Banco banco, int clave, Divisas divisa, String nombre) {
		super(banco, clave, divisa, nombre);
		Corriente.getCuentasCorrienteTotales().add(this);
	}	
	
	public Corriente(Banco banco, int clave, String nombre) {
		super(banco, clave, nombre);
		Corriente.getCuentasCorrienteTotales().add(this);
	}
	
	public Corriente() {
		super();
		Corriente.getCuentasCorrienteTotales().add(this);
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
		double interes_nominal_mensual = Corriente.calculoInteresNominalMensual(this.getIntereses());
		double interes = DeudaActual * (interes_nominal_mensual / 100);
		cuotaMensual[0] = interes;
		double abono_capital = this.getDisponible() / this.getPlazo_Pago().getCantidad_Cuotas();
		cuotaMensual[1] = abono_capital;
		double cuotaMensualFinal = interes + abono_capital;
		cuotaMensual[2] = cuotaMensualFinal;
		return cuotaMensual;
	}
	
	public double[] retornoCuotaMensual(double DeudaActual, int mes) {
		double[] cuotaMensual = new double[3];
		double interes_nominal_mensual = Corriente.calculoInteresNominalMensual(this.getIntereses());
		if(mes == 1) {
			cuotaMensual[0] = 0;
			double abono_capital = this.getDisponible() / this.getPlazo_Pago().getCantidad_Cuotas();
			cuotaMensual[1] = abono_capital;
			double cuotaMensualFinal = abono_capital;
			cuotaMensual[2] = cuotaMensualFinal;
		}
		else if(mes == 2){
			double abono_capital = this.getDisponible() / this.getPlazo_Pago().getCantidad_Cuotas();
			double interes_mes1 = (interes_nominal_mensual / 100) * (abono_capital + DeudaActual);
			double interes_mes2 = DeudaActual * (interes_nominal_mensual / 100);
			double interes = interes_mes1 + interes_mes2;
			cuotaMensual[0] = interes;
			cuotaMensual[1] = abono_capital;
			double cuotaMensualFinal = interes + abono_capital;
			cuotaMensual[2] = cuotaMensualFinal;
		}
		
		return cuotaMensual;
	}
	
	public static String imprimirCuotaMensual(double[] cuotaMensual) {
		return "Cuota: " + cuotaMensual[2] +
				"\nIntereses: " + cuotaMensual[1] +
				"\nAbono a capital: " + cuotaMensual[0];
	}
	
	public static double calculoInteresNominalMensual(double interesEfectivoAnual) {
		double interes = Math.pow((1 + interesEfectivoAnual), (30 / 360)) - 1;
		return interes;
	}
	
	// Funcionalidad asesor inversiones

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
	
	//Método creado como calculadora de cuotas mensuales para pago de un préstamo
		//El atributo interes hace referencia a la tasa efectiva anual
	public static double[][] calculadoraCuotas(Cuotas cuotas, double deuda, double intereses) {
		int cuotasTotales = cuotas.getCantidad_Cuotas();
		double[][] cuota = new double[cuotasTotales][3];
		double interesMensual = Corriente.calculoInteresNominalMensual(intereses);
		double deudaActual = deuda;
		
		double abono_capital = deuda / cuotasTotales;
		
		for (int i = 0; i <= cuotasTotales; i++) {
			double[] cuotaMes = new double[3];
			double interes = deudaActual * (interesMensual / 100);
			cuotaMes[0] = interes;
			double cuota_pagar = interes + abono_capital;
			cuotaMes[1] = cuota_pagar;
			double deudaTotal = deudaActual - (cuota_pagar - interes);
			cuotaMes[2] = deudaTotal;
			cuota[i] = cuotaMes;
			
			deudaActual = deudaTotal;
		}
		
		return cuota;
	}
	
	public static double[][] calculadoraCuotas(Cuotas cuotas, double deuda, double intereses, boolean auxiliar){
		int cuotasTotales = cuotas.getCantidad_Cuotas();
		double[][] cuota = new double[cuotasTotales][3];
		double interesMensual = Corriente.calculoInteresNominalMensual(intereses);
		double deudaActual = deuda;
		
		double abono_capital = deuda / cuotasTotales;
		
		double interesMes1 = deudaActual * (interesMensual / 100);
		double[] cuotaMes1 = new double[3];
		cuotaMes1[0] = 0;
		cuotaMes1[1] = abono_capital;
		cuotaMes1[2] = deudaActual - abono_capital;
		cuota[0] = cuotaMes1;
		
		deudaActual = deudaActual - abono_capital;
		
		for (int i = 1; i <= cuotasTotales; i++) {
			double[] cuotaMes = new double[3];
			double interes = deudaActual * (interesMensual / 100);
			cuotaMes[0] = interes;
			double cuota_pagar = interes + abono_capital + interesMes1;
			cuotaMes[1] = cuota_pagar;
			double deudaTotal = deudaActual - (cuota_pagar - interes);
			cuotaMes[2] = deudaTotal;
			cuota[i] = cuotaMes;
			
			interesMes1 = 0;
			deudaActual = deudaTotal;
		}
		
		return cuota;
	}
	
	public static double[] informacionAdicionalCalculadora (double[][] cuota, double deuda) {
		double[] infoAdicional = new double[3];
		double totalPagado = 0;
		
		for (int i = 0; i <= cuota.length; i++) {
			totalPagado += cuota[i][1];
		}
		
		double interesesPagados = totalPagado - deuda;
		
		infoAdicional[0] = totalPagado;
		infoAdicional[1] = interesesPagados;
		infoAdicional[2] = deuda;
		
		return infoAdicional;
	}
	
	public int compareTo(Corriente cuenta) {
		if(this.getDisponible() > cuenta.getDisponible()) {
			return 1;
		}
		else if(this.getDisponible() < cuenta.getDisponible()) {
			return -1;
		}
		else {
			return 0;
		}
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

	public boolean getPrimerMensualidad() {
		return primerMensualidad;
	}
	
	public void setPrimerMensualidad(boolean primerMensualidad) {
		this.primerMensualidad = primerMensualidad;
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
