package concurrency;
import java.util.concurrent.locks.ReentrantLock;

public class OddEvenThread {
    int counter = 1;
    int max = 10;
    public static void main(String[] args) throws InterruptedException {
        OddEvenThread demo = new OddEvenThread();
        new Thread(() -> {
            try {
                demo.odd();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();;
        new Thread(() -> {
            try {
                demo.even();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();
    }
    public void even() throws InterruptedException{
        synchronized (this) {
            while( counter < max) {
                while(counter%2 == 1) {
                    wait();
                }
                System.out.println(counter);
                counter += 1;
                notify();
            }
        }
    }
    public void odd() throws InterruptedException {
        synchronized (this) {
            
            while (counter < max) {
                while(counter%2 == 0) {
                    wait();
                }
                System.out.println(counter);
                counter += 1;
                notify();
            }
        }
    }
}
