package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionBD;

public class AsnLvpDAO {

    ConexionBD db = new ConexionBD();
    
    public boolean searchOrder(String numOrder){
          ResultSet resultSet = null;
          try {
        	  resultSet = db.consultar("SELECT * FROM [192.168.1.26].[SAP_AUX].[dbo].[ASN_LV_CORREOS] as t1\r\n" + 
                                      "WHERE t1.numPedido = '"+numOrder+"' and envioAutorizado is null");
        	  if(resultSet.next()) 
        		  return true;
        	  else
        		  return false;
		} catch (SQLException e) {
			return false;
		}
    }
    
    public boolean updateStatus(String numOrder,byte yesOrNot) {
    	boolean resp = false;
    	try {
    		resp = db.ejecutar("UPDATE [192.168.1.26].[SAP_AUX].[dbo].[ASN_LV_CORREOS] set envioAutorizado = "+yesOrNot+" "+
                               "WHERE numPedido = '"+numOrder+"'");
    	}catch(Exception e) {
             resp = false;
    	}
    	return resp;
    }
}
