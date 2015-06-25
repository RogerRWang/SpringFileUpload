package readers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;
import java.util.Vector;
import java.sql.PreparedStatement;

import app.Main;

@SuppressWarnings("unused")
public class DbReader implements Reader {
	
	private static Map<Integer,List<String>> Users;
	private static Connection dbConnection=null;
	private String READQUERY;
	
	@Override
	public LinkedHashMap<Integer, List<String>> read()
	{
		getProps();
		
		String Tablename = "TRNG_ADDRS_INFO";
		Users = new LinkedHashMap<Integer,List<String>>();
		//data variables
		Long ADDRS_INFO_SID,ZIP_CODE;
		String FIRST_NAME,LAST_NAME,STREET1,STREET2,CITY,STATE_CODE;
		Date CREATED_DATE,MODIFIED_DATE;
		
		//query db
		ResultSet queryResult = null;
		PreparedStatement getDataStmt = null;
		
		try {
			String getDataSql = READQUERY + Tablename;
			//System.out.println(getDataSql);
			getDataStmt = dbConnection.prepareStatement(getDataSql);
			queryResult = getDataStmt.executeQuery();
			while(queryResult.next())
			{
				ADDRS_INFO_SID = queryResult.getLong(1);
				FIRST_NAME = queryResult.getString(2);
				LAST_NAME = queryResult.getString(3);
				STREET1 = queryResult.getString(4);
				STREET2 = queryResult.getString(5);
				CITY = queryResult.getString(6);
				STATE_CODE = queryResult.getString(7);
				ZIP_CODE = queryResult.getLong(8);
				String ZIP_CODE_STRING = ZIP_CODE.toString();
				CREATED_DATE = queryResult.getDate(9);
				MODIFIED_DATE = queryResult.getDate(10);
				String CREATED_DATE_STRING = CREATED_DATE.toString();
				String MODIFIED_DATE_STRING = MODIFIED_DATE.toString();
				
				//put data into hashmap
				Users.put(ADDRS_INFO_SID.intValue(), new ArrayList<String>(
					    Arrays.asList(FIRST_NAME,LAST_NAME,STREET1,STREET2,CITY,STATE_CODE,ZIP_CODE_STRING,CREATED_DATE_STRING,MODIFIED_DATE_STRING)));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally //close result set and prepared statements
		{
			if(queryResult!=null)
			{
				try {
					queryResult.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(getDataStmt != null)
			{
				try {
					getDataStmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return (LinkedHashMap<Integer, List<String>>) Users;
	}
	
	private void getProps()
	{
		Properties prop = new Properties();
		InputStream input = null;
		READQUERY="";
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
			READQUERY = prop.getProperty("READQUERY");
	 
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
	
	public DbReader(Connection c)
	{
		dbConnection = c;
		Users = new LinkedHashMap<Integer,List<String>>();
	}
}
