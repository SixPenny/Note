package com.dylan.test;

import org.junit.Test;

import junit.framework.Assert;

public class CaculatorTest {

  @Test
  public void testAdd(){
    Caculator ca = new Caculator();
    double result = ca.add(2d, 2.5d);
    Assert.assertEquals(3.5d, result,1);
  }
}
