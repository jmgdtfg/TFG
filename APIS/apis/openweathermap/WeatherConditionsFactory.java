package openweathermap;

import java.util.HashMap;
import java.util.Map;

public class WeatherConditionsFactory {
	private Map<String, String> conditions_ = new HashMap<String, String>();
	
	public WeatherConditionsFactory(){
		conditions_.put("thunderstorm with light rain", "tormenta con lluvia ligera");
		conditions_.put("thunderstorm with rain", "tormenta con lluvia");
		conditions_.put("thunderstorm with heavy rain", "tormenta con fuertes lluvias");
		conditions_.put("light thunderstorm", "tormenta ligera");
		conditions_.put("thunderstorm", "tormenta");
		conditions_.put("heavy thunderstorm", "fuerte tormenta");
		conditions_.put("ragged thunderstorm", "tormenta muy fuerte");
		conditions_.put("thunderstorm with light drizzle", "tormenta con ligera llovizna");
		conditions_.put("thunderstorm with drizzle", "tormenta con llovizna");
		conditions_.put("thunderstorm with heavy drizzle", "tormenta con fuerte llovizna");
		conditions_.put("light intensity drizzle", "Intensidad de llovizna");
		conditions_.put("drizzle", "llovizna");
		conditions_.put("heavy intensity drizzle", "llovizna de alta intensidad");
		conditions_.put("light intensity drizzle rain", "llovizna muy intensa");
		conditions_.put("drizzle rain", "llovizna/lluvia");
		conditions_.put("heavy intensity drizzle rain", "lluvia intensa llovizna");
		conditions_.put("shower rain and drizzle", "chaparrón de lluvia ligera o llovizna");
		conditions_.put("heavy shower rain and drizzle", "chaparrón de lluvia fuerte y llovizna");
		conditions_.put("shower drizzle", "chaparrón de llovizna");
		conditions_.put("light rain", "lluvia ligera");
		conditions_.put("moderate rain", "lluvia moderada");
		conditions_.put("heavy intensity rain", "lluvia de intensidad pesada");
		conditions_.put("very heavy rain", "lluvias muy intensas");
		conditions_.put("extreme rain", "lluvia extrema");
		conditions_.put("freezing rain", "lluvia helada");
		conditions_.put("light intensity shower rain", "chaparrón de lluvia de intensidad ligera");
		conditions_.put("shower rain", "aguacero");
		conditions_.put("heavy intensity shower rain", "lluvia de lluvia de intensidad pesada");
		conditions_.put("ragged shower rain", "chaparrón muy intenso de lluvia");
		conditions_.put("light snow", "nieve ligera");
		conditions_.put("snow", "nieve");
		conditions_.put("heavy snow", "fuertes nevadas");
		conditions_.put("sleet", "aguanieve");
		conditions_.put("shower sleet", "aguanieve de ducha");
		conditions_.put("light rain and snow", "lluvia ligera y nieve");
		conditions_.put("rain and snow", "lluvia y nieve");
		conditions_.put("light shower snow", "nieve ligera");
		conditions_.put("shower snow", "ducha de nieve");
		conditions_.put("heavy shower snow", "nevada muy intensa");
		conditions_.put("Atmosphere", "Atmósfera");
		conditions_.put("mist", "niebla");
		conditions_.put("smoke", "nube de contaminación");
		conditions_.put("haze", "calina");
		conditions_.put("sand, dust whirls", "arena, remolinos de polvo");
		conditions_.put("fog", "niebla");
		conditions_.put("sand", "arena");
		conditions_.put("dust", "polvo");
		conditions_.put("volcanic ash", "ceniza volcánica");
		conditions_.put("squalls", "chubascos");
		conditions_.put("tornado", "tornado");
		conditions_.put("clear sky", "cielo despejado");
		conditions_.put("few clouds", "pocas nubes");
		conditions_.put("scattered clouds", "nubes dispersas");
		conditions_.put("broken clouds", "nubes rotas");
		conditions_.put("overcast clouds", "nubes de tormenta");
		conditions_.put("tropical storm", "tormenta tropical");
		conditions_.put("hurricane", "huracán");
		conditions_.put("cold", "frío");
		conditions_.put("hot", "caliente");
		conditions_.put("windy", "Mucho viento");
		conditions_.put("hail", "granizo");
		conditions_.put("calm", "Calmado");
		conditions_.put("light breeze", "brisa ligera");
		conditions_.put("gentle breeze", "suave brisa");
		conditions_.put("moderate breeze", "brisa moderada");
		conditions_.put("fresh breeze", "brisa fresca");
		conditions_.put("strong breeze", "fuerte brisa");
		conditions_.put("high wind, near gale", "viento fuerte, cerca de vendaval");
		conditions_.put("gale", "vendaval");
		conditions_.put("severe gale", "vendaval severo");
		conditions_.put("storm", "tormenta");
		conditions_.put("violent storm", "tormenta violenta");
		conditions_.put("hurricane", "huracán");
	}
	
	
	public String getCondition(String key){
		return conditions_.get(key);
	}
}
