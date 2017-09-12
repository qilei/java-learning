package com.example.learning.java.multithread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 阻塞队列，实现生产者消费者模式
 */
public class ThreadDemo1 {

  public static void main(String[] args) {
    BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(5);
    Producer producer = new Producer(blockingQueue);
    Consumer consumer = new Consumer(blockingQueue);

    Thread thread = new Thread(producer);
    thread.setDaemon(true);
    thread.start();

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    for (int i = 0; i < 2; i++) {
      executorService.execute(consumer);
    }
    executorService.shutdown();
  }
}

class Producer implements Runnable {

  private BlockingQueue<Integer> blockingQueue;

  public Producer(BlockingQueue<Integer> blockingQueue) {
    this.blockingQueue = blockingQueue;
  }

  public void run() {
    try {
      for (int i = 0; i < 10; i++) {
        System.out.println("Producer value:" + i);
        blockingQueue.put(i);
      }
      blockingQueue.put(-1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class Consumer implements Runnable {

  private BlockingQueue<Integer> blockingQueue;

  public Consumer(BlockingQueue<Integer> blockingQueue) {
    this.blockingQueue = blockingQueue;
  }

  public void run() {
    boolean isDone = false;
    try {
      while (!isDone) {
        Integer value = blockingQueue.take();
        if (value == -1) {
          isDone = true;
        }else{
          Thread.sleep(1000);
          System.out.println(Thread.currentThread().getName() + " consumer value:" + value);
        }
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}


