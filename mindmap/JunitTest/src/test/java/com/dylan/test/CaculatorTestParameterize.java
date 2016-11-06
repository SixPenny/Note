package com.dylan.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import junit.framework.Assert;

@RunWith(value=Parameterized.class)
public class CaculatorTestParameterize {

  private double result;
  private double one;
  private double two;
  
  @Parameters
  public static Collection<Double[]> parameters(){
    return Arrays.asList(new Double[][]{
        {3d,2d,1d},
        {4d,3d,1d}
    });
  }
  public CaculatorTestParameterize(double result,double one,double two){
    this.result = result;
    this.one = one;
    this.two = two;
  }
  
  @Test
  public void testAddMethod(){
    Caculator ca = new Caculator();
    Assert.assertEquals(result, ca.add(one, two));
    System.out.println("case success");
  }
}
