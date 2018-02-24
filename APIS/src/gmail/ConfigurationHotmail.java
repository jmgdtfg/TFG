package mail;

import java.util.Properties;
/*La clase ConfigurationHotmail coge los valores de configuracion comunes de
 * la clase Configuration y añade los valores de configuración propios
 * de una conexión a hotmail.
 */
public class ConfigurationHotmail extends Configuration{
	private Properties props_ = new Properties();
	private String servidor_ = "imap-mail.outlook.com";
	
	ConfigurationHotmail(){
		//Añadimos todas las propiedades básicas de configuración
		props_ = getProperties();
		//Se añaden las propiedades específicas de gmail
		this.props_.put("mail.smtp.host", "smtp.live.com");
		this.props_.put("mail.smtp.port", "25");
	}
	
	public Properties getPropertiesHotmail(){
		return props_;
	}
	public String getServer(){
		return servidor_;
	}

}
