package processingComponents;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import twitter4j.Status;
//!!!LA SALIDA DE ESTA FUNCIÓN ES EL PARÁMETRO DE ENTRADA DE OTRO PROCESS COMPONENT
public class TimeIntervalTweetsProcess implements ProcessComponent{

	private int intervalEnd_;
	private int intervalStart_;

	//Constructor parametrizado obligatorio de la clase
	public TimeIntervalTweetsProcess(int intervalEnd, int intervalStart){
		intervalEnd_=intervalEnd;
		intervalStart_=intervalStart;
	}

	//Función que filtra un intervalo de tweets
	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Object data, Map<String, String> configuration) {


		List<Status> tweets = (List<Status>) data;		//Lista con todos los tweets recibidos por parámetro
		List<Status> filterTweets = new ArrayList<>();	//Lista donde se almacenaran los tweets filtrados

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
		
		for (Status s:tweets){
			if (s.getCreatedAt().before(dateBefore.getTime()) && s.getCreatedAt().after(dateAfter.getTime())){
				filterTweets.add(s);	//Si el tweet está en el intervalo se añade a la lista
			}
		}
		//Devuelve un objeto de tipo List<Status>
		return filterTweets;
	}

}
