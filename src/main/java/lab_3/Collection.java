package lab_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

public class Collection {

    public static int[] stringToInt(String str) {

        String[] numbersArray = str.split(",");
        int[] numbers = new int[numbersArray.length];
        for (int i = 0; i < numbersArray.length; i++) {
            numbers[i] = Integer.parseInt(numbersArray[i].trim());
        }
        return numbers;
    }
    public static int countSumCouple(int[] array) {

        while (array.length > 1) {
            int[] arraySum = new int[(array.length + 1) / 2];
            for (int i = 0; i < array.length - 1; i += 2) {
                arraySum[i / 2] = array[i] + array[i + 1];
            }
            if (array.length % 2 != 0) {
                arraySum[arraySum.length - 1] = array[array.length - 1];
            }
            System.out.println(Arrays.toString(arraySum));
            array = arraySum;
        }
        return array[0];
    }

    public static int[] generateRandomArray(int arraySize) {
        int[] randomArray = new int[arraySize];
        Random random = new Random();

        for (int i = 0; i < arraySize; i++) {
            randomArray[i] = random.nextInt(100); // Генеруємо випадкове число в діапазоні від 0 до 99
        }
        return randomArray;
    }

    public static void main(String[] args) {


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // Додамо цикл do-while для повторення вводу в разі помилки
        do {
            System.out.print("Enter array: ");

            String str = "";

            try {
                str = reader.readLine();
            }
            catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                System.exit(0);
            }

            try {
                // Перевірка, чи текст містить букви
                if (str.matches(".*[a-zA-Z].*")) {
                    throw new IncorrectInputException("Input should not contain letters.");
                }
                // Перевірка, чи числа розділені комами
                else if (!str.matches("\\d+(,\\s*\\d+)*")) {
                    throw new IncorrectInputException("Numbers should be separated by commas.");
                }
                else {
                    System.out.println("Result: " + countSumCouple(stringToInt(str)));
                    // Якщо дані правильні, виходимо з циклу
                    break;
                }
            }
            catch (IncorrectInputException e) {
                System.out.println("Caught: " + e);
            }
        } while (true); // Повторюємо введення даних, доки не будуть введені правильно
       /* try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }*/
    }
}

class IncorrectInputException extends Exception {

    private final String message;

    IncorrectInputException(String error) {
        message = error;
    }

    @Override
    public String toString() {
        return message;
    }
}