package outputComponents;

import java.util.Map;

import mail.MailManager;

public class EmailOutput implements OutputComponent{

	//Envía los datos por correo.
	@Override
	public void execute(Object data, Map<String, String> configuration) {
		
		MailManager mm = new MailManager();
		String message = (String) data;
		mm.sendEmail(configuration.get("emailList"), configuration.get("subject"), message);
		
	}

}
