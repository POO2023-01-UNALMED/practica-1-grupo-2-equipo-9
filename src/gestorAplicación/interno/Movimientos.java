package gestorAplicación.interno;

import java.util.Date;
import java.io.Serializable;
import gestorAplicación.externo.Banco;
import gestorAplicación.externo.Divisas;
import java.util.ArrayList;
import java.time.Instant;

public class Movimientos implements Serializable{
	//	Atributos
	public static final String nombreD = "Movimientos";
	private static final long serialVersionUID = 5L;
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
	public static String recomendarFecha;

	//	Constructores
	//	Movimiento entre dos cuentas de ahorros
	public Movimientos(Ahorros origen, Ahorros destino, double cantidad, Categoria categoria, Date fecha) {
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
	
	//	Movimiento a una cuenta de ahorros
	public Movimientos(Ahorros destino, double cantidad, Categoria categoria, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(null);
		destino.setSaldo(destino.getSaldo() + cantidad);
	}
	
	//	Movimiento entre dos cuentas corrientes
	public Movimientos(Corriente origen, Corriente destino, double cantidad, Categoria categoria, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(origen);
		origen.setDisponible(origen.getDisponible() - cantidad);
		destino.setDisponible(destino.getDisponible() + cantidad);
	}
	
	//	Movimiento a una cuenta corriente
	public Movimientos(Corriente destino, double cantidad, Categoria categoria, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(null);
		destino.setDisponible(destino.getDisponible() + cantidad);
	}
	
	//	Movimiento de una cuenta de ahorros a una cuenta corriente
	public Movimientos(Ahorros origen, Corriente destino, double cantidad, Categoria categoria, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(origen);
		origen.setSaldo(origen.getSaldo() - cantidad);
		destino.setDisponible(destino.getDisponible() + cantidad);
	}
	
	//	Movimiento de una cuenta corriente a una cuenta de ahorros
	public Movimientos(Corriente origen, Ahorros destino, double cantidad, Categoria categoria, Date fecha) {
		Movimientos.movimientosTotales.add(this);
		this.setCantidad(cantidad);
		this.setCategoria(categoria);
		this.setFecha(fecha);
		this.setId(Movimientos.getMovimientosTotales().size());
		this.setDestino(destino);
		this.setOrigen(origen);
		origen.setDisponible(origen.getDisponible() - cantidad);
		destino.setSaldo(destino.getSaldo() + cantidad);
	}
	
	//	MÉTODOS
	//	Funcionalidad de Suscripciones de Usuarios
	public static Object crearMovimiento(Ahorros origen, Ahorros destino, double cantidad, Categoria categoria, Date fecha) {
		if(Cuenta.getCuentasTotales().contains(origen) && Cuenta.getCuentasTotales().contains(destino)){
			if (origen.getSaldo() < cantidad) {
				return("¡Saldo Insuficiente! Su cuenta origen tiene un saldo de: " + origen.getSaldo() + " por lo tanto no es posible realizar el movimiento");
			} else {
				return(new Movimientos(origen, destino, cantidad - cantidad * (destino.getBanco().getEstadoAsociado().getTasa_impuestos() +  destino.getBanco().getComision()), categoria, fecha));
			}
		}else {
			return("Debes verificar que las cuentas origen y/o destino existan");
		}
	}
	
	public static Object crearMovimiento(Ahorros destino, double cantidad, Categoria categoria, Date fecha) {
		if(Cuenta.getCuentasTotales().contains(destino)){
			return(new Movimientos(destino, cantidad - cantidad * (destino.getBanco().getEstadoAsociado().getTasa_impuestos() +  destino.getBanco().getComision()), categoria, fecha));
		}else {
			return("Debes verificar que la cuenta de destino exista");
		}
	}

	public static Object crearMovimiento(int origen, int destino, double cantidad, Categoria categoria, Date fecha) {
		Cuenta cuentaOrigen = null;
		Cuenta cuentaDestino = null;
		ArrayList<Cuenta> cuentasTotales = Cuenta.getCuentasTotales();
		for(int i = 0; i < cuentasTotales.size(); i++){
			if(cuentasTotales.get(i).getId() == origen){
				cuentaOrigen = cuentasTotales.get(i);
			}else if(cuentasTotales.get(i).getId() == destino){
				cuentaDestino = cuentasTotales.get(i);
			}
		}
		if(cuentaOrigen != null && cuentaDestino != null){
			if (cuentaOrigen instanceof Ahorros) {
				if (((Ahorros) cuentaOrigen).getSaldo() < cantidad) {
					return ("¡Saldo Insuficiente! Su cuenta origen tiene un saldo de: " + ((Ahorros) cuentaOrigen).getSaldo() + " por lo tanto no es posible realizar el movimiento");
				} 
				else {
					if (cuentaDestino instanceof Ahorros) {
						return (new Movimientos((Ahorros) cuentaOrigen, (Ahorros) cuentaDestino, cantidad, categoria, fecha));
					}
					else {//cuentaDestino será instancia de Corriente
						return (new Movimientos((Ahorros) cuentaOrigen, (Corriente) cuentaDestino, cantidad, categoria, fecha));
					}
				}
			}
			else {//cuentaOrigen será instancia de Corriente
				if (((Corriente) cuentaOrigen).getDisponible() < cantidad) {
					return ("¡Cupo Insuficiente! Su cuenta origen tiene un cupo disponible de: " + ((Corriente) cuentaOrigen).getDisponible() + " por lo tanto no es posible realizar el movimiento");
				} 
				else {
					if (cuentaDestino instanceof Ahorros) {
						return (new Movimientos((Corriente) cuentaOrigen, (Ahorros) cuentaDestino, cantidad, categoria, fecha));
					}
					else {
						return (new Movimientos((Corriente) cuentaOrigen, (Corriente) cuentaDestino, cantidad, categoria, fecha));
					}
				}	
			}
		}
		else {
			return("Debes verificar que las cuentas origen y/o destino existan");
		}
	}
	
	public static Object crearMovimiento(Corriente origen, Corriente destino, double cantidad, Categoria categoria, Date fecha) {
		if(Cuenta.getCuentasTotales().contains(origen) && Cuenta.getCuentasTotales().contains(destino)) {
			if(origen.getDisponible() < cantidad) {
				return("¡Cupo Insuficiente! Su cuenta origen tiene un cupo disponible de: " + origen.getDisponible() + " por lo tanto no es posible realizar el movimiento");
			}
			else {
				return(new Movimientos(origen, destino, cantidad - cantidad * (destino.getBanco().getEstadoAsociado().getTasa_impuestos() + destino.getBanco().getComision()), categoria, fecha));
			}
		}
		else {
			return("Debes verificar que las cuentas origen y/o destino existan");
		}
	}
	
	public static Object crearMovimiento(Corriente destino, double cantidad, Categoria categoria, Date fecha) {
		if(Cuenta.getCuentasTotales().contains(destino)) {
			return(new Movimientos(destino, cantidad - cantidad * (destino.getBanco().getEstadoAsociado().getTasa_impuestos() + destino.getBanco().getComision()), categoria, fecha));
		}
		else {
			return("Debes verificar que la cuenta de destino exista");
		}
	}
	
	//Funcionalidad de Suscripciones de Usuarios
	public static Object modificarSaldo(Ahorros origen, Ahorros destino, double cantidad, Usuario usuario, Categoria categoria) {
		if (usuario.getCuentasAsociadas().contains(origen) && usuario.getCuentasAsociadas().contains(destino)) {
			usuario.setContadorMovimientos(usuario.getContadorMovimientos() + 1);
			return (crearMovimiento(origen, destino, cantidad, categoria, new Date()));

		} else {
			return ("Las cuentas de origen y destino deben estar asociadas al usuario, por favor verifique");
		}
	}

	public String toString() {
		if(this.getOrigen() == null) {
			return("Movimiento creado \nFecha: " + getFecha() + "\nID: " + getId() + "\nDestino: " + getDestino().getId() + "\nCantidad: " +
					getCantidad() + "\nCategoria: " + getCategoria().name());
		}else {
			return("Movimiento creado \nFecha: " + getFecha() + "\nID: " + getId() + "\nOrigen: " + getOrigen().getId() + "\nDestino: " + getDestino().getId() + "\nCantidad: " +
					getCantidad() + "\nCategoria: " + getCategoria().name());
		}
	}

	//	Funcionalidad Prestamos
	public static Boolean realizarPrestamo(Ahorros cuenta,double cantidad){
		Banco banco = cuenta.getBanco();
		Usuario titular = cuenta.getTitular();
		double maxCantidad = banco.getPrestamo()*titular.getSuscripcion().getPorcentajePrestamo();
		//	Comprueba que la cantidad si sea la adecuada
		if(cantidad>maxCantidad){
			return false;
		}else{
			//		Creamos instancia de la clase deuda
			Deuda deuda = new Deuda(cantidad,cuenta,titular,banco);
//		agrega el dinero a la cuenta
			cuenta.setSaldo(cuenta.getSaldo()+cantidad);
			return true;
		}

	}
	
	//Métodos para funcionalidad cambio de divisa
	public void facilitarInformación(Usuario titular, Divisas divisaOrigen, Divisas divisaDevolucion) {
		for (int i = 0; i < titular.getBancosAsociados().size() ; i++) {
			//int totalOrigen=0;
			titular.getBancosAsociados().get(i).setAsociado(true);
		}
		String cadena= divisaOrigen.name() + divisaDevolucion.name();
		ArrayList<Banco> existeCambio = new ArrayList<Banco>();
		for (int j = 0; j < Banco.getBancosTotales().size(); j++) {
			for (int k = 0; j< Banco.getBancosTotales().get(j).getDic().size(); k++ )
				if (cadena.equals(Banco.getBancosTotales().get(j).getDic().get(k))) {
					existeCambio.add(Banco.getBancosTotales().get(j));
			}
		}
	}

	// Funcionalidad asesoramiento de inversión
	public static void analizarCategoria(Usuario u, String plazo) {
		int transporte = 0;
		int comida = 0;
		int educacion = 0;
		int finanzas = 0;
		int otros = 0;
		int regalos = 0;
		int salud = 0;
		int big = 0;
		int posicion = 0;
		ArrayList<Integer> mayor = new ArrayList<Integer>();

		// Buscar la categoría en la que más dinero ha gastado el usuario
		for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
			Categoria categoria = u.getMovimientosAsociados().get(i).getCategoria();
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
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.TRANSPORTE == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 1) {
			nombreCategoria = "Comida";
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.COMIDA == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 2) {
			nombreCategoria = "Educacion";
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.EDUCACION == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 3) {
			nombreCategoria = "Salud";
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.SALUD == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 4) {
			nombreCategoria = "Regalos";
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.REGALOS == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 5) {
			nombreCategoria = "Finanzas";
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.FINANZAS == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}
		}

