package edu.nju.isefuzz;

public class HelloBranches1 {

    public static void main(String[] args) {

        if (args.length != 1) {
            printCov();
            System.out.println("Require 1 arg!!!");
            System.exit(0);
        }

        // Start to process
        char[] charArr = args[0].toCharArray();
        if (charArr[0] == 'h') {
            printCov();
            System.out.println("Catch h!");
            if (charArr[1] == 'e') {
                printCov();
                System.out.println("Catch he!");
                if (charArr[2] == 'l') {
                    printCov();
                    System.out.println("Catch hel!");
                    if (charArr[3] == 'l') {
                        printCov();
                        System.out.println("Catch hell!");
                        if (charArr[4] == 'o') {
                            printCov();
                            System.out.println("Catch hello!");
                        } else {
                            printCov();
                            System.out.println("Failed at (4, o)!");
                        }
                    } else {
                        printCov();
                        System.out.println("Failed at (3, l)!");
                    }
                } else {
                    printCov();
                    System.out.println("Failed at (2, l)!");
                }
            } else {
                printCov();
                System.out.println("Failed at (1, e)!");
            }
        } else {
            printCov();
            System.out.println("Failed at (0, h)!");
        }

    }

    public static void printCov() {
        System.out.println("[LOG] Reaching a branch!");
    }

}
