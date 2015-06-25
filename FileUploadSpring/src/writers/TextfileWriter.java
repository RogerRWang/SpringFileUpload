package writers;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class TextfileWriter implements Writer{
	
	private static PrintWriter writer;
	
	@Override
	public void write(Map<Integer, List<String>> data) {
		writeToFile(data);
	}
	
	@Override
	public void sortWrite(Map<Integer, List<String>> data, int sortOn, String dir) {
		//sorting
		switch(sortOn)
		{
			case 0: //sort on uid
			{
				//TreeMap automatically sorts on key, so just push Map into TreeMap
				Map<Integer, List<String>> sortedMap;
				if(dir.equals("ASC"))
					sortedMap = new TreeMap<Integer, List<String>>(data);
				else
				{
					sortedMap = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
					sortedMap.putAll(data);
				}
				
				writeToFile(sortedMap);
				break;
			}
			case 1: //sort on Fname
			{
				LinkedHashMap<Integer, List<String>> sortedMap;
				if(dir.equals("ASC"))
					sortedMap = sortByValue(data,1,"ASC");
				else
					sortedMap = sortByValue(data,1,"DESC");
				writeToFile(sortedMap);
				break;
			}
			case 2: //sort on Lname
			{
				LinkedHashMap<Integer, List<String>> sortedMap;
				if(dir.equals("ASC"))
					sortedMap = sortByValue(data,2,"ASC");
				else
					sortedMap = sortByValue(data,2,"DESC");
				writeToFile(sortedMap);
				break;
			}
			case 3: //sort on Zip
			{
				LinkedHashMap<Integer, List<String>> sortedMap;
				if(dir.equals("ASC"))
					sortedMap = sortByValue(data,7,"ASC");
				else
					sortedMap = sortByValue(data,7,"DESC");
				writeToFile(sortedMap);
				break;
			}
			case 4: //sort on State
			{
				LinkedHashMap<Integer, List<String>> sortedMap;
				if(dir.equals("ASC"))
					sortedMap = sortByValue(data,6,"ASC");
				else
					sortedMap = sortByValue(data,6,"DESC");
				writeToFile(sortedMap);
				break;
			}
			default:
			{
				System.out.println("Did not specify valid column to sort on");
				break;
			}
		}
	}
	
	private static LinkedHashMap<Integer,List<String>> sortByValue(Map<Integer,List<String>> unsortedData, final int col,final String dir)
	{
		//inspired by: http://stackoverflow.com/questions/8119366/sorting-hashmap-by-values
		//Create intermediate list to sort by value
	    List<Entry<Integer, List<String>>> list = new LinkedList<Entry<Integer, List<String>>>(unsortedData.entrySet());

        // Sorting the list based on values (using custom comparator)
        Collections.sort(list, new Comparator<Entry<Integer, List<String>>>()
        {
            public int compare(Entry<Integer, List<String>> o1, Entry<Integer, List<String>> o2)
            {
            	String i="";
            	String j="";
            	if(dir.equals("ASC"))
            	{
            		i = o2.getValue().get(col-1); //col-1 b/c uid is key and not part of value list
    	            j = o1.getValue().get(col-1);
            	}
            	else if(dir.equals("DESC"))
            	{
            		j = o2.getValue().get(col-1); //col-1 b/c uid is key and not part of value list
    	            i = o1.getValue().get(col-1);
            	}
            	
	            if (i.compareTo(j) < 0) {
	                return 1;
	            } else if (i.compareTo(j) > 0) {
	                return -1;
	            } else {
	                return 0;
	            }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<Integer, List<String>> sortedMap = new LinkedHashMap<Integer, List<String>>();
        for (Entry<Integer, List<String>> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return (LinkedHashMap<Integer, List<String>>) sortedMap;
    }
	
	private static void writeToFile(Map<Integer,List<String>> input)
	{
		//iterate through hashmap
		for(Entry<Integer, List<String>> entry : input.entrySet()) {
		    Integer key = entry.getKey();
		    List<String> value = entry.getValue();
		    
		    //write data to file
		    writer.println(key+","+value.get(0)+","+value.get(1)+","+value.get(2)+","+
		    		value.get(3)+","+value.get(4)+","+value.get(5)+","+value.get(6)+","+
		    		value.get(7)+","+value.get(8));
		}
		//close PrintWriter
		writer.close();
	}
	
	//constructor
	public TextfileWriter(String outfileName)
	{
		try {
			//create a PrintWriter to file specified by outfileName
			writer = new PrintWriter(outfileName, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
