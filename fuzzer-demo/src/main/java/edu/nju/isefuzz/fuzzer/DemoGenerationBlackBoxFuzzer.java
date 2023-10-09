package edu.nju.isefuzz.fuzzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class DemoGenerationBlackBoxFuzzer {


    private static char genRandLowerCaseChar() {
        int lower = 'a';
        int upper = 'z';
        Random rand = new Random();
        return (char) (lower + rand.nextInt(upper - lower));
    }

    /**
     * Generate a string with given length using [a-z]
     */
    private static String genRanLowerCaseStringInput(int len) {
        StringBuilder inputBuilder = new StringBuilder();
        for (int i = 0 ; i < len; i++) {
            inputBuilder.append(genRandLowerCaseChar());
        }
        return inputBuilder.toString();
    }

    public static void main(String[] args) throws IOException {

        String classPath = args[0];
        String targetName = args[1];

        System.out.println("[FUZZER] classPath: " + classPath);
        System.out.println("[FUZZER] targetName: " + targetName);

        while (true) {

            // Generate a test input.
            String testInput = genRanLowerCaseStringInput(5);

            // Execute the
            System.out.println("[FUZZER] Execute the target with input: " + testInput);
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "java", "-cp", classPath, targetName, testInput
            );
            System.out.println(processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process p = processBuilder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }


        }

    }

}
