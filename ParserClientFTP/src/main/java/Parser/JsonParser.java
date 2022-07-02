package Parser;

import java.net.URL;
import java.util.Scanner;
import java.net.HttpURLConnection;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.opencsv.CSVWriter;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPClient;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class JsonParser {

	public Transaction[] parseJsonFromURL(String Endpoint)  throws Exception {
		
		String StringJSON = "";
		
		// Fetch API Endpoint		
		URL url = new URL(Endpoint);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		// Perform GET Request
		conn.setRequestMethod("GET");
		conn.addRequestProperty("accept", "application/json");
		conn.connect();
		
		// Process API Response
		if(conn.getResponseCode() == 200)	{
			
			Scanner read = new Scanner(conn.getInputStream());
			
			while(read.hasNext())
				StringJSON += read.nextLine();
			
			read.close();
			
		}	conn.disconnect();
		
		JSONArray arr = new JSONArray(StringJSON);
		
		Transaction result[] = new Transaction[arr.length()];
		
		for(int i = 0; i < arr.length(); i++)	{
			JSONObject obj = arr.getJSONObject(i);
			
			String ID = obj.getString("id");
			boolean type = obj.getBoolean("type");
			double amount = obj.getDouble("amount");
			String currency = obj.getString("currency");
			JSONArray acc = obj.getJSONArray("accounts");
			String timestamp = obj.getString("timestamp");
			String accounts[] = { acc.getString(0), acc.getString(1) };
			
			result[i] = new Transaction(type, ID, currency, accounts, amount, timestamp);
			
		}
		
		return result;
	}
	
	public FTPClient remoteCsvWriter(Transaction[] data, String[] ftpcred) throws Exception	{
		
		System.out.println(ftpcred[5]);
		
		boolean isCsvType = ftpcred[5] == "csv";
		
		String[] fields = { "ID", "Type", "Currency", "Amount", "Done By", "Done To", "Timestamp" };
		
		String[][] values = new String[data.length][fields.length];
		
		for(int i = 0; i < data.length; i++)	{
			
			String ID = data[i].ID;
			String Type = data[i].type ? "true" : "false";
			String Currency = data[i].currency;
			String Amount = String.valueOf(data[i].amount);
			String DoneBy = data[i].accounts[0];
			String DoneTo = data[i].accounts[1];
			String Timestamp = data[i].timestamp.toString();
			
			String[] collection = { ID, Type, Currency, Amount, DoneBy, DoneTo, Timestamp };
			
			values[i] = collection;
		
		}
		
		if(isCsvType)	{
		
			// Writing JSON to a CSV File
			
			File file = new File("C:\\Users\\VP\\Desktop\\transactions.csv");
			
			FileWriter output = new FileWriter(file);
			
			CSVWriter writer = new CSVWriter(output);
			
			writer.writeNext(fields);
			
			for(int i = 0; i < data.length; i++)
				writer.writeNext(values[i]);
			
			writer.close();		
			
		}	else {
			
			// Write JSON to XLS / XLSX File
			
			Workbook wb = new HSSFWorkbook();
			
			Sheet sh =  wb.createSheet("Lookup");
			
			Row row = sh.createRow(0);
					
			for(int i = 0; i < fields.length; i++)
				row.createCell(i).setCellValue(fields[i]);
			
			for(int i = 0; i < data.length; i++)	{
				row = sh.createRow(i + 1);
				for(int j = 0; j < fields.length; j++)
					row.createCell(j).setCellValue(values[i][j]);
			}
			
			wb.write(new FileOutputStream("C:\\Users\\VP\\Desktop\\transactions.xls"));
			
			wb.close();
		}
		
		// Transferring CSV file to remote destination
		
		String host = ftpcred[0];
		String user = ftpcred[1];
		String pass = ftpcred[2];
		int port = Integer.parseInt(ftpcred[3]);
		
		FTPClient ftpClient = new FTPClient();
		
		try	{
			
			ftpClient.connect(host, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			ftpClient.makeDirectory("/" + ftpcred[4]);
				
			File localFile = new File("C:\\Users\\VP\\Desktop\\transactions." + ftpcred[5]);
			
			InputStream fileInputStream = new FileInputStream(localFile);
			
			boolean fileSent = ftpClient.storeFile(ftpcred[4] + "/something." + ftpcred[5], fileInputStream);
			
			System.out.println(fileSent ? "File Sent Successfully" : "File Transfer Failed");						
			
		}	catch(Exception e)	{	e.printStackTrace();	}
		
		return ftpClient;
	}
	
	public String buildDirectoryTree(FTPClient ftpClient) throws Exception {
		
		DirectoryTree dt = new DirectoryTree(ftpClient);
		
		FTPFile[] home = ftpClient.listDirectories();
		
		String directoryTree = "";
		
		for(int i = 0; i < home.length; i++)	{
			String out = dt.printDirectoryTree(home[i]);
			directoryTree += out;
		}
		
		return directoryTree;
	}
}
