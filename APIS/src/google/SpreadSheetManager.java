package google;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

	private String clientId_ = {CLIENT_ID};
	private String clientSecret_ = {CLIENT_SECRET};
	private String token_={TOKEN}
	private String refreshToken_={REFRESH_TOKEN};
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
	public void readSheet(String name) throws IOException, ServiceException {

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

				//Obtenemos las celdas de una determinada hoja de cálculo
				URL cellFeedUrl = worksheet.getCellFeedUrl();
				CellFeed cellFeed = service_.getFeed(cellFeedUrl, CellFeed.class);

				// Iteramos sobre las celdas para obtener los datos
				for (CellEntry cell : cellFeed.getEntries()) {
					// Nombre de la celda
					System.out.print(cell.getTitle().getPlainText() + "\t");
					// Valor de la celda
					System.out.println(cell.getCell().getValue() + "\t");
				}
			}
		}
	}
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
					Set<String> columnHeadings = row.get(0).getCustomElements().getTags();
					List<String> columns = new ArrayList<String>();
					columns.addAll(columnHeadings);

					for (int i = 0; i<columns.size(); i++){
						newRow.getCustomElements().setValueLocal(
								columns.get(i),
								data.getOrDefault(columns.get(i), "null"));
					}
				}
				else{
					URL cellFeedUrl = worksheet.getCellFeedUrl();
					CellFeed cellFeed = service_.getFeed(cellFeedUrl, CellFeed.class);
					for (CellEntry cell : cellFeed.getEntries()) {
						newRow.getCustomElements().setValueLocal(
								cell.getCell().getValue(),
								data.getOrDefault(cell.getCell().getValue(), "null"));
					}
				}
				//Se inserta la fila de datos en la hoja de cálculo
				newRow = service_.insert(listFeedUrl, newRow);
			}
		}
	}

	public void getColumsTitle(String name) throws IOException, ServiceException{

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
