package menu;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.client.ClientProtocolException;

import com.google.gdata.util.ServiceException;

import google.SpreadSheetManager;



public class MenuSpreadSheet {
	private static Scanner scanner;
	private static int opcion;

	public static void main(String[] args) throws ClientProtocolException, IOException, ServiceException {

		scanner = new Scanner(System.in);

		while (opcion != 4) {
			System.out.println("1. Leer hoja de calculo  ");
			System.out.println("2. Escribir en hoja de calculo ");
			System.out.println("3. Obtener nombre de las columnas ");

			System.out.println("4. Salir\n");
			System.out.print("Introduce una de las opciones: ");

			try {
				opcion = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("OPCION NO DISPONIBLE\n\n");

				scanner.next();
			}
			SpreadSheetManager ssm = new SpreadSheetManager();
			switch(opcion){

			case 1:

				ssm.readSheet("Datos");

				break;
			case 2:
				Map<String, String> data = new HashMap<String, String>();
				data.put("dato0", "a");
				data.put("dato1", "b");
				data.put("dato3", "d");
				data.put("dato4", "e");
				ssm.writeData("Datos", data);

				break;
			case 3:
				ssm.getColumsTitle("Datos");
				break;
			case 4: 

				return;
			}

		}

	}

}
