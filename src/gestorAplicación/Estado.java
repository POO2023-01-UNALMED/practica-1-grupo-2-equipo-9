package gestorAplicación;

import java.io.Serializable;

public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String nombreD = "Estados";
	private String nombre;
	private double tasa_interes;
	private double tasa_impuestos;
	private Divisas divisa;
	
	//Constructor
	public Estado(String nombre, double tasa_interes, double tasa_impuestos, Divisas divisa) {
		this.nombre = nombre;
		this.tasa_interes = tasa_interes;
		this.tasa_impuestos = tasa_impuestos;
		this.divisa = divisa;
	}
	
	public Estado() {}
	
	//Gets
	public String getNombre() {
		return nombre;
	}
	public double getTasa_interes() {
		return tasa_interes;
	}
	public double getTasa_impuestos() {
		return tasa_impuestos;
	}

	public Divisas getDivisa() {
		return divisa;
	}
	//Sets
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setTasa_interes(double tasa_interes) {
		this.tasa_interes = tasa_interes;
	}
	public void setTasa_impuestos(double tasa_impuestos) {
		this.tasa_impuestos = tasa_impuestos;
	}

	public void setDivisa(Divisas divisa) {
		this.divisa = divisa;
	}

}
