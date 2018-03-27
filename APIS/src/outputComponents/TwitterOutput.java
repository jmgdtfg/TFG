package outputComponents;

import java.util.Map;

import twitter.TwitterManager;
import twitter4j.TwitterException;

public class TwitterOutput implements OutputComponent{
	
	private static int limit_ = 280; 	//Límite de carácteres
	
	//Twitea los datos
	@Override
	public void execute(Object data, Map<String, String> configuration) {
		TwitterManager tm = new TwitterManager();
		String message = (String) data;
		
		System.out.println(message.length());
		//Si el mensaje es demasiado grande se recorta
		if (message.length()>limit_){
			String newMessage = message.substring(0, 274);
			message = "";
			message = newMessage + "[...]";
			System.out.println(message.length());
		}
		
		try {
			//Se escribe el tweet
			tm.writeTweet(message);
		} catch (TwitterException e) {
			// ***
			e.printStackTrace();
		}
	}

}
