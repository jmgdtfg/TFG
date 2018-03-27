package twitter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.Location;
import twitter4j.Paging;
//librerias necesarias para streamTweets
//import twitter4j.FilterQuery;
//import twitter4j.TwitterStream;
//import twitter4j.TwitterStreamFactory;
//import twitter4j.StatusDeletionNotice;
//import twitter4j.StatusListener;
//import twitter4j.StallWarning;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
//import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import twitter4j.conf.ConfigurationBuilder;

public class TwitterManager {
	//Información disponible en https://apps.twitter.com/
	private  String ConsumerKey_ = "";
	private  String ConsumerSecret_ = "";
	private  String AccessToken_ = "";
	private  String AccessTokenSecret_ = "";
	private  ConfigurationBuilder cb_ = new ConfigurationBuilder()
			.setDebugEnabled(true)
			.setOAuthConsumerKey(ConsumerKey_)
			.setOAuthConsumerSecret(ConsumerSecret_)
			.setOAuthAccessToken(AccessToken_)
			.setOAuthAccessTokenSecret(AccessTokenSecret_);
	private  Twitter twitter_ = new TwitterFactory(cb_.build()).getInstance();

	//Constructor por defecto de la clase TwitterManager

	public TwitterManager() {
		// TODO Auto-generated constructor stub
	}

	//Constructor parametrizado de la clase TwitterManager

	public TwitterManager(String ConsumerKey,String ConsumerSecret,String AccessToken,String AccessTokenSecret){

		this.ConsumerKey_ = ConsumerKey;
		this.ConsumerSecret_ = ConsumerSecret;
		this.AccessToken_ = AccessToken;
		this.AccessTokenSecret_ = AccessTokenSecret;		
		this.cb_ = new ConfigurationBuilder()
				.setDebugEnabled(true)
				.setOAuthConsumerKey(this.ConsumerKey_)
				.setOAuthConsumerSecret(this.ConsumerSecret_)
				.setOAuthAccessToken(this.AccessToken_)
				.setOAuthAccessTokenSecret(this.AccessTokenSecret_);
		this.twitter_ = new TwitterFactory(cb_.build()).getInstance();
	}
	//Esta función muestra los tweets del timeline de otro usuario
	public List<Status> getUserTimeline(String user) throws TwitterException{
		List<Status> statuses = new ArrayList<Status>();
		for (int i=1; i<=6; i++){
			Paging p = new Paging(i,200);
			statuses.addAll(twitter_.getUserTimeline(user,p));
		}

		return statuses;
	}

	//Esta función muestra los tweets del timeline
	public List<Status> getOwnTimeline() throws TwitterException{

		List<Status> statuses = new ArrayList<Status>();
		for (int i=1; i<=6; i++){
			Paging p = new Paging(i,200);
			statuses.addAll(twitter_.getHomeTimeline(p));
		}

		return statuses;

	}
	//Esta función busca tweets en función de un hashtag
	public QueryResult searchByHashtag(String hashtag, int days, int results) throws TwitterException{
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -days);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String format = format1.format(calendar.getTime());

		Query query = new Query('#'+hashtag);

		query.setSince(format);						
		query.resultType(Query.ResultType.popular);	//Filtro por tweets populares
		query.count(results); 						//Limite de resultados de la busqueda 
		QueryResult result = twitter_.search(query);
		return result;

	}
	/*
	 * Esta función filtra tweets según una determinada palabra. Estos tweets no tienen por qué
	 * estar relacionados con el timeline del usuario.
	 */
	public QueryResult searchByWord(String palabra, int days, int results) throws TwitterException{

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -days);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String format = format1.format(calendar.getTime());
		
		Query query = new Query(palabra);
		query.setSince(format);	
		query.resultType(Query.ResultType.popular);
		query.count(results); 
		QueryResult result = twitter_.search(query);
		return result;
	}

	//Función para obtener los trendings topics en uun determinado momento.
	public Trends getTrendingTopics(String country) throws TwitterException{

		Map <String,Integer> map = new HashMap<String,Integer>();
		map = this.getMapWithTrends();
		Trends trends;
		if (!map.containsKey(country)){
			trends = twitter_.getPlaceTrends(1); //Devuelve los trends a nivel mundial
		}
		else{
			trends = twitter_.getPlaceTrends(map.get(country));
		}
		return trends;

	}
	//Función que almacena en un map los paises y su código
	private Map<String, Integer> getMapWithTrends() throws TwitterException{
		
		ResponseList<Location> locations= twitter_.getAvailableTrends();
		Map <String,Integer> map = new HashMap<String,Integer>();
		map.put("world", 1);//Trends mundiales
		for (Location l: locations){
			if(!map.containsKey(l.getCountryName())){
				map.put(l.getCountryName(), l.getWoeid());
			}
		}
		return map;
	}

	//Funcion para escribir un tweet
	public void writeTweet(String texto) throws TwitterException{

		twitter_.updateStatus(texto);

	}

};
