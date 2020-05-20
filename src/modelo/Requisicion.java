package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbAnnotation;

@JsonbAnnotation
public class Requisicion {
	private int id;
	private String fechaCreacion;
	private String fechaNecesaria;
	private String cotizacion;
	private float tc;
	private float subtotal;
	private float iva;
	private float total;
	private String comentarios;
	private Proveedor proveedor = new Proveedor();
	private Usuario usuario = new Usuario();
	private Sociedad sociedad = new Sociedad();
	private Area area = new Area();
	private Estatus estatus = new Estatus();
	private Moneda moneda = new Moneda();
	private String pedidoSap;
	private String docEntry;
	private String recibo;
	private List<RequisicionDetalle> detalle = new ArrayList<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getFechaNecesaria() {
		return fechaNecesaria;
	}
	public void setFechaNecesaria(String fechaNecesaria) {
		this.fechaNecesaria = fechaNecesaria;
	}
	public String getCotizacion() {
		return cotizacion;
	}
	public void setCotizacion(String cotizacion) {
		this.cotizacion = cotizacion;
	}
	public float getTc() {
		return tc;
	}
	public void setTc(float tc) {
		this.tc = tc;
	}
	public float getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}
	public float getIva() {
		return iva;
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
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
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
	public Sociedad getSociedad() {
		return sociedad;
	}
	public void setSociedad(Sociedad sociedad) {
		this.sociedad = sociedad;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Estatus getEstatus() {
		return estatus;
	}
	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}
	public Moneda getMoneda() {
		return moneda;
	}
	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
	public List<RequisicionDetalle> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<RequisicionDetalle> detalle) {
		this.detalle = detalle;
	}
	public String getPedidoSap() {
		return pedidoSap;
	}
	public void setPedidoSap(String pedidoSap) {
		this.pedidoSap = pedidoSap;
	}
	public String getDocEntry() {
		return docEntry;
	}
	public void setDocEntry(String docEntry) {
		this.docEntry = docEntry;
	}
	public String getRecibo() {
		return recibo;
	}
	public void setRecibo(String recibo) {
		this.recibo = recibo;
	}
}
