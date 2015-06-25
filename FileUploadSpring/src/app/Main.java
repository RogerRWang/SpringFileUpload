package app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Map.Entry;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;

import readers.DbReader;
import readers.TextfileReader;
import writers.ConsoleWriter;
import writers.DbWriter;
import writers.TextfileWriter;

@SuppressWarnings("unused")
public class Main {
	public static void main(String[] args)
	{
		//Get Properties
		Properties prop = new Properties();
		InputStream input = null;
		String URL="";
		String USER="";
		String PASSWORD="";
		try {
	 
			String filename = "config.properties";
    		input = Main.class.getClassLoader().getResourceAsStream(filename);
    		if(input==null){
    	            System.out.println("Sorry, unable to find " + filename);
    		    return;
    		}
			// load a properties file
			prop.load(input);
	 
			// get the property values
			URL = prop.getProperty("URL");
			USER = prop.getProperty("USER");
			PASSWORD = prop.getProperty("PASSWORD");
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//start of code
		List<User> userList = new ArrayList<User>();
		Map<Integer,List<String>> data = new LinkedHashMap<Integer,List<String>>();
		Scanner scanner = new Scanner(System.in);
		
		//-------------------------------------READING----------------------------------
		String readmode="";
		System.out.print("Enter File for File input or Db for Db input: ");
		readmode = scanner.nextLine();
		while(!(readmode.equals("File") || readmode.equals("Db")))
		{
			System.out.println("Input did not match options, try again");
			System.out.print("Enter File for File input or Db for Db input: ");
			readmode = scanner.nextLine();
		}
		
		if(readmode.equals("File")) //read from File
		{
			boolean validFile = false;
			while(!validFile)
			{
				String infile="";
				System.out.print("Enter file to read: ");
				infile = scanner.nextLine();
				while(!infile.endsWith(".txt"))
				{
					System.out.print("Enter filename ending with .txt since only\n "
							+ "textfilereader is implemented right now: ");
					infile = scanner.nextLine();
				}
				String delim="";
				System.out.print("Enter delimiter: ");
				delim = scanner.nextLine();
				try {
					TextfileReader reader = new TextfileReader(infile);
					validFile = true;
					data = reader.read(delim);
				} catch (FileNotFoundException e) {
					System.out.println("Could not find file");
				}
			}
		}
		
		if(readmode.equals("Db")) //read from Db
		{
			//establish Db connection
			try {
				Connection conn = getConnection(URL,USER,PASSWORD);
				DbReader reader = new DbReader(conn);
				data = reader.read();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//push map into list of User objects for easier manipulation for other tasks
		for(Entry<Integer, List<String>> entry : data.entrySet()) {
		    Integer key = entry.getKey();
		    List<String> value = entry.getValue();
		    
		    //create User and push into userList
		    String USR_FIRST_NAME = value.get(0);
		    String USR_LAST_NAME = value.get(1);
		    String STREET1 = value.get(2);
		    String STREET2 = value.get(3);
		    String CITY = value.get(4);
		    String STATE_CODE = value.get(5);
		    Long ZIP_CODE = Long.parseLong(value.get(6));
		    Date CREATED_DATE=null;
		    Date MODIFIED_DATE=null;
		    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    try {
		        java.sql.Date sqlDate = new java.sql.Date(dateFormat.parse(value.get(7)).getTime());
		        CREATED_DATE = sqlDate;
				Calendar cal = Calendar.getInstance();
				sqlDate = new java.sql.Date(dateFormat.parse(dateFormat.format(cal.getTime())).getTime());
				MODIFIED_DATE = sqlDate;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    userList.add(new User(Long.valueOf(key),USR_FIRST_NAME,USR_LAST_NAME,STREET1,STREET2,
		    								CITY,STATE_CODE,ZIP_CODE,CREATED_DATE,MODIFIED_DATE));
		}
		
		//-------------------------------------WRITING-----------------------------------------------------
		String writemode="";
		System.out.print("Enter Console for Console output or File for File output or Db for Db output: ");
		writemode = scanner.nextLine();
		while(!(writemode.equals("Console") || writemode.equals("File") || writemode.equals("Db")))
		{
			System.out.println("Input did not match options, try again");
			System.out.print("Enter Console for Console output or File for File output or Db for Db output: ");
			writemode = scanner.nextLine();
		}
		
		String sortmode="";
		System.out.print("Enter Sorted if you want sorted output, nothing otherwise: ");
		sortmode = scanner.nextLine();
		while(!(sortmode.equals("Sorted") || sortmode.equals("")))
		{
			System.out.println("Input did not match options, try again");
			System.out.print("Enter Sorted if you want sorted output, nothing otherwise: ");
			sortmode = scanner.nextLine();
		}
		int col=0;
		String dir="";
		if(sortmode.equals("Sorted"))
		{
			System.out.print("Input ASC or DESC: ");
			dir = scanner.nextLine();
			System.out.print("Input which column to sort on\n" +
							"0) uid, 1) Fname, 2) Lname, 3) Zip, 4) State\n" +
							"Column: ");
			col = Integer.parseInt(scanner.nextLine());
		}
		
		//ConsoleWriter
		if(writemode.equals("Console"))
		{
			ConsoleWriter writer = new ConsoleWriter();
			if(sortmode.equals(""))
			{
				writer.write(data);
			}
			else
			{
				if(dir.equals("ASC"))
					writer.sortWrite(data, col,"ASC");
				else if(dir.equals("DESC"))
					writer.sortWrite(data, col,"DESC");
				else
					System.out.println("No valid sorting direction specified");
			}
		}
		
		//TextfileWriter
		if(writemode.equals("File"))
		{
			String outfileName="";
			System.out.print("Enter name of file to output to: ");
			outfileName = scanner.nextLine();
			TextfileWriter filewriter = new TextfileWriter(outfileName);
			if(sortmode.equals(""))
			{
				filewriter.write(data);
			}
			else
			{
				if(dir.equals("ASC"))
					filewriter.sortWrite(data, col,"ASC");
				else if(dir.equals("DESC"))
					filewriter.sortWrite(data, col,"DESC");
				else
					System.out.println("No valid sorting direction specified");
			}
		}
		
		//DbWriter
		if(writemode.equals("Db"))
		{
			try {
				Connection conn = getConnection(URL,USER,PASSWORD);
				DbWriter writer = new DbWriter(conn);
				writer.write(data);
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		scanner.close();
	}
	
	//static String URL = "jdbc:oracle:thin:@204.22.48.12:1521:miclddev";
	//static String USER = "prdmmis";
	//static String PASSWORD = "prdmmis126";
	public static Connection getConnection(String URL,String USER,String PASSWORD) throws SQLException{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
