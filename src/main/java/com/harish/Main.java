package com.harish;
import demo1.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        Demo1 t1 = new Demo1();
        Thread t2 = new Thread(new Demo2());
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10; i++) {
                    System.out.println("hello from inline runnable :" + i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        t1.start();t2.start();t3.start();
    }
}