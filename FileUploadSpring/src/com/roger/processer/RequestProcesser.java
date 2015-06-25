package com.roger.processer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.roger.DAO.UserJDBCTemplate;
import com.roger.DAO.UsersDAO;

import readers.TextfileReader;
import writers.DbWriter;
import writers.HTMLWriter;
import writers.TextfileWriter;
import app.User;

public class RequestProcesser {
	
	private final String UPLOAD_DIRECTORY = "C:\\Users\\wangr\\workspaceEE\\FileUploadSpring\\src\\";
	
	@Autowired
	public UsersDAO userJDBCTemplate;
	
	public RequestProcesser()
	{
		//nothing to do
	}
	public ModelMap doUpload(MultipartFile file, ModelMap model)
	{
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(UPLOAD_DIRECTORY+file.getOriginalFilename())));
                stream.write(bytes);
                stream.close();
                model.addAttribute("message","You successfully uploaded " + file.getOriginalFilename() + "!");
                model.addAttribute("filename",file.getOriginalFilename());
                return model;
            } catch (Exception e) {
            	model.addAttribute("You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
                return model;
            }
        } else {
        	model.addAttribute("You failed to upload " + file.getOriginalFilename() + " because the file was empty.");
            return model;
        }
	}
	
	public List<User> doWrite(String filename, 
							List<String> inputoptions,
						    List<String> outputoptions,
						    List<String> sortingoptions,
						    String sortcol) throws FileNotFoundException
	{
   		int sortindex=0;
   		boolean fileinput=false;
   		boolean dbinput=false;
   		boolean fileoutput=false;
   		boolean dboutput=false;
   		boolean topageoutput=false;
   		boolean sorted=false;
   		boolean asc=false;
   		boolean desc=false;
   		
   		for(String s : inputoptions)
   		{
   			if(s.equals("File"))
   				fileinput=true;
   			if(s.equals("Db"))
   				dbinput=true;
   		}
   		for(String t : outputoptions)
   		{
   			if(t.equals("File"))
   				fileoutput=true;
   			if(t.equals("Db"))
   				dboutput=true;
   			if(t.equals("ToPage"))
   				topageoutput=true;
   		}
   		if(sortingoptions!=null)
   		{
   			for(String u : sortingoptions)
   			{
   				if(u.equals("Sorted"))
   					sorted=true;
   				if(u.equals("ASC"))
   					asc=true;
   				if(u.equals("DESC"))
   					desc=true;
   			}
   		}
   		if(sortcol!=null)
   		{
   			if(sortcol.equals("uid"))
   				sortindex=0;
   			else if(sortcol.equals("firstname"))
   				sortindex=1;
   			else if(sortcol.equals("lastname"))
   				sortindex=2;
   			else if(sortcol.equals("zip"))
   				sortindex=3;
   			else
   				sortindex=4;
   		}
   		
   		
   		//calling old java code
   		//Get Properties
   		Properties prop = new Properties();
   		InputStream input = new FileInputStream("C:\\Users\\wangr\\workspaceEE\\FileUploadSpring\\src\\config.properties");
   		String URL="";
   		String USER="";
   		String PASSWORD="";
   		try {
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
   		Map<Integer,List<String>> data = new LinkedHashMap<Integer,List<String>>();
   		if(fileinput==true)
   		{
   			TextfileReader reader = new TextfileReader(UPLOAD_DIRECTORY+filename);
   			data = reader.read(",");
   		}
   		
   		
   		
   		if(fileoutput==true)
   		{
   			TextfileWriter writer = new TextfileWriter(UPLOAD_DIRECTORY+"output.txt");
   			if(sorted)
   			{
   				if(asc)
   				{
   					writer.sortWrite(data,sortindex, "ASC");
   				}
   				if(desc)
   				{
   					writer.sortWrite(data,sortindex,"DESC");
   				}
   			}
   			else
   			{
   				writer.write(data);
   			}
   		}
   		
   		if(dboutput==true)
   		{
//   			try {
//   				Connection conn = getConnection(URL,USER,PASSWORD);
//   				DbWriter writer = new DbWriter(conn);
//   				writer.write(data);
//   				conn.close();
//   			} catch (SQLException e) {
//   				// TODO Auto-generated catch block
//   				e.printStackTrace();
//   			}
   			userJDBCTemplate.write(data);
   		}
   		
   		List<User> userList = new ArrayList<User>();
   		if(topageoutput==true)
   		{
   			Map<Integer,List<String>> resultData = new LinkedHashMap<Integer,List<String>>();
   			HTMLWriter writer = new HTMLWriter();
   			if(sorted)
   			{
   				if(asc)
   				{
   					resultData = writer.returnSorted(data,sortindex, "ASC");
   				}
   				if(desc)
   				{
   					resultData = writer.returnSorted(data,sortindex,"DESC");
   				}
   			}
   			else
   			{
   				resultData = writer.returnUnsorted(data);
   			}
   			for(Entry<Integer, List<String>> entry : resultData.entrySet()) {
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
   		}
   		return userList;
	}
	 private static Connection getConnection(String URL,String USER,String PASSWORD) throws SQLException{
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
