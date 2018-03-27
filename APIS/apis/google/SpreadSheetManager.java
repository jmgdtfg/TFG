package google;
//Hacer fncion para leer acceso directo a una parte en concreto de la hoja de calculo
//Para escribir lo mismo


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;



public class SpreadSheetManager {

	private String clientId_ = "";
	private String clientSecret_ = "";
	private String token_="";
	private String refreshToken_="";
	private Credential credential_ = new GoogleCredential.Builder()
			.setClientSecrets(clientId_, clientSecret_)
			.setJsonFactory(new JacksonFactory())
			.setTransport(new NetHttpTransport()).build()
			.setAccessToken(token_)
			.setRefreshToken(refreshToken_);
	private SpreadsheetService service_ = new SpreadsheetService("SheetService");
	// Se solicita a la API obtener todas las entradas a todas la hojas de cálculo.
	private URL spreadsheetFeedUrl_ = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
	private SpreadsheetFeed feed_;
	private List<SpreadsheetEntry> spreadsheets_;

	//Constructor por defecto de la clase SpreadSheetManager
	public SpreadSheetManager() throws IOException, ServiceException{
		credential_.refreshToken();
		token_=credential_.getAccessToken();
		service_.setAuthSubToken(token_);
		feed_ = service_.getFeed(spreadsheetFeedUrl_, SpreadsheetFeed.class);
		spreadsheets_ = feed_.getEntries();
	}
	//Constructor parametrizado de la clase SpreadSheetManager
	public SpreadSheetManager(String clientId, String clientSecret, String token, String refreshToken) throws IOException, ServiceException{
		clientId_=clientId;
		clientSecret_=clientSecret;
		token_=token;
		refreshToken_=refreshToken;
		credential_.refreshToken();
		token_=credential_.getAccessToken();
		service_.setAuthSubToken(token_);
		feed_ = service_.getFeed(spreadsheetFeedUrl_, SpreadsheetFeed.class);
		spreadsheets_ = feed_.getEntries();
	}

