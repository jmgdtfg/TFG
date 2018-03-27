package processingComponents;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import net.aksingh.owmjapis.model.param.Weather;
import net.aksingh.owmjapis.model.param.WeatherData;
import openweathermap.AllWeatherData;
import openweathermap.WeatherConditionsFactory;

public class WeatherSummaryProcess implements ProcessComponent{
	//Función que obtiene el resumen del tiempo
	@Override
	public Object execute(Object data, Map<String, String> configuration) {

		AllWeatherData weatherData = (AllWeatherData) data;
		WeatherConditionsFactory wcf = new WeatherConditionsFactory();
		String message = "============== Información Meteorológica de "+configuration.get("weatherCity")+" ==============\n";


		message = message.concat("\n=> ESTADO METEOROLÓGICO ACTUAL:\n");
		message = message.concat("\nEstado: "+weatherData.getConditions()+"	|	Nubosidad: "+weatherData.getCloudiness()+"%");
		message = message.concat("   |	Temperatura: "+weatherData.getTemp()+"ºC"+"	|	Humedad: "+weatherData.getHumidity()+"%\n");


		message = message.concat("\n\n=> PREDICCIÓN:\n\n");
		List<WeatherData> wd = weatherData.getForecast();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (int i=0;i<wd.size()/4;i++){

			String date = format1.format(wd.get(i).getDateTime());
			int temp = wd.get(i).getMainData().getTemp().intValue() - 273;
			int cloudiness = wd.get(i).getCloudData().getCloudiness();
			int humidity = wd.get(i).getMainData().getHumidity().intValue();
			Weather weather = wd.get(i).getWeatherList().get(0);
			String condition = wcf.getCondition(weather.getDescription());

			message = message.concat(
					"Fecha: "+date+" | Estado: "+condition+" | Temp.: "+temp+"ºC | Nubosidad: "+
							cloudiness+"% | Humedad: "+humidity+"%\n");

		}
		message = message.concat("\n\n---------------------------------------------------------\n");

		//Devuelve un tipo String
		return message;
	}

}
