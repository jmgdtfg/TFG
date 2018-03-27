package outputComponents;

import java.util.Map;

import github.GithubManager;

public class GithubGistOutput implements OutputComponent{
	//Función que publica los datos en un Gist
	@Override
	public void execute(Object data, Map<String, String> configuration) {
		
		GithubManager gm = new GithubManager();
		String message = (String) data;
		
		gm.createGist(
				configuration.get("gistDescription"),
				true,								//El Gist será público por defecto
				message,
				configuration.get("gistName"));
		
	}

}
