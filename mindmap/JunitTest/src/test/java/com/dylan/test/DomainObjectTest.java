package com.dylan.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DomainObjectTest {

  StringBuffer sb;
  
  @Before
  public void init(){
    sb = new StringBuffer();
  }
  
  @Test
  public void test1(){
    sb.append("111");
    System.out.println(sb.toString());
  }
  
  @Test
  public void test2(){
    sb.append("222");
    System.out.println(sb.toString());
  }
  
  @After
  public void destroy(){
    System.out.println(sb.toString());
    throw new RuntimeException();
  }
}
