package CLI;

import java.util.Scanner;

public class InputHelper {
    static Scanner sc = new Scanner(System.in);
    public static int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = sc.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Помилка: Число має бути від " + min + " до " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Це не число. Введіть цифру.");
            }
        }
    }
    public static double getDoubleInput(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = sc.nextLine();
            try {
                double value = Double.parseDouble(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Помилка: Число має бути від " + min + " до " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: Це не число. Введіть цифру.");
            }
        }
    }
    public static String getStringInput(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine();
    }


}
