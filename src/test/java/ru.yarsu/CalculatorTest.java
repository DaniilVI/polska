package ru.yarsu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

    @Test
    public void testToPostfixSimple() throws Exception {
        assertEquals("1 2 +", Calculator.toPostfix("1+2"));
    }

    @Test
    public void testToPostfixWithPrecedence() throws Exception {
        assertEquals("1 2 3 * +", Calculator.toPostfix("1+2*3"));
    }

    @Test
    public void testToPostfixWithParentheses() throws Exception {
        assertEquals("1 2 3 + *", Calculator.toPostfix("1*(2+3)"));
    }

    @Test
    public void testToPostfixWithPower() throws Exception {
        assertEquals("2 3 ^", Calculator.toPostfix("2^3"));
    }

    @Test
    public void testToPostfixWithUnaryMinus() throws Exception {
        assertEquals("0 3 -", Calculator.toPostfix("-3"));
        assertEquals("1 0 2 - *", Calculator.toPostfix("1*(-2)"));
        assertEquals("0 2 3 + -", Calculator.toPostfix("-(2+3)"));
    }

    @Test
    public void testEvaluatePostfixSimple() throws Exception {
        assertEquals(3.0, Calculator.evaluatePostfix("1 2 +"), 0.0001);
    }

    @Test
    public void testEvaluatePostfixWithPrecedence() throws Exception {
        assertEquals(7.0, Calculator.evaluatePostfix("1 2 3 * +"), 0.0001);
    }

    @Test
    public void testEvaluatePostfixWithParentheses() throws Exception {
        assertEquals(5.0, Calculator.evaluatePostfix("1 2 3 + *"), 0.0001);
    }

    @Test
    public void testEvaluatePostfixWithPower() throws Exception {
        assertEquals(8.0, Calculator.evaluatePostfix("2 3 ^"), 0.0001);
    }

    @Test
    public void testEvaluatePostfixWithUnaryMinus() throws Exception {
        assertEquals(-3.0, Calculator.evaluatePostfix("0 3 -"), 0.0001);
        assertEquals(-2.0, Calculator.evaluatePostfix("1 0 2 - *"), 0.0001);
        assertEquals(-5.0, Calculator.evaluatePostfix("0 2 3 + -"), 0.0001);
    }

    @Test
    public void testDivisionByZero() {
        Exception exception = assertThrows(Exception.class, () -> {
            Calculator.evaluatePostfix("1 0 /");
        });
        assertTrue(exception.getMessage().contains("Деление на ноль"));
    }

    @Test
    public void testInvalidExpression() {
        Exception exception = assertThrows(Exception.class, () -> {
            Calculator.evaluatePostfix("1 +");
        });
        assertTrue(exception.getMessage().contains("Неверное выражение"));
    }

    @Test
    public void testUnbalancedParenthesesToPostfix() {
        Exception exception = assertThrows(Exception.class, () -> {
            Calculator.toPostfix("1+(2*3");
        });
        assertTrue(exception.getMessage().contains("несоответствие скобок"));
    }

    @Test
    public void testInvalidCharacterToPostfix() {
        Exception exception = assertThrows(Exception.class, () -> {
            Calculator.toPostfix("1 + a");
        });
        assertTrue(exception.getMessage().contains("Недопустимый символ"));
    }
}