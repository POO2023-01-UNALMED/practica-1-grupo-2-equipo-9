package externo;

import java.util.ArrayList;

public enum Divisas {
	EUR,
	USD,
	YEN,
	COP,
	GPB,
	MXN;
	
	public static ArrayList<Divisas> getDivisas() {
		ArrayList<Divisas> listaDivisas = new ArrayList<Divisas>();
		listaDivisas.add(EUR);
		listaDivisas.add(USD);
		listaDivisas.add(YEN);
		listaDivisas.add(COP);
		listaDivisas.add(GPB);
		listaDivisas.add(MXN);
		return(listaDivisas); 
	}
}
