package modelo;

import javax.json.bind.annotation.JsonbAnnotation;

@JsonbAnnotation
public class Presupuesto {
	private String sociedad;
	private int anio;
	private String normaReparto;
	private String nombreNormaReparto;
	private String cuentaContable;
	private String nombreCuentaContable;
	private String razonNegocio;
	private float enero;
	private float febrero;
	private float marzo;
	private float abril;
	private float mayo;
	private float junio;
	private float julio;
	private float agosto;
	private float septiembre;
	private float octubre;
	private float noviembre;
	private float diciembre;
	private float total;
	
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public String getNormaReparto() {
		return normaReparto;
	}
	public void setNormaReparto(String normaReparto) {
		this.normaReparto = normaReparto;
	}
	public String getNombreNormaReparto() {
		return nombreNormaReparto;
	}
	public void setNombreNormaReparto(String nombreNormaReparto) {
		this.nombreNormaReparto = nombreNormaReparto;
	}
	public String getCuentaContable() {
		return cuentaContable;
	}
	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
	public String getNombreCuentaContable() {
		return nombreCuentaContable;
	}
	public void setNombreCuentaContable(String nombreCuentaContable) {
		this.nombreCuentaContable = nombreCuentaContable;
	}
	public String getRazonNegocio() {
		return razonNegocio;
	}
	public void setRazonNegocio(String razonNegocio) {
		this.razonNegocio = razonNegocio;
	}
	public float getEnero() {
		return enero;
	}
	public void setEnero(float enero) {
		this.enero = enero;
	}
	public float getFebrero() {
		return febrero;
	}
	public void setFebrero(float febrero) {
		this.febrero = febrero;
	}
	public float getMarzo() {
		return marzo;
	}
	public void setMarzo(float marzo) {
		this.marzo = marzo;
	}
	public float getAbril() {
		return abril;
	}
	public void setAbril(float abril) {
		this.abril = abril;
	}
	public float getMayo() {
		return mayo;
	}
	public void setMayo(float mayo) {
		this.mayo = mayo;
	}
	public float getJunio() {
		return junio;
	}
	public void setJunio(float junio) {
		this.junio = junio;
	}
	public float getJulio() {
		return julio;
	}
	public void setJulio(float julio) {
		this.julio = julio;
	}
	public float getAgosto() {
		return agosto;
	}
	public void setAgosto(float agosto) {
		this.agosto = agosto;
	}
	public float getSeptiembre() {
		return septiembre;
	}
	public void setSeptiembre(float septiembre) {
		this.septiembre = septiembre;
	}
	public float getOctubre() {
		return octubre;
	}
	public void setOctubre(float octubre) {
		this.octubre = octubre;
	}
	public float getNoviembre() {
		return noviembre;
	}
	public void setNoviembre(float noviembre) {
		this.noviembre = noviembre;
	}
	public float getDiciembre() {
		return diciembre;
	}
	public void setDiciembre(float diciembre) {
		this.diciembre = diciembre;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
}
