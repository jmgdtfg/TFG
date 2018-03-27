package inputComponents;

import java.io.IOException;
import java.util.Map;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import spotify.SpotifyManager;

public class SpotifyPlaylistInput implements InputComponent{
	//Función que devuelve toda la información de una playlist
	@Override
	public Object execute(Map<String, String> configuration) {
		
		try {
			SpotifyManager sm = new SpotifyManager();
			//Devuelve un objeto Playlist
			return sm.getPlaylist(
					configuration.get("country"), 
					configuration.get("IdUserSpotify"), 
					configuration.get("IdPlaylist"));
			
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
