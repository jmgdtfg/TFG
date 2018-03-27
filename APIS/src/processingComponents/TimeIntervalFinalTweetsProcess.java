package processingComponents;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import twitter4j.Status;

public class TimeIntervalFinalTweetsProcess implements ProcessComponent{

	private int intervalEnd_;
	private int intervalStart_;

	//Constructor parametrizado obligatorio de la clase
	public TimeIntervalFinalTweetsProcess(int intervalEnd, int intervalStart){
		intervalEnd_=intervalEnd;
		intervalStart_=intervalStart;
	}


	//Función que devuelve un mensaje con los tweets filtrados
	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Object data, Map<String, String> configuration) {

		List<Status> tweets = (List<Status>) data;		//Lista con todos los tweets recibidos por parámetro

		//Comprobación de que los valores del intervalo sean válidos
		if (intervalEnd_<=0 || intervalStart_<=0)
			return null;
		if (intervalStart_-intervalEnd_ <=0)
			return null;

		//Declaración de las fechas de inicio y fin del intervalo
		Calendar dateBefore = Calendar.getInstance();
		dateBefore.add(Calendar.DATE, -intervalEnd_);
		Calendar dateAfter = Calendar.getInstance();
		dateAfter.add(Calendar.DATE, -intervalStart_);

		//Elaboración del mensaje
		String message = "Tweets del intervalo de tiempo["+dateAfter+" - "+dateBefore+"]\n\n";
		for (Status s:tweets){
			if (s.getCreatedAt().before(dateBefore.getTime()) && s.getCreatedAt().after(dateAfter.getTime())){

				message = message.concat(s.getUser().getName()+ ": " +s.getText()+"\n");

			}
		}
		message = message.concat("------------------------------------------------\n");	
		//Devuelve un string
		return message;
	}

}
