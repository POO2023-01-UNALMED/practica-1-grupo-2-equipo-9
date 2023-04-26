package gestorAplicación.externo;

import java.io.Serializable;
import java.util.ArrayList;

public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String nombreD = "Estados";
	private static ArrayList<Estado> estadosTotales = new ArrayList<Estado>();
	private String nombre;
	private int id;
	private double tasa_impuestos;
	private double interes_bancario_corriente;
	private double tasa_usura;
	private Divisas divisa;
	
	//Constructor
	public Estado(String nombre, double tasa_impuestos, Divisas divisa) {
		this.setNombre(nombre);
		this.setTasa_impuestos(tasa_impuestos);
		this.setDivisa(divisa);
		Estado.getEstadosTotales().add(this);
		this.setId(Estado.getEstadosTotales().size());
	}
	public Estado(){}
	
	//Gets
	public String getNombre() {
		return nombre;
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
	public void setTasa_impuestos(double tasa_impuestos) {
		this.tasa_impuestos = tasa_impuestos;
	}

	public void setDivisa(Divisas divisa) {
		this.divisa = divisa;
	}

	public static ArrayList<Estado> getEstadosTotales() {
		return estadosTotales;
	}

	public static void setEstadosTotales(ArrayList<Estado> estadosTotales) {
		Estado.estadosTotales = estadosTotales;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
