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
	public static String deserializar_listas(String clase) {		
		switch(clase) {
			case "Usuarios":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Usuario.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Deserializador.readObject(streamEntrada, clase);
					streamEntrada.close();
					return ("Lista con " + Usuario.getUsuariosTotales().size() + " cargada con éxito");
			
				}catch(IOException ex) {
					return ("La lista de Usuarios no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Usuarios no pudo ser deserializada en el sistema: " + ex);
				}				
			case "Estados":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Estado.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Deserializador.readObject(streamEntrada, clase);
					streamEntrada.close();
					return ("Lista con " + Estado.getEstadosTotales().size() + " cargada con éxito");
				
				}catch(IOException ex) {
					return ("La lista de Estados no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Estados no pudo ser deserializada en el sistema: " + ex);
				}	
			case "Bancos":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Banco.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Deserializador.readObject(streamEntrada, clase);
					streamEntrada.close();
					return("Lista con " + Banco.getBancosTotales().size() + " cargada con éxito");
				
				}catch(IOException ex) {
					return ("La lista de Estados no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Estados no pudo ser deserializada en el sistema: " + ex);
				}	
			case "Cuentas":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Cuenta.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Deserializador.readObject(streamEntrada, clase);
					streamEntrada.close();
					return ("Lista con " + Cuenta.getCuentasTotales().size() + " cargada con éxito");
				
				}catch(IOException ex) {
					return ("La lista de Cuentas no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Cuentas no pudo ser deserializada en el sistema: " + ex);
				}	
			case "Movimientos":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Movimientos.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Deserializador.readObject(streamEntrada, clase);
					streamEntrada.close();
					return ("Lista con " + Movimientos.getMovimientosTotales().size() + " cargada con éxito");
				
				}catch(IOException ex) {
					return ("La lista de Movimientos no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Movimientos no pudo ser deserializada en el sistema: " + ex);
				}			
			case "Metas":
				try{
					File f = new File("");
					FileInputStream fe = new FileInputStream(new File(f.getAbsolutePath() + "\\src\\baseDatos\\temp\\" + Metas.nombreD + "_lista" + ".dat"));
					ObjectInputStream streamEntrada = new ObjectInputStream(fe);
					Deserializador.readObject(streamEntrada, clase);
					streamEntrada.close();
					return ("Lista con " + Metas.getMetasTotales().size() + " cargada con éxito");
				
				}catch(IOException ex) {
					return ("La lista de Metas no pudo ser deserializada en el sistema: " + ex);
				} catch (ClassNotFoundException ex) {
					return ("La lista de Metas no pudo ser deserializada en el sistema: " + ex);
				}		
			default:
				return("Error de deserialización: El objeto debe estar definido en el sistema.");
		}
	}
	
	private static void readObject(ObjectInputStream in, String clase) throws IOException, ClassNotFoundException {
	    switch(clase) {
	    case "Usuarios":
	    	Usuario.setUsuariosTotales((ArrayList<Usuario>) in.readObject());
	    case "Cuentas":
	    	Cuenta.setCuentasTotales((ArrayList<Cuenta>) in.readObject());
	    case "Bancos":
	    	Banco.setBancosTotales((ArrayList<Banco>) in.readObject());
	    case "Estados":
	    	Estado.setEstadosTotales((ArrayList<Estado>) in.readObject());
	    case "Movimentos":
	    	Movimientos.setMovimientosTotales((ArrayList<Movimientos>) in.readObject());
	    case "Metas":
	    	Metas.setMetasTotales((ArrayList<Metas>) in.readObject());
	    }
		
	}
}
