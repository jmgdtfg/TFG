package menu;


import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;

import spotify.SpotifyManager;



public class MenuSpotify {
	private static Scanner scanner;
	private static int opcion;

	public static void main(String[] args) throws SpotifyWebApiException, IOException {

		scanner = new Scanner(System.in);

		while (opcion != 7) {
			System.out.println("1. Buscar canción ");
			System.out.println("2. Buscar album ");
			System.out.println("3. Buscar artista ");
			System.out.println("4. Buscar playlist");
			System.out.println("5. Obtener recomendaciones ");
			System.out.println("6. Obtener top playlist ");
			System.out.println("7. Salir\n");
			System.out.print("Introduce una de las opciones: ");

			try {
				opcion = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("OPCION NO DISPONIBLE\n\n");

				scanner.next();
			}
			SpotifyManager sm = new SpotifyManager();
			CountryCode countryCode = CountryCode.ES;
			switch (opcion){
			case 1:
				Track[] songs = sm.searchSongs("Vereda de la puerta", countryCode);

				for (Track song : songs){
					ArtistSimplified[] ar = song.getArtists();
					System.out.println(song.getName());
					for (ArtistSimplified x : ar){
						System.out.println(x.getName()+", ");
					}

					System.out.println(song.getPopularity());
					System.out.println(song.getId());
					System.out.println("------");
				}
				break;
			case 2:
				AlbumSimplified[] albums = sm.searchAlbums("Standby", countryCode);

				for (AlbumSimplified album : albums){
					ArtistSimplified[] ar = album.getArtists();
					System.out.println(album.getName());
					for (ArtistSimplified x : ar){
						System.out.println(x.getName()+", ");
					}

					System.out.println(album.getAlbumType());
					System.out.println(album.getId());
					System.out.println("------");
				}


				break;
			case 3:

				Artist[] artists = sm.searchArtists("Extremoduro", countryCode);

				for (Artist artist : artists){
					//ArtistSimplified[] ar = album.getArtists();
					System.out.println(artist.getName());


					System.out.println(artist.getFollowers().getTotal());
					System.out.println(artist.getId());
					System.out.println("------");
				}
				break;
			case 4: 
				PlaylistSimplified[] playlists = sm.searchPlaylist("trap", countryCode);

				for (PlaylistSimplified playlist : playlists){
					//ArtistSimplified[] ar = album.getArtists();
					System.out.println(playlist.getName());
					System.out.println(playlist.getOwner().getEmail());					
					System.out.println(playlist.getSnapshotId());
					System.out.println("------");
				}
				break;
			case 5:
				
				String id_artist = "3bgsNtcf5d5h9jbQbohfBK";//ID extremoduro
				String id_track = "1KPLNOTQDSWe68ea6JUjpx";//ID "La vereda de la puerta de atras"
				String genre = "rock";
				TrackSimplified[] tracks = sm.getRecommendations(countryCode,id_artist,id_track,genre);

				for (TrackSimplified track : tracks){
					System.out.println(track.getName());
					System.out.println(track.getPreviewUrl());
				}

				break;

			case 6:

				
				Paging<PlaylistSimplified> ps = sm.getTopPlaylists(countryCode);
				PlaylistSimplified[] psf = ps.getItems();
				for (PlaylistSimplified p : psf){
					System.out.println(p.getName());
				}

				break;

			case 7:
				return;
			}

		}

	}

}
