package edu.nju.mutest.example;

/**
 * Demo test suite for {@link Calculator}
 */
public class CalculatorTestSuite {

    public static void main(String[] args) {
        testAdd();
        testMul();
        testSub();
    }

    private static void testAdd() {
        int oracle = 4;
        int res = Calculator.add(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testAdd() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testAdd() fail (%d, %d)!", oracle, res));
    }

    private static void testSub() {
        int oracle = 2;
        int res = Calculator.subtract(5, 3);
        if (oracle == res)
            System.out.println("[TEST] testSub() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testSub() fail (%d, %d)!", oracle, res));
    }

    private static void testMul() {
        int oracle = 4;
        int res = Calculator.multiply(2, 2);
        if (oracle == res)
            System.out.println("[TEST] testMul() pass!");
        else
            throw new RuntimeException(String.format(
                    "[TEST] testMul() fail (%d, %d)!", oracle, res));
    }

}
