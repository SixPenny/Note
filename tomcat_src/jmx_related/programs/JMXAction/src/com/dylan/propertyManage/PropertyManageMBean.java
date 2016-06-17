package com.dylan.propertyManage;

import java.util.Enumeration;

/**
 * Created by dylanliu on 16/6/17.
 */
public interface PropertyManageMBean {

    public String getProperty(String property);
    public void setProperty(String name,String value);
    public Enumeration keys();
    public void setSource(String source);
    public String getSource();
    public void loadSource();
}
