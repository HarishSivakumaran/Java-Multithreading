package FutureAndCallable;

import java.util.Random;
import java.util.concurrent.*;

// this is basically used when you want to return some data from your thread
public class LearnFutureCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future =  executorService.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                Random random = new Random();
                int duration = random.nextInt(4000);

                System.out.println("Starting......");
                Thread.sleep(duration); // simulating work
                System.out.println("finished....");

                return duration;
            }
        });


        executorService.shutdown();

        System.out.println("Result is: " + future.get());

    }
}
