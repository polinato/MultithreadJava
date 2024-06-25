package multithread;

import lab_1.Main;
import lab_2.RegularExpressions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WaitNotify {

    public synchronized void checkDate() {

        System.out.println("THREAD_ID: " + Thread.currentThread().getId() + "\n");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter data (dd/mm/yyyy): ");
            String inputDate = reader.readLine();
            System.out.println();

            notify();

            wait();

            System.out.println("THREAD_ID: " + Thread.currentThread().getId() + "\n");
            System.out.println("Date is" + (RegularExpressions.isValidDate(inputDate) ? "" : "n\'t") + " correct");
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void lettersCount() {

        System.out.println("THREAD_ID: " + Thread.currentThread().getId() + "\n");

        String path = "C:\\Users\\Public\\Documents\\code\\Labs\\input.txt";

        try {
            String text = Main.readFile(path);

            wait();

            System.out.println("THREAD_ID: " + Thread.currentThread().getId() + "\n");

            String[] sentences = text.split("[.!?]");
            for (String sentence : sentences) {

                //робимо все одного регістру (нижнього)
                int[] counts = Main.countVowelsAndConsonant(sentence.toLowerCase());
                Main.printResults(sentence, counts);
            }

            notify();
        }
        catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        System.out.println("THREAD_ID: " + Thread.currentThread().getId() + "\n");

        WaitNotify waitNotify = new WaitNotify();

        new Thread(waitNotify::lettersCount).start();

        new Thread(waitNotify::checkDate).start();
    }
}
