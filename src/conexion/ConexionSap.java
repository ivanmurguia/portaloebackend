package conexion;

import com.sap.smb.sbo.api.ICompany;
import com.sap.smb.sbo.api.SBOCOMConstants;
import com.sap.smb.sbo.api.SBOCOMUtil;

public class ConexionSap {
	public ICompany company;
	
	public ConexionSap(String baseDatos) {
        company = SBOCOMUtil.newCompany();
//		company.setCompanyDB("OE_MODA2017");
//      company.setCompanyDB("Oe_Moda_RetailPruebas");
        company.setCompanyDB(baseDatos);
		company.setUserName("manager");
		company.setPassword("ALEX");
		company.setServer("OE-SAP01");
        company.setLanguage(SBOCOMConstants.BoSuppLangs_ln_Spanish_La);
        company.setLicenseServer("192.168.1.35:30000");
        company.setDbServerType(SBOCOMConstants.BoDataServerTypes_dst_MSSQL2012);
	}
	
	public void conectar() {
        while(!company.isConnected()) {
            company.connect();
        }
	}
}