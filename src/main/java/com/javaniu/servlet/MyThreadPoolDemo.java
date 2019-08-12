package com.javaniu.servlet;

import java.util.concurrent.*;

/**
 * @author Lzf
 * @create 2019-08-07 15:49
 * @Description:
 */
@SuppressWarnings("all")
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(3),
                Executors.defaultThreadFactory(),
                //1、默认抛出异常
                //new ThreadPoolExecutor.AbortPolicy()
                //2、回退调用者
                //new ThreadPoolExecutor.CallerRunsPolicy()
                //3、处理不来的不处理
                //new ThreadPoolExecutor.DiscardOldestPolicy()
                //4、处理最大数8
                new ThreadPoolExecutor.DiscardPolicy()
        );

        //模拟10个用户来办理业务。每个用户一个请求
        try {
            for (int i = 1; i <=5 ; i++) {
                threadPoolExecutor.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 来办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }
    }

    //基础线程池，不要直接使用(LinkedBlockingQueue 最大数是21亿 服务器抗不住，OOM)
    private static void basePool() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);//一池5个处理线程
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();//一池1个处理线程
        ExecutorService executorService2 = Executors.newCachedThreadPool();//一池n个处理线程(自动扩容)

        //模拟10个用户来办理业务。每个用户一个请求
        try {
            for (int i = 1; i <=5 ; i++) {
                executorService.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 来办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
