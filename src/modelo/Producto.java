package modelo;

import javax.json.bind.annotation.JsonbAnnotation;

@JsonbAnnotation
public class Producto {
	private int id;
	private String producto;
	private String descripcion;
	private boolean activo;
	private boolean esAdministrativo;
	private String cuentaContable;
	private Sociedad sociedad = new Sociedad();
	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Producto(int id, String producto, String descripcion, boolean activo, boolean esAdministrativo,
			String cuentaContable, Sociedad sociedad) {
		super();
		this.id = id;
		this.producto = producto;
		this.descripcion = descripcion;
		this.activo = activo;
		this.esAdministrativo = esAdministrativo;
		this.cuentaContable = cuentaContable;
		this.sociedad = sociedad;
	}
	public Producto(int id, String producto, String descripcion, boolean activo, boolean esAdministrativo,
			String cuentaContable) {
		super();
		this.id = id;
		this.producto = producto;
		this.descripcion = descripcion;
		this.activo = activo;
		this.esAdministrativo = esAdministrativo;
		this.cuentaContable = cuentaContable;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public boolean isEsAdministrativo() {
		return esAdministrativo;
	}
	public void setEsAdministrativo(boolean esAdministrativo) {
		this.esAdministrativo = esAdministrativo;
	}
	public String getCuentaContable() {
		return cuentaContable;
	}
	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
	public Sociedad getSociedad() {
		return sociedad;
	}
	public void setSociedad(Sociedad sociedad) {
		this.sociedad = sociedad;
	}
	
	
}
