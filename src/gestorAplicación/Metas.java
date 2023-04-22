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
	private static ArrayList<Metas> metasTotales = new ArrayList<Metas>();;
	
	// FUNCIONALIDAD
	public static int metaProxima = 0;
	public static String plazo;

	// CONSTRUCTORES	
	public Metas(String nombre, double cantidad, String fecha, int id) throws ParseException{
		this.setId(id);
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.fecha = DATE_FORMAT.parse(fecha);
		Metas.getMetasTotales().add(this);
	}
	
	public Metas(String nombre, double cantidad, int id) {
		this.setId(id);
		this.nombre = nombre;
		this.cantidad = cantidad;
		Metas.getMetasTotales().add(this);
	}
	
	public Metas(String nombre, String fecha, int id) throws ParseException{
		this.setId(id);
		this.nombre = nombre;
		this.fecha = DATE_FORMAT.parse(fecha);
		Metas.getMetasTotales().add(this);
	}
	
	public Metas(double cantidad, String fecha, int id) throws ParseException{
		this.setId(id);
		this.cantidad = cantidad;
		this.fecha = DATE_FORMAT.parse(fecha);
		Metas.getMetasTotales().add(this);
	}
	
	// METODOS
	
	// Crear una meta
	public static void crearMeta(Metas meta) {
		mel.add(meta);
		Serializador.serializar(mel, "Metas");
	}
	
	// Eliminar una meta
	public void eliminarMeta(int n) {
		mel.remove(n);
		Serializador.serializar(mel, "Metas");
	}
	
	// Metodos de la funcionalidad asesoramiento de inversion
	public static void revisionMetas() {
		Date proximaFecha = mel.get(0).getFecha();
		for (int i = 1; i < mel.size()-1; i++) {
			for (int e = 1; e < mel.size() + 1; e++) {	
					if (mel.get(e-1).getFecha().compareTo(proximaFecha) < 0) {
						proximaFecha = mel.get(e-1).getFecha();
						metaProxima = e-1;
					}
					else {
						continue;
					}
				}
		}
	}
	
	public static void cambiarFecha(String Fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date nuevaFecha = sdf.parse(Fecha);
			mel.get(metaProxima).setFecha(nuevaFecha);
			Serializador.serializar(mel, "Metas");
			plazo(nuevaFecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void plazo(Date nuevaFecha) throws ParseException {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
	    Date d1 = sdformat.parse("2024-01-01");
	    Date d2 = sdformat.parse("2026-01-01");
		if (nuevaFecha.compareTo(d1) < 0) {
			plazo = "Corto";
		}
		else if (nuevaFecha.compareTo(d1) > 0 && nuevaFecha.compareTo(d2) < 0) {
			plazo = "Mediano";
		}
		else {
			plazo = "Largo";
		}
	}
	
	public static void prioridadMetas(Metas me) {
		mel.remove(mel.size()-1);
		mel.add(0, me);
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
	
	public String getFechaNormal() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fecha);
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

	public static ArrayList<Metas> getMetasTotales() {
		return metasTotales;
	}

	public static void setMetasTotales(ArrayList<Metas> metasTotales) {
		Metas.metasTotales = metasTotales;
	}
}