package com.dylan.concurrent.future;

import java.util.concurrent.Callable;

public class DefaultFuture<T> implements Future<T>{

  /**
   * 标示任务是否已经完成
   */
  private volatile boolean isDone = false;
  /**
   * 任务执行结果
   */
  private volatile T result ;
  public synchronized T get() {
    try{
      if(!isDone){
        wait();
      }
      return result;
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }

  public synchronized void set(T result) {
    try{
      if(isDone){
        notifyAll();
      }
      this.result = result;
      this.isDone = true;
      notifyAll();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void submit(final Callable<T> task) {
    //这里直接使用线程模拟，真正生产上是要使用线程池的
    Thread thread = new Thread(new Runnable(){
      public void run(){
        T result = null;
        try {
          result = task.call();
//          set(result);//如果系统抛出异常将导致主线程一直阻塞
        } catch (Exception e) {
          e.printStackTrace();
        }
        set(result);
      }
    });
    thread.start();
  }

  public void submit(final Runnable task) {
    Thread thread = new Thread(new Runnable(){
      public void run(){
        try{
          task.run();
        }catch(Exception e){
          e.printStackTrace();
        }
        set(null);
      }
    });
    thread.start();
  }

}
