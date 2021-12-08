package concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SquareCalculator squareCalculator = new SquareCalculator();
        Future<Integer> future1 = squareCalculator.calculate(10);
        Future<Integer> future2 = squareCalculator.calculate(100);
        while (!(future1.isDone() && future2.isDone())) {
            System.out.println("Future 1 is " + (future1.isDone() ? "done" : "not done") + "Future 2 is " + (future2.isDone() ? "done" : "not done"));
            Thread.sleep(100);
        }
        System.out.println("Future 1 : " + future1.get() + ", Future 2 : " + future2.get());
    }
}

class SquareCalculator {
    private final ExecutorService service = Executors.newCachedThreadPool();

    public Future<Integer> calculate(int number) {
        return service.submit(() -> {
            Thread.sleep(1000);
            return number * number;
        });
    }
}
