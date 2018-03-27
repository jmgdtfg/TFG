package inputComponents;

import java.util.Map;

import twitter.TwitterManager;
import twitter4j.TwitterException;

public class TwitterOwnTimelineInput implements InputComponent{

	@Override
	public Object execute(Map<String, String> configuration) {
		
		TwitterManager tm = new TwitterManager();
		
		try {
			//Devolverá un List<Status>
			return tm.getOwnTimeline();
		} catch (TwitterException e) {
			// ***
			e.printStackTrace();
			return null;
		}
		
	}

}
