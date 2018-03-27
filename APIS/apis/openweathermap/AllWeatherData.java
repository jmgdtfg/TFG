package openweathermap;

import java.util.List;

import net.aksingh.owmjapis.model.param.WeatherData;

//Clase que almacena la estructura de datos del tiempo actual.
public class AllWeatherData {
	
	private String conditions_;
	private int temp_;
	private int humidity_;
	private int cloudiness_;
	private List<WeatherData> forecast_;
	
	//Getters para acceder a las variables privadas
	public String getConditions() {
		return conditions_;
	}

	public int getTemp() {
		return temp_;
	}

	public int getHumidity() {
		return humidity_;
	}

	public int getCloudiness() {
		return cloudiness_;
	}
	public List<WeatherData> getForecast(){
		return forecast_;
	}
	
	//Constructor parametrizado de la clase CurrentWeather
	public AllWeatherData(String conditions, int temp, int humidity, int cloudiness, List<WeatherData> forecast){
		conditions_ = conditions;
		temp_ = temp;
		humidity_ = humidity;
		cloudiness_ = cloudiness;
		forecast_ = forecast;
	}
}
