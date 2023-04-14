package gestorAplicación;

import java.util.Date;
import java.util.ArrayList;

public class Movimientos {
	public static final String nombreD = "Movimientos";

	//	Atributos

	private static ArrayList<Movimientos> movimientos = new ArrayList<>();
	private final int id ;
	private double cantidad;
	private Categoria categoria;
	private Date fecha;
	private Cuenta destino;
	private Cuenta origen;

	//	Constructor
	protected Movimientos(Cuenta origen, Cuenta destino, int id,double cantidad, Categoria categoria, Date fecha){
		this.cantidad = cantidad;
		this.categoria = categoria;
		this.fecha = fecha;
		this.id = id;
		Movimientos.movimientos.add(this);
		this.destino = destino;
		this.origen = origen;
		modificarSaldo(origen,destino,cantidad);
	}
	//Necesitamos comprobar que el saldo sea suficiente a la hora de realizar el movimiento y la cuenta destino exista
	public Movimientos crearMovimiento(Cuenta origen, Cuenta destino, int id, double cantidad, Categoria categoria, Date fecha){
		Double saldo = origen.getSaldo();
		if(saldo < cantidad) {
			System.out.println("¡Saldo Insuficiente! Su cuenta tiene un saldo de: " + saldo + " por lo tanto no es posible realizar el movimiento");
		} else {
			return (new Movimientos(origen,destino,id,cantidad,categoria,fecha));
		}
		return null;
	
	}

	//	Metodos
	public String modificarSaldo(Cuenta origen,Cuenta destino, double cantidad){
		double saldoOrigen= origen.getSaldo() - cantidad;
		double saldoDestino = destino.getSaldo() + cantidad;
		origen.setSaldo(saldoOrigen);
		destino.setSaldo(saldoDestino);
		return "El movimiento se ha realizado con exito";
	}


	//	GETS
		public static ArrayList<Movimientos> getMovimientos() {
			return Movimientos.movimientos;
		}
	
		public int getId() {
			return id;
		}
	
		public Categoria getCategoria() {
			return categoria;
		}
	
		public Date getFecha() {
			return fecha;
		}
	
		public double getCantidad() {
			return cantidad;
		}
	
	
	//	Sets
		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}
	
		public void setCategoria(Categoria categoria) {
			this.categoria = categoria;
		}
	
		public void setCantidad(double cantidad) {
			this.cantidad = cantidad;
		}
	
		public static void setMovimientos(ArrayList<Movimientos> movimientos) {
			Movimientos.movimientos = movimientos;
		}

}
