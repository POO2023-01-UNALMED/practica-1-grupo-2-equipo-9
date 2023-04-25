package gestorAplicación;

import java.util.Date;
import java.util.ArrayList;
import gestorAplicación.Cuenta;

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
	
	// Funcionalidad de Asesor Inversiones
	private Usuario owner;
	public static String nombreCategoria;
	public static double cantidadCategoria;

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

	public static Object crearMovimiento(int origen, int destino, double cantidad, Categoria categoria, Date fecha) {
		Cuenta cuentaOrigen = null;
		Cuenta cuentaDestino = null;
		ArrayList<Cuenta> cuentasTotales = Cuenta.getCuentasTotales();
		for(int i =0;i<cuentasTotales.size();i++){
			if(cuentasTotales.get(i).getId() == origen){
				cuentaOrigen = cuentasTotales.get(i);
			}else if(cuentasTotales.get(i).getId() == destino){
				cuentaDestino = cuentasTotales.get(i);
			}
		}

		if(cuentaOrigen != null && cuentaDestino != null){
			if (cuentaOrigen.getSaldo() < cantidad) {
				return ("¡Saldo Insuficiente! Su cuenta origen tiene un saldo de: " + cuentaOrigen.getSaldo() + " por lo tanto no es posible realizar el movimiento");
			} else {
				return (new Movimientos(cuentaOrigen, cuentaDestino, cantidad, categoria, fecha));
			}
		}else {
			return("Debes verificar que las cuentas origen y/o destino existan");
		}
	}


	public String toString() {
		return("Movimiento creado\nFecha:"+getFecha()+"\nID:"+getId()+"\nOrigen:"+getOrigen().getId()+"\nDestino:"+getDestino().getId()+"\nCantidad:"+
				getCantidad()+"\nCategoria:"+getCategoria().name());
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
	//Métodos para funcionalidad cambio de divisa
	public void facilitarInformación(Usuario titular, Divisas divisaOrigen, Divisas divisaDevolucion) {
		for (int i = 0; i < titular.getBancosAsociados().size() ; i++) {
			//int totalOrigen=0;
			titular.getBancosAsociados().get(i).setAsociado(true);
		}
		String cadena= divisaOrigen.name() + divisaDevolucion.name();
		for (int j = 0; j < Banco.getBancosTotales().size(); j++) {
			for (int k = 0; j< Banco.getBancosTotales().get(j).getDic().size(); k++ )
				if (cadena.equals(Banco.getBancosTotales().get(j).getDic().get(k))) {
					
			}
		}
	}
	
	// METODOS PARA LA FUNCIONALIDAD DE ASESORAMIENTO DE INVERSION
	public static String analizarCategoria(Usuario u) {
		int transporte = 0;
		int comida = 0;
		int educacion = 0;
		int finanzas = 0;
		int otros = 0;
		int regalos = 0;
		int salud = 0;

		// Buscar la categoría en la que más dinero ha gastado el usuario
		for (int i = 0; i < u.getMovimientosAsociadas().size(); i++) {
			Categoria categoria = u.getMovimientosAsociadas().get(i).getCategoria();
			if (categoria == Categoria.TRANSPORTE) {
				transporte++;
			} else if (categoria == Categoria.COMIDA) {
				comida++;
			} else if (categoria == Categoria.EDUCACION) {
				educacion++;
			} else if (categoria == Categoria.SALUD) {
				salud++;
			} else if (categoria == Categoria.REGALOS) {
				regalos++;
			} else if (categoria == Categoria.FINANZAS) {
				finanzas++;
			} else if (categoria == Categoria.OTROS) {
				otros++;
			}
		}

		int big = 0;
		int posicion = 0;
		ArrayList<Integer> mayor = new ArrayList<Integer>();

		mayor.add(transporte);
		mayor.add(comida);
		mayor.add(educacion);
		mayor.add(salud);
		mayor.add(regalos);
		mayor.add(finanzas);
		mayor.add(otros);

		for (int e = 0; e < mayor.size(); e++) {
			if (mayor.get(e) > big) {
				big = mayor.get(e);
				posicion = e;
			}
		}

		if (posicion == 0) {
			nombreCategoria = "Transporte";
			for (int i = 0; i < u.getMovimientosAsociadas().size(); i++) {
				if (Categoria.TRANSPORTE == u.getMovimientosAsociadas().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociadas().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 1) {
			nombreCategoria = "Comida";
			for (int i = 0; i < u.getMovimientosAsociadas().size(); i++) {
				if (Categoria.COMIDA == u.getMovimientosAsociadas().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociadas().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 2) {
			nombreCategoria = "Educacion";
			for (int i = 0; i < u.getMovimientosAsociadas().size(); i++) {
				if (Categoria.EDUCACION == u.getMovimientosAsociadas().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociadas().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 3) {
			nombreCategoria = "Salud";
			for (int i = 0; i < u.getMovimientosAsociadas().size(); i++) {
				if (Categoria.SALUD == u.getMovimientosAsociadas().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociadas().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 4) {
			nombreCategoria = "Regalos";
			for (int i = 0; i < u.getMovimientosAsociadas().size(); i++) {
				if (Categoria.REGALOS == u.getMovimientosAsociadas().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociadas().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 5) {
			nombreCategoria = "Finanzas";
			for (int i = 0; i < u.getMovimientosAsociadas().size(); i++) {
				if (Categoria.FINANZAS == u.getMovimientosAsociadas().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociadas().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 6) {
			nombreCategoria = "Otros";
			for (int i = 0; i < u.getMovimientosAsociadas().size(); i++) {
				if (Categoria.OTROS == u.getMovimientosAsociadas().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociadas().get(i).getCantidad();
				}
			}

		}
		return "La categoría en la que más dinero ha gastado es en: " + nombreCategoria + " que suma un total de "
				+ cantidadCategoria + ".";
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
	
	public Usuario getOwner() {
		return owner;
	}

	public void setOwner(Usuario owner) {
		this.owner = owner;
	}
}