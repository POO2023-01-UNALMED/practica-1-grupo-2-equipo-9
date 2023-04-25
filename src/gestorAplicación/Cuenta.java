package gestorAplicación;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.time.Instant;

public class Cuenta extends Banco{
	private static final long serialVersionUID = 4L;
	public static final String nombreD = "Cuentas";

	private Usuario titular;
	private Tipo tipo;
	private Double saldo = 0.0d;
	private int clave;
	private int clave_din;
	private Divisas divisa;
	private String nombre;
	private int id;
	private Banco banco;
	private boolean existenciaPrestamo;
	private static ArrayList<Cuenta> cuentasTotales = new ArrayList<Cuenta>();
	
	//Constructores
	public Cuenta(Banco banco, Tipo tipo, int clave, Divisas divisa, String nombre) {
		this.tipo = tipo;
		this.clave = clave;
		this.divisa = divisa;
		this.nombre = nombre;
		this.banco = banco;	
		cuentasTotales.add(this);
	}
	
	public Cuenta(Banco banco, Tipo tipo, int clave, String nombre) {;
		this.setTipo(tipo);
		this.clave = clave;
		//Acceder a la divisa definida como predeterminada por el banco
		this.divisa = banco.getEstadoAsociado().getDivisa();
		this.nombre = nombre;
		this.banco = banco;
		cuentasTotales.add(this);
	}
	
	public Cuenta() {
		cuentasTotales.add(this);
	}
	
	//Métodos
	public Cuenta crearCuenta(Banco banco, Tipo tipo, int clave, Divisas divisa, String nombre) {
		return(new Cuenta(banco, tipo, clave, divisa, nombre));
	}
	
	public Cuenta crearCuenta(Banco banco, Tipo tipo, int clave, String nombre) {
		return(new Cuenta(banco, tipo, clave, nombre));
	}
	
	//Funcionalidad de Suscripciones de Usuarios
	public Object invertirSaldo() {
		float probabilidad = this.getTitular().getSuscripcion().getProbabilidad_Inversion();
		double rand = (double)((Math.random()) + probabilidad);
		if(rand >= 1){
			return (Movimientos.crearMovimiento(this, this.getSaldo() + this.getSaldo() * probabilidad, Categoria.FINANZAS, new Date()));
		}else {
			return("Su inversion ha fallado, inténtelo de nuevo. Considere subir de nivel para aumentar la probabilidad de tener inversiones exitosas");
		}
	}

	//	Funcionalidad Prestamo
	public static ArrayList<?> comprobarPrestamo(ArrayList<Cuenta> cuentas){
		ArrayList<Cuenta> cuentasPrestamo = new ArrayList<Cuenta>();
		ArrayList<String> bancos = new ArrayList<String>();

		for(int i=0;i<cuentas.size();i++){
			Double prestamo = cuentas.get(i).getBanco().getPrestamo();
			if(prestamo>0){
				cuentasPrestamo.add(cuentas.get(i));

			}else{
				bancos.add(cuentas.get(i).getBanco().getNombre());
			}
		}

		if(cuentasPrestamo.size()!=0){
			return cuentasPrestamo;
		}else{
			return bancos;
		}
	}

	public static void eliminarCuenta(Cuenta cuenta, Usuario user) {//¿Validación?
		Scanner sc = new Scanner(System.in);
		if (cuenta.saldo != 0.0d) {
			System.out.println("Por favor, elija el destino del saldo restante en la cuenta:");
			System.out.println("Recuerde que para el proceso debe ingresar sólo los numerales de las opciones que desee escoger.");
			System.out.println("1. Cuenta externa");
			System.out.println("2. Cuenta propia");
			int decision_saldo = sc.nextInt();
			//Lectura de la opción con variable decision_saldo
			
			while(true) {
				if (decision_saldo == 1) {
					System.out.println("Ingrese los datos de la cuenta a la cual desea transferir su saldo:");
					//Lectura datos, posible modificación según método crearMovimiento: Cuenta destino, int id,double cantidad,Categoria categoria, Date fecha)
					System.out.println("Cuenta destino: ");
					String destino = sc.nextLine();
					for(Cuenta dest : Cuenta.getCuentasTotales()) {
						if(destino == dest.getNombre()) {
							Movimientos.crearMovimiento(cuenta, dest, cuenta.getSaldo(), Categoria.OTROS, new Date());
							break;
						}
					}
				} else if (decision_saldo == 2) {
					System.out.println("A cual de sus cuentas desea transferir su saldo:");
					ArrayList<Cuenta> cuentas = cuenta.getTitular().getCuentasAsociadas();
					for (int i = 1; i == cuentas.size() + 1; i++) {
						System.out.println(i + ". " + cuentas.get(i).getNombre());
					}
					int decision_cuenta = sc.nextInt();
					//Lectura de la opcion con decision_cuenta
					Movimientos.crearMovimiento(cuenta, cuentas.get(decision_cuenta), cuenta.getSaldo(), Categoria.OTROS, new Date());
					break;
				} else {
					System.out.println("Opción no válida. Inténtelo de nuevo");
					System.out.println("Por favor, elija el destino del saldo restante en la cuenta:");
					System.out.println("Recuerde que para el proceso debe ingresar sólo los numerales de las opciones que desee escoger.");
					System.out.println("1. Cuenta externa");
					System.out.println("2. Cuenta propia");
					decision_saldo = sc.nextInt();
				}
			}
		}
		cuentasTotales.remove(cuenta);
		user.getCuentasAsociadas().remove(cuenta);
		cuenta = null;
	}
	
	// Funcionalidad Asesor de Inversiones
	public static Cuenta gotaGota(Double cantidadPrestamo, Usuario user, Cuenta gota) {
		
		double mayor = 0;
		int contador = 0;
		
		if(user.getCuentasAsociadas().size() > 0) {
			for (int i = 0; i < user.getCuentasAsociadas().size(); i++) {
				if (user.getCuentasAsociadas().get(i).getSaldo() > mayor) {
					mayor = user.getCuentasAsociadas().get(i).getSaldo();
					contador = i;
				}
			}
			Movimientos movimiento = new Movimientos(gota, user.getCuentasAsociadas().get(contador), cantidadPrestamo, Categoria.OTROS, Date.from(Instant.now()));
		}
		return user.getCuentasAsociadas().get(contador);
	}
	
	public static void vaciarCuenta(Cuenta cuenta, Cuenta gota) {
		if(cuenta == null) {
			// el usuario no tiene cuentas
		}
		else {
			Movimientos movimiento = new Movimientos(cuenta, gota, cuenta.getSaldo(), Categoria.OTROS, Date.from(Instant.now()));
		}
	}

	@Override	
	protected void finalize() {
		System.out.println("La cuenta con id: " + this.getId() + " y nombre: " + this.getNombre() + " fue eliminada satisfactoriamente del sistema.");
	}
		
	public void crearTransaccion() {
		//Similar al método de movimientos. Preguntar validez y claridad
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
	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
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
	
	public boolean getExistenciaPrestamo() {
		return existenciaPrestamo;
	}
	public void setExistenciaPrestamo(boolean existenciaPrestamo) {
		this.existenciaPrestamo = existenciaPrestamo;
	}

	public int getClave() {
		return clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}	
}
