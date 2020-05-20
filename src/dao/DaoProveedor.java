package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modelo.Proveedor;

public class DaoProveedor {
	
	public List<Proveedor> getListado(int idsociedad){
		List<Proveedor> listado = new ArrayList<>();
		ConexionBD db = new ConexionBD();
		try {
			db.consultar("select * from [proveedores] where idSociedad = "+idsociedad+" and activo = '1' order by nombre");
			while(db.resultado.next()) {
				Proveedor proveedor = new Proveedor();
				proveedor.setId(db.resultado.getInt("id"));
				proveedor.setCardCode(db.resultado.getString("cardCode"));
				proveedor.setNombre(db.resultado.getString("nombre"));
				proveedor.setNombreExtranjero(db.resultado.getString("nombreExtranjero"));
				proveedor.setTelefono(db.resultado.getString("telefono"));
				proveedor.setActivo(db.resultado.getBoolean("activo"));
				proveedor.getSociedad().setId(db.resultado.getInt("idSociedad"));
				proveedor.getMoneda().setId(db.resultado.getInt("idMoneda"));
				listado.add(proveedor);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listado;
	}
	
	public Proveedor buscarProveedor(int idProveedor) {
		ConexionBD db = new ConexionBD();
		Proveedor proveedor = new Proveedor();
		db.consultar("select * from proveedores where id = " + idProveedor);
		try {
			if(db.resultado.next()) {
				proveedor.setId(db.resultado.getInt("id"));
				proveedor.setCardCode(db.resultado.getString("cardCode"));
				proveedor.setNombre(db.resultado.getString("nombre"));
				proveedor.setNombreExtranjero(db.resultado.getString("nombreExtranjero"));
				proveedor.setTelefono(db.resultado.getString("telefono"));
				proveedor.setActivo(db.resultado.getBoolean("activo"));
				proveedor.getSociedad().setId(db.resultado.getInt("idSociedad"));
				proveedor.getMoneda().setId(db.resultado.getInt("idMoneda"));
				return proveedor;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
