package processingComponents;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import github.GithubManager;

public class RepositoryInformationProcess implements ProcessComponent{
	//Función que obtiene la información de un repositorio.
	@Override
	public Object execute(Object data, Map<String, String> configuration) {
		GithubManager gm = new GithubManager();
		File file = (File) data;
		
		// Lista para los valores devueltos por getDirectoryTree(path)
		List<Path> pathFiles = new LinkedList<Path>();
		
		//Mapa para acoger los datos =>"tipo", nº de apariciones
		Map<String, Integer> datos = new HashMap<String, Integer>();
		
		try {
			pathFiles = gm.getDirectoryTree(file.getParent());
			
			//Recorremos las rutas de cada archivo
			for (Path p : pathFiles){
				/*Si el para una palabra clave determinada EJ: xml, no existe ningún valor en
				 * el Map se le asignará un nuevo valor de aparición (1)
				 * */
				if (datos.get(FilenameUtils.getExtension(p.getFileName().toString()))==null){
					datos.put(FilenameUtils.getExtension(p.getFileName().toString()), 1);
				}
				//Si ya existe dicha palabra clave se reemplazara su valor antiguo por uno nuevo (+1)
				else if(datos.get(FilenameUtils.getExtension(p.getFileName().toString()))>=1){
					int value = datos.get(FilenameUtils.getExtension(p.getFileName().toString()))+1;
					datos.replace(
							FilenameUtils.getExtension(p.getFileName().toString()),
							value);
				}
			}
			
			//Elaboración del mensaje
			String message = "Tipos de archivos que contiene el repositorio "+file.getParent()+":"+"\n\n";

			for (Map.Entry<String,Integer> entry : datos.entrySet()) {
				String key = entry.getKey();
				int value = entry.getValue();
				if (key == "")
					message = message.concat("Otros"+": "+value+"\n");
				else
					message = message.concat(key+": "+value+"\n");
			}
			//Devuelve el mnesaje
			return message;
		} catch (IOException e) {
			// ***
			e.printStackTrace();
			return null;
		}

	}

}
