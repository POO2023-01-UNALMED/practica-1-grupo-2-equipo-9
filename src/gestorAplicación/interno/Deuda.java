package gestorAplicación.interno;
import gestorAplicación.externo.Banco;

import java.util.ArrayList;

public class Deuda {
//  Atributos
    public static final String nombreD = "Deudas";
    private static final long serialVersionUID = 6L;
    private int id;
    private double cantidad;
    private Ahorros cuenta;
    private Usuario titular;
    private Banco banco;
    private static ArrayList<Deuda> deudasTotales = new ArrayList<>();

    //	Constructor
    public Deuda(double cantidad, Ahorros cuenta, Usuario titular, Banco banco){
        this.id = deudasTotales.size();
        this.cantidad = cantidad;
        this.cuenta = cuenta;
        this.titular = titular;
        this.banco = banco;
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
        return titular;
    }

    public void setTitular(Usuario titular) {
        this.titular = titular;
    }

}
