package com.dzco.hibernatepractice.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuUtil {
    public static int readInt(int min, int max, Scanner in) {
        int num = 0;
        String buf = "";
        buf = in.nextLine();
        try {
            num = Integer.parseInt(buf);
            if (num < min || num > max) {
                throw new InputMismatchException();
            }
        } catch (Exception ex) {
            System.out.println("Некорректный ввод! Попробуйте снова.");
            return readInt(min, max, in);
        }
        return num;
    }

    public static int readInt(Scanner in) {
        int num = 0;
        String buf = "";
        buf = in.nextLine();
        try {
            num = Integer.parseInt(buf);
        } catch (Exception ex) {
            System.out.println("Некорректный ввод! Попробуйте снова.");
            return readInt(in);
        }
        return num;
    }

    public static int readInt(int min, Scanner in) {
        int num = 0;
        String buf = "";
        buf = in.nextLine();
        try {
            num = Integer.parseInt(buf);
            if (num < min) {
                throw new InputMismatchException();
            }
        } catch (Exception ex) {
            System.out.println("Некорректный ввод! Попробуйте снова.");
            return readInt(in);
        }
        return num;
    }
}
