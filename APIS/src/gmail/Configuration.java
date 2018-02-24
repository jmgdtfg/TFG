package mail;

import java.util.Properties;
//La clase Configuration contiene los valores de configuración comunes.
public class Configuration {
	
	private Properties props_ = new Properties();
	
	
	Configuration(){
		this.props_.put("mail.smtp.auth", "true");
		this.props_.put("mail.smtp.starttls.enable", "true");
		
		//Añadimos los valores necesarios para leer correos
		this.props_.setProperty("mail.imap.starttls.enable", "false");
		this.props_.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		this.props_.setProperty("mail.imap.socketFactory.fallback", "false");
		this.props_.setProperty("mail.imap.port", "993");
		this.props_.setProperty("mail.imap.socketFactory.port", "993");
		
	}
	
	public Properties getProperties(){
		return props_;
	}
}
