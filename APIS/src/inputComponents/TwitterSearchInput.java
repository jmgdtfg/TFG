package inputComponents;

import java.util.List;
import java.util.Map;

import twitter.TwitterManager;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

public class TwitterSearchInput implements InputComponent{
	
	private int days_;		//Días hasta la fecha actual válidos para la búsqueda
	private int results_;	//Limitación de resultados de búsqueda
	
	//Constructor parametrizado que recibe los días y los resultados 
	public TwitterSearchInput(int days, int results){
		days_ = days;
		results_ = results;
	}
	
	//Devuelve una lista de tweets si la configuración pasada por parámetro es correcta
	@Override
	public Object execute(Map<String, String> configuration) {
		
		TwitterManager tm = new TwitterManager();
		//Si el tipo de la configuración es hashtag, buscara en función de un hashtag
		if (configuration.get("twitterSearchType").equals("hashtag")){
			String hashtag = configuration.get("hashtag");
			try {
				QueryResult result = tm.searchByHashtag(hashtag, days_, results_);
				List<Status> tweets = result.getTweets();
				//Devuelve un List<Status>
				return tweets;
			} catch (TwitterException e) {
				// ***
				e.printStackTrace();
				return null;
			}
		}
		//Si el tipo de la configuración es word, buscara en función de una palabra
		else if (configuration.get("twitterSearchType").equals("word")){
			String word = configuration.get("word");
			try {
				QueryResult result = tm.searchByWord(word, days_, results_);
				List<Status> tweets = result.getTweets();
				//Devuelve un List<Status>
				return tweets;
				
			} catch (TwitterException e) {
				// ***
				e.printStackTrace();
				return null;
			}
		}
		else{
			return "twitterSearchType no es valido";
		}
	}
}
