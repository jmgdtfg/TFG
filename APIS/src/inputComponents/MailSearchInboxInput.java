package inputComponents;

import java.util.Map;

import mail.MailManager;

public class MailSearchInboxInput implements InputComponent{

	//Función que devuelve los mensajes que coinciden con la búsqueda
	@Override
	public Object execute(Map<String, String> configuration) {
		
		MailManager mm = new MailManager();
		
		if (configuration.get("mailSearchType").equals("content")){
			//Devuelve el objeto Message[]
			return mm.filterByContent(configuration.get("word"));
		}
		else if (configuration.get("mailSearchType").equals("sender")){
			//Devuelve el objeto Message[]
			return mm.filterBySender(configuration.get("word"));
		}
		else if (configuration.get("mailSearchType").equals("subject")){
			//Devuelve el objeto Message[]
			return mm.filterBySubject(configuration.get("word"));
		}
		
		else{
			return "mailSearchType no es válido";
		}

	}

}
