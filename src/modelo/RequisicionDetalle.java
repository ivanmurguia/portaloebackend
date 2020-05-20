package modelo;

import javax.json.bind.annotation.JsonbAnnotation;

@JsonbAnnotation
public class RequisicionDetalle{
	private int id;
	private String descripcion;
	private float precioEstimado;
	private float cantidad;
	private float monto;
	private String iva;
	private Producto producto = new Producto();
	public RequisicionDetalle() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RequisicionDetalle(int id, String descripcion, float precioEstimado, float cantidad, float monto, String iva,
			Producto producto) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.precioEstimado = precioEstimado;
		this.cantidad = cantidad;
		this.monto = monto;
		this.iva = iva;
		this.producto = producto;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public float getPrecioEstimado() {
		return precioEstimado;
	}
	public void setPrecioEstimado(float precioEstimado) {
		this.precioEstimado = precioEstimado;
	}
	public float getCantidad() {
		return cantidad;
	}
	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}
	public float getMonto() {
		return monto;
	}
	public void setMonto(float monto) {
		this.monto = monto;
	}
	public String getIva() {
		return iva;
	}
	public void setIva(String iva) {
		this.iva = iva;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}
