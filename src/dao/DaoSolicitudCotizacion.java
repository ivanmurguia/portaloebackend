package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modelo.DetalleSolicitudCotizacion;
import modelo.Estatus;
import modelo.Mail;
import modelo.SolicitudCotizacion;
import modelo.Usuario;

public class DaoSolicitudCotizacion {
	
	public List<SolicitudCotizacion> buscarSolicitudesdeCotizacion(int idUsuario){
		ConexionBD db = new ConexionBD();
		List<SolicitudCotizacion> lista = new ArrayList<>();
		DaoUsuarios daousuario = new DaoUsuarios();
		
		Usuario user = daousuario.buscarUsuario(idUsuario);

		switch(user.getTipousuario().getId()) {
			case 5:
				db.consultar("select * from solicitudcotizaciones where idStatus in (4,9,12,14) order by fechaCreacion desc");
			break;
			case 2:
				db.consultar("select * from solicitudcotizaciones where idStatus in (1,10)  and idArea = " + user.getArea().getId());
			break;
			case 1:
				db.consultar("select * from solicitudcotizaciones where idStatus in (1,10)  and idDepartamento = " + user.getArea().getDepartamento().getId());
			break;
			default:
				db.consultar("select * from solicitudcotizaciones where idUsuario = " + idUsuario);
			break;				
		}

		try {
			while(db.resultado.next()) {
				DaoSociedad daosociedad = new DaoSociedad();
				DaoArea daoarea = new DaoArea();
				DaoEstatus daoestatus = new DaoEstatus();
				DaoMoneda daomoneda = new DaoMoneda();
				SolicitudCotizacion solicitud = new SolicitudCotizacion();
				solicitud.setId(db.resultado.getInt("id"));
				solicitud.setFechaCreacion(db.resultado.getString("fechaCreacion"));
				solicitud.setFechaNecesaria(db.resultado.getString("fechaNecesaria"));
				solicitud.setComentarios(db.resultado.getString("comentarios"));
				if(db.resultado.getInt("tecnologia") == 0) {
					solicitud.setTecnologia(false);
				}else {
					solicitud.setTecnologia(true);
				}
				solicitud.setSociedad(daosociedad.buscarSociedad(db.resultado.getInt("idSociedad")));
				solicitud.setArea(daoarea.buscarArea(db.resultado.getInt("idArea")));
				solicitud.setUsuario(daousuario.buscarUsuario(db.resultado.getInt("idUsuario")));
				solicitud.setEstatus(daoestatus.buscarEstatus(db.resultado.getInt("idStatus")));
				solicitud.setMoneda(daomoneda.buscarMoneda(db.resultado.getInt("idMoneda")));
				lista.add(solicitud);
			}
			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public SolicitudCotizacion buscarSolicitudCotizacion(int id) {
		ConexionBD db = new ConexionBD();
		SolicitudCotizacion solicitudCotizacion = new SolicitudCotizacion();
		DaoSociedad daosociedad = new DaoSociedad();
		DaoArea daoarea = new DaoArea();
		DaoUsuarios daousuario = new DaoUsuarios();
		DaoEstatus daoestatus = new DaoEstatus();
		DaoMoneda daomoneda = new DaoMoneda();
		DaoDepartamento daodepartamento = new DaoDepartamento();
		db.consultar("select * from solicitudcotizaciones where id = " + id);
		System.out.println("Se va a buscar la solicitud " + id);
		try {
			if(db.resultado.next()) {
				solicitudCotizacion.setId(id);
				solicitudCotizacion.setFechaCreacion(db.resultado.getString("fechaCreacion"));
				solicitudCotizacion.setFechaNecesaria(db.resultado.getString("fechaNecesaria"));
				solicitudCotizacion.setComentarios(db.resultado.getString("comentarios"));
				solicitudCotizacion.setTecnologia(db.resultado.getBoolean("tecnologia"));
				solicitudCotizacion.setSociedad(daosociedad.buscarSociedad(db.resultado.getInt("idSociedad")));
				System.out.println("Se va a buscar el area con el id = " + db.resultado.getInt("idArea"));
				solicitudCotizacion.setArea(daoarea.buscarArea(db.resultado.getInt("idArea")));
				System.out.println("Se va a buscar el gerente con el id de area " + solicitudCotizacion.getArea().getGerente().getId());
				solicitudCotizacion.getArea().setGerente(daousuario.buscarEncargadoArea(solicitudCotizacion.getArea().getId()));
				solicitudCotizacion.getArea().setDepartamento(daodepartamento.buscarDepartamento(solicitudCotizacion.getArea().getDepartamento().getId()));
				solicitudCotizacion.setUsuario(daousuario.buscarUsuario(db.resultado.getInt("idUsuario")));
				solicitudCotizacion.setEstatus(daoestatus.buscarEstatus(db.resultado.getInt("idStatus")));
				solicitudCotizacion.setMoneda(daomoneda.buscarMoneda(db.resultado.getInt("idMoneda")));
				solicitudCotizacion.setDetalle(buscarSolicitudCotizacionDetalle(id));
				return solicitudCotizacion;
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Estatus solicitudCotizacionEstatus(int idSolicidutCotizacion) {
		DaoEstatus daoestatus = new DaoEstatus();
		ConexionBD db = new ConexionBD();
		db.consultar("select idStatus from solicitudcotizaciones where id = " + idSolicidutCotizacion);
		try {
			if(db.resultado.next()) {
				return daoestatus.buscarEstatus(db.resultado.getInt("idStatus"));
			}else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<DetalleSolicitudCotizacion> buscarSolicitudCotizacionDetalle(int idSolicitudCotizacion){
		ConexionBD db = new ConexionBD();
		List<DetalleSolicitudCotizacion> listasolicitudCotizacion = new ArrayList<>();
		db.consultar("select * from solicitudcotizacionesdetalle where idSolicitudCotizacion = " + idSolicitudCotizacion);
		try {
			while(db.resultado.next()) {
				DetalleSolicitudCotizacion detalle = new DetalleSolicitudCotizacion();
				DaoProducto daoproducto = new DaoProducto();
				detalle.setId(db.resultado.getInt("id"));
				detalle.setDescripcion(db.resultado.getString("descripcion"));
				detalle.setCantidad(db.resultado.getInt("cantidad"));
				detalle.setPrecio(db.resultado.getFloat("precio"));
				detalle.setProducto(daoproducto.buscarProducto(db.resultado.getInt("idProducto")));
				listasolicitudCotizacion.add(detalle);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return listasolicitudCotizacion;
	}
	
	public int crearSolicitudCotizacion(SolicitudCotizacion solicitudCotizacion) {
		ConexionBD db = new ConexionBD();
		int nuevaSolicitud = 0;
		db.ejecutar("insert into solicitudcotizaciones(fechaCreacion, fechaNecesaria, comentarios, idSociedad, idDepartamento, idArea, idUsuario, idStatus, idMoneda) values ("
				+ "getDate(),"
				+ "'" + solicitudCotizacion.getFechaNecesaria() + "',"
				+ "'" + solicitudCotizacion.getComentarios() + "',"
				+ solicitudCotizacion.getSociedad().getId() + ","
				+ solicitudCotizacion.getDepartamento().getId() + ","
				+ solicitudCotizacion.getArea().getId() + ","
				+ solicitudCotizacion.getUsuario().getId() + ","
				+ solicitudCotizacion.getEstatus().getId() + ","
				+ solicitudCotizacion.getMoneda().getId()
				+ ")");
		db.consultar("select max(id) from solicitudcotizaciones");
		try {
			if(db.resultado.next()) {
				nuevaSolicitud = db.resultado.getInt(1);
			}else {
				return 0;
			}
			for(int i = 0; i < solicitudCotizacion.getDetalle().size(); i++) {
				db.ejecutar("insert into solicitudcotizacionesdetalle (descripcion, cantidad, precio, idSolicitudCotizacion, idProducto) values ("
						+ "'" + solicitudCotizacion.getDetalle().get(i).getDescripcion() + "',"
						+ solicitudCotizacion.getDetalle().get(i).getCantidad() + ","
						+ solicitudCotizacion.getDetalle().get(i).getPrecio() + ","
						+ nuevaSolicitud + ","
						+ solicitudCotizacion.getDetalle().get(i).getProducto().getId()
						+ ")");
			}

			
			SolicitudCotizacion listaMail = new SolicitudCotizacion();
			
			listaMail = buscarSolicitudCotizacion(nuevaSolicitud);
			
			switch(solicitudCotizacion.getEstatus().getId()) {
				case 1: 
					Mail mail = new Mail();
					mail.solicitudCreada(listaMail);
					Mail mail2 = new Mail();
					mail2.autorizarSolicitud(listaMail);
				break;
				case 10:
					Mail mail3 = new Mail();
					mail3.solicitudCreada(listaMail);
					Mail mail4 = new Mail();
					mail4.autorizarSolicitudSinPresupuestoGerente(listaMail);
				break;
				case 7:
					Mail mail5 = new Mail();
					mail5.solicitudCreada(listaMail);
					Mail mail6 = new Mail();
					mail6.autorizarSolicitudMayorGerente(listaMail);
				break;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		
		return nuevaSolicitud;
	}
	
	public int cambiarProducto(int idDetalle, int idProducto) {
		ConexionBD db = new ConexionBD();
		db.ejecutar("update solicitudcotizacionesdetalle set idProducto = "+idProducto+" where id = " + idDetalle);
		return 1;
	}
	
	public String rechazar(int idsc, String motivo) {
		ConexionBD db = new ConexionBD();
		SolicitudCotizacion solicitudCotizacion = new SolicitudCotizacion();
		solicitudCotizacion = buscarSolicitudCotizacion(idsc);
		
		if(solicitudCotizacion.getEstatus().getId() == 2) {
			return "La solicitud no se puede rechazar por que ya esta cancelada.";
		}
		
		if(solicitudCotizacion.getEstatus().getId() == 3) {
			return "La solicitud no se puede rechazar por que ya esta vencida.";
		}
		
		if(solicitudCotizacion.getEstatus().getId() == 5) {
			return "Ya esta rechazada la Solicitud de Cotizacion.";
		}
		
		if(solicitudCotizacion.getEstatus().getId() == 6) {
			return "La Solicitud de Cotizacion no se puede rechazar por que ya esta cerrada.";
		}
		
		db.ejecutar("update solicitudcotizaciones set idstatus = 5, comentarios = comentarios + ' \nRechazada por: " + motivo + "'  where id = " + solicitudCotizacion);
		solicitudCotizacion.setComentarios(solicitudCotizacion.getComentarios() + "\nRechazada por: " + motivo);
		
		Mail mail = new Mail();
		mail.solicitudRechazada(solicitudCotizacion, motivo);
		return "Se rechazo con exito!";
	}
	
	public String actualizarEstatusSolicitud(int idsolicitud, int estatus) {
		SolicitudCotizacion solicitudCotizacion = new SolicitudCotizacion();
		solicitudCotizacion = buscarSolicitudCotizacion(idsolicitud);
		ConexionBD db = new ConexionBD();
		String mensaje = "";

		if(estatus == 6) {
			Mail mail = new Mail();

			mail.solicitudCerrada(solicitudCotizacion);
			db.ejecutar("update solicitudcotizaciones set idstatus = "+estatus+" where id = " + solicitudCotizacion.getId());
			
			return mensaje;
		}

		if(estatus == 4 && solicitudCotizacion.getEstatus().getId() == 14) {
			Mail mail = new Mail();

			mail.solicitudAutorizada(solicitudCotizacion, solicitudCotizacion.getArea().getGerente().getNombres() + " " + solicitudCotizacion.getArea().getGerente().getApellidop());
			db.ejecutar("update solicitudcotizaciones set idstatus = "+estatus+" where id = " + solicitudCotizacion.getId());
			
			return mensaje;
		}

		if(estatus == 4) {
			if(solicitudCotizacion.getEstatus().getId() == 1) {
				
				boolean requiereTI = false;
				
				if(solicitudCotizacion.getArea().getId() != 11){
					for(DetalleSolicitudCotizacion detalle : solicitudCotizacion.getDetalle()) {
						if(detalle.getProducto().getProducto().endsWith("MANCO") || detalle.getProducto().getProducto().endsWith("COMPUTO")) {
							requiereTI = true;
						}
					}
				}

				if(requiereTI) {
					Mail mail = new Mail();
					mail.autorizarSolicitudTI(solicitudCotizacion);
					
					estatus = 14;
					
					db.ejecutar("update solicitudcotizaciones set idstatus = "+estatus+" where id = " + solicitudCotizacion.getId());
				} else {
					Mail mail = new Mail();

					mail.solicitudAutorizada(solicitudCotizacion, solicitudCotizacion.getArea().getGerente().getNombres() + " " + solicitudCotizacion.getArea().getGerente().getApellidop());
					db.ejecutar("update solicitudcotizaciones set idstatus = "+estatus+" where id = " + solicitudCotizacion.getId());
				}
				
				mensaje = "Se autorizo con exito!";
			} else {
				mensaje = "No se puede autorizar por que ya no esta activa la solicitud!";
			}
		}else if(estatus == 5) {
			if(solicitudCotizacion.getEstatus().getId() == 1) {
				db.ejecutar("update solicitudcotizaciones set idstatus = "+estatus+" where id = " + solicitudCotizacion.getId());
				
				Mail mail = new Mail();
				mail.solicitudRechazada(solicitudCotizacion,"");
				mensaje = "Se rechazo con exito!";
			}else {
				mensaje = "No se puede rechazar por que ya no esta activa la solicitud!";
			}
		}else if(estatus == 8) {
			if(solicitudCotizacion.getEstatus().getId() == 7) {
				db.ejecutar("update solicitudcotizaciones set idstatus = "+estatus+" where id = " + solicitudCotizacion.getId());
				
				Mail mail = new Mail();
				mail.autorizarSolicitudMayorDirector(solicitudCotizacion);
				
				mensaje = "Se autorizo con exito!";
			} else {
				mensaje = "No se puede autorizar por que ya no esta activa la solicitud!";
			}
		}else if(estatus == 9) {
			if(solicitudCotizacion.getEstatus().getId() == 8) {
				
				boolean requiereTI = false;
				
				for(DetalleSolicitudCotizacion detalle : solicitudCotizacion.getDetalle()) {
					if(detalle.getProducto().getProducto().endsWith("MANCO") || detalle.getProducto().getProducto().endsWith("COMPUTO")) {
						requiereTI = true;
					}
				}
				
				if(requiereTI) {
					Mail mail = new Mail();
					mail.autorizarSolicitudTI(solicitudCotizacion);
					
					estatus = 14;
					
					db.ejecutar("update solicitudcotizaciones set idstatus = "+estatus+" where id = " + solicitudCotizacion.getId());
				} else {
					db.ejecutar("update solicitudcotizaciones set idstatus = "+estatus+" where id = " + solicitudCotizacion.getId());
					
					Mail mail = new Mail();
					mail.solicitudAutorizada(solicitudCotizacion, solicitudCotizacion.getArea().getDepartamento().getDirector().getNombres() + " " + solicitudCotizacion.getArea().getDepartamento().getDirector().getApellidop());
				}
				
				mensaje = "Se autorizo con exito!";
			} else {
				mensaje = "No se puede autorizar por que ya no esta activa la solicitud!";
			}
		}else if(estatus == 11) {
			if(solicitudCotizacion.getEstatus().getId() == 10) {
				db.ejecutar("update solicitudcotizaciones set idstatus = "+estatus+" where id = " + solicitudCotizacion.getId());
				
				Mail mail = new Mail();
				mail.autorizarSolicitudSinPresupuestoDirector(solicitudCotizacion);
				
				mensaje = "Se autorizo con exito!";
			} else {
				mensaje = "No se puede autorizar por que ya no esta activa la solicitud!";
			}
		} else if(estatus == 12) {
			if(solicitudCotizacion.getEstatus().getId() == 11) {
				
				boolean requiereTI = false;
				
				for(DetalleSolicitudCotizacion detalle : solicitudCotizacion.getDetalle()) {
					if(detalle.getProducto().getProducto().endsWith("MANCO") || detalle.getProducto().getProducto().endsWith("COMPUTO")) {
						requiereTI = true;
					}
				}
				
				if(requiereTI) {
					Mail mail = new Mail();
					mail.autorizarSolicitudTI(solicitudCotizacion);
					
					estatus = 14;
					
					db.ejecutar("update solicitudcotizaciones set idstatus = "+estatus+" where id = " + solicitudCotizacion.getId());
				}else {
					db.ejecutar("update solicitudcotizaciones set idstatus = "+estatus+" where id = " + solicitudCotizacion.getId());
					
					Mail mail = new Mail();
					mail.solicitudAutorizada(solicitudCotizacion, solicitudCotizacion.getArea().getDepartamento().getDirector().getNombres() + " " + solicitudCotizacion.getArea().getDepartamento().getDirector().getApellidop());
				}

				mensaje = "Se autorizo con exito!";
			} else {
				mensaje = "No se puede autorizar por que ya no esta activa la solicitud!";
			}
		}
		
		return mensaje;
	}
	
	public void reenviarCorreo(int id) {
		SolicitudCotizacion solicitudCotizacion = buscarSolicitudCotizacion(id);
		switch(solicitudCotizacion.getEstatus().getId()) {
		case 1: 
			Mail mail = new Mail();
			mail.solicitudCreada(solicitudCotizacion);
			Mail mail2 = new Mail();
			mail2.autorizarSolicitud(solicitudCotizacion);
		break;
		case 10:
			Mail mail3 = new Mail();
			mail3.solicitudCreada(solicitudCotizacion);
			Mail mail4 = new Mail();
			mail4.autorizarSolicitudSinPresupuestoGerente(solicitudCotizacion);
		break;
		case 7:
			Mail mail5 = new Mail();
			mail5.solicitudCreada(solicitudCotizacion);
			Mail mail6 = new Mail();
			mail6.autorizarSolicitudMayorGerente(solicitudCotizacion);
		break;
	}
	}
}
