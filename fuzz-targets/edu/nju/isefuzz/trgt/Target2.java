package edu.nju.isefuzz.trgt;

/**
 * Require a string in format "a,b,c", where a, b, c are the
 * side lengths of a triangle.
 */
public class Target2 {

    private static void targetLog(String mes) {
        System.out.printf("[TARGET] %s\n", mes);
    }

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            targetLog("Require 1 arg!!!");
            System.exit(0);
        }

        String input = args[0];

        if (input.isEmpty()) {
            targetLog("Empty string!");
            System.exit(0);
        }

        String[] parts = input.split(",");

        if (parts.length != 3) {
            targetLog("Oops, Invalid input: " + input);
            System.exit(0);
        }

        int a = Integer.parseInt(parts[0]);
        int b = Integer.parseInt(parts[1]);
        int c = Integer.parseInt(parts[2]);

        if ((a + b > c && a + c > b && b + c > a)) {
            if (a == b && b == c)
                targetLog("Equilateral triangle!!");
            else if (a == b || b == c || a == c)
                targetLog("Isosceles triangle!");
            else {
                targetLog("Scalene triangle.");
                if (a*a + b*b == c*c || a*a + c*c == b*b || b*b + c*c == a*a) {
                    targetLog("Also a right triangle!");
                    if (c == 15) {
                        throw new Exception("[TARGET] Find a bug! Do not want such a triangle.");
                    }
                }
            }

        } else
            targetLog("Oops, not a triangle :-(");


    }

}
