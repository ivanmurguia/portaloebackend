package modelo;

import javax.json.bind.annotation.JsonbAnnotation;

@JsonbAnnotation
public class PrivaliaRegistro {
	private String pedido;
	private String referencia;
	private String modelo;
	private String ean;
	private String piezas;
	private String coste;
	private String dia;
	private String guias;
	private String status;
	private String razon;
	public PrivaliaRegistro() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PrivaliaRegistro(String pedido, String referencia, String modelo, String ean, String piezas, String coste,
			String dia, String guias, String status, String razon) {
		super();
		this.pedido = pedido;
		this.referencia = referencia;
		this.modelo = modelo;
		this.ean = ean;
		this.piezas = piezas;
		this.coste = coste;
		this.dia = dia;
		this.guias = guias;
		this.status = status;
		this.razon = razon;
	}
	public String getPedido() {
		return pedido;
	}
	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	public String getPiezas() {
		return piezas;
	}
	public void setPiezas(String piezas) {
		this.piezas = piezas;
	}
	public String getCoste() {
		return coste;
	}
	public void setCoste(String coste) {
		this.coste = coste;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getGuias() {
		return guias;
	}
	public void setGuias(String guias) {
		this.guias = guias;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRazon() {
		return razon;
	}
	public void setRazon(String razon) {
		this.razon = razon;
	}

}
