package baseDatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import gestorAplicación.*;

public class Serializador{	
	
	public static String serializar(Object object) {		
		switch(object.getClass().getSimpleName()) {
			case "Usuario":
				Usuario u = (Usuario) object;
				try{
					File f = new File("");
					ObjectOutputStream writingObject = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Usuario.nombreD +".dat")));
					writingObject.writeObject(u);
					writingObject.close();
					return("El usuario con id: " + u.getId() + " y nombre: " + u.getNombre() + " fue serializado satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					return("El usuario con id: " + u.getId() + " y nombre: " + u.getNombre() + " no pudo ser serializado en el sistema: " + ex);
				}
				
			case "Estado":
				Estado e = (Estado) object;
				try{
					File f = new File("");
					ObjectOutputStream writingObject = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Estado.nombreD +".dat")));
					writingObject.writeObject(e);
					writingObject.close();
					//return("El estado con id: " + e.getId() + " y nombre: " + u.getNombre() + " fue serializado satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					//return("El estado con id: " + e.getId() + " y nombre: " + u.getNombre() + " no pudo ser serializado en el sistema: " + ex);
				}
				
			case "Cuenta":
				Cuenta c = (Cuenta) object;
				try{
					File f = new File("");
					ObjectOutputStream writingObject = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Cuenta.nombreD +".dat")));
					writingObject.writeObject(c);
					writingObject.close();
					//return("El estado con id: " + c.getId() + " y nombre: " + c.getNombre() + " fue serializado satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					//return("El estado con id: " + c.getId() + " y nombre: " + c.getNombre() + " no pudo ser serializado en el sistema: " + ex);
				}
				
			case "Movimientos":
				Movimientos m = (Movimientos) object;
				try{
					File f = new File("");
					ObjectOutputStream writingObject = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Movimientos.nombreD +".dat")));
					writingObject.writeObject(m);
					writingObject.close();
					//return("El estado con id: " + m.getId() + " y nombre: " + m.getNombre() + " fue serializado satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					//return("El estado con id: " + m.getId() + " y nombre: " + m.getNombre() + " no pudo ser serializado en el sistema: " + ex);
				}
				
			case "Metas":
				Metas me = (Metas) object;
				try{
					File f = new File("");
					ObjectOutputStream writingObject = new ObjectOutputStream(new FileOutputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Metas.nombreD +".dat")));
					writingObject.writeObject(me);
					writingObject.close();
					//return("El estado con id: " + me.getId() + " y nombre: " + me.getNombre() + " fue serializado satisfactoriamente en el sistema.");
				}catch(IOException ex) {
					//return("El estado con id: " + me.getId() + " y nombre: " + me.getNombre() + " no pudo ser serializado en el sistema: " + ex);
				}
				
			default:
				return("Error de serialización: El objeto debe esta definido en el sistema.");
		}
	}
}
