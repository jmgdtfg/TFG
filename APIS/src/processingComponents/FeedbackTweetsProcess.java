package processingComponents;

import java.util.List;
import java.util.Map;

import twitter4j.Status;

public class FeedbackTweetsProcess implements ProcessComponent{
	//Función que devuelve información de menciones, retweets y me gusta
	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Object data, Map<String, String> configuration) {

		List<Status> tweets = (List<Status>) data;

		int mentions = 0;
		int favorites = 0;
		int retweets = 0;

		//Se realiza un recuento
		for (Status s : tweets){
			favorites = favorites + s.getFavoriteCount();
			retweets = retweets + s.getRetweetCount();
			mentions = mentions + s.getUserMentionEntities().length;
		}

		//Elaboración del mensaje
		String message = "- Feedback de los tweets -\n\n";
		message = message.concat("Retweets: "+retweets);
		message = message.concat("\nMe gusta: "+favorites);
		message = message.concat("\nMenciones: "+mentions);
		message = message.concat("\n------------------------------------------------\n");	
		
		//Se devuelve la información en un String
		return message;
	}

}
