package com.dylan.concurrent.future;

import java.util.concurrent.Callable;

/**
 * @ClassName: Future 
 * @Description: 简易future实现
 * @author: dylan
 * @date: 2016年11月29日 下午10:06:15
 */
public interface Future<T> {
  
  /**
   * @Title: get 
   * @Description: 获取执行结果
   * @author: Dylan
   * @return
   * @date: 2016年11月29日 下午10:07:40 
   * @return: T
   */
  public T get();
  
  /**
   * @Title: set 
   * @Description: 设置执行结果
   * @author: Dylan
   * @param result
   * @date: 2016年11月29日 下午10:10:09 
   * @return: void
   */
  public void set(T result);
  
  /**
   * @Title: submit 
   * @Description: 提交任务到框架中
   * @author: Dylan
   * @param task
   * @date: 2016年11月29日 下午10:11:33 
   * @return: void
   */
  public void submit(Callable<T> task);
  
  /**
   * @Title: submit 
   * @Description: 提交任务到框架中
   * @author: Dylan
   * @param task
   * @date: 2016年11月29日 下午10:12:39 
   * @return: void
   */
  public void submit(Runnable task);
}
