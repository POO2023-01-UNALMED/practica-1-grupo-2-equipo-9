package gestorAplicación;

	import java.util.ArrayList;

public class Banco extends Estado {
	private static final long serialVersionUID = 2L;
	public static final String nombreD = "Bancos";
	private String nombreb;
	private double comision;
	private static ArrayList<Banco> bancos = new ArrayList<Banco>();
	
	//Constructor
	public Banco(String nombre, double tasa_interes, double tasa_impuestos, String nombreb, double comision, ArrayList<Banco> bancos) {
		super(nombre, tasa_interes, tasa_impuestos);
		this.nombreb = nombreb;
		this.comision = comision;
		bancos.add(this);
	}
	
	//Métodos
	public void imprimirBancos() {
		for (int i = 0; i < bancos.size(); i++) {
			System.out.print(i+1 + ". " + bancos.get(i).getNombre());
		}
	}
	
	//Gets
	public double getComision() {
		return comision;
	}
	public String getNombreb() {
		return nombreb;
	}

	public ArrayList<Banco> getBancos() {
		return bancos;
	}
	
	//Sets
	public void setComision(double comision) {
		this.comision = comision;
	}
	public void setNombreb(String nombreb) {
		this.nombreb = nombreb;
	}


	public void setBancos(ArrayList<Banco> bancos) {
		this.bancos = bancos;
	}
}
