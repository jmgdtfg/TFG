package processingComponents;

import java.util.Map;
//Interfaz de los componentes de procesamiento
public interface ProcessComponent {
	/*Función que tiene como parámetros de entrada los datos y una
	 * configuración. Devuelve cualquier tipo de objeto*/
	Object execute(Object data, Map<String, String> configuration);
	
}
