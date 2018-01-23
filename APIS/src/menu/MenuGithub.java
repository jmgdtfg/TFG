package menu;



import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.eclipse.jgit.api.errors.GitAPIException;

import github.GithubManager;

public class MenuGithub {
	private static Scanner scanner;
	private static int opcion;

	public static void main(String[] args) throws IOException, GitAPIException{

		scanner = new Scanner(System.in);


		GithubManager gm = new GithubManager();

		while (opcion != 8) {
			System.out.println("1. Crear nuevo repositorio ");
			System.out.println("2. Crear nuevo Gist ");
			System.out.println("3. Obtener repositorios propios");
			System.out.println("4. Buscar repositorios");
			System.out.println("5. Clonar repositorios");
			System.out.println("6. ");
			System.out.println("7. ");

			System.out.println("8. Salir\n");
			System.out.print("Introduce una de las opciones: ");

			try {
				opcion = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("OPCION NO DISPONIBLE\n\n");

				scanner.next();
			}


			switch (opcion){
			case 1:

				if (gm.createRepository("Repositorio de prueba", "Java", "Probando crear repositorio..."))
					System.out.println("Repositorio creado con éxito");
				else
					System.out.println("Ya existe un repositorio con el mismo nombre");
				break;
					
				case 2:

					if (gm.createGist("Descripcion",true,"Contenido", "nombre"))
						System.out.println("Gist creado con éxito");
					else
						System.out.println("Ocurrió un error");


					break;
				case 3:
					gm.getOwnRepos();
					
					break;
				case 4: 

					gm.searchRepos("slack","Java");


					break;
				case 5:


					gm.cloneRepository(
							"https://github.com/jmgdtfg/TFG","C:/Users/jmgd_/Desktop/descarga");

					break;

				case 6:

					break;
				case 7:


					break;
				case 8:
					return;
			}
		}
	}
}
