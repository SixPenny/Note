package com.dylan.helloworld;


import com.sun.jdmk.comm.HtmlAdaptorServer;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

/**
 * Created by dylanliu on 16/6/15.
 */
public class HelloAgent {
    public HelloAgent(){
        MBeanServer mbs = MBeanServerFactory.createMBeanServer("HelloAgent");

        HelloWorldMBean hello = new HelloWorld();

        HtmlAdaptorServer htmlAdaptorServer = new HtmlAdaptorServer();

        try {
            ObjectName objectName = new ObjectName("HelloAgent:name=world");
            ObjectName adaptorName = new ObjectName("HellopAgent:name=htmlAgent,port=8090");

            mbs.registerMBean(hello,objectName);

            htmlAdaptorServer.setPort(9092);
            mbs.registerMBean(htmlAdaptorServer,adaptorName);

            htmlAdaptorServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        System.out.println("Hello agent starting");
        HelloAgent ha = new HelloAgent();
        System.out.println("Hello agent started ");

    }
}
