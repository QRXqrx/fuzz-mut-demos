package edu.nju.isefuzz.trgt;

/**
 * A GPT-3.5 generated program. Injected with a bug.
 */
public class Target5 {

    private static void targetLog(String mes) {
        System.out.printf("[TARGET] %s\n", mes);
    }
    
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            targetLog("Usage: java Target5 <input_string>");
            return;
        }

        String input = args[0];

        if (input.isEmpty()) {
            targetLog("The input string is empty.");
        } else {
            if (input.length() <= 10) {
                if (input.startsWith("A")) {
                    targetLog("The input starts with 'A' and has 10 or fewer characters.");
                } else {
                    targetLog("The input does not start with 'A' and has 10 or fewer characters.");
                }
            } else {
                if (input.endsWith("Z")) {
                    targetLog("The input ends with 'Z' and has more than 10 characters.");
                    if (input.contains("B")) {
                        if (input.contains("C")) {
                            targetLog("The input contains both 'B' and 'C'.");
                            // Injected bug
                            throw new Exception("[TARGET] Find a bug!");
                        } else {
                            targetLog("The input contains 'B' but not 'C'.");
                        }
                    } else {
                        targetLog("The input does not contain 'B'.");
                    }
                } else {
                    targetLog("The input does not end with 'Z' and has more than 10 characters.");
                }

            }
        }
    }
}

