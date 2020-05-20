package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.json.bind.annotation.JsonbAnnotation;


//@XmlRootElement
@JsonbAnnotation
public class Articulo {
	private int id;
	private String titulo;
	private String contenido;
	private Calendar creado;
	private String autor;
	public Articulo() {
		
	}
	public Articulo(int id, String titulo, String contenido, String autor) {
		
		this.id = id;
		this.titulo = titulo;
		this.contenido = contenido;
		this.autor = autor;
		this.creado = new GregorianCalendar();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public Calendar getCreado() {
		return creado;
	}
	public void setCreado(Calendar creado) {
		this.creado = creado;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
}
