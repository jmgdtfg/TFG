package menu;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.github.seratch.jslack.api.methods.SlackApiException;

import slack.SlackManager;

public class MenuSlack {
	private static Scanner scanner;
	private static int opcion;

	public static void main(String[] args) throws IOException, SlackApiException{

		scanner = new Scanner(System.in);

		while (opcion != 8) {
			System.out.println("1. Enviar mensaje (webhook service)  ");
			System.out.println("2. Obtener lista de canales ");
			System.out.println("3. Obtener lista de usuarios ");
			System.out.println("4. Enviar mensaje (con bot)");
			System.out.println("5. Enviar mensaje directo(con bot)");
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
			SlackManager sm = new SlackManager();

			switch (opcion){
			case 1:
				sm.sendMessage("Prueba","general","bot3");
				
				break;
			case 2:

				sm.getChannels();


				break;
			case 3:
				sm.getUsers();
				break;
			case 4: 

				sm.sendMessageWithBot("hola", "tfg");

				break;
			case 5:

				sm.sendDirectMessageToAUser("correo@uco.es","hola");

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
