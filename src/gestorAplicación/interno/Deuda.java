package gestorAplicación.interno;
import gestorAplicación.externo.Banco;

import java.util.ArrayList;

public class Deuda extends Metas{
//  Atributos
    public static final String nombreD = "Deudas";
    private static final long serialVersionUID = 6L;
    private int id;
    private Ahorros cuenta;
    private Banco banco;
    private static ArrayList<Deuda> deudasTotales = new ArrayList<>();

    //	Constructor
    public Deuda(double cantidad, Ahorros cuenta, Usuario dueno, Banco banco){
        super(cantidad,dueno);
        this.id = deudasTotales.size();
        this.cuenta = cuenta;
        this.banco = banco;
        deudasTotales.add(this);
        Metas.getMetasTotales().add(this);
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidad() {
        return cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static ArrayList<Deuda> getDeudasTotales() {
        return deudasTotales;
    }

    public static void setDeudasTotales(ArrayList<Deuda> deudasTotales) {
        Deuda.deudasTotales = deudasTotales;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Ahorros getCuenta() {
        return cuenta;
    }

    public void setCuenta(Ahorros cuenta) {
        this.cuenta = cuenta;
    }

    public Usuario getTitular() {
        return dueno;
    }

    public void setTitular(Usuario titular) {
        this.dueno = titular;
    }

    //	Conseguir deudas
    public static ArrayList<Deuda> conseguirDeudas(Usuario usuario){
        ArrayList<Deuda> deudas = Deuda.getDeudasTotales();
        ArrayList<Deuda> deudasUsuario = new ArrayList<>();
        for(int i =0; i<deudas.size();i++){
            if(deudas.get(i).getTitular()==usuario){
                deudasUsuario.add(deudas.get(i));
            }
        }
        return deudasUsuario;
    }

    @Override
    public void finalize(){
        Cuenta cuenta = this.getCuenta();
        Banco banco = this.getBanco();
        int id = this.getId();
        System.out.println("La deduda con id: "+id+"\n"+cuenta+"\nrealizada con el banco"+banco+
                "ha sido PAGADA EXITOSAMENTE");
        return;
    }
    
    public String toString() {
		return "id: " + this.id +
				"\nTitular " + this.dueno+
				"\nCantidad: " + this.cantidad ;
	}

}
