package com.javaniu.servlet;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Lzf
 * @create 2019-08-06 10:47
 * @Description:
 */
class MyThread implements Callable{
    @Override
    public Object call() throws Exception {  //高并发下，如果其中有2个出错，可以抛异常
        System.out.println("111111111");
        return 1024;
    }
}

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
        new Thread(futureTask,"AA").start();
        Integer integer = futureTask.get();//永远放最后，如果该线程计算得比较慢，会阻塞其他线程。
        System.out.println(integer);
    }
}
