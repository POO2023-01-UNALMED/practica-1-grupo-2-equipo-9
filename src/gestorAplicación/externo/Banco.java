package gestorAplicación.externo;

import java.util.ArrayList;
import java.time.Instant;
import java.util.Date;
import gestorAplicación.interno.Categoria;
import gestorAplicación.interno.Corriente;
import gestorAplicación.interno.Cuenta;
import gestorAplicación.interno.Movimientos;
import gestorAplicación.interno.Suscripcion;
import gestorAplicación.interno.Usuario;
import gestorAplicación.interno.Ahorros;

public class Banco extends Estado {
	private static final long serialVersionUID = 2L;
	private static ArrayList<Banco> bancosTotales = new ArrayList<Banco>();
	public static final String nombreD = "Bancos";
	private ArrayList<Banco> bancosTotalesAux = new ArrayList<Banco>();
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
	
	public Banco(String nombre, double comision, Estado estado, double prestamo) {
		this.nombre = nombre;
		this.setEstadoAsociado(estado);
		this.comision = comision + this.getTasa_impuestos();
		this.setPrestamo(prestamo);
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

		Ahorros cuentaAhorros = new Ahorros(user.getBancosAsociados().get(0), 1234, Divisas.COP, "Ahorros", 1.0);
		Corriente cuentaCorriente = new Corriente(user.getBancosAsociados().get(0), 1234, Divisas.COP, "Corriente");
		Usuario pepitoMaster1234 = new Usuario("pepito", "pepito", "pepito");
		cuentaAhorros.setTitular(pepitoMaster1234);
		cuentaCorriente.setTitular(pepitoMaster1234);

		double interes = Math.random() + riesgo;
		if (user.getCuentasAhorrosAsociadas().size() != 0
				&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() > invertir) {

			double x = user.getCuentasAhorrosAsociadas().get(0).getSaldo();
			double cobro = x * interes - x;
			Movimientos movimiento = new Movimientos(cuentaAhorros, user.getCuentasAhorrosAsociadas().get(0), cobro,
					Categoria.OTROS, Date.from(Instant.now()));

			if (movimiento.impuestosMovimiento(interes)
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
				return 1;
			}

			else if (movimiento.impuestosMovimiento(interes) == false
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
				return 2;
			}

			else if (movimiento.impuestosMovimiento(interes)
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() > invertir) {
				return 3;
			}

			else if (movimiento.impuestosMovimiento(interes) == false
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() > invertir) {
				return 4;
			}

		}

		else if (user.getCuentasAhorrosAsociadas().size() != 0
				&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
			double x = user.getCuentasAhorrosAsociadas().get(0).getSaldo();
			double cobro = x * interes - x;
			Movimientos movimiento = new Movimientos(cuentaAhorros, user.getCuentasAhorrosAsociadas().get(0), cobro,
					Categoria.OTROS, Date.from(Instant.now()));

			if (movimiento.impuestosMovimiento(interes)
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
				return 5;
			}

			else if (movimiento.impuestosMovimiento(interes) == false
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() < invertir) {
				return 6;
			}

			else if (movimiento.impuestosMovimiento(interes)
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() > invertir) {
				return 7;
			}

			else if (movimiento.impuestosMovimiento(interes) == false
					&& user.getCuentasAhorrosAsociadas().get(0).getSaldo() > invertir) {
				return 8;
			}
		}

		else if (user.getCuentasCorrienteAsociadas().size() != 0
				&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() > invertir) {

			double x = user.getCuentasCorrienteAsociadas().get(0).getDisponible();
			double cobro = x * interes - x;
			Movimientos movimiento = new Movimientos(cuentaCorriente, user.getCuentasCorrienteAsociadas().get(0), cobro,
					Categoria.OTROS, Date.from(Instant.now()));

			if (movimiento.impuestosMovimiento(interes)
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() < invertir) {
				return 1;
			}

			else if (movimiento.impuestosMovimiento(interes) == false
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() < invertir) {
				return 2;
			}

			else if (movimiento.impuestosMovimiento(interes)
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() > invertir) {
				return 3;
			}

			else if (movimiento.impuestosMovimiento(interes) == false
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() > invertir) {
				return 4;
			}

		}

		else {
			double x = user.getCuentasCorrienteAsociadas().get(0).getDisponible();
			double cobro = x * interes - x;
			Movimientos movimiento = new Movimientos(cuentaCorriente, user.getCuentasCorrienteAsociadas().get(0), cobro,
					Categoria.OTROS, Date.from(Instant.now()));

			if (movimiento.impuestosMovimiento(interes)
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() < invertir) {
				return 5;
			}

			else if (movimiento.impuestosMovimiento(interes) == false
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() < invertir) {
				return 6;
			}

			else if (movimiento.impuestosMovimiento(interes)
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() > invertir) {
				return 7;
			}

			else if (movimiento.impuestosMovimiento(interes) == false
					&& user.getCuentasCorrienteAsociadas().get(0).getDisponible() > invertir) {
				return 8;
			}
		}

		return 0;
	}

