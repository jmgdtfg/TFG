package spotify;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.special.FeaturedPlaylists;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.browse.GetListOfFeaturedPlaylistsRequest;
import com.wrapper.spotify.requests.data.browse.GetRecommendationsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchPlaylistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;


public class SpotifyManager {

	private SpotifyApi spotifyApi_ = new SpotifyApi.Builder()
			.setClientId("")
			.setClientSecret("")
			.build();

	private ClientCredentialsRequest clientCredentialsRequest = spotifyApi_.clientCredentials()
			.build();

	private void clientCredentialsUpdate() throws SpotifyWebApiException, IOException {
		ClientCredentials clientCredentials = clientCredentialsRequest.execute();
		spotifyApi_.setAccessToken(clientCredentials.getAccessToken());
	}



	//Constructor por defecto de la clase SpotifyManager
	public SpotifyManager() throws SpotifyWebApiException, IOException{
		this.clientCredentialsUpdate();
	}
	//Constructor parametrizado de la clase SpotifyManager
	public SpotifyManager(String clientId, String clientSecret) throws SpotifyWebApiException, IOException{
		spotifyApi_ = new SpotifyApi.Builder()
				.setClientId(clientId)
				.setClientSecret(clientSecret)
				.build();
		this.clientCredentialsUpdate();
	}

	//Función que busca canciones según un nombre y un país pasado por parámetro
	public Track[] searchSongs(String name, String country){
		
		SearchTracksRequest searchTracksRequest = spotifyApi_.searchTracks(name)
				.market(this.getCountryCodesMap().getOrDefault(country, CountryCode.ES))
				.limit(10)
				.offset(0)
				.build();
		try{
			final Paging<Track> trackPaging = searchTracksRequest.execute();
			Track[] songs = trackPaging.getItems();
			return songs;
		}catch (IOException | SpotifyWebApiException e) {
			//System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	//Función que busca albums segun un nombre y un país pasado por parámetro
	public AlbumSimplified[] searchAlbums(String name, String country){
		SearchAlbumsRequest searchAlbumsRequest = spotifyApi_.searchAlbums(name)
				.market(this.getCountryCodesMap().getOrDefault(country, CountryCode.ES))
				.limit(10)
				.offset(0)
				.build();
		try{
			final Paging<AlbumSimplified> albumPaging = searchAlbumsRequest.execute();
			AlbumSimplified[] albums = albumPaging.getItems();
			return albums;
		}catch (IOException | SpotifyWebApiException e) {
			//System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	//Función que busca artistas según un nombre y un país pasado por parámetro.
	public Artist[] searchArtists(String name, String country){
		SearchArtistsRequest searchArtistsRequest = spotifyApi_.searchArtists(name)
				.market(this.getCountryCodesMap().getOrDefault(country, CountryCode.ES))
				.limit(10)
				.offset(0)
				.build();
		try{
			final Paging<Artist> ArtistPaging = searchArtistsRequest.execute();
			Artist[] artists = ArtistPaging.getItems();
			return artists;
		}catch (IOException | SpotifyWebApiException e) {
			//System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	//Función que busca playlists según un nombre y un país pasado por parámetro
	public PlaylistSimplified[] searchPlaylist(String name,String country){
		SearchPlaylistsRequest searchPlaylitsRequest = spotifyApi_.searchPlaylists(name)
				.market(this.getCountryCodesMap().getOrDefault(country, CountryCode.ES))
				.limit(50)
				.offset(0)
				.build();
		try{
			final Paging<PlaylistSimplified> PlaylistPaging = searchPlaylitsRequest.execute();
			PlaylistSimplified[] playlits = PlaylistPaging.getItems();
			return playlits;
		}catch (IOException | SpotifyWebApiException e) {
			//System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	/*
	 * Función que obtiene las recomendaciones según los siguientes parámetros:
	 * 1)Código de país
	 * 2)identificador del artista(semilla)
	 * 3)identificador de una canción(semilla)
	 * 4)género musical
	 */
	public TrackSimplified[] getRecommendations(String country, String id_artist, String id_track, String genre){
		CountryCode countryCode = this.getCountryCodesMap().getOrDefault(country, CountryCode.ES);
		GetRecommendationsRequest getRecommendationsRequest = spotifyApi_.getRecommendations()
				//https://beta.developer.spotify.com/documentation/web-api/reference/browse/get-recommendations/
				.limit(10)
				.market(countryCode)
				.max_popularity(50)
				.min_popularity(10)
				.seed_artists(id_artist)
				.seed_genres(genre)
				.seed_tracks(id_track)
				.target_popularity(20)
				.build();
		try {
			Recommendations recommendations = getRecommendationsRequest.execute();
			TrackSimplified[] tracks = recommendations.getTracks();
			return tracks;
		} catch (IOException | SpotifyWebApiException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}


	//Función que obtiene las principales playlist según un determinado país pasado por parámetro
	public Paging<PlaylistSimplified> getTopPlaylists(String country){
		CountryCode countryCode = this.getCountryCodesMap().getOrDefault(country, CountryCode.ES);
		GetListOfFeaturedPlaylistsRequest getListOfFeaturedPlaylistsRequest = spotifyApi_
				.getListOfFeaturedPlaylists()
				.country(countryCode)
				.limit(10)
				.offset(0)
				.build();
		try {
			final FeaturedPlaylists featuredPlaylists = getListOfFeaturedPlaylistsRequest.execute();
			Paging<PlaylistSimplified> ps = featuredPlaylists.getPlaylists();
			return ps;
		} catch (IOException | SpotifyWebApiException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}

	}
	//Función que obtiene una playlist determinada
	public Playlist getPlaylist(String country, String userId, String playlistId){
		CountryCode countryCode = this.getCountryCodesMap().getOrDefault(country, CountryCode.ES);
		GetPlaylistRequest getPlaylistRequest = spotifyApi_.getPlaylist(userId, playlistId)
				.market(countryCode)
				.build();
		try {
			final Playlist playlist = getPlaylistRequest.execute();

			return playlist;
		} catch (IOException | SpotifyWebApiException e) {
			System.out.println("Errorr: " + e.getMessage());
			return null;
		}

	}

	//Función que genera un Map con el par de valores pais -> countryCode
	private Map<String, CountryCode> getCountryCodesMap(){
		
		Map <String,CountryCode> map = new HashMap<String,CountryCode>();
		Locale[] countryCodes = Locale.getAvailableLocales();

		for (Locale countryCode : countryCodes) {
			if (!map.containsKey(countryCode.getDisplayCountry())){
				map.put(countryCode.getDisplayCountry(), CountryCode.getByCode(countryCode.getCountry()));
			}
		}
		return map;
	}


}//Fin SpotifyManager


