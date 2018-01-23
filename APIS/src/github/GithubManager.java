package github;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;


public class GithubManager {
	private String token_="b62f000ea3190042feeb58730ad10097322353d1";
	private String user_="jmgdtfg@gmail.com";
	private String password_="tfg_pass3";

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

	public void getOwnRepos() throws IOException{

		GitHubClient client = new GitHubClient();
		client.setCredentials(user_,password_);
		RepositoryService service = new RepositoryService(client);
		List<Repository> repos = service.getRepositories();

		for (Repository repo : repos) {
			System.out.println(repo.getId());
			System.out.println(repo.getName());
			System.out.println(repo.getMasterBranch());
		}
	}

	/*
	 * Función que se encarga de clonar repositorios
	 * @param 1 => Url del repositorio a clonar
	 * @param 2 => Directorio local en el que se clona el repositorio
	 * */
	public File cloneRepository(String url, String path) throws GitAPIException {

		File descarga = new File (path);
		if (descarga.exists()){
			System.out.println("El directorio ya existe");
			return null;
		}
		else{
			CloneCommand clone = Git.cloneRepository()	
					.setURI(url)
					.setDirectory(descarga);

			try (Git repositorio = clone.call()) {
				return repositorio.getRepository().getWorkTree();
			}
		}
	}

	/*
	 * Función que se encarga de buscar repositorios
	 * @param 1 => Palabra clave para la busqueda (Ej: slack, telegram, interface...)
	 * @param 2 => Lenguaje de programación que buscamos
	 * */
	public void searchRepos(String keyword, String language) throws IOException {

		Map<String, String> searchQuery = new HashMap<String, String>();
		GitHubClient client = new GitHubClient();
		client.setCredentials(user_,password_);
		RepositoryService service = new RepositoryService(client);
		searchQuery.put("keyword",keyword);
		searchQuery.put("language", language);

		List<SearchRepository> searchRes = service.searchRepositories(searchQuery);

		for(SearchRepository srs : searchRes){
			System.out.println(srs.getOwner().toString());
			System.out.println(srs.getName().toString());
			System.out.println(srs.getCreatedAt().toString());
			System.out.println("-	-	-	-	-");
		}
	}

}



