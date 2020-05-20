package recursos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DaoUsuarios;

@Path("/usuarios")
public class Usuarios {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{idUsuario}")
	public Response insertarRegistro(@PathParam("idUsuario") int usuario) {
		DaoUsuarios dao = new DaoUsuarios();
		return Response.status(Response.Status.ACCEPTED).entity(dao.permisos(usuario)).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{idUsuario}/{idSubmodulo}/{permiso}")
	public Response insertarPermiso(@PathParam("idUsuario") int usuario, @PathParam("idSubmodulo") int submodulo, @PathParam("permiso") int permiso) {
		DaoUsuarios dao = new DaoUsuarios();
		dao.permisoUsuarioSubmodulo(usuario, submodulo, permiso);
		return Response.status(Response.Status.ACCEPTED).entity("Actualizado con exito").build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/up/{usuario}/{idSubmodulo}/{permiso}")
	public Response insertarPermiso(@PathParam("usuario") String usuario, @PathParam("idSubmodulo") int submodulo, @PathParam("permiso") int permiso) {
		DaoUsuarios dao = new DaoUsuarios();
		dao.permisoUsuarioSubmodulo(usuario, submodulo, permiso);
		return Response.status(Response.Status.ACCEPTED).entity("Actualizado con exito").build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/permiso/{idUsuario}/{submodulo}")
	public Response insertarPermiso(@PathParam("idUsuario") int idUsuario, @PathParam("submodulo") String href) {
		DaoUsuarios dao = new DaoUsuarios();
		return Response.status(Response.Status.ACCEPTED).entity(dao.permisoUsuarioSubmodulo(idUsuario, href)).build();
	}
}