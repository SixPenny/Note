package com.dylan.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest {

  public static class Soldier implements Runnable{

    String name;
    CyclicBarrier barrier;
    public Soldier(String name,CyclicBarrier barrier){
      this.name = name;
      this.barrier = barrier;
    }
    public void execise() throws InterruptedException{
      Thread.sleep(Math.round(Math.random()*2000));
      System.out.println(name+"完成工作！");
    }
    public void run() {
      try {
        //等待所以士兵到达
        barrier.await();
        execise();
        //等待所有士兵完成训练
        barrier.await();

      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        //某个任务抛出异常后，再等待已经没有意义
        e.printStackTrace();
      }
      
    }
  }
  public static class Action implements Runnable{
    int i;
    public Action(int i){
      this.i = i;
    }

    public void run(){
      System.out.println(i+"哈哈");
    }
  }
  public static void main(String...strings) throws InterruptedException{
    CyclicBarrier barrier = new CyclicBarrier(10, new Action(10));
    ExecutorService pool = Executors.newFixedThreadPool(10);
    for(int i=0 ;i<10 ; i++){
      pool.submit(new Soldier("士兵"+i,barrier));
    }
    pool.shutdown();
  }
}
