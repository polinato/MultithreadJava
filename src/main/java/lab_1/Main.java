package lab_1;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

//10. Знайти, яких букв, голосних або приголосних, більше в кожному реченні тексту.

public class Main {

    public static String readFile(String path) {

        String text = "";

        try {
            //зчитуємо файл
            text = new String(Files.readAllBytes(Paths.get(path)));

            try {
                if (text.isEmpty()) {
                    throw new EmptyTextException("Empty text. Try again!");
                }

                else {
                    System.out.println("Input text:");
                    System.out.println(text);
                    System.out.println();
                }
            }
            catch (EmptyTextException e) {

                System.out.println("Caught: " + e);
                System.exit(0);
            }

        }
        catch(IOException e) {
            System.out.println("Reading file error: " + e.getMessage());
        }

        return text;
    }

    public static int[] countVowelsAndConsonant(String sentence) {

        //обнуляємо лічильники
        int vowels = 0;
        int consonants = 0;

        for (int i = 0; i < sentence.length(); i++) {
            //перебираємо кожен символ в реченні
            char ch = sentence.charAt(i);
            //перевірка, чи це літера
            if (Character.isLetter(ch)) {
                //додаємо 1 до відповідної категорії
                if (isVowel(ch)) {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }

        return new int[]{vowels, consonants};
    }
    public static boolean isVowel(char ch) {

        return "a, e, i, o, u".indexOf(ch) != -1;
    }

    public static void printResults(String sentence, int[] counts) {

        int vowels = counts[0];
        int consonants = counts[1];

        System.out.println("Sentence: " + sentence);

        //порівнюємо та друкуємо результат
        if (vowels > consonants) {
            System.out.println("There are more vowels than consonants");
        }
        else if (vowels < consonants) {
            System.out.println("There are more consonants than vowels");
        }
        else {
            System.out.println("The number of consonants and vowels is the same");
        }

        System.out.println();
    }

    public static void main(String[] args) {

        String path = "C:\\Users\\Public\\Documents\\code\\Labs\\input.txt";

        String text = "";

        try {
            text = readFile(path);
        }
        catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
        }

        try {
            String[] sentences = text.split("[.!?]");

            if ((sentences[0].isEmpty()) && (sentences.length == 1)) {

                throw new EmptyTextException("Empty sentence!");
            }
            else {
                //перебираємо кожне речення
                for (String sentence : sentences) {

                    //робимо все одного регістру (нижнього)
                    int[] counts = countVowelsAndConsonant(sentence.toLowerCase());
                    printResults(sentence, counts);
                }
            }
        }
        catch (EmptyTextException e) {
            System.out.println("Caught: " + e);
        }
    }
}

class EmptyTextException extends Exception {

    private final String message;

    EmptyTextException(String error) {

        message = error;
    }

     @Override
     public String toString() {

        return message;
     }
}