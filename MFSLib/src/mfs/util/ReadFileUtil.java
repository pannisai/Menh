package mfs.util;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFileUtil {
	
	public static String read(String fileName){
		StringBuffer sb = new StringBuffer();
		try{
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = in.readLine()) != null){
			    sb.append(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
}
