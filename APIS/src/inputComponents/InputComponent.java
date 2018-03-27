package inputComponents;

import java.util.Map;
//Interfaz de los componentes de entrada
public interface InputComponent {
	/*Función que podrá tener como parámetro de entrada una configuración
	 * y que devolverá cualquier tipo de objeto (Tweet, tracks, mails...)*/
	Object execute(Map<String, String> configuration);

}
