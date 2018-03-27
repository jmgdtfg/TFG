package inputComponents;

import java.io.IOException;
import java.util.Map;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import spotify.SpotifyManager;

public class SpotifySearchInput implements InputComponent{

	//Función que devolverá los resultados de la búsqueda.
	@Override
	public Object execute(Map<String, String> configuration) {
		try {
			SpotifyManager sm = new SpotifyManager();
			//configuration.get("country") debe devolver el país EN ESPAÑOL
			if (configuration.get("spotifySearchType").equals("tracks")){		
				//Devuelve un Track[]
				return sm.searchSongs(configuration.get("word"), configuration.get("country"));
			}
			else if (configuration.get("spotifySearchType").equals("albums")){
				//Devuelve un AlbumSimplified[]
				return sm.searchAlbums(configuration.get("word"), configuration.get("country"));
			}
			else if (configuration.get("spotifySearchType").equals("artists")){
				//Devuelve un Artist[]
				return sm.searchArtists(configuration.get("word"), configuration.get("country"));
			}
			else if (configuration.get("spotifySearchType").equals("playlists")){
				//Devuelve un PlaylistSimplified[]
				return sm.searchPlaylist(configuration.get("word"), configuration.get("country"));
			}
			
			else{
				return "spotifySearchType no es válido";
			}
			
			
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
