package recursos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.multipart.FormDataParam;

import dao.DaoCotizacion;
import dao.DaoSolicitudCotizacion;
import modelo.Cotizacion;
import modelo.SolicitudCotizacion;

@Path("/cotizaciones")
public class Cotizaciones  {
	   @GET
	   @Produces(MediaType.TEXT_PLAIN)
	   @Path("/autorizacion/sc/{idsc}/{estatus}")
	   public String actualizarEstatus(@PathParam("idsc") int idsc, @PathParam("estatus") int estatus) {
		   DaoSolicitudCotizacion dao = new DaoSolicitudCotizacion();
			return dao.actualizarEstatusSolicitud(idsc, estatus);
	   }
	   
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		@Path("/rechazar/{idsc}/{motivo}")
		public Response toRefuse(@PathParam("idsc") int idsc, @PathParam("motivo") String motivo) {
			DaoSolicitudCotizacion dao = new DaoSolicitudCotizacion();
			return Response.status(Response.Status.ACCEPTED).entity(dao.rechazar(idsc, motivo)).build();
		}
	   
       @GET
       @Path("/buscarSolicitudCotizacion/{id}")
       public SolicitudCotizacion buscarCotizacion(@PathParam("id") int id) {
         DaoSolicitudCotizacion dao = new DaoSolicitudCotizacion();
         return dao.buscarSolicitudCotizacion(id);
       }
       @GET
       @Path("/buscarSolicitudesdeCotizacion/{idUsuario}")
       public List<SolicitudCotizacion> buscarSolicitudesdeCotizacion(@PathParam("idUsuario") int idUsuario) {
         DaoSolicitudCotizacion dao = new DaoSolicitudCotizacion();
         return dao.buscarSolicitudesdeCotizacion(idUsuario);
       }
       @POST
       @Path("/nuevaSolicitudCotizacion")
	   @Consumes(MediaType.APPLICATION_JSON)
	   @Produces(MediaType.TEXT_PLAIN)
       public int crearSolicitudCotizacion(SolicitudCotizacion nueva) {
         DaoSolicitudCotizacion dao = new DaoSolicitudCotizacion();
         return dao.crearSolicitudCotizacion(nueva);
       }
       @POST
       @Path("/nuevaCotizacion")
	   @Consumes(MediaType.APPLICATION_JSON)
	   @Produces(MediaType.TEXT_PLAIN)
       public int crearCotizacion(Cotizacion nueva) {
         DaoCotizacion dao = new DaoCotizacion();
         return dao.crearCotizacion(nueva);
       }
       @GET
       @Produces(MediaType.APPLICATION_JSON)
       @Path("/buscarCotizacionesSolicitud/{idCotizacion}")
       public List<Cotizacion> buscarCotizacionesSolicitud( @PathParam("idCotizacion") int idCotizacion){
    	   System.out.println("entro al metodo buscarCotizacionesSolicitud");
           DaoCotizacion dao = new DaoCotizacion();
           return dao.buscarCotizacionesSolicitud(idCotizacion);
       }
       @GET 	
       @Produces(MediaType.APPLICATION_JSON)
       @Path("/buscarCotizacionesU/{idUsuario}")
       public List<Cotizacion> buscarCotizacionesU( @PathParam("idUsuario") int idUsuario){
           DaoCotizacion dao = new DaoCotizacion();
           return dao.buscarCotizacionesU(idUsuario);
       }
       @GET
       @Produces(MediaType.APPLICATION_JSON)
       @Path("/eliminarCotizacion/{idCotizacion}")
       public void eliminarCotizacion( @PathParam("idCotizacion") int idCotizacion){
           DaoCotizacion dao = new DaoCotizacion();
           dao.eliminarCotizacion(idCotizacion);
       }
       @GET
       @Produces(MediaType.TEXT_PLAIN)
       @Path("/cambiarProducto/{idDetalle}/{idProducto}")
       public int cambiarProducto(@PathParam("idDetalle") int idDetalle, @PathParam("idProducto") int idProducto) {
    	   DaoSolicitudCotizacion dao = new DaoSolicitudCotizacion();
    	   return dao.cambiarProducto(idDetalle, idProducto);
       }
       @POST
       @Path("/crearRequisicion")
	   @Consumes(MediaType.APPLICATION_JSON)
	   @Produces(MediaType.TEXT_PLAIN)
       public String crearRequisiciondeCotizacion(List<Cotizacion> listaCotizaciones) {
         DaoCotizacion dao = new DaoCotizacion();
         return dao.crearRequisiciondeCotizacion(listaCotizaciones);
       }
       @POST
       @Consumes(MediaType.MULTIPART_FORM_DATA)
       @Path("/upload/{idCotizacion}")
       public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @PathParam("idCotizacion") int idCotizacion) {
             
		     Properties properties = new Properties();
		     String cdn = "";
    	     
		     try {
				properties.load(getClass().getResourceAsStream("/resources/config.properties"));
				
				cdn = properties.getProperty("cdn");
				
				
				
		     } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Response.status(500).entity("error load properties").build();
		     }
    	     
     		 String uploadedFileLocation = cdn + idCotizacion + ".pdf";
	         
	         writeToFile(uploadedInputStream, uploadedFileLocation);
	         
	         DaoCotizacion daoCotizacion = new DaoCotizacion();
	         daoCotizacion.actualizarDocumento(idCotizacion);
	
	         return Response.status(200).entity("successful").build();
       }
       private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
             try {
                    OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
                    int read = 0;
                    byte[] bytes = new byte[1024];

                    out = new FileOutputStream(new File(uploadedFileLocation));
                    while((read = uploadedInputStream.read(bytes)) != -1) {
                           out.write(bytes, 0, read);
                    }
                    out.flush();
                    out.close();
                    uploadedInputStream.close();
             } catch (IOException e) {
                    e.printStackTrace();
             }
       }
}