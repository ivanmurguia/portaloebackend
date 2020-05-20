package recursos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import dao.DaoRecibos;

@Path("/recibos")
public class Recibos {
	@GET
	@Path("/crear/{idrequisicion}")
	public Response create(@PathParam("idrequisicion") int idrequisicion) {
		DaoRecibos dao = new DaoRecibos();
		return Response.status(Response.Status.ACCEPTED).entity(dao.crearRecibo(idrequisicion)).build();
	}
}
