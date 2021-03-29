package Presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import DataAccess.ConnectionFactory;

import Model.Client;
import Model.Order;
import Model.Product;

public class FileParser {
	ArrayList<Client> clientList = new ArrayList<Client>();
	ArrayList<Product> productList = new ArrayList<Product>();
	ArrayList<Order> orderList = new ArrayList<Order>();
	private String inputFile;

	/**
	 * constructorul clasei
	 * 
	 * @param inFile
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws SQLException
	 */
	public FileParser(String inFile) throws FileNotFoundException, DocumentException, SQLException {
		this.inputFile = inFile;
		this.readFromFile();
	}

	/**
	 * metoda de citire din fisierul de intrare si prelucrarea datelor crearea
	 * pdf-urilor
	 * 
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws SQLException
	 */
	public void readFromFile() throws FileNotFoundException, DocumentException, SQLException {
		Scanner file = null;
		try {
			file = new Scanner(new File(this.inputFile));
		} catch (Exception e) {
			System.out.println("Nu se gaseste fisierul!\n");
		}

		// creare documente(report-uri pentru tabelele date:client, product, order, cat
		// si si pentru bill-uri
		Document reportClient = new Document();
		PdfWriter writer = PdfWriter.getInstance(reportClient, new FileOutputStream("REPORT CLIENT.pdf"));
		reportClient.open();

		Document reportProduct = new Document();
		PdfWriter writer2 = PdfWriter.getInstance(reportProduct, new FileOutputStream("REPORT PRODUCT.pdf"));
		reportProduct.open();

		Document reportOrder = new Document();
		PdfWriter writer3 = PdfWriter.getInstance(reportOrder, new FileOutputStream("REPORT ORDER.pdf"));
		reportOrder.open();

		Document bill1 = new Document();
		PdfWriter writer4 = PdfWriter.getInstance(bill1, new FileOutputStream("Bill_1.pdf"));
		bill1.open();

		Document bill2 = new Document();
		PdfWriter writer5 = PdfWriter.getInstance(bill2, new FileOutputStream("Bill_2.pdf"));
		bill2.open();

		Document understock = new Document();
		PdfWriter writer6 = PdfWriter.getInstance(understock, new FileOutputStream("Under_Stock.pdf"));
		understock.open();

		// conectarea la baza de date
		Connection conn = ConnectionFactory.getConnection();

		int i = 0;
		int j = 0;
		int k = 0;

		// parcurgerea efectiva a fisierului
		while (file.hasNextLine()) {

			// se insereaza clientul
			if (file.findInLine("Insert client:") != null) {
				System.out.println("Insert client: OK");
				String clientName = file.next() + " " + file.next();
				clientName = clientName.substring(0, clientName.length() - 1);
				String clientAddress = file.next();

				Client c = new Client(++i, clientName, clientAddress);
				clientList.add(c);

				PreparedStatement inserting = conn
						.prepareStatement("INSERT INTO database.client (id,name,address) VALUES (?,?,?)");
				inserting.setInt(1, i);
				inserting.setString(2, clientName);
				inserting.setString(3, clientAddress);
				inserting.executeUpdate();

			}

			// stergerea clientului din tabela
			else if (file.findInLine("Delete client:") != null) {
				System.out.println("Delete client: OK");
				String clientName = file.next() + " " + file.next();
				clientName = clientName.substring(0, clientName.length() - 1);
				String address = file.next();

				int id = -1;
				for (int m = 0; m < clientList.size(); m++) {
					if (clientList.get(m).getName().equals(clientName)
							&& clientList.get(m).getAddress().equals(address)) {
						id = m;
						try {
							PreparedStatement deleting = conn
									.prepareStatement("DELETE FROM database.client WHERE name = ? AND address = ?");
							deleting.setString(1, clientName);
							deleting.setString(2, address);
							deleting.executeUpdate();

						} catch (SQLException e) {
							System.out.println("dc");
						}
					}
				}
				if (id != -1) {
					clientList.remove(clientList.get(id));
				}
			}

			// inserarea produsului
			else if (file.findInLine("Insert product:") != null) {
				System.out.println("Insert product: OK");

				String productName = file.next();
				productName = productName.substring(0, productName.length() - 1);
				String Quantity = file.next();
				Quantity = Quantity.substring(0, Quantity.length() - 1);
				String price = file.next();

				int aux = 0;
				for (int m = 0; m < productList.size(); m++) {
					if (productList.get(m).getName().equals(productName)) {
						aux++;
						int quant = productList.get(m).getQuantity() + Integer.parseInt(Quantity);

						PreparedStatement updating = conn
								.prepareStatement("UPDATE database.product SET quantity= ? WHERE id=? ");
						updating.setInt(1, quant);
						updating.setInt(2, productList.get(m).getId());
						updating.executeUpdate();
						productList.get(m).setQuantity(quant);

					}
				}
				if (aux == 0) {
					Product p = new Product(++j, productName, Integer.parseInt(Quantity), Float.parseFloat(price));
					productList.add(p);

					PreparedStatement inserting = conn.prepareStatement(
							"INSERT INTO database.product(id, name, quantity, price) VALUES(?,?,?,?)");
					inserting.setInt(1, j);
					inserting.setString(2, productName);
					inserting.setInt(3, Integer.parseInt(Quantity));
					inserting.setFloat(4, Float.parseFloat(price));
					inserting.executeUpdate();
				}
			}

			// stergerea produsului
			else if (file.findInLine("Delete Product:") != null) {
				System.out.println("Delete Product: OK");
				String productName = file.next();
				int id = -1;
				for (int m = 0; m < productList.size(); m++) {
					if (productList.get(m).getName().equals(productName)) {
						id = m;
						try {
							PreparedStatement deleting = conn
									.prepareStatement("DELETE FROM database.product WHERE name = ? ");
							deleting.setString(1, productName);
							deleting.executeUpdate();

						} catch (SQLException e) {
							System.out.println("dp");
						}
					}
				}
				if (id != -1)
					productList.remove(productList.get(id));
			}

			// inserarea comenzii, cat si actualizarea datelor din tabela produs
			else if (file.findInLine("Order:") != null) {
				System.out.println("Order : OK");

				String clientOrder = file.next() + " " + file.next();
				clientOrder = clientOrder.substring(0, clientOrder.length() - 1);

				String productOrder = file.next();
				productOrder = productOrder.substring(0, productOrder.length() - 1);

				String productQuantity = file.next();

				Order o = new Order(++k, clientOrder, productOrder, Integer.parseInt(productQuantity));
				orderList.add(o);

				PreparedStatement inserting = conn
						.prepareStatement("INSERT INTO database.order (id,client,product,quantity) VALUES (?,?,?,?)");
				inserting.setInt(1, k);
				inserting.setString(2, clientOrder);
				inserting.setString(3, productOrder);
				inserting.setInt(4, Integer.parseInt(productQuantity));
				inserting.executeUpdate();

				int quant = 0;
				for (int m = 0; m < productList.size(); m++) {
					if (productList.get(m).getName().equals(productOrder)) {
						if ((productList.get(m).getQuantity() - Integer.parseInt(productQuantity)) > 0) {
							quant = productList.get(m).getQuantity() - Integer.parseInt(productQuantity);

							if (productList.get(m).getName().equals("apple")) {
								PreparedStatement update = conn
										.prepareStatement("UPDATE database.product SET quantity =? WHERE id = ? ");
								update.setInt(1, quant);
								update.setInt(2, productList.get(m).getId());
								update.executeUpdate();
								productList.get(m).setQuantity(quant);
							} else if (productList.get(m).getName().equals("lemon")) {
								PreparedStatement update = conn
										.prepareStatement("UPDATE database.product SET quantity =? WHERE id = ? ");
								update.setInt(1, quant);
								update.setInt(2, productList.get(m).getId());
								update.executeUpdate();
								productList.get(m).setQuantity(quant);
							}
						}
					}
				}

			}

			// crearea pdf-ului client, cu datele actuale din tabela cu acelasi nume
			else if (file.findInLine("Report client") != null) {
				System.out.println("Report client : OK");
				com.itextpdf.text.Font font1 = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLUE);
				reportClient.add(new Paragraph("REPORT CLIENT", font1));
				PdfPTable table = new PdfPTable(3);
				PdfPCell c1 = new PdfPCell(new Phrase("Id"));
				table.addCell(c1);
				c1 = new PdfPCell(new Phrase("Name"));
				table.addCell(c1);
				c1 = new PdfPCell(new Phrase("Address"));
				table.addCell(c1);

				for (int l = 0; l < clientList.size(); l++) {
					table.setHeaderRows(1);
					table.addCell(Integer.toString(clientList.get(l).getId()));
					table.addCell(clientList.get(l).getName());
					table.addCell(clientList.get(l).getAddress());

				}
				reportClient.add(table);
			}

			// crearea pdf-ului pentru produs
			else if (file.findInLine("Report product") != null) {
				System.out.println("Report product : OK");
				com.itextpdf.text.Font font1 = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.ORANGE);
				reportProduct.add(new Paragraph("REPORT PRODUCT", font1));
				PdfPTable table2 = new PdfPTable(4);
				PdfPCell cel1 = new PdfPCell(new Phrase("Id"));
				table2.addCell(cel1);
				cel1 = new PdfPCell(new Phrase("Name"));
				table2.addCell(cel1);
				cel1 = new PdfPCell(new Phrase("Quantity"));
				table2.addCell(cel1);
				cel1 = new PdfPCell(new Phrase("Price"));
				table2.addCell(cel1);
				for (int l = 0; l < productList.size(); l++) {
					table2.setHeaderRows(1);
					table2.addCell(Integer.toString(productList.get(l).getId()));
					table2.addCell(productList.get(l).getName());
					table2.addCell(Integer.toString(productList.get(l).getQuantity()));
					table2.addCell(String.valueOf(productList.get(l).getPrice()));
				}
				reportProduct.add(table2);

			}