	//Función que permite leer una hoja de cálculo cuyo título coincida con el nombre pasado por parámetros
	public List<ListEntry> readSheet(String name) throws IOException, ServiceException {

		if (spreadsheets_.isEmpty()) {
			System.out.println("No hay disponible ninguna hoja de cálculo\n");
		}
		for (SpreadsheetEntry spreadsheet : spreadsheets_){
			if (spreadsheet.getTitle().getPlainText().contentEquals(name)){
				System.out.println("Título: " + spreadsheet.getTitle().getPlainText());

				WorksheetFeed worksheetFeed = service_.getFeed(
						spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
				List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
				WorksheetEntry worksheet = worksheets.get(0);
				

				System.out.println("Filas: "+worksheet.getRowCount());
				System.out.println("Columnas: "+worksheet.getColCount());

				URL listFeedUrl = worksheet.getListFeedUrl();
				ListFeed listFeed = service_.getFeed(listFeedUrl, ListFeed.class);
				List<ListEntry> rows = listFeed.getEntries();
/*				for (ListEntry r : rows){
					System.out.println(r.getPlainTextContent());
					System.out.println(r.getCustomElements().getValue("dato1"));
					System.out.println(r.getCustomElements().getTags());


				}*/
				return rows;
			}
		}
		return null;
	}
	
	/*Función que permite escribir en una hoja de cálculo
	 * Parámetros: Nombre de la hoja de cálculo, datos a insertar
	 * */
	public void writeData(String name, Map<String, String> data) throws IOException, ServiceException{
		if (spreadsheets_.size() == 0) {
			System.out.println("No se encontró la hoja de cálculo");
		}
		for (SpreadsheetEntry spreadsheet : spreadsheets_){
			if (spreadsheet.getTitle().getPlainText().contentEquals(name)){

				WorksheetFeed worksheetFeed = service_.getFeed(
						spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
				List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
				WorksheetEntry worksheet = worksheets.get(0);

				//Se establece la URL de la hoja de cálculo que se usa
				URL listFeedUrl = worksheet.getListFeedUrl();
				//Obtenemos el número de columnas de la hoja de cálculo
				ListFeed listFeed = service_.getFeed(listFeedUrl, ListFeed.class);
				
				List<ListEntry> row = listFeed.getEntries();
				ListEntry newRow = new ListEntry(); // Nueva fila que se insertará

				if (!row.isEmpty()){
					//Si existen datos en la hoja de cálculo, antes de insertar nuevos
					//datos hay que obtener los nombres de las columnas
					Set<String> columnHeadings = row.get(0).getCustomElements().getTags();
					List<String> columns = new ArrayList<String>();
					columns.addAll(columnHeadings);

					//Una vez se tienen los nombres de las columnas se insertan los datos
					//en su correspondiente columna
					for (int i = 0; i<columns.size(); i++){
						newRow.getCustomElements().setValueLocal(
								columns.get(i),
								data.getOrDefault(columns.get(i), "null"));
					}
				}
				else{
					
					//Si la hoja está vacia crea las columnas. Sus nombre serán los
					//pasados como clave en el HashMap
					List<String> headers = new ArrayList<String>();
					headers.addAll(data.keySet());
					Collections.sort(headers);						//Ordena alfabéticamente
					this.createColumnHeaders(worksheet, headers);
					
					//Una vez existen las columnas, se insertarán los datos.
					for (int i = 0; i<headers.size(); i++){
						newRow.getCustomElements().setValueLocal(
								headers.get(i),
								data.getOrDefault(headers.get(i), "null"));
					}
				}
				//Se inserta la fila de datos en la hoja de cálculo
				newRow = service_.insert(listFeedUrl, newRow);
			}
		}
	}
	
	//Función utilizada para crear las columnas. Necesita el worksheet y una lista con los nombres
	private void createColumnHeaders(WorksheetEntry worksheet, List<String> headers) throws IOException, ServiceException{
		URL cellFeedUrl = worksheet.getCellFeedUrl();
		CellFeed cellFeed = service_.getFeed(cellFeedUrl, CellFeed.class);
		CellEntry cellEntry;
		
		for (int i = 1; i <= headers.size(); i++){
			//Itera sobre la lista pasada por parámetro, insertando los nombres a las columnas
			cellEntry = new CellEntry(1, i, headers.get(i-1));
			cellFeed.insert(cellEntry);
		}
	}
	
	/*Función para insertar un dato en una celda específica
	 * Parametro 1: Nombre de la hoja de cálculo
	 * Parametro 2: Dato que se insertará
	 * */
	public void setDataInSpecificCell(String name,int row, int column, String data) throws IOException, ServiceException{
		if (spreadsheets_.size() == 0) {
			System.out.println("No se encontró la hoja de cálculo");
		}
		for (SpreadsheetEntry spreadsheet : spreadsheets_){
			if (spreadsheet.getTitle().getPlainText().contentEquals(name)){

				WorksheetFeed worksheetFeed = service_.getFeed(
						spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
				List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
				WorksheetEntry worksheet = worksheets.get(0);
				
				URL cellFeedUrl = worksheet.getCellFeedUrl();
				CellFeed cellFeed = service_.getFeed(cellFeedUrl, CellFeed.class);
				CellEntry cellEntry = new CellEntry(row,column,data);
				cellFeed.insert(cellEntry);
			}
		}
		
	}
	public String getDataFromSpecificCell(String name, int row, int column) throws IOException, ServiceException{
		if (spreadsheets_.size() == 0) {
			System.out.println("No se encontró la hoja de cálculo");
		}
		for (SpreadsheetEntry spreadsheet : spreadsheets_){
			if (spreadsheet.getTitle().getPlainText().contentEquals(name)){

				WorksheetFeed worksheetFeed = service_.getFeed(
						spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
				List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
				WorksheetEntry worksheet = worksheets.get(0);
				
				URL cellFeedUrl = worksheet.getCellFeedUrl();
				CellFeed cellFeed = service_.getFeed(cellFeedUrl, CellFeed.class);
				
				for (CellEntry cell: cellFeed.getEntries()){
					if (cell.getCell().getCol() == column && cell.getCell().getRow() == row){
						return cell.getCell().getValue();
					}
				}		
			}
		}
		return null;
	}
	
	
	//Función para leer un dato de una celda específica

	//Función que muestra los títulos de las columnas.
	public void showColumsTitle(String name) throws IOException, ServiceException{

		if (spreadsheets_.size() == 0) {
			System.out.println("No se encontró la hoja de cálculo");
		}
		for (SpreadsheetEntry spreadsheet : spreadsheets_){
			if (spreadsheet.getTitle().getPlainText().contentEquals(name)){

				WorksheetFeed worksheetFeed = service_.getFeed(
						spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
				List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
				WorksheetEntry worksheet = worksheets.get(0);

				//Se establece la URL de la hoja de cálculo que se usa
				URL listFeedUrl = worksheet.getListFeedUrl();
				ListFeed listFeed = service_.getFeed(listFeedUrl, ListFeed.class);
				List<ListEntry> row = listFeed.getEntries();
				if (!row.isEmpty()){
					Set<String> columnHeadings = row.get(0).getCustomElements().getTags();
					System.out.println(columnHeadings);
				}else{
					URL cellFeedUrl = worksheet.getCellFeedUrl();
					CellFeed cellFeed = service_.getFeed(cellFeedUrl, CellFeed.class);
					// Iteramos sobre las celdas para obtener los datos
					System.out.print("[");
					for (CellEntry cell : cellFeed.getEntries()) {
						System.out.print(cell.getCell().getValue()+", ");
					}
					System.out.println("]");
				}
			}
		}
	}
}
