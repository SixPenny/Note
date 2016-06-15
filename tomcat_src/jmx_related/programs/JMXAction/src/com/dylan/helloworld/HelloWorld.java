package com.dylan.helloworld;

/**
 * Created by dylanliu on 16/6/15.
 */
public class HelloWorld implements HelloWorldMBean {
    private String greeting;
    private String name;

    public HelloWorld(){
        this.greeting = "Hello ";
        this.name = " World";
    }
    @Override
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    @Override
    public String getGreeting() {
        return greeting;
    }

    @Override
    public String printGreeting() {
        System.out.println(greeting + " " + name);
        return greeting+" "+name;
    }

    @Override
    public void setGreetingName(String greetingName) {
        this.name = greetingName;
    }

    @Override
    public String getGreetingName() {
        return this.name;
    }
}