	public static Banco bancoPortafolio(Usuario user) {
		Banco banco = null;
		if (user.getBancosAsociados().size() == 1) {
			banco = user.getBancosAsociados().get(0);
		} else {
			for (int i = 1; i <= user.getBancosAsociados().size(); i++) {
				if (user.getBancosAsociados().get(i - 1) != user.getBancosAsociados().get(i)) {
					banco = user.getBancosAsociados().get(i);
				} else {
					continue;
				}
			}
		}
		return banco;
	}

	public static double interesesPortafolio(Banco banco, Usuario user) {
		double interes = 0.0;

		for (int i = 0; i < user.getBancosAsociados().size(); i++) {
			if (user.getBancosAsociados().get(i) == banco) {
				interes = Math.round((interes + Math.random() + i) * 100.0) / 100.0;
			}
		}
		return interes;
	}
	
	public static Double verificarTasasdeInteres(Suscripcion suscripcion, Corriente cuenta){
		double interes = 0.0d;
		double descuento_movimientos = cuenta.getBanco().retornarDescuentosMovimientos(cuenta);
		double[] descuento_suscripcion = cuenta.getBanco().retornarDescuentosSuscripcion();
		double[] descuento_total = Banco.descuentoTotal(descuento_movimientos, descuento_suscripcion);
		switch(suscripcion) {
			case DIAMANTE:
				if (cuenta.getBanco().getInteres_bancario_corriente() >= descuento_total[3]) {
					interes = cuenta.getBanco().getInteres_bancario_corriente() - descuento_total[3];
				}
				else {
					interes = 0.0;
				}
			case ORO:
				if (cuenta.getBanco().getInteres_bancario_corriente() >= descuento_total[2]) {
					interes = cuenta.getBanco().getInteres_bancario_corriente() - descuento_total[2];
				}
				else {
					interes = 0.0;
				}
			case PLATA:
				if(cuenta.getBanco().getInteres_bancario_corriente() >= descuento_total[1]) {
					interes = cuenta.getBanco().getInteres_bancario_corriente() - descuento_total[1];
				}
				else {
					interes = 0.0;
				}
			case BRONCE:
				if(cuenta.getBanco().getInteres_bancario_corriente() >= descuento_total[0]) {
					interes = cuenta.getBanco().getInteres_bancario_corriente() - descuento_total[0];
				}
				else {
					interes = 0.0;
				}
		}
		return interes;
	}
	
	private double[] retornarDescuentosSuscripcion() {
		double[] descuento = new double[4];
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
		for (double descuento : descuento_total) {
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

	public ArrayList<Banco> getBancosTotalesAux() {
		return bancosTotalesAux;
	}

	public void setBancosTotalesAux(ArrayList<Banco> bancosTotalesAux) {
		this.bancosTotalesAux = bancosTotalesAux;
	}
}
