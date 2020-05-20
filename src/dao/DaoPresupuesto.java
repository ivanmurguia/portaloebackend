package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import conexion.ConexionBD;
import modelo.Presupuesto;

public class DaoPresupuesto {
	
	public float tcSAP(String fecha) {
		ConexionBD db = new ConexionBD();

		db.consultar("SELECT [Rate] FROM [192.168.1.26].[OE_MODA2017].[dbo].[ORTT] where RateDate = '"+fecha+"' and currency = 'USD'");
		
		try {
			if(db.resultado.next()) {
				return (float) db.resultado.getFloat("Rate");
			}else {
				return (float) 20;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return (float) 20;
		}
	}
	
	public List<Presupuesto> presupuestoArea(String idUsuario, String idTipoUsuario, String normaReparto){
		List<Presupuesto> lista = new ArrayList<>();
		ConexionBD db = new ConexionBD();
		
		if(idTipoUsuario.equals("1")) {
			db.consultar("SELECT t0.* FROM presupuestos t0 " + 
					"left join areas t1 on t0.normaReparto = t1.normaReparto " + 
					"left join departamentos t2 on t1.idDepartamento = t2.id " + 
					"left join usuarios t3 on t2.idUsuario = t3.id " +
					"where t3.id = " + idUsuario);
		}else if(idTipoUsuario.equals("2")){
			db.consultar("SELECT * FROM presupuestos where nombreNormaReparto = '"+normaReparto+"'");
		}else if(idTipoUsuario.equals("4") || idTipoUsuario.equals("6")) {
			db.consultar("select * from presupuestos");
		}else {
			System.out.println("no entro en ninguno");
			return null;
		}
		
		try {
			while(db.resultado.next()){
				Presupuesto presupuesto = new Presupuesto();
				presupuesto.setSociedad(db.resultado.getString(1));
				presupuesto.setAnio(db.resultado.getInt(2));
				presupuesto.setNormaReparto(db.resultado.getString(3));
				presupuesto.setNombreNormaReparto(db.resultado.getString(4));
				presupuesto.setCuentaContable(db.resultado.getString(5));
				presupuesto.setNombreCuentaContable(db.resultado.getString(6));
				presupuesto.setRazonNegocio(db.resultado.getString(7));
				presupuesto.setEnero(db.resultado.getFloat(8));
				presupuesto.setFebrero(db.resultado.getFloat(9));
				presupuesto.setMarzo(db.resultado.getFloat(10));
				presupuesto.setAbril(db.resultado.getFloat(11));
				presupuesto.setMayo(db.resultado.getFloat(12));
				presupuesto.setJunio(db.resultado.getFloat(13));
				presupuesto.setJulio(db.resultado.getFloat(14));
				presupuesto.setAgosto(db.resultado.getFloat(15));
				presupuesto.setSeptiembre(db.resultado.getFloat(16));
				presupuesto.setOctubre(db.resultado.getFloat(17));
				presupuesto.setNoviembre(db.resultado.getFloat(18));
				presupuesto.setDiciembre(db.resultado.getFloat(19));
				presupuesto.setTotal(db.resultado.getFloat(20));
				lista.add(presupuesto);
			}
			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean verificarPresupuesto(String anio, String sociedad, int idProducto, String normaReparto, String mes, float monto) {
		try {
		ConexionBD db = new ConexionBD();
		String cuentaContable = "";
		float cantidad = monto;
		float presupuesto = 0, gasto = 0;
		
		
		
		db.consultar("select cuentaContable from productos where id = " + idProducto);
		
		if(db.resultado.next()) {
			cuentaContable = db.resultado.getString("cuentaContable");
		}else {
			System.out.println("No esta la cyuebta cibvtabkle");
			return false;
		}
		
		db.consultar("select sum("+mes(Integer.valueOf(mes))+") from presupuestos " +
				     " where anio = " + anio + 
				     " and sociedad = '"+sociedad+"' " +
	     		     " and cuentaContable = '"+cuentaContable+"' " +
	     		     " and normaReparto = '"+normaReparto+"'");
		
		if(db.resultado.next()) {
			presupuesto = db.resultado.getFloat(1);
		}else {
			System.out.println("No esata ese presupuesto");
			return false;
		}

		db.consultar("SELECT SUM(DEBIT-CREDIT) FROM [192.168.1.26].["+baseDatos(sociedad)+"].[dbo].[jdt1] "
				   + "where YEAR(duedate) = "+anio+" AND MONTH(duedate) = "+mes+" "
				   + "AND Account = '"+cuentaContable+"' and ProfitCode = '"+normaReparto+"'");

		if(db.resultado.next()) {
			gasto = db.resultado.getFloat(1);
		}
		
		System.out.println("Cantidad " + cantidad + " Presupuesto " + presupuesto + " Gasto " + gasto);
		
		System.out.println("Consulta cuenta contable");
		System.out.println("select cuentaContable from productos where id = " + idProducto);
		
		System.out.println("Consulta presupuesto ");
		System.out.println("select sum("+mes(Integer.valueOf(mes))+") from presupuestos " +
				     " where anio = " + anio + 
				     " and sociedad = '"+sociedad+"' " +
	     		     " and cuentaContable = '"+cuentaContable+"' " +
	     		     " and normaReparto = '"+normaReparto+"'");
		
		System.out.println("Consulta gasto ");
		System.out.println("SELECT SUM(DEBIT-CREDIT) FROM [192.168.1.26].["+baseDatos(sociedad)+"].[dbo].[jdt1] "
				   + "where YEAR(duedate) = "+anio+" AND MONTH(duedate) = "+mes+" "
				   + "AND Account = '"+cuentaContable+"' and ProfitCode = '"+normaReparto+"'");
		
		if(cantidad > (presupuesto - gasto)) {
			System.out.println("No hay presupuesto");
			return false;
		}else {
			System.out.println("Si hay presupuesto");
			return true;
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Hubo un error " + e);
			return false;
		}
	}
	
	private String mes(int mes) {
		switch(mes) {
			case 1:
				return "enero";
			case 2:
				return "febrero";
			case 3:
				return "marzo";
			case 4:
				return "abril";
			case 5:
				return "mayo";
			case 6:
				return "junio";
			case 7:
				return "julio";
			case 8:
				return "agosto";
			case 9:
				return "septiembre";
			case 10:
				return "octubre";
			case 11:
				return "noviembre";
			case 12:
				return "diciembre";
			default:
				return "error";
		}
	}
	@SuppressWarnings("unused")
	private int mes(String mes) {
		switch(mes) {
			case "Enero":
				return 1;
			case "Febrero":
				return 2;
			case "Marzo":
				return 3;
			case "Abril":
				return 4;
			case "Mayo":
				return 5;
			case "Junio":
				return 6;
			case "Julio":
				return 7;
			case "Agosto":
				return 8;
			case "Septiembre":
				return 9;
			case "Octubre":
				return 10;
			case "Noviembre":
				return 11;
			case "Diciembre":
				return 12;
			default:
				return 0;
		}
	}
	private String baseDatos(String sociedad) {
		switch(sociedad) {
			case "Cloe Moda SA de CV":
				return "OE_MODA2017";
			case "Cloe Personale SA de CV":
				return "Cloe_Personale";
			case "Oe_Moda_RetailPruebas":
				return "Oe_Moda_RetailPruebas";
			default:
				return "error";
		}
	}
	
	
	public void cargarPresupuesto(String json) {
		try {
			ConexionBD db = new ConexionBD();
			JSONArray jsonarray = new JSONArray(json);
			JSONObject jsonod = (JSONObject) jsonarray.get(1);
			db.ejecutar("delete presupuestos where anio = " + jsonod.get("anio"));
			
			for(int i = 0; i < jsonarray.length(); i++) {
				JSONObject jsono = (JSONObject) jsonarray.get(i);
				
				db.ejecutar("insert presupuestos values ( "
						+ "'"+jsono.get("sociedad")+"' "
						+ ","+jsono.get("anio")+" "
						+ ",'"+jsono.get("normaReparto")+"' "
						+ ",'"+jsono.get("nombreNormaReparto")+"' "
						+ ",'"+jsono.get("cuentaContable")+"' "
						+ ",'"+jsono.get("nombreCuentaContable")+"' "
						+ ",'"+jsono.get("razonNegocio")+"' "
						+ ","+jsono.get("Enero")
						+ ","+jsono.get("Febrero")
						+ ","+jsono.get("Marzo")
						+ ","+jsono.get("Abril")
						+ ","+jsono.get("Mayo")
						+ ","+jsono.get("Junio")
						+ ","+jsono.get("Julio")
						+ ","+jsono.get("Agosto")
						+ ","+jsono.get("Septiembre")
						+ ","+jsono.get("Octubre")
						+ ","+jsono.get("Noviembre")
						+ ","+jsono.get("Diciembre")
						+ ","+jsono.get("Total")
						+ ")");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Presupuesto presupuestoDepartamento(int idDepartamento){
		Presupuesto pres = new Presupuesto();
		ConexionBD db = new ConexionBD();
		db.consultar("SELECT\r\n" + 
				"      sum(Enero)\r\n" + 
				"      ,sum(Febrero) \r\n" + 
				"      ,sum(Marzo)\r\n" + 
				"      ,sum(Abril)\r\n" + 
				"      ,sum(Mayo)\r\n" + 
				"      ,sum(Junio)\r\n" + 
				"      ,sum(Julio)\r\n" + 
				"      ,sum(Agosto)\r\n" + 
				"      ,sum(Septiembre)\r\n" + 
				"      ,sum(Octubre)\r\n" + 
				"      ,sum(Noviembre)\r\n" + 
				"      ,sum(Diciembre)\r\n" + 
				"  FROM PortalOeTest.dbo.presupuestos t0\r\n" + 
				"  inner join areas t1 on t0.normaReparto = t1.normaReparto\r\n" + 
				"  inner join departamentos t2 on t1.idDepartamento = t2.id\r\n" + 
				"  where anio = 2020 and t2.id = " + idDepartamento);
		try {
			if(db.resultado.next()) {
				pres.setEnero(db.resultado.getFloat(1));
				pres.setFebrero(db.resultado.getFloat(2));
				pres.setMarzo(db.resultado.getFloat(3));
				pres.setAbril(db.resultado.getFloat(4));
				pres.setMayo(db.resultado.getFloat(5));
				pres.setJunio(db.resultado.getFloat(6));
				pres.setJulio(db.resultado.getFloat(7));
				pres.setAgosto(db.resultado.getFloat(8));
				pres.setSeptiembre(db.resultado.getFloat(9));
				pres.setOctubre(db.resultado.getFloat(10));
				pres.setNoviembre(db.resultado.getFloat(11));
				pres.setDiciembre(db.resultado.getFloat(12));
			}
			return pres;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Presupuesto gastosDepartamento(int idDepartamento){
		Presupuesto pres = new Presupuesto();
		ConexionBD db = new ConexionBD();
		db.consultar("select sum([1]) enero,sum([2]) febrero, SUM([3]) marzo, SUM([4]) abril, SUM([5]) mayo, sum([6]) junio, \r\n" + 
				"sum([7]) julio, sum([8]) agosto, sum([9]) septiembre, sum([10]) octubre, sum([11]) noviembre, SUM([12]) diciembre  from (\r\n" + 
				"select * from (\r\n" + 
				"SELECT MONTH(t0.duedate) mes,isnull(SUM(DEBIT-CREDIT),0) monto \r\n" + 
				"FROM [192.168.1.26].[OE_MODA2017].[dbo].[jdt1] t0\r\n" + 
				"inner join areas t1 on t0.ProfitCode = t1.normaReparto collate Latin1_General_CI_AS\r\n" + 
				"inner join departamentos t2 on t1.idDepartamento = t2.id\r\n" + 
				"where YEAR(t0.duedate) = 2020 and t0.account like '601%' and t2.id = "+idDepartamento+" and account collate SQL_Latin1_General_CP850_CI_AS in (\r\n" + 
						"select cuentaContable from presupuestos t0\r\n" + 
						"inner join areas t1 on t0.normaReparto = t1.normaReparto\r\n" + 
						"inner join departamentos t2 on t1.idDepartamento = t2.id\r\n" + 
						"where t2.id = "+idDepartamento+"\r\n" + 
						") \r\n" + 
				"GROUP BY MONTH(duedate)			\r\n" + 
				") as tabla\r\n" + 
				"pivot\r\n" + 
				"(\r\n" + 
				"	sum(monto)\r\n" + 
				"		for mes in ([1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[12])\r\n" + 
				") as pvt\r\n" + 
				"\r\n" + 
				"union\r\n" + 
				"\r\n" + 
				"select * from (\r\n" + 
				"select MONTH(t0.fechaNecesaria) mes, isnull(SUM(precio),0) monto from solicitudcotizaciones t0\r\n" + 
				"inner join solicitudcotizacionesdetalle t1 on t0.id = t1.idSolicitudCotizacion\r\n" + 
				"inner join areas t2 on t0.idArea = t2.id\r\n" + 
				"inner join departamentos t3 on t2.idDepartamento = t3.id\r\n" + 
				"where YEAR(t0.fechaNecesaria) = 2020 and t3.id = "+idDepartamento+" and t0.idStatus not in (2,3,5,6)\r\n" + 
				"group by MONTH(t0.fechaNecesaria)		\r\n" + 
				") as tabla\r\n" + 
				"pivot\r\n" + 
				"(\r\n" + 
				"	sum(monto)\r\n" + 
				"		for mes in ([1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[12])\r\n" + 
				") as pvt\r\n" + 
				"\r\n" + 
				"union\r\n" + 
				"\r\n" + 
				"select * from (\r\n" + 
				"SELECT MONTH(fechaCreacion) mes, SUM(subtotal)monto\r\n" + 
				"FROM requisiciones t0\r\n" + 
				"INNER JOIN areas t1 on t0.idArea = t1.id\r\n" + 
				"inner join departamentos t2 on t1.idDepartamento = t2.id\r\n" + 
				"WHERE t2.id = "+idDepartamento+" and t0.idStatus not in (2,3,5,6)\r\n" + 
				"GROUP BY MONTH([fechaCreacion])	\r\n" + 
				") as tabla\r\n" + 
				"pivot\r\n" + 
				"(\r\n" + 
				"	sum(monto)\r\n" + 
				"		for mes in ([1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[12])\r\n" + 
				") as pvt\r\n" + 
				") tabla");
		try {
			if(db.resultado.next()) {
				pres.setEnero(db.resultado.getFloat(1));
				pres.setFebrero(db.resultado.getFloat(2));
				pres.setMarzo(db.resultado.getFloat(3));
				pres.setAbril(db.resultado.getFloat(4));
				pres.setMayo(db.resultado.getFloat(5));
				pres.setJunio(db.resultado.getFloat(6));
				pres.setJulio(db.resultado.getFloat(7));
				pres.setAgosto(db.resultado.getFloat(8));
				pres.setSeptiembre(db.resultado.getFloat(9));
				pres.setOctubre(db.resultado.getFloat(10));
				pres.setNoviembre(db.resultado.getFloat(11));
				pres.setDiciembre(db.resultado.getFloat(12));
			}
			return pres;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
