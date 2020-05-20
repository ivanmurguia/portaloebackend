package modelo;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import conexion.ConexionBD;
import dao.DaoUsuarios;

public class Mail {
	DecimalFormat formateador = new DecimalFormat("###,###.##"); 
	private final String host = "outlook.oemoda.com";
	private String user;
	private final String password = "Cloe.123";
	private final String auth = "true";
	private final String starttls = "true";
	private final String ehlo = "false";
	private final String port = "25";
	private String url, urlws;
	private Properties props;
	private Session session;
	private MimeMessage message;
	private Transport transport;
	private Properties properties = new Properties();
	
	public Mail() {
		try {
			properties.load(getClass().getResourceAsStream("/resources/config.properties"));
			
			props = System.getProperties();
			
			url = properties.getProperty("url");
			user = properties.getProperty("email");
			urlws = properties.getProperty("urlws");
			
			System.out.println("Mail: " + user);
			
		    props.put("mail.smtp.host", host);
		    props.put("mail.smtp.user", user);
		    props.put("mail.smtp.clave", password);
		    props.put("mail.smtp.auth", auth);
		    props.put("mail.smtp.starttls.enable", starttls);
		    props.put("mail.smtp.ehlo", ehlo);
		    props.put("mail.smtp.port", port);
			session = Session.getDefaultInstance(props);
		    session.setDebug(false);
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			transport = session.getTransport("smtp");
		
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	private void enviarCorreo(String destinatario, String html, String asunto) {
        try {
            message.addRecipients(Message.RecipientType.TO, destinatario);
            message.addRecipients(Message.RecipientType.BCC, user);
            message.setSubject(asunto);
            message.setContent(html,"text/html");
			transport.connect("outlook.oemoda.com", user, password);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void enviarCorreo(List<String> destinatarios, String html, String asunto) {
        try {
        	for(int i = 0; i < destinatarios.size(); i++) {
        		message.addRecipients(Message.RecipientType.TO, destinatarios.get(i).toString());
        	}
        	message.addRecipients(Message.RecipientType.BCC, user);
            message.setSubject(asunto);
            message.setContent(html,"text/html");
			transport.connect("outlook.oemoda.com", user, password);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void solicitudCreada(SolicitudCotizacion solicitud) {
		String encabezado = "<p>Hola, tu Solicitud de Cotizacion esta en espera de ser autorizada por "+solicitud.getArea().getGerente().getNombres() + " " + solicitud.getArea().getGerente().getApellidop() +".</p>";
		String pie = "";
		
		enviarCorreo(solicitud.getUsuario().getEmail(), solicitud(solicitud, encabezado, pie),"Solicitud de Cotizacion " + solicitud.getId());
	}
	
	public void solicitudCerrada(SolicitudCotizacion solicitud) {
		String encabezado = "<p>Hola, ya se terminaron de cotizar tus productos, entra al portal a seleccionarlos.</p>";
		String pie = "";
		
		enviarCorreo(solicitud.getUsuario().getEmail(), solicitud(solicitud, encabezado, pie),"Solicitud de Cotizacion " + solicitud.getId());
	}
	
	public void RequisicionCreada(Requisicion requisicion) {
		String encabezado = "<p>Hola, tu requisicion esta en espera de ser autorizada por "+requisicion.getArea().getGerente().getNombres() + " " + requisicion.getArea().getGerente().getApellidop() +".</p>";
		String pie = "";

		enviarCorreo(requisicion.getUsuario().getEmail(), requisicion(requisicion, encabezado, pie), "Requisicion " + requisicion.getId());
	}
	
	public void reciboCreado(Requisicion requisicion) {
		requisicion.setId(Integer.valueOf(requisicion.getRecibo()));
		String encabezado = "<p>Hola, tu recibo se creó con el folio "+requisicion.getRecibo()+".</p>";
		String pie = "";

		enviarCorreo(requisicion.getUsuario().getEmail(), requisicion(requisicion, encabezado, pie).replace("Requisicion", "Recibo"), "Recibo " + requisicion.getRecibo());
	}
	
	public void autorizarSolicitudMayorGerente(SolicitudCotizacion solicitudc) {
		String encabezado = "<p>Hola, tienes una Solicitud de Cotizacion creada por "+solicitudc.getUsuario().getNombres()+" "+ solicitudc.getUsuario().getApellidop()+" con el monto mayor al permitido por ser autorizada.</p>";
		String pie = 

		"<a href=\""+url+"rechazo.html?f="+solicitudc.getId()+"&t=s\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+solicitudc.getId()+"&e=8&t=s\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;

		enviarCorreo(solicitudc.getArea().getGerente().getEmail(), solicitud(solicitudc, encabezado, pie), "Solicitud de Cotizacion " + solicitudc.getId());
	}
	
	public void autorizarRequisicionMayorGerente(Requisicion requisicion) {
		String encabezado = "<p>Hola, tienes una requisicion creada por "+requisicion.getUsuario().getNombres()+" "+ requisicion.getUsuario().getApellidop()+" con el monto mayor al permitido por ser autorizada.</p>";
		String pie = 

		"<a href=\""+url+"rechazo.html?f="+requisicion.getId()+"&t=r\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+requisicion.getId()+"&e=8&t=r\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;

		enviarCorreo(requisicion.getArea().getGerente().getEmail(), requisicion(requisicion, encabezado, pie), "Requisicion " + requisicion.getId());
	}
	
	public void autorizarSolicitudMayorDirector(SolicitudCotizacion solicitudc) {
		String encabezado = "<p>Hola, tienes una Solicitud de Cotizacion creada por "+solicitudc.getUsuario().getNombres()+" "+ solicitudc.getUsuario().getApellidop()+" con el monto mayor al permitido por ser autorizada.</p>";
		String pie = 

		"<a href=\""+url+"rechazo.html?f="+solicitudc.getId()+"&t=s\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+solicitudc.getId()+"&e=8&t=s\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;

		enviarCorreo(solicitudc.getArea().getDepartamento().getDirector().getEmail(), solicitud(solicitudc, encabezado, pie), "Solicitud de Cotizacion " + solicitudc.getId());
	}
	
	public void autorizarRequisicionMayorDirector(Requisicion requisicion) {
		String encabezado = "<p>Hola, tienes una requisicion creada por "+requisicion.getUsuario().getNombres()+" "+ requisicion.getUsuario().getApellidop()+" con el monto mayor al permitido por ser autorizada.</p>";
		
		String pie = 

		"<a href=\""+url+"rechazo.html?f="+requisicion.getId()+"&t=r\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+requisicion.getId()+"&e=9&t=r\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;

		enviarCorreo(requisicion.getArea().getDepartamento().getDirector().getEmail(), requisicion(requisicion, encabezado, pie), "Requisicion " + requisicion.getId());
	}
	
	public void autorizarSolicitudSinPresupuestoGerente(SolicitudCotizacion solicitudc) {
		String encabezado = "<p>Hola, tienes una Solicitud de Cotizacion creada por "+solicitudc.getUsuario().getNombres()+" "+ solicitudc.getUsuario().getApellidop()+" <strong>sin presupuesto</strong> que esta en espera de ser autorizada.</p>";
		String pie = 
				
		"<a href=\""+url+"rechazo.html?f="+solicitudc.getId()+"&t=s\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+solicitudc.getId()+"&e=11&t=s\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;

		enviarCorreo(solicitudc.getArea().getGerente().getEmail(), solicitud(solicitudc, encabezado, pie), "Solicitud de Cotizacion " + solicitudc.getId());
	}
	
	public void autorizarRequisicionSinPresupuestoGerente(Requisicion requisicion) {
		String encabezado = "<p>Hola, tienes una requisicion creada por "+requisicion.getUsuario().getNombres()+" "+ requisicion.getUsuario().getApellidop()+" <strong>sin presupuesto</strong> que esta en espera de ser autorizada.</p>";
		
		String pie = 
				
		"<a href=\""+url+"rechazo.html?f="+requisicion.getId()+"&t=r\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+requisicion.getId()+"&e=11&t=r\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;

		enviarCorreo(requisicion.getArea().getGerente().getEmail(), requisicion(requisicion, encabezado, pie), "Requisicion " + requisicion.getId());
	}
	
	public void autorizarSolicitudSinPresupuestoDirector(SolicitudCotizacion solicitudc) {
		String encabezado = "<p>Hola, tienes una Solicitud de Cotizacion creada por "+solicitudc.getUsuario().getNombres()+" "+ solicitudc.getUsuario().getApellidop()+" <strong>sin presupuesto</strong> que esta en espera de ser autorizada.</p>";
		String pie = 

		"<a href=\""+url+"rechazo.html?f="+solicitudc.getId()+"&t=s\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+solicitudc.getId()+"&e=12&t=s\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;

		enviarCorreo(solicitudc.getArea().getGerente().getEmail(), solicitud(solicitudc, encabezado, pie), "Solicitud de Cotizacion " + solicitudc.getId());
	}
	
	public void autorizarRequisicionSinPresupuestoDirector(Requisicion requisicion) {
		String encabezado = "<p>Hola, tienes una requisicion creada por "+requisicion.getUsuario().getNombres()+" "+ requisicion.getUsuario().getApellidop()+" <strong>sin presupuesto</strong> que esta en espera de ser autorizada.</p>";
		
		String pie = 
				
		"<a href=\""+url+"rechazo.html?f="+requisicion.getId()+"&t=r\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+requisicion.getId()+"&e=12&t=r\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;

		enviarCorreo(requisicion.getArea().getDepartamento().getDirector().getEmail(), requisicion(requisicion, encabezado, pie), "Requisicion " + requisicion.getId());
	}
	
	public void autorizarSolicitudTI(SolicitudCotizacion solicitudc) {
		DaoUsuarios daousuarios = new DaoUsuarios();
		
		Encargado encargado = daousuarios.buscarEncargadoArea(11);
		
		String encabezado = "<p>Hola, tienes una Solicitud de Cotizacion creada por "+solicitudc.getUsuario().getNombres()+" "+ solicitudc.getUsuario().getApellidop()+" para la compra de Equipo de Computo y/o Mantenimiento de Equipo de Computo.</p>";
		
		String pie = 
				
		"<a href=\""+url+"rechazo.html?f="+solicitudc.getId()+"&t=s\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+solicitudc.getId()+"&e=4&t=s\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;

		enviarCorreo(encargado.getEmail(), solicitud(solicitudc, encabezado, pie), "Requisicion " + solicitudc.getId());
	}
	
	public void autorizarRequisicionTI(Requisicion requisicion) {
		DaoUsuarios daousuarios = new DaoUsuarios();
		
		Encargado encargado = daousuarios.buscarEncargadoArea(11);
		
		String encabezado = "<p>Hola, tienes una requisicion creada por "+requisicion.getUsuario().getNombres()+" "+ requisicion.getUsuario().getApellidop()+" para la compra de Equipo de Computo y/o Mantenimiento de Equipo de Computo.</p>";
		
		String pie = 
				
		"<a href=\""+url+"rechazo.html?f="+requisicion.getId()+"&t=r\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+requisicion.getId()+"&e=4&t=r\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;

		enviarCorreo(encargado.getEmail(), requisicion(requisicion, encabezado, pie), "Requisicion " + requisicion.getId());
	}
	
	public void autorizarRequisicionSinPresupuestoPresupuestos(Requisicion requisicion) {
		String encabezado = "<p>Hola, hay una nueva requisicion  creada por "+requisicion.getUsuario().getNombres()+" "+ requisicion.getUsuario().getApellidop()+" autorizada sin Presupuesto.</p>";
		
		String pie = 
		
		"<a href=\""+url+"rechazo.html?f="+requisicion.getId()+"&t=r\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+requisicion.getId()+"&e=13&t=r\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;

		DaoUsuarios daousuarios = new DaoUsuarios();
		
		List<Usuario> usuarios = new ArrayList<>();
		List<String> presupuestos = new ArrayList<>();
		
		usuarios = daousuarios.importarUsuariosTipo(6);
		
		for(int i = 0; i < usuarios.size(); i++) {
			presupuestos.add(usuarios.get(i).getEmail());
		}
		
		Mail mail = new Mail();
		mail.enviarCorreo(presupuestos, requisicion(requisicion, encabezado, pie), "Requisicion " + requisicion.getId());
	}
	
	public void autorizarSolicitud(SolicitudCotizacion solicitudc) {
		String encabezado = "<p>Hola, tienes una Solicitud de Cotizacion creada por "+solicitudc.getUsuario().getNombres()+" "+ solicitudc.getUsuario().getApellidop()+" que esta en espera de ser autorizada.</p>";
		String pie = 

		"<a href=\""+url+"rechazo.html?f="+solicitudc.getId()+"&t=s\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+solicitudc.getId()+"&e=4&t=s\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;

		enviarCorreo(solicitudc.getArea().getGerente().getEmail(), solicitud(solicitudc, encabezado, pie), "Solicitud de Cotizacion " + solicitudc.getId());
	}
	
	public void autorizarRequisicion(Requisicion requisicion) {
		String encabezado = "<p>Hola, tienes una requisicion creada por "+requisicion.getUsuario().getNombres()+" "+ requisicion.getUsuario().getApellidop()+" que esta en espera de ser autorizada.</p>";
		String pie = 
				
		"<a href=\""+url+"rechazo.html?f="+requisicion.getId()+"&t=r\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+url+"autorizacion.html?f="+requisicion.getId()+"&e=4&t=r\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;
				
		enviarCorreo(requisicion.getArea().getGerente().getEmail(), requisicion(requisicion, encabezado, pie), "Requisicion " + requisicion.getId());
	}
	
	public void solicitudAutorizada(SolicitudCotizacion solicitudc, String autorizador) {
		String encabezado = "<p>Hola, hay una nueva Solicitud de Cotizacion autorizada creada por "+solicitudc.getUsuario().getNombres()+" "+ solicitudc.getUsuario().getApellidop()+".</p>";

		DaoUsuarios daousuarios = new DaoUsuarios();
		
		List<Usuario> usuarios = new ArrayList<>();
		List<String> compradores = new ArrayList<>();
		
		usuarios = daousuarios.importarUsuariosTipo(5);
		
		for(int i = 0; i < usuarios.size(); i++) {
			compradores.add(usuarios.get(i).getEmail());
		}
		
		Mail mail = new Mail();
		mail.enviarCorreo(compradores, solicitud(solicitudc, encabezado, ""),"Solicitud de Cotizacion " + solicitudc.getId());
		
		Mail mail2 = new Mail();
		encabezado = "<p>Hola, tu Solicitud de Cotizacion ha sido autorizada..</p>";
		mail2.enviarCorreo(solicitudc.getUsuario().getEmail(), solicitud(solicitudc, encabezado, ""), "Solicitud de Cotizacion " + solicitudc.getId());
	}
	
	public void requisicionAutorizada(Requisicion requisicion, String autorizador) {
		String encabezado = "<p>Hola, hay una nueva requisicion autorizada creada por "+requisicion.getUsuario().getNombres()+" "+ requisicion.getUsuario().getApellidop()+".</p>";
		
		String pie = 
				
		"<a href=\""+url+"rechazo.html?f="+requisicion.getId()+"&t=r\"><img src=\""+url+"cdn/btnRechazar.png\" alt=\"Rechazar\"></a></td><td width=\"100%\">\r\n" + 
		"<a href=\""+urlws+"api/articulos/crearOrdenSAP/"+requisicion.getId()+"\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"></a></td><td width=\"100%\">\r\n" ;
		
		DaoUsuarios daousuarios = new DaoUsuarios();
		
		List<Usuario> usuarios = new ArrayList<>();
		List<String> compradores = new ArrayList<>();
		
		usuarios = daousuarios.importarUsuariosTipo(5);
		
		for(int i = 0; i < usuarios.size(); i++) {
			compradores.add(usuarios.get(i).getEmail());
		}
		
		Mail mail = new Mail();
		mail.enviarCorreo(compradores, requisicion(requisicion, encabezado, pie),"Requisicion " + requisicion.getId());
		
		Mail mail2 = new Mail();
		encabezado = "<p>Hola, tu requisicion ha sido autorizada.</p>";
		pie = "";
		mail2.enviarCorreo(requisicion.getUsuario().getEmail(), requisicion(requisicion, encabezado, pie), "Requisicion " + requisicion.getId());
	}
	
	public void solicitudRechazada(SolicitudCotizacion solicitudc, String motivo) {
		String encabezado = "<p>Hola, tu Solicitud de Cotizacion ha sido rechazada por "+motivo+".</p>";
		String pie = "";
		
		enviarCorreo(solicitudc.getUsuario().getEmail(), solicitud(solicitudc, encabezado, pie), "Solicitud " + solicitudc.getId());
	}
	
	public void requisicionRechazada(Requisicion requisicion, String motivo) {
		String encabezado = "<p>Hola, tu requisicion ha sido rechazada por que "+motivo+".</p>";
		String pie = "";
		
		enviarCorreo(requisicion.getUsuario().getEmail(), requisicion(requisicion, encabezado, pie), "Requisicion " + requisicion.getId());
	}
	
	public void requisicionCancelada(Requisicion requisicion) {
		String encabezado = "<p>Hola, tu requisicion ha sido cancelada.</p>";
		String pie = "";
		
		enviarCorreo(requisicion.getUsuario().getEmail(), requisicion(requisicion, encabezado, pie), "Requisicion " + requisicion.getId());
	}
	
	private String solicitud(SolicitudCotizacion solicitud, String encabezado, String pie) {
		String html = "";
		html =  "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-size: 100% 100%; font-family: Century Gothic; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px\">\r\n" + 
				"    <!--[if gte mso 9]>\r\n" + 
				"    <v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\r\n" + 
				"        <v:fill type=\"tile\" src=\""+url+"CloeApiDocumentacion/assets/images/header-bg.jpg\" color=\"black\"/>\r\n" + 
				"    </v:background>\r\n" + 
				"    <![endif]-->\r\n" + 				
				"    <tr>\r\n" + 
				"        <td align=\"center\" bgcolor=\"#272727\" style=\"padding: 10px 0 5px 0;\">\r\n" + 
				"            <img src=\""+url+"cdn/oebco.png\"  alt=\"Cloe\" width=\"100\" height=\"100\" style=\"display: block;\" />\r\n" + 
				"        </td>\r\n" + 
				"    </tr>\r\n" + 
				"    <tr>\r\n" + 
				"        <td background=\""+url+"CloeApiDocumentacion/assets/images/header-bg.jpg\" style=\"background-size: 100% 100%;padding: 40px 30px 40px 30px;\">\r\n" + 
				"            <table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"opacity:0.95;\">\r\n" + 
				"                <tr>\r\n" + 
				"                    <td align=\"center\">\r\n" + 
				"                        "+encabezado+"\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"            </table>\r\n" + 
				"            &nbsp;\r\n" + 
				"            <table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"opacity:0.95; padding: 20px 30px 20px 30px; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px\">\r\n" + 
				"                <tr>\r\n" + 
				"                    <td>\r\n" + 
				"                        <table  cellpadding=\"1px\" cellspacing=\"1px\" width=\"80%\" align=\"left\">\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td>\r\n" + 
				"                                    <strong>Fecha</strong>\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    "+solicitud.getFechaCreacion().substring(0, 10)+"\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td>\r\n" + 
				"                                    <strong>Razon Social</strong>\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    "+solicitud.getSociedad().getNombre()+"\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td>\r\n" + 
				"                                    <strong>Area</strong>\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    "+solicitud.getArea().getNombre()+"\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td>\r\n" + 
				"                                    <strong>Moneda</strong>\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    "+solicitud.getMoneda().getNombre()+"\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                        </table>\r\n" + 
				"                    </td>\r\n" + 
				"                    &nbsp;\r\n" + 
				"                    <td valign=\"TOP\" colspan=\"2\">\r\n" + 
				"                        <table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"30%\" align=\"right\" >\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td bgcolor = \"#272727\"  style=\" color:white; font-size: 25px; padding: 10px 10px 10px 10px;\" align=\"center\">\r\n" + 
				"                                    Solicitud Cotización\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td style=\"color:red; font-size: 25px;\" align=\"center\">\r\n" + 
				"                                    <strong>"+solicitud.getId()+"</strong>\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                        </table>\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                    <td colspan=\"3\" >\r\n" + 
				"                        <table cellpadding=\"5\" cellspacing=\"0\" width=\"100%\" align=\"center\" style=\"border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px\" >\r\n" + 
				"                            <tr  bgcolor = \"#272727\" align=\"center\" style=\"color:white; font-size: 15px;\">\r\n" + 
				"                                <td>\r\n" + 
				"                                    Producto\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    Descripcion\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    Cantidad\r\n" + 
				"                                </td>      \r\n" + 
				"                                <td>\r\n" + 
				"                                    Precio\r\n" + 
				"                                </td>       \r\n" + 
				"                            </tr>\r\n" ;
		        for(int i = 0; i < solicitud.getDetalle().size(); i++) {
		        	html = html + "<tr align=\"center\"><td>"+solicitud.getDetalle().get(i).getProducto().getProducto()+"</td><td>"+solicitud.getDetalle().get(i).getDescripcion()+"</td><td>"+solicitud.getDetalle().get(i).getCantidad()+"</td><td>"+formateador.format(solicitud.getDetalle().get(i).getPrecio())+"</td></tr>\r\n";
		        }
				html = html + 
				"                        </table>\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                    <td colspan=\"3\">\r\n" + 
				"                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td valign=\"TOP\" style=\"padding: 0px 5px 0px 0px;\">\r\n" + 
				"                                    <table border=\"1\" cellpadding=\"3\" cellspacing=\"0\" width=\"100%\">\r\n" + 
				"                                        <tr bgcolor = \"#272727\" align=\"center\" style=\"color:white; font-size: 15px;\">\r\n" + 
				"                                            <td>\r\n" + 
				"                                                Comentarios\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        <tr rowspan=\"2\">\r\n" + 
				"                                            <td >\r\n" + 
				"                                                "+solicitud.getComentarios()+"\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                    </table>\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                        </table>\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                    <td colspan=\"3\" align=\"center\">\r\n" + 
				"                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td valign=\"TOP\" align=\"right\">\r\n" + 
				"                                    <table border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" width=\"30%\" >\r\n" + 
				"                                        <tr align=\"right\">\r\n" + 
				"                                            <td width=\"100%\">\r\n" + 
				pie +
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                    </table>\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                        </table>\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"            </table>\r\n" + 
				"        </td>\r\n" + 
				"    </tr>\r\n" + 
				"    <tr>\r\n" + 
				"        <td align=\"center\" bgcolor=\"#272727\" style=\"padding: 0px 0 0px 0;\" height=\"33\">\r\n" + 
				"        </td>\r\n" + 
				"    </tr>\r\n" + 
				"</table>\r\n" + 
				"";
				
				return html;
	}
	
	private String requisicion(Requisicion requisicion, String encabezado, String pie) {
		String html;

		html = "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-size: 100% 100%; font-family: Century Gothic; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px\">\r\n" + 
				"    <!--[if gte mso 9]>\r\n" + 
				"    <v:background xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"t\">\r\n" + 
				"        <v:fill type=\"tile\" src=\""+url+"CloeApiDocumentacion/assets/images/header-bg.jpg\" color=\"black\"/>\r\n" + 
				"    </v:background>\r\n" + 
				"    <![endif]-->\r\n" + 
				"    <tr>\r\n" + 
				"        <td align=\"center\" bgcolor=\"#272727\" style=\"padding: 10px 0 5px 0;\">\r\n" + 
				"            <img src=\""+url+"cdn/oebco.png\"  alt=\"Cloe\" width=\"100\" height=\"100\" style=\"display: block;\" />\r\n" + 
				"        </td>\r\n" + 
				"    </tr>\r\n" + 
				"    <tr>\r\n" + 
				"        <td background=\""+url+"CloeApiDocumentacion/assets/images/header-bg.jpg\" style=\"background-size: 100% 100%;padding: 40px 30px 40px 30px;\">\r\n" + 
				"            <table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"opacity:0.95;\">\r\n" + 
				"                <tr>\r\n" + 
				"                    <td align=\"center\">\r\n" + 
				"                        "+encabezado+"\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"            </table>\r\n" + 
				"            &nbsp;\r\n" + 
				"            <table bgcolor=\"#FFFFFF\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"opacity:0.95; padding: 20px 30px 20px 30px; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px\">\r\n" + 
				"                <tr>\r\n" + 
				"                    <td>\r\n" + 
				"                        <table  cellpadding=\"1px\" cellspacing=\"1px\" width=\"80%\" align=\"left\">\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td>\r\n" + 
				"                                    <strong>Fecha</strong>\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    "+requisicion.getFechaCreacion().substring(0, 10)+"\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td>\r\n" + 
				"                                    <strong>Razon Social</strong>\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    "+requisicion.getSociedad().getNombre()+"\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td>\r\n" + 
				"                                    <strong>Area</strong>\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    "+requisicion.getArea().getNombre()+"\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td>\r\n" + 
				"                                    <strong>Proveedor</strong>\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    "+requisicion.getProveedor().getNombre()+"\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td>\r\n" + 
				"                                    <strong>Moneda</strong>\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    "+requisicion.getMoneda().getNombre()+"\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                        </table>\r\n" + 
				"                    </td>\r\n" + 
				"                    &nbsp;\r\n" + 
				"                    <td valign=\"TOP\" colspan=\"2\">\r\n" + 
				"                        <table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"30%\" align=\"right\" >\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td bgcolor = \"#272727\"  style=\" color:white; font-size: 25px; padding: 10px 10px 10px 10px;\" align=\"center\">\r\n" + 
				"                                    Requisicion\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td style=\"color:red; font-size: 25px;\" align=\"center\">\r\n" + 
				"                                    <strong>"+requisicion.getId()+"</strong>\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                        </table>\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                    <td colspan=\"3\" >\r\n" + 
				"                        <table cellpadding=\"5\" cellspacing=\"0\" width=\"100%\" align=\"center\" style=\"border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px\" >\r\n" + 
				"                            <tr  bgcolor = \"#272727\" align=\"center\" style=\"color:white; font-size: 15px;\">\r\n" + 
				"                                <td>\r\n" + 
				"                                    Producto\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    Descripcion\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    Iva\r\n" + 
				"                                </td>\r\n" + 
				"                                <td>\r\n" + 
				"                                    Precio\r\n" + 
				"                                </td>      \r\n" + 
				"                                <td>\r\n" + 
				"                                    Cantidad\r\n" + 
				"                                </td>       \r\n" + 
				"                                <td>\r\n" + 
				"                                    Total\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" ;
		        for(int i = 0; i < requisicion.getDetalle().size(); i++) {
		        	html = html + "<tr align=\"center\"><td>"+requisicion.getDetalle().get(i).getProducto().getProducto()+"</td><td>"+requisicion.getDetalle().get(i).getDescripcion()+"</td><td>"+requisicion.getDetalle().get(i).getIva()+"</td><td>"+formateador.format(requisicion.getDetalle().get(i).getPrecioEstimado())+"</td><td>"+requisicion.getDetalle().get(i).getCantidad()+"</td><td>"+formateador.format(requisicion.getDetalle().get(i).getMonto())+"</td></tr>\r\n";
		        }
				html = html + 
				"                        </table>\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                    <td colspan=\"3\">\r\n" + 
				"                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td valign=\"TOP\" style=\"padding: 0px 5px 0px 0px;\">\r\n" + 
				"                                    <table border=\"1\" cellpadding=\"3\" cellspacing=\"0\" width=\"100%\">\r\n" + 
				"                                        <tr bgcolor = \"#272727\" align=\"center\" style=\"color:white; font-size: 15px;\">\r\n" + 
				"                                            <td>\r\n" + 
				"                                                Comentarios\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        <tr rowspan=\"2\">\r\n" + 
				"                                            <td >\r\n" + 
				"                                                "+requisicion.getComentarios()+"\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                    </table>\r\n" + 
				"                                </td>\r\n" + 
				"                                <td valign=\"TOP\" align=\"right\" style=\"padding: 0px 0px 0px 20px;\">\r\n" + 
				"                                    <table border=\"1\"  cellpadding=\"3\" cellspacing=\"0\" width=\"100%\" >\r\n" + 
				"                                        <tr >\r\n" + 
				"                                            <td bgcolor = \"#272727\" align=\"center\" style=\"color:white; font-size: 15px;\">\r\n" + 
				"                                                Subtotal\r\n" + 
				"                                            </td>\r\n" + 
				"                                            <td>\r\n" + 
				"                                                "+formateador.format(requisicion.getSubtotal())+"\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <td bgcolor = \"#272727\" align=\"center\" style=\"color:white; font-size: 15px;\">\r\n" + 
				"                                                Iva\r\n" + 
				"                                            </td>\r\n" + 
				"                                            <td>\r\n" + 
				"                                                "+formateador.format(requisicion.getIva())+"\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <td bgcolor = \"#272727\" align=\"center\" style=\"color:white; font-size: 15px;\">\r\n" + 
				"                                                Total\r\n" + 
				"                                            </td>\r\n" + 
				"                                            <td>\r\n" + 
				"                                                "+formateador.format(requisicion.getTotal())+"\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                    </table>\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                        </table>\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                    <td colspan=\"3\" align=\"center\">\r\n" + 
				"                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n" + 
				"                            <tr>\r\n" + 
				"                                <td valign=\"TOP\" align=\"right\">\r\n" + 
				"                                    <table border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" width=\"30%\" >\r\n" + 
				"                                        <tr align=\"right\">\r\n" + 
				"                                            <td width=\"100%\">\r\n" + 
				pie +
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                    </table>\r\n" + 
				"                                </td>\r\n" + 
				"                            </tr>\r\n" + 
				"                        </table>\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"            </table>\r\n" + 
				"        </td>\r\n" + 
				"    </tr>\r\n" + 
				"    <tr>\r\n" + 
				"        <td align=\"center\" bgcolor=\"#272727\" style=\"padding: 0px 0 0px 0;\" height=\"33\">\r\n" + 
				"        </td>\r\n" + 
				"    </tr>\r\n" + 
				"</table>\r\n" + 
				"";
				
                return html;
	}
	
	public void olvidoContrasena(String user) {
		 DaoUsuarios daousuario = new DaoUsuarios();
		 Usuario usuario = new Usuario();		 
		 usuario = daousuario.buscarUsuarioU(user);
		 
		 Random random = new Random();
		 String token = String.valueOf(random.nextLong()).replace("-", "0");
		 for(int i = token.length(); i < 20; i++ ) {
			 token = token + "0";
		 }
		 
		 ConexionBD db = new ConexionBD();

		 db.ejecutar("insert into olvidocontrasena(token, usado, idusuario) values ('"+token+"','0',"+usuario.getId()+")");
		 
		 String html =  "<p>Su solicitud de reseteo de password ha sido procesado,</br> el siguiente boton es para generar un password nueva.</p>\r\n" + 
				 		"<p>Si usted no solicito el cambio de contraseña de aviso al personal de sistemas.</p>\r\n" + 
				 		"<a title=\"Autorizar\" href=\""+url+"olvidoContrasena.html?t="+token+"\"><img src=\""+url+"cdn/btnAutorizar.png\" alt=\"Autorizar\"/></a>";
		 
        try {
            message.addRecipients(Message.RecipientType.TO, usuario.getEmail());
            message.setSubject("Recuperacion de contraseña");
            message.setContent(html,"text/html");
			transport.connect("outlook.oemoda.com", user, password);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
