package gestorAplicación;

import java.util.Date;
import java.util.ArrayList;

public class Movimientos {
	public static final String nombreD = "Movimientos";

	//	Atributos
	private static ArrayList<Movimientos> movimientosTotales = new ArrayList<>();
	private int id;
	private double cantidad;
	private Categoria categoria;
	private Date fecha;
	private Cuenta destino;
	private Cuenta origen;

	//	Constructores
	public Movimientos(Cuenta origen, Cuenta destino, double cantidad, Categoria categoria, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(origen);
		origen.setSaldo(origen.getSaldo() - cantidad);
		destino.setSaldo(destino.getSaldo() + cantidad);
	}
	
	public Movimientos(Cuenta destino, double cantidad, Categoria categoria, Date fecha) {
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(new Cuenta());
		destino.setSaldo(destino.getSaldo() + cantidad);
	}

	//Funcionalidad de Suscripciones de Usuarios
	public static Object crearMovimiento(Cuenta origen, Cuenta destino, double cantidad, Categoria categoria, Date fecha) {
		if(Cuenta.getCuentasTotales().contains(origen) && Cuenta.getCuentasTotales().contains(destino)){
			if (origen.getSaldo() < cantidad) {
				return ("¡Saldo Insuficiente! Su cuenta origen tiene un saldo de: " + origen.getSaldo() + " por lo tanto no es posible realizar el movimiento");
			} else {
				return (new Movimientos(origen, destino, cantidad, categoria, fecha));
			}
		}else {
			return("Debes verificar que las cuentas origen y/o destino existan");
		}
	}
	
	public static Object crearMovimiento(Cuenta destino, double cantidad, Categoria categoria, Date fecha) {
		if(Cuenta.getCuentasTotales().contains(destino)){
			return (new Movimientos(destino, cantidad, categoria, fecha));
		}else {
			return("Debes verificar que las cuenta de destino exista");
		}
	}

	//Métodos
	//Funcionalidad de Suscripciones de Usuarios
	/*public Object modificarSaldo(Cuenta origen, Cuenta destino, double cantidad, Usuario usuario, Categoria categoria) {
		if (usuario.getBancosAsociados().contains(origen.getBanco()) && usuario.getBancosAsociados().contains(destino.getBanco())) {
			usuario.setContadorMovimientos(usuario.getContadorMovimientos() + 1);
			usuario.verificarContadorMovimientos();
			return (crearMovimiento(origen, destino, cantidad, categoria, new Date()));

		} else {
			return ("Las cuentas de origen y destino deben estar asociadas al usuario, por favor verifique");
		}

	}*/

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



	//	GETS
	public static ArrayList<Movimientos> getMovimientosTotales() {
		return Movimientos.movimientosTotales;
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

	public static void setMovimientosTotales(ArrayList<Movimientos> movimientos) {
		Movimientos.movimientosTotales = movimientos;
	}

	public void setDestino(Cuenta destino) {
		this.destino = destino;
	}
	public void setId(int id) {
		this.id = id;
	}

	public void setOrigen(Cuenta origen) {
		this.origen = origen;
	}
}