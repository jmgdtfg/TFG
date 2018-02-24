package mail;

import java.util.Properties;
/*La clase ConfigurationGmail coge los valores de configuracion comunes de
 * la clase Configuration y añade los valores de configuración propios
 * de una conexión a gmail.
 */
public class ConfigurationGmail extends Configuration{
	
	private Properties props_ = new Properties();
	private String servidor_ = "imap.gmail.com";
	
	ConfigurationGmail(){
		//Añadimos todas las propiedades básicas de configuración
		props_ = getProperties();
		//Se añaden las propiedades específicas de gmail
		props_.put("mail.smtp.host", "smtp.gmail.com");
		props_.put("mail.smtp.port", "587");
	}
	
	public Properties getPropertiesGmail(){
		return props_;
	}
	public String getServer(){
		return servidor_;
	}

}
