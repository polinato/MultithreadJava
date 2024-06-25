package multithread;

import lab_1.Main;
import lab_3.Collection;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static lab_3.Collection.countSumCouple;

public class CyclicBarrierUsage {

    String input = "Hi, how are you? I'm fine";
    static int delay = 100;

    public void startMain() {

        String[] sentences = input.split("[.!?]");
        for (String sentence : sentences) {
            int[] counts = Main.countVowelsAndConsonant(sentence.toLowerCase());
            Main.printResults(sentence, counts);
        }
    }

    public void executeTogether(CyclicBarrier c1, CyclicBarrier c2, CyclicBarrier c3) {

        try {
            startMain();
            c1.await();

            input = "10, 41, 53, 5, 15, 15, 9";
            delay *= 2;
            Thread.sleep(delay);
            System.out.println("Enter array: " + input + "\n");
            c2.await();

            delay /= 2;
            Thread.sleep(delay);
            System.out.println("Result: " + countSumCouple(Collection.stringToInt(input)) + "\n");
            c3.await();
        }
        catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
}

static final int COUNT = 4;

    public static void main(String[] args) {

        try (ExecutorService service = Executors.newFixedThreadPool(4)) {

            CyclicBarrier c1 = new CyclicBarrier(COUNT, () -> System.out.println("First barrier\n"));
            CyclicBarrier c2 = new CyclicBarrier(COUNT, () -> System.out.println("Second barrier\n"));
            CyclicBarrier c3 = new CyclicBarrier(COUNT, () -> System.out.println("Third barrier\n"));

            CyclicBarrierUsage cyclicBarrierUsage = new CyclicBarrierUsage();

            try {
                for (int i = 0; i < COUNT; i++) {
                    service.submit(() -> cyclicBarrierUsage.executeTogether(c1, c2, c3));
                    Thread.sleep(delay);
                }
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            finally {
                service.shutdown();
            }
        }
    }
}
