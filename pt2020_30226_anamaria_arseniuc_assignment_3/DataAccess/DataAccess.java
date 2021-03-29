package DataAccess;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.itextpdf.text.DocumentException;
import Presentation.FileParser;

/**
 * clasa principala, care contine metoda main
 * 
 * @author Arseniuc Anamaria
 *
 */
public class DataAccess {

	public static void main(String[] args) throws FileNotFoundException, DocumentException, SQLException {
		FileParser fp = new FileParser("commands.txt");
	}

}
