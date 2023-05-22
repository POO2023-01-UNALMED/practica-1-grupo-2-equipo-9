package gestorAplicación.externo;

import java.lang.reflect.Field;
import java.util.ArrayList;

import gestorAplicación.interno.Ahorros;
import gestorAplicación.interno.Corriente;
import gestorAplicación.interno.Cuenta;
import gestorAplicación.interno.Deuda;
import gestorAplicación.interno.Metas;
import gestorAplicación.interno.Movimientos;
import gestorAplicación.interno.Usuario;

public interface Tablas {
	default public ArrayList<String> recibirPropiedadesObjeto(){
		ArrayList<String> propiedades = new ArrayList<String>();
		Field[] propiedad = this.getClass().getDeclaredFields();
		for(int i = 0 ; i < propiedad.length; i++) {
			propiedades.add(propiedad[i].getName());
		}
		return propiedades;
	}
	
	public abstract void impresionBancos(ArrayList<Banco> bancos);
	public abstract void impresionEstados(ArrayList<Estado> estados);
	public abstract void impresionCuentasCorriente(ArrayList<Corriente> cuentas);
	public abstract void impresionCuentasAhorros(ArrayList<Ahorros> cuentas);
	public abstract void impresionCuentas(ArrayList<Cuenta> cuentas);
	public abstract void impresionDeudas(ArrayList<Deuda> deudas);
	public abstract void impresionMetas(ArrayList<Metas> metas);
	public abstract void impresionMovimientos(ArrayList<Movimientos> movimientos);
	public abstract void impresionUsuarios(ArrayList<Usuario> usuarios);
}
