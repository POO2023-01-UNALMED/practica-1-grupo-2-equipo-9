package gestorAplicación;

public class Banco extends Estado {
	private static final long serialVersionUID = 2L;
	private String nombreb;
	private double comision;
	
	//Constructor
	public Banco(String nombre, double tasa_interes, double tasa_impuestos, String nombreb, double comision) {
		super(nombre, tasa_interes, tasa_impuestos);
		this.nombreb = nombreb;
		this.comision = comision;
	}
	//Gets
	public double getComision() {
		return comision;
	}
	public String getNombreb() {
		return nombreb;
	}
	//Sets
	public void setComision(double comision) {
		this.comision = comision;
	}
	public void setNombreb(String nombreb) {
		this.nombreb = nombreb;
	}
}
