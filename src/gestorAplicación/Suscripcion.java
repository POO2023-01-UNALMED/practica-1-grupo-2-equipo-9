package gestorAplicación;

public enum Suscripcion{
	DIAMANTE(4,0.80F), 
	ORO(3, 0.60F), 
	PLATA(2, 0.40F), 
	BRONCE(1, 0.20F);
	
	private final int limite_Bancos;
	private final float probabilidad_Inversion;
	
	Suscripcion(int limite_Bancos, float probabilidad_Inversion){
		this.limite_Bancos = limite_Bancos;
		this.probabilidad_Inversion = probabilidad_Inversion;
	}
	
	public int getLimite_Bancos() { return limite_Bancos; }

	public float getProbabilidad_Inversion() { return probabilidad_Inversion; }
}
