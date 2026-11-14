package utils;

import java.util.Scanner;

public class Util {

    public static Scanner scannerStr = new Scanner(System.in);
    public static Scanner scannerNum = new Scanner(System.in);

    public static int userId = 10;
    public static int postId = 1;

    public static int currentUserId = 0;


    public static int inputInt(String text){
        System.out.print(text + ": ");
        return scannerNum.nextInt();
    }

    public static String inputStr(String text){
        System.out.print(text + ": ");
        return scannerStr.nextLine();
    }

}
