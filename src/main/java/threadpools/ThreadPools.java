package threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPools {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for(int i = 0; i < 8; i++) {
            executorService.submit(new Processor(i));
        }
        executorService.shutdown();
        System.out.println("all tasks submitted");

    }
}

