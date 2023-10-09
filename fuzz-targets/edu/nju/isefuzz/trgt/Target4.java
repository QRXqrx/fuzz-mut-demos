package edu.nju.isefuzz.trgt;

/**
 * A GPT-3.5 generated program. Injected with a bug.
 */
public class Target4 {

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

        char choice = input.charAt(0);

        switch (choice) {
            case '1':
                String reversed = reverseString(input);
                targetLog("Reversed string: " + reversed);
                break;
            case '2':
                int[] counts = countVowelsAndConsonants(input);
                targetLog("Vowels: " + counts[0]);
                targetLog("Consonants: " + counts[1]);
                break;
            case '3':
                boolean isPalindrome = isPalindrome(input);
                targetLog("Is it a palindrome? " + isPalindrome);
                break;
            default:
                targetLog("Invalid choice.");
        }

    }

    // Function to reverse a string
    public static String reverseString(String str) {
        StringBuilder reversed = new StringBuilder(str);
        return reversed.reverse().toString();
    }

    // Function to count vowels and consonants in a string
    public static int[] countVowelsAndConsonants(String str) throws Exception {
        int vowels = 0;
        int consonants = 0;
        str = str.toLowerCase();

        for (char c : str.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                    vowels++;
                } else{
                    consonants++;
                }
            } else {
                // Injected bug
                throw new Exception("[TARGET] Find a bug!");
            }
        }

        return new int[] { vowels, consonants };
    }

    // Function to check if a string is a palindrome
    public static boolean isPalindrome(String str) {
        str = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        String reversed = reverseString(str);
        return str.equals(reversed);
    }

}
