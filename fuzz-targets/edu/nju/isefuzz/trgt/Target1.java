package edu.nju.isefuzz.trgt;

/**
 * Require a string as input. A string that starts with 'hello'
 * will trigger the injected bug.
 */
public class Target1 {

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.out.println("[TARGET] Require 1 arg!!!");
            System.exit(0);
        }

        // Start to process
        char[] charArr = args[0].toCharArray();
        if (charArr[0] == 'h') {
            System.out.println("[TARGET] Catch h!");
            if (charArr[1] == 'e') {
                System.out.println("[TARGET] Catch he!");
                if (charArr[2] == 'l') {
                    System.out.println("[TARGET] Catch hel!");
                    if (charArr[3] == 'l') {
                        System.out.println("[TARGET] Catch hell!");
                        if (charArr[4] == 'o')
                            throw new Exception("[TARGET] Hello! Find a bug!");
                        else
                            System.out.println("[TARGET] Failed at (4, o)!");
                    } else {
                        System.out.println("[TARGET] Failed at (3, l)!");
                    }
                } else {
                    System.out.println("[TARGET] Failed at (2, l)!");
                }
            } else {
                System.out.println("[TARGET] Failed at (1, e)!");
            }
        } else {
            System.out.println("[TARGET] Failed at (0, h)!");
        }

    }

}
