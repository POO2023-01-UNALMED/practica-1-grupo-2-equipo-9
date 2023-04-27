package gestorAplicación.externo;

import java.util.ArrayList;
import java.time.Instant;
import java.util.Date;
import java.

import gestorAplicación.interno.Categoria;
import gestorAplicación.interno.Corriente;
import gestorAplicación.interno.Cuenta;
import gestorAplicación.interno.Movimientos;
import gestorAplicación.interno.Suscripcion;
import gestorAplicación.interno.Usuario;
import gestorAplicación.interno.Ahorros;
import gestorAplicación.interno.Corriente;

public class Banco extends Estado {
	private static final long serialVersionUID = 2L;
	private static ArrayList<Banco> bancosTotales = new ArrayList<Banco>();
	public static final String nombreD = "Bancos";
	private String nombre;
	private double comision;
	private Divisas divisa;
	private Estado estadoAsociado;
	private double prestamo;
	private boolean asociado = false;
	private ArrayList<String> dic = new ArrayList<String>();
	private ArrayList<Double> cionario = new ArrayList<Double>();
	
	//Funcionalidad Compra de Cartera
	private double desc_suscripcion = 0.2;
	private double desc_movimientos_porcentaje = 0.2;
	private int desc_movimientos_cantidad = 5;
	
	//Constructor
	
	public Banco(String nombre, double comision, Estado estado) {
		this.nombre = nombre;
		this.setEstadoAsociado(estado);
		this.comision = comision + this.getTasa_impuestos();
		bancosTotales.add(this);
	}
	public Banco() {}
	
	//Métodos
	
	public String mostrarBancosTotales() {
		if(Banco.bancosTotales.size() != 0) { 
			for (int i = 0; i < Banco.bancosTotales.size();) { 
				return(i + 1 + ". " + Banco.bancosTotales.get(i).getNombre()); 
				}
		}else { 
			return("No hay bancos en este momento, considere asociar bancos"); 
			}
		return ("");
	}
	
	public String mostrarDic(int k) {
		String clave= getDic().get(k);
		return clave;
	}
	
	public String mostrarDic() {
		
		return"";
	}
	
	//Funcionalidad de Suscripciones de Usuarios
	public Object comprobarSuscripción(Usuario usuario) {
		for(Usuario u : Usuario.getUsuariosTotales()) {
			if(usuario.getId() == u.getId()) {
				usuario.setLimiteCuentas(usuario.getSuscripcion().getLimiteCuentas());
				switch(usuario.getSuscripcion()) {
					case DIAMANTE:
						this.setComision(this.getComision() * 0.50);
						return ("Bienvenido " + usuario.getNombre() + ", eres un cliente " + usuario.getSuscripcion().name() + " de nuestro banco, "
								+ "por eso te cobramos " + (this.getComision() * 0.50) + " de comision");
					case ORO:
						this.setComision(this.getComision() * 0.65);
						return ("Bienvenido " + usuario.getNombre() + ", eres un cliente " + usuario.getSuscripcion().name() + " de nuestro banco, "
								+ "por eso te cobramos " + this.getComision() * 0.65 + " de comision");
					case PLATA:
						this.setComision(this.getComision() * 0.85);
						return ("Bienvenido " + usuario.getNombre() + ", eres un cliente " + usuario.getSuscripcion().name() + " de nuestro banco, "
								+ "por eso te cobramos " + this.getComision() * 0.85 + " de comision");
					case BRONCE:
						this.setComision(this.getComision());
						return ("Bienvenido " + usuario.getNombre() + ", eres un cliente " + usuario.getSuscripcion().name() + " de nuestro banco, "
								+ "por eso te cobramos " + this.getComision() + " de comision");	
					default:
						return ("No encontramos tu grado de suscripción, considera registrarte en nuestro banco.");
				}
			}
		} return ("No encontramos tu ID registrado en este banco, considera registrarte en nuestro banco.");
	}
	
