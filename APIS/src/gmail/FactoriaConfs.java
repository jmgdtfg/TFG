package mail;

import java.util.Properties;
/*La clase FactoriaConfs sirve elegir el servidor del que se recibirá la
 * configuración en función de un parámetro de entrada con el nombre 
 * del tipo de servidor.
 * */
public class FactoriaConfs {
	//Función que devuelve la configuración 
	public Properties getProperties(String servidor){

		if (servidor.equals("gmail")) {

			ConfigurationGmail cg = new ConfigurationGmail();
			return cg.getPropertiesGmail();
			
		}
		else if (servidor.equals("hotmail")){
			
			ConfigurationHotmail ch = new ConfigurationHotmail();
			return ch.getPropertiesHotmail();
			
		}
		/*else if(servidor.equals("..."){
		 * ...
		 * ...
		 * }
		 * */
		else{
			return null;
		}

	}
	//Función que devuelve el servidor
	public String getServer(String servidor){
		
		if(servidor.equals("gmail")){
			ConfigurationGmail cg = new ConfigurationGmail();
			return cg.getServer();
			
		}
		else if (servidor.equals("hotmail")){
			
			ConfigurationHotmail ch = new ConfigurationHotmail();
			return ch.getServer();
			
		}
		/*else if(servidor.equals("..."){
		 * ...
		 * ...
		 * }
		 * */
		else{
			return null;
		}
		
	}

}
