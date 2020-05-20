package recursos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import dao.AsnLvpDAO;

@Path("/authAsn")
public class AsnLvp  {
       @GET
       @Path("/{numOrder}/{yesOrNot}")
       public String authAsn(@PathParam("numOrder") String numOrder, @PathParam("yesOrNot") byte yesOrNot) {
         AsnLvpDAO dao = new AsnLvpDAO();
         if(dao.searchOrder(numOrder))
        	 if(yesOrNot == 1 || yesOrNot == 0)
        		 if(dao.updateStatus(numOrder, yesOrNot)) 
        			 if(yesOrNot == 1) 
        				 return "Se ha autorizado la liberacion incompleta del pedido! "+numOrder;
        			 else
        				 return "Se ha denegado la liberacion incomplet del pedido! "+numOrder;
        		 else 
        			 return "Error al enviar su respuesta, Vuelva a intentarlo!";
        	 else
        		 return "Valor invalido, vuelva a intentarlo\n1 = AUTORIZO\n0 = NO AUTORIZO";
         return "El pedido "+numOrder+" no se encuentra en la lista de pedidos por autorizar!";        
       }      
}
