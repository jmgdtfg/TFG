package outputComponents;

import java.util.Map;
//Interfaz de los componentes de salida
public interface OutputComponent {
	/*Función que tiene como parámetro de entrada los datos y
	 * una configuración. No devuelve nada*/
	void execute(Object data, Map<String, String> configuration);
	
}
