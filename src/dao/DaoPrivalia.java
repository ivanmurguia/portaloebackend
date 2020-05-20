package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sap.smb.sbo.api.IDocuments;
import com.sap.smb.sbo.api.SBOCOMConstants;
import com.sap.smb.sbo.api.SBOCOMException;
import com.sap.smb.sbo.api.SBOCOMUtil;

import conexion.ConexionBD;
import conexion.ConexionSap;
import modelo.PrivaliaRegistro;

public class DaoPrivalia {
	public List<PrivaliaRegistro> insertarRegistro(String razon, List<PrivaliaRegistro> registro) {
		try {
			ConexionBD db = new ConexionBD();
			int numreg = registro.size();
			for(int i = 0; i < numreg; i++) {
				String itemCode = barsToSku(registro.get(i).getEan());
				db.consultar("SELECT * FROM [192.168.1.26].SAP_AUX.dbo.[MKP_ORDERS_PRIVALIA] WHERE "
						   + "ORDERS_NUMBER = '"+registro.get(i).getPedido()+"' and "
						   + "ITEMCODE = '" + itemCode + "'");
				if(!db.resultado.next()) {
					System.out.println("Insertando el pedido: " + registro.get(i).getPedido());
					db.ejecutar(" Insert into [192.168.1.26].SAP_AUX.dbo.[MKP_ORDERS_PRIVALIA] "
			                  + " ([ORDERS_NUMBER],[ITEMCODE],[QUANTITY],[PRICE],[CREATED_AT],[TRACKING_CODE],[STATUS],[PRIVALIA_SKU],[CODE_BARS])"
			                  + " values ('" 
			                  + registro.get(i).getPedido() + "','" 
			                  + itemCode + "',"
			                  + registro.get(i).getPiezas() + ","
			                  + registro.get(i).getCoste() + ",'"
			                  + registro.get(i).getDia() + "','"
			                  + registro.get(i).getGuias() + "',"
			                  + 1 + ",'"
			                  + registro.get(i).getReferencia() + "','"
			                  + registro.get(i).getEan() + "'"
			                  + ")");
					registro.get(i).setStatus("Se inserto el registro con exito");
					System.out.println("Se inserto el registro con exito");
				} else {
					registro.get(i).setStatus("Ya existe este registro");
					System.out.println("Ya existe este registro");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderSAP(razon, registro);
	}
	private List<PrivaliaRegistro> orderSAP(String razon, List<PrivaliaRegistro> registro) {
		ConexionBD db = new ConexionBD();
		ConexionSap sap;
		String cliente = "";
		int serie = 0;
		if(razon.equals("cloe")) {
			sap = new ConexionSap("OE_MODA2017");
			cliente = "C-03406";
			serie = 9;
		}else if(razon.equals("calzado")) {
			sap = new ConexionSap("CALZADISSIMO_PROD");
			cliente = "C-00185";
			serie = 78;
		}else {
			sap = null;
		}
		
		int numreg = registro.size();
		if(numreg > 0) {
			sap.conectar();
		}
		for(int i = 0; i < numreg; i++) {
			try {
				IDocuments MarketPlace = SBOCOMUtil.newDocuments(sap.company, SBOCOMConstants.BoObjectTypes_Document_oOrders);
				db.consultar(" SELECT [ORDERS_NUMBER],[TRACKING_CODE],[STATUS],[ID_SAP] "
						   + " FROM [192.168.1.26].[SAP_AUX].[dbo].[MKP_ORDERS_PRIVALIA] "
	                       + " WHERE ORDERS_NUMBER = " + registro.get(i).getPedido()
	                       + " group by ORDERS_NUMBER,TRACKING_CODE,STATUS,ID_SAP");
				ResultSet ordenes = db.resultado;
				while(ordenes.next()) {
					if(ordenes.getString(3).equals("2")) {
						System.out.println("Ya exite en sap la orden " + registro.get(i).getPedido());
						registro.get(i).setStatus("SAP-"+docEntrytoDocNum(razon,ordenes.getString(4)));
					} else {
						System.out.println("Se va a crear en SAP " + registro.get(i).getPedido());
						MarketPlace.setDocDate(new Date());
						MarketPlace.setDocDueDate(sumarRestarDiasFecha(new Date(), 1));
						MarketPlace.setCardCode(cliente);
						MarketPlace.setNumAtCard(ordenes.getString(1) + " PRIVALIA");
						MarketPlace.getUserFields().getFields().item("U_NoRequ").setValue(ordenes.getString(1));
						MarketPlace.getUserFields().getFields().item("U_NumdeGuia").setValue(ordenes.getString(2));
						MarketPlace.setDiscountPercent(0.0);
						MarketPlace.setSeries(serie);
						db.consultar("SELECT  [ID],[ITEMCODE],[QUANTITY],[PRICE] "
	                            + " FROM [192.168.1.26].[SAP_AUX].[dbo].[MKP_ORDERS_PRIVALIA] "
	                            + " WHERE ORDERS_NUMBER = '" + ordenes.getString(1) + "'");
						while (db.resultado.next()) {
							MarketPlace.getLines().setItemCode(db.resultado.getString(2));
							MarketPlace.getLines().setQuantity(db.resultado.getDouble(3));
							MarketPlace.getLines().getUserFields().getFields().item("U_LXP_DocEntry").setValue(db.resultado.getString(1));
							MarketPlace.getLines().setWarehouseCode("39");
							MarketPlace.getLines().setTaxCode("IVAV16");
							MarketPlace.getLines().getUserFields().getFields().item("OcrCode5").setValue("1310");
							MarketPlace.getLines().getUserFields().getFields().item("CogsOcrCo5").setValue("1310");
							MarketPlace.getLines().setLineTotal(db.resultado.getDouble(4));
							MarketPlace.getLines().add();
						}
						if (MarketPlace.add() != 0) {
							registro.get(i).setStatus(sap.company.getLastErrorDescription());
							db.ejecutar("insert into [192.168.1.26].[SAP_AUX].[dbo].[Bitac_Privalia]([NoOrden],[Error],[Fecha],[ModuloError])" 
	                                + " VALUES('"+registro.get(i).getPedido()+"','"+sap.company.getLastErrorDescription()+"',GETDATE(),'OrderSAP')");
						} else {
							db.ejecutar(" UPDATE  [192.168.1.26].[SAP_AUX].[dbo].[MKP_ORDERS_PRIVALIA] "
                                    + " SET "
                                    + " [ID_SAP] = " + sap.company.getNewObjectCode()
                                    + ",[UPDATE_AT] = GETDATE()"
                                    + ",[STATUS] = 2"
                                    + " WHERE ORDERS_NUMBER = '" + registro.get(i).getPedido() + "'");
							registro.get(i).setStatus("SAP-"+docEntrytoDocNum(razon,sap.company.getNewObjectKey()));
						}
					}
				}
			} catch (SBOCOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return registro;
	}
	private String barsToSku(String bars) {
		ConexionBD db = new ConexionBD();
		db.consultar("SELECT ItemCode FROM [192.168.1.26].OE_MODA2017.[dbo].[OITM] WHERE CodeBars='" + bars + "'");
		try {
			if(db.resultado.next()) {
				return db.resultado.getString("ItemCode");
			} else {
				return "";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
    public static Date sumarRestarDiasFecha(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }
    private String docEntrytoDocNum(String razon,String docEntry) {
    	ConexionBD db = new ConexionBD();
		if(razon.equals("cloe")) {
			db.consultar("select docnum from [192.168.1.26].OE_MODA2017.[dbo].[ORDR] where docentry = " + docEntry);
		}else if(razon.equals("calzado")) {
			db.consultar("select docnum from [192.168.1.26].CALZADISSIMO_PROD.[dbo].[ORDR] where docentry = " + docEntry);
		}else {
			return "";
		}
    	try {
			if(db.resultado.next()) {
				return db.resultado.getString("docnum");
			}else {
				return "";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
    }
}