	// Método funcionalidad Asesoramiento de inversiones
	public static Integer retornoPortafolio(int riesgo, double invertir, String plazo, Usuario user) {

		double interes = Math.random() + riesgo;
		if (user.getCuentasAhorrosAsociadas().get(0).getSaldo() > invertir) {
			Ahorros cuenta = new Ahorros(user.getBancosAsociados().get(riesgo - 1), 1234, Divisas.COP, "Ahorros", 10.0);
	
			double x = user.getCuentasAhorrosAsociadas().get(0).getSaldo();
			double cobro = x * interes - x;
			Movimientos movimiento = new Movimientos(cuenta, user.getCuentasAhorrosAsociadas().get(0), cobro,
					Categoria.OTROS, Date.from(Instant.now()));
	
			if (user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
				int n = (int) Math.round(interes);
				return n;
			} else {
				int n = (int) Math.round(interes - 2);
				return n;
			}

	}

	else if (user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
		Ahorros cuenta = new Ahorros(user.getBancosAsociados().get(user.getBancosAsociados().size() - 1), 1234,
				Divisas.COP, "Ahorros", 1000.0);

		double x = user.getCuentasAhorrosAsociadas().get(0).getSaldo();
		double cobro = x * interes - x;
		Movimientos movimiento = new Movimientos(cuenta, user.getCuentasAhorrosAsociadas().get(0), cobro,
				Categoria.OTROS, Date.from(Instant.now()));

		if (user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
			int n = (int) Math.round(interes + 2);
			return n;
		} else {
			int n = (int) Math.round(interes + 4);
			return n;
		}
	}

	else if (user.getCuentasCorrienteAsociadas().get(0).getCupo() > invertir) {
		Corriente cuenta = new Corriente(user.getBancosAsociados().get(user.getBancosAsociados().size() - 1), 1234, Divisas.COP, "Corriente");

		double x = user.getCuentasCorrienteAsociadas().get(0).getCupo();
		double cobro = x * interes - x;
		Movimientos movimiento = new Movimientos(cuenta, user.getCuentasCorrienteAsociadas().get(0), cobro,
				Categoria.OTROS, Date.from(Instant.now()));

		if (user.getCuentasCorrienteAsociadas().get(0).getCupo() < invertir) {
			int n = (int) Math.round(interes);
			return n;
		} else {
			int n = (int) Math.round(interes - 2);
			return n;
		}

	}

	else {
		Corriente cuenta = new Corriente(user.getBancosAsociados().get(user.getBancosAsociados().size() - 1), 1234, Divisas.COP, "Corriente");

		double x = user.getCuentasCorrienteAsociadas().get(0).getCupo();
		double cobro = x * interes - x;
		Movimientos movimiento = new Movimientos(cuenta, user.getCuentasCorrienteAsociadas().get(0), cobro, Categoria.OTROS,
				Date.from(Instant.now()));

		if (user.getCuentasCorrienteAsociadas().get(0).getCupo() < invertir) {
			int n = (int) Math.round(interes + 2);
			return n;
		} else {
			int n = (int) Math.round(interes + 4);
			return n;
		}
	}
}
	
	public static String bancoPortafolio(int riesgo, Usuario user) {
		double interes = Math.random() + riesgo;
		String asociado = null;
		
		if (user.getBancosAsociados().size() >= riesgo) {
			asociado = "El banco asociado a este portafolio es: " + 
					user.getBancosAsociados().get(riesgo-1).getNombre() + 
					" con una taza de interes del: " + Math.round(interes*100.0)/100.0 + "%";
		}
		
		else {
			Banco banco = new Banco("Banco de inversiones", riesgo, Estado.getEstadosTotales().get(0));
			user.asociarBanco(banco);
			asociado = "El banco asociado a este portafolio es: " + 
					user.getBancosAsociados().get(user.getBancosAsociados().size()-1).getNombre() + 
					" con una taza de interes del: " + Math.round(interes*100.0)/100.0 + "%";
		}
		return asociado;
	}
	
