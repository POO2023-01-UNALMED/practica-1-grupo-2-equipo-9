package gestorAplicación;

import baseDatos.*;

public class Main {

	public static void main(String[] args) {
		Usuario u1 = new Usuario("13123123", "Juan1@gmail.com", "121212312", 3);
		System.out.println(Serializador.serializar(u1));
		Usuario u13 = (Usuario) Deserializador.deserializar("Usuario");
		System.out.println(u13.getNombre());
	
	}

}
