package readers;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TextfileReader implements Reader {

	private static BufferedReader in;
	private static Map<Integer,List<String>> Users;
	
	@Override
	public LinkedHashMap<Integer, List<String>> read() {
		return read(",");
	}
	
	public LinkedHashMap<Integer, List<String>> read(String delim) {
		// temp is the string buffer to parse later
		String temp="";
		
		//data variables
		Integer ADDRS_INFO_SID;
		String FIRST_NAME,LAST_NAME,STREET1,STREET2,CITY,STATE_CODE,ZIP_CODE,CREATED_DATE,MODIFIED_DATE;
		
		try {
			//read until end of file
			while((temp = in.readLine()) != null)
			{
				//parse temp and put data into hashmap
				//entries in map use Uid as unique key
				String[] splitString = temp.split(delim);
				ADDRS_INFO_SID = Integer.parseInt(splitString[0]);
				FIRST_NAME = splitString[1];
				LAST_NAME = splitString[2];
				STREET1 = splitString[3];
				STREET2 = splitString[4];
				CITY = splitString[5];
				STATE_CODE = splitString[6];
				ZIP_CODE = splitString[7];
				CREATED_DATE = splitString[8];
				MODIFIED_DATE = splitString[9];
				
				Users.put(ADDRS_INFO_SID, new ArrayList<String>(
					    Arrays.asList(FIRST_NAME,LAST_NAME,STREET1,STREET2,CITY,STATE_CODE,ZIP_CODE,CREATED_DATE,MODIFIED_DATE)));
			}
			//close FileReader
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return hashmap
		return (LinkedHashMap<Integer, List<String>>) Users;
	}
	
	//constructor
	public TextfileReader(String filename) throws FileNotFoundException
	{
		//create a FileReader object to read from the file
		in = new BufferedReader(new FileReader(filename));

		//initialize container for data from file
		Users = new LinkedHashMap<Integer, List<String>>();
	}
}
