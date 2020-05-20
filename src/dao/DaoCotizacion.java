package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBD;
import modelo.Cotizacion;
import modelo.Departamento;
import modelo.Proveedor;
import modelo.Requisicion;
import modelo.RequisicionDetalle;
import modelo.SolicitudCotizacion;
import modelo.Usuario;

public class DaoCotizacion {
//   	public static final String SERVER_UPLOAD_LOCATION_FOLDER = "C:\\Users\\ivanm\\Desktop\\Upload_Files\\";
	public int crearCotizacion(Cotizacion cotizacion) {
		int idUltimo, idNuevo;
		ConexionBD db = new ConexionBD();
		db.consultar("select max(id) from cotizaciones");
		try {
			if(db.resultado.next()) {
				idUltimo = db.resultado.getInt(1);
			}else {
				return 0;
			}
		String sentencia = "insert into cotizaciones (proveedor,comentario,precioUnitario,cantidad,subtotal,iva,total,fechaentrega,condicionespago,minimocredito,minimocompra,tiempoentrega,idsolicitudcotizacionesdetalle,idUsuario,idProveedor) values ("
				  + "'"+cotizacion.getNombreProveedor()+"',"
				  + "'"+cotizacion.getComentario()+"',"
			      + cotizacion.getPrecioUnitario()+","
			      + cotizacion.getCantidad()+","
	    		  + cotizacion.getSubtotal()+","
				  + cotizacion.getIva()+","
			      + cotizacion.getTotal() + ",'"
	    		  + cotizacion.getFechaEntrega()+ "','"
			      + cotizacion.getCondicionespago()+ "','"
			      + cotizacion.getMinimocredito()+ "','"
			      + cotizacion.getMinimocompra()+ "','"
			      + cotizacion.getTiempoentrega()+ "',"
			      + cotizacion.getDetalleSolicitudCotizacion().getId() + ","
				  + cotizacion.getUsuario().getId() + ",";
		if(cotizacion.getProveedor().getId() == 0) {
			sentencia = sentencia + "null)";
		}else {
			sentencia = sentencia + cotizacion.getProveedor().getId() + ")";
		}
		db.ejecutar(sentencia);

		db.consultar("select max(id) from cotizaciones");
		if(db.resultado.next()) {
			idNuevo = db.resultado.getInt(1);
		}else {
			return 0;
		}
		if(idNuevo == idUltimo + 1) {
			return idNuevo;
		}else {
			return 0;
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public List<Cotizacion>  buscarCotizacionesSolicitud(int idSolicitud) {
		List<Cotizacion> cotizaciones = new ArrayList<>();
		ConexionBD db = new ConexionBD();
		db.consultar("SELECT t0.id, t0.proveedor, t0.comentario, t0.precioUnitario, t0.cantidad, t0.subtotal, t0.iva, t0.total, t1.id, t0.documento, t1.descripcion, t0.fechaentrega FROM cotizaciones t0 inner join solicitudcotizacionesdetalle t1 on t0.idsolicitudcotizacionesdetalle = t1.id inner join solicitudcotizaciones t2 on t1.idSolicitudCotizacion = t2.id where t2.id = " + idSolicitud);
		try {
			while(db.resultado.next()) {
				Cotizacion cotizacion = new Cotizacion();
				cotizacion.setId(db.resultado.getInt(1));
				cotizacion.setNombreProveedor(db.resultado.getString(2));
				cotizacion.setComentario(db.resultado.getString(3));
				cotizacion.setPrecioUnitario(db.resultado.getFloat(4));
				cotizacion.setCantidad(db.resultado.getFloat(5));
				cotizacion.setSubtotal(db.resultado.getFloat(6));
				cotizacion.setIva(db.resultado.getFloat(7));
				cotizacion.setTotal(db.resultado.getFloat(8));
				cotizacion.getDetalleSolicitudCotizacion().setId(db.resultado.getInt(9));
				cotizacion.setDocumento(db.resultado.getBoolean(10));
				cotizacion.getDetalleSolicitudCotizacion().setDescripcion(db.resultado.getString(11));
				cotizacion.setFechaEntrega(db.resultado.getString(12));
				cotizaciones.add(cotizacion);
			}
		} catch (SQLException e) {
			return null;
		}
		
		return cotizaciones;
	}
	
	public List<Cotizacion>  buscarCotizacionesU(int idUsuario) {
		List<Cotizacion> cotizaciones = new ArrayList<>();
		DaoUsuarios daousuario = new DaoUsuarios();
		DaoProveedor daoproveedor = new DaoProveedor();
		Usuario usuario = daousuario.buscarUsuario(idUsuario);
		switch(usuario.getTipousuario().getId()) {
			case 1:
				DaoDepartamento daoDepartamento = new DaoDepartamento();
				List<Departamento> departamentos = daoDepartamento.buscarDepartamentosPorEncargado(usuario.getId());
				for(int i = 0; i < departamentos.size(); i++) {
					ConexionBD db = new ConexionBD();
					db.consultar("SELECT * FROM cotizaciones t0 inner join solicitudcotizacionesdetalle t1 on t0.idsolicitudcotizacionesdetalle = t1.id "
							+ "inner join solicitudcotizaciones t2 on t1.idSolicitudCotizacion = t2.id where idDepartamento = " + departamentos.get(0).getId());
					try {
						while(db.resultado.next()) {
							Cotizacion cotizacion = new Cotizacion();
							cotizacion.setId(db.resultado.getInt(1));
							cotizacion.setNombreProveedor(db.resultado.getString(2));
							cotizacion.setComentario(db.resultado.getString(3));
							cotizacion.setPrecioUnitario(db.resultado.getFloat(4));
							cotizacion.setCantidad(db.resultado.getFloat(5));
							cotizacion.setTotal(db.resultado.getFloat(6));
							cotizacion.getDetalleSolicitudCotizacion().setId(db.resultado.getInt(7));
							cotizacion.setProveedor(daoproveedor.buscarProveedor(db.resultado.getInt(8)));
							cotizacion.setUsuario(daousuario.buscarUsuario(db.resultado.getInt(9)));
							cotizaciones.add(cotizacion);
						}
					} catch (SQLException e) {
						return null;
					}
				}
			break;
			case 2:
				
			break;
		}

		return cotizaciones;
	}
	
	public void actualizarDocumento(int idCotizacion) {
		ConexionBD db = new ConexionBD();
		db.ejecutar("UPDATE cotizaciones SET documento = 1 where id = " + idCotizacion);
	}
	
	public void eliminarCotizacion(int idCotizacion) {
		ConexionBD db = new ConexionBD();
		db.ejecutar("delete from cotizaciones where id = " + idCotizacion);
	}
	
	public String crearRequisiciondeCotizacion(List<Cotizacion> listaCotizaciones) {
		try {
			String idnueva = "";
			ConexionBD db = new ConexionBD();
			
			String in = "";
			
			for(int i = 0 ; i < listaCotizaciones.size(); i++) {
				if(i == listaCotizaciones.size() - 1) {
					in = in + listaCotizaciones.get(i).getId();
				}else {
					in = in + listaCotizaciones.get(i).getId() + ",";
				}
			}
			
			List<Cotizacion> cotizaciones = new ArrayList<>();
			SolicitudCotizacion solicitudCotizacion = new SolicitudCotizacion();

			db.consultar("SELECT t0.id, t0.proveedor, t0.comentario, t0.precioUnitario, t0.cantidad, t0.subtotal, t0.iva , t0.total,t0.idProveedor,t1.id,t1.descripcion,t1.cantidad,t1.precio,t1.idProducto,t2.id "
					   + "FROM cotizaciones t0 "
					   + "inner join solicitudcotizacionesdetalle t1 on t0.idsolicitudcotizacionesdetalle = t1.id "
					   + "inner join solicitudcotizaciones t2 on t1.idSolicitudCotizacion = t2.id where t0.id in ("+in+")");
			
			while(db.resultado.next()) {
				DaoSolicitudCotizacion daosolicitud = new DaoSolicitudCotizacion();
				DaoProveedor daoproveedor = new DaoProveedor();
				DaoProducto daoproducto = new DaoProducto();
				Cotizacion cotizacion = new Cotizacion();
				
				cotizacion.setId(db.resultado.getInt(1));
				cotizacion.setNombreProveedor(db.resultado.getString(2));
				cotizacion.setComentario(db.resultado.getString(3));
				cotizacion.setPrecioUnitario(db.resultado.getFloat(4));
				cotizacion.setCantidad(db.resultado.getFloat(5));
				cotizacion.setSubtotal(db.resultado.getFloat(6));
				cotizacion.setIva(db.resultado.getFloat(7));
				cotizacion.setTotal(db.resultado.getFloat(8));
				cotizacion.setProveedor(daoproveedor.buscarProveedor(db.resultado.getInt(9)));
				cotizacion.getDetalleSolicitudCotizacion().setId(db.resultado.getInt(10));
				cotizacion.getDetalleSolicitudCotizacion().setDescripcion(db.resultado.getString(11));
				cotizacion.getDetalleSolicitudCotizacion().setCantidad(db.resultado.getInt(12));
				cotizacion.getDetalleSolicitudCotizacion().setPrecio(db.resultado.getFloat(13));
				cotizacion.getDetalleSolicitudCotizacion().setProducto(daoproducto.buscarProducto(db.resultado.getInt(14)));
				solicitudCotizacion = daosolicitud.buscarSolicitudCotizacion(db.resultado.getInt(15));
				cotizaciones.add(cotizacion);
			}
			
			//Activo
			if(solicitudCotizacion.getEstatus().getId() != 6) {
				return ""+(-1);
			}
			
			//buscar duplicados
			for(int i = 0 ; i < cotizaciones.size(); i++) {
				for(int j = 0 ; j < cotizaciones.size(); j++) {
					if(i != j) {
						if(cotizaciones.get(i).getDetalleSolicitudCotizacion().getId() == cotizaciones.get(j).getDetalleSolicitudCotizacion().getId()) {
							return ""+(-2);
						}
					}
				}
			}

			//Productos asignados
			for(int i = 0 ; i < cotizaciones.size(); i++) {
				if(cotizaciones.get(i).getDetalleSolicitudCotizacion().getProducto() == null) {
					return ""+(-3);
				}
			}

			//proveedores existentes
			List<Proveedor> proveedores = new ArrayList<>();
			proveedores.add(cotizaciones.get(0).getProveedor());
			for(int i = 0 ; i < cotizaciones.size(); i++) {
				if(cotizaciones.get(i).getProveedor() == null) {
					return ""+(-4);
				}else {
					for(int j = 0 ; j < proveedores.size(); j++) {
						if(cotizaciones.get(i).getProveedor().getId() != proveedores.get(j).getId()) {
							proveedores.add(cotizaciones.get(i).getProveedor());
							j=0;
						}
					}
				}
			}

			List<List<Cotizacion>> lista = new ArrayList<>();
			for(int i = 0; i < proveedores.size(); i++) {
				List<Cotizacion> cotizacionesaux = new ArrayList<>();
				for(int j = 0; j < cotizaciones.size(); j++) {
					if(cotizaciones.get(j).getProveedor().getId() == proveedores.get(i).getId()) {
						Cotizacion cot = new Cotizacion();
						cot = cotizaciones.get(j);
						cotizacionesaux.add(cot);
					}
				}
				lista.add(cotizacionesaux);
			}
			
			DaoRequisicion daoRequisicion = new DaoRequisicion();
			
			for(int i = 0 ; i < lista.size(); i++) {
				DaoPresupuesto daopresupuesto = new DaoPresupuesto();
				Requisicion requisicion = new Requisicion();
				requisicion.setFechaNecesaria(solicitudCotizacion.getFechaNecesaria());
				//subtotal
				float subtotal = 0;
				float iva = 0;
				float total = 0;
				for(int j = 0; j < lista.get(i).size(); j++) {
					subtotal += lista.get(i).get(j).getSubtotal();
					iva += lista.get(i).get(j).getIva();
					total += lista.get(i).get(j).getTotal();
				}
				
				System.out.println("subtotal " + subtotal);
				System.out.println("iva " + iva);
				System.out.println("total " + total);
				
				requisicion.setSubtotal(subtotal);
				requisicion.setIva(iva);
				requisicion.setTotal(total);
				requisicion.getProveedor().setId(lista.get(i).get(0).getProveedor().getId());
				requisicion.getSociedad().setId(solicitudCotizacion.getSociedad().getId());
				requisicion.getUsuario().setId(solicitudCotizacion.getUsuario().getId());
				requisicion.getArea().setId(solicitudCotizacion.getArea().getId());
				boolean presupuesto = true;
				for(int j = 0; j < lista.get(i).size(); j++) {
					if(!daopresupuesto.verificarPresupuesto("2020", solicitudCotizacion.getSociedad().getNombre(), lista.get(i).get(j).getDetalleSolicitudCotizacion().getProducto().getId(), solicitudCotizacion.getArea().getNormaReparto(), solicitudCotizacion.getFechaNecesaria().substring(5, 7), lista.get(i).get(j).getTotal())) {
						presupuesto = false;
						j = lista.get(i).size();
					}
				}
				if(presupuesto) {
					requisicion.getEstatus().setId(1);
				}else {
					requisicion.getEstatus().setId(10);
				}
				requisicion.setCotizacion("SC-" + solicitudCotizacion.getId());
				requisicion.setComentarios("Requisición basado en SC-" + solicitudCotizacion.getId() + " " + solicitudCotizacion.getComentarios());
				requisicion.setTc(0);
				requisicion.getMoneda().setId(solicitudCotizacion.getMoneda().getId());
				for(int j = 0; j < lista.get(i).size(); j++) {
					RequisicionDetalle det = new RequisicionDetalle();
					det.setDescripcion(lista.get(i).get(j).getDetalleSolicitudCotizacion().getDescripcion());
					det.setPrecioEstimado(lista.get(i).get(j).getPrecioUnitario());
					det.setCantidad(lista.get(i).get(j).getCantidad());
					if(lista.get(i).get(j).getIva() > 0) {
						System.out.println("Si tiene Iva");
						det.setIva("16%");
					}else {
						System.out.println("No tiene iva");
						det.setIva("0%");
					}
					det.setMonto(lista.get(i).get(j).getTotal());
					det.getProducto().setId(lista.get(i).get(j).getDetalleSolicitudCotizacion().getProducto().getId());
					requisicion.getDetalle().add(det);
				}
				idnueva = ""+daoRequisicion.crearRequisicion(requisicion);
				db.ejecutar("update solicitudcotizaciones set idStatus = 6 where id = " + solicitudCotizacion.getId());
			}
			
			return idnueva;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ""+(-5);
		}
	}
}