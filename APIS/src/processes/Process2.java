/*Este proceso proporciona un resumen de un proyecto GitHub
- 1)Obtener un repositorio dada su URL
- 2)Contar el número de archivos de cada tipo que existen. Ejemplo: 10 archivos .java, 2 .xml...
- 3)Enviar resultado del informe por email y publicar un tweet
 */
package processes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.jgit.api.errors.GitAPIException;

import github.GithubManager;
import mail.MailManager;
import twitter.TwitterManager;
import twitter4j.TwitterException;

public class Process2 {

	private static String repositoryUrl_;
	private static String emailList_;
	private static String path_;

	/*El constructor de esta clase necesitará:
	 * Repositorio que se clonará ( url )
	 * Email(s)
	 * Ruta donde se clonarán los archivos localmente
	 * */
	public Process2(String repositoryUrl, String emailList, String path){
		repositoryUrl_=repositoryUrl;
		emailList_=emailList;
		path_=path;
	}
	
	public static void main(String[] args) throws IOException, GitAPIException, TwitterException{
		
		//Se crean los objetos necesarios
		GithubManager gm = new GithubManager();
		TwitterManager tm = new TwitterManager();
		MailManager mm = new MailManager();
		
		// Lista para los valores devueltos por getDirectoryTree(path)
		List<Path> pathFiles = new LinkedList<Path>();
		//Mapa para acoger los datos =>"tipo", nº de apariciones
		Map<String, Integer> data = new HashMap<String, Integer>();		

		gm.cloneRepository(repositoryUrl_, path_);
		pathFiles = gm.getDirectoryTree(path_);
		
		//Recorremos las rutas de cada archivo
		for (Path p : pathFiles){
			/*Si el para una palabra clave determinada EJ: xml, no existe ningún valor en
			 * el Map se le asignará un nuevo valor de aparición (1)
			 * */
			if (data.get(FilenameUtils.getExtension(p.getFileName().toString()))==null){
				data.put(FilenameUtils.getExtension(p.getFileName().toString()), 1);
			}
			//Si ya existe dicha palabra clave se reemplazara su valor antiguo por uno nuevo (+1)
			else if(data.get(FilenameUtils.getExtension(p.getFileName().toString()))>=1){
				int value = data.get(FilenameUtils.getExtension(p.getFileName().toString()))+1;
				data.replace(
						FilenameUtils.getExtension(p.getFileName().toString()),
						value);
			}
		}
		
		//Elaboración del mensaje
		String message = "Tipos de archivos que contiene el repositorio "+repositoryUrl_+":"+"\n\n";

		for (Map.Entry<String,Integer> entry : data.entrySet()) {
			String key = entry.getKey();
			int value = entry.getValue();
			if (key == "")
				message = message.concat("Otros"+": "+value+"\n");
			else
				message = message.concat(key+": "+value+"\n");
		}
		
		//Se envía el mensaje a twitter y e-mails
		mm.sendEmail(emailList_, "INFORME DE: "+repositoryUrl_, message);
		tm.writeTweet(message);
	}
}
