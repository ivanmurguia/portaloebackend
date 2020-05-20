package recursos;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import modelo.PrivaliaRegistro;
import dao.DaoPrivalia;

@Path("/privalia")
public class Privalia {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{razon}")
	public Response insertarRegistro(@PathParam("razon") String razon,List<PrivaliaRegistro> registros) {
		DaoPrivalia dao = new DaoPrivalia();
		System.out.println("Razon: " + razon);
		return Response.status(Response.Status.ACCEPTED).entity(dao.insertarRegistro(razon,registros)).build();
	}
}