package menu;

import gmail.*;
//import twitter.*;



import java.util.InputMismatchException;
import java.util.Scanner;

import javax.mail.Message;



public class Menu {

	private static Scanner scanner;
	private static int opcion;

	public static void main(String[] args) {

		scanner = new Scanner(System.in);

		while (opcion != 7) {
			System.out.println("1. Enviar correo");
			System.out.println("2. Ver correos de la bandeja de entrada");
			//System.out.println("3. Filtrar correos por palabras");
			System.out.println("4. Filtrar correos por asunto");
			System.out.println("5. Filtrar correos por remitente");
			System.out.println("6. Filtrar correos por contenido");

			System.out.println("7. Salir\n");
			System.out.print("Introduce una de las opciones: ");

			try {
				opcion = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("OPCION NO DISPONIBLE\n\n");

				scanner.next();
			}
			GmailManager gmail = new GmailManager();
			switch (opcion){
			case 1:
				String listaDestinatarios;
				String asunto;
				String mensaje;


				Scanner inputCorreo = new Scanner(System.in);
				System.out.printf("Introduce los destinatarios separados por una ',': ");
				listaDestinatarios = inputCorreo.nextLine();

				System.out.printf("Introduce el asunto: ");
				asunto = inputCorreo.nextLine();

				System.out.printf("Introduce el mensaje: ");
				mensaje = inputCorreo.nextLine();


				gmail.sendEmail(listaDestinatarios,asunto,mensaje);


				break;
			case 2:

				try{

					Message[] mensajes = gmail.getInbox();

					//Sacamos los mensajes por pantalla
					for (int i = 0; i < mensajes.length; i++){
						System.out.println(
								"From:" + mensajes[i].getFrom()[0].toString());
						System.out.println("Subject:" + mensajes[i].getSubject());
						//A la hora de imprimir el mensaje llamamos a las funciones privada getTextFromMessage
						System.out.println("Mensaje:" + gmail.getTextFromMessage(mensajes[i]));

					}
				}
				catch (Exception e){
					e.printStackTrace();
				}


				break;
			case 3:
				System.out.println("Opcion 3");
				break;
			case 4: 

				String palabra;
				Scanner inputAsunto = new Scanner(System.in);
				System.out.printf("Introduce una palabra de búsqueda: ");
				palabra = inputAsunto.next();

				try{

					Message[] mensajes = gmail.filterBySubject(palabra);

					//Sacamos los mensajes por pantalla
					for (int i = 0; i < mensajes.length; i++){
						System.out.println(
								"From:" + mensajes[i].getFrom()[0].toString());
						System.out.println("Subject:" + mensajes[i].getSubject());
						System.out.println("Mensaje:" + gmail.getTextFromMessage(mensajes[i]));

					}
				}
				catch (Exception e){
					e.printStackTrace();
				}

				break;
			case 5:

				String palabra3;
				Scanner inputRemitente = new Scanner(System.in);
				System.out.printf("Introduce una palabra de búsqueda: ");
				palabra3 = inputRemitente.next();

				try{

					Message[] mensajes = gmail.filterBySender(palabra3);

					//Sacamos los mensajes por pantalla
					for (int i = 0; i < mensajes.length; i++){
						System.out.println(
								"From:" + mensajes[i].getFrom()[0].toString());
						System.out.println("Subject:" + mensajes[i].getSubject());
						System.out.println("Mensaje:" + gmail.getTextFromMessage(mensajes[i]));

					}
				}
				catch (Exception e){

					e.printStackTrace();
				}

				break;

			case 6:

				String palabra2;
				Scanner inputContenido = new Scanner(System.in);
				System.out.printf("Introduce una palabra de búsqueda: ");
				palabra2 = inputContenido.next();

				try{

					Message[] mensajes = gmail.filterByContent(palabra2);

					//Sacamos los mensajes por pantalla
					for (int i = 0; i < mensajes.length; i++){
						System.out.println(
								"From:" + mensajes[i].getFrom()[0].toString());
						System.out.println("Subject:" + mensajes[i].getSubject());
						System.out.println("Mensaje:" + gmail.getTextFromMessage(mensajes[i]));

					}
				}
				catch (Exception e){
					e.printStackTrace();
				}



				break;
			case 7:
				return;
			}

		}

	}

}
