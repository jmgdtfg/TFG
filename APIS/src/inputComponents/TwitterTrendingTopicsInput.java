package inputComponents;

import java.util.Map;

import twitter.TwitterManager;
import twitter4j.TwitterException;

public class TwitterTrendingTopicsInput implements InputComponent{
	
	@Override
	public Object execute(Map<String, String> configuration) {
		TwitterManager tm = new TwitterManager();
		try {
			//Devolverá un objeto de tipo Trends		
			return tm.getTrendingTopics(configuration.get("country"));
		} catch (TwitterException e) {
			// ***
			e.printStackTrace();
			return null;
		}
	}
}
