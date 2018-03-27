package google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class OAuth2ProtocolFlow {

	//Es necesario crear un cliente OAuth en la cuenta de desarrollador de google
	private String clientId_ = "";
	private String clientSecret_ = "";
	private String redirectUrl_ = "";
	// Se declara el ámbito para el que será util el token.
	static List<String> scopes_ = Arrays.asList("https://spreadsheets.google.com/feeds");
	//Función principal
	public static void main (String args[]) throws IOException{

		OAuth2ProtocolFlow oauth = new OAuth2ProtocolFlow();

		Credential credencial = oauth.getCredentials();
		System.out.println("Token=> " + credencial.getAccessToken());
		System.out.println("Refresh TOKEN => "+ credencial.getRefreshToken());
		//refreshToken_=credencial.getRefreshToken();
		//credencial.refreshToken();
		//System.out.println("Token=> " + credencial.getAccessToken());
		//System.out.println("Refresh TOKEN => "+ credencial.getRefreshToken());


	}

	//Constructor por defecto de la clase OAuth2ProtocolFLow
	public OAuth2ProtocolFlow(){}
	//Constructor parametrizado de la clase Oauth2ProtocolFlow
	public OAuth2ProtocolFlow(String clientId, String clientSecret, String redirectUrl, List<String> scopes){
		clientId_=clientId;
		clientSecret_=clientSecret;
		redirectUrl_=redirectUrl;
		scopes_=scopes;
	}

	private Credential getCredentials() throws IOException {

		HttpTransport transport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		//Paso 1: Se solicita la autorización a google
		String authorizationUrl =
				new GoogleAuthorizationCodeRequestUrl(clientId_, redirectUrl_, scopes_)
				.setApprovalPrompt("force")
				.setAccessType("offline")
				.build();
		//Paso 2: Se ha generado la URL de autorización

		System.out.println("Visita esta página en tu navegador: ");
		System.out.println(authorizationUrl);

		// Si google autoriza la solicitud nos devolverá un código de autorización
		// en la dirección REDIRECT_URI que hayamos proporcionado.
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Introduce el código de autorización");
		String code = in.readLine();
		//Enviamos la solicitud a google con toda la información completa para que nos
		//proporcione las credenciales. De ellas podremos obtener el token.

		GoogleTokenResponse response =
				new GoogleAuthorizationCodeTokenRequest(transport, jsonFactory, clientId_, clientSecret_,
						code, redirectUrl_).execute();

		return new GoogleCredential.Builder()
				.setClientSecrets(clientId_, clientSecret_)
				.setJsonFactory(jsonFactory)
				.setTransport(transport).build()
				.setAccessToken(response.getAccessToken())
				.setRefreshToken(response.getRefreshToken());
	}

}
