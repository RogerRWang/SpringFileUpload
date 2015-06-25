package writers;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class HTMLWriter implements Writer{
	private PrintWriter writer=null;
	@Override
	public void write(Map<Integer, List<String>> data) {
		// print out data to console
		printToPage(data,writer);
	}
	
	public Map<Integer,List<String>> returnUnsorted(Map<Integer, List<String>> data)
	{
		return data;
	}
	public Map<Integer,List<String>> returnSorted(Map<Integer, List<String>> data, int sortOn, String dir)
	{
		Map<Integer, List<String>> sortedMap=null;
		switch(sortOn)
		{
			case 0: //sort on uid
			{
				//TreeMap automatically sorts on key, so just push Map into TreeMap
				
				if(dir.equals("ASC"))
					sortedMap = new TreeMap<Integer, List<String>>(data);
				else
				{
					sortedMap = new TreeMap<Integer, List<String>>(Collections.reverseOrder());
					sortedMap.putAll(data);
				}
				break;
			}
			case 1: //sort on Fname
			{
				if(dir.equals("ASC"))
					sortedMap = sortByValue(data,1,"ASC");
				else
					sortedMap = sortByValue(data,1,"DESC");
				break;
			}
			case 2: //sort on Lname
			{
				if(dir.equals("ASC"))
					sortedMap = sortByValue(data,2,"ASC");
				else
					sortedMap = sortByValue(data,2,"DESC");
				break;
			}
			case 3: //sort on Zip
			{
				if(dir.equals("ASC"))
					sortedMap = sortByValue(data,7,"ASC");
				else
					sortedMap = sortByValue(data,7,"DESC");
				break;
			}
			case 4: //sort on State
			{
				if(dir.equals("ASC"))
					sortedMap = sortByValue(data,6,"ASC");
				else
					sortedMap = sortByValue(data,6,"DESC");
				break;
			}
			default:
			{
				System.out.println("Did not specify valid column to sort on");
				break;
			}
		}
		return sortedMap;
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
				
				printToPage(sortedMap,writer);
				break;
			}
			case 1: //sort on Fname
			{
				Map<Integer, List<String>> sortedMap;
				if(dir.equals("ASC"))
					sortedMap = sortByValue(data,1,"ASC");
				else
					sortedMap = sortByValue(data,1,"DESC");
				printToPage(sortedMap,writer);
				break;
			}
			case 2: //sort on Lname
			{
				Map<Integer, List<String>> sortedMap;
				if(dir.equals("ASC"))
					sortedMap = sortByValue(data,2,"ASC");
				else
					sortedMap = sortByValue(data,2,"DESC");
				printToPage(sortedMap,writer);
				break;
			}
			case 3: //sort on Zip
			{
				Map<Integer, List<String>> sortedMap;
				if(dir.equals("ASC"))
					sortedMap = sortByValue(data,7,"ASC");
				else
					sortedMap = sortByValue(data,7,"DESC");
				printToPage(sortedMap,writer);
				break;
			}
			case 4: //sort on State
			{
				Map<Integer, List<String>> sortedMap;
				if(dir.equals("ASC"))
					sortedMap = sortByValue(data,6,"ASC");
				else
					sortedMap = sortByValue(data,6,"DESC");
				printToPage(sortedMap,writer);
				break;
			}
			default:
			{
				System.out.println("Did not specify valid column to sort on");
				break;
			}
		}
		
	}
	
	private static LinkedHashMap<Integer,List<String>> sortByValue(Map<Integer,List<String>> unsortedData, final int col, final String dir)
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
	
	private static void printToPage(Map<Integer,List<String>> input,PrintWriter out)
	{
		out.print("<h2> Data </h2>");
		for(Entry<Integer, List<String>> entry : input.entrySet()) {
		    Integer key = entry.getKey();
		    List<String> value = entry.getValue();
		    
		    //write data to Page
		   out.println("<li>" + key+","+value.get(0)+","+value.get(1)+","+value.get(2)+","+
		    		value.get(3)+","+value.get(4)+","+value.get(5)+","+value.get(6)+","+
		    		value.get(7)+","+value.get(8) + "</li>");
		}
	}
	
	//constructor
	public HTMLWriter()
	{
		//nothing to do
	}
}
