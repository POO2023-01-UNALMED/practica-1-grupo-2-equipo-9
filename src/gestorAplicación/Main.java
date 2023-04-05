package gestorAplicación;

import baseDatos.*;

import java.text.ParseException;

public class Main {

	public static void main(String[] args) throws ParseException {
		
		Usuario u1 = new Usuario("13123123", "Juan1@gmail.com", "121212312", 3);
		System.out.println(Serializador.serializar(u1));
		Usuario u13 = (Usuario) Deserializador.deserializar("Usuario");
		System.out.println(u13.getNombre());
		
		
		Metas me1 = new Metas("Comprar una casa", 50000.0, "27/10/2025");
		System.out.println(Serializador.serializar(me1));
		Metas me2 = (Metas) Deserializador.deserializar("Metas");
		System.out.println(me2.getFecha());
	}
}