package spotify;

import java.io.IOException;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.special.FeaturedPlaylists;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.browse.GetListOfFeaturedPlaylistsRequest;
import com.wrapper.spotify.requests.data.browse.GetRecommendationsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchPlaylistsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

public class SpotifyManager {

	private SpotifyApi spotifyApi_ = new SpotifyApi.Builder()
			.setClientId(CLIENT_ID)
			.setClientSecret(CLIENT_SECRET)
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

	//Funci�n que busca canciones seg�n un nombre y un pa�s pasado por par�metro
	public Track[] searchSongs(String name, CountryCode countryCode){
		SearchTracksRequest searchTracksRequest = spotifyApi_.searchTracks(name)
				.market(countryCode)
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

	//Funci�n que busca albums segun un nombre y un pa�s pasado por par�metro
	public AlbumSimplified[] searchAlbums(String name, CountryCode countryCode){
		SearchAlbumsRequest searchAlbumsRequest = spotifyApi_.searchAlbums(name)
				.market(countryCode)
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
	
	//Funci�n que busca artistas seg�n un nombre y un pa�s pasado por par�metro.
	public Artist[] searchArtists(String name, CountryCode countryCode){
		SearchArtistsRequest searchArtistsRequest = spotifyApi_.searchArtists(name)
				.market(countryCode)
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
	
	//Funci�n que busca playlists seg�n un nombre y un pa�s pasado por par�metro
	public PlaylistSimplified[] searchPlaylist(String name,CountryCode countryCode){
		SearchPlaylistsRequest searchPlaylitsRequest = spotifyApi_.searchPlaylists(name)
				.market(countryCode)
				.limit(10)
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
	 * Funci�n que obtiene las recomendaciones seg�n los siguientes par�metros:
	 * 1)C�digo de pa�s
	 * 2)identificador del artista(semilla)
	 * 3)identificador de una canci�n(semilla)
	 * 4)g�nero musical
	 */
	public TrackSimplified[] getRecommendations(CountryCode countryCode, String id_artist, String id_track, String genre){
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
	

	//Funci�n que obtiene las principales playlist seg�n un determinado pa�s pasado por par�metro
	public Paging<PlaylistSimplified> getTopPlaylists(CountryCode countryCode){
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



}//Fin SpotifyManager


