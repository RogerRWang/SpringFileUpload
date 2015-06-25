package writers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import app.Main;

public class DbWriter implements Writer{

	private static Connection dbConnection=null;
	private String WRITEQUERY1;
	private String WRITEQUERY2;
	private String WRITEQUERY3;
	
	@Override
	public void write(Map<Integer, List<String>> data) {
		getProps();
		
		String Tablename = "TRNG_USER_ADDRS_INFO";
		String seq = "TRNG_USER_ADDRS_INFO_SEQ";
		Long ZIP_CODE;
		String USR_FIRST_NAME,USR_LAST_NAME,STREET1,STREET2,CITY,STATE_CODE;
		Date CREATED_DATE = null,MODIFIED_DATE = null;
		for(Entry<Integer, List<String>> entry : data.entrySet()) {
		    //Integer key = entry.getKey();
		    List<String> value = entry.getValue();

		    USR_FIRST_NAME = value.get(0);
		    USR_LAST_NAME = value.get(1);
		    STREET1 = value.get(2);
		    STREET2 = value.get(3);
		    CITY = value.get(4);
		    STATE_CODE = value.get(5);
		    ZIP_CODE = Long.parseLong(value.get(6));
		    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    try {
		        Date sqlDate = new Date(dateFormat.parse(value.get(7)).getTime());
		        CREATED_DATE = sqlDate;
				Calendar cal = Calendar.getInstance();
				sqlDate = new Date(dateFormat.parse(dateFormat.format(cal.getTime())).getTime());
				MODIFIED_DATE = sqlDate;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    //write date to database
		    PreparedStatement writeDataStmt = null;
		    String writeDataSql = "";
		    try {
		    		writeDataSql = WRITEQUERY1+Tablename+" "+
					    			WRITEQUERY2+seq+WRITEQUERY3;
		    	//System.out.println(writeDataSql);
				writeDataStmt = dbConnection.prepareStatement(writeDataSql);
				writeDataStmt.setString(1,USR_FIRST_NAME);
				writeDataStmt.setString(2,USR_LAST_NAME);
				writeDataStmt.setString(3,STREET1);
				writeDataStmt.setString(4,STREET2);
				writeDataStmt.setString(5,CITY);
				writeDataStmt.setString(6,STATE_CODE);
				writeDataStmt.setLong(7,ZIP_CODE);
				writeDataStmt.setDate(8,CREATED_DATE);
				writeDataStmt.setDate(9,MODIFIED_DATE);
				
				writeDataStmt.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(writeDataStmt!=null)
				{
					try {
						writeDataStmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}

	@Override
	public void sortWrite(Map<Integer, List<String>> data, int sortOn, String dir) {
		write(data); //doesn't matter what order you insert into a database
	}
	
	private void getProps()
	{
		Properties prop = new Properties();
		InputStream input = null;
		WRITEQUERY1="";
		WRITEQUERY2="";
		WRITEQUERY3="";
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
			WRITEQUERY1 = prop.getProperty("WRITEQUERY1");
			WRITEQUERY2 = prop.getProperty("WRITEQUERY2");
			WRITEQUERY3 = prop.getProperty("WRITEQUERY3");
	 
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
	}
	
	public DbWriter(Connection c)
	{
		dbConnection = c;
	}
}
