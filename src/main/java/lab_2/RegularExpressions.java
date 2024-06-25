package lab_2;

/*
* 6.Написати регулярне вираження,
* що визначає чи є даний рядок датою у форматі dd/mm/yyyy.
* Починаючи з 1600 року до 9999 року.
*
* Приклад правильних виражень: 29/02/2000, 30/04/2003, 01/01/2003.
* Приклад неправильних виражень: 29/02/2001, 30-04-2003, 1/1/1899.
*/

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressions {

    public static boolean isValidDate(String str){

        // regular expression
        String regex =
                "((([0-2][0-9])|(30))/((0(1|(3-9)))|(1[0-2]))/((1[6-9][0-9]{2})|([2-9][0-9]{3})))|" + //
                        "(31/((0([13578]))|1([02]))/((1[6-9][0-9]{2})|([2-9][0-9][0-9][0-9])))|" + //31
                        "((([01][0-9])|(2[0-8]))/(02)/((1[6-9][0-9]{2})|([2-9][0-9]{3})))|" + //not leap
                        "(29/02/(((1[6-9])|([2-9][0-9]))(([13579][26])|([02468][048]))))"; //leap

        Pattern dataPattern = Pattern.compile(regex);
        Matcher dataMatcher = dataPattern.matcher(str);
        return dataMatcher.matches();
    }

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                System.out.print("Enter data (dd/mm/yyyy): ");
                String inputDate = reader.readLine();

                try {
                    if (!isValidDate(inputDate)) {
                        throw new InvalidDateException("Invalid date format. Try again");
                    }
                    else {
                        System.out.println("Valid date format");
                        // reader.close();
                        break;
                    }
                }
                catch (InvalidDateException e) {

                    System.out.println("Caught: " + e);
                }
            }
            catch (IOException e) {
                System.out.println("Error reading input: " + e.getMessage());
            }
       }
       while (true);
    }
}

class InvalidDateException extends Exception {

    private final String message;

    InvalidDateException(String error) {
        message = error;
    }

    @Override
    public String toString() {
        return message;
    }
}
