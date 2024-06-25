package multithread;

import lab_1.Main;
import lab_3.Collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Phaser;


// A thread of execution that uses a Phaser.
class MyThread implements Runnable {
    Phaser phsr;
    String name;

    MyThread(Phaser p, String n) {
        phsr = p;
        name = n;
        phsr.register();
        new Thread(this).start();
    }



    public void run() {

        String input = "";
        String[] sentences = input.split("[.!?]");
        for (String sentence : sentences) {
            int[] counts = Main.countVowelsAndConsonant(sentence.toLowerCase());
            Main.printResults(sentence, counts);
        }

        System.out.println("Thread " + name + " Beginning Phase One");
        phsr.arriveAndAwaitAdvance(); // Signal arrival.

        // Pause a bit to prevent jumbled output. This is for illustration
        // only. It is not required for the proper opration of the phaser.
        try {
            Thread.sleep(10);
        } catch(InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("Thread " + name + " Beginning Phase Two");
        phsr.arriveAndAwaitAdvance(); // Signal arrival.

        // Pause a bit to prevent jumbled output. This is for illustration
        // only. It is not required for the proper operation of the phaser.
        try {
            Thread.sleep(10);
        } catch(InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("Thread " + name + " Beginning Phase Three");
        phsr.arriveAndDeregister(); // Signal arrival and deregister.
    }
}

public class PhaserUsage {
    public static String manipulateText(String text) {
        // Крок 1: Збираємо масив пунктуаційних знаків
        List<Character> punctuationList = new ArrayList<>();
        for (char c : text.toCharArray()) {
            if (Character.toString(c).matches("[!?.]")) {
                punctuationList.add(c);
            }
        }

        Collections.shuffle(punctuationList);

        String textWithoutPunctuation = text.replaceAll("[!?.]", "");
        System.out.println(textWithoutPunctuation);

        List<Integer> whiteSpaceList = new ArrayList<>();
        for (int i = 1; i < textWithoutPunctuation.length(); i++) {
            //int j = 0;
                if (textWithoutPunctuation.charAt(i) == ' ') {
                    whiteSpaceList.add(i);
                    System.out.println(whiteSpaceList.getLast());
                }
        }

        Collections.shuffle(whiteSpaceList);

        // Крок 4: Вставка випадкового символа пунктуації перед кожним пробілом, окрім останнього
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < punctuationList.size(); i++) {
            int pos = whiteSpaceList.get(i);
            System.out.println(pos);
            System.out.println(punctuationList.get(i));
            result.insert(pos, punctuationList.get(i));
        }

        result.append(punctuationList.getLast());

        return result.toString();
    }
    public static void main(String args[]) {
       /* Phaser phsr = new Phaser(1);
        int curPhase;

        System.out.println("Starting");

        new MyThread(phsr, "A");
        new MyThread(phsr, "B");
        new MyThread(phsr, "C");

        // Wait for all threads to complete phase one.
        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Phase " + curPhase + " Complete");

        // Wait for all threads to complete phase two.
        curPhase =  phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Phase " + curPhase + " Complete");

        curPhase =  phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Phase " + curPhase + " Complete");

        // Deregister the main thread.
        phsr.arriveAndDeregister();

        if(phsr.isTerminated())
            System.out.println("The Phaser is terminated");*/

        String text = "Hi! How are you? I'm fine, thanks!";
        String modifiedText = manipulateText(text);

        // Виведення результату
        System.out.println("Original text: " + text);
        System.out.println("Modified text: " + modifiedText);
    }
}
