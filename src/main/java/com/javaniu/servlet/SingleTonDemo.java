package com.javaniu.servlet;

/**
 * @author Lzf
 * @create 2019-07-24 17:48
 * @Description:
 */
public class SingleTonDemo {

    private static volatile SingleTonDemo instance = null;

    private SingleTonDemo(){
        System.out.println(Thread.currentThread().getName() +"\t 只能执行一次");
    }

    //双重检索机制
    public static SingleTonDemo getInstance(){
        if(instance == null){
            synchronized(SingleTonDemo.class){ //类锁：拦截所有线程，同步代码块
                if(instance == null){
                    instance = new SingleTonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //单线程下
        //System.out.println(SingleTonDemo.getInstance() == SingleTonDemo.getInstance());
        //System.out.println(SingleTonDemo.getInstance() == SingleTonDemo.getInstance());
        //System.out.println(SingleTonDemo.getInstance() == SingleTonDemo.getInstance());

        //多线程下
        for(int i = 1; i <= 10; i++){
            new Thread(() ->{
                SingleTonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
