

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import inputComponents.*;		//Todos los componentes de entrada
import outputComponents.*;		//Todos los componentes de salida
import processingComponents.*;	//Todos los componentes de procesamiento
import rss.RssClient;

//Ejemplo de un flujo de información completo
public class Prueba {

	public static void main(String[] args){

		Map <String, String> configuration = new HashMap<String,String>();
		
		//****PARÁMETROS DE CONFIGURACIÓN GENERICOS
		
		/*Palabra de búsqueda. Se utiliza en: 
		 * - TwitterSearchInput 
		 * - SpotifySearchInput
		 * - MailSearchInboxInput
		 * - GithubSearchRepositoryInput
		 * */
		configuration.put("word", "la");
		/*País. Se utiliza en:
		 * - TwitterTrendingTopicsInput (El país se pasa en inglés)
		 * - Todos los Input Components de Spotify (El país se pasa en castellano)
		 * */
		configuration.put("country", "España");
		
		//****PARAMETROS DE CONFIGURACIÓN ÚNICOS DE TWITTER
		
		//Esta key solo funciona correctamente con los valores "hashtag" y "word"
		configuration.put("twitterSearchType", "hashtag");	
		configuration.put("hashtag", "Sevilla");			//Hashtag clave para realizar búsqueda
		
		//****PARAMETROS DE CONFIGURACIÓN ÚNICOS DE EMAIL
		
		//Esta key solo funciona correctamente con los valores "content","sender" y "subject"
		configuration.put("mailSearchType", "subject");
		configuration.put("emailList", "i32gaduj@uco.es");	//Lista de emails("a@uco.es,b@uco.es[...]")
		configuration.put("subject", "probando...");		//Asunto del mensaje ( para enviar correos )

		//****PARAMETROS DE CONFIGURACIÓN ÚNICOS DE SPOTIFY
		
		//Esta key solo funciona correctamente los valores "tracks","albums","artists" y "playlists"
		configuration.put("spotifySearchType", "tracks");		
		configuration.put("idArtist", "3bgsNtcf5d5h9jbQbohfBK");	//Id de extremoduro en spotify
		configuration.put("idTrack", "1KPLNOTQDSWe68ea6JUjpx");		//Id de "la vereda de la puerta de atras"
		configuration.put("genre", "rock");							//Genero musical
		configuration.put("IdUserSpotify", "soniamqmz");			//Id de usuario
		configuration.put("IdPlaylist", "2XI2yekXk221aNsTN18pXr");	//Id de playlist
		
		//****PARAMETROS DE CONFIGURACIÓN ÚNICOS DE GITHUB
		configuration.put("repositoryUrl", "https://github.com/jmgdtfg/TFG/");//URL de un repositorio
		configuration.put("localPath", "C:/Users/jmgd_/Desktop/descarga");//Ruta local donde se descarga un repositorio
		configuration.put("searchLanguage", "Java");//Se utiliza para buscar resultados con un lenguaje de programación concreto.
		configuration.put("gistName", "---GIST---");//Nombre de un Gist
		configuration.put("gistDescription", "Gist de prueba...");//Descripción del Gist
		
		//****PARAMETROS DE CONFIGURACIÓN ÚNICOS DE SLACK
		configuration.put("channelName", "general"); 				//Nombre del canal de slack
		configuration.put("slackUserMail", "jmgd_3@outlook.com");	//Email de un usuario en slack
		
		//****PARAMETROS DE CONFIGURACIÓN ÚNICOS DE RSS
		configuration.put("rssUrl", "https://futbol.as.com/rss/futbol/primera.xml"); //URL del canal RSS
		
		//****PARAMETROS DE CONFIGURACIÓN ÚNICOS DE OPENWEATHERMAP
		configuration.put("weatherCity", "Sevilla");				//Ciudad de la que se obtendrán los datos
		
		//****PARAMETROS DE CONFIGURACIÓN ÚNICOS DE SPREADSHEET
		configuration.put("sheetName", "Datos");					//Nombre de la hoja de cálculo
		
		//EJEMPLO
		/*
		TwitterSearchInput input = new TwitterSearchInput(20,300);
		SlackChannelMessageOutput output = new SlackChannelMessageOutput();
		//TwitterOutput output = new TwitterOutput();
		TopFiveLikesProcess process = new TopFiveLikesProcess();
		
		output.execute(process.execute(input.execute(configuration), configuration), configuration);
		
		*/
		
		
	}
}