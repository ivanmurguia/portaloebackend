package modelo;

import javax.json.bind.annotation.JsonbAnnotation;

@JsonbAnnotation
public class Respuesta {
	private String valor;
	private String descripcion;
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
