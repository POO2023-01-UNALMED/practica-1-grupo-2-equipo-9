package gestorAplicación.interno;

import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Divisas;
import java.util.ArrayList;

public class Ahorros extends Cuenta{
	//Atributos
	private static final long serialVersionUID = 7L;
	public static final String nombreD = "Ahorros";
	private static ArrayList<Ahorros> cuentasAhorroTotales = new ArrayList<Ahorros>();;
	
	protected double saldo = 0.0d;

	//Constructores
	public Ahorros(Banco banco, int clave, Divisas divisa, String nombre, Double saldo) {
		super(banco, clave, divisa, nombre);
		this.saldo = saldo;
	}
	
	public Ahorros(Banco banco, int clave, String nombre, Double saldo) {
		super(banco, clave, nombre);
		this.saldo = saldo;
	}
	
	public Ahorros(Double saldo) {
		super();
		this.saldo = saldo;
	}
	
	public Ahorros(Banco banco, int clave, Divisas divisa, String nombre) {
		super(banco, clave, divisa, nombre);
	}	
	
	public Ahorros(Banco banco, int clave, String nombre) {
		super(banco, clave, nombre);
	}
	
	public Ahorros() {
		super();
	}
	
	//Métodos
	public Ahorros crearCuenta(Banco banco, int clave, Divisas divisa, String nombre) {
		return (new Ahorros(banco, clave, divisa, nombre));
	}
	
	public Ahorros crearCuenta(Banco banco, int clave, String nombre) {
		return (new Ahorros(banco, clave, nombre));
	}
	
	public String toString() {
		return "Cuenta: " + super.nombre +
				"\nCuenta de Ahorros # " + this.id +
				"\nBanco: " + this.banco +
				"\nDivisa: " + this.divisa +
				"\nSaldo: " + this.saldo + " " + this.divisa;
	}
	
	public double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	public static ArrayList<Ahorros> getCuentasAhorroTotales() {
		return cuentasAhorroTotales;
	}

	public static void setCuentasAhorroTotales(ArrayList<Ahorros> cuentasAhorroTotales) {
		Ahorros.cuentasAhorroTotales = cuentasAhorroTotales;
	}
}
