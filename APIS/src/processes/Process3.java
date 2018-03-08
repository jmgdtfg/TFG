/*Resumen semanal del propio timeline:
Primero se obtienen los tweet del timeline
Se filtran los tweets de la semana anterior.
Se calcula el total de menciones, retweets, favoritos...
Se enviar resultado por email
 */

package processes;

import java.util.Calendar;
import java.util.List;

import mail.MailManager;
import twitter.TwitterManager;
import twitter4j.Status;
import twitter4j.TwitterException;

public class Process3 {

	private static String emailList_;
	
	//Constructor de la clase. Necesitará el e-mail
	public Process3 (String emailList){
		emailList_ = emailList;
	}

	public static void main(String[] args) throws TwitterException {

		//Declaración de los objetos necesarios
		TwitterManager tm = new TwitterManager();
		MailManager mm = new MailManager();

		//En statuses se almacenarán los últimos 1200 tweets
		List<Status> statuses = tm.showTimeline();

		//Declaración de las fechas de inicio y fin de la semana anterior
		Calendar dateBefore = Calendar.getInstance();
		dateBefore.add(Calendar.DATE, -1);
		Calendar dateAfter = Calendar.getInstance();
		dateAfter.add(Calendar.DATE, -8);

		int mentions = 0;
		int favorites = 0;
		int retweets = 0;

		for (Status s : statuses){
			//De los 1200 statuses, se filtran aquellos que sean de la semana pasada
			if (s.getCreatedAt().before(dateBefore.getTime()) && s.getCreatedAt().after(dateAfter.getTime())){
				favorites = favorites + s.getFavoriteCount();
				retweets = retweets + s.getRetweetCount();
				mentions = mentions + s.getUserMentionEntities().length;
			}

		}
		//Elaboración del mensaje
		String message = "- ESTADÍSTICAS DEL TIMELINE DE LA ÚLTIMA SEMANA -\n\n";
		message = message.concat("Retweets: "+retweets);
		message = message.concat("\nMe gusta: "+favorites);
		message = message.concat("\nMenciones: "+mentions);

		//Envío del email con la información del mensaje
		mm.sendEmail(emailList_, "Resumen semanal del timeline", message);
	}

}

