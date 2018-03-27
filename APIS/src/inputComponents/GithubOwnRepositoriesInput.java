package inputComponents;

import java.io.IOException;
import java.util.Map;

import github.GithubManager;

public class GithubOwnRepositoriesInput implements InputComponent{
	//Función que devuelve una lista de los propios repositorios
	@Override
	public Object execute(Map<String, String> configuration) {
		
		GithubManager gm = new GithubManager();
		
		try {
			//Devuelve un objeto de tipo List<Repository>
			return gm.getOwnRepos();
			
		} catch (IOException e) {
			// ***
			e.printStackTrace();
			return null;
		}
	}

}