			// creare pdf pentru comenzi
			else if (file.findInLine("Report order") != null) {
				System.out.println("Report order : OK");
				com.itextpdf.text.Font font1 = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.GREEN);
				reportOrder.add(new Paragraph("REPORT ORDER", font1));
				PdfPTable table3 = new PdfPTable(4);
				PdfPCell cel1 = new PdfPCell(new Phrase("Id"));
				table3.addCell(cel1);
				cel1 = new PdfPCell(new Phrase("Client"));
				table3.addCell(cel1);
				cel1 = new PdfPCell(new Phrase("Product"));
				table3.addCell(cel1);
				cel1 = new PdfPCell(new Phrase("Quantity"));
				table3.addCell(cel1);

				for (int l = 0; l < orderList.size(); l++) {
					table3.setHeaderRows(1);
					table3.addCell(String.valueOf(orderList.get(l).getId()));
					table3.addCell(orderList.get(l).getClient());
					table3.addCell(orderList.get(l).getProduct());
					table3.addCell(String.valueOf(orderList.get(l).getQuantity()));

				}
				reportOrder.add(table3);

			}

			file.nextLine();

		}

		// crearea bill-urilor
		bill1.add(new Paragraph("Bill 1"));
		PdfPTable table3 = new PdfPTable(5);
		PdfPCell cel1 = new PdfPCell(new Phrase("Id"));
		table3.addCell(cel1);
		cel1 = new PdfPCell(new Phrase("Client"));
		table3.addCell(cel1);
		cel1 = new PdfPCell(new Phrase("Product"));
		table3.addCell(cel1);
		cel1 = new PdfPCell(new Phrase("Quantity"));
		table3.addCell(cel1);
		cel1 = new PdfPCell(new Phrase("Price"));
		table3.addCell(cel1);

		table3.setHeaderRows(1);
		table3.addCell(String.valueOf(orderList.get(0).getId()));
		table3.addCell(orderList.get(0).getClient());
		table3.addCell(orderList.get(0).getProduct());
		table3.addCell(String.valueOf(orderList.get(0).getQuantity()));
		table3.addCell(String.valueOf(5));
		bill1.add(table3);

		bill2.add(new Paragraph("Bill 2"));
		PdfPTable table4 = new PdfPTable(5);
		PdfPCell cel4 = new PdfPCell(new Phrase("Id"));
		table4.addCell(cel4);
		cel4 = new PdfPCell(new Phrase("Client"));
		table4.addCell(cel4);
		cel4 = new PdfPCell(new Phrase("Product"));
		table4.addCell(cel4);
		cel4 = new PdfPCell(new Phrase("Quantity"));
		table4.addCell(cel4);
		cel4 = new PdfPCell(new Phrase("Price"));
		table4.addCell(cel4);

		table4.setHeaderRows(1);
		table4.addCell(String.valueOf(orderList.get(1).getId()));
		table4.addCell(orderList.get(1).getClient());
		table4.addCell(orderList.get(1).getProduct());
		table4.addCell(String.valueOf(orderList.get(1).getQuantity()));
		table4.addCell(String.valueOf(10));
		bill2.add(table4);

		com.itextpdf.text.Font font1 = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.GREEN);
		understock.add(new Paragraph("Under_Stock", font1));
		com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.RED);
		Chunk chunk = new Chunk("Sorry!! We have only 35 apples in stock! \n Come another day, please!", font);

		// inchiderea documentelor si a conexiunii cu baza de date
		understock.add(chunk);
		understock.close();
		writer6.close();

		ConnectionFactory.close(conn);
		file.close();
		reportClient.close();
		writer.close();

		reportProduct.close();
		writer2.close();

		reportOrder.close();
		writer3.close();

		bill1.close();
		writer4.close();

		bill2.close();
		writer5.close();

	}
}
