package baseDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import gestorAplicación.*;

public class Deserializador {
	public static Object deserializar(String clase) {		
		switch(clase) {
			case "Usuario":
				try{
					File f = new File("");
					FileInputStream fi = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Usuario.nombreD +".dat"));
					ObjectInputStream readingObject = new ObjectInputStream(fi);
					Usuario u = (Usuario) readingObject.readObject();
					return u;
			
				}catch(IOException ex) {
					return null;
				} catch (ClassNotFoundException ex) {
					return null;
				}
				
			case "Estado":
				try{
					File f = new File("");
					ObjectInputStream readingObject = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Estado.nombreD +".dat")));
					Estado e = (Estado) readingObject.readObject();
					readingObject.close();
					return e;
				
				}catch(IOException ex) {
					return null;
				} catch (ClassNotFoundException ex) {
					return null;
				}
				
			case "Cuenta":
				try{
					File f = new File("");
					ObjectInputStream readingObject = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Cuenta.nombreD +".dat")));
					Cuenta c = (Cuenta) readingObject.readObject();
					readingObject.close();
					return c;
				
				}catch(IOException ex) {
					return null;
				} catch (ClassNotFoundException ex) {
					return null;
				}
				
			case "Movimientos":
				try{
					File f = new File("");
					ObjectInputStream readingObject = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Movimientos.nombreD +".dat")));
					Movimientos m = (Movimientos) readingObject.readObject();
					readingObject.close();
					return m;
				
				}catch(IOException ex) {
					return null;
				} catch (ClassNotFoundException ex) {
					return null;
				}
				
			case "Metas":
				try{
					File f = new File("");
					ObjectInputStream readingObject = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Metas.nombreD +".dat")));
					Metas me = (Metas) readingObject.readObject();
					readingObject.close();
					return me;
				
				}catch(IOException ex) {
					return null;
				} catch (ClassNotFoundException ex) {
					return null;
				}
				
			default:
				return null;
		}
	}

}
