package com.dylan.helloworld;

/**
 * Created by dylanliu on 16/6/15.
 */
public interface HelloWorldMBean {
    /**
     * set the greeting sentence
     * @param greeting
     */
    public void setGreeting(String greeting);

    /**
     * get the greeting sentence
     * @return
     */
    public String getGreeting();

    /**
     * print the greeting to who
     */
    public String printGreeting();

    /**
     * set the greet to who
     * @param greetingName
     */
    public void setGreetingName(String greetingName);

    /**
     * get greet person name
     * @return
     */
    public String getGreetingName();
}
