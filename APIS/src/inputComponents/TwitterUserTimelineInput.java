package inputComponents;

import java.util.Map;

import twitter.TwitterManager;
import twitter4j.TwitterException;

public class TwitterUserTimelineInput implements InputComponent{

	@Override
	public Object execute(Map<String, String> configuration) {

		TwitterManager tm = new TwitterManager();
		try {
			//Devuelve un List<Status>
			return tm.getUserTimeline(configuration.get("user"));
		} catch (TwitterException e) {
			// ***
			e.printStackTrace();
			return null;
		}
		
	}

}
