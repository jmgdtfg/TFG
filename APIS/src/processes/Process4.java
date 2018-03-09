/*Proceso que proporciona informaci�n general sobre una ciudad o pa�s
 * y lo publica en un canal de slack
- Resumen del tiempo
- Top10 de las canciones m�s escuchadas en el pa�s
- Top10 de los tweets con la palabra C�rdoba m�s importantes
- Publicar resultado en un canal de Slack
 */

package processes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.model.param.Weather;
import net.aksingh.owmjapis.model.param.WeatherData;
import openweathermap.OpenWeatherMapManager;
import openweathermap.WeatherConditionsFactory;
import slack.SlackManager;
import spotify.SpotifyManager;
import twitter.TwitterManager;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

public class Process4 {

	private static String city_;
	private static String country_;
	private static String groupName_;
	
	//Constructor del proceso. Necesitar� ciudad,pa�s,nombre del grupo de slack
	public Process4 (String city, String country, String groupName) throws SpotifyWebApiException, IOException, APIException, TwitterException{
		city_=city;
		country_=country;
		groupName_=groupName;
		main(null);
	}
	private void main(String[] args) throws SpotifyWebApiException, IOException, APIException, TwitterException {

		//Se crean los objetos necesarioss
		SpotifyManager sm = new SpotifyManager();
		TwitterManager tm = new TwitterManager();
		OpenWeatherMapManager om = new OpenWeatherMapManager();
		WeatherConditionsFactory wcf = new WeatherConditionsFactory();
		SlackManager slm = new SlackManager();

		//- Resumen del tiempo
		String messageWeather = "============== Informaci�n Meteorol�gica de "+city_+" ==============\n";


		messageWeather = messageWeather.concat("\n=> ESTADO METEOROL�GICO ACTUAL:\n");
		messageWeather = messageWeather.concat("\nEstado: "+om.getCondition(city_)+"	|	Nubosidad: "+om.getCloudiness(city_)+"%");
		messageWeather = messageWeather.concat("   |	Temperatura: "+om.getCurrentTemp(city_)+"�C"+"	|	Humedad: "+om.getHumidity(city_)+"%\n");


		messageWeather = messageWeather.concat("\n\n=> PREDICCI�N:\n\n");
		List<WeatherData> wd = om.HourlyForecast(city_);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (int i=0;i<wd.size()/4;i++){

			String date = format1.format(wd.get(i).getDateTime());
			int temp = wd.get(i).getMainData().getTemp().intValue() - 273;
			int cloudiness = wd.get(i).getCloudData().getCloudiness();
			int humidity = wd.get(i).getMainData().getHumidity().intValue();
			Weather weather = wd.get(i).getWeatherList().get(0);
			String condition = wcf.getCondition(weather.getDescription());

			messageWeather = messageWeather.concat(
					"Fecha: "+date+" | Estado: "+condition+" | Temp.: "+temp+"�C | Nubosidad: "+
							cloudiness+"% | Humedad: "+humidity+"%\n");

		}
		messageWeather = messageWeather.concat("\n\n============================================================\n");


		//- Top10 de las canciones m�s escuchadas en el pa�s
		String messageTopTenSongs = "============== Top 10 canciones en "+country_+" ==============\n";
		//Primero buscamos el id del playlist del top 50 de un pa�s en concreto
		String playlistId = "";
		String userId = "";
		CountryCode countryCode = CountryCode.findByName(country_).get(0);
		PlaylistSimplified[] ps = sm.searchPlaylist("top 50 "+country_, countryCode);

		for (PlaylistSimplified p : ps){
			String query = "top 50 "+country_.toLowerCase();
			if (p.getName().toLowerCase().contains(query)){
				playlistId = p.getId();
				userId = p.getOwner().getId();
				break;
			}
		}
		//Una vez tenemos el id de la playlist obtenemos las canciones

		Playlist p = sm.getPlaylist(countryCode, userId, playlistId);
		Paging<PlaylistTrack> t = p.getTracks();
		PlaylistTrack[] tracks = t.getItems();
		List<String> listTracks = new ArrayList<String>();
		String artists = "Artista(s): ";

		for (PlaylistTrack track : tracks){

			ArtistSimplified[] at = track.getTrack().getArtists();
			for (ArtistSimplified a : at){
				artists = artists.concat(a.getName()+" ");
			}
			listTracks.add(artists+" | T�tulo: "+track.getTrack().getName());
			artists = "Artista(s): ";
		}
		for (int i = 0; i < 10 ; i++){
			messageTopTenSongs = messageTopTenSongs.concat("\n"+listTracks.get(i));
		}
		messageTopTenSongs = messageTopTenSongs.concat("\n\n============================================================\n");


		//- Top10 de los tweets con la palabra C�rdoba m�s importantes
		QueryResult result = tm.searchByWord(city_, 100, 200);
		List<Status> tweets = result.getTweets();
		List<Status> topPopularTweets = new ArrayList<Status>();
		String messageTweets = "============== Top 10 tweets con la palabra "+city_+" ==============\n";

		//Se ordenan en funci�n de los retweets
		tweets.sort(Comparator.comparing(Status::getRetweetCount).reversed());
		//Los 10 tweets con m�s retweets pasan a la lista del top tweets m�s populares
		for (int i = 0; i < 10 ; i++)
			topPopularTweets.add(tweets.get(i));
		//Por �ltimo se ordenan en funci�n de los "me gusta" que tienen
		topPopularTweets.sort(Comparator.comparing(Status::getFavoriteCount).reversed());

		for (Status s : topPopularTweets){
			messageTweets = messageTweets.concat(
					"\n- "+s.getText()+"\n| Retweets: "+s.getRetweetCount()+" | Me gusta: "+s.getFavoriteCount()+"\n");
		}
		messageTweets = messageTweets.concat("\n============================================================");


		//- Publicar resultado en un canal de Slack
		slm.sendMessageWithBot(messageWeather+messageTopTenSongs+messageTweets, groupName_);

	}

}
