package com.dylan.agent;


import com.dylan.helloworld.HelloWorld;
import com.dylan.helloworld.HelloWorldMBean;
import com.dylan.propertyManage.PropertyManage;
import com.sun.jdmk.comm.HtmlAdaptorServer;
import com.sun.jdmk.comm.RmiConnectorServer;

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
            mbs.registerMBean(hello,objectName);

            ObjectName adaptorName = new ObjectName("HelloAgent:name=htmlAgent,port=8090");
            htmlAdaptorServer.setPort(9093);
            mbs.registerMBean(htmlAdaptorServer,adaptorName);


            ObjectName property = new ObjectName("HelloAgent:name=propertyManage");
            PropertyManage propertyManage = new PropertyManage("/Users/dylanliu/Downloads/JMXAction/src/com/dylan/propertyManage/message.properties");
            mbs.registerMBean(propertyManage,property);

            RmiConnectorServer rmi = new RmiConnectorServer();
            ObjectName rmiName = new ObjectName("HelloAgent:name=rmiServer");
            mbs.registerMBean(rmi,rmiName);

            rmi.setPort(8999);
            rmi.start();
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
