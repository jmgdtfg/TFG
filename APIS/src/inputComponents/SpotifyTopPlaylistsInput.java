package inputComponents;

import java.io.IOException;
import java.util.Map;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import spotify.SpotifyManager;

public class SpotifyTopPlaylistsInput implements InputComponent{
	//Función que devuelve las top playlist de un determinado país
	@Override
	public Object execute(Map<String, String> configuration) {
		
		try {
			
			SpotifyManager sm = new SpotifyManager();
			//Devuelve un Paging<PlaylistSimplified>
			return sm.getTopPlaylists(configuration.get("country"));
			
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
