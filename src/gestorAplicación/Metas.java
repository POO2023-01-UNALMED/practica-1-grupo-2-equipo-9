package gestorAplicación;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import baseDatos.Deserializador;
import baseDatos.Serializador;

@SuppressWarnings("unchecked")

public class Metas implements Serializable{
	private static final long serialVersionUID = 5L;
	public static final String nombreD = "Metas";
	public String nombre;
	protected double cantidad;
	private Date fecha;
	private int id;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	public static ArrayList<Metas> mel = (ArrayList<Metas>) Deserializador.deserializar_listas("Metas");

	// CONSTRUCTORES	
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
		
	// CREAR UNA META
	public void crearMeta(Metas meta) {
		mel.add(meta);
		Serializador.serializar(mel, "Metas");
	}
	
	// ELIMINAR UNA META
	public void eliminarMeta(int n) {
		mel.remove(n);
		Serializador.serializar(mel, "Metas");
	}
	
	@Override
	public void finalize() {}

	// GETTER Y SETTER
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
	
	public String getFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fecha);
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
}