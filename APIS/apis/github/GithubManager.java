package github;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;



public class GithubManager {
	private String token_="";
	private String user_="";
	private String password_="";
	private File file_ = null;

	//Constructor parametrizado de la clase GithubManager
	public GithubManager(String user,String password, String token){
		user_=user;
		password_=password;
		token_=token;
	}

	//Constructor por defecto de la clase GithubManager
	public GithubManager() {
		// TODO Auto-generated constructor stub
	}


	/*
	 * Función que se encarga de crear un nuevo respositorio
	 * Devuelve true si se crea con éxito
	 * Devuelve false si el repositorio ya existe o no se pudo crear
	 * */
	public boolean createRepository(String name, String language, String description) throws IOException {

		GitHubClient client = new GitHubClient();	    
		client.setOAuth2Token(token_);
		RepositoryService service = new RepositoryService(client);
		UserService uService = new UserService(client);
		RepositoryC repo = new RepositoryC(name,uService.getUser(),language,description);    
		try {
			service.createRepository(repo.build());
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	/*
	 * Función que se encarga de crear un nuevo Gist
	 * Devuelve true si se crea con éxito
	 * Devuelve false si el repositorio ya existe o no se pudo crear
	 * */
	public boolean createGist(String description, boolean isPublic, String content, String name){
		GitHubClient client = new GitHubClient();	    
		client.setOAuth2Token(token_);
		GistService service = new GistService(client);
		GistC gist = new GistC(description,isPublic,content,name);    
		try {
			service.createGist(gist.build());
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	//Función que se encarga obtener nuestros propios repositorios

	public List<org.eclipse.egit.github.core.Repository> getOwnRepos() throws IOException{

		GitHubClient client = new GitHubClient();
		client.setCredentials(user_,password_);
		RepositoryService service = new RepositoryService(client);
		List<org.eclipse.egit.github.core.Repository> repos = service.getRepositories();
		return repos;
		/*		for (Repository repo : repos) {
			//System.out.println(repo.getId());
			System.out.println(repo.getName());
			//System.out.println(repo.getMasterBranch());
		}*/
	}

	/*
	 * Función que se encarga de clonar repositorios
	 * @param 1 => Url del repositorio a clonar
	 * @param 2 => Directorio local en el que se clona el repositorio
	 * */
	public File cloneRepository(String url, String path) throws GitAPIException, IOException {

		File descarga = new File (path);
		if (descarga.exists()){
			//Primero vaciamos el directorio
			FileUtils.deleteDirectory(descarga);
			CloneCommand clone = Git.cloneRepository()		
					.setURI(url)
					.setDirectory(descarga);

			try (Git repositorio = clone.call()) {
				file_=repositorio.getRepository().getDirectory();		
				return file_;//.getWorkTree();

			}
		}
		else{
			CloneCommand clone = Git.cloneRepository()		
					.setURI(url)
					.setDirectory(descarga);

			try (Git repositorio = clone.call()) {
				file_=repositorio.getRepository().getDirectory();
				return file_;//.getWorkTree();

			}
		}
	}
	//Función que lista todos los archivos de un directorio
	public List<Path> getDirectoryTree(String path) throws IOException{
		List<Path>pathFiles = new LinkedList<Path>();
		Files.walk(Paths.get(path)).forEach(ruta-> {
			if (Files.isRegularFile(ruta)){	    	
				pathFiles.add(ruta);
			}
		});
		return pathFiles;
	}
	/*
	 * Función que copia en una determinada ruta un archivo cuya ruta 
	 * ha sido especificada por parámetro.
	 * */
	public void getFile(String path){
		Path initPath = FileSystems.getDefault().getPath(path);
		Path finalPath = FileSystems.getDefault().getPath("C:/Users/jmgd_/Desktop/example/");

		try {
			Files.move(initPath, finalPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/*
	 * Función que se encarga de buscar repositorios
	 * @param 1 => Palabra clave para la busqueda (Ej: slack, telegram, interface...)
	 * @param 2 => Lenguaje de programación que buscamos
	 * */
	public List<SearchRepository> searchRepos(String keyword, String language) throws IOException {

		Map<String, String> searchQuery = new HashMap<String, String>();
		GitHubClient client = new GitHubClient();
		client.setCredentials(user_,password_);
		RepositoryService service = new RepositoryService(client);
		searchQuery.put("keyword",keyword);
		searchQuery.put("language", language);

		List<SearchRepository> searchRes = service.searchRepositories(searchQuery);
		return searchRes;
		/*		for(SearchRepository srs : searchRes){
			System.out.println(srs.getOwner().toString());
			System.out.println(srs.getName().toString());
			System.out.println(srs.getCreatedAt().toString());
			System.out.println("-	-	-	-	-");
		}*/
	}
	//Función para borrar un directorio pasado por parámetro
	public void delete(String path) throws IOException{
		File descarga = new File (path);
		FileUtils.deleteDirectory(descarga);
	}

}



