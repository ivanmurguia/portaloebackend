package recursos;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DaoPresupuesto;

@Path("/presupuestos")
public class Presupuestos {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{idDepartamento}")
	public Response presupuestoDepartamento(@PathParam("idDepartamento") int id) {
		DaoPresupuesto dao = new DaoPresupuesto();
		return Response.status(Response.Status.ACCEPTED).entity(dao.presupuestoDepartamento(id)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/gastos/{idDepartamento}")
	public Response gastosDepartamento(@PathParam("idDepartamento") int id) {
		DaoPresupuesto dao = new DaoPresupuesto();
		return Response.status(Response.Status.ACCEPTED).entity(dao.gastosDepartamento(id)).build();
	}
	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/autorizadas")
//	public Response findauthorized(){
//		DaoRequisicion dao = new DaoRequisicion();
//		return Response.status(Response.Status.ACCEPTED).entity(dao.findauthorized()).build();
//	}
//	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/validar")
//	public Response validate(Requisicion requisicion) {
//		DaoRequisicion dao = new DaoRequisicion();
//		return Response.status(Response.Status.ACCEPTED).entity(dao.validar(requisicion)).build();
//	}
}
