package recursos;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import modelo.Area;
import modelo.Articulo;
import modelo.Departamento;
import modelo.Presupuesto;
import modelo.Producto;
import modelo.Proveedor;
import modelo.Requisicion;
import modelo.TipoUsuario;
import modelo.Usuario;
import servicio.ArticuloServicio;

@Path("/articulos")
public class ArticuloRecurso {
	ArticuloServicio servicio = new ArticuloServicio();
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/tcSAP/{anio}/{mes}/{dia}")
	public float tcSAP(@PathParam("anio") String anio, @PathParam("mes") String mes, @PathParam("dia") String dia) {
		String fecha = anio+"/"+mes+"/"+dia;
		return servicio.tcSAP(fecha);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/presupuestoArea/{idUsuario}/{idTipoUsuario}/{normaReparto}")
	public List<Presupuesto> presupuestoArea(@PathParam("idUsuario") String idUsuario,@PathParam("idTipoUsuario") String idTipoUsuario, @PathParam("normaReparto") String normaReparto){
		return servicio.presupuestoArea(idUsuario,idTipoUsuario,normaReparto);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/olvidoContrasena/{token}/{pass}")
	public String olvidoContrasena(@PathParam("token") String token, @PathParam("pass") String contrasena){
		return servicio.olvidoContrasena(token, contrasena);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/mailOlvidoContrasena/{user}")
	public String mailOlvidoContrasena(@PathParam("user") String user){
		return servicio.mailOlvidoContrasena(user);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/crearOrdenSAP/{idRequisicion}")
	public String crearOrdenSAP(@PathParam("idRequisicion") int idRequisicion) {
		return servicio.crearOrdenSAP(idRequisicion);
	}
	
	@POST
	@Path("/cargarPresupuesto")
	@Consumes(MediaType.TEXT_PLAIN)
	public void cargarPresupuesto(String json) {
		servicio.cargarPresupuesto(json);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/verificarPresupuesto/{anio}/{sociedad}/{idProducto}/{normaReparto}/{mes}/{monto}")
	public boolean verificarPresupuesto(@PathParam("anio") String anio,@PathParam("sociedad") String sociedad,@PathParam("idProducto") int idProducto,@PathParam("normaReparto") String normaReparto,@PathParam("mes") String mes,@PathParam("monto") float monto) {
		return servicio.verificarPresupuesto(anio, sociedad, idProducto, normaReparto, mes, monto);
	}
	
	@POST
	@Path("/nuevaRequisicion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public int crearRequisicion(Requisicion requisicion) {
		int nueva = servicio.crearRequisicion(requisicion);
		return nueva;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/autorizacion/{idrequisicion}/{estatus}")
	public String actualizarEstatus(@PathParam("idrequisicion") int idrequisicion, @PathParam("estatus") int estatus) {
		return servicio.actualizarEstatus(idrequisicion, estatus);
	}
	
	@GET
	@Path("/importarUsuarios")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> importarUsuarios() {
		return servicio.importarUsuarios();
	}
	
	@GET
	@Path("/buscarUsuarioU/{usuario}")
	public Usuario buscarUsuarioU(@PathParam("usuario") String usuario) {
		return servicio.buscarUsuarioU(usuario);
	}
	
	@POST
	@Path("/nuevoUsuario")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public int crearUsuario(Usuario usuario) {
		System.out.println("Id tipo usuario: " + usuario.getTipousuario().getId());
		return servicio.crearUsuario(usuario);
	}
	
	@POST
	@Path("/actualizarUsuario")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public int actualizarUsuario(Usuario usuario) {
		return servicio.actualizarUsuario(usuario);
	}
	
	@POST
	@Path("/cambioContrasena")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public int cambioContrasena(Usuario usuario) {
		return servicio.cambioContrasena(usuario);
	}
	
	@POST
	@Path("/olvidoContrasena")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public int olvidoContrasena(Usuario usuario) {
		return servicio.cambioContrasena(usuario);
	}
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Articulo> getArticulos() {
		return servicio.getArticulos();
	}

	@GET
	@Path("/getAllProductsS/{idSociedad}/{esAdministrativo}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> getProductosS(@PathParam("idSociedad") int idsociedad, @PathParam("esAdministrativo") int esAdministrativo) {
		return servicio.getProductosS(idsociedad, esAdministrativo);
	}
	
	@GET
	@Path("/getAllDepartaments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Departamento> getDepartamentos() {
		return servicio.getDepartamento();
	}
	
	@GET
	@Path("/getDepartamenUs/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Departamento> getDepartamentosEncargado(@PathParam("id") int idEncargado) {
		return servicio.importarDepartamentosEncargado(idEncargado);
	}
	
	@GET
	@Path("/actualizarDepartamento/{id}/{usuario}")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean actualizarDepartamento(@PathParam("id") int id, @PathParam("usuario") String usuario) {
		return servicio.actualizarDepartamento(id, usuario);
	}
	
	@GET
	@Path("/actualizarMontoDepartamento/{id}/{monto}")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean actualizarMonto(@PathParam("id") int id, @PathParam("monto") float monto) {
		return servicio.actualizarMonto(id, monto);
	}
	
	@GET
	@Path("/importarDepartamentos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Departamento> importarDepartamentos() {
		return servicio.importarDepartamentos();
	}
	
	@GET
	@Path("/getMonto/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public float getMonto(@PathParam("id") int id) {
		return servicio.getMonto(id);
	}
	
	@GET
	@Path("/importarTiposUsuario")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TipoUsuario> importarTiposUsuario() {
		return servicio.importarTiposUsuario();
	}
	
	@GET
	@Path("/getAllAreas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Area> getAreas() {
		return servicio.getAreas();
	}
	
	@GET
	@Path("/importarAreas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Area> importarAreas() {
		return servicio.importarAreas();
	}
	
	@GET
	@Path("/actualizarArea/{id}/{usuario}")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean actualizarArea(@PathParam("id") int id, @PathParam("usuario") String usuario) {
		return servicio.actualizarArea(id, usuario);
	}
	
	@GET
	@Path("/getAreas/{idDepartamento}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Area> getArticulo(@PathParam("idDepartamento") int id) {
		return servicio.buscarAreas(id);
	}
	
	@GET
	@Path("/getRequisicionesUsuario/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Requisicion> getRequisicionesUsuario(@PathParam("idUsuario") int id) {
		return servicio.importarRequisicionesUsuario(id);
	}
	
	@GET
	@Path("/getRequisicion/{idRequisicion}")
	@Produces(MediaType.APPLICATION_JSON)
	public Requisicion getRequisicion(@PathParam("idRequisicion") int id) {
		return servicio.importarRequisicion(id);
	}
	
	@GET
	@Path("/getProveedoresS/{idSociedad}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Proveedor> getProveedores(@PathParam("idSociedad") int idsociedad) {
		return servicio.getProveedores(idsociedad);
	}
	
//	@GET
//	@Path("/getUsers")
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Usuario> getUsuarios() {
//		return servicio.getUsuarios();
//	}
//	
//	@GET
//	@Path("/getOne/{articuloId}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Articulo getArticulo(@PathParam("articuloId") int id) {
//		return servicio.getArticulo(id);
//	}
	
	@GET
	@Path("/getAreaId/{idArea}")
	@Produces(MediaType.APPLICATION_JSON)
	public Area getArea(@PathParam("idArea") int id) {
		return servicio.buscarArea(id);
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario validateLogin(Usuario usuario) {
		Usuario usuarioResp = servicio.getLogin(usuario.getUsuario(), usuario.getContrasena());
		if(usuarioResp != null) {
			usuarioResp.setContrasena("****");
			return usuarioResp;
		}else {
			return null;
		}
	}

	@GET
	@Path("/importarUsuariosTipo/{idTipo}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> importarUsuariosTipo(@PathParam("idTipo") int idTipo) {
		return servicio.importarUsuariosTipo(idTipo);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Articulo addArticulo(Articulo articulo) {
		return servicio.addArticulo(articulo);
	}
	
	@GET
	@Path("/reenvioCorreo/{idRequisicion}")
	public void ReenviarCorreo(@PathParam("idRequisicion") int idRequisicion) {
		System.out.println("reenvio de correo de la requi: " + idRequisicion);
		servicio.reenviarCorreo(idRequisicion);
		
	}
}
