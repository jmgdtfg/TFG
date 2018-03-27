package mail;


import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.BodyTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Store;

public class MailManager {


	private String user_="";
	private String password_="";
	private Properties props_ = new Properties();
	private Session session_;
	private Message[] bandejaEntrada_;
	private Folder folder_;



	//Constructor por defecto de la clase GmailManager
	public MailManager(){

		build("gmail");//Por defecto conectará con el servidor de gmail

	};

	//Constructor parametrizado de la clase GmailManager
	/*Permite conectar con distintos servidores:
	 * 		servidor de gmail
	 * 		servidor de hotmail
	 */		
	public MailManager(String user,String password, String server){
		this.user_=user;
		this.password_=password;
		build(server);

	}

	//Función que carga los datos de las variables privadas de la clase GmailManager
	private void build(String servidor){
		
		FactoriaConfs fc = new FactoriaConfs();
		this.props_=fc.getProperties(servidor);

		this.session_ = Session.getInstance(props_,
				new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(user_,password_);
			}
		});


		try{
			// Creamos store y folder

			Store store = session_.getStore("imaps");
			//connect(server,user,pass)

				store.connect(fc.getServer(servidor), this.user_, this.password_);

			
			//A folder le indicamos la carpeta y que la abriremos en modo lectura
			Folder folder = store.getFolder("INBOX");
			folder_=folder;
			folder.open(Folder.READ_ONLY);

			//Se obtienen los mensajes
			this.bandejaEntrada_ = folder.getMessages();

			//Folder debe quedarse abierto para poder acceder a la información.
			//folder.close(false);
			//store.close();


		}
		catch (Exception e){
			e.printStackTrace();

		}

	}




	/*Funciones necesarias para leer el contenido de los correos, ya que estos pueden tener
	 * distintos formatos
	 */
	public String getTextFromMessage(Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	public String getTextFromMimeMultipart(MimeMultipart mimeMultipart)  throws MessagingException, IOException{
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break;
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart){
				result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
			}
		}
		return result;
	}






	//Función para enviar un correo
	public void sendEmail(String listaDestinatarios, String asunto, String mensaje){

		try{

			//Se añaden los parámetros del mensaje
			Message message = new MimeMessage(session_);
			message.setFrom(new InternetAddress(user_));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(listaDestinatarios));
			message.setSubject(asunto);
			message.setText(mensaje);

			//Enviar mensaje
			Transport.send(message);


		}catch (Exception e){
			throw new RuntimeException(e);
		}

	}
	/*Esta función lee los mensajes de la bandeja de entrada
	 * 	Nota: Se puede configurar en gmail para ver todos los correos o solo los de la sesion actual
	 * 			*Habilitar IMAP para todos los mensajes (incluso si ya se han descargado)
	 *			*Habilitar IMAP para los mensajes que se reciban a partir de ahora
	 * */
	public Message[] getInbox(){


		Message[] mensajes = bandejaEntrada_;
		return mensajes;


	}


	//Función que filtra los asuntos según una palabra pasada por parámetro.

	public Message[] filterBySubject(String palabra){

		Folder folder = this.folder_;
		Message[] vacio = null;

		SearchTerm filtraAsunto = new SubjectTerm(palabra);
		try{
			Message[] resultados = folder.search(filtraAsunto);
			return resultados;

		}
		catch (Exception e){
			e.printStackTrace();
			return vacio;
		}

	}

	//Función que filtra el remitente según una palabra pasada por parámetro.
	public Message[] filterBySender(String palabra){

		Folder folder = this.folder_;
		Message[] vacio = null;

		SearchTerm filtra = new FromStringTerm(palabra);
		try{
			Message[] resultados = folder.search(filtra);
			return resultados;

		}
		catch (Exception e){
			e.printStackTrace();
			return vacio;
		}

	}

	//Función que filtra el según una palabra pasada por parámetro.
	public Message[] filterByContent(String palabra){

		Folder folder = this.folder_;
		Message[] vacio = null;

		SearchTerm filtra = new BodyTerm(palabra);
		try{
			Message[] resultados = folder.search(filtra);
			return resultados;

		}
		catch (Exception e){
			e.printStackTrace();
			return vacio;
		}

	}





}//Fin clase GmailManager
