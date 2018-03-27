package inputComponents;

import java.io.IOException;
import java.util.Map;

import slack.SlackManager;

public class SlackChannelMessagesInput implements InputComponent{
	//Función que devuelve los mensajes de un canal
	@Override
	public Object execute(Map<String, String> configuration) {
		
		try {
			SlackManager sm = new SlackManager();
			//Devuelve un objeto de tipo List<SlackMessagePosted>
			return sm.getChannelMessages(configuration.get("channelName"));
			
		} catch (IOException e) {
			// ***
			e.printStackTrace();
			return null;
		}
		
	}

}
