package recursos;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DaoRequisicion;
import modelo.Requisicion;

@Path("/requisiciones")
public class Requisiciones {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{idRequisicion}")
	public Response findRequisicionById(@PathParam("idRequisicion") int id) {
		DaoRequisicion dao = new DaoRequisicion();
		return Response.status(Response.Status.ACCEPTED).entity(dao.findRequisicionById(id)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/autorizadas")
	public Response findauthorized(){
		DaoRequisicion dao = new DaoRequisicion();
		return Response.status(Response.Status.ACCEPTED).entity(dao.findauthorized()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/validar")
	public Response validate(Requisicion requisicion) {
		DaoRequisicion dao = new DaoRequisicion();
		return Response.status(Response.Status.ACCEPTED).entity(dao.validar(requisicion)).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/rechazar/{idrequisicion}/{motivo}")
	public Response toRefuse(@PathParam("idrequisicion") int idrequisicion, @PathParam("motivo") String motivo) {
		DaoRequisicion dao = new DaoRequisicion();
		return Response.status(Response.Status.ACCEPTED).entity(dao.rechazar(idrequisicion, motivo)).build();
	}
	
	@GET
	@Path("/cancelar/{idrequisicion}")
	public Response cancel(@PathParam("idrequisicion") int idrequisicion) {
		DaoRequisicion dao = new DaoRequisicion();
		System.out.println("Se va a cancelar " + idrequisicion);
		return Response.status(Response.Status.ACCEPTED).entity(dao.cancelar(idrequisicion)).build();
	}
}
