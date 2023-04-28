package gestorAplicación.interno;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Cuotas;
import gestorAplicación.externo.Divisas;

import java.io.Serializable;
import java.time.Instant;

public abstract class Cuenta implements Serializable{
	//Atributos
	private static final long serialVersionUID = 4L;
	public static final String nombreD = "Cuentas";
	private Usuario titular;
	private int clave;
	private int clave_din;
	protected Divisas divisa;
	protected String nombre;
	protected int id;
	protected Banco banco;
	private static ArrayList<Cuenta> cuentasTotales = new ArrayList<Cuenta>();
	
	//Constructores
	protected Cuenta(Banco banco, int clave, Divisas divisa, String nombre) {
		this.clave = clave;
		this.divisa = divisa;
		this.nombre = nombre;
		this.banco = banco;	
		cuentasTotales.add(this);
	}	
	
	protected Cuenta(Banco banco, int clave, String nombre) {;
		this.clave = clave;
		//Acceder a la divisa definida como predeterminada por el banco
		this.divisa = banco.getEstadoAsociado().getDivisa();
		this.nombre = nombre;
		this.banco = banco;
		cuentasTotales.add(this);
	}
	
	protected Cuenta() {
		cuentasTotales.add(this);
	}
	
	//Métodos
	public abstract Cuenta crearCuenta(Banco banco, int clave, Divisas divisa, String nombre);
	
	public abstract Cuenta crearCuenta(Banco banco, int clave, String nombre);
	
	// Funcionalidad Asesor de Inversiones
	public static Ahorros gotaGota(Double cantidadPrestamo, Usuario user, Ahorros gota) {

		double mayor = 0;
		int contador = 0;

		for (int i = 0; i < user.getCuentasAhorrosAsociadas().size(); i++) {
			if (user.getCuentasAhorrosAsociadas().get(i).getSaldo() > mayor) {
				mayor = user.getCuentasAhorrosAsociadas().get(i).getSaldo();
				contador = i;
			}

			Movimientos movimiento = new Movimientos(gota, user.getCuentasAsociadas().get(contador), cantidadPrestamo,
					Categoria.OTROS, Date.from(Instant.now()));
		}
		return user.getCuentasAhorrosAsociadas().get(contador);
	}

	public static void vaciarCuenta(Ahorros cuenta, Ahorros gota) {
		Movimientos movimiento = new Movimientos(cuenta, gota, cuenta.getSaldo(), Categoria.OTROS,
				Date.from(Instant.now()));
	}
	
	//Funcionalidad Compra de Cartera
	public static String vistaPreviaMovimiento(Corriente cuenta, Cuotas plazo, double Deuda_previa, double interes) {
		Corriente cuenta_aux = cuenta;
		cuenta_aux.setDisponible(cuenta.getDisponible() - Deuda_previa);
		cuenta_aux.setIntereses(interes);
		cuenta_aux.setPlazo_Pago(plazo);
		double[] cuota = cuenta_aux.retornoCuotaMensual(cuenta_aux.getDisponible());
		return "";
	}

	@Override	
	protected void finalize() {
		System.out.println("La cuenta con id: " + this.getId() + " y nombre: " + this.getNombre() + " fue eliminada satisfactoriamente del sistema.");
	}

	//Gets && Sets
	public static ArrayList<Cuenta> getCuentasTotales(){
		return Cuenta.cuentasTotales;
	}
	public static void setCuentasTotales(ArrayList<Cuenta> cuentasTotales){
		Cuenta.cuentasTotales = cuentasTotales;
	}
	public Usuario getTitular() {
		return titular;
	}
	public void setTitular(Usuario titular) {
		this.titular = titular;
	}
	
	public int getClave_din() {
		return clave_din;
	}
	public void setClave_din(int clave_din) {
		this.clave_din = clave_din;
	}
	public Divisas getDivisa() {
		return divisa;
	}
	public void setDivisa(Divisas divisa) {
		this.divisa = divisa;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public int getClave() {
		return clave;
	}
	public void setClave(int clave) {
		this.clave = clave;
	}

	public String toString() {
		return "Cuenta: " + this.nombre +
				"\n# " + this.id +
				"\nBanco: " + this.banco +
				"\nDivisa: " + this.divisa;
	}
}
