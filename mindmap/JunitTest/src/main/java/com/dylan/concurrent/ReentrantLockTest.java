package com.dylan.concurrent;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

  public static void main() throws InterruptedException{
    ReentrantLock lock = new ReentrantLock();
    lock.lockInterruptibly();
    try{
      //do something
    }finally{
      if(lock.isHeldByCurrentThread()){
        lock.unlock();
      }
    }
  }
}
