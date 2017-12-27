package twitter;

import java.util.List;
//librerias necesarias para streamTweets
//import twitter4j.FilterQuery;
//import twitter4j.TwitterStream;
//import twitter4j.TwitterStreamFactory;
//import twitter4j.StatusDeletionNotice;
//import twitter4j.StatusListener;
//import twitter4j.StallWarning;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
//import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import twitter4j.conf.ConfigurationBuilder;

/*


 * 	- Descargar correos bandeja de entrda

 *   - Descargar correo por un contenido en concreto
 *   - */
public class TwitterManager {
	//Información disponible en https://apps.twitter.com/
	private  String ConsumerKey_ = "M0kEd4bb5Gbg94Khtj7YYPQNU";
	private  String ConsumerSecret_ = "RXqdvmp0Noy0lc18WSD3SGmTkJDgvIPGEYXUxj81wxdV7ghqY3";
	private  String AccessToken_ = "915865196706635776-coJoFOfuzfUTet8H3Bl48joI2eAbE3r";
	private  String AccessTokenSecret_ = "RfWnEht5ZKoiF149iRDb0zedcXQ8sNshcbABYZ2AA6NlS";
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
	//Esta función muestra los tweets del timeline
	public List<Status> showTimeline() throws TwitterException{

		List<Status> statuses = twitter_.getHomeTimeline();
		return statuses;

	}
	//Esta función busca tweets en función de un hashtag
	public QueryResult searchByHashtag(String hashtag) throws TwitterException{

		Query query = new Query('#'+hashtag);
		query.count(10); //Limite de 10 resultados de la busqueda 
		QueryResult result = twitter_.search(query);
		return result;

	}
	/*
	 * Esta función filtra tweets según una determinada palabra. Estos tweets no tienen por qué
	 * estar relacionados con el timeline del usuario.
	 */
	public QueryResult searchByWord(String palabra) throws TwitterException{

		Query query = new Query(palabra);
		query.count(10); //Limite de 10 resultados de la busqueda 
		QueryResult result = twitter_.search(query);
		return result;
	}
	
	//Función para obtener los trendings topics en uun determinado momento.
	public Trends getTrendingTopics() throws TwitterException{
		
		Trends trends = twitter_.getPlaceTrends(1); //Con el valor 1 devuelve los TT a nivel mundial.
		//trends.getLocation();
		return trends;

	}

	//Funcion para escribir un tweet.s
	public void writeTweet(String texto) throws TwitterException{

		twitter_.updateStatus(texto);

	}

};
