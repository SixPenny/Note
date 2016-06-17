package com.dylan.helloworld;

/**
 * Created by dylanliu on 16/6/15.
 */
public interface HelloWorldMBean {
    /**
     * set the greeting sentence
     * @param helloVo
     */
    public void setGreeting(HelloVo helloVo);

    /**
     * get the greeting sentence
     * @return
     */
    public HelloVo getGreeting();

    /**
     * print the greeting to who
     */
    public HelloVo printGreeting();
}
