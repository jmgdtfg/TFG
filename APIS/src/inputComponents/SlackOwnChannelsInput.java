package inputComponents;

import java.io.IOException;
import java.util.Map;

import slack.SlackManager;

public class SlackOwnChannelsInput implements InputComponent{
	//Función que devuelve los canales
	@Override
	public Object execute(Map<String, String> configuration) {
		
		try {
			SlackManager sm = new SlackManager();
			//Devuelve un objeto de tipo List<String>
			return sm.getChannels();
			
		} catch (IOException e) {
			// ***
			e.printStackTrace();
			return null;
		}
		
	}

}
