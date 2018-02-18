package menu;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.model.param.WeatherData;
import openweathermap.OpenWeatherMapManager;


public class MenuWeather {
	private static Scanner scanner;
	private static int opcion;

	public static void main(String[] args) throws ClientProtocolException, IOException, JSONException, APIException {

		scanner = new Scanner(System.in);

		while (opcion != 8) {
			System.out.println("1. Obtener temp. actual ");
			System.out.println("2. Obtener temp. máxima de hoy ");
			System.out.println("3. Obtener temp. mínima de hoy ");
			System.out.println("4. Obtener humedad ");
			System.out.println("5. Obtener nubosidad ");
			System.out.println("6. Obtener pronóstico ");
			System.out.println("7. Obtener lluvia? ");

			System.out.println("8. Salir\n");
			System.out.print("Introduce una de las opciones: ");

			try {
				opcion = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("OPCION NO DISPONIBLE\n\n");

				scanner.next();
			}
			OpenWeatherMapManager ac = new OpenWeatherMapManager();
			int dato;
			switch (opcion){
			case 1:
				dato = ac.getCurrentTemp("Madrid");
				System.out.println(dato);
				break;
			case 2:

				dato = ac.getMaxTemp("Madrid");
				System.out.println(dato);

				break;
			case 3:
				dato = ac.getMinTemp("Madrid");
				System.out.println(dato);
				break;
			case 4: 

				dato = ac.getHumidity("Madrid");
				System.out.println(dato);
				break;
			case 5:

				dato = ac.getCloudiness("Madrid");
				System.out.println(dato);

				break;

			case 6:
				List<WeatherData> wd = ac.HourlyForecast("Sevilla");
				for (int i=0;i<wd.size();i++){
					System.out.println(wd.get(i).getDateTime());
					System.out.println(wd.get(i).getMainData().getTemp().intValue());
					System.out.println(wd.get(i).getCloudData().getCloudiness());
				}

				break;
			case 7:
				//dato = ac.getRain("Madrid");
				//System.out.println(dato);


				break;
			case 8:
				return;
			}

		}

	}

}
