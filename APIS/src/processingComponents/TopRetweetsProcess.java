package processingComponents;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import twitter4j.Status;
//Función que devuevlve el top de retweets
public class TopRetweetsProcess implements ProcessComponent{

	private int numTop_;						//Número de resultados que se obtendrán para el top
	private static List<Status> topRetweets_; 	//Lista que contiene tweets
	
	//Constructor parametrizado obligatorio de la clase
	public TopRetweetsProcess(int numTop){
		numTop_ = numTop;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Object data, Map<String, String> configuration) {
		
		List<Status> tweets = (List<Status>) data;
		
		//Si hay menos de numTop_ tweets se finalizará el proceso.
		if (tweets.size()<numTop_){
			String message = "No se encontraron suficientes datos";
			return message;
		}
		//Se ordenan los tweets de mayor a menor Retweets
		tweets.sort(Comparator.comparing(Status::getRetweetCount).reversed());
		topRetweets_ = tweets;
		//Elaboración del mensaje
		String message = "TOP "+numTop_+": Tweets mas retwiteados\n\n";
		
		for (int i = 0; i < numTop_ ; i++){
			int number = i + 1;
			message = message.concat(number+") "+topRetweets_.get(i).getUser().getName()+ ": " +topRetweets_.get(i).getText()+"\n");
		}
		message = message.concat("------------------------------------------------\n");	
		
		return message;
	}

}
