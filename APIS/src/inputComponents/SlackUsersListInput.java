package inputComponents;

import java.io.IOException;
import java.util.Map;

import slack.SlackManager;

public class SlackUsersListInput implements InputComponent{
	//Función que devuelve los usuarios
	@Override
	public Object execute(Map<String, String> configuration) {
		
		try {
			SlackManager sm = new SlackManager();
			//Devuelve un objeto de tipo List<String>
			return sm.getUsers();
			
		} catch (IOException e) {
			// ***
			e.printStackTrace();
			return null;
		}
		
	}

}
