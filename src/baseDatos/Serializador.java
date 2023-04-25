package baseDatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import externo.Estado;

import java.io.IOException;
import gestorAplicación.*;
import interno.Cuenta;
import interno.Metas;
import interno.Movimientos;
import interno.Usuario;

public class Serializador{	
	//Serializar objetos individuales
	public static String serializar(Object objeto) {		
		switch(objeto.getClass().getSimpleName()) {
			case "Usuario":
				Usuario u = (Usuario) objeto;
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Usuario.nombreD + ".dat")));
					streamSalida.writeObject(u);
					streamSalida.close();
					return("El Usuario con id: " + u.getId() + " y nombre: " + u.getNombre() + " fue guardado satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("El Usuario con id: " + u.getId() + " y nombre: " + u.getNombre() + " no pudo ser guardado en el sistema: " + ex);
				}	
			case "Estado":
				Estado e = (Estado) objeto;
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Estado.nombreD + ".dat")));
					streamSalida.writeObject(e);
					streamSalida.close();
					return("El Estado con id: " + e.getId() + " y nombre: " + e.getNombre() + " fue guardado satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("El Estado con id: " + e.getId() + " y nombre: " + e.getNombre() + " no pudo ser guardado en el sistema: " + ex);
				}	
			case "Cuenta":
				Cuenta c = (Cuenta) objeto;
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Cuenta.nombreD + ".dat")));
					streamSalida.writeObject(c);
					streamSalida.close();
					return("La Cuenta con id: " + c.getId() + " y nombre: " + c.getNombre() + " fue guardado satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La Cuenta con id: " + c.getId() + " y nombre: " + c.getNombre() + " no pudo ser guardado en el sistema: " + ex);
				}
			case "Movimientos":
				Movimientos m = (Movimientos) objeto;
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Movimientos.nombreD + ".dat")));
					streamSalida.writeObject(m);
					streamSalida.close();
					return("El Movimiento con id: " + m.getId() + " fue guardado satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("El Movimiento con id: " + m.getId() + " no pudo ser guardado en el sistema: " + ex);
				}	
			case "Metas":
				Metas me = (Metas) objeto;
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Metas.nombreD + ".dat")));
					streamSalida.writeObject(me);
					streamSalida.close();
					return("La Meta con id: " + me.getId() + " y nombre: " + me.getNombre() + " fue guardado satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La Meta con id: " + me.getId() + " y nombre: " + me.getNombre() + " no pudo ser guardado en el sistema: " + ex);
				}	
			default:
				return("Error de guardado: El objeto debe estar definido en el sistema.");
		}
	}
	
	//Serializar ArrayLists con objetos
	@SuppressWarnings("unchecked")
	public static String serializar(Object objetos, String clase) {		
		switch(clase) {
			case "Usuarios":
				ArrayList<Usuario> u = (ArrayList<Usuario>) objetos;
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Usuario.nombreD + "_lista" + ".dat")));
					streamSalida.writeObject(u);
					streamSalida.close();
					return("La lista de Usuarios fue guardada satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La lista de Usuarios no pudo ser guardada en el sistema: " + ex);
				}			
			case "Estados":
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Estado.nombreD + "_lista" + ".dat")));
					streamSalida.writeObject(objetos);
					streamSalida.close();
					return("La lista de Estados fue guardada satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La lista de Estados no pudo ser guardada en el sistema: " + ex);
				}				
			case "Cuentas":
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Cuenta.nombreD + "_lista" + ".dat")));
					streamSalida.writeObject(objetos);
					streamSalida.close();
					return("La lista de Cuentas fue guardada satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La lista de Cuentas no pudo ser guardada en el sistema: " + ex);
				}			
			case "Movimientos":
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Movimientos.nombreD + "_lista" + ".dat")));
					streamSalida.writeObject(objetos);
					streamSalida.close();
					return("La lista de Movimientos fue guardada satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La lista de Movimientos no pudo ser guardada en el sistema: " + ex);
				}				
			case "Metas":
				try{
					File f = new File("");
					ObjectOutputStream streamSalida = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Metas.nombreD + "_lista" + ".dat")));
					streamSalida.writeObject(objetos);
					streamSalida.close();
					return("La lista de Metas fue guardada satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("La lista de Metas no pudo ser guardada en el sistema: " + ex);
				}		
			default:
				return("Error de guardado: El objeto debe estar definido en el sistema.");
		}
	}
}
