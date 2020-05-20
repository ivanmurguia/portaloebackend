package modelo;

public class Departamento {
	private int id;
	private String nombre;
	private float monto;
	private Encargado director = new Encargado();
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
	public float getMonto() {
		return monto;
	}
	public void setMonto(float monto) {
		this.monto = monto;
	}
	public Encargado getDirector() {
		return director;
	}
	public void setDirector(Encargado encargado) {
		this.director = encargado;
	}
}
