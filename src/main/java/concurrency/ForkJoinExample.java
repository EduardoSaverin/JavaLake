package concurrency;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * For this example we will calculate sum of square of numbers from number of factorial
 * Example : 4!
 * Sum : 4*4 + 3*3 + 2*2 + 1*1
 */
public class ForkJoinExample {

    public static void main(String[] args) {
        System.out.println("Thread Count (Start) : " + ManagementFactory.getThreadMXBean().getThreadCount());
        FactorialProductCalculator factorialProductCalculator = new FactorialProductCalculator(4);
        System.out.println(factorialProductCalculator.compute());
        new CustomForkJoinPool().doWork();
        System.out.println("Thread Count (End) : " + ManagementFactory.getThreadMXBean().getThreadCount());

    }
}

/**
 * There are two abstract classes that implement ForkJoinTask:
 * RecursiveTask : which returns a value upon completion, and
 * RecursiveAction : which doesn't return anything.
 */
class FactorialProductCalculator extends RecursiveTask<Integer> {
    private final Integer number;

    public FactorialProductCalculator(Integer number) {
        this.number = number;
    }

    @Override
    protected Integer compute() {
        if (number <= 1) {
            return 1;
        }
        FactorialProductCalculator factorialProductCalculator = new FactorialProductCalculator(number - 1);
        System.out.println("Before Fork : " + ManagementFactory.getThreadMXBean().getThreadCount());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("After Fork : " + ManagementFactory.getThreadMXBean().getThreadCount());
        factorialProductCalculator.fork();
        return number * number + factorialProductCalculator.join();
    }
}

class CustomForkJoinPool {
    private final ForkJoinPool forkJoinPool;
    public CustomForkJoinPool() {
        this.forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors()-1);
    }
    public void doWork() {
        // Below Statement prints nothing I think it executes in background.
//        IntStream.range(0, 1000).forEach((i) -> forkJoinPool.execute(() -> System.out.println(i + ":" + new FactorialProductCalculator(i).compute())));
        IntStream.range(0, 1000).forEach((i) -> forkJoinPool.invoke(new FactorialProductCalculator(i)));
    }
}
