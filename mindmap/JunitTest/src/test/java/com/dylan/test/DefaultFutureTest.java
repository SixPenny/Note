package com.dylan.test;

import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;

import com.dylan.concurrent.future.DefaultFuture;
import com.dylan.concurrent.future.Future;

import junit.framework.Assert;

public class DefaultFutureTest {

  private Future<String> taskFuture;

  @Before
  public void init(){
    taskFuture = new DefaultFuture<String>();
  }
  @Test
  public void testSubmit(){
    
    Callable<String> task1 = new Callable<String>(){
      public String call() throws InterruptedException{
        Thread.sleep(10000);
        return "hah";
      }
    };
    
    taskFuture.submit(task1);
    
    String result1 = taskFuture.get();
    Assert.assertEquals(result1, "hah");
  }
}
