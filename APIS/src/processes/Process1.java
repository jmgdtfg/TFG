/* 
 * Este proceso se encarga de obtener un numero relevante de tweets de un determinado
 * hashtag. Después selecciona el top 5 de tweets más retuiteados y el top 5 de tweets
 * con más veces marcados como favoritos. Por último envía los resultados a una 
 * determinada cuenta de email
 * - Selecciona los del último mes
 */

package processes;

import java.util.Comparator;
import java.util.List;

import mail.MailManager;
import twitter.TwitterManager;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

public class Process1 {

	private static String emailList_;	//Puede ser un único e-mail o una lista: <a@gmail.com, b@gmail.com...>
	private static String hashtag_;
	private static List<Status> topRetweets_;
	private static List<Status> topFavorites_;

	//El constructor de la clase necesita email y hashtag
	public Process1(String emailList, String hashtag){
		emailList_=emailList;
		hashtag_=hashtag;
	}

	public static void main(String[] args) throws TwitterException {

		//Se crean los objetos necesarios
		TwitterManager tm = new TwitterManager();
		MailManager mm = new MailManager();
		
		//Se obtienen los tweets más destacados del hashtag desde el último mes( hasta 100 )
		QueryResult result = tm.searchByHashtag(hashtag_);
		List<Status> tweets = result.getTweets();

		//Si hay menos de 5 tweets para este hashtag se finalizará el proceso.
		if (tweets.size()<5)
			return;
		//Se ordenan los tweets de mayor a menor Retweets
		tweets.sort(Comparator.comparing(Status::getRetweetCount).reversed());
		topRetweets_ = tweets;
		//Se ordenan los tweets de más a menos Favoritos
		tweets.sort(Comparator.comparing(Status::getFavoriteCount).reversed());
		topFavorites_ = tweets;
		
		//Elaboración del mensaje
		String message = "TOP 5: Tweets con el hashtag #"+hashtag_+" mas retwiteados\n\n";
		
		for (int i = 0; i < 5 ; i++){
			int number = i + 1;
			message = message.concat(number+") "+topRetweets_.get(i).getText()+"\n");
		}
		message = message.concat("------------------------------------------------\n");
		message = message.concat("TOP 5: Tweets con el hashtag #"+hashtag_+" más veces marcados como favoritos\n\n");
		
		for (int i = 0; i < 5 ; i++){
			int number = i + 1;
			message = message.concat(number+") "+topFavorites_.get(i).getText()+"\n");
		}
		
		//Se envía (destinatarios, asunto , mensaje )
		mm.sendEmail(emailList_, "Top #" + hashtag_ + " tweets!", message);
		

	}

}
