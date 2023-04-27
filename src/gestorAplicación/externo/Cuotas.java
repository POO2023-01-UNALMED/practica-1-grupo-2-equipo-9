package gestorAplicación.externo;

import java.util.ArrayList;

public enum Cuotas {
	C1,
	C6,
	C12,
	C18,
	C24,
	C36,
	C48;
	
	public static ArrayList<Cuotas> getCuotas(){
		ArrayList<Cuotas> listaCuotas = new ArrayList<Cuotas>();
		listaCuotas.add(C1);
		listaCuotas.add(C6);
		listaCuotas.add(C12);
		listaCuotas.add(C18);
		listaCuotas.add(C24);
		listaCuotas.add(C36);
		listaCuotas.add(C48);
		return listaCuotas;
	}
}
