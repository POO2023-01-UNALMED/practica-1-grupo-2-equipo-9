package gestorAplicación;

import java.util.ArrayList;

public enum Suscripcion{
	DIAMANTE(4,0.80F), 
	ORO(3, 0.60F), 
	PLATA(2, 0.40F), 
	BRONCE(1, 0.20F);
	
	//Atributos
	private final int limiteCuentas;
	private final float probabilidad_Inversion;
	
	//Constructor
	Suscripcion(int limiteCuentas, float probabilidad_Inversion){
		this.limiteCuentas = limiteCuentas;
		this.probabilidad_Inversion = probabilidad_Inversion;
	}
	
	//Métodos
	public int getLimiteCuentas() { return limiteCuentas; }
	public float getProbabilidad_Inversion() { return probabilidad_Inversion; }
	public static ArrayList<Suscripcion> getNivelesSuscripcion() {
		ArrayList<Suscripcion> listaSuscripcion = new ArrayList<Suscripcion>();
		listaSuscripcion.add(DIAMANTE);
		listaSuscripcion.add(ORO);
		listaSuscripcion.add(PLATA);
		listaSuscripcion.add(BRONCE);	
		return(listaSuscripcion); 
	}
}
