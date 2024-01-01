package com.mysticalducks.bots.financeBot.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
	
	private Properties prop = new Properties();

	String fileName = "/config.properties";
	
    public PropertyManager(){
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream(fileName);
            prop.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public String getRestApiUrl() {
    	return getValue("restApiUrl");
    }

    public String getBotUsername() {
    	return getValue("botUsername");
    }
    
    public String getBotToken() {
    	return getValue("botToken");
    }
    
    private String getValue(String key){
        return this.prop.getProperty(key);
    }

}
