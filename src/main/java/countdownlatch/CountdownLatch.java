package countdownlatch;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Processor implements Runnable {
    CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("Started Processing");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        latch.countDown();
    }
}
public class CountdownLatch {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(7);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for(int i = 0; i < 7; i++) {
            executorService.submit(new Processor(countDownLatch));
        }

        countDownLatch.await();

        System.out.println("Completed");
    }
}
