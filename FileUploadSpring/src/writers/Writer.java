package writers;

import java.util.List;
import java.util.Map;

public interface Writer {
	public void write(Map<Integer,List<String>> data);
	public void sortWrite(Map<Integer,List<String>> data,int sortOn,String dir);
}
