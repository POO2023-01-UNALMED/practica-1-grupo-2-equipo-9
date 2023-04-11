package gestorAplicación;

import baseDatos.*;

import java.text.ParseException;
import java.util.ArrayList;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException {
		
		Usuario u1 = new Usuario("Juan Pablo", "Juan1@gmail.com", "Juanpa0128", 1);
		System.out.println(Serializador.serializar(u1));
		Usuario u13 = (Usuario) Deserializador.deserializar("Usuario");
		System.out.println(u13.getNombre());
		
		Usuario u2 = new Usuario("Pepe", "Pepe1@gmail.com", "Pepe0128", 2);
		Usuario u3 = new Usuario("Mario", "Mario1@gmail.com", "Mario0128", 3);
		Usuario u4 = new Usuario("David", "David1@gmail.com", "David0128", 4);
		
		ArrayList<Usuario> u = new ArrayList<Usuario>();
		u.add(u1);
		u.add(u2);
		u.add(u3);
		u.add(u4);
		System.out.println(Serializador.serializar(u, "Usuario"));
		ArrayList<Usuario> ul = (ArrayList<Usuario>) Deserializador.deserializar_listas("Usuario");	
		System.out.println(ul.get(2).getNombre());
		
		
		Metas me1 = new Metas("Comprar una casa", 50000.0, "27/10/2025", 1);
		System.out.println(Serializador.serializar(me1));
		Metas me2 = (Metas) Deserializador.deserializar("Metas");
		System.out.println(me2.getFecha());
	}
}