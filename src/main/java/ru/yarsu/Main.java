package ru.yarsu;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
//            String infix = "1+2*(3^2-5)-8";
//            String infix = "1/2";
            Scanner scn = new Scanner(System.in);
            String infix = scn.nextLine();
            scn.close();

            String postfix = Calculator.toPostfix(infix);
            System.out.println("Postfix: " + postfix);
            System.out.println(Calculator.evaluatePostfix(postfix));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
