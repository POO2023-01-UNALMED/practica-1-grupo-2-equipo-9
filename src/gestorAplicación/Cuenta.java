package gestorAplicación;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Cuenta extends Banco{
	private static final long serialVersionUID = 4L;
	public static final String nombreD = "Cuentas";

	private Usuario titular;
	private String tipo;
	private Double saldo;
	private int clave_din;
	private String divisa;
	private String nombre;
	private int id;
	
	public Cuenta(Banco banco, Usuario titular, String tipo, Double saldo, int clave_din, String divisa, String nombre, int id) {
		this.titular = titular;
		this.tipo = tipo;
		this.saldo = saldo;
		this.clave_din = clave_din;
		this.divisa = divisa;
		this.nombre = nombre;
		this.id = id;
		super.setBanco(Banco);
		

	}
	
	public Usuario getTitular() {
		return titular;
	}
	public void serTitular(Usuario titular) {
		this.titular = titular;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	public int getClave_din() {
		return clave_din;
	}
	public void setClave_din(int clave_din) {
		this.clave_din = clave_din;
	}
	
	public String getDivisa() {
		return divisa;
	}
	public void setDivisa(String divisa) {
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
	
	
	public int invertirSaldo() {
		//Aclaración del método
	}
	
	public void eliminarCuenta() {//¿Validación?
		if (this.saldo != 0) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Por favor, elija el destino del saldo restante en la cuenta:");
			System.out.println("Recuerde que para el proceso debe ingresar sólo los numerales de las opciones que desee escoger.");
			System.out.println("1. Cuenta externa");
			System.out.println("2. Cuenta propia");
			int decision_saldo = sc.nextInt();
			//Lectura de la opción con variable decision_saldo
			if (decision_saldo == 1) {
				System.out.println("Ingrese los datos de la cuenta a la cual desea transferir su saldo:");
				//Lectura datos, posible modificación según método crearMovimiento: Cuenta destino, int id,double cantidad,Categoria categoria, Date fecha)
				System.out.println("Cuenta destino: ");
				String saldo = sc.nextLine();
				System.out.println("Categoria: ");
				String categoria = sc.nextLine();
				System.out.println("Fecha: ");
				//Ingresar fecha
				Movimientos.crearMovimiento(this, destino, id, this.getSaldo(), categoria, fecha);
			} else if (decision_saldo == 2) {
				System.out.println("A cual de sus cuentas desea transferir su saldo:");
				ArrayList<Cuenta> cuentas = titular.getCuentas();
				for (int i = 1; i == cuentas.size() + 1; i++) {
					System.out.println(i + ". " + cuentas.get(i).getNombre());
				}
				int decision_cuenta = sc.nextInt();
				//Lectura de la opcion con decision_cuenta
				Movimientos.modificarSaldo(this, cuentas.get(decision_cuenta).getNombre(), this.getSaldo());
			} else {
				System.out.println("Opción no válida. Proceso finalizado por su seguridad");
				return;
			}
		}
		//System.gc();
		finalize();
	}
	
	protected void finalize() {
		System.out.println("La cuenta: " + this.getNombre() + " y nombre: " + this.getNombre() + " fue eliminada satisfactoriamente del sistema.");
	}
	
	public void crearTransaccion() {
		//Similar al método de movimientos. Preguntar validez y claridad
	}
}
