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

public class Metas implements Serializable {
	private static final long serialVersionUID = 5L;
	public static final String nombreD = "Metas";
	public String nombre;
	private double cantidad;
	private Date fecha;
	public int id;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	private static ArrayList<Metas> metasTotales = new ArrayList<Metas>();;
	private Usuario dueno;

	// FUNCIONALIDAD
	public static Metas metaProxima;
	public static String plazo;

	// CONSTRUCTORES
	public Metas(String nombre, double cantidad, String fecha, int id) throws ParseException {
		this.setId(id);
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.fecha = DATE_FORMAT.parse(fecha);
		Metas.getMetasTotales().add(this);
		id++;
	}

	public Metas(String nombre, double cantidad, int id) {
		this.setId(id);
		this.nombre = nombre;
		this.cantidad = cantidad;
		Metas.getMetasTotales().add(this);
		id++;
	}

	public Metas(String nombre, String fecha, int id) throws ParseException {
		this.setId(id);
		this.nombre = nombre;
		this.fecha = DATE_FORMAT.parse(fecha);
		Metas.getMetasTotales().add(this);
		id++;
	}

	public Metas(double cantidad, String fecha, int id) throws ParseException {
		this.setId(id);
		this.cantidad = cantidad;
		this.fecha = DATE_FORMAT.parse(fecha);
		Metas.getMetasTotales().add(this);
		id++;
	}

	// Metodos de la funcionalidad asesoramiento de inversion.
	public static void revisionMetas(Usuario u) {
		Date proximaFecha = null;

		// Debemos comparar cada fecha con todas las otras fechas, por eso hay dos
		// loops.
		for (int i = 0; i < u.getMetasAsociadas().size(); i++) {

			for (int e = 0; e < u.getMetasAsociadas().size(); e++) {

				int r = u.getMetasAsociadas().get(e).getFecha().compareTo(u.getMetasAsociadas().get(i).getFecha());

				if (proximaFecha != null) {

					if (r < 0) {

						int t = u.getMetasAsociadas().get(e).getFecha().compareTo(proximaFecha);

						if (t < 0) {
							proximaFecha = u.getMetasAsociadas().get(e).getFecha();
							metaProxima = u.getMetasAsociadas().get(e);
						}

						else {
							continue;
						}
					} else {
						continue;
					}
				} else {

					proximaFecha = u.getMetasAsociadas().get(e).getFecha();

				}
			}
		}
	}

	public static void cambioFecha(Metas metaMe, String Fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date nuevaFecha = sdf.parse(Fecha);
			metaMe.setFecha(nuevaFecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void determinarPlazo(Metas me1) throws ParseException {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = sdformat.parse("2024-01-01");
		Date d2 = sdformat.parse("2026-01-01");
		if (me1.getFecha().compareTo(d1) < 0) {
			plazo = "Corto";
		} else if (me1.getFecha().compareTo(d1) > 0 && me1.getFecha().compareTo(d2) < 0) {
			plazo = "Mediano";
		} else {
			plazo = "Largo";
		}
	}

	public static void prioridadMetas(Usuario u, Metas me) {
		u.getMetasAsociadas().remove(u.getMetasAsociadas().size() - 1);
		u.getMetasAsociadas().add(0, me);
	}

	@Override
	public void finalize() {
		String name = this.getNombre();
		double amount = this.getCantidad();
		Date date = this.getFecha();
		if (date == null) {
			System.out.print("La meta con nombre " + this.getNombre()
//				+ "y cantidad" + this.getCantidad()
					+ " fue eliminada satisfactoriamente del sistema.");
		} else if (amount == 0) {
			System.out.print("La meta con nombre " + this.getNombre() + "para la fecha " + this.getFechaNormal()
					+ " fue eliminada satisfactoriamente del sistema.");
		} else if (name == null) {
			System.out.print("La meta para la fecha " + this.getFechaNormal()
//				+ "y cantidad" + this.getCantidad()
					+ " fue eliminada satisfactoriamente del sistema.");
		} else {
			System.out.print("La meta con nombre " + this.getNombre() + "para la fecha " + this.getFechaNormal()
//				+ "y cantidad" + this.getCantidad()
					+ " fue eliminada satisfactoriamente del sistema.");
		}

		// el getCantidad no funciona por ser double. No encontre la manera de
		// solucionarlo
	}

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

	public Usuario getDueno() {
		return dueno;
	}

	public void setDueno(Usuario dueno) {
		this.dueno = dueno;
	}
}