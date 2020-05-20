package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modelo.Encargado;
import modelo.Mail;
import modelo.Modulo;
import modelo.TipoUsuario;
import modelo.Usuario;

public class DaoUsuarios {
	private final List<Usuario> listado = new ArrayList<>();
	private ConexionBD db = new ConexionBD();
	public Usuario comprobarLogin(String user, String pass) {
		System.out.println("select usuarios.*, areas.nombre 'area' from usuarios inner join areas on usuarios.idArea = areas.id where usuario = '"+user+"' and contrasena = '"+pass+"'");
		db.consultar("select usuarios.*, areas.nombre 'area' from usuarios inner join areas on usuarios.idArea = areas.id where usuario = '"+user+"' and contrasena = '"+pass+"'");
		try {
			if(db.resultado.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(db.resultado.getInt("id"));
				usuario.setNombres(db.resultado.getString("nombres"));
				usuario.setApellidop(db.resultado.getString("apellidop"));
				usuario.setApellidom(db.resultado.getString("apellidom"));
				usuario.setEmail(db.resultado.getString("email"));
				usuario.setUsuario(db.resultado.getString("usuario"));
				usuario.setContrasena(db.resultado.getString("contrasena"));
				usuario.setActivo(db.resultado.getBoolean("activo"));
				usuario.getTipousuario().setId(db.resultado.getInt("idTipoUsuario"));
				usuario.getArea().setId(db.resultado.getInt("idArea"));
				usuario.getArea().setNombre(db.resultado.getString("area"));
				return usuario;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	public List<Usuario> getListado(){
		return listado;
	}
	
	public List<Usuario> importarUsuarios(){
		try {
			List<Usuario> listaUsuarios = new ArrayList<>();
			db.consultar("SELECT t0.*, t1.tipo, t2.nombre FROM usuarios t0\r\n" + 
					     "left join tipousuarios t1 on t0.idTipoUsuario = t1.id\r\n" + 
					     "left join areas t2 on t0.idArea = t2.id");
			while(db.resultado.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(db.resultado.getInt(1));
				usuario.setNombres(db.resultado.getString(2));
				usuario.setApellidop(db.resultado.getString(3));
				usuario.setApellidom(db.resultado.getString(4));
				usuario.setEmail(db.resultado.getString(5));
				usuario.setUsuario(db.resultado.getString(6));
				usuario.setContrasena("****");
				if(db.resultado.getInt(8) == 0) {
					usuario.setActivo(false);
				}else{
					usuario.setActivo(true);
				}
				usuario.getTipousuario().setNombre(db.resultado.getString(11));
				usuario.getArea().setNombre(db.resultado.getString(12));
				listaUsuarios.add(usuario);
			}
			return listaUsuarios;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Usuario> importarUsuariosTipo(int idTipo){
		List<Usuario> usuarios = new ArrayList<>();
		db.consultar("select * from usuarios where idTipoUsuario = " + idTipo);
		try {
			while(db.resultado.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(db.resultado.getInt(1));
				usuario.setNombres(db.resultado.getString(2));
				usuario.setApellidop(db.resultado.getString(3));
				usuario.setApellidom(db.resultado.getString(4));
				usuario.setEmail(db.resultado.getString(5));
				usuario.setUsuario(db.resultado.getString(6));
				usuario.setContrasena("****");
				if(db.resultado.getInt(8) == 0) {
					usuario.setActivo(true);
				}else {
					usuario.setActivo(false);
				}
				usuario.getTipousuario().setId(db.resultado.getInt(9));
				usuario.getArea().setId(db.resultado.getInt(10));
				usuarios.add(usuario);
			}
			return usuarios;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Usuario buscarUsuarioU(String usuario) {
		db.consultar("select * from usuarios where usuario = '" + usuario + "'");
		Usuario usuarioU = new Usuario();
		try {
			if(db.resultado.next()) {
				usuarioU.setId(db.resultado.getInt(1));
				usuarioU.setNombres(db.resultado.getString(2));
				usuarioU.setApellidop(db.resultado.getString(3));
				usuarioU.setApellidom(db.resultado.getString(4));
				usuarioU.setEmail(db.resultado.getString(5));
				usuarioU.setUsuario(db.resultado.getString(6));
				usuarioU.setContrasena("****");
				if(db.resultado.getInt(8) == 0) {
					usuarioU.setActivo(true);
				}else {
					usuarioU.setActivo(false);
				}
				usuarioU.getTipousuario().setId(db.resultado.getInt(9));
				usuarioU.getArea().setId(db.resultado.getInt(10));
				return usuarioU;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Usuario buscarUsuarioId(int idusuario) {
		db.consultar("select * from usuarios where id = '" + idusuario + "'");
		Usuario usuario = new Usuario();
		try {
			if(db.resultado.next()) {
				System.out.println("Gerente: " + db.resultado.getString(2));
				usuario.setId(db.resultado.getInt(1));
				usuario.setNombres(db.resultado.getString(2));
				usuario.setApellidop(db.resultado.getString(3));
				usuario.setApellidom(db.resultado.getString(4));
				usuario.setEmail(db.resultado.getString(5));
				usuario.setUsuario(db.resultado.getString(6));
				usuario.setContrasena("****");
				if(db.resultado.getInt(8) == 0) {
					usuario.setActivo(true);
				}else {
					usuario.setActivo(false);
				}
				usuario.getTipousuario().setId(db.resultado.getInt(9));
				usuario.getArea().setId(db.resultado.getInt(10));
				return usuario;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Usuario buscarUsuario(int idusuario) {
		db.consultar("select * from usuarios where id = " + idusuario);
		Usuario usuario = new Usuario();
		DaoArea daoarea = new DaoArea();
		try {
			if(db.resultado.next()) {
				usuario.setId(db.resultado.getInt(1));
				usuario.setNombres(db.resultado.getString(2));
				usuario.setApellidop(db.resultado.getString(3));
				usuario.setApellidom(db.resultado.getString(4));
				usuario.setEmail(db.resultado.getString(5));
				usuario.setUsuario(db.resultado.getString(6));
				usuario.setContrasena("****");
				if(db.resultado.getInt(8) == 0) {
					usuario.setActivo(true);
				}else {
					usuario.setActivo(false);
				}
				usuario.getTipousuario().setId(db.resultado.getInt(9));
				usuario.setArea(daoarea.buscarArea(db.resultado.getInt(10)));
				return usuario;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Encargado buscarEncargado(int usuario) {
		db.consultar("select * from usuarios where id = " + usuario);
		Encargado usuarioU = new Encargado();
		try {
			if(db.resultado.next()) {
				usuarioU.setId(db.resultado.getInt(1));
				usuarioU.setNombres(db.resultado.getString(2));
				usuarioU.setApellidop(db.resultado.getString(3));
				usuarioU.setApellidom(db.resultado.getString(4));
				usuarioU.setEmail(db.resultado.getString(5));
				usuarioU.setUsuario(db.resultado.getString(6));
				usuarioU.setContrasena("****");
				if(db.resultado.getInt(8) == 0) {
					usuarioU.setActivo(true);
				}else {
					usuarioU.setActivo(false);
				}
				return usuarioU;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Encargado buscarEncargadoArea(int idArea) {
		db.consultar("select t0.* from usuarios t0\r\n" + 
					 "inner join areas t1 on t0.id = t1.idUsuario\r\n" + 
				     "where t1.id = " + idArea);
		Encargado usuarioU = new Encargado();
		try {
			if(db.resultado.next()) {
				usuarioU.setId(db.resultado.getInt(1));
				usuarioU.setNombres(db.resultado.getString(2));
				usuarioU.setApellidop(db.resultado.getString(3));
				usuarioU.setApellidom(db.resultado.getString(4));
				usuarioU.setEmail(db.resultado.getString(5));
				usuarioU.setUsuario(db.resultado.getString(6));
				usuarioU.setContrasena("****");
				if(db.resultado.getInt(8) == 0) {
					usuarioU.setActivo(true);
				}else {
					usuarioU.setActivo(false);
				}
				return usuarioU;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	public int actualizarUsuario(Usuario usuario) {
		db.ejecutar("update usuarios set nombres = '"+usuario.getNombres()+"', "
				+ " apellidop = '"+usuario.getApellidop()+"', "
				+ " apellidom = '"+usuario.getApellidom()+"', "
				+ " email     = '"+usuario.getEmail()+"', "
				+ " idTipoUsuario = "+usuario.getTipousuario().getId()+", "
				+ " idArea = " + usuario.getArea().getId()
				+ " where usuario = '" + usuario.getUsuario() + "'");
		return 1;
	}
	
	public int crearUsuario(Usuario usuario) {
		int nuevo = 0;
		try {
		db.ejecutar("insert into usuarios (nombres,apellidop,apellidom,email,usuario,contrasena,activo,idTipoUsuario,idArea) values "
				   +"('"+usuario.getNombres()+"','"+usuario.getApellidop()+"','"+usuario.getApellidom()+"','"+usuario.getEmail()+"','"+usuario.getUsuario()+"','"+usuario.getContrasena()+"',"
			   	   + 1 + ","+usuario.getTipousuario().getId()+","+usuario.getArea().getId()+")");
		db.consultar("select max(id) from usuarios");
			if(db.resultado.next()) {
				nuevo = db.resultado.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error ivan: " + e);
			e.printStackTrace();
		}
		return nuevo;
	}
	
	public List<TipoUsuario> importarTiposUsuario() {
		List<TipoUsuario> listaTipo = new ArrayList<>();
		try {
			db.consultar("select * from tipousuarios");
			while(db.resultado.next()) {
				TipoUsuario tipo = new TipoUsuario();
				tipo.setId(db.resultado.getInt(1));
				tipo.setNombre(db.resultado.getString(2));
				listaTipo.add(tipo);
			}
			return listaTipo;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int cambioContrasena(Usuario usuario)  {
		if(comprobarLogin(usuario.getUsuario(), usuario.getContrasena()) != null) {
			db.ejecutar("update usuarios set contrasena = '"+usuario.getContrasena()+"' where usuario = '"+usuario.getUsuario()+"'");
			return 1;
		}else {
			return 0;
		}
	}
	
	public String mailOlvidoContrasena(String user) {
		Mail mail = new Mail();
		mail.olvidoContrasena(user);
		return "Se te ha enviado un correo";
	}
	
	public String olvidoContrasena(String token, String contrasena)  {
		int idusuario = 0;
		db.consultar("select * from olvidocontrasena where token = '"+token+"' and usado = '0'");
		try {
			if(db.resultado.next()) {
				idusuario = db.resultado.getInt("idusuario");
				db.ejecutar("update usuarios set contrasena = '"+contrasena+"' where id = '"+idusuario+"'; " + " update olvidocontrasena set usado = '1' where token = '"+token+"'");
				return "ok";
			}else {
				return "Ya se habia cambiado la contraseña";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}
	
	public List<Modulo> permisos(int idUsuario){
		List<Modulo> modulos = new ArrayList<>();
		Modulo modulo = new Modulo();
		modulos = modulo.buscar(idUsuario);
		
		return modulos;
	}
	
	public void permisoUsuarioSubmodulo(int idUsuario, int idSubmodulo, int permiso) {
		db.ejecutar("update submodulosdetalle set permiso = " + permiso + 
					" where idsubmodulo = " + idSubmodulo + " and idusuario = " + idUsuario);
	}
	
	public void permisoUsuarioSubmodulo(String usuario, int idSubmodulo, int permiso) {
		db.ejecutar("update t0 set t0.permiso = "+permiso+" from submodulosdetalle t0\r\n" + 
					"inner join usuarios t1 on t1.id = t0.idusuario\r\n" + 
					"where t0.idsubmodulo = "+idSubmodulo+" and t1.usuario = '"+usuario+"'");
	}
	
	public boolean permisoUsuarioSubmodulo(int idUsuario, String href) {
		try {
		db.consultar("select t0.permiso from submodulosdetalle t0 \r\n" + 
					"inner join submodulos t1 on t0.idsubmodulo = t1.id \r\n" + 
					"inner join usuarios t2 on t0.idusuario = t2.id \r\n" + 
					"where t1.href = '"+href+"' and t2.id = " + idUsuario);
		if(db.resultado.next()) {
			return db.resultado.getBoolean("permiso");
		}else {
			return false;
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
