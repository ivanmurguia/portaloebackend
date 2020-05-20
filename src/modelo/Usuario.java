package modelo;

import javax.json.bind.annotation.JsonbAnnotation;

@JsonbAnnotation
public class Usuario {
	private int id;
	private String nombres;
	private String apellidop;
	private String apellidom;
	private String email;
	private String usuario;
	private String contrasena;
	private boolean activo;
	private Area area = new Area();
	private TipoUsuario tipousuario = new TipoUsuario();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidop() {
		return apellidop;
	}
	public void setApellidop(String apellidop) {
		this.apellidop = apellidop;
	}
	public String getApellidom() {
		return apellidom;
	}
	public void setApellidom(String apellidom) {
		this.apellidom = apellidom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public TipoUsuario getTipousuario() {
		return tipousuario;
	}
	public void setTipousuario(TipoUsuario tipousuario) {
		this.tipousuario = tipousuario;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombres=" + nombres + ", apellidop=" + apellidop + ", apellidom=" + apellidom
				+ ", email=" + email + ", usuario=" + usuario + ", contrasena=" + contrasena + ", activo=" + activo
				+ ", area=" + area.getId() + ", tipousuario=" + tipousuario.getId() + "]";
	}

}
