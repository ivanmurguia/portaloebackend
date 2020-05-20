package modelo;

public class Area {
	private int id;
	private String nombre;
	private String normaReparto;
	private String dimension;
	private boolean activo;
	private Departamento departamento = new Departamento();
	private Encargado gerente = new Encargado();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNormaReparto() {
		return normaReparto;
	}
	public void setNormaReparto(String normaReparto) {
		this.normaReparto = normaReparto;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public Encargado getGerente() {
		return gerente;
	}
	public void setGerente(Encargado usuario) {
		this.gerente = usuario;
	}

}
