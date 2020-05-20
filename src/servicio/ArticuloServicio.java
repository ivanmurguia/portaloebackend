package servicio;

import java.util.List;

import dao.DaoArea;
import dao.DaoDepartamento;
import dao.DaoPresupuesto;
import dao.DaoProducto;
import dao.DaoProveedor;
import dao.DaoRequisicion;
import dao.DaoUsuarios;
import dao.DataBase;
import modelo.Area;
import modelo.Articulo;
import modelo.Departamento;
import modelo.Presupuesto;
import modelo.Producto;
import modelo.Proveedor;
import modelo.Requisicion;
import modelo.Usuario;
import modelo.TipoUsuario;

public class ArticuloServicio {
	
	private DaoUsuarios daousuario = new DaoUsuarios();
	
	private List<Articulo> listado = DataBase.getInstancia().getListado();
	private DaoProducto daoproducto = new DaoProducto();
	private List<Departamento> listadoDepartamentos = DaoDepartamento.getInstancia().getListado();
	private DaoDepartamento daodepartamento = new DaoDepartamento();
	private DaoArea daoarea = new DaoArea();
	private DaoProveedor daoProveedor = new DaoProveedor();
	
	private DaoPresupuesto daopresupuesto = new DaoPresupuesto();
	
	public void reenviarCorreo(int idRequisicion) {
		DaoRequisicion requisicion = new DaoRequisicion();
		requisicion.reenviarCorreo(idRequisicion);
	}
	
	public float tcSAP(String fecha) {
		return daopresupuesto.tcSAP(fecha);
	}
	
	public String mailOlvidoContrasena(String user) {
		return daousuario.mailOlvidoContrasena(user);
	}
	
	public String olvidoContrasena(String token, String contrasena) {
		return daousuario.olvidoContrasena(token, contrasena);
	}
	
	public List<Presupuesto> presupuestoArea(String idUsuario, String idTipoUsuario,String normaReparto){
		return daopresupuesto.presupuestoArea(idUsuario, idTipoUsuario, normaReparto);
	}
	
	public String crearOrdenSAP(int idRequisicion){
		DaoRequisicion requisicion = new DaoRequisicion();
		return requisicion.crearOrdenSAP(idRequisicion);
	}
	
	public void cargarPresupuesto(String json) {
		daopresupuesto.cargarPresupuesto(json);
	}
	
	public boolean verificarPresupuesto(String anio, String sociedad, int idProducto, String normaReparto, String mes, float monto) {
		return daopresupuesto.verificarPresupuesto(anio, sociedad, idProducto, normaReparto, mes, monto);
	}
	
	public String actualizarEstatus(int idrequisicion, int estatus) {
		DaoRequisicion requisicion = new DaoRequisicion();
		return requisicion.actualizarEstatus(idrequisicion, estatus);
	}
	
	public int crearRequisicion(Requisicion requi) {
		DaoRequisicion requisicion = new DaoRequisicion();
		int nueva = requisicion.crearRequisicion(requi);
		return nueva;
	}
	
	public Requisicion importarRequisicion(int idrequisicion){
		DaoRequisicion requisicion = new DaoRequisicion();
		return requisicion.buscarRequisicion(idrequisicion);
	}
	
	public Usuario getLogin(String user, String pass) {
		Usuario usuario = daousuario.comprobarLogin(user, pass);
		return usuario;
	}
	
	public int crearUsuario(Usuario usuario) {
		return daousuario.crearUsuario(usuario);
	}
	
	public int actualizarUsuario(Usuario usuario) {
		return daousuario.actualizarUsuario(usuario);
	}
	
	public int cambioContrasena(Usuario usuario) {
		return daousuario.cambioContrasena(usuario);
	}
	
	public List<Usuario> importarUsuariosTipo(int idTipo) {
		return daousuario.importarUsuariosTipo(idTipo);
	}
	
	public List<Usuario> importarUsuarios(){
		return daousuario.importarUsuarios();
	}
	
	public List<TipoUsuario> importarTiposUsuario(){
		return daousuario.importarTiposUsuario();
	}
	
	public List<Proveedor> getProveedores(int idsociedad){
		return daoProveedor.getListado(idsociedad);
	}
	
	public Usuario buscarUsuarioU(String usuario) {
		return daousuario.buscarUsuarioU(usuario);
	}
	
	public List<Requisicion> importarRequisicionesUsuario(int idusuario){
		DaoRequisicion requisicion = new DaoRequisicion();
		return requisicion.importarRequisicionesUsuario(idusuario);
	}
	
	public List<Area> getAreas(){
		DaoArea daoarea = new DaoArea();
		return daoarea.getListado();
	}
	
	public List<Producto> getProductosS(int idSociedad, int esAdministrativo){
		return daoproducto.getListadoS(idSociedad, esAdministrativo);
	}
	
	public List<Articulo> getArticulos(){
		return listado;
	}
	
	public List<Departamento> getDepartamento(){
		return listadoDepartamentos;
	}
	
	public List<Departamento> importarDepartamentos(){
		return daodepartamento.importarDepartamentos();
	}
	
	public List<Departamento> importarDepartamentosEncargado(int idEncargado){
		return daodepartamento.buscarDepartamentosPorEncargado(idEncargado);
	}
	
	public boolean actualizarDepartamento(int id, String usuario) {
		return daodepartamento.actualizarDepartamento(id, usuario);
	}
	
	public boolean actualizarMonto(int id, float monto) {
		return daodepartamento.actualizarMonto(id, monto);
	}
	
	public float getMonto(int id) {
		return daodepartamento.getMonto(id);
	}
	
	
	public List<Area> buscarAreas(int departamento){
		DaoArea daoarea = new DaoArea(departamento);
		return daoarea.getListado();
	}
	
	public List<Area> importarAreas(){
		return daoarea.importarAreas();
	}
	
	public Area buscarArea(int id) {
		DaoArea daoarea = new DaoArea();
		Area area = daoarea.buscarArea(id);
		return area;
	}
	
	public boolean actualizarArea(int id, String usuario) {
		return daoarea.actualizarArea(id, usuario);
	}
	
	public Articulo  getArticulo(int id) {
		for(Articulo articulo : listado) {
			if(articulo.getId()==id) {
				return articulo;
			}
		}
		return null;	
	}
	
	public Articulo addArticulo(Articulo articulo) {
		articulo.setId(getMaximo());
		listado.add(articulo);
		return articulo;
	}
	
	public Articulo updateArticulo(Articulo articulo) {
		int posicion = getPosicion(articulo.getId());
		try {
			listado.set(posicion, articulo);
		}catch(IndexOutOfBoundsException e) {
			return null;
		}
		return articulo;
	}
	
	public void deleteArticulo(int id) {
		int posicion = getPosicion(id);
		listado.remove(posicion);
	}
	private int getPosicion(int id) {
		for(int i = 0;i<listado.size();i++) {
			if(listado.get(i).getId() == id) {
				return i;
			}
		}
		return -1;
	}
	
	private int getMaximo() {
		int size = listado.size();
		if(size >0 ) {
			return listado.get(size-1).getId()+1;
		}else {
			return 1;
		}
	}
	
}
