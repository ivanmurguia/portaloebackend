package recursos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DaoModulos;


@Path("/modulos")
public class Modulos {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarModulos() {
		DaoModulos dao = new DaoModulos();
		return Response.status(Response.Status.ACCEPTED).entity(dao.consultarModulos()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{usuario}")
	public Response consultarModulos(@PathParam("usuario") String usuario) {
		DaoModulos dao = new DaoModulos();
		return Response.status(Response.Status.ACCEPTED).entity(dao.consultarModulos(usuario)).build();
	}
}
