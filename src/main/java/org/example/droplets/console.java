package org.example.droplets;

import java.util.Scanner;

public final class console {
    public static void println(String s) {
        System.out.println(s);
    }

    public static void print(String s) {
        System.out.print(s);
    }

    public static void paragraph() {
        System.out.println("------------------------");
    }

    public static String readline() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static String readline(String prompt) {
        console.print(prompt);
        return readline();
    }
}
