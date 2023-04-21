package gestorAplicación;

public enum Suscripcion{
	DIAMANTE(4,0.80F), 
	ORO(3, 0.60F), 
	PLATA(2, 0.40F), 
	BRONCE(1, 0.20F);
	
	private final int limiteCuentas;
	private final float probabilidad_Inversion;
	
	Suscripcion(int limiteCuentas, float probabilidad_Inversion){
		this.limiteCuentas = limiteCuentas;
		this.probabilidad_Inversion = probabilidad_Inversion;
	}
	
	public int getLimiteCuentas() { return limiteCuentas; }

	public float getProbabilidad_Inversion() { return probabilidad_Inversion; }
}
