package computer.memory;

import java.util.Scanner;

/**
 * Direct Memory Access
 */
public class DMA {

  public static final int KEYBOARD_ADDRESS = 2047;

  public static final String FONT_GREEN = "\u001B[32m";
  public static final String RESET = "\u001B[0m";

  public static int scan(String message) {
    int value = 0;
    print(message);
    Scanner scanner = new Scanner(System.in);
    value = scanner.nextInt();
    scanner.close();
    return value;
  }

  public static void print(String message) {
      System.out.println(FONT_GREEN + message + RESET);
  }
}
