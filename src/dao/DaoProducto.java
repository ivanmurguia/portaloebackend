package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modelo.Producto;

public class DaoProducto {
	public DaoProducto() {
		
	}
	
	public List<Producto> getListadoS(int idSociedad, int esAdministrativo){
		List<Producto> listado = new ArrayList<>();
		ConexionBD db = new ConexionBD();
		try {
			db.consultar("select * from productos where activo = 1 and idSociedad = " + idSociedad + " and esAdministrativo = " + esAdministrativo + " order by descripcion");
			while(db.resultado.next()) {
				Producto producto = new Producto();
				producto.setId(db.resultado.getInt("id"));
				producto.setProducto(db.resultado.getString("producto"));
				producto.setDescripcion(db.resultado.getString("descripcion"));
				producto.setActivo(db.resultado.getBoolean("activo"));
				if(db.resultado.getInt("esAdministrativo") == 0) {
					producto.setEsAdministrativo(false);
				}else {
					producto.setEsAdministrativo(true);
				}
				producto.getSociedad().setId(db.resultado.getInt("idSociedad"));
				listado.add(producto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return listado;
	}
	public Producto buscarProducto(int id) {
		ConexionBD db = new ConexionBD();
		db.consultar("select * from productos where id = " + id);
		try {
			if(db.resultado.next()) {
				Producto producto = new Producto();
				producto.setId(db.resultado.getInt(1));
				producto.setProducto(db.resultado.getString(2));
				producto.setDescripcion(db.resultado.getString(3));
				return producto;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public String cambioSKU(String item) {
		ConexionBD db = new ConexionBD();
		String newSKU = "";
		System.out.println("SELECT \r\n" + 
				"CASE\r\n" + 
				"	WHEN SWW IS NOT NULL THEN SWW\r\n" + 
				"	ELSE ItemCode\r\n" + 
				"END SKU\r\n" + 
				"FROM [192.168.1.26].OE_MODA2017.dbo.oitm \r\n" + 
				"WHERE itemcode = '"+item+"' OR SWW = '" + item + "'");
		db.consultar("SELECT \r\n" + 
				"CASE\r\n" + 
				"	WHEN SWW IS NOT NULL THEN SWW\r\n" + 
				"	ELSE ItemCode\r\n" + 
				"END SKU\r\n" + 
				"FROM [192.168.1.26].OE_MODA2017.dbo.oitm \r\n" + 
				"WHERE itemcode = '"+item+"' OR SWW = '" + item + "'");
//		System.out.println("SELECT isnull(SWW, '404') SKU [192.168.1.26].FROM OE_MODA2017.dbo.oitm where itemcode = '"+item+"'");
//		db.consultar("SELECT isnull(SWW, '404') SKU FROM [192.168.1.26].OE_MODA2017.dbo.oitm where itemcode = '"+item+"'");
		try {
			while(db.resultado.next()) {
				newSKU = db.resultado.getString("SKU");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			newSKU = "error";
			return newSKU;
		}
		return newSKU;
	}
}