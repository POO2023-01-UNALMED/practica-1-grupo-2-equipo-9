package gestorAplicación.interno;

import java.util.ArrayList;

public enum Suscripcion{
	DIAMANTE(4,0.80F,4,2f),
	ORO(3, 0.60F,3,1.5f),
	PLATA(2, 0.40F,2,1f),
	BRONCE(1, 0.20F,1,0.5f);
	
	//Atributos
	private final int limiteCuentas;
	private final float probabilidad_Inversion;
	private final int maxDeudas;
	private final float porcentajePrestamo;
	
	//Constructor
	Suscripcion(int limiteCuentas, float probabilidad_Inversion,int maxDeudas,float porcentajePrestamo){
		this.limiteCuentas = limiteCuentas;
		this.probabilidad_Inversion = probabilidad_Inversion;
		this.maxDeudas = maxDeudas;
		this.porcentajePrestamo = porcentajePrestamo;
	}
	
	//Métodos
	public int getLimiteCuentas() { return limiteCuentas; }
	
	public float getProbabilidad_Inversion() { return probabilidad_Inversion; }

	public int getMaxDeudas(){
		return maxDeudas;
	}

	public float getPorcentajePrestamo(){
		return porcentajePrestamo;
	}

	public static ArrayList<Suscripcion> getNivelesSuscripcion() {
		ArrayList<Suscripcion> listaSuscripcion = new ArrayList<Suscripcion>();
		listaSuscripcion.add(DIAMANTE);
		listaSuscripcion.add(ORO);
		listaSuscripcion.add(PLATA);
		listaSuscripcion.add(BRONCE);	
		return(listaSuscripcion); 
	}
}
