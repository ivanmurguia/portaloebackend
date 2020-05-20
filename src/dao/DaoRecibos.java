package dao;

import com.sap.smb.sbo.api.IDocument_Lines;
import com.sap.smb.sbo.api.IDocuments;
import com.sap.smb.sbo.api.SBOCOMConstants;
import com.sap.smb.sbo.api.SBOCOMException;
import com.sap.smb.sbo.api.SBOCOMUtil;

import conexion.ConexionBD;
import conexion.ConexionSap;
import modelo.Requisicion;

public class DaoRecibos {
	public String crearRecibo(int idrequisicion) {
		DaoRequisicion daorequisicion = new DaoRequisicion();
		Requisicion requisicion = new Requisicion();
		requisicion = daorequisicion.buscarRequisicion(idrequisicion);
		
		if(requisicion.getEstatus().getId() != 6) {
			return "Esta requisicion aun no se envia a SAP.";
		}
		
	    ConexionSap sap = null;
	    
	    switch(requisicion.getSociedad().getId()) {
	    	case 1:
	    		sap = new ConexionSap("OE_MODA2017");
	    	break;
	    	case 2:
	    		sap = new ConexionSap("Cloe_Personale");
	    		sap.company.setPassword("alex");
	    	break;
	    	case 3:
	    		sap = new ConexionSap("CALZADISSIMO_PROD");
	    	break;
	    	case 4:
	    		sap = new ConexionSap("IDM_PROD");
	    	break;
	    	case 5:
	    		sap = new ConexionSap("CARUVE_PROD");
	    	break;
	    	case 6:
	    		sap = new ConexionSap("Oe_Moda_RetailPruebas");
	    	break;
	    }
	    
	    sap.conectar();
		
	    try {
	    	System.out.println(Integer.parseInt(requisicion.getDocEntry()));
			IDocuments order = SBOCOMUtil.getDocuments(sap.company, SBOCOMConstants.BoObjectTypes_Document_oPurchaseOrders, Integer.parseInt(requisicion.getDocEntry()));
			
			if(order.getByKey(Integer.parseInt(requisicion.getDocEntry())) == true){
				IDocuments delivery = SBOCOMUtil.newDocuments(sap.company, SBOCOMConstants.BoObjectTypes_Document_oPurchaseDeliveryNotes);
				
				if(requisicion.getMoneda().getId() == 2) {
					delivery.setDocCurrency("USD");
					if(requisicion.getTc() > 0){
						delivery.setDocRate(Double.parseDouble(String.valueOf(requisicion.getTc())));
					}
				}
				
				delivery.setCardCode(requisicion.getProveedor().getCardCode());
				delivery.setNumAtCard("Oe-" + requisicion.getId());
				delivery.setDocDate(order.getDocDate());
				delivery.setDocDueDate(order.getDocDueDate());
				
				delivery.setComments(order.getComments());
				
				IDocument_Lines detail = order.getLines();
				
				for(int i = 0; i < detail.getCount(); i++) {
					detail.setCurrentLine(i);
					delivery.getLines().setBaseEntry(Integer.parseInt(requisicion.getDocEntry()));
					delivery.getLines().setBaseLine(i);
					delivery.getLines().setBaseType(22);
					
					delivery.getLines().setItemCode(detail.getItemCode());
					delivery.getLines().setWarehouseCode(detail.getWarehouseCode());
					delivery.getLines().setTaxCode(detail.getTaxCode());
					delivery.getLines().setItemDescription(detail.getItemDescription());
					delivery.getLines().setCurrency(detail.getCurrency());
					
					delivery.getLines().setQuantity(detail.getQuantity());
					delivery.getLines().setPrice(detail.getPrice());
//					delivery.getLines().setLineTotal(detail.getLineTotal());
					
					switch(requisicion.getArea().getDimension()) {
						case "1":
							delivery.getLines().setCostingCode(requisicion.getArea().getNormaReparto());
							delivery.getLines().setCOGSCostingCode(requisicion.getArea().getNormaReparto());	
						break;
						case "2":
							delivery.getLines().setCostingCode2(requisicion.getArea().getNormaReparto());
							delivery.getLines().setCOGSCostingCode2(requisicion.getArea().getNormaReparto());	
						break;
						case "3":
							delivery.getLines().setCostingCode3(requisicion.getArea().getNormaReparto());
							delivery.getLines().setCOGSCostingCode3(requisicion.getArea().getNormaReparto());	
						break;
						case "4":
							delivery.getLines().setCostingCode4(requisicion.getArea().getNormaReparto());
							delivery.getLines().setCOGSCostingCode4(requisicion.getArea().getNormaReparto());	
						break;
						case "5":
							delivery.getLines().setCostingCode5(requisicion.getArea().getNormaReparto());
							delivery.getLines().setCOGSCostingCode5(requisicion.getArea().getNormaReparto());	
						break;
					}

					delivery.getLines().setUnitPrice(detail.getUnitPrice());
					delivery.getLines().add();
				}
				
				if(delivery.add() == 0) {
					
					String newDocEntryDelivery = sap.company.getNewObjectCode();
					
					IDocuments newDelivery = SBOCOMUtil.getDocuments(sap.company, SBOCOMConstants.BoObjectTypes_Document_oPurchaseDeliveryNotes, Integer.parseInt(newDocEntryDelivery));
					
					ConexionBD db = new ConexionBD();
					
					db.ejecutar("update requisiciones set recibo = " + newDelivery.getDocNum() + " where id = " + requisicion.getId());
					
					sap.company.disconnect();
					return "El folio de tu recibo es " + newDelivery.getDocNum();
				} else {
					sap.company.disconnect();
					return "Error en SAP: " + sap.company.getLastErrorDescription();
				}
			}else {
				return "Ocurrio un error.";
			}

		} catch (SBOCOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sap.company.disconnect();
			return "Error SAP: " + e.getMessage();
		}
	}
}