	public static Double verificarTasasdeInteres(Suscripcion suscripcion, Corriente cuenta){
		Double interes = 0.0;
		double descuento_movimientos = cuenta.getBanco().retornarDescuentosMovimientos(cuenta);
		double[] descuento_suscripcion = cuenta.getBanco().retornarDescuentosSuscripcion();
		double[] descuento_total = Banco.descuentoTotal(descuento_movimientos, descuento_suscripcion);
		switch(suscripcion) {
			case DIAMANTE:
				interes = cuenta.getBanco().getInteres_bancario_corriente() - descuento_total[3];
			case ORO:
				interes = cuenta.getBanco().getInteres_bancario_corriente() - descuento_total[2];
			case PLATA:
				interes = cuenta.getBanco().getInteres_bancario_corriente() - descuento_total[1];
			case BRONCE:
				interes = cuenta.getBanco().getInteres_bancario_corriente() - descuento_total[0];
		}
		return interes;
	}
	
	private double[] retornarDescuentosSuscripcion() {
		double[] descuento;
		for(int i = 1; i < 5; i++) {
			descuento[i - 1] = this.desc_suscripcion * i;
		}
		return descuento;
	}
	
	//Método para recibir el descuento ocasionado por la cantidad de movimientos
	private double retornarDescuentosMovimientos(Corriente cuenta) {
		//Atributo que almacena todos los movimientos que están asociados del usuario con un Banco
		ArrayList<Movimientos> movimientosOriginariosconBanco = Movimientos.verificarMovimientosUsuario_Banco(cuenta.getTitular(), cuenta.getBanco());
		double descuento = Math.floorDiv(movimientosOriginariosconBanco.size(), this.desc_movimientos_cantidad) * this.desc_movimientos_porcentaje;
		return descuento;
	}
	
	public static ArrayList<Double> verificarTasasdeInteres(Usuario usuario, ArrayList<Corriente> cuentas){
		ArrayList<Double> tasasdeInteres = new ArrayList<Double>();
		Suscripcion suscripcion = usuario.getSuscripcion();
		for (Cuenta cuenta : cuentas) {
			Double interes = verificarTasasdeInteres(suscripcion, (Corriente) cuenta);
			tasasdeInteres.add(interes);
		}
		return tasasdeInteres;
	}
	
	public static double[] descuentoTotal(double movimientos, double[] suscripcion) {
		double[] descuento_total = suscripcion;
		for (double descuento: descuento_total) {
			descuento = descuento + movimientos;
		}
		return descuento_total;
	}
	
	//Gets
	public double getComision() {
		return comision;
	}
	public String getNombre() {
		return nombre;
	}

	public static ArrayList<Banco> getBancosTotales() {
		return bancosTotales;
	}
	
	public Estado getEstadoAsociado() {
		return estadoAsociado;
	}

	public double getPrestamo(){return prestamo;}
	
	public Divisas getDivisa() { return divisa; }
	public boolean isAsociado() {
		return asociado;
	}
	public ArrayList<String> getDic() {
		return dic;
	}
	public ArrayList<Double> getCionario() {
		return cionario;
	}

	//Sets
	public void setDivisa(Divisas divisa) { this.divisa = divisa; }
	
	public void setComision(double comision) {
		this.comision = comision;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static void setBancosTotales(ArrayList<Banco> bancosTotales) {
		Banco.bancosTotales = bancosTotales;
	}

	public void setEstadoAsociado(Estado estadoAsociado) {
		this.estadoAsociado = estadoAsociado;
	}

	public void setPrestamo(Double prestamo){this.prestamo = prestamo;}
	
	public void setAsociado(boolean asociado) {
		this.asociado = asociado;
	}
	public void setDic(ArrayList<String> dic) {
		this.dic = dic;
	}
	public void setCionario(ArrayList<Double> cionario) {
		this.cionario = cionario;
	}
	
	public String toString() {
		return this.nombre;
	}
}
