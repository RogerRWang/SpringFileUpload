package com.roger.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.transaction.annotation.Transactional;

import app.Main;
import app.User;

@Transactional(readOnly = false, rollbackFor = DataAccessException.class, timeout=30)
public class UserJDBCTemplate implements UsersDAO{
	//private DataSource dataSource;
	private JdbcOperations jdbcTemplateObject; //wired in XML
	private String WRITEQUERY1;
	private String WRITEQUERY2;
	private String WRITEQUERY3;
	
	@Override
	public void setDataSource(DataSource ds) {
		//this.dataSource = ds;
	}
	
	@Override
	public void write(Map<Integer,List<String>> data) {
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
		    
		    try {
		    	String SQL = WRITEQUERY1 + Tablename+" "+WRITEQUERY2 + seq + WRITEQUERY3;
			    jdbcTemplateObject.update(SQL,USR_FIRST_NAME,USR_LAST_NAME,STREET1,STREET2,CITY,STATE_CODE,ZIP_CODE,CREATED_DATE,MODIFIED_DATE);
		    } catch (DataAccessException e){
		    	throw e;
		    }
		    
		}
	}

	@Override
	public User getUser(Integer uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> listUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer uid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Integer uid) {
		// TODO Auto-generated method stub
		
	}
	
	public void setJdbcTemplateObject(JdbcOperations jdbcTemplateObject)
	{
		this.jdbcTemplateObject = jdbcTemplateObject;
	}
	public JdbcOperations getJdbcTemplateObject()
	{
		return jdbcTemplateObject;
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
}
