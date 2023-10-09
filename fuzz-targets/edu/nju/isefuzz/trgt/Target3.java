package edu.nju.isefuzz.trgt;

/**
 * Try to guess the password check.
 */
public class Target3 {

    private static void targetLog(String mes) {
        System.out.printf("[TARGET] %s\n", mes);
    }

    static String pwd = "isefuzz";
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

        boolean equalLen = input.length() == pwd.length();
        boolean pass = equalLen;
        int traverseLen = Math.min(input.length(), pwd.length());

        for (int i = 0 ; i < traverseLen; i++) {

            boolean equalChar = (input.charAt(i) == pwd.charAt(i));
            if (equalChar) {
                targetLog(String.format("Catch %c at %d!", pwd.charAt(i), i));
            } else {
                pass = false;
            }

        }

        if (pass) {
            throw new Exception("Successful intruding!");
        } else if (!equalLen) {
            targetLog("Oops, the length of the input is incorrect: "
                    + input.length());
        }

    }

}
