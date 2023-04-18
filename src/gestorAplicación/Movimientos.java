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
	public Movimientos(Cuenta origen, Cuenta destino, Usuario usuario, int id, double cantidad, Categoria categoria, Date fecha){
		this.cantidad = cantidad;
		this.categoria = categoria;
		this.fecha = fecha;
		this.id = id;
		Movimientos.movimientos.add(this);
		this.setDestino(destino);
		this.setOrigen(origen);
	}
	//Necesitamos comprobar que el saldo sea suficiente a la hora de realizar el movimiento y la cuenta destino exista
	public static String crearMovimiento(Cuenta origen, Cuenta destino, Usuario usuario, int id, double cantidad, Categoria categoria, Date fecha){
		if(origen.getSaldo() < cantidad) {
			return("¡Saldo Insuficiente! Su cuenta origen tiene un saldo de: " + origen.getSaldo() + " por lo tanto no es posible realizar el movimiento");
		} else {
			return(new Movimientos(origen,destino, usuario, id, cantidad, categoria, fecha).modificarSaldo(origen, destino, cantidad, usuario));
		}
	}

	//Métodos
	//Funcionalidad de Suscripciones de Usuarios
	public String modificarSaldo(Cuenta origen, Cuenta destino, double cantidad, Usuario usuario){
		 if(usuario.getBancosAsociados().contains(origen.getBanco()) && usuario.getBancosAsociados().contains(destino.getBanco())) {
			 usuario.setContadorMovimientos(usuario.getContadorMovimientos() + 1);
			 usuario.verificarContadorMovimientos();
			 origen.setSaldo(origen.getSaldo() - cantidad);
			 destino.setSaldo(destino.getSaldo() + cantidad);
			 return ("El movimiento se ha realizado con exito");
			 
		 }else {
			 return("Las cuentas de origen y destino deben estar asociadas al usuario, por favor verifique");
		 }
		
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
		
		public Cuenta getOrigen() {
			return origen;
		}
		
		public Cuenta getDestino() {
			return destino;
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
		
		public void setDestino(Cuenta destino) {
			this.destino = destino;
		}

		public void setOrigen(Cuenta origen) {
			this.origen = origen;
		}
}
