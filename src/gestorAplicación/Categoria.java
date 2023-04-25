package gestorAplicación;

import java.util.ArrayList;

public enum Categoria {
    TRANSPORTE,
    COMIDA,
    EDUCACION,
    SALUD,
    REGALOS,
    FINANZAS,
    OTROS;
	
	public static ArrayList<Categoria> getCategorias() {
		ArrayList<Categoria> listaCategorias = new ArrayList<Categoria>();
		listaCategorias.add(TRANSPORTE);
		listaCategorias.add(COMIDA);
		listaCategorias.add(EDUCACION);
		listaCategorias.add(SALUD);
		listaCategorias.add(REGALOS);
		listaCategorias.add(FINANZAS);
		listaCategorias.add(OTROS);
		return(listaCategorias); 
	}	
}
