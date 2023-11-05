package threadpools;

public class Processor implements Runnable {
    private int id;
    public Processor(int id) {
        this.id = id;
    }
    @Override
    public void run() {
        System.out.println("starting task : "+id);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Completed task : "+id);
    }
}
