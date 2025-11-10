package ru.yarsu;

import java.util.*;

public class Calculator {
    private static int priority(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    public static String toPostfix(String expression) throws Exception {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        int i = 0;

        while (i < expression.length()) {
            char c = expression.charAt(i);

            if (c == '-') {
                if (i == 0 || expression.charAt(i - 1) == '(' || isOperator(expression.charAt(i - 1))) {
                    result.append("0 ");
                }
            }

            if (Character.isDigit(c)) {
                StringBuilder number = new StringBuilder();
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    number.append(expression.charAt(i));
                    i++;
                }
                result.append(number).append(' ');
                continue;
            }

            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(' ');
                }
                if (stack.isEmpty() || stack.pop() != '(') {
                    throw new Exception("Ошибка в выражении: несоответствие скобок");
                }
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && priority(c) <= priority(stack.peek())) {
                    if (stack.peek() == '(') break;
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            } else {
                throw new Exception("Недопустимый символ: " + c);
            }
            i++;
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(') throw new Exception("Ошибка в выражении: несоответствие скобок");
            result.append(stack.pop()).append(' ');
        }

        return result.toString().trim();
    }

    public static double evaluatePostfix(String expression) throws Exception {
        Stack<Double> stack = new Stack<>();
        String[] tokens = expression.split("\\s+");

        for (String token : tokens) {
            if (isOperator(token.charAt(0)) && token.length() == 1) {
                if (stack.size() < 2) throw new Exception("Неверное выражение");
                double b = stack.pop();
                double a = stack.pop();
                switch (token.charAt(0)) {
                    case '+' -> stack.push(a + b);
                    case '-' -> stack.push(a - b);
                    case '*' -> stack.push(a * b);
                    case '/' -> {
                        if (b == 0) throw new Exception("Деление на ноль");
                        stack.push(a / b);
                    }
                    case '^' -> stack.push(Math.pow(a, b));
                    default -> throw new Exception("Несуществующий оператор: " + token);
                }
            } else {
                stack.push(Double.parseDouble(token));
            }
        }

        if (stack.size() != 1) throw new Exception("Неверное выражение");
        return stack.pop();
    }
}
