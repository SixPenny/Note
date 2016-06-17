package com.dylan.helloworld;

/**
 * Created by dylanliu on 16/6/15.
 */
public class HelloWorld implements HelloWorldMBean {

    private HelloVo helloVo;
    public HelloWorld(){
        HelloVo helloVo =  new HelloVo();
        helloVo.setGreeting("hello");
        helloVo.setName("world");
        this.helloVo = helloVo;
    }

    @Override
    public void setGreeting(HelloVo helloVo) {
        this.helloVo = helloVo;
    }

    @Override
    public HelloVo getGreeting() {
        return this.helloVo;
    }

    @Override
    public HelloVo printGreeting() {
        System.out.println(helloVo.getGreeting() + " " + helloVo.getName());
        return helloVo;
    }
}