		else if (posicion == 6) {
			nombreCategoria = "Otros";
			for (int i = 0; i < u.getMovimientosAsociados().size(); i++) {
				if (Categoria.OTROS == u.getMovimientosAsociados().get(i).getCategoria()) {
					cantidadCategoria = cantidadCategoria + u.getMovimientosAsociados().get(i).getCantidad();
				}
			}

		}

		// Recomendadar fecha
		if (plazo == "Corto") {
			if (u.getMovimientosAsociados().get(u.getMovimientosAsociados().size() - 1).getFecha()
					.compareTo(Metas.revisionMetas(u).getFecha()) < 0) {
				recomendarFecha = "01/01/2024";
			} else {
				recomendarFecha = "01/06/2025";
			}
		} else if (plazo == "Mediano") {
			if (u.getMovimientosAsociados().get(u.getMovimientosAsociados().size() - 1).getFecha()
					.compareTo(Metas.revisionMetas(u).getFecha()) < 0) {
				recomendarFecha = "01/01/2026";
			} else {
				recomendarFecha = "01/06/2027";
			}
		} else if (plazo == "Largo") {
			if (u.getMovimientosAsociados().get(u.getMovimientosAsociados().size() - 1).getFecha()
					.compareTo(Metas.revisionMetas(u).getFecha()) < 0) {
				recomendarFecha = "01/01/2028";
			} else {
				recomendarFecha = "01/06/2029";
			}
		} else {
			recomendarFecha = null;
		}
	}

	public boolean impuestosMovimiento(double interes) {

		// Los movimientos aparecen sin uso, pero en realidad el uso que se les da es la
		// transacción, porque modifica el saldo de las cuentas y luego ese saldo es
		// usado como condicion para otro método

		if (this.getOrigen().getBanco() == this.getDestino().getBanco()) {
			if (this.getOrigen() instanceof Corriente) {
				Ahorros impuestosBanco = new Ahorros(this.getOrigen().getBanco(), 1234, Divisas.COP, "Ahorros", 10.0);
				Movimientos movimiento1 = new Movimientos((Corriente) this.getOrigen(), impuestosBanco, interes,
						Categoria.OTROS, Date.from(Instant.now()));
				Movimientos movimiento2 = new Movimientos((Corriente) this.getDestino(), impuestosBanco, interes,
						Categoria.OTROS, Date.from(Instant.now()));
				this.getOrigen().getTitular().asociarMovimiento(movimiento1);
				this.getOrigen().getTitular().asociarMovimiento(movimiento2);
				this.getDestino().getTitular().asociarMovimiento(movimiento1);
				this.getDestino().getTitular().asociarMovimiento(movimiento2);
			} else if (this.getOrigen() instanceof Ahorros) {
				Ahorros impuestosBanco = new Ahorros(this.getOrigen().getBanco(), 1234, Divisas.COP, "Ahorros", 10.0);
				Movimientos movimiento1 = new Movimientos((Ahorros) this.getOrigen(), impuestosBanco, interes,
						Categoria.OTROS, Date.from(Instant.now()));
				Movimientos movimiento2 = new Movimientos((Ahorros) this.getDestino(), impuestosBanco, interes,
						Categoria.OTROS, Date.from(Instant.now()));
				this.getOrigen().getTitular().asociarMovimiento(movimiento1);
				this.getOrigen().getTitular().asociarMovimiento(movimiento2);
				this.getDestino().getTitular().asociarMovimiento(movimiento1);
				this.getDestino().getTitular().asociarMovimiento(movimiento2);
			}
			return true;
		} else {
			if (this.getOrigen() instanceof Corriente) {
				Ahorros impuestosBanco = new Ahorros(this.getOrigen().getBanco(), 1234, Divisas.COP, "Ahorros", 10.0);
				Movimientos movimiento1 = new Movimientos((Corriente) this.getOrigen(), impuestosBanco, interes + 1,
						Categoria.OTROS, Date.from(Instant.now()));
				Movimientos movimiento2 = new Movimientos((Corriente) this.getDestino(), impuestosBanco, interes + 1,
						Categoria.OTROS, Date.from(Instant.now()));
				this.getOrigen().getTitular().asociarMovimiento(movimiento1);
				this.getOrigen().getTitular().asociarMovimiento(movimiento2);
				this.getDestino().getTitular().asociarMovimiento(movimiento1);
				this.getDestino().getTitular().asociarMovimiento(movimiento2);
			} else if (this.getOrigen() instanceof Ahorros) {
				Ahorros impuestosBanco = new Ahorros(this.getOrigen().getBanco(), 1234, Divisas.COP, "Ahorros", 10.0);
				Movimientos movimiento1 = new Movimientos((Ahorros) this.getOrigen(), impuestosBanco, interes + 1,
						Categoria.OTROS, Date.from(Instant.now()));
				Movimientos movimiento2 = new Movimientos((Ahorros) this.getDestino(), impuestosBanco, interes + 1,
						Categoria.OTROS, Date.from(Instant.now()));
				this.getOrigen().getTitular().asociarMovimiento(movimiento1);
				this.getOrigen().getTitular().asociarMovimiento(movimiento2);
				this.getDestino().getTitular().asociarMovimiento(movimiento1);
				this.getDestino().getTitular().asociarMovimiento(movimiento2);
			}
			return false;
		}
	}
	
	//Funcionalidad Compra de Cartera
	//Método que retorna un arreglo con los movimientos que salgan de una cuenta específica
	public static ArrayList<Movimientos> verificarOrigenMovimientos(ArrayList<Movimientos> movimientosAsociados, Cuenta cuenta){
		ArrayList<Movimientos> movimientosOriginariosCuenta = new ArrayList<Movimientos>();
		for (Movimientos movimiento: movimientosAsociados) {
			if(movimiento.origen == cuenta) {
				movimientosOriginariosCuenta.add(movimiento);
			}
		}
		return movimientosOriginariosCuenta;
	}
	
	//Método que retorna un arreglo con los movimientos que entren a una cuenta específica
	public static ArrayList<Movimientos> verificarDestinoMovimientos(ArrayList<Movimientos> movimientosAsociados, Cuenta cuenta){
		ArrayList<Movimientos> movimientosDestinoCuenta = new ArrayList<Movimientos>();
		for (Movimientos movimiento: movimientosAsociados) {
			if(movimiento.destino == cuenta) {
				movimientosDestinoCuenta.add(movimiento);
			}
		}
		return movimientosDestinoCuenta;
	}
	
	public static ArrayList<Movimientos> verificarMovimientosUsuario_Banco(Usuario usuario, Banco banco){
		ArrayList<Movimientos> movimientosAsociados = usuario.getMovimientosAsociados();
		ArrayList<Cuenta> cuentasAsociadas = usuario.getCuentasAsociadas();
		ArrayList<Cuenta> cuentasAsociadasaBanco = new ArrayList<Cuenta>();
		ArrayList<Movimientos> movimientosUsuario_Banco = new ArrayList<Movimientos>();
		for(Cuenta cuenta: cuentasAsociadas) {
			if(cuenta.getBanco() == banco) {
				cuentasAsociadasaBanco.add(cuenta);
			}
		}
		for(Cuenta cuenta: cuentasAsociadasaBanco) {
			ArrayList<Movimientos> movimientosAux = Movimientos.verificarOrigenMovimientos(movimientosAsociados, cuenta);
			for (Movimientos movimiento: movimientosAux) {
				movimientosUsuario_Banco.add(movimiento);
			}
		}
		return movimientosUsuario_Banco;
		
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