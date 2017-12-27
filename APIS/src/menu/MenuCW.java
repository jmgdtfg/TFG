package menu;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.http.client.ClientProtocolException;

import webClient.*;

public class MenuCW {
	private static Scanner scanner;
	private static int opcion;

	public static void main(String[] args) throws ClientProtocolException, IOException {

		scanner = new Scanner(System.in);

		while (opcion != 8) {
			System.out.println("1. Probar GET ");
			System.out.println("2. Probar POST ");
			System.out.println("3. Probar PUT ");
			System.out.println("4. Probar DELETE");
			System.out.println("5. Probar HEAD");
			System.out.println("6. Probar OPTIONS");
			System.out.println("7. Probar PATCH");

			System.out.println("8. Salir\n");
			System.out.print("Introduce una de las opciones: ");

			try {
				opcion = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("OPCION NO DISPONIBLE\n\n");

				scanner.next();
			}
			WebClient cw = new WebClient();
			String url = "http://javaen.blogspot.com.es";
			//String url = "http://www.oracle.com/technetwork/articles/java/json-1973242.html";
			switch (opcion){
			case 1:
				
				cw.GET(url,"aplication/xml");
				break;
			case 2:

				cw.POST(url,"<h1>prueba<h1>","aplication/xml");


				break;
			case 3:
				cw.PUT(url, "<h1>prueba<h1>","text/html");
				break;
			case 4: 

				
				cw.DELETE(url,"aplication/json");

				break;
			case 5:

				
				cw.HEAD(url,"aplication/json");

				break;

			case 6:

				cw.OPTIONS(url);

				break;
			case 7:

				cw.PATCH(url,"<h1>prueba<h1>","aplication/xml");

				break;
			case 8:
				return;
			}

		}

	}

}
