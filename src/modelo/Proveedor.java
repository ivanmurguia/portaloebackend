package modelo;

import javax.json.bind.annotation.JsonbAnnotation;

@JsonbAnnotation
public class Proveedor {
	private int id;
	private String cardCode;
	private String nombre;
	private String nombreExtranjero;
	private String telefono;
	private boolean activo;
	private Sociedad sociedad = new Sociedad();
	private Moneda moneda = new Moneda();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreExtranjero() {
		return nombreExtranjero;
	}
	public void setNombreExtranjero(String nombreExtranjero) {
		this.nombreExtranjero = nombreExtranjero;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public Sociedad getSociedad() {
		return sociedad;
	}
	public void setSociedad(Sociedad sociedad) {
		this.sociedad = sociedad;
	}
	public Moneda getMoneda() {
		return moneda;
	}
	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}


}
