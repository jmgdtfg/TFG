package webClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;




public class WebClient {

	/*
	M�todo GET:	
	Request has body				No
	Successful response has body	Yes
	Safe							Yes
	Idempotent						Yes
	Cacheable						Yes
	Allowed in HTML forms			Yes
	
	*/
	
	
	public void GET(String url, String type) throws ClientProtocolException, IOException{

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		request.addHeader("content-type", type);
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "en-US,en;q=0.8");

		HttpResponse response = client.execute(request);
		this.responseReader(response);

	}

	
	/*
	M�todo POST:
	Request has body				Yes
	Successful response has body	Yes
	Safe							No
	Idempotent						No
	Cacheable						Only if freshness information is included
	Allowed in HTML forms			Yes
	*/


	public void POST(String url, String data,String type) throws ClientProtocolException, IOException {

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);


		StringEntity entity = new StringEntity(data);
		request.setEntity(entity);


		request.addHeader("content-type", type);
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "en-US,en;q=0.8");


		HttpResponse response = client.execute(request);
		this.responseReader(response);

	}
	
	
	/*
	M�todo PUT	
	Request has body				Yes
	Successful response has body	No
	Safe							No
	Idempotent						Yes
	Cacheable						No
	Allowed in HTML forms			No
	*/

	public void PUT( String url, String data, String type) throws IOException {

		HttpClient client = HttpClientBuilder.create().build();
		HttpPut request = new HttpPut(url);
		StringEntity entity =new StringEntity(data);


		entity.setContentType(type);

		request.addHeader("content-type", type);
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "en-US,en;q=0.8");

		request.setEntity(entity);

		HttpResponse response = client.execute(request);
		this.responseReader(response);

	}

	/*
	M�todo DELETE:
	Request has body				No
	Successful response has body	No
	Safe							No
	Idempotent						Yes
	Cacheable						No
	Allowed in HTML forms			No
	*/

	public void DELETE(String url, String type) throws ClientProtocolException, IOException {

		HttpClient client = HttpClientBuilder.create().build();
		HttpDelete request = new HttpDelete(url);
		request.addHeader("Accept", type);

		request.addHeader("content-type", type);
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "en-US,en;q=0.8");

		HttpResponse response = client.execute(request);
		this.responseReader(response);

	}


	/*	
	M�todo HEAD:
 	Request has body				No
	Successful response has body	No
	Safe							Yes
	Idempotent						Yes
	Cacheable						Yes
	Allowed in HTML forms			No
	*/
	
	public void HEAD(String url, String type) throws IOException {

		HttpClient client = HttpClientBuilder.create().build();
		HttpHead request = new HttpHead(url);


		request.addHeader("Accept", type);
		request.addHeader("content-type", type);
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "en-US,en;q=0.8");


		HttpResponse response = client.execute(request);
		this.responseReader(response);


	}

	/*
	M�todo PATCH:	
	Request has body				Yes
	Successful response has body	No
	Safe							No
	Idempotent						No
	Cacheable						No
	Allowed in HTML forms			No
	*/
	public void PATCH(String url, String data,String type) throws ClientProtocolException, IOException {

		HttpClient client = HttpClientBuilder.create().build();
		HttpPatch request = new HttpPatch(url);


		StringEntity entity = new StringEntity(data);
		request.setEntity(entity);


		request.addHeader("content-type", type);
		request.addHeader("Accept", "*/*");
		request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
		request.addHeader("Accept-Language", "en-US,en;q=0.8");


		HttpResponse response = client.execute(request);
		this.responseReader(response);

	}
	
	/*
	M�todo OPTIONS:	
	Request has body				No
	Successful response has body	Yes
	Safe							Yes
	Idempotent						Yes
	Cacheable						No
	Allowed in HTML forms			No
	*/
	
	public void OPTIONS(String url)throws ClientProtocolException, IOException{
		HttpClient client = HttpClientBuilder.create().build();

				HttpOptions request = new HttpOptions(url);
				HttpResponse response = client.execute(request);
				
				List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());        
			    for (Header header : httpHeaders) {
			        System.out.println(header.getName() + "," + header.getValue());
			    }
			
			}
	
	
	/*
	 * La funci�n responseReader se encarga de capturar los siguientes errores:
	 * 		->NullPointerException: Ocurre cuando la respuesta no tiene cuerpo.(Caso de HEAD)
	 * 		->Error 501: M�todo no soportado
	 * 		->Error 405: M�todo no permitido
	 * 
	 * En caso de que el codigo de respuesta de la petici�n sea 200, se encarga de imprimir
	 * la respuesta.
	 * */
	
	public void responseReader(HttpResponse response) throws UnsupportedOperationException, IOException{
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());


		try{
			if (response.getStatusLine().getStatusCode()==200){
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					System.out.println(line);
					result.append(line);
				}
			}
			else if(response.getStatusLine().getStatusCode()==405){
				System.out.println("Este m�todo no est� permitido en este servidor");
			}
			else if(response.getStatusLine().getStatusCode()==501){
				System.out.println("Este m�todo no est� soportado en este servidor");
			}
			else{
				System.out.println("Error, c�digo de respuesta distinto de 200");
			}
		}
		catch(NullPointerException e) {
			//En el caso de HEAD, al no devolver cuerpo el mensaje no imprimiria nada
		}
	}


}



