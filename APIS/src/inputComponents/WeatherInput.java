package inputComponents;

import java.util.List;
import java.util.Map;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.model.param.WeatherData;
import openweathermap.AllWeatherData;
import openweathermap.OpenWeatherMapManager;

public class WeatherInput implements InputComponent{
	//Función que devuelve el tiempo actual
	@Override
	public Object execute(Map<String, String> configuration) {

		OpenWeatherMapManager weather = new OpenWeatherMapManager();
		
		try {
			
			String condition = weather.getCondition(configuration.get("weatherCity"));		
			int temp = weather.getCurrentTemp(configuration.get("weatherCity"));	
			int humidity = weather.getHumidity(configuration.get("weatherCity"));		
			int cloudiness = weather.getCloudiness(configuration.get("weatherCity"));
			List<WeatherData> forecast = weather.HourlyForecast(configuration.get("weatherCity"));
		
			//Se crea una nueva clase CurrentWeather que almacena los datos del tiempo actual
			AllWeatherData cw = new AllWeatherData(condition,temp,humidity,cloudiness,forecast);
			//Devuelve el tipo AllWeatherData
			return cw;

		} catch (APIException e) {
			// ***
			e.printStackTrace();
			return null;
		}
		
	}

}
