package gestorAplicación.interno;

import java.util.ArrayList;
import java.util.Date;
import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Divisas;
import gestorAplicación.externo.Estado;

import java.io.Serializable;
import java.time.Instant;

public abstract class Cuenta implements Serializable, Comparable<Cuenta>{
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
	private static transient ArrayList<Cuenta> cuentasTotales = new ArrayList<Cuenta>();

	
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
	public static Cuenta gotaGota(double cantidadPrestamo, Usuario user, Ahorros gota) {

		double mayor = 0;
		int contador = 0;
		if (user.getCuentasCorrienteAsociadas().size() == 0) {
			for (int i = 0; i < user.getCuentasAhorrosAsociadas().size(); i++) {
				if (user.getCuentasAhorrosAsociadas().get(i).getSaldo() > mayor) {
					mayor = user.getCuentasAhorrosAsociadas().get(i).getSaldo();
					contador = i;
				}

				Movimientos movimiento = new Movimientos(gota, user.getCuentasAhorrosAsociadas().get(contador),
						cantidadPrestamo, Categoria.OTROS, Date.from(Instant.now()));
				user.asociarMovimiento(movimiento);
			}
			return user.getCuentasAhorrosAsociadas().get(contador);
		} else {
			for (int i = 0; i < user.getCuentasCorrienteAsociadas().size(); i++) {
				if (user.getCuentasCorrienteAsociadas().get(i).getCupo() > mayor) {
					mayor = user.getCuentasCorrienteAsociadas().get(i).getCupo();
					contador = i;
				}

				Movimientos movimiento = new Movimientos(gota, user.getCuentasCorrienteAsociadas().get(contador),
						cantidadPrestamo, Categoria.OTROS, Date.from(Instant.now()));
				user.asociarMovimiento(movimiento);
			}
			return user.getCuentasCorrienteAsociadas().get(contador);
		}
	}

	// Implementación métodos abstracto a redefinir
	public abstract void vaciarCuenta(Ahorros gota);
	
	//Implementación de la interfaz Comparable
	public int compareTo(Cuenta cuenta) {
		if (this.getId() > cuenta.getId()) {
			return 1;
		}
		else if(this.getId() < cuenta.getId()) {
			return -1;
		}
		else {
			return 0;
		}
	}
	//Para ordenar cualquier arreglo de tipo Cuenta, se ordenará según el id de la cuenta y se hará con Collections.sort(nombre_lista);

	@Override
	public boolean equals(Object o) {
		if(this.getId() == ((Cuenta) o).getId()){
			return true;
		}else {
			return false;
		}	
	}
	
	@Override	
	protected void finalize() {
		System.out.println("La cuenta " + this.getClass() + " con id: " + this.getId() + " y nombre: " + this.getNombre() + " fue eliminada satisfactoriamente del sistema.");
	}

	public String toString() {
		return "Cuenta: " + this.nombre +
				"\n# " + this.id +
				"\nBanco: " + this.banco +
				"\nDivisa: " + this.divisa;
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
}
