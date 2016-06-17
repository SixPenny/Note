package com.dylan.propertyManage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by dylanliu on 16/6/17.
 */
public class PropertyManage implements PropertyManageMBean {
    Properties properties = null;
    String source;

    public PropertyManage(String source){
        this.source = source;
        this.properties = new Properties();
        loadSource();
    }

    @Override
    public String getProperty(String property) {
        return properties.getProperty(property,"not found");
    }

    @Override
    public void setProperty(String name, String value) {
        properties.setProperty(name,value);
    }

    @Override
    public Enumeration keys() {
        return properties.propertyNames();
    }

    @Override
    public void setSource(String source) {
        this.source = source;
        loadSource();
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public void loadSource() {
        try {
            File file = new File(source);
            System.out.println(file.exists());
            FileInputStream fis = new FileInputStream(source);
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
