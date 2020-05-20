package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modelo.Modulo;
import modelo.Submodulo;

public class DaoModulos {
	public List<Modulo> consultarModulos(){
		try {
			ConexionBD db = new ConexionBD();
			List<Modulo> lista = new ArrayList<>();
			db.consultar("select t0.id idmodulo, t0.nombre nombremodulo, t1.id idsubmodulo, t1.nombre nombresubmodulo, t1.href, t1.icon from modulos t0 inner join submodulos t1 on t1.idmodulo = t0.id");
		
			while(db.resultado.next()) {
				if(lista.size() == 0) {
					Modulo modulo = new Modulo();
					modulo.setId(db.resultado.getInt("idmodulo"));
					modulo.setNombre(db.resultado.getString("nombremodulo"));
					
					Submodulo submodulo = new Submodulo();
					
					submodulo.setId(db.resultado.getInt("idsubmodulo"));
					submodulo.setNombre(db.resultado.getString("nombresubmodulo"));
					submodulo.setHref(db.resultado.getString("href"));
					submodulo.setIcon(db.resultado.getString("icon"));
					
					modulo.getSubmodulos().add(submodulo);
					
					lista.add(modulo);
					
				}else if(db.resultado.getInt("idmodulo") == lista.get(lista.size() - 1).getId()) {
					
					Submodulo submodulo = new Submodulo();
					
					submodulo.setId(db.resultado.getInt("idsubmodulo"));
					submodulo.setNombre(db.resultado.getString("nombresubmodulo"));
					submodulo.setHref(db.resultado.getString("href"));
					submodulo.setIcon(db.resultado.getString("icon"));
					
					lista.get(lista.size() - 1).getSubmodulos().add(submodulo);
					
				} else {
					Modulo modulo = new Modulo();
					modulo.setId(db.resultado.getInt("idmodulo"));
					modulo.setNombre(db.resultado.getString("nombremodulo"));
					
					Submodulo submodulo = new Submodulo();
					
					submodulo.setId(db.resultado.getInt("idsubmodulo"));
					submodulo.setNombre(db.resultado.getString("nombresubmodulo"));
					submodulo.setHref(db.resultado.getString("href"));
					submodulo.setIcon(db.resultado.getString("icon"));
					
					modulo.getSubmodulos().add(submodulo);
					
					lista.add(modulo);
				}
			}
			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public List<Modulo> consultarModulos(String usuario){
		try {
			ConexionBD db = new ConexionBD();
			List<Modulo> lista = new ArrayList<>();
			System.out.println();
			db.consultar("select t0.id idmodulo, t0.nombre nombremodulo, t1.id idsubmodulo, t1.nombre nombresubmodulo, t1.href, t1.icon, t2.permiso\r\n" + 
					"from modulos t0 \r\n" + 
					"inner join submodulos t1 on t1.idmodulo = t0.id \r\n" + 
					"inner join submodulosdetalle t2 on t2.idsubmodulo = t1.id\r\n" + 
					"inner join usuarios t3 on t3.id = t2.idusuario\r\n" + 
					"where t3.usuario = '" + usuario + "' order by t0.id");
		
			while(db.resultado.next()) {
				if(lista.size() == 0) {
					Modulo modulo = new Modulo();
					modulo.setId(db.resultado.getInt("idmodulo"));
					modulo.setNombre(db.resultado.getString("nombremodulo"));
					
					Submodulo submodulo = new Submodulo();
					
					submodulo.setId(db.resultado.getInt("idsubmodulo"));
					submodulo.setNombre(db.resultado.getString("nombresubmodulo"));
					submodulo.setHref(db.resultado.getString("href"));
					submodulo.setIcon(db.resultado.getString("icon"));
					submodulo.setPermiso(db.resultado.getBoolean("permiso"));
					
					modulo.getSubmodulos().add(submodulo);
					
					lista.add(modulo);
					
				}else if(db.resultado.getInt("idmodulo") == lista.get(lista.size() - 1).getId()) {
					
					Submodulo submodulo = new Submodulo();
					
					submodulo.setId(db.resultado.getInt("idsubmodulo"));
					submodulo.setNombre(db.resultado.getString("nombresubmodulo"));
					submodulo.setHref(db.resultado.getString("href"));
					submodulo.setIcon(db.resultado.getString("icon"));
					submodulo.setPermiso(db.resultado.getBoolean("permiso"));
					
					lista.get(lista.size() - 1).getSubmodulos().add(submodulo);
					
				} else {
					Modulo modulo = new Modulo();
					modulo.setId(db.resultado.getInt("idmodulo"));
					modulo.setNombre(db.resultado.getString("nombremodulo"));
					
					Submodulo submodulo = new Submodulo();
					
					submodulo.setId(db.resultado.getInt("idsubmodulo"));
					submodulo.setNombre(db.resultado.getString("nombresubmodulo"));
					submodulo.setHref(db.resultado.getString("href"));
					submodulo.setIcon(db.resultado.getString("icon"));
					submodulo.setPermiso(db.resultado.getBoolean("permiso"));
					
					modulo.getSubmodulos().add(submodulo);
					
					lista.add(modulo);
				}
			}
			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
