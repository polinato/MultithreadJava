package multithread;

import lab_1.Main;
import lab_3.Collection;
import lab_4.VisualInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Semaphore;

public class SemaphoreUsage {

    private static void startMain() throws InterruptedException, IOException {
        s1.acquire();
        System.out.println("***Semaphore 1 acquires***");

        System.out.print("Enter some text: ");
        String text = reader.readLine();
        String[] sentences = text.split("[.!?]");

        System.out.println("***Semaphore 2 releases***");
        s2.release();

        s1.acquire();
        System.out.println("***Semaphore 1 acquires***");

        for (String sentence : sentences) {
            int[] counts = Main.countVowelsAndConsonant(sentence.toLowerCase());
            Main.printResults(sentence, counts);
        }

        System.out.println("***Semaphore 2 releases***");
        s2.release();
    }

    public static void startCollection() throws InterruptedException, IOException {

        s2.acquire();
        System.out.println("***Semaphore 2 acquires***");

        System.out.print("Enter array: ");
        String array = reader.readLine();

        System.out.println("***Semaphore 3 releases***");
        s3.release();

        s2.acquire();
        System.out.println("***Semaphore 2 acquires***");

        Collection.countSumCouple(Collection.stringToInt(array));

        System.out.println("***Semaphore 2 releases***");
        s2.release();
    }

    public static void startVisualInterface(String[] args) throws InterruptedException {

        s3.acquire();
        System.out.println("***Semaphore 3 acquires***");

        VisualInterface.main(args);

        System.out.println("***Semaphore 1 releases***");
        s1.release();
    }

    static Semaphore s1 = new Semaphore(1);
    static Semaphore s2 = new Semaphore(0);
    static Semaphore s3 = new Semaphore(0);
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws InterruptedException, IOException {
        new Thread(() -> {
            try {
                startMain();
            } catch (InterruptedException | IOException e) {
                System.out.println(e.getMessage());
            }
        }).start();
        new Thread(() -> {
            try {
                startCollection();
            } catch (InterruptedException | IOException e) {
                System.out.println(e.getMessage());
            }
        }).start();
        new Thread(() -> {
            try {
                startVisualInterface(args);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }
}
