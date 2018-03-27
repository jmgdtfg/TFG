package inputComponents;

import java.io.IOException;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;

import github.GithubManager;

public class GithubRepositoryInput implements InputComponent{
	//Función que devuelve un repositorio
	@Override
	public Object execute(Map<String, String> configuration) {
		
		GithubManager gm = new GithubManager();
		
		try {
			
			//Devuelve un objeto de tipo File
			return gm.cloneRepository(
					configuration.get("repositoryUrl"),
					configuration.get("localPath"));
			
		} catch (GitAPIException | IOException e) {
			//***
			e.printStackTrace();
			return null;
		}
	}

}
