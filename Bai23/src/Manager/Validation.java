/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manager;

import java.util.Scanner;

public class Validation {

    public static int readInt(Scanner scanner, String message, int min, int max) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Show the same validation message for non-numeric input.
            }
            System.out.println("Please enter a number from " + min + " to " + max + ".");
        }
    }

    public static double readPositiveDouble(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value > 0 && !Double.isInfinite(value) && !Double.isNaN(value)) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Show the same validation message for non-numeric input.
            }
            System.out.println("Please enter a number greater than 0.");
        }
    }

    public static String readNonEmpty(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty.");
        }
    }

    public static boolean readYesNo(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String answer = scanner.nextLine().trim();
            if (answer.equalsIgnoreCase("Y")) {
                return true;
            }
            if (answer.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Please enter Y or N.");
        }
    }
}
