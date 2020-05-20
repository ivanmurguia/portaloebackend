package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbAnnotation;

@JsonbAnnotation
public class SolicitudCotizacion {
	private int id;
	private String fechaCreacion;
	private String fechaNecesaria;
	private String comentarios;
	private Sociedad sociedad = new Sociedad();
	private Departamento departamento = new Departamento();
	private Area area = new Area();
	private Usuario usuario = new Usuario();
	private Estatus estatus = new Estatus();
	private Moneda moneda = new Moneda();
	private boolean tecnologia;
	private List<DetalleSolicitudCotizacion> detalle = new ArrayList<>();
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
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public Sociedad getSociedad() {
		return sociedad;
	}
	public void setSociedad(Sociedad sociedad) {
		this.sociedad = sociedad;
	}
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	public boolean isTecnologia() {
		return tecnologia;
	}
	public void setTecnologia(boolean tecnologia) {
		this.tecnologia = tecnologia;
	}
	public List<DetalleSolicitudCotizacion> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<DetalleSolicitudCotizacion> detalle) {
		this.detalle = detalle;
	}
}
