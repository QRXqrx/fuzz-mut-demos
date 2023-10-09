package edu.nju.isefuzz;

import org.junit.Test;

import java.util.Random;

public class TrialTest {


    private char mutateChar(char x) {
        return x + 1 > 'z' ? (char) ((x + 1) % 'z' - 1 + 'a')
                           : (char) (x + 1);
    }

    @Test
    public void test1() {
        for (int i = 'a'; i < 'z'; i++) {
            System.out.println(mutateChar((char) i));
        }
    }

    @Test
    public void test() {
        Random rand = new Random();
        for (int i = 0; i < 200; i++) {

            int num = rand.nextInt(127);
            System.out.printf("%d, %c\n", num, num);

        }

    }

}
