package com.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.print.DocFlavor.INPUT_STREAM;

public class Utils {
	
	private static Utils manager;
	private static final Properties prop = new Properties() ;
	private  Utils() throws IOException {		
		InputStream inputStream = Utils.class.getResourceAsStream( "/Adddata.properties");
		prop.load(inputStream);
		
	}
	
	public static Utils getInstance() {
		if(manager == null) {
			synchronized (Utils.class) {
				try {
					manager = new Utils();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}					
		return manager;		
	}
	
	public String geString(String key){
		return System.getProperty(key, prop.getProperty(key));
		
	}

}
