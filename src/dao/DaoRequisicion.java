package dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import conexion.ConexionSap;

import com.sap.smb.sbo.api.IDocuments;
import com.sap.smb.sbo.api.SBOCOMConstants;
import com.sap.smb.sbo.api.SBOCOMUtil;

import conexion.ConexionBD;
import modelo.Area;
import modelo.Mail;
import modelo.Producto;
import modelo.Requisicion;
import modelo.RequisicionDetalle;
import modelo.Usuario;

public class DaoRequisicion {
	
	public Requisicion findRequisicionById(int id) {
		Requisicion requisicion = new Requisicion();
		ConexionBD db = new ConexionBD();
		
		db.consultar("SELECT \r\n" + 
				"t0.id 't0.id',t0.fechaCreacion 't0.fechaCreacion',t0.fechaNecesaria 't0.fechaNecesaria',t0.subtotal 't0.subtotal',t0.iva 't0.iva',t0.total 't0.total',t0.cotizacion 't0.cotizacion',t0.comentarios 't0.comentarios',t0.tc 't0.tc', t0.pedidoSap 't0.pedidoSap', t0.docentry 't0.docentry', t0.recibo 't0.recibo', \r\n" + 
				"t1.id 't1.id',t1.nombre 't1.nombre',\r\n" + 
				"t2.id 't2.id',t2.descripcion 't2.descripcion',t2.precioEstimado 't2.precioEstimado',t2.cantidad 't2.cantidad',t2.monto 't2.monto',t2.iva 't2.iva',\r\n" + 
				"t3.id 't3.id',t3.producto 't3.producto',t3.descripcion 't3.descripcion',t3.activo 't3.activo',t3.esAdministrativo 't3.esAdministrativo',t3.cuentaContable 't3.cuentaContable',\r\n" + 
				"t4.id 't4.id',t4.nombre 't4.nombre',\r\n" + 
				"t5.id 't5.id',t5.nombre 't5.nombre',t5.normaReparto 't5.normaReparto',t5.dimension 't5.dimension',t5.activo 't5.activo',\r\n" + 
				"t6.id 't6.id',t6.nombre 't6.nombre',\r\n" + 
				"t7.id 't7.id',t7.cardCode 't7.cardCode',t7.nombre 't7.nombre',t7.nombreExtranjero 't7.nombreExtranjero',t7.telefono 't7.telefono',t7.activo 't7.activo',\r\n" + 
				"t8.id 't8.id',t8.nombres 't8.nombres', t8.apellidop 't8.apellidop', t8.apellidom 't8.apellidom', t8.email 't8.email',t8.usuario 't8.usuario', t8.activo 't8.activo',\r\n" + 
				"t12.id 't12.id', t12.nombre 't12.nombre',t12.normaReparto 't12.normaReparto',t12.dimension 't12.dimension',t12.activo 't12.activo',\r\n" + 
				"t9.id 't9.id',t9.nombres 't9.nombres', t9.apellidop 't9.apellidop', t9.apellidom 't9.apellidom', t9.email 't9.email',t9.usuario 't9.usuario', t9.activo 't9.activo',\r\n" + 
				"t11.id 't11.id',t11.nombres 't11.nombres',t11.apellidop 't11.apellidop',t11.apellidom 't11.apellidom', t11.email 't11.email',t11.usuario 't11.usuario', t11.activo 't11.activo'\r\n" + 
				"FROM requisiciones t0\r\n" + 
				"inner join moneda t1 on t0.idMoneda = t1.id\r\n" + 
				"inner join requisicionesdetalle t2 on t0.id = t2.idrequisicion\r\n" + 
				"inner join productos t3 on t2.idproducto = t3.id\r\n" + 
				"inner join sociedades t4 on t0.idSociedad = t4.id\r\n" + 
				"inner join areas t5 on t0.idArea = t5.id\r\n" + 
				"inner join status t6 on t0.idStatus = t6.id\r\n" + 
				"inner join proveedores t7 on t0.idProveedor = t7.id\r\n" + 
				"inner join usuarios t8 on t0.idUsuario = t8.id\r\n" + 
				"inner join usuarios t9 on t5.idUsuario = t9.id\r\n" + 
				"inner join departamentos t10 on t5.idDepartamento = t10.id\r\n" + 
				"inner join usuarios t11 on t10.idUsuario = t11.id\r\n" + 
				"inner join areas t12 on t8.idArea = t12.id\r\n" + 
				"where t0.id = " + id);
		try {
			while(db.resultado.next()) {
				requisicion.setId(db.resultado.getInt("t0.id"));
				requisicion.setFechaCreacion(db.resultado.getString("t0.fechaCreacion"));
				requisicion.setFechaNecesaria(db.resultado.getString("t0.fechaNecesaria"));
				requisicion.setSubtotal(db.resultado.getFloat("t0.subtotal"));
				requisicion.setIva(db.resultado.getFloat("t0.iva"));
				requisicion.setTotal(db.resultado.getFloat("t0.total"));
				requisicion.setCotizacion(db.resultado.getString("t0.cotizacion"));
				requisicion.setComentarios(db.resultado.getString("t0.comentarios"));
				requisicion.setTc(db.resultado.getFloat("t0.tc"));
				requisicion.setDocEntry(db.resultado.getString("t0.docentry"));
				requisicion.setPedidoSap(db.resultado.getString("t0.pedidoSap"));
				requisicion.setRecibo(db.resultado.getString("t0.recibo"));
				requisicion.getMoneda().setId(db.resultado.getInt("t1.id"));
				requisicion.getMoneda().setNombre(db.resultado.getString("t1.nombre"));
				requisicion.getSociedad().setId(db.resultado.getInt("t4.id"));
				requisicion.getSociedad().setNombre(db.resultado.getString("t4.nombre"));
				requisicion.getUsuario().setId(db.resultado.getInt("t8.id"));
				requisicion.getUsuario().setNombres(db.resultado.getString("t8.nombres"));
				requisicion.getUsuario().setApellidop(db.resultado.getString("t8.apellidop"));
				requisicion.getUsuario().setApellidom(db.resultado.getString("t8.apellidom"));
				requisicion.getUsuario().setEmail(db.resultado.getString("t8.email"));
				requisicion.getUsuario().setUsuario(db.resultado.getString("t8.usuario"));
				requisicion.getUsuario().setActivo(db.resultado.getBoolean("t8.activo"));
				requisicion.getUsuario().getArea().setId(db.resultado.getInt("t12.id"));
				requisicion.getUsuario().getArea().setNombre(db.resultado.getString("t12.nombre"));
				requisicion.getUsuario().getArea().setNormaReparto(db.resultado.getString("t12.normaReparto"));
				requisicion.getArea().setId(db.resultado.getInt("t5.id"));
				requisicion.getArea().setNombre(db.resultado.getString("t5.nombre"));
				requisicion.getArea().setNormaReparto(db.resultado.getString("t5.normaReparto"));
				requisicion.getArea().setDimension(db.resultado.getString("t5.dimension"));
				requisicion.getArea().setActivo(db.resultado.getBoolean("t5.activo"));
				requisicion.getArea().getGerente().setId(db.resultado.getInt("t9.id"));
				requisicion.getArea().getGerente().setNombres(db.resultado.getString("t9.nombres"));
				requisicion.getArea().getGerente().setApellidop(db.resultado.getString("t9.apellidop"));
				requisicion.getArea().getGerente().setApellidom(db.resultado.getString("t9.apellidom"));
				requisicion.getArea().getGerente().setEmail(db.resultado.getString("t9.email"));
				requisicion.getArea().getGerente().setUsuario(db.resultado.getString("t9.usuario"));
				requisicion.getArea().getGerente().setActivo(db.resultado.getBoolean("t9.activo"));			
				requisicion.getArea().getDepartamento().getDirector().setId(db.resultado.getInt("t11.id"));
				requisicion.getArea().getDepartamento().getDirector().setNombres(db.resultado.getString("t11.nombres"));
				requisicion.getArea().getDepartamento().getDirector().setApellidop(db.resultado.getString("t11.apellidop"));
				requisicion.getArea().getDepartamento().getDirector().setApellidom(db.resultado.getString("t11.apellidom"));
				requisicion.getArea().getDepartamento().getDirector().setEmail(db.resultado.getString("t11.email"));
				requisicion.getArea().getDepartamento().getDirector().setUsuario(db.resultado.getString("t11.usuario"));
				requisicion.getArea().getDepartamento().getDirector().setActivo(db.resultado.getBoolean("t11.activo"));					
				requisicion.getEstatus().setId(db.resultado.getInt("t6.id"));
				requisicion.getEstatus().setNombre(db.resultado.getString("t6.nombre"));
				requisicion.getProveedor().setId(db.resultado.getInt("t7.id"));
				requisicion.getProveedor().setCardCode(db.resultado.getString("t7.cardCode"));
				requisicion.getProveedor().setNombre(db.resultado.getString("t7.nombre"));
				requisicion.getProveedor().setNombreExtranjero(db.resultado.getString("t7.nombreExtranjero"));
				requisicion.getProveedor().setTelefono(db.resultado.getString("t7.telefono"));
				requisicion.getProveedor().setActivo(db.resultado.getBoolean("t7.activo"));
				RequisicionDetalle detalle = new RequisicionDetalle();
				detalle.setId(db.resultado.getInt("t2.id"));
				detalle.setDescripcion(db.resultado.getString("t2.descripcion"));
				detalle.setMonto(db.resultado.getFloat("t2.precioEstimado"));
				detalle.setCantidad(db.resultado.getFloat("t2.cantidad"));
				detalle.setMonto(db.resultado.getFloat("t2.monto"));
				detalle.setIva(db.resultado.getString("t2.iva"));
				detalle.getProducto().setId(db.resultado.getInt("t3.id"));
				detalle.getProducto().setProducto(db.resultado.getString("t3.producto"));
				detalle.getProducto().setDescripcion(db.resultado.getString("t3.descripcion"));
				detalle.getProducto().setActivo(db.resultado.getBoolean("t3.activo"));
				detalle.getProducto().setEsAdministrativo(db.resultado.getBoolean("t3.esAdministrativo"));
				detalle.getProducto().setCuentaContable(db.resultado.getString("t3.cuentaContable"));
				requisicion.getDetalle().add(detalle);
			}
			return requisicion;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Requisicion> findauthorized(){
		List<Requisicion> lista = new ArrayList<>();
		ConexionBD db = new ConexionBD();
		
		db.consultar("select t0.id 't0.id', t1.nombre 't1.nombre', t2.nombre 't2.nombre', t3.usuario 't3.usuario', t0.fechaCreacion 't0.fechaCreacion', t0.fechaNecesaria 't0.fechaNecesaria',"+
				"t4.nombre 't4.nombre', t0.total 't0.total', t5.nombre 't5.nombre' from requisiciones t0\r\n" + 
				"inner join sociedades t1 on t0.idSociedad = t1.id\r\n" + 
				"inner join areas t2 on t0.idArea = t2.id\r\n" + 
				"inner join usuarios t3 on t0.idUsuario = t3.id\r\n" + 
				"inner join proveedores t4 on t0.idProveedor = t4.id\r\n" + 
				"inner join [status] t5 on t0.idStatus = t5.id\r\n" + 
				"where t5.id in (4,9,12)");
		
		try {
			while(db.resultado.next()) {
				Requisicion requisicion = new Requisicion();
				requisicion.setId(db.resultado.getInt("t0.id"));
				requisicion.getSociedad().setNombre(db.resultado.getString("t1.nombre"));
				requisicion.getArea().setNombre(db.resultado.getString("t2.nombre"));
				requisicion.getUsuario().setUsuario(db.resultado.getString("t3.usuario"));
				requisicion.setFechaCreacion(db.resultado.getString("t0.fechaCreacion"));
				requisicion.setFechaNecesaria(db.resultado.getString("t0.fechaNecesaria"));
				requisicion.getProveedor().setNombre(db.resultado.getString("t4.nombre"));
				requisicion.setTotal(db.resultado.getFloat("t0.total"));
				requisicion.getEstatus().setNombre(db.resultado.getString("t5.nombre"));
				lista.add(requisicion);
			}
			return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Requisicion buscarRequisicion(int idrequisicion){
		try {
		ConexionBD db = new ConexionBD();
		Requisicion requi = new Requisicion();
		
		db.consultar("SELECT t0.id 't0.id', t0.descripcion 't0.descripcion', t0.precioEstimado 't0.precioEstimado', t0.cantidad 't0.cantidad', t0.monto 't0.monto', t0.iva 't0.iva', t1.pedidoSap 't1.pedidoSap', t1.recibo 't1.recibo', t1.docentry 't1.docentry', \r\n" + 
				"t1.id 't1.id', t1.fechaCreacion 't1.fechaCreacion', t1.fechaNecesaria 't1.fechaNecesaria', t1.subtotal 't1.subtotal', t1.iva 't1.iva', t1.total 't1.total', t1.idStatus 't1.idStatus', \r\n" + 
				"t1.cotizacion 't1.cotizacion', t1.comentarios 't1.comentarios', t1.tc 't1.tc', t1.idMoneda 't1.idMoneda', t2.id 't2.id', t2.producto 't2.producto', t2.descripcion 't2.descripcion', \r\n" + 
				"t2.activo 't2.activo',t2.esAdministrativo 't2.esAdministrativo', t2.cuentaContable 't2.cuentaContable' , t3.id 't3.id', t3.cardCode 't3.cardCode', t3.nombre 't3.nombre', t3.nombreExtranjero 't3.nombreExtranjero', t3.telefono 't3.telefono', \r\n" + 
				"t3.activo 't3.activo', t4.id 't4.id', t4.nombres 't4.nombres', t4.apellidop 't4.apellidop', t4.apellidom 't4.apellidom', t4.email 't4.email', t4.usuario 't4.usuario', \r\n" + 
				"t5.id 't5.id', t5.nombre 't5.nombre', t6.id 't6.id', t6.nombre 't6.nombre', t6.normaReparto 't6.normaReparto', t6.dimension 't6.dimension', \r\n" + 
				"t7.id 't7.id', t7.nombre 't7.nombre', t9.id 't9.id', t9.nombres 't9.nombres', t9.apellidop 't9.apellidop', t9.email 't9.email', t9.usuario 't9.usuario', t10.id 't10.id', t10.nombres 't10.nombres', \r\n" + 
				"t10.apellidop 't10.apellidop', t10.email 't10.email', t10.usuario 't10.usuario', t11.nombre 't11.nombre', t12.nombre 't12.nombre' \r\n" + 
				"FROM \r\n" + 
				"requisicionesdetalle t0   \r\n" + 
				"inner join requisiciones t1 on t0.idrequisicion = t1.id  \r\n" + 
				"inner join productos t2 on t0.idproducto = t2.id  \r\n" + 
				"inner join proveedores t3 on t1.idProveedor = t3.id  \r\n" + 
				"inner join usuarios t4 on t1.idUsuario = t4.id  \r\n" + 
				"inner join sociedades t5 on t1.idSociedad = t5.id  \r\n" + 
				"inner join areas t6 on t1.idArea = t6.id  \r\n" + 
				"inner join [status] t7 on t1.idStatus = t7.id \r\n" + 
				"inner join departamentos t8 on t6.idDepartamento = t8.id \r\n" + 
				"inner join usuarios t9 on t6.idUsuario = t9.id \r\n" + 
				"inner join usuarios t10 on t8.idUsuario = t10.id  \r\n" + 
				"inner join areas t11 on t11.id = t4.idArea  \r\n" + 
				"inner join moneda t12 on t12.id = t1.idMoneda  \r\n" + 
				"where t0.idrequisicion = " + idrequisicion);

			while(db.resultado.next()) {
				requi.setId(db.resultado.getInt("t1.id"));
				requi.setFechaCreacion(db.resultado.getString("t1.fechaCreacion"));
				requi.setFechaNecesaria(db.resultado.getString("t1.fechaNecesaria"));
				requi.setSubtotal(db.resultado.getFloat("t1.subtotal"));
				requi.setIva(db.resultado.getFloat("t1.iva"));
				requi.setTotal(db.resultado.getFloat("t1.total"));
				requi.getEstatus().setId(db.resultado.getInt("t1.idStatus"));
				requi.setCotizacion(db.resultado.getString("t1.cotizacion"));
				requi.setComentarios(db.resultado.getString("t1.comentarios"));
				requi.setTc(db.resultado.getFloat("t1.tc"));
				requi.setPedidoSap(db.resultado.getString("t1.pedidoSap"));
				requi.setRecibo(db.resultado.getString("t1.recibo"));
				requi.setDocEntry(db.resultado.getString("t1.docentry"));
				requi.getMoneda().setId(db.resultado.getInt("t1.idMoneda"));
				
				requi.getDetalle().add(new RequisicionDetalle(
						db.resultado.getInt("t0.id"),
						db.resultado.getString("t0.descripcion"),
						db.resultado.getFloat("t0.precioEstimado"),
						db.resultado.getFloat("t0.cantidad"),
						db.resultado.getFloat("t0.monto"),
						db.resultado.getString("t0.iva"),
						new Producto(
								db.resultado.getInt("t2.id"),
								db.resultado.getString("t2.producto"),
								db.resultado.getString("t2.descripcion"),
								db.resultado.getBoolean("t2.activo"),
								db.resultado.getBoolean("t2.esAdministrativo"),
								db.resultado.getString("t2.cuentaContable")
						)	
						));

				requi.getProveedor().setId(db.resultado.getInt("t3.id"));
				requi.getProveedor().setCardCode(db.resultado.getString("t3.cardCode"));
				requi.getProveedor().setNombre(db.resultado.getString("t3.nombre"));
				requi.getProveedor().setNombreExtranjero(db.resultado.getString("t3.nombreExtranjero"));
				requi.getProveedor().setTelefono(db.resultado.getString("t3.telefono"));
				requi.getProveedor().setActivo(db.resultado.getBoolean("t3.activo"));
				requi.getUsuario().setId(db.resultado.getInt("t4.id"));
				requi.getUsuario().setNombres(db.resultado.getString("t4.nombres"));
				requi.getUsuario().setApellidop(db.resultado.getString("t4.apellidop"));
				requi.getUsuario().setApellidom(db.resultado.getString("t4.apellidom"));
				requi.getUsuario().setEmail(db.resultado.getString("t4.email"));
				requi.getUsuario().setUsuario(db.resultado.getString("t4.usuario"));
				requi.getSociedad().setId(db.resultado.getInt("t5.id"));
				requi.getSociedad().setNombre(db.resultado.getString("t5.nombre"));
				requi.getArea().setId(db.resultado.getInt("t6.id"));
				requi.getArea().setNombre(db.resultado.getString("t6.nombre"));
				requi.getArea().setNormaReparto(db.resultado.getString("t6.normaReparto"));
				requi.getArea().setDimension(db.resultado.getString("t6.dimension"));
				requi.getEstatus().setId(db.resultado.getInt("t7.id"));
				requi.getEstatus().setNombre(db.resultado.getString("t7.nombre"));
				requi.getArea().getGerente().setId(db.resultado.getInt("t9.id"));
				requi.getArea().getGerente().setNombres(db.resultado.getString("t9.nombres"));
				requi.getArea().getGerente().setApellidop(db.resultado.getString("t9.apellidop"));
				requi.getArea().getGerente().setEmail(db.resultado.getString("t9.email"));
				requi.getArea().getGerente().setUsuario(db.resultado.getString("t9.usuario"));
				requi.getArea().getDepartamento().getDirector().setId(db.resultado.getInt("t10.id"));
				requi.getArea().getDepartamento().getDirector().setNombres(db.resultado.getString("t10.nombres"));
				requi.getArea().getDepartamento().getDirector().setApellidop(db.resultado.getString("t10.apellidop"));
				requi.getArea().getDepartamento().getDirector().setEmail(db.resultado.getString("t10.email"));
				requi.getArea().getDepartamento().getDirector().setUsuario(db.resultado.getString("t10.usuario"));
				requi.getUsuario().getArea().setNombre(db.resultado.getString("t11.nombre"));
				requi.getMoneda().setNombre(db.resultado.getString("t12.nombre"));
			}
			return requi;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Requisicion validar(Requisicion requisicion) {	
		DaoDepartamento daodepartamento = new DaoDepartamento();
		
		if(requisicion.getTotal() > daodepartamento.buscarDepartamento(requisicion.getArea().getDepartamento().getId()).getMonto()) {
			requisicion.getEstatus().setId(7);
		}
		
		boolean presupuesto = true;
		DaoPresupuesto daopresupuesto = new DaoPresupuesto();
		DaoArea daoarea = new DaoArea();
		
		Area area = daoarea.buscarArea(requisicion.getArea().getId());
		
		requisicion.setArea(area);
		
		for(int i = 0; i < requisicion.getDetalle().size(); i++) {
			presupuesto = daopresupuesto.verificarPresupuesto(
					requisicion.getFechaNecesaria().substring(0, 4), 
					requisicion.getSociedad().getNombre(), 
					requisicion.getDetalle().get(i).getProducto().getId(), 
					requisicion.getArea().getNormaReparto(), 
					requisicion.getFechaNecesaria().substring(5, 7), 
					requisicion.getDetalle().get(i).getPrecioEstimado());
		}
		if(presupuesto) {
			requisicion.getEstatus().setId(1);
		}else {
			requisicion.getEstatus().setId(10);
		}

		return requisicion;
	}
	
	public int crearRequisicion(Requisicion requisicion) {
		ConexionBD db = new ConexionBD();
		try {
		db.ejecutar("insert into requisiciones (fechaCreacion,fechaNecesaria,subtotal,iva,total,idProveedor,idUsuario,idSociedad,idArea,idStatus,cotizacion,comentarios,tc,idMoneda) values "
				  + "(getDate(), '"+requisicion.getFechaNecesaria()+"',"+requisicion.getSubtotal()+","+requisicion.getIva()+","+requisicion.getTotal()+", "
				  + requisicion.getProveedor().getId() + ", "+requisicion.getUsuario().getId()+", "+requisicion.getSociedad().getId()+","+requisicion.getArea().getId()+", "
		  		  + requisicion.getEstatus().getId() + ",'"+requisicion.getCotizacion()+"','"+requisicion.getComentarios()+"',"+requisicion.getTc()+","+requisicion.getMoneda().getId()+")");
		db.consultar("select max(id) from requisiciones");
		int idNueva = 0;

		if(db.resultado.next()) {
			idNueva = db.resultado.getInt(1);
		}

		for(int i = 0 ; i < requisicion.getDetalle().size(); i++) {
			db.ejecutar("insert into requisicionesdetalle (descripcion,precioEstimado,cantidad,monto,iva,idrequisicion,idproducto) values "
					  + "('"+requisicion.getDetalle().get(i).getDescripcion()+"',"+requisicion.getDetalle().get(i).getPrecioEstimado()+","+requisicion.getDetalle().get(i).getCantidad()+","+requisicion.getDetalle().get(i).getMonto()+" , "
					  + "'" + requisicion.getDetalle().get(i).getIva()+"',"+idNueva+","+requisicion.getDetalle().get(i).getProducto().getId()+")");
		}
		
		Requisicion listaMail = new Requisicion();
		
		listaMail = buscarRequisicion(idNueva);
		
		switch(requisicion.getEstatus().getId()) {
			case 1: 
				Mail mail = new Mail();
				mail.RequisicionCreada(listaMail);
				Mail mail2 = new Mail();
				mail2.autorizarRequisicion(listaMail);
			break;
			case 10:
				Mail mail3 = new Mail();
				mail3.RequisicionCreada(listaMail);
				Mail mail4 = new Mail();
				mail4.autorizarRequisicionSinPresupuestoGerente(listaMail);
			break;
			case 7:
				Mail mail5 = new Mail();
				mail5.RequisicionCreada(listaMail);
				Mail mail6 = new Mail();
				mail6.autorizarRequisicionMayorGerente(listaMail);
			break;
		}
		
		return idNueva;
		
		} catch (SQLException e) {
			return 0;
		}
	}
	
	public List<Requisicion> importarRequisicionesUsuario(int idusuario){
		List<Requisicion> lista = new ArrayList<>();
		DaoUsuarios daousuario = new DaoUsuarios();
		Usuario usuario =daousuario.buscarUsuarioId(idusuario);
		ConexionBD db = new ConexionBD();
		System.out.println(usuario.toString());
		try {
			
		if(usuario.getTipousuario().getId() == 6) {
			db.consultar("SELECT t0.id Folio, t0.pedidoSap pedidoSap, t0.recibo Recibo, t3.nombre Sociedad, t4.nombre Area, t0.fechaCreacion Fecha, t0.fechaNecesaria 'Fecha Necesaria', t1.nombre Proveedor, t0.total, t5.nombre 'Status', t2.usuario 'usuario'\r\n" + 
					"  FROM requisiciones t0\r\n" + 
					"  inner join proveedores t1 on t0.idProveedor = t1.id\r\n" + 
					"  inner join usuarios t2 on t0.idUsuario = t2.id\r\n" + 
					"  inner join sociedades t3 on t0.idSociedad = t3.id\r\n" + 
					"  inner join areas t4 on t0.idArea = t4.id\r\n" + 
					"  inner join [status] t5 on t0.idStatus = t5.id order by t0.id desc");
		}else if(usuario.getTipousuario().getId() == 5) {
			db.consultar("SELECT t0.id Folio, t0.pedidoSap pedidoSap, t0.recibo Recibo, t3.nombre Sociedad, t4.nombre Area, t0.fechaCreacion Fecha, t0.fechaNecesaria 'Fecha Necesaria', t1.nombre Proveedor, t0.total, t5.nombre 'Status', t2.usuario 'usuario'\r\n" + 
					"  FROM requisiciones t0\r\n" + 
					"  inner join proveedores t1 on t0.idProveedor = t1.id\r\n" + 
					"  inner join usuarios t2 on t0.idUsuario = t2.id\r\n" + 
					"  inner join sociedades t3 on t0.idSociedad = t3.id\r\n" + 
					"  inner join areas t4 on t0.idArea = t4.id\r\n" + 
					"  inner join [status] t5 on t0.idStatus = t5.id where t5.id in (4,8,9,11,12,13)"  + " order by t0.id desc");
		}else if(usuario.getTipousuario().getId() == 4) {
			db.consultar("SELECT t0.id Folio, t0.pedidoSap pedidoSap, t0.recibo Recibo, t3.nombre Sociedad, t4.nombre Area, t0.fechaCreacion Fecha, t0.fechaNecesaria 'Fecha Necesaria', t1.nombre Proveedor, t0.total, t5.nombre 'Status', t2.usuario 'usuario'\r\n" + 
					"  FROM requisiciones t0\r\n" + 
					"  inner join proveedores t1 on t0.idProveedor = t1.id\r\n" + 
					"  inner join usuarios t2 on t0.idUsuario = t2.id\r\n" + 
					"  inner join sociedades t3 on t0.idSociedad = t3.id\r\n" + 
					"  inner join areas t4 on t0.idArea = t4.id\r\n" + 
					"  inner join [status] t5 on t0.idStatus = t5.id "  + " order by t0.id desc");
		} else if(usuario.getTipousuario().getId() == 3) {
			db.consultar("SELECT t0.id Folio, t0.pedidoSap pedidoSap, t0.recibo Recibo, t3.nombre Sociedad, t4.nombre Area, t0.fechaCreacion Fecha, t0.fechaNecesaria 'Fecha Necesaria', t1.nombre Proveedor, t0.total, t5.nombre 'Status', t2.usuario 'usuario'\r\n" + 
					"  FROM requisiciones t0\r\n" + 
					"  inner join proveedores t1 on t0.idProveedor = t1.id\r\n" + 
					"  inner join usuarios t2 on t0.idUsuario = t2.id\r\n" + 
					"  inner join sociedades t3 on t0.idSociedad = t3.id\r\n" + 
					"  inner join areas t4 on t0.idArea = t4.id\r\n" + 
					"  inner join [status] t5 on t0.idStatus = t5.id where t2.id = " + idusuario + " order by t0.id desc");
		}else if(usuario.getTipousuario().getId() == 2) {
			db.consultar("SELECT t0.id Folio, t0.pedidoSap pedidoSap, t0.recibo Recibo, t3.nombre Sociedad, t4.nombre Area, t0.fechaCreacion Fecha, t0.fechaNecesaria 'Fecha Necesaria', t1.nombre Proveedor, t0.total, t5.nombre 'Status', t2.usuario 'usuario'\r\n" + 
					"  FROM requisiciones t0\r\n" + 
					"  inner join proveedores t1 on t0.idProveedor = t1.id\r\n" + 
					"  inner join usuarios t2 on t0.idUsuario = t2.id\r\n" + 
					"  inner join sociedades t3 on t0.idSociedad = t3.id\r\n" + 
					"  inner join areas t4 on t0.idArea = t4.id\r\n" + 
					"  inner join [status] t5 on t0.idStatus = t5.id where t0.idArea = " + usuario.getArea().getId() + " order by t0.id desc");
		}else if(usuario.getTipousuario().getId() == 1) {
			DaoArea daoarea = new DaoArea();
			Area area = daoarea.buscarArea(usuario.getArea().getId());
			db.consultar("SELECT t0.id Folio, t0.pedidoSap pedidoSap, t0.recibo Recibo, t3.nombre Sociedad, t4.nombre Area, t0.fechaCreacion Fecha, t0.fechaNecesaria 'Fecha Necesaria', t1.nombre Proveedor, t0.total, t5.nombre 'Status', t2.usuario 'usuario'\r\n" + 
					"  FROM requisiciones t0\r\n" + 
					"  inner join proveedores t1 on t0.idProveedor = t1.id\r\n" + 
					"  inner join usuarios t2 on t0.idUsuario = t2.id\r\n" + 
					"  inner join sociedades t3 on t0.idSociedad = t3.id\r\n" + 
					"  inner join areas t4 on t0.idArea = t4.id\r\n" + 
					"  inner join [status] t5 on t0.idStatus = t5.id  " +
					"  inner join [departamentos] t6 on t4.idDepartamento = t6.id " +
					"  where t6.id = " + area.getDepartamento().getId()  + " order by t0.id desc");
		}
		
		while(db.resultado.next()) {
			Requisicion requisiciones = new Requisicion();
			requisiciones.setId(db.resultado.getInt("Folio"));
			requisiciones.setPedidoSap(db.resultado.getString("pedidoSap"));
			requisiciones.setRecibo(db.resultado.getString("Recibo"));
			requisiciones.getSociedad().setNombre(db.resultado.getString("Sociedad"));
			requisiciones.getArea().setNombre(db.resultado.getString("Area"));
			requisiciones.setFechaCreacion(db.resultado.getString("Fecha"));
			requisiciones.setFechaNecesaria(db.resultado.getString("Fecha Necesaria"));
			requisiciones.getProveedor().setNombre(db.resultado.getString("Proveedor"));
			requisiciones.setTotal(db.resultado.getFloat("total"));
			requisiciones.getEstatus().setNombre(db.resultado.getString("Status"));
			requisiciones.getUsuario().setUsuario(db.resultado.getString("usuario"));
			lista.add(requisiciones);
		}
		return lista;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String rechazar(int idrequisicion, String motivo) {
		ConexionBD db = new ConexionBD();
		Requisicion requisicion = new Requisicion();
		requisicion = buscarRequisicion(idrequisicion);
		
		if(requisicion.getEstatus().getId() == 2) {
			return "La requisicion no se puede rechazar por que ya esta cancelada.";
		}
		
		if(requisicion.getEstatus().getId() == 3) {
			return "La requisicion no se puede rechazar por que ya esta vencida.";
		}
		
		if(requisicion.getEstatus().getId() == 5) {
			return "Ya esta rechazada la requisicion.";
		}
		
		if(requisicion.getEstatus().getId() == 6) {
			return "La requisicion no se puede rechazar por que ya esta cerrada.";
		}
		
		db.ejecutar("update requisiciones set idstatus = 5, comentarios = comentarios + ' \nRechazada por: " + motivo + "'  where id = " + idrequisicion);
		requisicion.setComentarios(requisicion.getComentarios() + "\nRechazada por: " + motivo);
		
		Mail mail = new Mail();
		mail.requisicionRechazada(requisicion, motivo);
		return "Se rechazo con exito!";
	}
	
	public String cancelar(int idrequisicion) {
		ConexionBD db = new ConexionBD();
		Requisicion requisicion = new Requisicion();
		requisicion = buscarRequisicion(idrequisicion);
		
		if(requisicion.getEstatus().getId() == 2) {
			return "La requisicion no se puede cancelar por que ya habia sido cancelada.";
		}
		
		if(requisicion.getEstatus().getId() == 3) {
			return "La requisicion no se puede cancelar por que ya esta vencida.";
		}
		
		if(requisicion.getEstatus().getId() == 5) {
			return "No se puede reechazar por que esta cancelada.";
		}
		
		if(requisicion.getEstatus().getId() == 6) {
			return "La requisicion no se puede cancelar por que ya esta cerrada.";
		}
		
		db.ejecutar("update requisiciones set idstatus = 2 where id = " + idrequisicion);
		
		Mail mail = new Mail();
		mail.requisicionCancelada(requisicion);
		return "Se cancelo con exito!";
	}
	
	public String actualizarEstatus(int idrequisicion, int estatus) {
		Requisicion requisicion = new Requisicion();
		requisicion = buscarRequisicion(idrequisicion);
		ConexionBD db = new ConexionBD();
		String mensaje = "";
		
		if(estatus == 4 && requisicion.getEstatus().getId() == 14) {
			Mail mail = new Mail();

			mail.requisicionAutorizada(requisicion, requisicion.getArea().getGerente().getNombres() + " " + requisicion.getArea().getGerente().getApellidop());
			db.ejecutar("update requisiciones set idstatus = "+estatus+" where id = " + requisicion.getId());
			
			return mensaje;
		}
		
		if(estatus == 4) {
			if(requisicion.getEstatus().getId() == 1) {
				
				boolean requiereTI = false;
				
				if(requisicion.getArea().getId() != 11){
					for(RequisicionDetalle detalle : requisicion.getDetalle()) {
						if(detalle.getProducto().getProducto().endsWith("MANCO") || detalle.getProducto().getProducto().endsWith("COMPUTO")) {
							requiereTI = true;
						}
					}
				}
				
				if(requiereTI) {
					Mail mail = new Mail();
					mail.autorizarRequisicionTI(requisicion);
					
					estatus = 14;
					
					db.ejecutar("update requisiciones set idstatus = "+estatus+" where id = " + requisicion.getId());
				} else {
					Mail mail = new Mail();

					mail.requisicionAutorizada(requisicion, requisicion.getArea().getGerente().getNombres() + " " + requisicion.getArea().getGerente().getApellidop());
					db.ejecutar("update requisiciones set idstatus = "+estatus+" where id = " + requisicion.getId());
				}
				
				mensaje = "Se autorizo con exito!";
			} else {
				mensaje = "No se puede autorizar por que ya no esta activa la requisicion!";
			}
		}else if(estatus == 5) {
			if(requisicion.getEstatus().getId() == 1) {
				db.ejecutar("update requisiciones set idstatus = "+estatus+" where id = " + requisicion.getId());
				
				Mail mail = new Mail();
				mail.requisicionRechazada(requisicion,"");
				mensaje = "Se rechazo con exito!";
			}else {
				mensaje = "No se puede rechazar por que ya no esta activa la requisicion!";
			}
		}else if(estatus == 8) {
			if(requisicion.getEstatus().getId() == 7) {
				db.ejecutar("update requisiciones set idstatus = "+estatus+" where id = " + requisicion.getId());
				
				Mail mail = new Mail();
				mail.autorizarRequisicionMayorDirector(requisicion);
				
				mensaje = "Se autorizo con exito!";
			} else {
				mensaje = "No se puede autorizar por que ya no esta activa la requisicion!";
			}
		}else if(estatus == 9) {
			if(requisicion.getEstatus().getId() == 8) {
				
				boolean requiereTI = false;
				
				for(RequisicionDetalle detalle : requisicion.getDetalle()) {
					if(detalle.getProducto().getProducto().endsWith("MANCO") || detalle.getProducto().getProducto().endsWith("COMPUTO")) {
						requiereTI = true;
					}
				}
				
				if(requiereTI) {
					Mail mail = new Mail();
					mail.autorizarRequisicionTI(requisicion);
					
					estatus = 14;
					
					db.ejecutar("update requisiciones set idstatus = "+estatus+" where id = " + requisicion.getId());
				} else {
					db.ejecutar("update requisiciones set idstatus = "+estatus+" where id = " + requisicion.getId());
					
					Mail mail = new Mail();
					mail.requisicionAutorizada(requisicion, requisicion.getArea().getDepartamento().getDirector().getNombres() + " " + requisicion.getArea().getDepartamento().getDirector().getApellidop());
				}
				
				mensaje = "Se autorizo con exito!";
			} else {
				mensaje = "No se puede autorizar por que ya no esta activa la requisicion!";
			}
		}else if(estatus == 11) {
			if(requisicion.getEstatus().getId() == 10) {
				db.ejecutar("update requisiciones set idstatus = "+estatus+" where id = " + requisicion.getId());
				
				Mail mail = new Mail();
				mail.autorizarRequisicionSinPresupuestoDirector(requisicion);
				
				mensaje = "Se autorizo con exito!";
			} else {
				mensaje = "No se puede autorizar por que ya no esta activa la requisicion!";
			}
		} else if(estatus == 12) {
			if(requisicion.getEstatus().getId() == 11) {
				
				boolean requiereTI = false;
				
				for(RequisicionDetalle detalle : requisicion.getDetalle()) {
					if(detalle.getProducto().getProducto().endsWith("MANCO") || detalle.getProducto().getProducto().endsWith("COMPUTO")) {
						requiereTI = true;
					}
				}
				
				if(requiereTI) {
					Mail mail = new Mail();
					mail.autorizarRequisicionTI(requisicion);
					
					estatus = 14;
					
					db.ejecutar("update requisiciones set idstatus = "+estatus+" where id = " + requisicion.getId());
				}else {
					db.ejecutar("update requisiciones set idstatus = "+estatus+" where id = " + requisicion.getId());
					
					Mail mail = new Mail();
					mail.requisicionAutorizada(requisicion, requisicion.getArea().getDepartamento().getDirector().getNombres() + " " + requisicion.getArea().getDepartamento().getDirector().getApellidop());
				}

				mensaje = "Se autorizo con exito!";
			} else {
				mensaje = "No se puede autorizar por que ya no esta activa la requisicion!";
			}
		}
		
		return mensaje;
	}
	
	public String crearOrdenSAP(int idrequisicion) {
		ConexionBD db = new ConexionBD();
		
		Requisicion requisicion = new Requisicion();
		requisicion = buscarRequisicion(idrequisicion);
		
		try {
			
		if(requisicion.getEstatus().getId() == 2) {
			return "La requisicion esta cancelada";
		}
		
		if(requisicion.getEstatus().getId() == 3) {
			return "La requisicion esta vencida";
		}
		
		if(requisicion.getEstatus().getId() == 5) {
			return "La requisicion esta rechazada";
		}
		
		if(requisicion.getEstatus().getId() == 6) {
			return "La requisicion ya se envio a SAP";
		}
		
		if(requisicion.getEstatus().getId() != 4 && requisicion.getEstatus().getId() != 9 && requisicion.getEstatus().getId() != 12) {
			return "Aun no esta autorizado";
		}
		
	    Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(requisicion.getFechaCreacion());  
	    Date fecharequerida = new SimpleDateFormat("yyyy-MM-dd").parse(requisicion.getFechaNecesaria());  
		
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(fecha); // Configuramos la fecha que se recibe
	    calendar.add(Calendar.DAY_OF_YEAR, 3);  // numero de d�as a a�adir, o restar en caso de d�as<0

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
		
		IDocuments order = SBOCOMUtil.newDocuments(sap.company, SBOCOMConstants.BoObjectTypes_Document_oPurchaseOrders);
		
		if(requisicion.getMoneda().getId() == 2) {
			order.setDocCurrency("USD");
			if(requisicion.getTc() > 0){
				order.setDocRate(Double.parseDouble(String.valueOf(requisicion.getTc())));
			}
		}

		order.setCardCode(requisicion.getProveedor().getCardCode());
		order.setNumAtCard("Oe-" + requisicion.getId());
		order.setDocDate(fecha);
		order.setDocDueDate(fecharequerida);
		order.setSalesPersonCode(-1);
		
		
		if(requisicion.getComentarios().length() < 254)
			order.setComments(requisicion.getComentarios());
		else
			order.setComments(requisicion.getComentarios().substring(0, 254));
		
		for(int i = 0; i < requisicion.getDetalle().size(); i++) {
			order.getLines().setItemCode(requisicion.getDetalle().get(i).getProducto().getProducto());
			order.getLines().setQuantity(Double.valueOf(requisicion.getDetalle().get(i).getCantidad()));
			order.getLines().setWarehouseCode("10");
			if(requisicion.getDetalle().get(i).getIva().equals("16%")) {
				order.getLines().setTaxCode("IVAC16");
			}else {
				order.getLines().setTaxCode("IVAC0");
			}
			if(requisicion.getDetalle().get(i).getDescripcion().length() < 99 || requisicion.getDetalle().get(i).getDescripcion() == null) {
				order.getLines().setItemDescription(requisicion.getDetalle().get(i).getDescripcion());
			}else {
				order.getLines().setItemDescription(requisicion.getDetalle().get(i).getDescripcion().substring(0,100));
			}
			
			switch(requisicion.getArea().getDimension()) {
				case "1":
					order.getLines().setCostingCode(requisicion.getArea().getNormaReparto());
					order.getLines().setCOGSCostingCode(requisicion.getArea().getNormaReparto());	
				break;
				case "2":
					order.getLines().setCostingCode2(requisicion.getArea().getNormaReparto());
					order.getLines().setCOGSCostingCode2(requisicion.getArea().getNormaReparto());	
				break;
				case "3":
					order.getLines().setCostingCode3(requisicion.getArea().getNormaReparto());
					order.getLines().setCOGSCostingCode3(requisicion.getArea().getNormaReparto());	
				break;
				case "4":
					order.getLines().setCostingCode4(requisicion.getArea().getNormaReparto());
					order.getLines().setCOGSCostingCode4(requisicion.getArea().getNormaReparto());	
				break;
				case "5":
					order.getLines().setCostingCode5(requisicion.getArea().getNormaReparto());
					order.getLines().setCOGSCostingCode5(requisicion.getArea().getNormaReparto());	
				break;
			}
			
			order.getLines().setUnitPrice(Double.valueOf(requisicion.getDetalle().get(i).getPrecioEstimado()));
			order.getLines().add();
		}

		if(order.add() == 0 ) {
			int nuevo = Integer.parseInt(sap.company.getNewObjectCode());
			IDocuments newOrder = SBOCOMUtil.getDocuments(sap.company, SBOCOMConstants.BoObjectTypes_Document_oPurchaseOrders, nuevo);
			db.ejecutar("update requisiciones set idstatus = 6, pedidoSap = '"+newOrder.getDocNum()+"', docEntry = '"+nuevo+"' where id = " + requisicion.getId());
			sap.company.disconnect();
			return "El folio del nuevo pedido es: " + nuevo;
		}else {
			sap.company.disconnect();
			String error = sap.company.getLastErrorDescription();
			db.ejecutar("update requisiciones set idstatus = "+requisicion.getEstatus().getId()+" where id = " + requisicion.getId());
			return "Hubo un error al crear la requisicion: " + error;
		}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			db.ejecutar("update requisiciones set idstatus = "+requisicion.getEstatus().getId()+" where id = " + requisicion.getId());
			return "No se logro crear la Orden de Venta en SAP";
		}
	}

//	public List<Requisiciones> buscarRequisicion(int id){
//		List<Requisiciones> lista = new ArrayList<>();
//		ConexionBD db = new ConexionBD();
//		try {
//		db.consultar("SELECT t0.id Folio, t3.nombre Sociedad, t4.nombre Area, t0.fechaCreacion Fecha, t0.fechaNecesaria 'Fecha Necesaria', t1.nombre Proveedor, t0.total, t5.nombre 'Status'\r\n" + 
//				"  FROM requisiciones t0\r\n" + 
//				"  inner join proveedores t1 on t0.idProveedor = t1.id\r\n" + 
//				"  inner join usuarios t2 on t0.idUsuario = t2.id\r\n" + 
//				"  inner join sociedades t3 on t0.idSociedad = t3.id\r\n" + 
//				"  inner join areas t4 on t0.idArea = t4.id\r\n" + 
//				"  inner join [status] t5 on t0.idStatus = t5.id where t0.id = " + id);
//		
//			while(db.resultado.next()) {
//				Requisiciones requisiciones = new Requisiciones();
//				requisiciones.setId(db.resultado.getInt("Folio"));
//				requisiciones.setSociedad(db.resultado.getString("Sociedad"));
//				requisiciones.setArea(db.resultado.getString("Area"));
//				requisiciones.setFechaCreacion(db.resultado.getString("Fecha"));
//				requisiciones.setFechaNecesaria(db.resultado.getString("Fecha Necesaria"));
//				requisiciones.setProveedor(db.resultado.getString("Proveedor"));
//				requisiciones.setTotal(db.resultado.getFloat("total"));
//				requisiciones.setStatus(db.resultado.getString("Status"));
//				lista.add(requisiciones);
//			}
//			return lista;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//	}
	public void reenviarCorreo(int idRequisicion) {
		Requisicion requisicion = new Requisicion();
		
		requisicion = buscarRequisicion(idRequisicion);
		
		switch(requisicion.getEstatus().getId()) {
			case 1: 
				Mail mail = new Mail();
				mail.RequisicionCreada(requisicion);
				Mail mail2 = new Mail();
				mail2.autorizarRequisicion(requisicion);
			break;
			case 4:
				Mail mail45 = new Mail();
				mail45.requisicionAutorizada(requisicion, requisicion.getArea().getGerente().getNombres() + " " + requisicion.getArea().getGerente().getApellidop());
			break;
			case 5: 
				Mail mail22 = new Mail();
				mail22.requisicionRechazada(requisicion,"");
			break;
			case 10:
				Mail mail3 = new Mail();
				mail3.RequisicionCreada(requisicion);
				Mail mail4 = new Mail();
				mail4.autorizarRequisicionSinPresupuestoGerente(requisicion);
			break;
			case 11:
				Mail mail30 = new Mail();
				mail30.autorizarRequisicionSinPresupuestoDirector(requisicion);
			break;
			case 7:
				Mail mail5 = new Mail();
				mail5.RequisicionCreada(requisicion);
				Mail mail6 = new Mail();
				mail6.autorizarRequisicionMayorGerente(requisicion);
			break;
		}
	}
}
