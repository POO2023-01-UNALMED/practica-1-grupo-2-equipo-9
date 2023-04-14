package gestorAplicación;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import baseDatos.Deserializador;

public class Metas implements Serializable{
	private static final long serialVersionUID = 5L;
	public static final String nombreD = "Metas";
	public String nombre;
	protected double cantidad;
	private Date fecha;
	private int id;
	private int del;
	private ArrayList<Metas> metas = new ArrayList<Metas>();
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	

	// Constructores 
	public Metas(String nombre, double cantidad, String fecha, int id) throws ParseException{
		this.setId(id);
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.fecha = DATE_FORMAT.parse(fecha);
	}
	
	public Metas(String nombre, double cantidad, int id) {
		this.setId(id);
		this.nombre = nombre;
		this.cantidad = cantidad;
	}
	
	public Metas(String nombre, String fecha, int id) throws ParseException{
		this.setId(id);
		this.nombre = nombre;
		this.fecha = DATE_FORMAT.parse(fecha);
	}
	
	public Metas(double cantidad, String fecha, int id) throws ParseException{
		this.setId(id);
		this.cantidad = cantidad;
		this.fecha = DATE_FORMAT.parse(fecha);
	}
	
	public Metas(String nombre, int id) {
		this.setId(id);
		this.nombre = nombre;
		}
	
	public Metas(double cantidad, int id) {
		this.setId(id);
		this.cantidad = cantidad;
		}

	// Eliminar una meta
	public void eliminarMeta() {
		ArrayList<Metas> mel = (ArrayList<Metas>) Deserializador.deserializar_listas("Metas");
		mel.remove(getDel() - 1);
		finalize();
	}
	
	@Override
	protected void finalize() {
        System.out.println("La meta con nombre " + this.getNombre() + " y cantidad " + this.getCantidad() + 
        		" para la fecha " + this.getFecha() + " fue eliminada satisfactoriamente del sistema.");
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Metas> getMetas() {
		return metas;
	}

	public void setMetas(ArrayList<Metas> metas) {
		this.metas = metas;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}
	
}