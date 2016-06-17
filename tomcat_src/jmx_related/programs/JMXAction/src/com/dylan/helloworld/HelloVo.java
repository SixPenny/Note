package com.dylan.helloworld;

import com.sun.org.apache.xml.internal.utils.SerializableLocatorImpl;

import java.io.Serializable;

/**
 * Created by dylanliu on 16/6/16.
 */
public class HelloVo implements Serializable{

    private String greeting;
    private String name;


    public String getGreeting() {
        return greeting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
