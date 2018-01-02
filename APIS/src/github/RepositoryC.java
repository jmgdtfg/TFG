package github;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;

public class RepositoryC {
	
	private Repository repos_ = new Repository();
	private String name_;
	private User user_;
	private String language_;
	private String description_;
	
	//Constructor por defecto de la clase RepositoryC
	public RepositoryC(){}
	
	
	//Constructor parametrizado de la clase RepositoryC
	public RepositoryC(String name, User user, String language, String description){
		
		name_=name;
		user_=user;
		language_=language;
		description_=description;
		
	}
	
	//Función que devuelve el repositorio
	public Repository build(){
		
		repos_.setName(name_);
		repos_.setOwner(user_);
		repos_.setLanguage(language_);
		repos_.setDescription(description_);
		
		return repos_;
	}
	
}
