package modelo;

import javax.json.bind.annotation.JsonbAnnotation;

@JsonbAnnotation
public class Cotizacion {
	private int id;
	private String nombreProveedor;
	private String comentario;
	private float precioUnitario;
	private float cantidad;
	private float subtotal;
	private float iva;
	private float total;
	private String fechaEntrega;
	private String condicionespago;
	private String minimocredito;
	private String minimocompra;
	private String tiempoentrega;
	private DetalleSolicitudCotizacion detalleSolicitudCotizacion = new DetalleSolicitudCotizacion();
	private Proveedor proveedor = new Proveedor();
	private Usuario usuario = new Usuario();
	private boolean documento;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public float getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(float precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public float getCantidad() {
		return cantidad;
	}
	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}
	public float getIva() {
		return iva;
	}
	public float getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}
	public void setIva(float iva) {
		this.iva = iva;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public String getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public String getCondicionespago() {
		return condicionespago;
	}
	public void setCondicionespago(String condicionespago) {
		this.condicionespago = condicionespago;
	}
	public String getMinimocredito() {
		return minimocredito;
	}
	public void setMinimocredito(String minimocredito) {
		this.minimocredito = minimocredito;
	}
	public String getMinimocompra() {
		return minimocompra;
	}
	public void setMinimocompra(String minimocompra) {
		this.minimocompra = minimocompra;
	}
	public String getTiempoentrega() {
		return tiempoentrega;
	}
	public void setTiempoentrega(String tiempoentrega) {
		this.tiempoentrega = tiempoentrega;
	}
	public DetalleSolicitudCotizacion getDetalleSolicitudCotizacion() {
		return detalleSolicitudCotizacion;
	}
	public void setDetalleSolicitudCotizacion(DetalleSolicitudCotizacion detalleSolicitudCotizacion) {
		this.detalleSolicitudCotizacion = detalleSolicitudCotizacion;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public boolean isDocumento() {
		return documento;
	}
	public void setDocumento(boolean documento) {
		this.documento = documento;
	}

}
