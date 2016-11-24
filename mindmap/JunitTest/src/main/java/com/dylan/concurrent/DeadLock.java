package com.dylan.concurrent;

/**
 * @ClassName: DeadLock 
 * @Description: 模拟哲学家使用筷子吃饭问题，分析死锁
 * @author: Administrator
 * @date: 2016年11月24日 上午11:48:06
 */
public class DeadLock {

  String chopstick1 = "chopstick1";
  String chopstick2 = "chopstick2";
  
  class Eat implements Runnable{
    boolean first = true;
    Eat(boolean first){
      this.first = first;
    }
    public void run(){
      if(first){
        lock1();
      }else{
        lock2();
      }
      
    }
    public void lock1(){
      synchronized(chopstick1){
        System.out.println("get chopstick1");
        try{
          Thread.sleep(2000);
        }catch(Exception e){
          
        }
        synchronized(chopstick2){
          System.out.println("now first on is eating");
        }
      }
      
    }
    public void lock2(){
      synchronized(chopstick2){
        System.out.println("get chopstick2");
        try{
          Thread.sleep(2000);
        }catch(Exception e){
          
        }
        synchronized(chopstick1){
          System.out.println("now first on is eating");
        }
      }
    }
  }
  public static void main(String...strings){
    DeadLock lock = new DeadLock();
    Thread first = new Thread(lock.new Eat(true),"哲学家1");
    Thread second = new Thread(lock.new Eat(false),"哲学家2");
    
    first.start();
    second.start();
    
  }
}
