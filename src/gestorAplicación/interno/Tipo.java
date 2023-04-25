package gestorAplicación.interno;

import java.util.ArrayList;

public enum Tipo {
	AHORROS,
	CORRIENTE;
	
	public static ArrayList<Tipo> getTipos() {
		ArrayList<Tipo> listaTipos = new ArrayList<Tipo>();
		listaTipos.add(CORRIENTE);
		listaTipos.add(AHORROS);	
		return(listaTipos); 
	}
}
