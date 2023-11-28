package ThreadInteruption;

import java.util.Scanner;

public class ThreadInteruption {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()) {
                    System.out.println("running...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        t1.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        t1.interrupt();



    }
}
