package synchronizedMethodblocks;

public class SynchronizedBlocks {
    private int counter = 0;
    public static void main(String[] args) throws InterruptedException {
    SynchronizedBlocks synchronizedBlocks = new SynchronizedBlocks();
    synchronizedBlocks.doWork();
    }

    public synchronized void increment() {
        counter++;
    }

    public void doWork() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 1000; i++) {
                    increment();
//                    counter--;
//                    counter++;
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 1000; i++){
                    increment();
//                    counter--;
                }
//                    counter++;
            }
        });

        t1.start(); t2.start();

        t1.join();
        t2.join();
        System.out.println(counter);
    }
}
