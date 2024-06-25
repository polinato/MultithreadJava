package multithread;

import lab_3.Collection;

import java.util.concurrent.*;

class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() {

        return lab_3.Collection.countSumCouple(Collection.generateRandomArray(10)); // Повернення результату
    }
}

public class CallableUsage {

    public static void main(String[] args) {

        int poolCount = 3;

        ExecutorService[] threadPools = new ExecutorService[poolCount];

        for (int i = 0; i < poolCount; i++) {
            threadPools[i] = Executors.newSingleThreadExecutor();
        }

        Callable<Integer> callable = new MyCallable();

        Future[] futures = new Future[poolCount];
        for (int i = 0; i < poolCount; i++) {
            futures[i] = threadPools[i].submit(callable);
        }

        for (int i = 0; i < poolCount; i++) {
            try {
                Integer result = (Integer) futures[i].get();
                System.out.println("Result from Pool " + i + ": " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        for (ExecutorService threadPool : threadPools) {
            threadPool.shutdown();
        }
    }
}
