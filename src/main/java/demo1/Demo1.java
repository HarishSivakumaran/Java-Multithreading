package demo1;

public class Demo1 extends Thread{
    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            System.out.println("hello from thread :" + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
