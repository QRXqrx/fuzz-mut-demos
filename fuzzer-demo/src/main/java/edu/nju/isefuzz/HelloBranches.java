package edu.nju.isefuzz;

public class HelloBranches {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Require 1 arg!!!");
            System.exit(0);
        }

        // Start to process
        char[] charArr = args[0].toCharArray();
        if (charArr[0] == 'h') {
            System.out.println("Catch h!");
            if (charArr[1] == 'e') {
                System.out.println("Catch he!");
                if (charArr[2] == 'l') {
                    System.out.println("Catch hel!");
                    if (charArr[3] == 'l') {
                        System.out.println("Catch hell!");
                        if (charArr[4] == 'o')
                            System.out.println("Catch hello!");
                        else
                            System.out.println("Failed at (4, o)!");
                    } else {
                        System.out.println("Failed at (3, l)!");
                    }
                } else {
                    System.out.println("Failed at (2, l)!");
                }
            } else {
                System.out.println("Failed at (1, e)!");
            }
        } else {
            System.out.println("Failed at (0, h)!");
        }

    }

}
