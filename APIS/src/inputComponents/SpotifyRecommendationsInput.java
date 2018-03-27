package inputComponents;

import java.io.IOException;
import java.util.Map;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import spotify.SpotifyManager;

public class SpotifyRecommendationsInput implements InputComponent{
	//Función que devuelve las recomendaciones
	@Override
	public Object execute(Map<String, String> configuration) {
		
		try {
			SpotifyManager sm = new SpotifyManager();
			//Devuelve un TrackSimplified[]
			return sm.getRecommendations(
					configuration.get("country"),
					configuration.get("idArtist"),
					configuration.get("idTrack"),
					configuration.get("genre"));
			
			
		} catch (SpotifyWebApiException e) {
			// ***
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// ***
			e.printStackTrace();
			return null;
		}
		
	}

}
