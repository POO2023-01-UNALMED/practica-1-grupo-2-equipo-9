package gestorAplicación;

import java.io.Serializable;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Metas implements Serializable{
	private static final long serialVersionUID = 5L;
	public static String nombreD = "Metas";
	public String nombre;
	protected double cantidad;
	private Date fecha;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	

// Constructor
	public Metas(String nombre, double cantidad, String fecha) throws ParseException{
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.fecha = DATE_FORMAT.parse(fecha);
	}

	@Override
	protected void finalize() {
        System.out.println("La meta con nombre " + this.getNombre() + " y cantidad " + this.getCantidad() + 
        		" para la fecha " + this.getFecha() + " fue eliminado satisfactoriamente del sistema.");
	}

// Getter y Setter
	public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
	public double getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
	public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}