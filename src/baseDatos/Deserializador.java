package baseDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import gestorAplicación.externo.Estado;
import gestorAplicación.interno.Cuenta;
import gestorAplicación.interno.Metas;
import gestorAplicación.interno.Movimientos;
import gestorAplicación.interno.Usuario;
import gestorAplicación.externo.Banco;

public class Deserializador {
	//Deserializar objetos individuales
	public static Object deserializar(String clase) {		
		switch(clase) {
			case "Usuarios":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Usuario.nombreD + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Usuario u = (Usuario) streamEntrada.readObject();
					streamEntrada.close();
					return u;
			
				}catch(IOException ex) {
					return ("El Usuario no pudo ser deserializado en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("El Usuario no pudo ser deserializado en el sistema: " + ex);
				}	
			case "Banco":
				try{
					File f = new File("");
					ObjectInputStream streamEntrada = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Banco.nombreD + ".dat")));
					Banco b = (Banco) streamEntrada.readObject();
					streamEntrada.close();
					return b;
				
				}catch(IOException ex) {
					return ("El Banco no pudo ser deserializado en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("El Banco no pudo ser deserializado en el sistema: " + ex);
				}
			case "Estado":
				try{
					File f = new File("");
					ObjectInputStream streamEntrada = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Estado.nombreD + ".dat")));
					Estado e = (Estado) streamEntrada.readObject();
					streamEntrada.close();
					return e;
				
				}catch(IOException ex) {
					return ("El Estado no pudo ser deserializado en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("El Estado no pudo ser deserializado en el sistema: " + ex);
				}
			case "Cuentas":
				try{
					File f = new File("");
					ObjectInputStream streamEntrada = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Cuenta.nombreD + ".dat")));
					Cuenta c = (Cuenta) streamEntrada.readObject();
					streamEntrada.close();
					return c;
				
				}catch(IOException ex) {
					return ("La Cuenta no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La Cuenta no pudo ser deserializada en el sistema: " + ex);
				}
			case "Movimientos":
				try{
					File f = new File("");
					ObjectInputStream streamEntrada = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Movimientos.nombreD + ".dat")));
					Movimientos m = (Movimientos) streamEntrada.readObject();
					streamEntrada.close();
					return m;
				
				}catch(IOException ex) {
					return ("El Movimiento no pudo ser deserializado en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("El Movimiento no pudo ser deserializado en el sistema: " + ex);
				}				
			case "Metas":
				try{
					File f = new File("");
					ObjectInputStream streamEntrada = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Metas.nombreD + ".dat")));
					Metas me = (Metas) streamEntrada.readObject();
					streamEntrada.close();
					return me;
				
				}catch(IOException ex) {
					return ("La Meta no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La Meta no pudo ser deserializada en el sistema: " + ex);
				}			
			default:
				return("Error de deserialización: El objeto debe estar definido en el sistema.");
		}
	}
	
	//Deserializar Arraylists con objetos
	@SuppressWarnings("unchecked")
	public static Object deserializar_listas(String clase) {		
		switch(clase) {
			case "Usuarios":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Usuario.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					ArrayList<Usuario> u = (ArrayList<Usuario>) streamEntrada.readObject();
					Usuario.setUsuariosTotales(u.get(0).getUsuariosTotalesAux());
					streamEntrada.close();
					return u;
			
				}catch(IOException ex) {
					return ("La lista de Usuarios no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Usuarios no pudo ser deserializada en el sistema: " + ex);
				}				
			case "Estados":
				try{
					File f = new File("");
					ObjectInputStream streamEntrada = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Estado.nombreD + "_lista" + ".dat")));
					ArrayList<Estado> e = (ArrayList<Estado>) streamEntrada.readObject();
					streamEntrada.close();
					return e;
				
				}catch(IOException ex) {
					return ("La lista de Estados no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Estados no pudo ser deserializada en el sistema: " + ex);
				}	
			case "Bancos":
				try{
					File f = new File("");
					ObjectInputStream streamEntrada = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Banco.nombreD + "_lista" + ".dat")));
					ArrayList<Banco> b = (ArrayList<Banco>) streamEntrada.readObject();
					streamEntrada.close();
					return b;
				
				}catch(IOException ex) {
					return ("La lista de Estados no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Estados no pudo ser deserializada en el sistema: " + ex);
				}	
			case "Cuentas":
				try{
					File f = new File("");
					ObjectInputStream streamEntrada = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Cuenta.nombreD + "_lista" + ".dat")));
					ArrayList<Cuenta> c = (ArrayList<Cuenta>) streamEntrada.readObject();
					streamEntrada.close();
					return c;
				
				}catch(IOException ex) {
					return ("La lista de Cuentas no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Cuentas no pudo ser deserializada en el sistema: " + ex);
				}	
			case "Movimientos":
				try{
					File f = new File("");
					ObjectInputStream streamEntrada = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Movimientos.nombreD  + "_lista" + ".dat")));
					ArrayList<Movimientos> m = (ArrayList<Movimientos>) streamEntrada.readObject();
					streamEntrada.close();
					return m;
				
				}catch(IOException ex) {
					return ("La lista de Movimientos no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Movimientos no pudo ser deserializada en el sistema: " + ex);
				}			
			case "Metas":
				try{
					File f = new File("");
					ObjectInputStream streamEntrada = new ObjectInputStream(new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Metas.nombreD  + "_lista" + ".dat")));
					ArrayList<Metas> me = (ArrayList<Metas>) streamEntrada.readObject();
					streamEntrada.close();
					return me;
				
				}catch(IOException ex) {
					return ("La lista de Metas no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Metas no pudo ser deserializada en el sistema: " + ex);
				}		
			default:
				return("Error de deserialización: El objeto debe estar definido en el sistema.");
		}
	}
}
