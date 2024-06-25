/*
* Здійснити сортування введеного користувачем масиву цілих чисел. Числа вводяться через кому.
* */

package lab_6;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Отримання форми для введення чисел
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Enter numbers</title></head><body>");
        out.println("<h2>Enter integers separated by comma:</h2>");
        out.println("<form action=\"Sort\" method=\"post\">");
        out.println("<input type=\"text\" name=\"numbers\">");
        out.println("<input type=\"submit\" value=\"Sort\">");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Отримання введених чисел
        String numbersString = request.getParameter("numbers");

        try {
            if (numbersString.matches(".*[a-zA-Z].*")) {
                throw new InvalidInputException("Input should not contain letters.");
            }
            // Перевірка, чи числа розділені комами
            else if (!numbersString.matches("\\d+(,\\s*\\d+)*")) {
                throw new InvalidInputException("Numbers should be separated by commas.");
            }
            else {
                // Сортування чисел
                int[] sortedNumbers;

                String[] numberStrings = numbersString.split(",");

                // Перевірка кожного рядка на ціле число
                sortedNumbers = sortNumbers(numbersString);

                // Виведення відсортованого масиву на сторінку
                PrintWriter out = response.getWriter();
                out.println("<html><head><title>Sorted Numbers</title></head><body>");
                out.println("<h2>Sorted numbers:</h2>");
                out.println("<p>");
                for (int num : sortedNumbers) {
                    out.println(num + " ");
                }
                out.println("</p>");
                out.println("</body></html>");
            }
        }
        catch (InvalidInputException e) {
            // Виведення повідомлення про помилку
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h2>Error:</h2>");
            out.println("<p>" + e + "</p>");
            out.println("<form action=\"Sort\" method=\"get\">");
            out.println("<input type=\"submit\" value=\"Go back\">");
            out.println("</form>");
            out.println("</body></html>");
        }

    }

    private int[] sortNumbers(String numbersString) throws NumberFormatException {
        // Перетворення введених чисел у масив і сортування його
        if (numbersString != null && !numbersString.isEmpty()) {
            String[] numbersArray = numbersString.split(",");
            int[] numbers = new int[numbersArray.length];
            for (int i = 0; i < numbersArray.length; i++) {
                numbers[i] = Integer.parseInt(numbersArray[i].trim());
            }
            Arrays.sort(numbers);
            return numbers;
        }
        else {
            return new int[0]; // Повернення пустого масиву, якщо рядок пустий
        }
    }
}

class InvalidInputException extends Exception {

    private final String message;
    InvalidInputException (String message) {
        this.message = message;
    }

    public String toString() {

        return message;
    }
}