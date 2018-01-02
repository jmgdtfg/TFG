package github;

import java.io.IOException;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.UserService;


public class GithubManager {
	private String token_={TOKEN};
	private String user_={USUARIO};
	private String password_={PASS};

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
	public void test() throws IOException{
	    GitHubClient client = new GitHubClient();
	    client.setOAuth2Token(token_);
	    UserService uService = new UserService(client);
	    try {
	        User us = uService.getUser();
	        String user = us.getLogin();
	        us.getEmail();
	        System.out.println(user);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }


	}*/
	
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


}
